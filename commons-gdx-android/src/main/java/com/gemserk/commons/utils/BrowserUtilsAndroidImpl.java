package com.gemserk.commons.utils;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

public class BrowserUtilsAndroidImpl implements BrowserUtils {
	
	protected static final Logger logger = LoggerFactory.getLogger(BrowserUtilsAndroidImpl.class);
	
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
		if (logger.isDebugEnabled()) 
			logger.debug("Starting intent to open URL " + url);
	}
}