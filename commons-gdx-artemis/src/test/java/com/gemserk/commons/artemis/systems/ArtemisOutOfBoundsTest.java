package com.gemserk.commons.artemis.systems;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsNull;
import org.junit.Test;

import com.artemis.Component;
import com.artemis.Entity;
import com.artemis.World;

public class ArtemisOutOfBoundsTest {

	private static class Component1 extends Component {
	}

	private static class Component2 extends Component {
	}

	private static class Component3 extends Component {
	}

	private static class Component4 extends Component {
	}

	private static class Component5 extends Component {
	}

	private static class Component6 extends Component {
	}

	private static class Component7 extends Component {
	}

	private static class Component8 extends Component {
	}

	private static class Component9 extends Component {
	}

	private static class Component10 extends Component {
	}

	private static class Component11 extends Component {
	}

	private static class Component12 extends Component {
	}

	private static class Component13 extends Component {
	}

	private static class Component14 extends Component {
	}

	private static class Component15 extends Component {
	}

	private static class Component16 extends Component {
	}

	private static class ComponentOutOfBounds extends Component {
	}

	@Test
	public void notest() {
		// to avoid mvn test failing
	}

	@Test
	public void shouldGetComponent() {
		World world = new World();

		Entity e = world.createEntity();
		e.addComponent(new Component1());
		e.refresh();

		Component1 component = e.getComponent(Component1.class);
		assertThat(component, IsNull.notNullValue());

	}

	@Test
	public void shouldNotFailWhenAskingForComponentOutsideBounds() {

		World world = new World();

		Entity e = world.createEntity();

		e.addComponent(new Component1());
		e.addComponent(new Component2());
		e.addComponent(new Component3());
		e.addComponent(new Component4());

		e.addComponent(new Component5());
		e.addComponent(new Component6());
		// e.addComponent(new Component7());
		e.addComponent(new Component8());

		e.addComponent(new Component9());
		e.addComponent(new Component10());
		e.addComponent(new Component11());
		e.addComponent(new Component12());
		e.addComponent(new Component13());
		e.addComponent(new Component14());
		e.addComponent(new Component15());
		e.addComponent(new Component16());
		e.addComponent(new ComponentOutOfBounds());

		e.refresh();

		e.getComponent(Component1.class);
		e.getComponent(Component2.class);
		e.getComponent(Component3.class);
		e.getComponent(Component4.class);

		e.getComponent(Component5.class);
		e.getComponent(Component6.class);
		e.getComponent(Component7.class);
		e.getComponent(Component8.class);

		e.getComponent(Component9.class);
		e.getComponent(Component10.class);
		e.getComponent(Component11.class);
		e.getComponent(Component12.class);
		e.getComponent(Component13.class);
		e.getComponent(Component14.class);
		e.getComponent(Component15.class);
		e.getComponent(Component16.class);

		// e.getComponent(ComponentOutOfBounds.class);

		ComponentOutOfBounds component = e.getComponent(ComponentOutOfBounds.class);
		assertThat(component, IsNull.notNullValue());

		Component7 component7 = e.getComponent(Component7.class);
		assertThat(component7, IsNull.nullValue());

	}

}
