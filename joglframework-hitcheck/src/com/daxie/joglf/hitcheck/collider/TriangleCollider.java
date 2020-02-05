package com.daxie.joglf.hitcheck.collider;

import com.daxie.basis.vector.Vector;
import com.daxie.basis.vector.VectorFunctions;

/**
 * Triangle collider
 * @author Daba
 *
 */
public class TriangleCollider extends Collider{
	private Vector[] vertices;
	
	public TriangleCollider() {
		this.SetColliderShape(ColliderShape.TRIANGLE);
		
		vertices=new Vector[3];
		vertices[0]=VectorFunctions.VGet(0.0f, 0.0f, -1.0f);
		vertices[1]=VectorFunctions.VGet(-1.0f, 0.0f, 1.0f);
		vertices[2]=VectorFunctions.VGet(1.0f, 0.0f, 1.0f);
	}
	
	public void SetTriangle(Vector[] vertices) {
		this.vertices=vertices;
	}
	public Vector[] GetVertices() {
		Vector[] ret=new Vector[3];
		
		for(int i=0;i<3;i++) {
			ret[i]=new Vector(vertices[i]);
		}
		
		return ret;
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
