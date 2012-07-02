package com.gemserk.commons.admob;

import android.os.Message;

public class AdMobViewAndroidImpl implements AdMobView {

	private AdMobHandler adMobHandler;

	public AdMobViewAndroidImpl(AdMobHandler adMobHandler) {
		this.adMobHandler = adMobHandler;
	}

	@Override
	public void show() {
		adMobHandler.sendEmptyMessage(AdMobHandler.SHOW_ADS);
	}

	@Override
	public void show(AdsParameters adsParameters) {
		Message msg = adMobHandler.obtainMessage();
		msg.what = AdMobHandler.SHOW_ADS;
		msg.obj = adsParameters;
		
		clearEnqueuedMessages();

		if (adsParameters.delay > 0L)
			adMobHandler.sendMessageDelayed(msg, adsParameters.delay);
		else
			adMobHandler.sendMessage(msg);
	}

	@Override
	public void hide() {
		clearEnqueuedMessages();
		adMobHandler.sendEmptyMessage(AdMobHandler.HIDE_ADS);
	}

	private void clearEnqueuedMessages() {
		adMobHandler.removeMessages(AdMobHandler.SHOW_ADS);
		adMobHandler.removeMessages(AdMobHandler.HIDE_ADS);
	}
	
}