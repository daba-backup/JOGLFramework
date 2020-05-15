package com.github.dabasan.joglf.gl;

import com.github.dabasan.basis.matrix.Matrix;
import com.github.dabasan.basis.matrix.MatrixFunctions;
import com.github.dabasan.basis.vector.Vector;
import com.github.dabasan.basis.vector.VectorFunctions;
import com.github.dabasan.joglf.gl.front.CameraFront;
import com.github.dabasan.joglf.gl.model.Model3DFunctions;
import com.github.dabasan.joglf.gl.window.JOGLFWindow;
import com.github.dabasan.tool.MathFunctions;

class LightingTestWindow extends JOGLFWindow {
	private int model_handle;
	private Vector camera_position;

	@Override
	public void Init() {
		model_handle = Model3DFunctions.LoadModel("./Data/Model/BD1/map2/temp.bd1");
		Model3DFunctions.RescaleModel(model_handle, VectorFunctions.VGet(0.1f, 0.1f, 0.1f));

		camera_position = VectorFunctions.VGet(50.0f, 50.0f, 50.0f);
	}

	@Override
	public void Update() {
		final Matrix rot_y = MatrixFunctions.MGetRotY(MathFunctions.DegToRad(0.5f));
		camera_position = MatrixFunctions.VTransform(camera_position, rot_y);

		CameraFront.SetCameraPositionAndTarget_UpVecY(camera_position,
				VectorFunctions.VGet(0.0f, 0.0f, 0.0f));
	}

	@Override
	public void Draw() {
		Model3DFunctions.DrawModel(model_handle);
	}
}
