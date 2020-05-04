package com.smartech.nativedemo.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {

    public static final String STATUS_LOGIN = "login";
    public static final String SMARTECH_ID = "smartech_id";
    private static SharedPreferencesManager sharePref = new SharedPreferencesManager();
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    public static SharedPreferencesManager getInstance(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(context.getPackageName(), Activity.MODE_PRIVATE);
            editor = sharedPreferences.edit();
        }
        return sharePref;
    }


    public String getLoginValue() {
        return sharedPreferences.getString(STATUS_LOGIN, null);
    }

    public void setLoginValue(String newValue) {
        editor.putString(STATUS_LOGIN, newValue);
        editor.apply();
    }

    public String getSmartechId() {
        return sharedPreferences.getString(SMARTECH_ID, Netcore.APPID);
    }

    public void setSmartechId(String appid) {
        editor.putString(SMARTECH_ID, appid);
        editor.apply();
    }

    public void clearLogin() {
        editor.clear();
        editor.apply();
    }

}