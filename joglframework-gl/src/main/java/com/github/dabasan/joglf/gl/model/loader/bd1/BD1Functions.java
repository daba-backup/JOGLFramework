package com.github.dabasan.joglf.gl.model.loader.bd1;

/**
 * BD1 functions
 * @author Daba
 *
 */
class BD1Functions {
	public static int[] GetFaceCorrespondingVertexIndices(int face_index) {
		int[] ret=new int[4];
		
		switch (face_index) {
		case 0:
			ret[0] = 0;
			ret[1] = 1;
			ret[2] = 2;
			ret[3] = 3;
			break;
		case 1:
			ret[0] = 5;
			ret[1] = 4;
			ret[2] = 7;
			ret[3] = 6;
			break;
		case 2:
			ret[0] = 1;
			ret[1] = 0;
			ret[2] = 4;
			ret[3] = 5;
			break;
		case 3:
			ret[0] = 2;
			ret[1] = 1;
			ret[2] = 5;
			ret[3] = 6;
			break;
		case 4:
			ret[0] = 3;
			ret[1] = 2;
			ret[2] = 6;
			ret[3] = 7;
			break;
		case 5:
			ret[0] = 0;
			ret[1] = 3;
			ret[2] = 7;
			ret[3] = 4;
			break;
		default:
			for (int i=0; i<4; i++)ret[i] = 0;
			break;
		}
		
		return ret;
	}
	public static int[] GetFaceCorrespondingUVIndices(int face_index) {
		int[] ret=new int[4];
		
		switch (face_index) {
		case 0:
			ret[0] = 3;
			ret[1] = 2;
			ret[2] = 1;
			ret[3] = 0;
			break;
		case 1:
			ret[0] = 7;
			ret[1] = 6;
			ret[2] = 5;
			ret[3] = 4;
			break;
		case 2:
			ret[0] = 9;
			ret[1] = 8;
			ret[2] = 11;
			ret[3] = 10;
			break;
		case 3:
			ret[0] = 13;
			ret[1] = 12;
			ret[2] = 15;
			ret[3] = 14;
			break;
		case 4:
			ret[0] = 17;
			ret[1] = 16;
			ret[2] = 19;
			ret[3] = 18;
			break;
		case 5:
			ret[0] = 21;
			ret[1] = 20;
			ret[2] = 23;
			ret[3] = 22;
			break;
		default:
			for (int i=0; i<4; i++)ret[i] = 0;
			break;
		}
		
		return ret;
	}
}
