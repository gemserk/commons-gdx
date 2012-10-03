package com.gemserk.commons.utils;

import com.badlogic.gdx.Gdx;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class ErrorDialogUtilsAndroidImpl implements ErrorDialogUtils {

	private final Activity activity;

	public ErrorDialogUtilsAndroidImpl(Activity activity) {
		this.activity = activity;
	}

	@Override
	public void showOutOfMemoryError() {
		activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				AlertDialog.Builder builder = new AlertDialog.Builder(activity);
				builder.setMessage("You are probably running low on memory, close some applications and try to run the game again.") //
						.setCancelable(false) //
						.setPositiveButton("OK", new DialogInterface.OnClickListener() {
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
