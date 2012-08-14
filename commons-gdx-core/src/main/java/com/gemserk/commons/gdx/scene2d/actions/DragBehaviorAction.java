package com.gemserk.commons.gdx.scene2d.actions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.gemserk.commons.gdx.scene2d.ActionAdapter;
import com.gemserk.commons.monitors.BooleanMonitor;

/**
 * Deprecated because Table.isPressed is not available anymore.
 */
@Deprecated
public class DragBehaviorAction extends ActionAdapter {

	BooleanMonitor booleanMonitor;
	boolean dragging = false;
	Vector2 previousPosition = new Vector2();
	Vector2 drag = new Vector2();

	Vector3 worldTouchPosition = new Vector3();

	@Override
	public void begin() {
		booleanMonitor = new BooleanMonitor(false);
	}

	@Override
	public boolean update(float delta) {
		Table table = (Table) getActor();

		worldTouchPosition.set(Gdx.input.getX(), Gdx.input.getY(), 1f);
		table.getStage().getCamera().unproject(worldTouchPosition);

		// booleanMonitor.update(table.isPressed);

		if (booleanMonitor.isChanged()) {
			if (booleanMonitor.getValue())
				previousPosition.set(worldTouchPosition.x, worldTouchPosition.y);
			dragging = booleanMonitor.getValue();

		}

		if (dragging) {
			drag.set(worldTouchPosition.x, worldTouchPosition.y);
			drag.sub(previousPosition);

			drag.x = Math.round(drag.x);
			drag.y = Math.round(drag.y);

			table.setX(table.getX() + drag.x);
			table.setY(table.getY() + drag.y);

			previousPosition.set(worldTouchPosition.x, worldTouchPosition.y);
		}

		return false;
	}
	
}