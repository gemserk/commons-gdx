package com.gemserk.commons.artemis.components;

import com.artemis.Component;

public class LinearVelocityLimitComponent extends Component {
	
	private float limit;
	
	public float getLimit() {
		return limit;
	}
	
	public void setLimit(float limit) {
		this.limit = limit;
	}

	public LinearVelocityLimitComponent(float limit) {
		this.limit = limit;
	}

}
