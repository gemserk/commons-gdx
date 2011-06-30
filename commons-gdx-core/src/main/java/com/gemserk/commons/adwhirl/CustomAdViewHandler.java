package com.gemserk.commons.adwhirl;

import android.os.Handler;

public class CustomAdViewHandler extends AdWhirlViewHandler {
	
	private final Handler handler;

	public CustomAdViewHandler(Handler handler) {
		this.handler = handler;
	}
	
	@Override
	public void show() {
		handler.sendEmptyMessage(AdWhirlAndroidHandler.SHOW_ADS);
	}

	@Override
	public void hide() {
		handler.sendEmptyMessage(AdWhirlAndroidHandler.HIDE_ADS);
	}
}