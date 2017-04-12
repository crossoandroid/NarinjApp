package com.orange_team.narinjapp.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.orange_team.narinjapp.R;

public class NetworkDialogManager {
	public void showAlertDialog(Context context, String title, String message, Boolean status) {
		AlertDialog alertDialog = new AlertDialog.Builder(context).create();

		alertDialog.setTitle(title);

		alertDialog.setMessage(message);

		if (status != null)
			alertDialog.setIcon((status) ? R.mipmap.ic_launcher : R.mipmap.ic_launcher);

		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}

			private void finish() {

			}
		});

		alertDialog.show();
	}
}
