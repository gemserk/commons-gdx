package com.gemserk.commons.admob;

import android.os.Message;

public class AdMobViewAndroidImpl implements AdMobView {

	private AdMobHandler adMobHandler;
	private boolean enabled;

	public AdMobViewAndroidImpl(AdMobHandler adMobHandler) {
		this.adMobHandler = adMobHandler;
		this.enabled = true;
	}

	@Override
	public void show() {
		if (!isEnabled())
			return;
		adMobHandler.sendEmptyMessage(AdMobHandler.SHOW_ADS);
	}

	@Override
	public void show(AdsParameters adsParameters) {
		if (!isEnabled())
			return;
		Message msg = adMobHandler.obtainMessage();
		msg.what = AdMobHandler.SHOW_ADS;
		msg.obj = adsParameters;

		clearEnqueuedMessages();

		sendMessage(msg, adsParameters);
	}

	@Override
	public void hide() {
		if (!isEnabled())
			return;
		clearEnqueuedMessages();
		adMobHandler.sendEmptyMessage(AdMobHandler.HIDE_ADS);
	}
	
	@Override
	public void hide(AdsParameters adsParameters) {
		if (!isEnabled())
			return;
		
		Message msg = adMobHandler.obtainMessage();
		msg.what = AdMobHandler.HIDE_ADS;
		msg.obj = adsParameters;
		
		clearEnqueuedMessages();
		
		sendMessage(msg, adsParameters);
	}

	private void sendMessage(Message msg, AdsParameters adsParameters) {
		if (adsParameters.delay > 0L)
			adMobHandler.sendMessageDelayed(msg, adsParameters.delay);
		else
			adMobHandler.sendMessage(msg);
	}

	private void clearEnqueuedMessages() {
		adMobHandler.removeMessages(AdMobHandler.SHOW_ADS);
		adMobHandler.removeMessages(AdMobHandler.HIDE_ADS);
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public void setEnabled(boolean enabled) {
		if (!enabled)
			hide();
		this.enabled = enabled;
	}

}