package com.gemserk.commons.gdx.input;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.junit.Test;

public class LibgdxPointerTest {
	
	@Test
	public void shouldNotBePressedIfPointerNotDown() {
		MockRealPointer pointer = new MockRealPointer();
		LibgdxPointer libgdxPointer = new LibgdxPointer(pointer);
		pointer.down = false;
		libgdxPointer.update();
		assertThat(libgdxPointer.wasPressed(), IsEqual.equalTo(false));
	}
	
	@Test
	public void shouldBePressedIfPointerDown() {
		MockRealPointer pointer = new MockRealPointer();
		LibgdxPointer libgdxPointer = new LibgdxPointer(pointer);
		pointer.down = true;
		libgdxPointer.update();
		assertThat(libgdxPointer.wasPressed(), IsEqual.equalTo(true));
	}

	@Test
	public void shouldBePressedOnlyFirstTime() {
		MockRealPointer pointer = new MockRealPointer();
		LibgdxPointer libgdxPointer = new LibgdxPointer(pointer);
		pointer.down = true;
		libgdxPointer.update();
		libgdxPointer.update();
		assertThat(libgdxPointer.wasPressed(), IsEqual.equalTo(false));
	}
	
	@Test
	public void shouldBeReleasedIfWasPressedAndPointerUp() {
		MockRealPointer pointer = new MockRealPointer();
		LibgdxPointer libgdxPointer = new LibgdxPointer(pointer);
		pointer.down = true;
		libgdxPointer.update();
		pointer.down = false;
		libgdxPointer.update();
		assertThat(libgdxPointer.wasReleased(), IsEqual.equalTo(true));
	}
	
	@Test
	public void shouldBeReleasedIfWasPressedAndPointerUpOnlyFirstTime() {
		MockRealPointer pointer = new MockRealPointer();
		LibgdxPointer libgdxPointer = new LibgdxPointer(pointer);
		pointer.down = true;
		libgdxPointer.update();
		pointer.down = false;
		libgdxPointer.update();
		libgdxPointer.update();
		assertThat(libgdxPointer.wasReleased(), IsEqual.equalTo(false));
	}
	
	@Test
	public void shouldNotBePressedIfReleased() {
		MockRealPointer pointer = new MockRealPointer();
		LibgdxPointer libgdxPointer = new LibgdxPointer(pointer);
		pointer.down = true;
		libgdxPointer.update();
		assertThat(libgdxPointer.wasPressed(), IsEqual.equalTo(true));
		pointer.down = false;
		libgdxPointer.update();
		assertThat(libgdxPointer.wasPressed(), IsEqual.equalTo(false));
	}
	
	@Test
	public void shouldNotBeReleasedIfWasReleasedAndNowPressed() {
		MockRealPointer pointer = new MockRealPointer();
		LibgdxPointer libgdxPointer = new LibgdxPointer(pointer);
		pointer.down = true;
		libgdxPointer.update();
		pointer.down = false;
		libgdxPointer.update();
		assertThat(libgdxPointer.wasReleased(), IsEqual.equalTo(true));
		pointer.down = true;
		libgdxPointer.update();
		assertThat(libgdxPointer.wasReleased(), IsEqual.equalTo(false));
	}
}
