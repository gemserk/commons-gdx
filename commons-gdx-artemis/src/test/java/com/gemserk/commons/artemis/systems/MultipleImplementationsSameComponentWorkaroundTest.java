package com.gemserk.commons.artemis.systems;

import org.junit.Test;

import com.artemis.Component;
import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;
import com.artemis.World;
import com.badlogic.gdx.math.Vector2;
import com.gemserk.commons.artemis.WorldWrapper;

public class MultipleImplementationsSameComponentWorkaroundTest {

	private static interface Spatial {

		float getX();

		float getY();

		void setPosition(float x, float y);

	}

	private static class SpatialComponent extends Component {

		private Spatial spatial;

		public Spatial getSpatial() {
			return spatial;
		}

		public SpatialComponent(Spatial spatial) {
			this.spatial = spatial;
		}

	}

	private static class SpatialFirstImpl implements Spatial {

		private float x;

		private float y;

		@Override
		public void setPosition(float x, float y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public float getX() {
			System.out.println("firstimpl");
			return x;
		}

		@Override
		public float getY() {
			return y;
		}

	}

	private static class SpatialSecondImpl implements Spatial {

		private Vector2 position = new Vector2();

		@Override
		public void setPosition(float x, float y) {
			this.position.set(x, y);
		}

		@Override
		public float getX() {
			System.out.println("second impl");
			return position.x;
		}

		@Override
		public float getY() {
			return position.y;
		}

	}

	private static class SpatialSystem extends EntityProcessingSystem {

		@SuppressWarnings("unchecked")
		public SpatialSystem() {
			super(SpatialComponent.class);
		}

		@Override
		protected void process(Entity e) {
			SpatialComponent spatialComponent = e.getComponent(SpatialComponent.class);
			Spatial spatial = spatialComponent.getSpatial();
			System.out.println("spatial system: " + spatial.getX());
		}

	}

	@Test
	public void shouldHandleSpecificComponentImplementation() {
		World world = new World();
		WorldWrapper worldWrapper = new WorldWrapper(world);
		worldWrapper.addUpdateSystem(new SpatialSystem());
		worldWrapper.init();

		Entity e1 = world.createEntity();
		e1.addComponent(new SpatialComponent(new SpatialFirstImpl()));
		e1.refresh();

		worldWrapper.update(100);
	}

	@Test
	public void shouldNotHandleSpecificComponentImplementation() {
		World world = new World();
		WorldWrapper worldWrapper = new WorldWrapper(world);
		worldWrapper.addUpdateSystem(new SpatialSystem());
		worldWrapper.init();

		Entity e1 = world.createEntity();
		e1.addComponent(new SpatialComponent(new SpatialSecondImpl()));
		e1.refresh();

		worldWrapper.update(100);
	}

}
