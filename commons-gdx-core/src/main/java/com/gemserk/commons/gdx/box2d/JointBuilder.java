package com.gemserk.commons.gdx.box2d;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;

/**
 * Provides an easier way to declare Box2D Bodies.
 * 
 * @author acoppes
 */
public class JointBuilder {
	
	public class DistanceJointBuilder {
		
		private DistanceJointDef distanceJointDef;

		private void reset() {
			distanceJointDef = new DistanceJointDef();
		}
		
		public DistanceJointBuilder bodyA(Body bodyA) {
			distanceJointDef.bodyA = bodyA;
			return this;
		}

		public DistanceJointBuilder bodyB(Body bodyB) {
			distanceJointDef.bodyB = bodyB;
			return this;
		}
		
		public DistanceJointBuilder collideConnected(boolean collideConnected) {
			distanceJointDef.collideConnected = collideConnected;
			return this;
		}
		
		public DistanceJointBuilder length(float length) {
			distanceJointDef.length = length;
			return this;
		}

		public Joint build() {
			return world.createJoint(distanceJointDef);
		}
		
	}

	private final World world;
	private DistanceJointBuilder distanceJointBuilder;
	
	public JointBuilder(World world) {
		this.world = world;
		this.distanceJointBuilder = new DistanceJointBuilder();
	}
	
	public DistanceJointBuilder distanceJoint() {
		distanceJointBuilder.reset();
		return distanceJointBuilder;
	}

}