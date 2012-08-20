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
	public void test() {

		// some vm warm up

		for (int i = 0; i < 100; i++) {
			testTimeWithGetComponentFromClassWithClassCache(10000);
			testTimeWithGetComponentFromClass(10000);
			testTimeWithGetComponentFromMapper(10000);
			testTimeWithGetComponentUsingTypeAndClass(10000);
			testTimeWithGetComponentUsingTypeAndCacheClass(10000);
		}

		int iterations = 50000;

		long testTimeWithGetComponentFromMapperNs = testTimeWithGetComponentFromMapper(iterations);
		long testTimeWithGetComponentUsingTypeAndClassNs = testTimeWithGetComponentUsingTypeAndClass(iterations);
		long testTimeWithGetComponentUsingTypeAndCacheClassNs = testTimeWithGetComponentUsingTypeAndCacheClass(iterations);
		long testTimeWithGetComponentFromClassWithClassCacheNs = testTimeWithGetComponentFromClassWithClassCache(iterations);
		long testTimeWithGetComponentFromClassNs = testTimeWithGetComponentFromClass(iterations);

		System.out.println("Time with e.getComponent(class) with class cache = " + testTimeWithGetComponentFromClassWithClassCacheNs + "ns");
		System.out.println("Time with e.getComponent(class) = " + testTimeWithGetComponentFromClassNs + "ns");
		System.out.println("Time with componentMapper.get(e) = " + testTimeWithGetComponentFromMapperNs + "ns");
		System.out.println("Time with cast(e.getComponent(type)) = " + testTimeWithGetComponentUsingTypeAndClassNs + "ns");
		System.out.println("Time with cast(e.getComponent(type)) (with class cache) = " + testTimeWithGetComponentUsingTypeAndCacheClassNs + "ns");

		System.out.println("Time with e.getComponent(class) with class cache = " + testTimeWithGetComponentFromClassWithClassCacheNs / iterations + "ns (average)");
		System.out.println("Time with e.getComponent(class) = " + testTimeWithGetComponentFromClassNs / iterations + "ns (average)");
		System.out.println("Time with componentMapper.get(e) = " + testTimeWithGetComponentFromMapperNs / iterations + "ns (average)");
		System.out.println("Time with cast(e.getComponent(type)) = " + testTimeWithGetComponentUsingTypeAndClassNs / iterations + "ns (average)");
		System.out.println("Time with cast(e.getComponent(type)) (with class cache) = " + testTimeWithGetComponentUsingTypeAndCacheClassNs / iterations + "ns (average)");

		System.out.println("Time with e.getComponent(class) with class cache = " + testTimeWithGetComponentFromClassWithClassCacheNs / 1000000 + "ms");
		System.out.println("Time with e.getComponent(class) = " + testTimeWithGetComponentFromClassNs / 1000000 + "ms");
		System.out.println("Time with componentMapper.get(e) = " + testTimeWithGetComponentFromMapperNs / 1000000 + "ms");
		System.out.println("Time with cast(e.getComponent(type)) = " + testTimeWithGetComponentUsingTypeAndClassNs / 1000000 + "ms");
		System.out.println("Time with cast(e.getComponent(type)) (with class cache) = " + testTimeWithGetComponentUsingTypeAndCacheClassNs / 1000000 + "ms");

	}

	public long testTimeWithGetComponentFromMapper(int iterations) {

		World world = new World();
		Entity e = world.createEntity();
		e.addComponent(new MyTestComponent());
		e.refresh();

		ComponentMapper<MyTestComponent> myTestComponentMapper = new ComponentMapper<MyTestComponent>(MyTestComponent.class, world.getEntityManager());

		long startNanoTime = System.nanoTime();

		for (int i = 0; i < iterations; i++) {
			myTestComponentMapper.get(e);
			// MyTestComponent myTestComponent = myTestComponentMapper.get(e);
		}

		return System.nanoTime() - startNanoTime;
	}

	public long testTimeWithGetComponentUsingTypeAndClass(int iterations) {

		World world = new World();
		Entity e = world.createEntity();
		e.addComponent(new MyTestComponent());
		e.refresh();

		ComponentType myTestComponentType = ComponentTypeManager.getTypeFor(MyTestComponent.class);

		long startNanoTime = System.nanoTime();

		for (int i = 0; i < iterations; i++) {
			// MyTestComponent myTestComponent = MyTestComponent.class.cast(e.getComponent(myTestComponentType.getId()));
			MyTestComponent.class.cast(e.getComponent(myTestComponentType.getId()));
		}

		return System.nanoTime() - startNanoTime;
	}

	public long testTimeWithGetComponentUsingTypeAndCacheClass(int iterations) {

		World world = new World();
		Entity e = world.createEntity();
		e.addComponent(new MyTestComponent());
		e.refresh();

		ComponentType myTestComponentType = ComponentTypeManager.getTypeFor(MyTestComponent.class);
		Class<MyTestComponent> myTestComponentClass = MyTestComponent.class;

		long startNanoTime = System.nanoTime();

		for (int i = 0; i < iterations; i++) {
			// MyTestComponent myTestComponent = myTestComponentClass.cast(e.getComponent(myTestComponentType.getId()));
			myTestComponentClass.cast(e.getComponent(myTestComponentType.getId()));
		}

		return System.nanoTime() - startNanoTime;
	}

	public long testTimeWithGetComponentFromClassWithClassCache(int iterations) {

		World world = new World();
		Entity e = world.createEntity();
		e.addComponent(new MyTestComponent());
		e.refresh();

		Class<MyTestComponent> myTestComponentClass = MyTestComponent.class;

		long startNanoTime = System.nanoTime();

		for (int i = 0; i < iterations; i++) {
			// MyTestComponent myTestComponent = e.getComponent(myTestComponentClass);
			e.getComponent(myTestComponentClass);
		}

		return System.nanoTime() - startNanoTime;
	}

	public long testTimeWithGetComponentFromClass(int iterations) {

		World world = new World();
		Entity e = world.createEntity();
		e.addComponent(new MyTestComponent());
		e.refresh();

		long startNanoTime = System.nanoTime();
		// long iterationsTime = 0;
		// long previousFrameTime = startNanoTime;

		for (int i = 0; i < iterations; i++) {
			// MyTestComponent myTestComponent = e.getComponent(MyTestComponent.class);
			e.getComponent(MyTestComponent.class);
		}

		return System.nanoTime() - startNanoTime;
	}
}
