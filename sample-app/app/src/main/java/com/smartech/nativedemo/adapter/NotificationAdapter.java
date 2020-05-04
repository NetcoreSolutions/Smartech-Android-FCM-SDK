package com.smartech.nativedemo.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.smartech.nativedemo.R;
import com.smartech.nativedemo.utils.Netcore;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import in.netcore.smartechfcm.notification.CircleIndicator;
import in.netcore.smartechfcm.notification.ClickInterface;
import in.netcore.smartechfcm.notification.DeleteEventsListener;
import in.netcore.smartechfcm.notification.NotificationList;


public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> implements ClickInterface {
    public static final String TAG = NotificationAdapter.class.getSimpleName();
    private static final String dateFormat = "dd MMM yyyy";
    private static final String text_today = "Today";
    private static final String text_yesterday = "Yesterday";
    private static final String text_read_less = "...Read less";
    private static final String text_read_more = "Read more...";
    private static final String text_read = "read";
    private static final String text_unread = "unread";
    private static final int colorWhite = Color.parseColor("#FFFFFF");
    private static final int color_white_selected = Color.parseColor("#DDDDDD");
    private Context mContext;
    private List<NotificationList> notificationList;
    private List<NotificationList.NotificationModel> notificationModelList;
    private List<String> selectedList;
    private Gson gson;
    private DeleteEventsListener deleteListener;
    private boolean selectedFlag = false;
    private CarouselAdapter carouselAdapter;
    private int selectedItemPosition = -1;
    private int deselectedItemPosition = -1;

    public NotificationAdapter(Context context, List<NotificationList> notificationList, DeleteEventsListener deleteEventsListener) {
        notificationModelList = new ArrayList<>();
        selectedList = new ArrayList<>();
        gson = new Gson();
        this.mContext = context;
        this.notificationList = notificationList;
        deleteListener = deleteEventsListener;
        updateList(notificationList);
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.notification_row, viewGroup, false);

        return new NotificationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final NotificationViewHolder holder, final int position) {
        final NotificationList.NotificationModel notificationModel;
        notificationModel = notificationModelList.get(position);
        final String strDate = notificationList.get(position).getDeliverTime();

        setUpDate(holder.textDate, strDate);

        holder.textTitle.setTypeface(null,
                notificationList.get(position).getStatus().equals(text_read) ? Typeface.NORMAL : Typeface.BOLD);
        holder.textTitle.setText(notificationModel.getTitle() != null ? notificationModel.getTitle() : "");
        holder.textMessage.setText(notificationModel.getMessage() != null ? notificationModel.getMessage() : "");

        if (notificationModel.getImage() != null && !notificationModel.getImage().equals("")) {
            holder.imgNotification.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(notificationModel.getImage()).placeholder(R.drawable.placeholder_image).into(holder.imgNotification);
        }

        int showActionButtonLayout = notificationModel.getActionButton().size() != 0
                && notificationModel.getActionButton() != null ? View.VISIBLE : View.GONE;
        holder.dividerview.setVisibility(showActionButtonLayout);
        if (showActionButtonLayout == View.VISIBLE) {
            for (int i = 0; i < notificationModel.getActionButton().size(); i++) {
                handleActions(holder, notificationModel, i, position);
            }
        }

        if (notificationModel.getCarousel().size() != 0 && notificationModel.getCarousel() != null) {
            holder.layoutCarousel.setVisibility(View.VISIBLE);
            holder.carousalDividerView.setVisibility(View.VISIBLE);
            carouselAdapter = new CarouselAdapter(mContext, notificationModel, notificationList.get(position), position, this);
            holder.mPager.setAdapter(carouselAdapter);

            holder.indicator.setViewPager(holder.mPager);

            if (holder.mPager.getCurrentItem() == 0) {
                if (notificationModel.getCarousel().get(holder.mPager.getCurrentItem()).getImgMsg() != null &&
                        !notificationModel.getCarousel().get(holder.mPager.getCurrentItem()).getImgMsg().trim().isEmpty()) {
                    holder.text_carousal_message.setVisibility(View.VISIBLE);
                    holder.text_carousal_message.setText(notificationModel.getCarousel().get(holder.mPager.getCurrentItem()).getImgMsg());
                }
                if (notificationModel.getCarousel().get(holder.mPager.getCurrentItem()).getImgTitle() != null &&
                        !notificationModel.getCarousel().get(holder.mPager.getCurrentItem()).getImgTitle().trim().isEmpty()) {
                    holder.text_carousal_title.setVisibility(View.VISIBLE);
                    holder.text_carousal_title.setText(notificationModel.getCarousel().get(holder.mPager.getCurrentItem()).getImgTitle());
                }
            }

            holder.mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int i, float v, int i1) {
                }

                @Override
                public void onPageSelected(int i) {
                    int setCarouselTitleVisibility = notificationModel.getCarousel().get(i).getImgTitle() != null &&
                            !notificationModel.getCarousel().get(i).getImgTitle().trim().isEmpty() ? View.VISIBLE : View.GONE;
                    holder.text_carousal_title.setVisibility(setCarouselTitleVisibility);
                    holder.text_carousal_title.setText(setCarouselTitleVisibility == View.VISIBLE ?
                            notificationModel.getCarousel().get(i).getImgTitle() : "");

                    int setCarouselMessageVisibility = notificationModel.getCarousel().get(i).getImgMsg() != null &&
                            !notificationModel.getCarousel().get(i).getImgMsg().trim().isEmpty() ? View.VISIBLE : View.GONE;
                    holder.text_carousal_message.setVisibility(setCarouselMessageVisibility);
                    holder.text_carousal_message.setText(setCarouselMessageVisibility == View.VISIBLE ?
                            notificationModel.getCarousel().get(i).getImgMsg() : "");
                }

                @Override
                public void onPageScrollStateChanged(int i) {
                }
            });
        }

        holder.textMessage.post(new Runnable() {
            @Override
            public void run() {
                if (holder.textMessage.getLineCount() <= 3) {
                    holder.btnKnowMore.setVisibility(View.GONE);
                }
            }
        });
        if (position == selectedItemPosition) {
            holder.cardView.setCardBackgroundColor(color_white_selected);
            selectedItemPosition = -1;
        }
        if (position == deselectedItemPosition) {
            holder.cardView.setCardBackgroundColor(colorWhite);
            deselectedItemPosition = -1;
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNotificationStatus(holder.textTitle, position);

                Toast.makeText(mContext, notificationModel.getCustomPayload().toString(), Toast.LENGTH_LONG).show();
                if (selectedFlag) {
                    if (notificationModel.isSelected()) {
                        holder.cardView.setCardBackgroundColor(colorWhite);
                        removeSelectedItem(notificationModel);
                    } else {
                        holder.cardView.setCardBackgroundColor(color_white_selected);
                        addSelectedItem(notificationModel);

                    }
                } else {
                    if (notificationModel.getDeeplink() != null && !notificationModel.getDeeplink().trim().equals("")) {
                        try {
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(notificationModel.getDeeplink()));
                            browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                            Bundle bundle = new Bundle();
                            bundle.putString("customPayload", notificationModel.getCustomPayload().toString());
                            bundle.putString("deeplink", notificationModel.getDeeplink());
                            browserIntent.putExtras(bundle);

                            mContext.startActivity(browserIntent);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(mContext, "Invalid Deeplink", Toast.LENGTH_LONG).show();
                        }
                    }
                }

                if (selectedList.size() == 0) {
                    selectedFlag = false;
                    deleteListener.showDeleteAction(selectedFlag);
                }
            }
        });

        holder.btnKnowMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNotificationStatus(holder.textTitle, position);
                boolean isViewExpanded = notificationModel.isExpanded();

                holder.textMessage.setMaxLines(isViewExpanded ? 3 : Integer.MAX_VALUE);
                holder.btnKnowMore.setText(isViewExpanded ? text_read_more : text_read_less);
                notificationModel.setExpanded(!isViewExpanded);
            }
        });


        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // TODO Auto-generated method stub
                selectedFlag = true;
                deleteListener.showDeleteAction(selectedFlag);
                addSelectedItem(notificationModel);
                holder.cardView.setCardBackgroundColor(color_white_selected);
                return true;
            }
        });
    }

    private void addSelectedItem(NotificationList.NotificationModel notificationModel) {
        notificationModel.setSelected(true);

        if (!selectedList.contains(notificationModel.getTrid()))
            selectedList.add(notificationModel.getTrid());

        deleteListener.updateNotificationDeleteList(selectedList);
    }

    private void removeSelectedItem(NotificationList.NotificationModel notificationModel) {
        for (Iterator<String> it = selectedList.iterator(); it.hasNext(); ) {
            String element = it.next();
            if (element.contains(notificationModel.getTrid())) {
                it.remove();
            }
        }
        notificationModel.setSelected(false);
        deleteListener.updateNotificationDeleteList(selectedList);
    }

    private void setNotificationStatus(TextView textTitle, int pos) {
        String notificationStatus = notificationList.get(pos).getStatus();
        Netcore.openNotificationEvent(mContext, notificationModelList.get(pos).getTrid(), notificationModelList.get(pos).getDeeplink(), notificationModelList.get(pos).getCustomPayload().toString());
        if (notificationStatus.equals(text_unread)) {
            notificationStatus = text_read;
            notificationList.get(pos).setStatus(notificationStatus);
        }
        textTitle.setTypeface(null, notificationStatus.equals(text_read) ? Typeface.NORMAL : Typeface.BOLD);
    }

    private void setUpDate(TextView textDate, String strDate) {
        if (strDate != null && !strDate.trim().equals("")) {
            long longdate = Long.parseLong(strDate) * 1000;

            if (DateUtils.isToday(longdate)) {
                textDate.setText(text_today);
            } else if (DateUtils.isToday(new Date(longdate).getTime() + DateUtils.DAY_IN_MILLIS)) {
                textDate.setText(text_yesterday);
            } else {
                String date = new SimpleDateFormat(dateFormat, Locale.getDefault())
                        .format(new Date(Long.parseLong(strDate) * 1000));
                textDate.setText(date);
            }
        }

    }

    private void handleActions(final NotificationViewHolder viewHolder, final NotificationList.NotificationModel notificationModel, final int index, final int position) {
        String actionButtonText = notificationModel.getActionButton().get(index).getActionName();
        Uri actionButtonUri = Uri.parse(notificationModel.getActionButton().get(index).getActionDeeplink());
        String customPayload = notificationModel.getCustomPayload().toString();
        switch (index) {
            case 0:
                setUpActionButton(viewHolder.btnActionOne, viewHolder.textTitle, actionButtonText, actionButtonUri, position, customPayload);
                break;

            case 1:
                setUpActionButton(viewHolder.btnActionTwo, viewHolder.textTitle, actionButtonText, actionButtonUri, position, customPayload);
                break;

            case 2:
                setUpActionButton(viewHolder.btnActionThree, viewHolder.textTitle, actionButtonText, actionButtonUri, position, customPayload);
                break;
        }
    }

    private void setUpActionButton(Button btnAction, final TextView tvTitle, final String actionText,
                                   final Uri actionButtonUri, final int position, final String customPayload) {
        btnAction.setVisibility(View.VISIBLE);
        btnAction.setText(actionText);
        btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNotificationStatus(tvTitle, position);
                try {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, actionButtonUri);
                    browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    Bundle bundle = new Bundle();
                    bundle.putString("customPayload", actionButtonUri.toString());
                    bundle.putString("deeplink", customPayload);
                    browserIntent.putExtras(bundle);

                    mContext.startActivity(browserIntent);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(mContext, "Invalid Deeplink", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void updateList(List<NotificationList> notificationList) {
        notificationModelList.clear();
        for (int i = 0; i < notificationList.size(); i++) {
            try {
                JSONObject jsonObjectNotification = new JSONObject(notificationList.get(i).getMessage());
                String msgJsonString = jsonObjectNotification.getString("data");
                NotificationList.NotificationModel notificatonModel = gson.fromJson(msgJsonString, NotificationList.NotificationModel.class);
                notificationModelList.add(notificatonModel);
            } catch (Exception e) {
                Log.e(TAG, "Error: " + e.getMessage());
            }
        }
        notifyDataSetChanged();
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    @Override
    public void onCarouselItemClickListener(NotificationList.NotificationModel notificationModel, int notificationPosition, Uri deepLink) {
        if (selectedFlag) {
            if (notificationModel.isSelected()) {
                removeSelectedItem(notificationModel);
                deselectedItemPosition = notificationPosition;
                deleteListener.showDeleteAction(selectedFlag);
                notifyItemChanged(notificationPosition);
            } else {
                addSelectedItem(notificationModel);
                selectedItemPosition = notificationPosition;
                notifyItemChanged(notificationPosition);
            }

            if (selectedList.size() == 0) {
                selectedFlag = false;
                deleteListener.showDeleteAction(selectedFlag);
            }
        } else {
            try {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, deepLink);
                browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                Bundle bundle = new Bundle();
                bundle.putString("customPayload", notificationModel.getCustomPayload().toString());
                bundle.putString("deeplink", notificationModel.getDeeplink());
                browserIntent.putExtras(bundle);

                mContext.startActivity(browserIntent);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(mContext, "Invalid Deeplink", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onLongClickListen(NotificationList.NotificationModel notification, int notificationPosition) {
        if (!selectedFlag) {
            selectedFlag = true;
            deleteListener.showDeleteAction(selectedFlag);
            addSelectedItem(notification);
            selectedItemPosition = notificationPosition;
            notifyItemChanged(notificationPosition);
        }
    }

    class NotificationViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        private ImageView imgNotification;
        private TextView textTitle, textMessage, textDate, text_carousal_message, text_carousal_title;
        private Button btnActionOne, btnActionTwo, btnActionThree, btnKnowMore;
        private View dividerview, carousalDividerView;
        private ViewPager mPager;
        private CircleIndicator indicator;
        private RelativeLayout layoutCarousel;


        private NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            imgNotification = itemView.findViewById(R.id.notification_row_imageView);
            btnKnowMore = itemView.findViewById(R.id.btn_know_more);
            textTitle = itemView.findViewById(R.id.notification_row_tv_title);
            textMessage = itemView.findViewById(R.id.text_message);
            textDate = itemView.findViewById(R.id.text_date);
            btnActionOne = itemView.findViewById(R.id.btn_action_one);
            btnActionTwo = itemView.findViewById(R.id.btn_action_two);
            btnActionThree = itemView.findViewById(R.id.btn_action_three);
            dividerview = itemView.findViewById(R.id.divider_view);
            mPager = itemView.findViewById(R.id.pager);
            indicator = itemView.findViewById(R.id.indicator);
            layoutCarousel = itemView.findViewById(R.id.layoutCarousel);
            cardView = itemView.findViewById(R.id.card);
            text_carousal_message = itemView.findViewById(R.id.notification_row_carousal_message);
            text_carousal_title = itemView.findViewById(R.id.notification_row_carousal_title);
            carousalDividerView = itemView.findViewById(R.id.carosal_divider_view);
        }
    }
}
