package com.gemserk.commons.gdx.box2d;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.physics.box2d.joints.RopeJointDef;

/**
 * Provides an easier way to declare Box2D Bodies.
 * 
 * @author acoppes
 */
public class JointBuilder {
	
	public class DistanceJointBuilder {
		
		private DistanceJointDef distanceJointDef;

		private void reset() {
			// TODO: reuse the same distance Joint by setting default values here..
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

		public DistanceJointBuilder frequencyHz(float frequencyHz) {
			distanceJointDef.frequencyHz = frequencyHz;
			return this;
		}
		
		public DistanceJointBuilder dampingRatio(float dampingRatio) {
			distanceJointDef.dampingRatio = dampingRatio;
			return this;
		}

		public Joint build() {
			return world.createJoint(distanceJointDef);
		}
		
	}
	
	public class RopeJointBuilder {
		
		private RopeJointDef ropeJointDef;

		private void reset() {
			// TODO: reuse the same distance Joint by setting default values here..
			ropeJointDef = new RopeJointDef();
		}
		
		public RopeJointBuilder bodyA(Body bodyA) {
			ropeJointDef.bodyA = bodyA;
			return this;
		}

		public RopeJointBuilder bodyB(Body bodyB) {
			ropeJointDef.bodyB = bodyB;
			return this;
		}
		
		public RopeJointBuilder bodyA(Body bodyA, float lx, float ly) {
			ropeJointDef.bodyA = bodyA;
			ropeJointDef.localAnchorA.set(lx, ly);
			return this;
		}
		
		public RopeJointBuilder bodyB(Body bodyB, float lx, float ly) {
			ropeJointDef.bodyB = bodyB;
			ropeJointDef.localAnchorB.set(lx, ly);
			return this;
		}
		
		public RopeJointBuilder collideConnected(boolean collideConnected) {
			ropeJointDef.collideConnected = collideConnected;
			return this;
		}
		
		public RopeJointBuilder maxLength(float maxLength) {
			ropeJointDef.maxLength = maxLength;
			return this;
		}
		
		public Joint build() {
			return world.createJoint(ropeJointDef);
		}
		
	}

	private final World world;
	private DistanceJointBuilder distanceJointBuilder;
	private RopeJointBuilder ropeJointBuilder;
	
	public JointBuilder(World world) {
		this.world = world;
		this.distanceJointBuilder = new DistanceJointBuilder();
		this.ropeJointBuilder = new RopeJointBuilder();
	}
	
	public DistanceJointBuilder distanceJoint() {
		distanceJointBuilder.reset();
		return distanceJointBuilder;
	}
	
	public RopeJointBuilder ropeJointBuilder() {
		ropeJointBuilder.reset();
		return ropeJointBuilder;
	}

}