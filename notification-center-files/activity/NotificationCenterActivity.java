package com.smartech.nativedemo.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.smartech.nativedemo.Adapter.NotificationAdapter;
import com.smartech.nativedemo.MainActivity;
import com.smartech.nativedemo.R;
import com.smartech.nativedemo.Utils.Netcore;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import in.netcore.smartechfcm.notification.DeleteEventsListener;
import in.netcore.smartechfcm.notification.NotificationList;

public class NotificationCenterActivity extends AppCompatActivity implements DeleteEventsListener, View.OnClickListener {
    private static final String notification_title = "Delete Notification";
    private List<String> listDeleteNotifications;
    private Gson gson;
    private RecyclerView notificationRecycler;
    private Toolbar activityNotificationToolbar;
    private ImageView actvityNotificationDeleteIcon, emptyNotificationPlaceholder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_center);

        notificationRecycler = findViewById(R.id.notification_recycler);
        activityNotificationToolbar = findViewById(R.id.activity_notification_toolbar);
        actvityNotificationDeleteIcon = findViewById(R.id.actvity_notification_delete_icon);
        emptyNotificationPlaceholder = findViewById(R.id.activity_notification_ceneter_placeholder);
        gson = new Gson();
        listDeleteNotifications = new ArrayList<>();

        handleNotificationData();
        activityNotificationToolbar.setTitle("Notification Manager");
        activityNotificationToolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        activityNotificationToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);
        activityNotificationToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        setSupportActionBar(activityNotificationToolbar);
     /*   getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);*/
        actvityNotificationDeleteIcon.setOnClickListener(this);
    }

    //for delete notifications
    private void showDeleteNotificationAlert() {
        String stringNotificationCount = (listDeleteNotifications.size() == 1) ? " notification?" : " notifications?";
        new AlertDialog.Builder(this)
                .setTitle(notification_title)
                .setMessage("Are you sure you want to delete " + listDeleteNotifications.size() + stringNotificationCount)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Netcore.deleteNotification(getApplicationContext(), listDeleteNotifications);
                        handleNotificationData();
                        actvityNotificationDeleteIcon.setVisibility(View.GONE);
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .show();
    }

    //handle notification data
    private void handleNotificationData() {
        try {
            Type listType = new TypeToken<List<NotificationList>>() {
            }.getType();
            List<NotificationList> notificationList = gson.fromJson(Netcore.getNotifications(getApplicationContext(), 100).toString(), listType);
            if (notificationList.size() <= 0) {
                //notificationList.clear();
                notificationRecycler.setVisibility(View.GONE);
                emptyNotificationPlaceholder.setVisibility(View.VISIBLE);
            } else {
                notificationRecycler.setVisibility(View.VISIBLE);
                emptyNotificationPlaceholder.setVisibility(View.GONE);
                setUpView(notificationList);
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
    }

    private void setUpView(List<NotificationList> notificationModelList) {
        if (actvityNotificationDeleteIcon.getVisibility() == View.VISIBLE) {
            actvityNotificationDeleteIcon.setVisibility(View.GONE);
        }
        NotificationAdapter mAdapter = new NotificationAdapter(getApplicationContext(), notificationModelList, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        notificationRecycler.setLayoutManager(mLayoutManager);
        notificationRecycler.setAdapter(mAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        handleNotificationData();
    }

    @Override
    public boolean onSupportNavigateUp() {
        startActivity(new Intent(NotificationCenterActivity.this, MainActivity.class));
        finish();
        return true;
    }

    @Override
    public void showDeleteAction(boolean isIconInvisible) {
        actvityNotificationDeleteIcon.setVisibility(isIconInvisible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void updateNotificationDeleteList(List<String> listNotification) {
        listDeleteNotifications = listNotification;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.actvity_notification_delete_icon:
                showDeleteNotificationAlert();
                break;
        }
    }
}
