package com.github.dabasan.joglf.gl;

import com.github.dabasan.joglf.gl.draw.DrawFunctions3D;
import com.github.dabasan.joglf.gl.model.Model3DFunctions;
import com.github.dabasan.joglf.gl.util.screen.Screen;
import com.github.dabasan.joglf.gl.window.JOGLFWindow;

class ScreenTestWindow extends JOGLFWindow {
	private Screen screen;
	private int model_handle;

	@Override
	public void Init() {
		screen = new Screen(1024, 1024);
		model_handle = Model3DFunctions.LoadModel("./Data/Model/OBJ/Teapot/teapot.obj");
	}

	@Override
	public void Draw() {
		screen.Enable();
		screen.Clear();
		Model3DFunctions.DrawModel(model_handle);
		DrawFunctions3D.DrawAxes(100.0f);
		screen.Disable();

		screen.Draw(0, 0, this.GetWidth(), this.GetHeight());
	}

	@Override
	public void Dispose() {
		screen.Dispose();
	}
}
