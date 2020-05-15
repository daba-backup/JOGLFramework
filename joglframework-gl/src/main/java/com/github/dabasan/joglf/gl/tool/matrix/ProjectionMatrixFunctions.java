package com.github.dabasan.joglf.gl.tool.matrix;

import com.github.dabasan.basis.matrix.Matrix;
import com.github.dabasan.basis.matrix.MatrixFunctions;
import com.github.dabasan.basis.vector.Vector;
import com.github.dabasan.basis.vector.VectorFunctions;

/**
 * Provides methods for projection transformation.
 * 
 * @author Daba
 *
 */
public class ProjectionMatrixFunctions {
	public static Matrix GetOrthogonalMatrix(float left, float right, float bottom, float top,
			float near, float far) {
		Matrix orthogonal_matrix;

		final Vector vec_translate = VectorFunctions.VGet(-(right + left) / 2, -(bottom + top) / 2,
				(far + near) / 2);
		final Vector vec_scale = VectorFunctions.VGet(2 / (right - left), 2 / (top - bottom),
				-2 / (far - near));

		final Matrix mat_translate = MatrixFunctions.MGetTranslate(vec_translate);
		final Matrix mat_scale = MatrixFunctions.MGetScale(vec_scale);

		orthogonal_matrix = MatrixFunctions.MMult(mat_scale, mat_translate);

		return orthogonal_matrix;
	}
	public static Matrix GetPerspectiveMatrix(float left, float right, float bottom, float top,
			float near, float far) {
		final Matrix perspective_matrix = new Matrix();

		perspective_matrix.SetValue(0, 0, 2 * near / (right - left));
		perspective_matrix.SetValue(0, 1, 0);
		perspective_matrix.SetValue(0, 2, (right + left) / (right - left));
		perspective_matrix.SetValue(0, 3, 0);
		perspective_matrix.SetValue(1, 0, 0);
		perspective_matrix.SetValue(1, 1, 2 * near / (top - bottom));
		perspective_matrix.SetValue(1, 2, (top * bottom) / (top - bottom));
		perspective_matrix.SetValue(1, 3, 0);
		perspective_matrix.SetValue(2, 0, 0);
		perspective_matrix.SetValue(2, 1, 0);
		perspective_matrix.SetValue(2, 2, -(far + near) / (far - near));
		perspective_matrix.SetValue(2, 3, -2 * far * near / (far - near));
		perspective_matrix.SetValue(3, 0, 0);
		perspective_matrix.SetValue(3, 1, 0);
		perspective_matrix.SetValue(3, 2, -1);
		perspective_matrix.SetValue(3, 3, 0);

		return perspective_matrix;
	}
	public static Matrix GetPerspectiveMatrix(float fov, float aspect, float near, float far) {
		final Matrix perspective_matrix = new Matrix();

		final float f = 1 / (float) Math.tan(fov / 2);

		perspective_matrix.SetValue(0, 0, f / aspect);
		perspective_matrix.SetValue(0, 1, 0);
		perspective_matrix.SetValue(0, 2, 0);
		perspective_matrix.SetValue(0, 3, 0);
		perspective_matrix.SetValue(1, 0, 0);
		perspective_matrix.SetValue(1, 1, f);
		perspective_matrix.SetValue(1, 2, 0);
		perspective_matrix.SetValue(1, 3, 0);
		perspective_matrix.SetValue(2, 0, 0);
		perspective_matrix.SetValue(2, 1, 0);
		perspective_matrix.SetValue(2, 2, -(far + near) / (far - near));
		perspective_matrix.SetValue(2, 3, -2 * far * near / (far - near));
		perspective_matrix.SetValue(3, 0, 0);
		perspective_matrix.SetValue(3, 1, 0);
		perspective_matrix.SetValue(3, 2, -1);
		perspective_matrix.SetValue(3, 3, 0);

		return perspective_matrix;
	}
}
