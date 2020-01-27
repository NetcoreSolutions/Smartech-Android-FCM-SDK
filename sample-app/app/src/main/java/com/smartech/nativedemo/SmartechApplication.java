package com.smartech.nativedemo;

import android.app.NotificationManager;
import android.graphics.Color;
import android.util.Log;

import androidx.multidex.MultiDexApplication;

import com.smartech.nativedemo.Utils.Netcore;

import org.json.JSONObject;

import in.netcore.smartechfcm.NetcoreSDK;
import in.netcore.smartechfcm.notification.SMTNotificationListener;
import in.netcore.smartechfcm.pushnotification.channel.SmtNotificationChannel;

public class SmartechApplication extends MultiDexApplication implements SMTNotificationListener {

    public static final String TAG = SmartechApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();

        Netcore.register(this);
        Netcore.setSMTNotificationListener(this);
        Netcore.setPushIconColor(this, Color.RED);
        createDemoChannel();
    }

    @Override
    public void getSmartechNotifications(JSONObject data, int from) {
        Log.d(TAG, "getNotification: received");
        Netcore.renderNotification(this, data);
    }

    private void createDemoChannel() {
//        NetcoreSDK.deleteNotificationChannelGroup(this, "aa");
//        NetcoreSDK.deleteNotificationChannel(this, "coon");
        NetcoreSDK.createNotificationChannelGroup(this, "group1", "group1");
        NetcoreSDK.createNotificationChannelGroup(this, "group2", "group2");
//
        SmtNotificationChannel smtNotificationChannel = new SmtNotificationChannel.Builder(this,
                "IMPORTANCE_MIN",
                "IMPORTANCE_MIN",
                NotificationManager.IMPORTANCE_MIN)
                .setChannelDescription("IMPORTANCE_MIN des")
                .setChannelGroupId("group1")
                .build();
        NetcoreSDK.createNotificationChannel(this, smtNotificationChannel);

        smtNotificationChannel = new SmtNotificationChannel.Builder(this,
                "IMPORTANCE_LOW",
                "IMPORTANCE_LOW",
                NotificationManager.IMPORTANCE_LOW)
                .setChannelDescription("IMPORTANCE_LOW des")
                .setChannelGroupId("group1")
                .build();
        NetcoreSDK.createNotificationChannel(this, smtNotificationChannel);


        smtNotificationChannel = new SmtNotificationChannel.Builder(this,
                "IMPORTANCE_DEFAULT",
                "IMPORTANCE_DEFAULT",
                NotificationManager.IMPORTANCE_DEFAULT)
                .setChannelDescription("IMPORTANCE_DEFAULT des")
                .setNotificationSound("sound_test1")
                .setChannelGroupId("group2")
                .build();
        NetcoreSDK.createNotificationChannel(this, smtNotificationChannel);

        smtNotificationChannel = new SmtNotificationChannel.Builder(this,
                "IMPORTANCE_HIGH",
                "IMPORTANCE_HIGH",
                NotificationManager.IMPORTANCE_HIGH)
                .setChannelDescription("IMPORTANCE_HIGH des")
                .setNotificationSound("sound_and_channel_testing_android")
                .setChannelGroupId("group2")
                .build();
        NetcoreSDK.createNotificationChannel(this, smtNotificationChannel);

        smtNotificationChannel = new SmtNotificationChannel.Builder(this,
                "IMPORTANCE_MAX",
                "IMPORTANCE_MAX",
                NotificationManager.IMPORTANCE_MAX)
                .setNotificationSound("s5")
                .setChannelDescription("IMPORTANCE_MAX_DEFAULT des")
                .build();
        NetcoreSDK.createNotificationChannel(this, smtNotificationChannel);

    }

    /*private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            NotificationChannel channel = new NotificationChannel("app_channel", "app_channel", NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("test1");
            channel.setSound(CommonUtils.getSoundUri(this, "s4"), new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_NOTIFICATION).build());
            notificationManager.createNotificationChannel(channel);

            channel = new NotificationChannel("app_channel1", "app_channel1", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("test2");
            notificationManager.createNotificationChannel(channel);
        }
    }*/
}
