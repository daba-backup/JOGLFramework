package com.github.dabasan.joglf.gl;

import static com.github.dabasan.basis.matrix.MatrixFunctions.*;
import static com.github.dabasan.basis.vector.VectorFunctions.*;

import com.github.dabasan.basis.matrix.Matrix;
import com.github.dabasan.basis.vector.Vector;
import com.github.dabasan.joglf.gl.front.CameraFront;
import com.github.dabasan.joglf.gl.model.Model3DFunctions;
import com.github.dabasan.joglf.gl.window.JOGLFWindow;
import com.github.dabasan.tool.MathFunctions;

class DrawModelTestWindow extends JOGLFWindow {
	private int model_handle;
	private Vector camera_position;

	@Override
	public void Init() {
		model_handle = Model3DFunctions.LoadModel("./Data/Model/OBJ/Plane/plane.obj");
		camera_position = VGet(35.0f, 35.0f, 35.0f);
	}

	@Override
	public void Update() {
		Matrix rot_y = MGetRotY(MathFunctions.DegToRad(0.5f));
		camera_position = VTransform(camera_position, rot_y);
		CameraFront.SetCameraPositionAndTarget_UpVecY(camera_position, VGet(0.0f, 0.0f, 0.0f));
	}

	@Override
	public void Draw() {
		Model3DFunctions.DrawModel(model_handle);
	}
}
