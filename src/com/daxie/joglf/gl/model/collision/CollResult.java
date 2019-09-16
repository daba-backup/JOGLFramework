package com.daxie.joglf.gl.model.collision;

import com.daxie.basis.vector.Vector;

/**
 * Collision result
 * @author Daba
 *
 */
public class CollResult {
	private boolean hit_flag;
	private Vector hit_position;
	private Vector[] vertices;
	private Vector face_normal;
	
	public CollResult() {
		hit_flag=false;
		hit_position=new Vector();
		
		vertices=new Vector[3];
		for(int i=0;i<3;i++) {
			vertices[i]=new Vector();
		}
		
		face_normal=new Vector();
	}
	public CollResult(CollResult c) {
		hit_flag=c.GetHitFlag();
		hit_position=c.GetHitPosition();
		vertices=c.GetVertices();
		face_normal=c.GetFaceNormal();
	}
	
	public boolean GetHitFlag() {
		return hit_flag;
	}
	public Vector GetHitPosition() {
		return new Vector(hit_position);
	}
	public Vector[] GetVertices() {
		Vector[] ret=new Vector[3];
		
		for(int i=0;i<3;i++) {
			ret[i]=new Vector(vertices[i]);
		}
		
		return ret;
	}
	public Vector GetFaceNormal() {
		return new Vector(face_normal);
	}
	
	public void SetHitFlag(boolean hit_flag) {
		this.hit_flag=hit_flag;
	}
	public void SetHitPosition(Vector hit_position) {
		this.hit_position=hit_position;
	}
	public void SetVertex(int index,Vector v) {
		vertices[index]=v;
	}
	public void SetFaceNormal(Vector face_normal) {
		this.face_normal=face_normal;
	}
}
