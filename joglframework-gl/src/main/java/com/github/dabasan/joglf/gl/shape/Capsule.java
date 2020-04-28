package com.github.dabasan.joglf.gl.shape;

import com.daxie.basis.coloru8.ColorU8;
import com.daxie.basis.vector.Vector;

/**
 * Capsule
 * @author Daba
 *
 */
public class Capsule {
	private Vector capsule_pos_1;
	private Vector capsule_pos_2;
	private float radius;
	private int slice_num;
	private int stack_num;
	private ColorU8 color;
	
	public void SetCapsulePos1(Vector capsule_pos_1) {
		this.capsule_pos_1=capsule_pos_1;
	}
	public void SetCapsulePos2(Vector capsule_pos_2) {
		this.capsule_pos_2=capsule_pos_2;
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
	
	public Vector GetCapsulePos1() {
		return new Vector(capsule_pos_1);
	}
	public Vector GetCapsulePos2() {
		return new Vector(capsule_pos_2);
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
