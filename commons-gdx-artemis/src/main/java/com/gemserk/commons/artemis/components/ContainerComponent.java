package com.gemserk.commons.artemis.components;

import com.artemis.Component;
import com.artemis.Entity;
import com.gemserk.componentsengine.utils.RandomAccessSet;

public class ContainerComponent extends Component {

	private RandomAccessSet<Entity> children = new RandomAccessSet<Entity>();
	
	public RandomAccessSet<Entity> getChildren() {
		return children;
	}

}