package com.smartech.nativedemo;

import android.app.Application;
import android.app.NotificationManager;
import android.graphics.Color;
import android.util.Log;

import com.smartech.nativedemo.utils.Netcore;

import org.json.JSONObject;

import java.util.HashMap;

import in.netcore.smartechfcm.NetcoreSDK;
import in.netcore.smartechfcm.inapp.InAppCustomHTMLListener;
import in.netcore.smartechfcm.logger.NCLogger;
import in.netcore.smartechfcm.notification.SMTNotificationListener;
import in.netcore.smartechfcm.pushnotification.channel.SMTNotificationChannel;

public class SmartechApplication extends Application implements SMTNotificationListener, InAppCustomHTMLListener {

    public static final String TAG = SmartechApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();

        Netcore.setDebugLevel(this, NCLogger.Level.LOG_LEVEL_VERBOSE);
        Netcore.register(this);
        Netcore.setSMTNotificationListener(this);
        Netcore.setInAppCustomHTMLListener(this);
        Netcore.setPushIconColor(this, Color.RED);
        createDemoChannel();
    }

    @Override
    public void getSmartechNotifications(JSONObject data, int from) {
        Log.d(TAG, "getNotification: received");
        Netcore.renderNotification(this, data);
    }

    private void createDemoChannel() {

        NetcoreSDK.createNotificationChannelGroup(this, "group1", "group1");
        NetcoreSDK.createNotificationChannelGroup(this, "group2", "group2");
//
        SMTNotificationChannel smtNotificationChannel = new SMTNotificationChannel.Builder(
                "IMPORTANCE_MIN",
                "IMPORTANCE_MIN",
                NotificationManager.IMPORTANCE_MIN)
                .setChannelDescription("IMPORTANCE_MIN des")
                .setChannelGroupId("group1")
                .build();
        NetcoreSDK.createNotificationChannel(this, smtNotificationChannel);

        smtNotificationChannel = new SMTNotificationChannel.Builder(
                "IMPORTANCE_LOW",
                "IMPORTANCE_LOW",
                NotificationManager.IMPORTANCE_LOW)
                .setChannelDescription("IMPORTANCE_LOW des")
                .setChannelGroupId("group1")
                .build();
        NetcoreSDK.createNotificationChannel(this, smtNotificationChannel);


        smtNotificationChannel = new SMTNotificationChannel.Builder(
                "IMPORTANCE_DEFAULT",
                "IMPORTANCE_DEFAULT",
                NotificationManager.IMPORTANCE_DEFAULT)
                .setChannelDescription("IMPORTANCE_DEFAULT des")
                .setNotificationSound("sound_test1")
                .setChannelGroupId("group2")
                .build();
        NetcoreSDK.createNotificationChannel(this, smtNotificationChannel);

        smtNotificationChannel = new SMTNotificationChannel.Builder(
                "IMPORTANCE_HIGH",
                "IMPORTANCE_HIGH",
                NotificationManager.IMPORTANCE_HIGH)
                .setChannelDescription("IMPORTANCE_HIGH des")
                .setNotificationSound("sound_and_channel_testing_android")
                .setChannelGroupId("group2")
                .build();
        NetcoreSDK.createNotificationChannel(this, smtNotificationChannel);

        smtNotificationChannel = new SMTNotificationChannel.Builder(
                "IMPORTANCE_MAX",
                "IMPORTANCE_MAX",
                NotificationManager.IMPORTANCE_MAX)
                .setNotificationSound("s5")
                .setChannelDescription("IMPORTANCE_MAX_DEFAULT des")
                .build();
        NetcoreSDK.createNotificationChannel(this, smtNotificationChannel);

    }

    @Override
    public void customHTMLCallback(HashMap<String, Object> hashMap) {
        Log.d(TAG, "customHTMLCallback: " + hashMap);
    }
}
