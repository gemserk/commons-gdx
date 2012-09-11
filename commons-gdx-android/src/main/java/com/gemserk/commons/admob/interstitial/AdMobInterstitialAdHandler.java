package com.gemserk.commons.admob.interstitial;

import com.google.ads.AdRequest;
import com.google.ads.InterstitialAd;

import android.os.Handler;
import android.os.Message;

public class AdMobInterstitialAdHandler extends Handler {

	public static final int SHOW_ADS = 2;
	public static final int LOAD_ADS = 1;

	private AdRequest adRequest;
	private InterstitialAd interstitialAd;
	
	public void setAdRequest(AdRequest adRequest) {
		this.adRequest = adRequest;
	}
	
	public void setInterstitialAd(InterstitialAd interstitialAd) {
		this.interstitialAd = interstitialAd;
	}
	
	@Override
	public void handleMessage(Message msg) {
		switch (msg.what) {
		case SHOW_ADS: {
			interstitialAd.show();
			break;
		}
		case LOAD_ADS: {
			interstitialAd.loadAd(adRequest);
			break;
		}
		}
	}

}
