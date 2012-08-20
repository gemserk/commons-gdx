package com.gemserk.commons.artemis.systems;

import org.junit.Test;

import com.artemis.Component;
import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;
import com.artemis.World;
import com.badlogic.gdx.math.Vector2;
import com.gemserk.commons.artemis.WorldWrapper;

public class MultipleImplementationsSameComponentTest {
	
	public static abstract class SpatialComponent extends Component {

		public abstract void getPosition(float[] p);

		public abstract void setPosition(float x, float y);

	}

	public static class FirstImplementationComponent extends SpatialComponent {

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

	public static class SecondImplementationComponent extends SpatialComponent {

		private Vector2 position = new Vector2();

		@Override
		public void getPosition(float[] p) {
			p[0] = position.x;
			p[1] = position.y;
			System.out.println("second implementation get");
		}

		@Override
		public void setPosition(float x, float y) {
			this.position.set(x,y);
		}

	}
	
	public static class SecondImplementationSystem extends EntityProcessingSystem {
		
		private float[] tmpPosition = new float[2];

		@SuppressWarnings("unchecked")
		public SecondImplementationSystem() {
			super(SecondImplementationComponent.class);
		}
		
		@Override
		protected void process(Entity e) {
			FirstImplementationComponent spatialComponent = e.getComponent(FirstImplementationComponent.class);
			spatialComponent.getPosition(tmpPosition);
			System.out.println("second implementation: " + tmpPosition[0]);
		}
		
	}

	public static class FirstImplementationSystem extends EntityProcessingSystem {
		
		private float[] tmpPosition = new float[2];

		@SuppressWarnings("unchecked")
		public FirstImplementationSystem() {
			super(FirstImplementationComponent.class);
		}
		
		@Override
		protected void process(Entity e) {
			FirstImplementationComponent spatialComponent = e.getComponent(FirstImplementationComponent.class);
			spatialComponent.getPosition(tmpPosition);
			System.out.println("first implementation: " + tmpPosition[0]);
		}
		
	}
	
	public static class ParentSpatialSystem extends EntityProcessingSystem {
		
		private float[] tmpPosition = new float[2];

		@SuppressWarnings("unchecked")
		public ParentSpatialSystem() {
			super(SpatialComponent.class);
		}
		
		@Override
		protected void process(Entity e) {
			SpatialComponent spatialComponent = e.getComponent(SpatialComponent.class);
			spatialComponent.getPosition(tmpPosition);
			System.out.println("parent implementation: " + tmpPosition[0]);
		}
		
	}
	
	@Test
	public void shouldHandleSpecificComponentImplementation() {
		World world = new World();
		WorldWrapper worldWrapper = new WorldWrapper(world);
		worldWrapper.addUpdateSystem(new FirstImplementationSystem());
		worldWrapper.init();
		
		Entity e1 = world.createEntity();
		e1.addComponent(new FirstImplementationComponent());
		e1.refresh();
		
		worldWrapper.update(100);
	}
	
	@Test
	public void shouldNotHandleSpecificComponentImplementation() {
		World world = new World();
		WorldWrapper worldWrapper = new WorldWrapper(world);
		worldWrapper.addUpdateSystem(new SecondImplementationSystem());
		worldWrapper.init();
		
		Entity e1 = world.createEntity();
		e1.addComponent(new FirstImplementationComponent());
		e1.refresh();
		
		worldWrapper.update(100);
	}

	@Test
	public void shouldHandleParentComponentWithoutKnowingTheImplementation() {
		
		World world = new World();
		WorldWrapper worldWrapper = new WorldWrapper(world);
		worldWrapper.addUpdateSystem(new ParentSpatialSystem());
		worldWrapper.addUpdateSystem(new FirstImplementationSystem());
		worldWrapper.addUpdateSystem(new SecondImplementationSystem());
		worldWrapper.init();
		
		Entity e1 = world.createEntity();
		e1.addComponent(new FirstImplementationComponent());
		e1.refresh();
		
		worldWrapper.update(100);
		
	}

}
