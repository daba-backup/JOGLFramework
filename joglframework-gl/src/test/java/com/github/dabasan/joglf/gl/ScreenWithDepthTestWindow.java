package com.github.dabasan.joglf.gl;

import com.github.dabasan.basis.vector.VectorFunctions;
import com.github.dabasan.joglf.gl.draw.DrawFunctions2D;
import com.github.dabasan.joglf.gl.front.CameraFront;
import com.github.dabasan.joglf.gl.model.Model3DFunctions;
import com.github.dabasan.joglf.gl.shader.ShaderProgram;
import com.github.dabasan.joglf.gl.util.screen.ScreenWithDepth;
import com.github.dabasan.joglf.gl.window.JOGLFWindow;

class ScreenWithDepthTestWindow extends JOGLFWindow {
	private ScreenWithDepth screen;
	private int model_handle;

	private ShaderProgram draw_depth;

	@Override
	public void Init() {
		screen = new ScreenWithDepth(1024, 1024);
		model_handle = Model3DFunctions.LoadModel("./Data/Model/BD1/Cube/cube.bd1");

		draw_depth = new ShaderProgram("draw_depth",
				"./Data/Shader/330/test/draw_depth/vshader.glsl",
				"./Data/Shader/330/test/draw_depth/fshader.glsl");
	}

	@Override
	public void Update() {
		CameraFront.SetCameraPositionAndTarget_UpVecY(VectorFunctions.VGet(30.0f, 30.0f, 30.0f),
				VectorFunctions.VGet(0.0f, 0.0f, 0.0f));
	}

	@Override
	public void Draw() {
		screen.Enable();
		screen.Clear();
		Model3DFunctions.DrawModel(model_handle);
		screen.Disable();

		this.Fit();
		draw_depth.Enable();
		screen.BindDepthTexture();
		DrawFunctions2D.TransferFullscreenQuad();
		screen.UnbindDepthTexture();
		draw_depth.Disable();
	}
}
