package com.gemserk.commons.admob;

public interface AdMobView {
	
	void show();

	void show(AdViewLocation adViewLocation);
	
	void show(AdViewLocation adViewLocation, long delay);

	void hide();
	
}