package com.gemserk.commons.utils;

import java.net.URI;

/**
 * Common utilities to open URL for the different backends.
 */
public interface BrowserUtils {
	
	/**
	 * Opens the specified URL in the current platform browser.
	 */
	void open(String url);
	
	/**
	 * Opens the specified URL in the current platform browser.
	 */
	void open(URI uri);

}
