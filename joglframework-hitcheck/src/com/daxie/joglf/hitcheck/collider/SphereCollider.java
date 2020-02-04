package com.daxie.joglf.hitcheck.collider;

import com.daxie.basis.vector.Vector;
import com.daxie.basis.vector.VectorFunctions;

/**
 * Sphere collider
 * @author Daba
 *
 */
public class SphereCollider extends Collider{
	private Vector c;
	private float r;
	
	public SphereCollider() {
		this.SetColliderShape(ColliderShape.SPHERE);
		
		c=VectorFunctions.VGet(0.0f, 0.0f, 0.0f);
		r=1.0f;
	}
	
	public void SetSphere(Vector c,float r) {
		this.c=c;
		this.r=r;
	}
	public Vector GetCenter() {
		return new Vector(c);
	}
	public float GetRadius() {
		return r;
	}
	
	public void CollideWith(Collider collider) {
		ColliderShape shape=collider.GetColliderShape();
		
		switch(shape) {
		case NONE:
			break;
		case BOX:
			this.CollideWithBox((BoxCollider)collider);
			break;
		case SPHERE:
			this.CollideWithSphere((SphereCollider)collider);
			break;
		case CAPSULE:
			this.CollideWithCapsule((CapsuleCollider)collider);
			break;
		default:
			break;
		}
	}
	private void CollideWithBox(BoxCollider collider) {
		
	}
	private void CollideWithSphere(SphereCollider collider) {
		
	}
	private void CollideWithCapsule(CapsuleCollider collider) {
		
	}
}
