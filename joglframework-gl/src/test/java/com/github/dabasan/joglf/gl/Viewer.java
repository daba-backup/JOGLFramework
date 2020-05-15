package com.github.dabasan.joglf.gl;

import com.github.dabasan.basis.vector.VectorFunctions;
import com.github.dabasan.joglf.gl.draw.DrawFunctions3D;
import com.github.dabasan.joglf.gl.input.keyboard.KeyboardEnum;
import com.github.dabasan.joglf.gl.input.mouse.MouseEnum;
import com.github.dabasan.joglf.gl.model.Model3DFunctions;
import com.github.dabasan.joglf.gl.util.camera.FreeCamera;
import com.github.dabasan.joglf.gl.window.JOGLFWindow;

public class Viewer extends JOGLFWindow {
	private FreeCamera camera;

	private int model_handle;
	private int model_handle_2;

	@Override
	public void Init() {
		camera = new FreeCamera();
		camera.SetPosition(VectorFunctions.VGet(50.0f, 50.0f, 50.0f));

		model_handle = Model3DFunctions
				.LoadModel("./Data/Model/BD1/map0/temp.bd1");
		Model3DFunctions.RescaleModel(model_handle,
				VectorFunctions.VGet(0.1f, 0.1f, 0.1f));
		model_handle_2 = Model3DFunctions
				.LoadModel("./Data/Model/OBJ/Teapot/teapot.obj");
	}

	@Override
	public void Update() {
		final int w_pressing_count = this
				.GetKeyboardPressingCount(KeyboardEnum.KEY_W);
		final int s_pressing_count = this
				.GetKeyboardPressingCount(KeyboardEnum.KEY_S);
		final int d_pressing_count = this
				.GetKeyboardPressingCount(KeyboardEnum.KEY_D);
		final int a_pressing_count = this
				.GetKeyboardPressingCount(KeyboardEnum.KEY_A);
		camera.Translate(w_pressing_count, s_pressing_count, d_pressing_count,
				a_pressing_count);

		if (this.GetMousePressingCount(MouseEnum.MOUSE_MIDDLE) > 0) {
			final int diff_x = this.GetCursorDiffX();
			final int diff_y = this.GetCursorDiffY();

			camera.Rotate(diff_x, diff_y);
		}

		camera.Update();
	}

	@Override
	public void Draw() {
		Model3DFunctions.DrawModel(model_handle);
		Model3DFunctions.DrawModel(model_handle_2);
		DrawFunctions3D.DrawAxes(100.0f);
	}
}
