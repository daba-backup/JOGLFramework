package com.daxie.basis.coloru8;

/**
 * Color with transparency
 * @author Daba
 *
 */
public class ColorU8 {
	private float r;
	private float g;
	private float b;
	private float a;
	
	public ColorU8() {
		r=0.0f;
		g=0.0f;
		b=0.0f;
		a=0.0f;
	}
	public ColorU8(float r,float g,float b,float a) {
		this.r=r;
		this.g=g;
		this.b=b;
		this.a=a;
	}
	public ColorU8(ColorU8 c) {
		this.r=c.GetR();
		this.g=c.GetG();
		this.b=c.GetB();
		this.a=c.GetA();
	}
	
	public void SetR(float r) {
		this.r=r;
	}
	public void SetG(float g) {
		this.g=g;
	}
	public void SetB(float b) {
		this.b=b;
	}
	public void SetA(float a) {
		this.a=a;
	}
	public void SetRGBA(float r,float g,float b,float a) {
		this.r=r;
		this.g=g;
		this.b=b;
		this.a=a;
	}
	public float GetR() {
		return r;
	}
	public float GetG() {
		return g;
	}
	public float GetB() {
		return b;
	}
	public float GetA() {
		return a;
	}
}
