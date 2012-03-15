package com.gemserk.commons.gdx.scene2d;

import com.badlogic.gdx.scenes.scene2d.Actor;

public interface ActorFocusListener {

	void focusLost(Actor actor);

	void focusGained(Actor actor);

}