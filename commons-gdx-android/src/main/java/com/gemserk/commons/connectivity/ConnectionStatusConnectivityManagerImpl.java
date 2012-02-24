package com.gemserk.commons.connectivity;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectionStatusConnectivityManagerImpl implements ConnectionStatus {

	private final Activity activity;

	public ConnectionStatusConnectivityManagerImpl(Activity activity) {
		this.activity = activity;
	}

	@Override
	public boolean isReachable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		return activeNetworkInfo != null;
	}

}
