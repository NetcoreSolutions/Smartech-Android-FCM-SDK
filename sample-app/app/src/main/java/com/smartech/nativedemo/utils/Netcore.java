package com.smartech.nativedemo.utils;

import android.app.Application;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import in.netcore.smartechfcm.NetcoreSDK;
import in.netcore.smartechfcm.inapp.InAppCustomHTMLListener;
import in.netcore.smartechfcm.notification.SMTNotificationListener;

public class Netcore {

    public static final String APPID = "903c8fa4dcf982b0626b48a1a932fb1a";


    public static void register(Application context) {
        NetcoreSDK.register(context, SharedPreferencesManager.getInstance(context).getSmartechId());
    }

    public static void login(Context context, String identity) {
        NetcoreSDK.setIdentity(context, identity);
        NetcoreSDK.login(context);
    }

    public static void logout(Context context) {
        NetcoreSDK.logout(context);
        NetcoreSDK.clearIdentity(context);
    }

    public static void track(Context context, String eventName, String payload) {
        NetcoreSDK.track(context, eventName, payload);
    }

    public static void track(Context context, int eventID, String payload) {
        NetcoreSDK.track(context, eventID, payload);
    }

    public static void profile(Context context, JSONObject payload) {
        NetcoreSDK.profile(context, payload);
    }

    public static String getGUID(Context context) {
        return NetcoreSDK.getGUID(context);
    }

    public static void deleteNotification(Context context, List<String> notificationList) {
        NetcoreSDK.deleteNotification(context, notificationList);
    }

    public static JSONArray getNotifications(Context context, int notificationCount) {
        return NetcoreSDK.getNotifications(context, notificationCount);
    }

    public static boolean handleNotification(Context context, Map<String, String> map) {
        return NetcoreSDK.handleNotification(context, map);
    }

    public static void renderNotification(Context context, JSONObject obj) {
        NetcoreSDK.renderNotification(context, obj);
    }

    public static int getUnreadNotificationsCount(Context context) {
        return NetcoreSDK.getUnreadNotificationsCount(context);
    }

    public static void openNotificationEvent(Context context, JSONObject pnMeta, String trID, String deeplink, String customPayload) {
        NetcoreSDK.openNotificationEvent(context, pnMeta, trID, deeplink, customPayload);
    }

    public static void optOut(Context context, boolean optOut) {
        NetcoreSDK.optOut(context, optOut);
    }

    public static void setUserLocation(Context context, Double latitude, Double longitude) {
        NetcoreSDK.setUserLocation(context, latitude, longitude);
    }

    public static void setIdentity(Context context, String identity) {
        NetcoreSDK.setIdentity(context, identity);
    }

    public static void clearIdentity(Context context) {
        NetcoreSDK.clearIdentity(context);
    }

    public static void setPushToken(Context context, String token) {
        NetcoreSDK.setPushToken(context, token);
    }

    public static String getPushToken(Context context) {
        return NetcoreSDK.getPushToken(context);
    }

    public static void setPushIconColor(Context context, int argb) {
        NetcoreSDK.setPushIconColor(context, argb);
    }

    public static void resetPushIconColor(Context context) {
        NetcoreSDK.resetPushIconColor(context);
    }

    public static SMTNotificationListener getSMTNotificationListener() {
        return NetcoreSDK.getSMTNotificationListener();
    }

    public static void setSMTNotificationListener(SMTNotificationListener listener) {
        NetcoreSDK.setSMTNotificationListener(listener);
    }

    public static void setInAppCustomHTMLListener(InAppCustomHTMLListener listener) {
        NetcoreSDK.setInAppCustomHTMLListener(listener);
    }

    public static void setDebugLevel(Context context, int level) {
        NetcoreSDK.setDebugLevel(context, level);
    }
}
