package com.gemserk.commons.admob;

import android.os.Message;

public class AdMobViewAndroidImpl extends AdMobView {

	private AdMobHandler adMobHandler;

	public AdMobViewAndroidImpl(AdMobHandler adMobHandler) {
		this.adMobHandler = adMobHandler;
	}

	@Override
	public void show() {
		super.show();
		adMobHandler.sendEmptyMessage(AdMobHandler.SHOW_ADS);
	}
	
	@Override
	public void show(AdViewLocation adViewLocation) {
		super.show(adViewLocation);
		Message msg = adMobHandler.obtainMessage();
		msg.what = AdMobHandler.SHOW_ADS;
		msg.obj = adViewLocation;
		adMobHandler.sendMessage(msg);
	}

	@Override
	public void hide() {
		super.hide();
		adMobHandler.sendEmptyMessage(AdMobHandler.HIDE_ADS);
	}

}