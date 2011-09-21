package com.gemserk.commons.artemis.systems;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.junit.Test;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.utils.ImmutableBag;

public class GroupManagerTest {
	
	@Test
	public void entityNotRemovedFromGroupWhenDeleted() {
		String group = "EntityGroup";
		
		World world = new World();
		Entity e = world.createEntity();
		
		e.setGroup(group);
		e.refresh();
		
		world.setDelta(10);
		world.loopStart();
		
		ImmutableBag<Entity> entities = world.getGroupManager().getEntities(group);
		assertThat(entities.size(), IsEqual.equalTo(1));
		
		world.deleteEntity(e);
		
		world.setDelta(10);
		world.loopStart();

		entities = world.getGroupManager().getEntities(group);
		assertThat(entities.size(), IsEqual.equalTo(0));
	}


}
