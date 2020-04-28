package com.github.dabasan.joglf.gl.shape;

import java.awt.Point;

import com.daxie.basis.coloru8.ColorU8;
import com.daxie.basis.coloru8.ColorU8Functions;

/**
 * 2D vertex
 * @author Daba
 *
 */
public class Vertex2D {
	private Point point;
	private ColorU8 color;
	
	public Vertex2D() {
		point=new Point();
		color=ColorU8Functions.GetColorU8(1.0f, 1.0f, 1.0f, 1.0f);
	}
	public Vertex2D(Point point,ColorU8 color) {
		this.point=point;
		this.color=color;
	}
	public Vertex2D(Vertex2D v) {
		
	}
	
	public void SetPoint(Point point) {
		this.point=point;
	}
	public void SetColor(ColorU8 color) {
		this.color=color;
	}
	
	public Point GetPoint() {
		return new Point(point);
	}
	public ColorU8 GetColor() {
		return new ColorU8(color);
	}
}
