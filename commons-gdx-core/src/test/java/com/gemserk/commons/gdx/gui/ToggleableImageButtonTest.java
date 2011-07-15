package com.gemserk.commons.gdx.gui;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.junit.Test;

import com.badlogic.gdx.math.Rectangle;
import com.gemserk.commons.gdx.input.MockPointer;


public class ToggleableImageButtonTest {
	
	@Test
	public void shouldNotToggleIfPointerIsNotPressed() {
		MockPointer pointer = new MockPointer();
		MockToggleHandler toggleHandler = new MockToggleHandler();
		
		ToggleableImageButton toggleableImageButton = new ToggleableImageButton();
		toggleableImageButton.setPointer(pointer);
		toggleableImageButton.setToggleHandler(toggleHandler);
		toggleableImageButton.setPosition(0f, 0f);
		toggleableImageButton.setBounds(new Rectangle(20, 20, 100, 100));
		
		pointer.wasReleased = false;
		
		toggleableImageButton.udpate(100);
		
		assertThat(toggleHandler.toggleCalled, IsEqual.equalTo(false));
	}
	
	@Test
	public void shouldNotToggleIfPointerPressedButOutside() {
		MockPointer pointer = new MockPointer();
		MockToggleHandler toggleHandler = new MockToggleHandler();
		
		ToggleableImageButton toggleableImageButton = new ToggleableImageButton();
		toggleableImageButton.setPointer(pointer);
		toggleableImageButton.setToggleHandler(toggleHandler);
		toggleableImageButton.setPosition(0f, 0f);
		toggleableImageButton.setBounds(new Rectangle(20, 20, 100, 100));
		
		pointer.wasReleased = true;
		pointer.releasedPosition.set(-20, -20);
		
		toggleableImageButton.udpate(100);
		
		assertThat(toggleHandler.toggleCalled, IsEqual.equalTo(false));
	}
	
	@Test
	public void shouldToggleIfPointerPressedAndInside() {
		MockPointer pointer = new MockPointer();
		MockToggleHandler toggleHandler = new MockToggleHandler();
		
		ToggleableImageButton toggleableImageButton = new ToggleableImageButton();
		toggleableImageButton.setPointer(pointer);
		toggleableImageButton.setToggleHandler(toggleHandler);
		toggleableImageButton.setPosition(0f, 0f);
		toggleableImageButton.setBounds(new Rectangle(20, 20, 100, 100));
		
		pointer.wasReleased = true;
		pointer.releasedPosition.set(25, 25);
		
		toggleableImageButton.udpate(100);
		
		assertThat(toggleHandler.toggleCalled, IsEqual.equalTo(true));
	}
	
	@Test
	public void shouldToggleIfPointerPressedAndInsideOnlyOnce() {
		MockPointer pointer = new MockPointer();
		MockToggleHandler toggleHandler = new MockToggleHandler();
		
		ToggleableImageButton toggleableImageButton = new ToggleableImageButton();
		toggleableImageButton.setPointer(pointer);
		toggleableImageButton.setToggleHandler(toggleHandler);
		toggleableImageButton.setPosition(0f, 0f);
		toggleableImageButton.setBounds(new Rectangle(20, 20, 100, 100));
		
		pointer.wasReleased = true;
		pointer.releasedPosition.set(25, 25);
		
		toggleableImageButton.udpate(100);
		assertThat(toggleHandler.toggleCalled, IsEqual.equalTo(true));

		pointer.wasReleased = false;
		toggleHandler.toggleCalled = false;

		toggleableImageButton.udpate(100);
		assertThat(toggleHandler.toggleCalled, IsEqual.equalTo(false));
	}


}
