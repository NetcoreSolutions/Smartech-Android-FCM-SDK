package com.smartech.nativedemo.services;


import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.smartech.nativedemo.utils.Netcore;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    String TAG = "MessagingService";

    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);
        Netcore.setPushToken(this, token);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        boolean pushFromSmartech = Netcore.handleNotification(this, remoteMessage.getData());
        Log.d(TAG, "onMessageReceived: " + pushFromSmartech);
    }
}
