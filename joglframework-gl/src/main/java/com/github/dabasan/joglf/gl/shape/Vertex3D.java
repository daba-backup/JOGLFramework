package com.github.dabasan.joglf.gl.shape;

import com.daxie.basis.coloru8.ColorU8;
import com.daxie.basis.vector.Vector;

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
		pos=new Vector();
		norm=new Vector();
		dif=new ColorU8();
		spc=new ColorU8();
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
