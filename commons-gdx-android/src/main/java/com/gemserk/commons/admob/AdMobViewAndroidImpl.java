package com.gemserk.commons.admob;

import android.os.Message;

public class AdMobViewAndroidImpl implements AdMobView {

	// protected static final Logger logger = LoggerFactory.getLogger(AdMobViewAndroidImpl.class);

	private AdMobHandler adMobHandler;
	private boolean enabled;

	public AdMobViewAndroidImpl(AdMobHandler adMobHandler) {
		this.adMobHandler = adMobHandler;
		this.enabled = true;
	}

	@Override
	public void show() {
		show(null);
	}

	@Override
	public void show(AdsParameters adsParameters) {
		if (!isEnabled())
			return;
		clearEnqueuedMessages();
		// if (logger.isInfoEnabled())
		// logger.info("Sending show ads message to handler");
		sendMessage(AdMobHandler.SHOW_ADS, adsParameters);
	}

	@Override
	public void hide() {
		hide(null);
	}

	@Override
	public void hide(AdsParameters adsParameters) {
		if (!isEnabled())
			return;
		clearEnqueuedMessages();
		// if (logger.isInfoEnabled())
		// logger.info("Sending hide ads message to handler");
		sendMessage(AdMobHandler.HIDE_ADS, adsParameters);
	}

	private void sendMessage(int what, AdsParameters adsParameters) {
		Message msg = adMobHandler.obtainMessage();
		msg.what = what;
		msg.obj = adsParameters;
		if (adsParameters != null && adsParameters.delay > 0L) {
			System.out.println("sending message " + (what == AdMobHandler.SHOW_ADS ? "SHOW" : "HIDE") + " with delay of " + adsParameters.delay + "ms");
			adMobHandler.sendMessageDelayed(msg, adsParameters.delay);
		} else {
			System.out.println("sending message " + (what == AdMobHandler.SHOW_ADS ? "SHOW" : "HIDE") + " without delay");
			adMobHandler.sendMessage(msg);
		}
	}

	private void clearEnqueuedMessages() {
		// if (logger.isDebugEnabled())
		// logger.debug("removing enqueued messages");
		System.out.println("clearing enqueued messages");
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