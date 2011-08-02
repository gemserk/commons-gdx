package com.gemserk.commons.artemis.systems;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsNull;
import org.hamcrest.core.IsSame;
import org.junit.Test;

import com.artemis.Entity;
import com.artemis.World;

public class TagManagerTest {
	
	@Test
	public void test() {
		
	}
	
	public void shouldGetComponent() {
		String tag = "SuperUniqueEntity";
		
		World world = new World();
		Entity e = world.createEntity();
		e.setTag(tag);
		e.refresh();
		
		world.setDelta(10);
		world.loopStart();
		
		Entity e1 = world.getTagManager().getEntity(tag);
		assertThat(e1, IsNull.notNullValue());
		assertThat(e1, IsSame.sameInstance(e));
		
		world.deleteEntity(e);
		
		world.setDelta(10);
		world.loopStart();

		Entity e2 = world.getTagManager().getEntity(tag);
		assertThat(e2, IsNull.nullValue());
	}


}
