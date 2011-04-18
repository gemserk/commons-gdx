package com.gemserk.commons.artemis.systems;

import java.util.ArrayList;

import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.utils.ImmutableBag;
import com.gemserk.commons.gdx.input.LibgdxPointer;

public class PointerUpdateSystem extends EntitySystem {

	private final ArrayList<LibgdxPointer> pointers;

	public PointerUpdateSystem(ArrayList<LibgdxPointer> pointers) {
		this.pointers = pointers;
	}

	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		
		for (int i = 0; i < pointers.size(); i++) {
			LibgdxPointer pointer = pointers.get(i);
			pointer.update();
		}

	}

	@Override
	public void initialize() {

	}

	@Override
	protected boolean checkProcessing() {
		return true;
	}
}