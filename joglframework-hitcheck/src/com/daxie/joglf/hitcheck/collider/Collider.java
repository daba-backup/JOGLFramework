package com.daxie.joglf.hitcheck.collider;

/**
 * Collider
 * @author Daba
 *
 */
public class Collider {
	private ColliderShape shape;
	
	public Collider() {
		shape=ColliderShape.NONE;
	}
	
	protected void SetColliderShape(ColliderShape shape) {
		this.shape=shape;
	}
	
	public ColliderShape GetColliderShape() {
		return shape;
	}
}
