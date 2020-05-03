package com.github.dabasan.joglf.gl.shape;

/**
 * Triangle
 * 
 * @author Daba
 *
 */
public class Triangle {
	private final Vertex3D[] vertices;

	public Triangle() {
		vertices = new Vertex3D[3];
		for (int i = 0; i < 3; i++) {
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
