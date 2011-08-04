package com.gemserk.commons.utils;

import java.net.URI;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

public class BrowserUtilsAndroidImpl implements BrowserUtils {
	
	private final Activity activity;

	public BrowserUtilsAndroidImpl(Activity androidApplication) {
		this.activity = androidApplication;
	}
	
	@Override
	public void open(URI uri) {
		open(uri.toString());
	}

	@Override
	public void open(String url) {
		Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
		activity.startActivity(i);
	}
}