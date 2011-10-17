package com.gemserk.commons.artemis.components;

import com.artemis.Component;

public class AliveComponent extends Component {
	
	private float time;
	
	public float getTime() {
		return time;
	}
	
	public void setTime(float time) {
		this.time = time;
	}

	public AliveComponent(float time) {
		this.time = time;
	}

}
