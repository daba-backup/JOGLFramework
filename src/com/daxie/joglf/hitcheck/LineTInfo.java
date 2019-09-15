package com.daxie.joglf.hitcheck;

/**
 * Used when computing the distance between two lines.
 * @author Daba
 *
 */
public class LineTInfo {
	private float t1;
	private float t2;
	private float distance;
	
	public LineTInfo() {
		t1=0.0f;
		t2=0.0f;
		distance=0.0f;
	}
	public LineTInfo(float t1,float t2,float distance) {
		this.t1=t1;
		this.t2=t2;
		this.distance=distance;
	}
	
	public void SetT1(float t1) {
		this.t1=t1;
	}
	public void SetT2(float t2) {
		this.t2=t2;
	}
	public void SetDistance(float distance) {
		this.distance=distance;
	}
	
	public float GetT1() {
		return t1;
	}
	public float GetT2() {
		return t2;
	}
	public float GetDistance() {
		return distance;
	}
}
