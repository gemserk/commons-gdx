package com.gemserk.commons.admob;

import java.util.ArrayList;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;

import com.gemserk.commons.admob.AdsAnimation.Type;
import com.google.ads.AdView;

public class AdMobHandler extends Handler {

	static class AnimationAdapter implements AnimationListener {

		@Override
		public void onAnimationStart(Animation animation) {

		}

		@Override
		public void onAnimationEnd(Animation animation) {

		}

		@Override
		public void onAnimationRepeat(Animation animation) {

		}

	}

	static class HideAnimationListener extends AnimationAdapter {

		private final AdView adView;

		public HideAnimationListener(AdView adView) {
			this.adView = adView;
		}

		@Override
		public void onAnimationEnd(Animation animation) {
			adView.setVisibility(View.GONE);
			System.out.println("setting visibility to gone");
		}

	}

	static class ShowAnimationListener extends AnimationAdapter {

		private final AdView adView;

		public ShowAnimationListener(AdView adView) {
			this.adView = adView;
		}

		@Override
		public void onAnimationStart(Animation animation) {
			adView.setVisibility(View.VISIBLE);
			System.out.println("setting visibility to visible");
		}

	}

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
			showAds(msg);
			break;
		}
		case HIDE_ADS: {
			hideAds(msg);
			break;
		}
		}
	}

	private void hideAds(Message msg) {
		AdsParameters adsParameters = (AdsParameters) msg.obj;
		
		if (adView.getVisibility() == View.GONE)
			return;

		if (adsParameters != null && !adsParameters.animations.isEmpty()) {

			ArrayList<AdsAnimation> animations = adsParameters.animations;

			for (int i = 0; i < animations.size(); i++) {
				AdsAnimation adsAnimation = animations.get(i);

				if (adsAnimation.type == AdsAnimation.Type.Alpha) {
					AlphaAnimation animation = new AlphaAnimation(1.0f, 0.0f);
					animation.setDuration(adsAnimation.duration);
					animation.setFillAfter(false);
					animation.setInterpolator(new LinearInterpolator());
					animation.setAnimationListener(new HideAnimationListener(adView));
					adView.startAnimation(animation);//
					
					System.out.println("hiding with animation alpha");
				} else if (adsAnimation.type == Type.Translation) {
					float yDelta = adsParameters.verticalAlign == AdsParameters.VERTICAL_TOP ? -75f : 75f;
					TranslateAnimation animation = new TranslateAnimation(0, 0, 0, yDelta);
					animation.setDuration(adsAnimation.duration);
					animation.setFillAfter(false);
					animation.setInterpolator(new LinearInterpolator());
					animation.setAnimationListener(new HideAnimationListener(adView));
					adView.startAnimation(animation);
					
					System.out.println("hiding with animation translation");
				} else {
					adView.setVisibility(View.GONE);
				}

			}

		} else {
			adView.setVisibility(View.GONE);
		}

	}

	private void showAds(Message msg) {
		AdsParameters adsParameters = (AdsParameters) msg.obj;

		if (adsParameters != null && !adsParameters.animations.isEmpty()) {
			RelativeLayout.LayoutParams adParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

			adParams.addRule(getVerticalAlign(adsParameters));
			adParams.addRule(getHorizontalAlign(adsParameters));

			layout.removeView(adView);
			layout.addView(adView, adParams);

			ArrayList<AdsAnimation> animations = adsParameters.animations;
			int size = animations.size();

			for (int i = 0; i < size; i++) {
				AdsAnimation adsAnimation = animations.get(i);

				if (adsAnimation.type == AdsAnimation.Type.Alpha) {
					AlphaAnimation animation = new AlphaAnimation(0.0f, 1.0f);
					animation.setDuration(adsAnimation.duration);
					animation.setFillAfter(false);
					animation.setInterpolator(new LinearInterpolator());
					animation.setAnimationListener(new ShowAnimationListener(adView));
					adView.startAnimation(animation);
					
					System.out.println("showing with animation alpha");
				} else if (adsAnimation.type == Type.Translation) {
					float yDelta = adsParameters.verticalAlign == AdsParameters.VERTICAL_TOP ? -75f : 75f;
					TranslateAnimation animation = new TranslateAnimation(0, 0, yDelta, 0);
					animation.setDuration(adsAnimation.duration);
					animation.setInterpolator(new LinearInterpolator());
					animation.setAnimationListener(new ShowAnimationListener(adView));
					adView.startAnimation(animation);
					
					System.out.println("showing with animation translation");
				} else {
					adView.setVisibility(View.VISIBLE);
				}

				// dont support other animations yet...
			}
		} else {
			adView.setVisibility(View.VISIBLE);
		}

	}

	private int getVerticalAlign(AdsParameters adViewLocation) {
		if (adViewLocation.verticalAlign == AdsParameters.VERTICAL_TOP)
			return RelativeLayout.ALIGN_PARENT_TOP;
		if (adViewLocation.verticalAlign == AdsParameters.VERTICAL_BOTTOM)
			return RelativeLayout.ALIGN_PARENT_BOTTOM;
		return RelativeLayout.CENTER_VERTICAL;
	}

	private int getHorizontalAlign(AdsParameters adViewLocation) {
		if (adViewLocation.horizontalAlign == AdsParameters.HORIZONTAL_LEFT)
			return RelativeLayout.ALIGN_PARENT_LEFT;
		if (adViewLocation.horizontalAlign == AdsParameters.HORIZONTAL_RIGHT)
			return RelativeLayout.ALIGN_PARENT_RIGHT;
		return RelativeLayout.CENTER_HORIZONTAL;
	}

}