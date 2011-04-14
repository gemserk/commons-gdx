package com.gemserk.commons.artemis.systems;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.utils.Bag;
import com.artemis.utils.ImmutableBag;
import com.artemis.utils.Utils;
import com.gemserk.commons.artemis.LocalTransform;
import com.gemserk.commons.artemis.Node;
import com.gemserk.commons.artemis.Transform;

public class SceneGraphSystem extends EntitySystem {

	private ComponentMapper<Node> nodeMapper;
	private ComponentMapper<Transform> transformMapper;
	private ComponentMapper<LocalTransform> localTransformMapper;

	private Bag<Entity> rootNode;

	public SceneGraphSystem() {
		super(Node.class);
		rootNode = new Bag<Entity>();
	}

	@Override
	public void initialize() {
		nodeMapper = new ComponentMapper<Node>(Node.class, world.getEntityManager());
		transformMapper = new ComponentMapper<Transform>(Transform.class, world.getEntityManager());
		localTransformMapper = new ComponentMapper<LocalTransform>(LocalTransform.class, world.getEntityManager());
	}

	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		for (int i = 0, s = rootNode.size(); s > i; i++) {
			processEntity(rootNode.get(i));
		}
	}

	private void processEntity(Entity entity) {
		Node node = nodeMapper.get(entity);
		Bag<Entity> children = node.getChildren();

		for (int i = 0, s = children.size(); s > i; i++) {
			Entity e = children.get(i);
			updateWorldTransformation(e);
			processEntity(e);
		}
	}

	private void updateWorldTransformation(Entity e) {
		Node node = nodeMapper.get(e);
		Entity parent = node.getParent();
		Transform parentTransform = transformMapper.get(parent);
		Transform transform = transformMapper.get(e);
		LocalTransform localTransform = localTransformMapper.get(e);

		float currentX = parentTransform.getX() + localTransform.getX();
		float currentY = parentTransform.getY() + localTransform.getY();

		float x = Utils.getRotatedX(currentX, currentY, parentTransform.getX(), parentTransform.getY(), parentTransform.getRotation());
		float y = Utils.getRotatedY(currentX, currentY, parentTransform.getX(), parentTransform.getY(), parentTransform.getRotation());

		transform.setLocation(x, y);
		transform.setRotation(parentTransform.getRotation());
	}

	@Override
	protected boolean checkProcessing() {
		return true;
	}

	@Override
	protected void added(Entity e) {
		Node node = nodeMapper.get(e);
		Entity parent = node.getParent();
		if (parent == null) {
			rootNode.add(e);
		} else {
			Node parentNode = nodeMapper.get(parent);
			parentNode.addChild(e);
		}
	}

	@Override
	protected void removed(Entity e) {
	}

}