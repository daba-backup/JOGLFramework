package com.github.dabasan.joglf.gl.shape;

import com.github.dabasan.basis.coloru8.ColorU8;
import com.github.dabasan.basis.coloru8.ColorU8Functions;
import com.github.dabasan.basis.vector.Vector;
import com.github.dabasan.basis.vector.VectorFunctions;

/**
 * Vertex
 * @author Daba
 *
 */
public class Vertex3D {
	private Vector pos;
	private Vector norm;
	private ColorU8 dif;
	private ColorU8 spc;
	private float u;
	private float v;
	
	public Vertex3D() {
		pos=VectorFunctions.VGet(0.0f, 0.0f, 0.0f);
		norm=VectorFunctions.VGet(0.0f, 1.0f, 0.0f);
		dif=ColorU8Functions.GetColorU8(1.0f, 1.0f, 1.0f, 1.0f);
		spc=ColorU8Functions.GetColorU8(1.0f, 1.0f, 1.0f, 1.0f);
		u=0.0f;
		v=0.0f;
	}
	public Vertex3D(Vector pos,Vector norm,ColorU8 dif,ColorU8 spc,float u,float v) {
		this.pos=pos;
		this.norm=norm;
		this.dif=dif;
		this.spc=spc;
		this.u=u;
		this.v=v;
	}
	public Vertex3D(Vertex3D v) {
		this.pos=v.GetPos();
		this.norm=v.GetNorm();
		this.dif=v.GetDif();
		this.spc=v.GetSpc();
		this.u=v.GetU();
		this.v=v.GetV();
	}
	
	public void SetPos(Vector pos) {
		this.pos=pos;
	}
	public void SetNorm(Vector norm) {
		this.norm=norm;
	}
	public void SetDif(ColorU8 dif) {
		this.dif=dif;
	}
	public void SetSpc(ColorU8 spc) {
		this.spc=spc;
	}
	public void SetU(float u) {
		this.u=u;
	}
	public void SetV(float v) {
		this.v=v;
	}
	
	public Vector GetPos() {
		return new Vector(pos);
	}
	public Vector GetNorm() {
		return new Vector(norm);
	}
	public ColorU8 GetDif() {
		return new ColorU8(dif);
	}
	public ColorU8 GetSpc() {
		return new ColorU8(spc);
	}
	public float GetU() {
		return u;
	}
	public float GetV() {
		return v;
	}
}
