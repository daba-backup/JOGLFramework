package com.github.dabasan.joglf.gl.shape;

import com.github.dabasan.basis.coloru8.ColorU8;
import com.github.dabasan.basis.coloru8.ColorU8Functions;
import com.github.dabasan.basis.vector.Vector;
import com.github.dabasan.basis.vector.VectorFunctions;

/**
 * Sphere
 * @author Daba
 *
 */
public class Sphere {
	private Vector center;
	private float radius;
	private int slice_num;
	private int stack_num;
	private ColorU8 color;
	
	public Sphere() {
		center=VectorFunctions.VGet(0.0f, 0.0f, 0.0f);
		radius=10.0f;
		slice_num=16;
		stack_num=16;
		color=ColorU8Functions.GetColorU8(1.0f, 1.0f, 1.0f, 1.0f);
	}
	
	public void SetCenter(Vector center) {
		this.center=center;
	}
	public void SetRadius(float radius) {
		this.radius=radius;
	}
	public void SetSliceNum(int slice_num) {
		this.slice_num=slice_num;
	}
	public void SetStackNum(int stack_num) {
		this.stack_num=stack_num;
	}
	public void SetColor(ColorU8 color) {
		this.color=color;
	}
	
	public Vector GetCenter() {
		return new Vector(center);
	}
	public float GetRadius() {
		return radius;
	}
	public int GetSliceNum() {
		return slice_num;
	}
	public int GetStackNum() {
		return stack_num;
	}
	public ColorU8 GetColor() {
		return new ColorU8(color);
	}
}
