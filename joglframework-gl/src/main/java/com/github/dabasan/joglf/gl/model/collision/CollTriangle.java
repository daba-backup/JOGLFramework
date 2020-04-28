package com.github.dabasan.joglf.gl.model.collision;

import com.daxie.basis.vector.Vector;

/**
 * Triangle for collision info
 * @author Daba
 *
 */
public class CollTriangle {
	private Vector[] vertices;
	private Vector face_normal;
	
	public CollTriangle() {
		vertices=new Vector[3];
		for(int i=0;i<3;i++) {
			vertices[i]=new Vector();
		}
		face_normal=new Vector();
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
	
	public void SetVertex(int index,Vector v) {
		vertices[index]=v;
	}
	public void SetFaceNormal(Vector face_normal) {
		this.face_normal=face_normal;
	}
}
