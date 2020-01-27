package com.smartech.nativedemo.Services;


import android.content.Context;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.smartech.nativedemo.Utils.Netcore;

import org.json.JSONObject;

import in.netcore.smartechfcm.pushnotification.Notifications;
import in.netcore.smartechfcm.utils.CommonUtils;

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


        /**
         *  If SMT_IS_NOTIFICATION_LISTENER_ENABLED value from 1 then we have to pass control to
         *  client to choose weather he wants to display notification or not
         *
         */

        boolean isListenerEnabled = readConfig(this);
        Log.d(TAG, "onMessageReceived: Listener status: " + isListenerEnabled);
        if (isListenerEnabled) {
            try {
                Log.d(TAG, "onMessageReceived: from application");
                JSONObject objData = new JSONObject(remoteMessage.getData().toString());
                Notifications.validateNotification(this, objData, 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            boolean pushFromSmartech = Netcore.handleNotification(this, remoteMessage.getData());
            Log.d(TAG, "onMessageReceived: " + pushFromSmartech);
        }

    }

    private Boolean readConfig(Context ctx) {
        boolean isEnabled = false;
        try {
            String value = CommonUtils.getManifestMetaData(ctx, "SMT_IS_NOTIFICATION_LISTENER_ENABLED");
            Log.d("App", "readConfig: " + value);
            if (value != null) {
                isEnabled = Boolean.valueOf(value);
            }
        } catch (Exception e) {
            //Resolve error for not existing meta-tag, inform the developer about adding his api key
        }
        return isEnabled;
    }
}
