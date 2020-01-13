package com.smartech.nativedemo.notificationcenter.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import com.smartech.nativedemo.R;
import com.smartech.nativedemo.sdks.SDKHandler;

import in.netcore.smartechfcm.notification.ClickInterface;
import in.netcore.smartechfcm.notification.NotificationList;

public class CarouselAdapter extends PagerAdapter implements View.OnClickListener {
    private NotificationList.NotificationModel notification;
    private LayoutInflater inflater;
    private Context context;
    private NotificationList notificationList;
    private int notificationPosition;
    private ClickInterface clickInterface;

    CarouselAdapter(Context context, NotificationList.NotificationModel notification, NotificationList notificationList, int notificationPosition, ClickInterface clickInterface) {
        this.context = context;
        this.notification = notification;
        this.notificationList = notificationList;
        inflater = LayoutInflater.from(context);
        this.clickInterface = clickInterface;
        this.notificationPosition=notificationPosition;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return notification.getCarousel().size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup view, final int position) {
        View myImageLayout = inflater.inflate(R.layout.carousel_item, view, false);

        myImageLayout.setOnClickListener(this);
        myImageLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                clickInterface.onCarouselItemClickListener(notification,notificationPosition,Uri.parse(notification.getCarousel().get(position).getImgDeeplink()));
                if (!notification.getCarousel().get(position).getImgDeeplink().isEmpty() &&
                        notification.getCarousel().get(position).getImgDeeplink() != null) {
                    //if (notificationList.getStatus().equals("unread")) {
                        SDKHandler.markNotificationAsRead(context, notification.getTrid(), notification.getDeeplink());
                    //}
                }
            }
        });

        myImageLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                clickInterface.onLongClickListen(notification,notificationPosition);
                return false;
            }
        });

        ImageView myImage = myImageLayout.findViewById(R.id.imgCarousel);
        Glide.with(context).load(notification.getCarousel().get(position).getImgUrl()).placeholder(R.drawable.placeholder_carousal).into(myImage);
        view.addView(myImageLayout, 0);
        return myImageLayout;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @Override
    public void onClick(View v) {

    }
}
