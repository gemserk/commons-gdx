package com.gemserk.commons.utils;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BrowserUtilsDesktopImpl implements BrowserUtils {

	protected static final Logger logger = LoggerFactory.getLogger(BrowserUtilsDesktopImpl.class);

	@Override
	public void open(URI uri) {

		if (!Desktop.isDesktopSupported()) {
			if (logger.isErrorEnabled())
				logger.error("Desktop feature is not supported.");
			return;
		}

		Desktop desktop = Desktop.getDesktop();

		if (!desktop.isSupported(Desktop.Action.BROWSE)) {
			if (logger.isErrorEnabled())
				logger.error("Desktop feature doesn't support the browse action.");
			return;
		}

		try {
			if (logger.isInfoEnabled())
				logger.info("Opening URL " + uri + " in default Browser.");
			desktop.browse(uri);
		} catch (IOException e) {
			if (logger.isErrorEnabled())
				logger.error(e.getMessage(), e);
		}

	}

	@Override
	public void open(String url) {
		try {
			open(new URI(url));
		} catch (URISyntaxException e) {
			if (logger.isErrorEnabled())
				logger.error(e.getMessage(), e);
		}
	}

}