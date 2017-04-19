package com.orange_team.narinjapp.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;

import com.orange_team.narinjapp.R;

/**
 * Created by User on 17.04.2017.
 */

public class OrderDialogManager {
    public void showAlertDialog(Context context, String title, String message) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(context).setTitle(title).setMessage(message);

        final AlertDialog alert = dialog.create();
        alert.show();

        final Handler handler  = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (alert.isShowing()) {
                    alert.dismiss();
                }
            }
        };

        alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                handler.removeCallbacks(runnable);
            }
        });

        handler.postDelayed(runnable, 5000);
    }

}
