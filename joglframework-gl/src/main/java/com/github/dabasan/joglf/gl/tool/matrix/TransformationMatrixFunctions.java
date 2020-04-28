package com.github.dabasan.joglf.gl.tool.matrix;

import com.daxie.basis.matrix.Matrix;
import com.daxie.basis.matrix.MatrixFunctions;
import com.daxie.basis.vector.Vector;
import com.daxie.basis.vector.VectorFunctions;

/**
 * Provides methods for view transformation.
 * @author Daba
 *
 */
public class TransformationMatrixFunctions {
	public static Matrix GetViewTransformationMatrix(Vector pos,Vector target,Vector up) {
		Matrix view_transformation_matrix;
		
		Vector vec_translate=VectorFunctions.VScale(pos, -1.0f);
		Matrix t=MatrixFunctions.MGetTranslate(vec_translate);
		
		Vector view_coord_x,view_coord_y,view_coord_z;
		view_coord_z=VectorFunctions.VSub(pos, target);
		view_coord_z=VectorFunctions.VNorm(view_coord_z);
		view_coord_x=VectorFunctions.VCross(up, view_coord_z);
		view_coord_x=VectorFunctions.VNorm(view_coord_x);
		view_coord_y=VectorFunctions.VCross(view_coord_z, view_coord_x);
		
		Matrix r=new Matrix();
		r.SetValue(0, 0, view_coord_x.GetX());
		r.SetValue(0, 1, view_coord_x.GetY());
		r.SetValue(0, 2, view_coord_x.GetZ());
		r.SetValue(0, 3, 0);
		r.SetValue(1, 0, view_coord_y.GetX());
		r.SetValue(1, 1, view_coord_y.GetY());
		r.SetValue(1, 2, view_coord_y.GetZ());
		r.SetValue(1, 3, 0);
		r.SetValue(2, 0, view_coord_z.GetX());
		r.SetValue(2, 1, view_coord_z.GetY());
		r.SetValue(2, 2, view_coord_z.GetZ());
		r.SetValue(2, 3, 0);
		r.SetValue(3, 0, 0);
		r.SetValue(3, 1, 0);
		r.SetValue(3, 2, 0);
		r.SetValue(3, 3, 1);
		
		view_transformation_matrix=MatrixFunctions.MMult(r, t);
		
		return view_transformation_matrix;
	}
}
