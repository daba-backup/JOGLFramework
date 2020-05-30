package com.github.dabasan.joglf.gl;

import com.github.dabasan.joglf.gl.shader.ShaderFunctions;
import com.github.dabasan.joglf.gl.shader.ShaderProgram;
import com.github.dabasan.joglf.gl.window.JOGLFWindow;

class SharedProgramTestWindow extends JOGLFWindow {
	private boolean create_program_flag;
	private ShaderProgram program;

	public SharedProgramTestWindow(boolean create_program_flag) {
		this.create_program_flag = create_program_flag;
	}

	@Override
	public void Init() {
		if (create_program_flag == true) {
			program = new ShaderProgram("test", "./Data/Shader/330/test/vshader.glsl",
					"./Data/Shader/330/test/fshader.glsl");
		} else {
			program = new ShaderProgram("test");

			int id = ShaderFunctions.GetProgramID("test");
			System.out.println(id);
		}

		program.Enable();
		program.Disable();
	}
}
