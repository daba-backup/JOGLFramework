package com.daxie.joglf.gl.shape;

/**
 * Quadrangle
 * @author Daba
 *
 */
public class Quadrangle {
	private Vertex3D[] vertices;
	
	public Quadrangle() {
		vertices=new Vertex3D[4];
	}
	
	public void SetVertex(int index,Vertex3D v) {
		vertices[index]=v;
	}
	
	public Vertex3D GetVertex(int index) {
		return new Vertex3D(vertices[index]);
	}
	public Vertex3D[] GetVertices() {
		Vertex3D[] ret=new Vertex3D[4];
		for(int i=0;i<4;i++)ret[i]=new Vertex3D(vertices[i]);
		
		return ret;
	}
}
