package com.gemserk.commons.utils;

/**
 * Contains Utilities to send a mail using the current OS.
 */
public interface MailUtils {

	/**
	 * Tries to send an email using the corresponding OS.
	 * 
	 * @param to
	 *            The destination address of the email.
	 * @param subject
	 *            The subject of the email.
	 * @param body
	 *            The default contents of the body.
	 */
	void send(String to, String subject, String body);

}
