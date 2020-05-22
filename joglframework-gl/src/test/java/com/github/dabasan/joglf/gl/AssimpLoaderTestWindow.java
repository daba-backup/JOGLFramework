package com.github.dabasan.joglf.gl;

import java.io.IOException;

import com.github.dabasan.joglf.gl.model.FlipVOption;
import com.github.dabasan.joglf.gl.model.Model3DFunctions;
import com.github.dabasan.joglf.gl.model.loader.assimp.AssimpLoader;
import com.github.dabasan.joglf.gl.window.JOGLFWindow;

class AssimpLoaderTestWindow extends JOGLFWindow {
	private int model_handle;

	@Override
	public void Init() {
		try {
			final var bvl = AssimpLoader.LoadModelWithAssimp("./Data/Model/X/model.x");
			model_handle = Model3DFunctions.Associate(bvl, FlipVOption.MUST_FLIP_VERTICALLY);
		} catch (final IOException e) {
			e.printStackTrace();
			this.CloseWindow();

			return;
		}
	}

	@Override
	public void Draw() {
		Model3DFunctions.DrawModel(model_handle);
	}
}
