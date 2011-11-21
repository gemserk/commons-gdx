package com.gemserk.commons.utils;

import java.text.MessageFormat;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

/**
 * Android implementation of MailUtils interface using Android API.
 */
public class MailUtilsAndroidImpl implements MailUtils {

	private final Activity activity;
	
	private final String mailToPattern = "mailto:{0}?subject={1}&body={2}";

	public MailUtilsAndroidImpl(Activity activity) {
		this.activity = activity;
	}

	@Override
	public void send(String to, String subject, String body) {
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(MessageFormat.format(mailToPattern, to, subject, body)));
		activity.startActivity(intent);
	}

}
