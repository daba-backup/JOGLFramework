package com.github.dabasan.joglf.gl;

import static com.github.dabasan.basis.matrix.MatrixFunctions.*;
import static com.github.dabasan.basis.vector.VectorFunctions.*;
import static com.github.dabasan.joglf.gl.wrapper.GLWrapper.*;
import static com.jogamp.opengl.GL.*;

import com.github.dabasan.basis.matrix.Matrix;
import com.github.dabasan.basis.vector.Vector;
import com.github.dabasan.joglf.gl.draw.DrawFunctions3D;
import com.github.dabasan.joglf.gl.front.CameraFront;
import com.github.dabasan.joglf.gl.model.Model3DFunctions;
import com.github.dabasan.joglf.gl.util.screen.Screen;
import com.github.dabasan.joglf.gl.window.JOGLFWindow;
import com.github.dabasan.tool.MathFunctions;

class ScreenTestWindow extends JOGLFWindow {
	private Screen screen;
	private int model_handle;
	private int model_handle_2;
	private Vector camera_position;

	@Override
	public void Init() {
		screen = new Screen(1024, 1024);
		model_handle = Model3DFunctions.LoadModel("./Data/Model/OBJ/Teapot/teapot.obj");
		model_handle_2 = Model3DFunctions.LoadModel("./Data/Model/OBJ/Plane/plane.obj");

		camera_position = VGet(35.0f, 35.0f, 35.0f);

		glDisable(GL_CULL_FACE);
	}

	@Override
	public void Update() {
		Matrix rot_y = MGetRotY(MathFunctions.DegToRad(0.5f));
		camera_position = VTransform(camera_position, rot_y);
		CameraFront.SetCameraPositionAndTarget_UpVecY(camera_position, VGet(0.0f, 0.0f, 0.0f));
	}

	@Override
	public void Draw() {
		screen.Enable();
		screen.Clear();
		Model3DFunctions.DrawModel(model_handle);
		Model3DFunctions.DrawModel(model_handle_2);
		DrawFunctions3D.DrawAxes(100.0f);
		screen.Disable();

		screen.Draw(0, 0, this.GetWidth(), this.GetHeight());
	}

	@Override
	public void Dispose() {
		screen.Dispose();
	}
}
