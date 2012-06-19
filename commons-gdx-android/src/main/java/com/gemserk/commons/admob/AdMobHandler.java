package com.gemserk.commons.admob;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.ads.AdView;

public class AdMobHandler extends Handler {

	public static final int SHOW_ADS = 1;
	public static final int HIDE_ADS = 0;

	private final AdView adView;
	private final RelativeLayout layout;

	public AdMobHandler(AdView adView, RelativeLayout layout) {
		this.adView = adView;
		this.layout = layout;
	}

	@Override
	public void handleMessage(Message msg) {
		switch (msg.what) {
		case SHOW_ADS: {
			AdViewLocation adViewLocation = (AdViewLocation) msg.obj;

			if (adViewLocation != null) {
				RelativeLayout.LayoutParams adParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

				adParams.addRule(getVerticalAlign(adViewLocation));
				adParams.addRule(getHorizontalAlign(adViewLocation));

				layout.removeView(adView);
				layout.addView(adView, adParams);
			}

			adView.setVisibility(View.VISIBLE);
			break;
		}
		case HIDE_ADS: {
			adView.setVisibility(View.GONE);
			break;
		}
		}
	}

	private int getVerticalAlign(AdViewLocation adViewLocation) {
		if (adViewLocation.verticalAlign == AdViewLocation.VERTICAL_TOP)
			return RelativeLayout.ALIGN_PARENT_TOP;
		if (adViewLocation.verticalAlign == AdViewLocation.VERTICAL_BOTTOM)
			return RelativeLayout.ALIGN_PARENT_BOTTOM;
		return RelativeLayout.CENTER_VERTICAL;
	}

	private int getHorizontalAlign(AdViewLocation adViewLocation) {
		if (adViewLocation.horizontalAlign == AdViewLocation.HORIZONTAL_LEFT)
			return RelativeLayout.ALIGN_PARENT_LEFT;
		if (adViewLocation.horizontalAlign == AdViewLocation.HORIZONTAL_RIGHT)
			return RelativeLayout.ALIGN_PARENT_RIGHT;
		return RelativeLayout.CENTER_HORIZONTAL;
	}

}