package com.github.dabasan.joglf.gl;

import com.github.dabasan.basis.vector.VectorFunctions;
import com.github.dabasan.joglf.gl.front.CameraFront;
import com.github.dabasan.joglf.gl.model.Model3DFunctions;
import com.github.dabasan.joglf.gl.window.JOGLFWindow;

class DrawModelTestWindow extends JOGLFWindow {
	private int model_handle;

	@Override
	public void Init() {
		model_handle = Model3DFunctions.LoadModel("./Data/Model/BD1/map2/temp.bd1");
		Model3DFunctions.RescaleModel(model_handle, VectorFunctions.VGet(0.1f, 0.1f, 0.1f));
	}

	@Override
	public void Update() {
		CameraFront.SetCameraPositionAndTarget_UpVecY(VectorFunctions.VGet(50.0f, 50.0f, 50.0f),
				VectorFunctions.VGet(0.0f, 0.0f, 0.0f));
	}

	@Override
	public void Draw() {
		Model3DFunctions.DrawModel(model_handle);
	}
}
