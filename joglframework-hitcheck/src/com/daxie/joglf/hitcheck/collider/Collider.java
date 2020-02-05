package com.daxie.joglf.hitcheck.collider;

/**
 * Collider
 * @author Daba
 *
 */
public abstract class Collider {
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
	
	public abstract boolean CollideWith(Collider collider);
}
