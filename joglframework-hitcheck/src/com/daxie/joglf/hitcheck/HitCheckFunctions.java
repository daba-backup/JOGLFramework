package com.daxie.joglf.hitcheck;

import com.daxie.basis.vector.Vector;
import com.daxie.basis.vector.VectorFunctions;
import com.daxie.joglf.hitcheck.entity.HitResult;
import com.daxie.joglf.hitcheck.entity.LineTInfo;
import com.daxie.joglf.hitcheck.tool.SegmentFunctions;

/**
 * Provides functions for hitcheck.
 * @author Daba
 *
 */
public class HitCheckFunctions {
	public static float GetSquareDistance_Capsule_Triangle(
			Vector capsule_pos_1,Vector capsule_pos_2,
			Vector triangle_pos_1,Vector triangle_pos_2,Vector triangle_pos_3) {
		float distance_c1c2_t1t2;
		float distance_c1c2_t2t3;
		float distance_c1c2_t3t1;
		HitResult hit_result_c1_t;
		HitResult hit_result_c2_t;
		
		distance_c1c2_t1t2=GetSquareDistance_Segment_Segment(capsule_pos_1, capsule_pos_2, triangle_pos_1,triangle_pos_2);
		distance_c1c2_t2t3=GetSquareDistance_Segment_Segment(capsule_pos_1, capsule_pos_2, triangle_pos_2,triangle_pos_3);
		distance_c1c2_t3t1=GetSquareDistance_Segment_Segment(capsule_pos_1, capsule_pos_2, triangle_pos_3,triangle_pos_1);
		hit_result_c1_t=HitCheck_PerpendicularFromPoint_Triangle(capsule_pos_1, triangle_pos_1, triangle_pos_2, triangle_pos_3);
		hit_result_c2_t=HitCheck_PerpendicularFromPoint_Triangle(capsule_pos_2, triangle_pos_1, triangle_pos_2, triangle_pos_3);
		
		float min_distance=distance_c1c2_t1t2;
		if(distance_c1c2_t2t3<min_distance)min_distance=distance_c1c2_t2t3;
		if(distance_c1c2_t3t1<min_distance)min_distance=distance_c1c2_t3t1;
		if(hit_result_c1_t.GetHitFlag()==true) {
			Vector perpendicular_vector;
			float distance;
			
			perpendicular_vector=VectorFunctions.VSub(hit_result_c1_t.GetHitPosition(),capsule_pos_1);
			distance=VectorFunctions.VSquareSize(perpendicular_vector);
			
			if(distance<min_distance)min_distance=distance;
		}
		if(hit_result_c2_t.GetHitFlag()==true) {
			Vector perpendicular_vector;
			float distance;
			
			perpendicular_vector=VectorFunctions.VSub(hit_result_c2_t.GetHitPosition(),capsule_pos_2);
			distance=VectorFunctions.VSquareSize(perpendicular_vector);
			
			if(distance<min_distance)min_distance=distance;
		}
		
		return min_distance;
	}
	public static LineTInfo GetSquareDistance_Line_Line(
			Vector line1_pos,Vector line1_direction_vector,Vector line2_pos,Vector line2_direction_vector) {
		LineTInfo line_t_info=new LineTInfo();
		
		final float EPSILON=1.0E-6f;
		final float SQUARE_EPSILON=EPSILON*EPSILON;
		
		//If two lines are parallel, 
		//the distance of two lines is equal to the distance between line1_pos and line2.
		Vector cr;
		cr=VectorFunctions.VCross(line1_direction_vector, line2_direction_vector);
		if(VectorFunctions.VSquareSize(cr)<SQUARE_EPSILON) {
			line_t_info=GetSquareDistance_Point_Line(line1_pos, line2_pos, line2_direction_vector);
			return line_t_info;
		}
		
		Vector v1=VectorFunctions.VNorm(line1_direction_vector);
		Vector v2=VectorFunctions.VNorm(line2_direction_vector);
		
		Vector p12=VectorFunctions.VSub(line2_pos, line1_pos);
		
		float dot1=VectorFunctions.VDot(p12, v1);
		float dot2=VectorFunctions.VDot(p12, v2);
		float dotv=VectorFunctions.VDot(v1, v2);
		
		float t1=(dot1-dot2)*dotv/(1.0f-dotv*dotv);
		float t2=(dot2-dot1)*dotv/(dotv*dotv-1.0f);
		
		Vector q1=VectorFunctions.VAdd(line1_pos, VectorFunctions.VScale(v1, t1));
		Vector q2=VectorFunctions.VAdd(line2_pos, VectorFunctions.VScale(v2, t2));
		
		Vector d=VectorFunctions.VSub(q2, q1);
		float distance=VectorFunctions.VSquareSize(d);
		
		line_t_info.SetDistance(distance);
		line_t_info.SetT1(t1);
		line_t_info.SetT2(t2);
		
		return line_t_info;
	}
	public static LineTInfo GetSquareDistance_Point_Line(Vector point,Vector line_point,Vector line_direction_vector) {
		LineTInfo line_t_info=new LineTInfo();
		float distance;
		
		Vector p1,p2,v2;
		Vector p21;
		float t;
		Vector h;
		Vector perpendicular_line;
		
		p1=point;
		p2=line_point;
		v2=line_direction_vector;
		p21=VectorFunctions.VSub(p1, p2);
		
		t=VectorFunctions.VDot(v2, p21)/VectorFunctions.VDot(v2, v2);
		
		h=VectorFunctions.VAdd(p2, VectorFunctions.VScale(v2, t));
		perpendicular_line=VectorFunctions.VSub(h, p1);
		
		distance=VectorFunctions.VSquareSize(perpendicular_line);
		line_t_info.SetDistance(distance);
		line_t_info.SetT1(t);
		
		return line_t_info;
	}
	public static float GetSquareDistance_Point_Segment(Vector point,Vector segment_pos_1,Vector segment_pos_2) {
		float distance;
		
		Vector v1,v2;
		float dot1,dot2;
		Vector vtemp;
		
		v1=VectorFunctions.VSub(point, segment_pos_1);
		v2=VectorFunctions.VSub(segment_pos_2, segment_pos_1);
		
		dot1=VectorFunctions.VDot(v1, v2);
		
		v1=VectorFunctions.VSub(point,segment_pos_2);
		v2=VectorFunctions.VSub(segment_pos_1, segment_pos_2);
		
		dot2=VectorFunctions.VDot(v1, v2);
		
		if(dot1<0.0f) {
			vtemp=VectorFunctions.VSub(point, segment_pos_1);
			distance=VectorFunctions.VSquareSize(vtemp);
		}
		else if(dot2<0.0f) {
			vtemp=VectorFunctions.VSub(point, segment_pos_2);
			distance=VectorFunctions.VSquareSize(vtemp);
		}
		else {
			LineTInfo line_t_info;
			
			vtemp=VectorFunctions.VSub(segment_pos_2, segment_pos_1);
			line_t_info=GetSquareDistance_Point_Line(point, segment_pos_1, vtemp);
			
			distance=line_t_info.GetDistance();
		}
		
		return distance;
	}
	public static float GetSquareDistance_Segment_Segment(
			Vector segment1_pos_1,Vector segment1_pos_2,Vector segment2_pos_1,Vector segment2_pos_2) {
		final float EPSILON=1.0E-6f;
		final float SQUARE_EPSILON=EPSILON*EPSILON;
		
		float distance;
		
		boolean segment1_degenerate_flag=false;
		boolean segment2_degenerate_flag=false;
		
		Vector segment1_length_vector;
		Vector segment2_length_vector;
		segment1_length_vector=VectorFunctions.VSub(segment1_pos_2, segment1_pos_1);
		segment2_length_vector=VectorFunctions.VSub(segment2_pos_2, segment2_pos_1);
		
		if(VectorFunctions.VSquareSize(segment1_length_vector)<SQUARE_EPSILON) {
			segment1_degenerate_flag=true;
		}
		if(VectorFunctions.VSquareSize(segment2_length_vector)<SQUARE_EPSILON) {
			segment2_degenerate_flag=true;
		}
		
		//The distance between two points.
		if(segment1_degenerate_flag==true&&segment2_degenerate_flag==true) {
			distance=VectorFunctions.VSquareSize(VectorFunctions.VSub(segment1_pos_1, segment2_pos_1));
			return distance;
		}
		//The distance between a point and a segment.
		else if(segment1_degenerate_flag==true) {
			distance=GetSquareDistance_Point_Segment(segment1_pos_1, segment2_pos_1, segment2_pos_2);
			return distance;
		}
		else if(segment2_degenerate_flag==true) {
			distance=GetSquareDistance_Point_Segment(segment2_pos_1, segment1_pos_1, segment1_pos_2);
			return distance;
		}
		
		LineTInfo line_t_info;
		
		Vector segment1_direction_vector;
		Vector segment2_direction_vector;
		
		segment1_direction_vector=VectorFunctions.VSub(segment1_pos_2, segment1_pos_1);
		segment2_direction_vector=VectorFunctions.VSub(segment2_pos_2, segment2_pos_1);
		
		line_t_info=GetSquareDistance_Line_Line(
				segment1_pos_1, segment1_direction_vector, segment2_pos_1,segment2_direction_vector);
		
		float t1,t2;
		t1=line_t_info.GetT1();
		t2=line_t_info.GetT2();
			
		if((0.0f<=t1&&t1<=1.0f)&&(0.0f<=t2&&t2<=1.0f)) {
			return line_t_info.GetDistance();
		}
		else {
			Vector p1,p2;
			float t;
			
			p1=segment1_pos_1;
			p2=segment2_pos_1;
			
			//Clamp t1.
			t1=SegmentFunctions.Clamp01(t1);
			p1=SegmentFunctions.GetSegmentPosition(p1, segment1_pos_2, t1);
			line_t_info=GetSquareDistance_Point_Line(p1, segment2_pos_1, segment2_direction_vector);
			
			t=line_t_info.GetT1();
			if(0.0f<=t&&t<=1.0f)return line_t_info.GetDistance();
			
			//Clamp t2.
			t2=SegmentFunctions.Clamp01(t2);
			p2=SegmentFunctions.GetSegmentPosition(p2, segment2_pos_2, t2);
			line_t_info=GetSquareDistance_Point_Line(p2, segment1_pos_1,segment1_direction_vector);
			
			t=line_t_info.GetT1();
			if(0.0f<=t&&t<=1.0f)return line_t_info.GetDistance();
			
			//Otherwise
			distance=VectorFunctions.VSquareSize(VectorFunctions.VSub(p2, p1));
			return distance;
		}
	}
	public static float GetSquareDistance_Sphere_Triangle(
			Vector center,Vector triangle_pos_1,Vector triangle_pos_2,Vector triangle_pos_3) {
		float distance_c_t1t2;
		float distance_c_t2t3;
		float distance_c_t3t1;
		HitResult hit_result_c_t;
		
		distance_c_t1t2=GetSquareDistance_Point_Segment(center, triangle_pos_1, triangle_pos_2);
		distance_c_t2t3=GetSquareDistance_Point_Segment(center, triangle_pos_2, triangle_pos_3);
		distance_c_t3t1=GetSquareDistance_Point_Segment(center, triangle_pos_3, triangle_pos_1);
		hit_result_c_t=HitCheck_PerpendicularFromPoint_Triangle(center, triangle_pos_1, triangle_pos_2, triangle_pos_3);
		
		float min_distance=distance_c_t1t2;
		if(distance_c_t2t3<min_distance)min_distance=distance_c_t2t3;
		if(distance_c_t3t1<min_distance)min_distance=distance_c_t3t1;
		if(hit_result_c_t.GetHitFlag()==true) {
			Vector perpendicular_vector;
			float distance;
			
			perpendicular_vector=VectorFunctions.VSub(hit_result_c_t.GetHitPosition(),center);
			distance=VectorFunctions.VSquareSize(perpendicular_vector);
			
			if(distance<min_distance)min_distance=distance;
		}
		
		return min_distance;
	}
	public static boolean HitCheck_Capsule_Capsule(
			Vector capsule1_pos_1,Vector capsule1_pos_2,float r1,
			Vector capsule2_pos_1,Vector capsule2_pos_2,float r2) {
		boolean ret;
		
		float distance;
		distance=GetSquareDistance_Segment_Segment(
				capsule1_pos_1, capsule1_pos_2, capsule2_pos_1, capsule2_pos_2);
		
		float square_r=(r1+r2)*(r1+r2);
		
		if(distance<square_r)ret=true;
		else ret=false;
		
		return ret;
	}
	public static boolean HitCheck_Capsule_Triangle(
			Vector capsule_pos_1,Vector capsule_pos_2,float r,
			Vector triangle_pos_1,Vector triangle_pos_2,Vector triangle_pos_3) {
		boolean ret;
		
		float min_distance;
		min_distance=GetSquareDistance_Capsule_Triangle(
				capsule_pos_1, capsule_pos_2, triangle_pos_1, triangle_pos_2, triangle_pos_3);
		
		if(min_distance<r*r)ret=true;
		else ret=false;
		
		return ret;
	}
	public static HitResult HitCheck_PerpendicularFromPoint_Triangle(
			Vector point,Vector triangle_pos_1,Vector triangle_pos_2,Vector triangle_pos_3) {
		HitResult hit_result;
		
		Vector normal;
		Vector edge1,edge2;
		edge1=VectorFunctions.VSub(triangle_pos_2, triangle_pos_1);
		edge2=VectorFunctions.VSub(triangle_pos_3, triangle_pos_2);
		normal=VectorFunctions.VCross(edge1, edge2);
		normal=VectorFunctions.VNorm(normal);
		
		final float L=1000.0f;
		Vector segment_pos_1,segment_pos_2;
		Vector line_direction_vector;
		
		line_direction_vector=new Vector(normal);
		line_direction_vector=VectorFunctions.VScale(line_direction_vector, L);
		
		segment_pos_1=VectorFunctions.VAdd(point, VectorFunctions.VScale(line_direction_vector, -1.0f));
		segment_pos_2=VectorFunctions.VAdd(point, line_direction_vector);
		
		hit_result=HitCheck_Segment_Triangle(
				segment_pos_1, segment_pos_2, triangle_pos_1, triangle_pos_2, triangle_pos_3);
		
		return hit_result;
	}
	public static HitResult HitCheck_Segment_Triangle(Vector segment_pos_1,Vector segment_pos_2,
			Vector triangle_pos_1,Vector triangle_pos_2,Vector triangle_pos_3) {
		HitResult hit_result=new HitResult();
		
		final float EPSILON=1.0E-3f;
		
		Vector edge1,edge2,edge3;
		edge1=VectorFunctions.VSub(triangle_pos_2, triangle_pos_1);
		edge2=VectorFunctions.VSub(triangle_pos_3, triangle_pos_2);
		edge3=VectorFunctions.VSub(triangle_pos_1, triangle_pos_3);
		
		Vector normal;
		normal=VectorFunctions.VCross(edge1, edge2);
		normal=VectorFunctions.VNorm(normal);
		
		Vector v1,v2;
		v1=VectorFunctions.VSub(segment_pos_1, triangle_pos_1);
		v2=VectorFunctions.VSub(segment_pos_2, triangle_pos_1);
		
		float dot1,dot2;
		dot1=VectorFunctions.VDot(v1, normal);
		dot2=VectorFunctions.VDot(v2, normal);
		
		float abs_dot1,abs_dot2;
		abs_dot1=Math.abs(dot1);
		abs_dot2=Math.abs(dot2);
		
		float a=abs_dot1/(abs_dot1+abs_dot2);
		
		Vector intersection;
		intersection=VectorFunctions.VAdd(VectorFunctions.VScale(v1, 1.0f-a), VectorFunctions.VScale(v2, a));
		intersection=VectorFunctions.VAdd(triangle_pos_1, intersection);
		
		//If the intersection is on an edge, then the intersection is on the triangle.
		float distance_edge_1,distance_edge_2,distance_edge_3;
		distance_edge_1=GetSquareDistance_Point_Segment(intersection, triangle_pos_1,triangle_pos_2);
		distance_edge_2=GetSquareDistance_Point_Segment(intersection,triangle_pos_2,triangle_pos_3);
		distance_edge_3=GetSquareDistance_Point_Segment(intersection,triangle_pos_3,triangle_pos_1);
		
		final float SQUARE_EPSILON=EPSILON*EPSILON;//Note that this might be an invalid value if EPSILON is too small.
		
		if(distance_edge_1<SQUARE_EPSILON||distance_edge_2<SQUARE_EPSILON||distance_edge_3<SQUARE_EPSILON) {
			hit_result.SetHitFlag(true);
			hit_result.SetHitPosition(intersection);
			
			return hit_result;
		}
		
		//Judge whether the intersection obtained is located on the triangle.
		Vector v3,v4,v5;
		v3=VectorFunctions.VSub(intersection,triangle_pos_2);
		v4=VectorFunctions.VSub(intersection, triangle_pos_3);
		v5=VectorFunctions.VSub(intersection, triangle_pos_1);
		
		Vector n1,n2,n3;
		n1=VectorFunctions.VCross(edge1, v3);
		n2=VectorFunctions.VCross(edge2, v4);
		n3=VectorFunctions.VCross(edge3, v5);
		n1=VectorFunctions.VNorm(n1);
		n2=VectorFunctions.VNorm(n2);
		n3=VectorFunctions.VNorm(n3);
		
		//Check whether calculated normals "n1", "n2" and "n3" are identical to "normal."
		Vector sub_1,sub_2,sub_3;
		sub_1=VectorFunctions.VSub(n1, normal);
		sub_2=VectorFunctions.VSub(n2, normal);
		sub_3=VectorFunctions.VSub(n3, normal);
		
		float sub_1_size,sub_2_size,sub_3_size;
		sub_1_size=VectorFunctions.VSquareSize(sub_1);
		sub_2_size=VectorFunctions.VSquareSize(sub_2);
		sub_3_size=VectorFunctions.VSquareSize(sub_3);
		
		if(sub_1_size<SQUARE_EPSILON&&sub_2_size<SQUARE_EPSILON&&sub_3_size<SQUARE_EPSILON) {
			hit_result.SetHitFlag(true);
			hit_result.SetHitPosition(intersection);
		}
		else {
			hit_result.SetHitFlag(false);
		}
		
		return hit_result;
	}
	public static boolean HitCheck_Sphere_Capsule(
			Vector sphere_center,float sphere_r,
			Vector capsule_pos_1,Vector capsule_pos_2,float capsule_r) {
		boolean ret;
		
		float distance=GetSquareDistance_Point_Segment(sphere_center, capsule_pos_1, capsule_pos_2);
		float square_sum_r=(sphere_r+capsule_r)*(sphere_r+capsule_r);
		
		if(distance<square_sum_r)ret=true;
		else ret=false;
		
		return ret;
	}
	public static boolean HitCheck_Sphere_Sphere(
			Vector sphere1_center,float sphere1_r,Vector sphere2_center,float sphere2_r) {
		boolean ret;
		
		Vector dvector=VectorFunctions.VSub(sphere2_center, sphere1_center);
		float distance=VectorFunctions.VSquareSize(dvector);
		float square_sum_r=(sphere1_r+sphere2_r)*(sphere1_r+sphere2_r);
		
		if(distance<square_sum_r)ret=true;
		else ret=false;
		
		return ret;
	}
	public static boolean HitCheck_Sphere_Triangle(
			Vector center,float r,Vector triangle_pos_1,Vector triangle_pos_2,Vector triangle_pos_3) {
		boolean ret;
		
		float min_distance;
		min_distance=GetSquareDistance_Sphere_Triangle(center, triangle_pos_1, triangle_pos_2, triangle_pos_3);
		
		if(min_distance<r*r)ret=true;
		else ret=false;
		
		return ret;
	}
}
