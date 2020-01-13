package com.daxie.joglf.gl.shape;

import java.awt.Point;

import com.daxie.basis.coloru8.ColorU8;
import com.daxie.basis.coloru8.ColorU8Functions;

/**
 * Circle
 * @author Daba
 *
 */
public class Circle2D {
	private Point center;
	private int radius;
	private ColorU8 color;
	private int div_num;
	
	public Circle2D() {
		center=new Point();
		radius=100;
		color=ColorU8Functions.GetColorU8(1.0f, 1.0f, 1.0f, 1.0f);
		div_num=32;
	}
	
	public void SetCenter(Point center) {
		this.center=center;
	}
	public void SetRadius(int radius) {
		this.radius=radius;
	}
	public void SetColor(ColorU8 color) {
		this.color=color;
	}
	public void SetDivNum(int div_num) {
		this.div_num=div_num;
	}
	
	public Point GetCenter() {
		return new Point(center);
	}
	public int GetRadius() {
		return radius;
	}
	public ColorU8 GetColor() {
		return new ColorU8(color);
	}
	public int GetDivNum() {
		return div_num;
	}
}
