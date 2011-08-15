package com.gemserk.commons.artemis.components;

import org.junit.Test;

import com.artemis.Component;
import com.artemis.ComponentMapper;
import com.artemis.ComponentType;
import com.artemis.ComponentTypeManager;
import com.artemis.Entity;
import com.artemis.World;

public class GetComponentPerformanceTest {

	private static class MyTestComponent extends Component {

	}

	@Test
	public void testTimeWithGetComponentFromClass() {

		World world = new World();
		Entity e = world.createEntity();
		e.addComponent(new MyTestComponent());
		e.refresh();

		long startNanoTime = System.nanoTime();

		for (int i = 0; i < 100000; i++) {
			MyTestComponent myTestComponent = e.getComponent(MyTestComponent.class);
		}

		long endNanoTime = System.nanoTime();
		long totalTimeNs = (endNanoTime - startNanoTime);
		long totalTimeMs = (endNanoTime - startNanoTime) / 1000000;
		
		System.out.println("Time with e.getComponent(class) = " + totalTimeNs + "ns");
		System.out.println("Time with e.getComponent(class) = " + totalTimeMs + "ms");

	}
	
	@Test
	public void testTimeWithGetComponentFromClassWithClassCache() {

		World world = new World();
		Entity e = world.createEntity();
		e.addComponent(new MyTestComponent());
		e.refresh();

		long startNanoTime = System.nanoTime();
		
		Class<MyTestComponent> myTestComponentClass = MyTestComponent.class;

		for (int i = 0; i < 100000; i++) {
			MyTestComponent myTestComponent = e.getComponent(myTestComponentClass);
		}

		long endNanoTime = System.nanoTime();
		long totalTimeNs = (endNanoTime - startNanoTime);
		long totalTimeMs = (endNanoTime - startNanoTime) / 1000000;
		
		System.out.println("Time with e.getComponent(class) with class cache = " + totalTimeNs + "ns");
		System.out.println("Time with e.getComponent(class) with class cache = " + totalTimeMs + "ms");

	}
	
	@Test
	public void testTimeWithGetComponentFromMapper() {

		World world = new World();
		Entity e = world.createEntity();
		e.addComponent(new MyTestComponent());
		e.refresh();
		
		ComponentMapper<MyTestComponent> myTestComponentMapper = new ComponentMapper<MyTestComponent>(MyTestComponent.class, world.getEntityManager());

		long startNanoTime = System.nanoTime();

		for (int i = 0; i < 100000; i++) {
			MyTestComponent myTestComponent = myTestComponentMapper.get(e);
		}

		long endNanoTime = System.nanoTime();
		long totalTimeNs = (endNanoTime - startNanoTime);
		long totalTimeMs = (endNanoTime - startNanoTime) / 1000000;
		
		System.out.println("Time with componentMapper.get(e) = " + totalTimeNs + "ns");
		System.out.println("Time with componentMapper.get(e) = " + totalTimeMs + "ms");

	}
	
	@Test
	public void testTimeWithGetComponentUsingTypeAndClass() {

		World world = new World();
		Entity e = world.createEntity();
		e.addComponent(new MyTestComponent());
		e.refresh();
		
		ComponentType myTestComponentType = ComponentTypeManager.getTypeFor(MyTestComponent.class);
		Class<MyTestComponent> myTestComponentClass = MyTestComponent.class;

		long startNanoTime = System.nanoTime();

		for (int i = 0; i < 100000; i++) {
			MyTestComponent myTestComponent = myTestComponentClass.cast(e.getComponent(myTestComponentType));
		}

		long endNanoTime = System.nanoTime();
		long totalTimeNs = (endNanoTime - startNanoTime);
		long totalTimeMs = (endNanoTime - startNanoTime) / 1000000;
		
		System.out.println("Time with cast(e.getComponent(type)) = " + totalTimeNs + "ns");
		System.out.println("Time with cast(e.getComponent(type)) = " + totalTimeMs + "ms");

	}

}
