package com.gemserk.commons.applications.decorators;

import com.gemserk.commons.admob.AdMobView;
import com.gemserk.commons.admob.AdsParameters;

public class AdMobViewSimulatorImpl implements AdMobView {

	private AdsParameters adsParameters = new AdsParameters().verticalAlign(AdsParameters.VERTICAL_TOP).horizontalAlign(AdsParameters.HORIZONTAL_CENTER);
	boolean visible = true;
	long delay = 0L;
	boolean enabled = true;

	public boolean isVisible() {
		return visible;
	}

	public AdsParameters getAdsParameters() {
		return adsParameters;
	}

	@Override
	public void show() {
		if (!enabled)
			return;
		this.visible = true;
	}

	@Override
	public void show(AdsParameters adsParameters) {
		if (!enabled)
			return;
		this.visible = true;
		if (adsParameters != null) 
			this.adsParameters = adsParameters;
		this.delay = adsParameters.delay;
	}

	@Override
	public void hide() {
		if (!enabled)
			return;
		this.visible = false;
	}

	@Override
	public void hide(AdsParameters adsParameters) {
		if (!enabled)
			return;
		this.visible = false;
		if (adsParameters != null)
			this.adsParameters = adsParameters;
		this.delay = adsParameters.delay;
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