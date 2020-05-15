package com.github.dabasan.joglf.gl;

import com.github.dabasan.joglf.gl.shader.ShaderFunctions;
import com.github.dabasan.joglf.gl.window.JOGLFWindow;

class ShaderCompilationTestWindow extends JOGLFWindow {
	@Override
	public void Init() {
		ShaderFunctions.CreateProgram("phong", "./Data/Shader/330/texture/phong/vshader.glsl",
				"./Data/Shader/330/texture/phong/fshader.glsl");
		ShaderFunctions.CreateProgram("simple_3d", "./Data/Shader/330/simple_3d/vshader.glsl",
				"./Data/Shader/330/simple_3d/fshader.glsl");
	}
}
