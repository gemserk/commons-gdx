package com.gemserk.commons.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileHelper {

	/**
	 * Returns the contents of a file in the class path as a String.
	 */
	public static String read(InputStream in) {
		StringBuilder stringBuilder = new StringBuilder();

		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			char[] buffer = new char[8192];

			int read;
			while ((read = bufferedReader.read(buffer, 0, buffer.length)) > 0) {
				stringBuilder.append(buffer, 0, read);
			}
			return stringBuilder.toString();
		} catch (Exception e) {
			throw new RuntimeException("failed to read file contents as string", e);
		} finally {
			try {
				in.close();
			} catch (Exception e) {

			}
		}
	}

}