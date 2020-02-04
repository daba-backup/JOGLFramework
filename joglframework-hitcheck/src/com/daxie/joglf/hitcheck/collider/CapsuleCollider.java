package com.daxie.joglf.hitcheck.collider;

import com.daxie.basis.vector.Vector;
import com.daxie.basis.vector.VectorFunctions;

/**
 * Capsule collider
 * @author Daba
 *
 */
public class CapsuleCollider extends Collider{
	private Vector[] p;
	private float r;
	
	public CapsuleCollider() {
		this.SetColliderShape(ColliderShape.CAPSULE);
		
		p=new Vector[2];
		p[0]=VectorFunctions.VGet(0.0f, 1.0f, 0.0f);
		p[1]=VectorFunctions.VGet(0.0f, -1.0f, 0.0f);
		
		r=1.0f;
	}
	
	public void SetCapsule(Vector[] p,float r) {
		this.p=p;
		this.r=r;
	}
	public Vector[] GetPoints() {
		Vector[] ret=new Vector[2];
		
		for(int i=0;i<2;i++) {
			ret[i]=new Vector(p[i]);
		}
		
		return ret;
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
