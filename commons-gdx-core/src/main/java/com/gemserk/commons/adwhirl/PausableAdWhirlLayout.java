package com.gemserk.commons.adwhirl;

import android.app.Activity;

import com.adwhirl.AdWhirlLayout;
import com.badlogic.gdx.Gdx;

/**
 * Custom implementation of AdWhirlLayout which allows you to pause AdWhirl from requesting ads when the view is not visible, 
 * reference <a href="https://groups.google.com/forum/#!topic/adwhirl-users/VQdGs7ZrVN8">here</a>.
 */
public class PausableAdWhirlLayout extends AdWhirlLayout {
	
	boolean paused = false;

	public PausableAdWhirlLayout(final Activity context, final String keyAdWhirl) {
		super(context, keyAdWhirl);
	}

	/**
	 * Call this to hide AdWhirlLayout and to pause AdWhirl requesting ads. 
	 */
	public void onPause() {
		paused = true;
		Gdx.app.log("Pause", "adwhirl paused");
		this.onWindowVisibilityChanged(INVISIBLE);
	}

	/**
	 * Call this to show AdWhirlLayout and to resume AdWhirl requesting ads. 
	 */
	public void onResume() {
		paused = false;
		Gdx.app.log("Pause", "adwhirl resumed");
		this.onWindowVisibilityChanged(VISIBLE);
	}

	@Override
	protected void onWindowVisibilityChanged(int visibility) {
		// FIXME: this is a hack
		// However if we don't do this the view will start requesting
		// ads whenever the "ACTIVITY" becomes visible even when the view is
		// invisible... which is not what we want in an OpenGL application
		visibility = (paused == true) ? INVISIBLE : VISIBLE;
		setVisibility(visibility);
		super.onWindowVisibilityChanged(visibility);
		
		Gdx.app.log("Pause", "adwhirl on window visibility changed");
	}
}
