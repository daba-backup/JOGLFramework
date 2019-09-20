package com.daxie.joglf.gl.front;

import com.daxie.joglf.gl.wrapper.GLShaderFunctions;
import com.daxie.joglf.gl.wrapper.GLWrapper;
import com.daxie.log.LogFile;
import com.jogamp.opengl.GL4;

/**
 * Offers methods for GL operations.
 * @author Daba
 *
 */
public class GLFront {
	public static void LoadDefaultShaders() {
		GLShaderFunctions.CreateProgram(
				"texture", 
				"./Data/Shader/330/texture/vshader.glsl",
				"./Data/Shader/330/texture/fshader.glsl");
		GLShaderFunctions.CreateProgram(
				"color",
				"./Data/Shader/330/color/vshader.glsl",
				"./Data/Shader/330/color/fshader.glsl");
		GLShaderFunctions.CreateProgram(
				"texture_drawer", 
				"./Data/Shader/330/texture_drawer/vshader.glsl", 
				"./Data/Shader/330/texture_drawer/fshader.glsl");
		GLShaderFunctions.CreateProgram(
				"line_drawer", 
				"./Data/Shader/330/line_drawer/vshader.glsl", 
				"./Data/Shader/330/line_drawer/fshader.glsl");
		
		GLShaderFunctions.InitializeSampler();
		
		LogFile.WriteInfo("[GLFront-LoadDefaultShaders] Default shaders loaded.",true);
	}
	public static void SetDefaultGLProperties() {
		GLWrapper.glEnable(GL4.GL_DEPTH_TEST);
		GLWrapper.glDepthFunc(GL4.GL_LESS);
		
		GLWrapper.glEnable(GL4.GL_CULL_FACE);
		GLWrapper.glCullFace(GL4.GL_BACK);
		
		GLWrapper.glEnable(GL4.GL_BLEND);
		GLWrapper.glBlendFunc(GL4.GL_SRC_ALPHA, GL4.GL_ONE_MINUS_SRC_ALPHA);
		
		LogFile.WriteInfo("[GLFront-SetDefaultGLProperties] Default properties set.",true);
	}
}
