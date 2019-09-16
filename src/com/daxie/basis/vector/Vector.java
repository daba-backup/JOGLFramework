package com.daxie.basis.vector;

/**
 * 3D vector
 * @author Daba
 *
 */
public class Vector {
	private float x;
	private float y;
	private float z;
	
	public Vector() {
		x=0.0f;
		y=0.0f;
		z=0.0f;
	}
	public Vector(float x,float y,float z) {
		this.x=x;
		this.y=y;
		this.z=z;
	}
	/**
	 * Used to duplicate a vector.
	 * @param v Original vector
	 */
	public Vector(Vector v) {
		this.x=v.GetX();
		this.y=v.GetY();
		this.z=v.GetZ();
	}
	
	@Override
	public String toString() {
		return "("+x+","+y+","+z+")";
	}
	
	public void SetX(float x) {
		this.x=x;
	}
	public void SetY(float y) {
		this.y=y;
	}
	public void SetZ(float z) {
		this.z=z;
	}
	public void SetVector(float x,float y,float z) {
		this.x=x;
		this.y=y;
		this.z=z;
	}
	public float GetX() {
		return x;
	}
	public float GetY() {
		return y;
	}
	public float GetZ() {
		return z;
	}
}
