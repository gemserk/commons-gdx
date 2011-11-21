package com.gemserk.commons.utils;

import java.awt.Desktop;
import java.net.URI;
import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Java Desktop implementation of MailUtils interface.
 */
public class MailUtilsDesktopImpl implements MailUtils {

	protected static final Logger logger = LoggerFactory.getLogger(MailUtilsDesktopImpl.class);

	private Desktop desktop;

	private String scheme = "mailto";
	private String pattern = "{0}?subject={1}&body={2}";

	public MailUtilsDesktopImpl() {
		desktop = Desktop.getDesktop();
	}

	@Override
	public void send(String to, String subject, String body) {
		if (!desktop.isSupported(Desktop.Action.MAIL)) {
			logger.error("Desktop.Action.MAIL not supported");
			throw new RuntimeException("Desktop.Action.MAIL not supported");
		}

		try {
			URI uri = new URI(scheme, MessageFormat.format(pattern, to, subject, body), null);
			logger.debug(uri.toString());
			desktop.mail(uri);
		} catch (Exception e) {
			logger.error("Failed to send email to " + to, e.getMessage());
			throw new RuntimeException(e);
		}
	}

}
