package com.daxie.joglf.hitcheck.collider;

import com.daxie.basis.vector.Vector;
import com.daxie.basis.vector.VectorFunctions;

/**
 * Box collider
 * @author Daba
 *
 */
public class BoxCollider extends Collider{
	private Vector c;
	private Vector[] u;
	private Vector e;
	
	public BoxCollider() {
		this.SetColliderShape(ColliderShape.BOX);
		
		c=VectorFunctions.VGet(0.0f, 0.0f, 0.0f);
		
		u=new Vector[3];
		u[0]=VectorFunctions.VGet(1.0f, 0.0f, 0.0f);
		u[1]=VectorFunctions.VGet(0.0f, 1.0f, 0.0f);
		u[2]=VectorFunctions.VGet(0.0f, 0.0f, 1.0f);
		
		e=VectorFunctions.VGet(1.0f, 1.0f, 1.0f);
	}
	
	public void SetBox(Vector c,Vector[] u,Vector e) {
		this.c=c;
		this.u=u;
		this.e=e;
	}
	public Vector GetCenter() {
		return new Vector(c);
	}
	public Vector[] GetAxes() {
		Vector[] ret=new Vector[3];
		
		for(int i=0;i<3;i++) {
			ret[i]=new Vector(u[i]);
		}
		
		return ret;
	}
	public Vector GetEdgeHalfLength() {
		return new Vector(e);
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
