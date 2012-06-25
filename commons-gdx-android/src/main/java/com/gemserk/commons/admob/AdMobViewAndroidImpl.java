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
	public void show(AdViewLocation adViewLocation) {
		Message msg = adMobHandler.obtainMessage();
		msg.what = AdMobHandler.SHOW_ADS;
		msg.obj = adViewLocation;
		adMobHandler.sendMessage(msg);
	}
	
	public void show(AdViewLocation adViewLocation, long delay) {
		Message msg = adMobHandler.obtainMessage();
		msg.what = AdMobHandler.SHOW_ADS;
		msg.obj = adViewLocation;
		adMobHandler.sendMessageDelayed(msg, delay);
	}

	@Override
	public void hide() {
		adMobHandler.sendEmptyMessage(AdMobHandler.HIDE_ADS);
	}

}