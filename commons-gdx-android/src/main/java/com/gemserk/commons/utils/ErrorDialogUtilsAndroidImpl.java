package com.gemserk.commons.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class ErrorDialogUtilsAndroidImpl implements ErrorDialogUtils {

	private final Activity activity;
	private final int messageTextId;
	private final int okTextId;

	public ErrorDialogUtilsAndroidImpl(Activity activity, int messageTextId, int okTextId) {
		this.activity = activity;
		this.messageTextId = messageTextId;
		this.okTextId = okTextId;
	}

	@Override
	public void showOutOfMemoryError() {
		// "You are probably running low on memory, close some applications and try to run the game again."
		// OK
		activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				AlertDialog.Builder builder = new AlertDialog.Builder(activity);
				builder.setMessage(messageTextId) //
						.setCancelable(false) //
						.setPositiveButton(okTextId, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// Gdx.app.exit();
								// System.exit(0);
								android.os.Process.killProcess(android.os.Process.myPid());
							}
						});
				AlertDialog alert = builder.create();
				alert.show();
			}
		});
	}

}
