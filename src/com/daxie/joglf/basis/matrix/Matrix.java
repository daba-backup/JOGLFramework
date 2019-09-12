package com.daxie.joglf.basis.matrix;

/**
 * 4 by 4 matrix
 * @author Daba
 *
 */
public class Matrix {
	private float[][] m;
	
	public Matrix() {
		m=new float[4][4];
		
		for(int i=0;i<4;i++) {
			for(int j=0;j<4;j++)m[i][j]=0.0f;
		}
	}
	public Matrix(Matrix mat) {
		this.m=new float[4][4];
		
		for(int i=0;i<4;i++) {
			for(int j=0;j<4;j++)this.m[i][j]=mat.m[i][j];
		}
	}
	
	@Override
	public String toString() {
		String ret="";
		String separator=System.getProperty("line.separator");
		
		for(int i=0;i<4;i++) {
			for(int j=0;j<4;j++)ret+=m[i][j]+" ";
			ret+=separator;
		}
		
		return ret;
	}
	
	public void SetValue(int row,int column,float value) {
		m[row][column]=value;
	}
	public float GetValue(int row,int column) {
		return m[row][column];
	}
}
