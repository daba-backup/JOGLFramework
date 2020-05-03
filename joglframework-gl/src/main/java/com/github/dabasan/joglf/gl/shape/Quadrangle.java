package com.github.dabasan.joglf.gl.shape;

/**
 * Quadrangle
 * 
 * @author Daba
 *
 */
public class Quadrangle {
	private final Vertex3D[] vertices;

	public Quadrangle() {
		vertices = new Vertex3D[4];
		for (int i = 0; i < 4; i++) {
			vertices[i] = new Vertex3D();
		}
	}

	public void SetVertex(int index, Vertex3D v) {
		vertices[index] = v;
	}

	public Vertex3D GetVertex(int index) {
		return vertices[index];
	}
	public Vertex3D[] GetVertices() {
		return vertices;
	}
}
