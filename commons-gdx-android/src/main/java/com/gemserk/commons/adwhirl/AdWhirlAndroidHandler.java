package com.gemserk.commons.adwhirl;

import android.os.Handler;
import android.os.Message;

public class AdWhirlAndroidHandler extends Handler {
	
	public static final int SHOW_ADS = 1;

	public static final int HIDE_ADS = 0;

	private final PausableAdWhirlLayout adView;
	
	public AdWhirlAndroidHandler(PausableAdWhirlLayout adView) {
		this.adView = adView;
	}
	
	@Override
	public void handleMessage(Message msg) {
		switch (msg.what) {
		case SHOW_ADS: {
			adView.onResume();
			break;
		}
		case HIDE_ADS: {
			adView.onPause();
			break;
		}
		}
	}
}