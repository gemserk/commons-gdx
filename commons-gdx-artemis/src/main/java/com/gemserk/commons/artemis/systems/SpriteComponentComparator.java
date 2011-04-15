package com.gemserk.commons.artemis.systems;

import java.util.Comparator;

import com.artemis.Entity;
import com.gemserk.commons.artemis.components.SpriteComponent;

public class SpriteComponentComparator implements Comparator<Entity> {
	@Override
	public int compare(Entity o1, Entity o2) {
		SpriteComponent spriteComponent1 = o1.getComponent(SpriteComponent.class);
		SpriteComponent spriteComponent2 = o2.getComponent(SpriteComponent.class);
		return spriteComponent1.getLayer() - spriteComponent2.getLayer();
	}
}