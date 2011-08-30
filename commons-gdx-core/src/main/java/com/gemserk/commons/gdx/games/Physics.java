package com.gemserk.commons.gdx.games;

import com.badlogic.gdx.physics.box2d.Body;
import com.gemserk.commons.gdx.box2d.Contacts;

public interface Physics {

	Body getBody();
	
	Contacts getContact();
	
}
