package com.gemserk.commons.artemis.components;

import com.artemis.Component;

public class AliveComponent extends Component {
	
	private int aliveTime;
	
	public int getAliveTime() {
		return aliveTime;
	}
	
	public void setAliveTime(int aliveTime) {
		this.aliveTime = aliveTime;
	}

	public AliveComponent(int time) {
		this.aliveTime = time;
	}

}
