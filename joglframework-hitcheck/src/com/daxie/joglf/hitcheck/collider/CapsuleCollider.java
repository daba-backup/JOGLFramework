package com.daxie.joglf.hitcheck.collider;

import com.daxie.basis.vector.Vector;
import com.daxie.basis.vector.VectorFunctions;

/**
 * Capsule collider
 * @author Daba
 *
 */
public class CapsuleCollider extends Collider{
	private Vector[] positions;
	private float radius;
	
	public CapsuleCollider() {
		this.SetColliderShape(ColliderShape.CAPSULE);
		
		positions=new Vector[2];
		positions[0]=VectorFunctions.VGet(0.0f, 1.0f, 0.0f);
		positions[1]=VectorFunctions.VGet(0.0f, -1.0f, 0.0f);
		
		radius=1.0f;
	}
	
	public void SetCapsule(Vector[] positions,float radius) {
		this.positions=positions;
		this.radius=radius;
	}
	public Vector[] GetPositions() {
		Vector[] ret=new Vector[2];
		
		for(int i=0;i<2;i++) {
			ret[i]=new Vector(positions[i]);
		}
		
		return ret;
	}
	public float GetRadius() {
		return radius;
	}
	
	@Override
	public boolean CollideWith(Collider collider) {
		boolean ret;
		ColliderShape shape=collider.GetColliderShape();
		
		switch(shape) {
		case TRIANGLE:
			ret=this.CollideWithTriangle((TriangleCollider)collider);
			break;
		case BOX:
			ret=this.CollideWithBox((BoxCollider)collider);
			break;
		case SPHERE:
			ret=this.CollideWithSphere((SphereCollider)collider);
			break;
		case CAPSULE:
			ret=this.CollideWithCapsule((CapsuleCollider)collider);
			break;
		default:
			ret=false;
			break;
		}
		
		return ret;
	}
	private boolean CollideWithTriangle(TriangleCollider collider) {
		return false;
	}
	private boolean CollideWithBox(BoxCollider collider) {
		return false;
	}
	private boolean CollideWithSphere(SphereCollider collider) {
		return false;
	}
	private boolean CollideWithCapsule(CapsuleCollider collider) {
		return false;
	}
}
