package com.gemserk.commons.artemis.components;

import com.artemis.Component;
import com.artemis.ComponentTypeManager;
import com.artemis.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.gemserk.commons.gdx.g2d.SpriteMesh;

public class MeshSpriteComponent extends Component {

	public static final int type = ComponentTypeManager.getTypeFor(MeshSpriteComponent.class).getId();

	public static MeshSpriteComponent get(Entity e) {
		return (MeshSpriteComponent) e.getComponent(type);
	}

	public Color color;
	public Vector2 center;
	public boolean updateRotation = true;

	public SpriteMesh spriteMesh;

	public MeshSpriteComponent(SpriteMesh spriteMesh, float cx, float cy, Color color) {
		this.spriteMesh = spriteMesh;
		this.color = new Color(color);
		this.center = new Vector2(cx, cy);
	}

	public void update(float x, float y, float angle, float width, float height) {
		if (updateRotation)
			spriteMesh.setRotation(angle);
		float ox = width * center.x;
		float oy = height * center.y;
		spriteMesh.setOrigin(ox, oy);
		spriteMesh.setSize(width, height);
		spriteMesh.setPosition(x, y);
	}

}
