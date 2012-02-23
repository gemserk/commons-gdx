package com.gemserk.commons.versions;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RemoteVersionProviderTextPlainFileImpl implements RemoteVersionProvider {
	
	protected static final Logger logger = LoggerFactory.getLogger(RemoteVersionProviderTextPlainFileImpl.class);

	URI versionUri;
	String latestVersionKey = "latest.txt";

	public RemoteVersionProviderTextPlainFileImpl(String versionUrl) {
		try {
			versionUri = new URI(versionUrl);
		} catch (URISyntaxException e) {
			throw new RuntimeException("Can't create VersionProvider using url: " + versionUrl, e);
		}
	}

	@Override
	public ApplicationVersion getLatestVersion(String currentVersionNumber) {
		try {
			HttpClient httpClient = new DefaultHttpClient();
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();

			params.add(new BasicNameValuePair("currentVersion", currentVersionNumber));

			String encodedParams = URLEncodedUtils.format(params, "UTF-8");

			URI uri = URIUtils.resolve(versionUri, latestVersionKey + "?" + encodedParams);

			HttpGet httpget = new HttpGet(uri);

			if (logger.isDebugEnabled())
				logger.debug("Fetching latest version");

			HttpResponse response = httpClient.execute(httpget);

			StatusLine statusLine = response.getStatusLine();

			if (statusLine.getStatusCode() != HttpStatus.SC_OK)
				throw new RuntimeException("Failed to retrieve version: " + statusLine.toString());

			String contents = EntityUtils.toString(response.getEntity());

			if (logger.isDebugEnabled())
				logger.debug("Version text plain file contents: " + contents);

			String[] contentsLines = contents.split("[\n]+");

			if (contentsLines.length == 1) {
				String versionNumber = contentsLines[0];
				return new ApplicationVersionImpl(versionNumber);
			} else if (contentsLines.length > 1) {
				String versionNumber = contentsLines[0];
				String[] changeLog = new String[contentsLines.length - 1];
				System.arraycopy(contentsLines, 1, changeLog, 0, contentsLines.length - 1);
				return new ApplicationVersionImpl(versionNumber, changeLog);
			}

			throw new RuntimeException("Version text was empty, failed to build version");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public ApplicationVersion getLatestVersion() {
		return getLatestVersion("undefined");
	}

}