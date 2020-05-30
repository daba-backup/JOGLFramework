package com.github.dabasan.joglf.gl;

import com.github.dabasan.joglf.gl.model.Model3DFunctions;
import com.github.dabasan.joglf.gl.window.JOGLFWindow;

class SharedModelTestWindow extends JOGLFWindow {
	private int model_handle;

	public SharedModelTestWindow(int model_handle) {
		this.model_handle = model_handle;
	}

	@Override
	public void Init() {
		if (model_handle < 0) {
			model_handle = Model3DFunctions.LoadModel("./Data/Model/BD1/Cube/cube.bd1");
		}
	}

	@Override
	public void Draw() {
		Model3DFunctions.DrawModel(model_handle);
	}

	public int GetModelHandle() {
		return model_handle;
	}
}
