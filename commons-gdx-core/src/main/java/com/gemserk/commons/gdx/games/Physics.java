package com.gemserk.commons.gdx.games;

import com.badlogic.gdx.physics.box2d.Body;
import com.gemserk.commons.gdx.box2d.Contact;

public interface Physics {

	Body getBody();
	
	Contact getContact();
	
}
