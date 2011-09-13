package com.gemserk.commons.gdx.gui;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsNull;
import org.hamcrest.core.IsSame;
import org.junit.Test;

public class ContainerTest {
	
	private Control mockControl(String id) {
		MockControl control = new MockControl();
		control.setId("A");
		return control;
	}

	@Test
	public void shouldFindControlInChildrenControls() {
		Container parent = new Container();
		Container child = new Container();
		Control grandChild = mockControl("A");

		child.add(grandChild);
		parent.add(child);

		Control findControl = parent.findControl("A");
		assertThat(findControl, IsNull.notNullValue());
		assertThat(findControl, IsSame.sameInstance(grandChild));
	}

}
