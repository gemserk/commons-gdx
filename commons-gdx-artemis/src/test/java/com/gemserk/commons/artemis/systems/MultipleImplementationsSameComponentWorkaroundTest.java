package com.gemserk.commons.artemis.systems;

import org.junit.Test;

import com.artemis.Component;
import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;
import com.artemis.World;
import com.badlogic.gdx.math.Vector2;
import com.gemserk.commons.artemis.WorldWrapper;

public class MultipleImplementationsSameComponentWorkaroundTest {

	public static interface Spatial {

		void getPosition(float[] p);

		void setPosition(float x, float y);

	}

	public static class SpatialComponent extends Component {

		private Spatial spatial;
		
		public Spatial getSpatial() {
			return spatial;
		}
		
		public void setSpatial(Spatial spatial) {
			this.spatial = spatial;
		}

		public SpatialComponent(Spatial spatial) {
			this.spatial = spatial;
		}

	}

	public static class SpatialFirstImpl implements Spatial {

		private float x;

		private float y;

		@Override
		public void getPosition(float[] p) {
			p[0] = x;
			p[1] = y;
			System.out.println("first implementation get");
		}

		@Override
		public void setPosition(float x, float y) {
			this.x = x;
			this.y = y;
		}

	}

	public static class SpatialSecondImpl implements Spatial {

		private Vector2 position = new Vector2();

		@Override
		public void getPosition(float[] p) {
			p[0] = position.x;
			p[1] = position.y;
			System.out.println("second implementation get");
		}

		@Override
		public void setPosition(float x, float y) {
			this.position.set(x, y);
		}

	}

	public static class SpatialSystem extends EntityProcessingSystem {

		private float[] tmpPosition = new float[2];

		public SpatialSystem() {
			super(SpatialComponent.class);
		}

		@Override
		protected void process(Entity e) {
			SpatialComponent spatialComponent = e.getComponent(SpatialComponent.class);
			Spatial spatial = spatialComponent.getSpatial();
			spatial.getPosition(tmpPosition);
			System.out.println("spatial system: " + tmpPosition[0]);
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
