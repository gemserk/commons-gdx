package com.gemserk.commons.admob;

public interface AdMobView {
	
	void show();
	
	void show(AdsParameters adsParameters);
	
	void hide();
	
	boolean isEnabled();
	
	void setEnabled(boolean enabled);
	
}