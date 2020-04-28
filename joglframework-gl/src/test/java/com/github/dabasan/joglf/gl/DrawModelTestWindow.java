package com.github.dabasan.joglf.gl;

import com.daxie.basis.vector.VectorFunctions;
import com.github.dabasan.joglf.gl.model.Model3D;
import com.github.dabasan.joglf.gl.window.JOGLFWindow;

class DrawModelTestWindow extends JOGLFWindow{
	private int model_handle;
	
	@Override
	public void Init() {
		model_handle=Model3D.LoadModel("./Data/Model/BD1/map2/temp.bd1");
		Model3D.RescaleModel(model_handle, VectorFunctions.VGet(0.1f, 0.1f, 0.1f));
	}

	@Override
	public void Draw() {
		Model3D.DrawModel(model_handle);
	}
}
