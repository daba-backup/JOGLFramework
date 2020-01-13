package com.daxie.joglf.hitcheck.tool;

import com.daxie.basis.vector.Vector;
import com.daxie.basis.vector.VectorFunctions;

/**
 * Provides functions to handle segments.
 * @author Daba
 *
 */
public class SegmentFunctions {
	public static float Clamp01(float t) {
		float temp;
		
		if(t<0.0f)temp=0.0f;
		else if(t>1.0f)temp=1.0f;
		else temp=t;
		
		return temp;
	}
	
	public static Vector GetSegmentPosition(Vector segment_pos_1,Vector segment_pos_2,float t) {
		Vector ret=new Vector();
		
		Vector segment_direction_vector;
		segment_direction_vector=VectorFunctions.VSub(segment_pos_2, segment_pos_1);
		
		ret=VectorFunctions.VAdd(segment_pos_1, VectorFunctions.VScale(segment_direction_vector, t));
		
		return ret;
	}
}
