package com.smartech.nativedemo.Services;


import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.smartech.nativedemo.Utils.Netcore;

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

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
        boolean pushFromSmartech = Netcore.handleNotification(this, remoteMessage.getData());
        Log.d(TAG, "onMessageReceived: "+pushFromSmartech);
        if(!pushFromSmartech){
            // Intentionally blank
        }
    }
}
