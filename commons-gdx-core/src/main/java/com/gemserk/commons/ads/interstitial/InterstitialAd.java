package com.gemserk.commons.ads.interstitial;

/**
 * Generic interface to use to show interstitial ads.
 */
public interface InterstitialAd {

	/**
	 * Shows the current ad if it is ready.
	 */
	void show();

	/**
	 * Loads a new ad by making a new request.
	 */
	void load();

	/**
	 * Returns true if the ad is ready to be shown.
	 */
	boolean isReady();

}