package com.gemserk.commons.utils;

import java.text.MessageFormat;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;

/**
 * Android implementation of FacebookUtils based on the post <a href="http://www.ziggysgames.com/launching-a-facebook-link-with-the-facebook-app#more-471">Launching a Facebook link with the Facebook app</a> from <a href="http://www.ziggysgames.com">Ziggy's games</a> blog.
 * 
 */
public class FacebookUtilsAndroidImpl implements FacebookUtils {

	private final Activity activity;

	private final String localUrlPagePattern = "fb://page/{0}";
	private final String externalUrlPagePattern = "https://www.facebook.com/{0}";

	public FacebookUtilsAndroidImpl(Activity activity) {
		this.activity = activity;
	}

	@Override
	public void openPage(String page) {
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(MessageFormat.format(localUrlPagePattern, page)));

		// check if there is an application able to handle the specified fb:// scheme.
		final PackageManager packageManager = activity.getPackageManager();
		List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);

		// there is no handler, then create an HTTP url to www.facebook.com.
		if (list.isEmpty())
			intent.setData(Uri.parse(MessageFormat.format(externalUrlPagePattern, page)));

		activity.startActivity(intent);
	}

}
