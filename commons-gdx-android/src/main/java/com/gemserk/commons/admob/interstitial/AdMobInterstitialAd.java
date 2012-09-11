package com.gemserk.commons.admob.interstitial;

import android.os.Handler;

import com.gemserk.commons.ads.interstitial.InterstitialAd;

public class AdMobInterstitialAd implements InterstitialAd {

	private com.google.ads.InterstitialAd interstitialAd;
	private Handler handler;

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public void setInterstitialAd(com.google.ads.InterstitialAd interstitialAd) {
		this.interstitialAd = interstitialAd;
	}

	@Override
	public void show() {
		// Message msg = handler.obtainMessage(AdMobInterstitialAdHandler.SHOW_ADS);
		// handler.removeMessages(AdMobInterstitialAdHandler.SHOW_ADS);
		handler.sendEmptyMessage(AdMobInterstitialAdHandler.SHOW_ADS);
		// interstitialAd.show();
	}

	@Override
	public void load() {
		// interstitialAd.loadAd(adRequest);
		// handler.removeMessages(AdMobInterstitialAdHandler.LOAD_ADS);
		handler.sendEmptyMessage(AdMobInterstitialAdHandler.LOAD_ADS);
	}

	@Override
	public boolean isReady() {
		return interstitialAd.isReady();
	}

}