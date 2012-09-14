package com.gemserk.commons.applications.decorators;

import java.awt.Dimension;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.gemserk.componentsengine.input.ButtonMonitor;
import com.gemserk.componentsengine.input.InputDevicesMonitorImpl;
import com.gemserk.componentsengine.input.LibgdxInputMappingBuilder;

public class SwitchableResolutionApplicationDelegate implements ApplicationListener {

	private final ApplicationListener applicationListener;
	private final Dimension[] resolutions;
	private int currentResolution;

	private InputDevicesMonitorImpl<String> inputMonitor;
	private ButtonMonitor switchResolutionButton;
	private ButtonMonitor switchPreviousResolutionButton;
	private String applicationName;

	public SwitchableResolutionApplicationDelegate(ApplicationListener applicationListener, String applicationName, Dimension... resolutions) {
		this.applicationListener = applicationListener;
		this.resolutions = resolutions;
		this.applicationName = applicationName;
	}

	public void create() {
		applicationListener.create();
		inputMonitor = new InputDevicesMonitorImpl<String>();
		new LibgdxInputMappingBuilder<String>(inputMonitor, Gdx.input) {
			{
				monitorKeys("nextResolution", Keys.PLUS);
				monitorKeys("previousResolution", Keys.MINUS);
			}
		};

		switchResolutionButton = inputMonitor.getButton("nextResolution");
		switchPreviousResolutionButton = inputMonitor.getButton("previousResolution");
		currentResolution = 0;
	}

	public void resize(int width, int height) {
		applicationListener.resize(width, height);
		Gdx.graphics.setTitle(String.format(applicationName + " - Resolution(%dx%d) - (%d/%d)", width, height, currentResolution + 1, resolutions.length));
	}

	public void render() {
		inputMonitor.update();

		if (switchResolutionButton.isReleased()) {
			currentResolution = (currentResolution + 1) % resolutions.length;
			changeResolution();
		} else if (switchPreviousResolutionButton.isReleased()) {
			currentResolution--;
			if (currentResolution < 0)
				currentResolution = resolutions.length - 1;
			changeResolution();
		}

		applicationListener.render();
	}

	private void changeResolution() {
		Dimension resolution = resolutions[currentResolution];
		Gdx.graphics.setDisplayMode(resolution.width, resolution.height, false);
		Gdx.app.log("commons-gdx", String.format("Switching resolution to: (%dx%d))", resolution.width, resolution.height));
		applicationListener.resize(resolution.width, resolution.height);
	}

	public void pause() {
		applicationListener.pause();
	}

	public void resume() {
		applicationListener.resume();
	}

	public void dispose() {
		applicationListener.dispose();
	}

}