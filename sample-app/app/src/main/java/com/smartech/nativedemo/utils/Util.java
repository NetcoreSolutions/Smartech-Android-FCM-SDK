package com.smartech.nativedemo.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.smartech.nativedemo.R;

public class Util {
    public static final String NC_DEEP_LINK = "deeplink";
    public static final String NC_CUSTOM_PAYLOAD = "customPayload";

    public static void showAlert(final Context context, final int flag, String title, String message) {

        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (flag) {
                            case 1:
                                Netcore.optOut(context, false);
                                break;
                            case 2:
                                Netcore.optOut(context, true);
                        }
                    }
                })
                .setNegativeButton("No", null)
                .setIcon(R.mipmap.ic_launcher_round)
                .show();
    }

    public static void showAlertWithMessage(final Activity context, String title, String message) {

        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Ok", null)
                .setIcon(R.mipmap.ic_launcher_round)
                .show();
    }
}
