package com.gemserk.commons.utils;

import java.text.MessageFormat;

public class FacebookUtilsDesktopImpl implements FacebookUtils {
	
	private final String urlPattern = "https://www.facebook.com/{0}";
	private final BrowserUtils browserUtils;
	
	public FacebookUtilsDesktopImpl(BrowserUtils browserUtils) {
		this.browserUtils = browserUtils;
	}

	@Override
	public void openPage(String page) {
		browserUtils.open(MessageFormat.format(urlPattern, page));
	}

}
