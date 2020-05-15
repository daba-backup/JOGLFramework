package com.github.dabasan.joglf.gl.front;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.dabasan.joglf.gl.draw.DrawFunctions2D;
import com.github.dabasan.joglf.gl.draw.DrawFunctions3D;
import com.github.dabasan.joglf.gl.shader.ShaderFunctions;
import com.github.dabasan.joglf.gl.shader.ShaderProgram;
import com.github.dabasan.joglf.gl.text.TextMgr;
import com.github.dabasan.joglf.gl.texture.TextureMgr;
import com.github.dabasan.joglf.gl.wrapper.GLVersion;
import com.github.dabasan.joglf.gl.wrapper.GLWrapper;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GLProfile;

/**
 * Provides methods for GL operations.
 * 
 * @author Daba
 *
 */
public class GLFront {
	private static Logger logger = LoggerFactory.getLogger(GLFront.class);

	private static String profile_str = GLProfile.GL3;
	private static Lock lock = new ReentrantLock();

	private static boolean setup_flag = false;

	public static void Setup(GLVersion gl_version) {
		GLWrapper.SetGLVersion(gl_version);
		CreateGLProfileStr(gl_version);

		setup_flag = true;
	}
	public static void Initialize() {
		CreateDefaultPrograms();
		SetDefaultGLProperties();
		AddProgramsToFronts();

		DrawFunctions2D.Initialize();
		DrawFunctions3D.Initialize();
		TextureMgr.Initialize();
		TextMgr.Initialize();
	}

	public static String GetProfileStr() {
		return profile_str;
	}
	public static boolean IsSetup() {
		return setup_flag;
	}

	private static void CreateGLProfileStr(GLVersion gl_version) {
		if (gl_version == GLVersion.GL3bc) {
			profile_str = GLProfile.GL3bc;
		} else if (gl_version == GLVersion.GL3) {
			profile_str = GLProfile.GL3;
		} else if (gl_version == GLVersion.GL4bc) {
			profile_str = GLProfile.GL4bc;
		} else if (gl_version == GLVersion.GL4) {
			profile_str = GLProfile.GL4;
		}
	}
	private static void CreateDefaultPrograms() {
		ShaderFunctions.CreateProgram("texture",
				"./Data/Shader/330/default/texture/gouraud/vshader.glsl",
				"./Data/Shader/330/default/texture/gouraud/fshader.glsl");
		ShaderFunctions.CreateProgram("texture2",
				"./Data/Shader/330/default/texture/phong/vshader.glsl",
				"./Data/Shader/330/default/texture/phong/fshader.glsl");
		ShaderFunctions.CreateProgram("color", "./Data/Shader/330/default/color/vshader.glsl",
				"./Data/Shader/330/default/color/fshader.glsl");
		ShaderFunctions.CreateProgram("texture_drawer",
				"./Data/Shader/330/default/texture_drawer/vshader.glsl",
				"./Data/Shader/330/default/texture_drawer/fshader.glsl");
		ShaderFunctions.CreateProgram("simple_2d",
				"./Data/Shader/330/default/simple_2d/vshader.glsl",
				"./Data/Shader/330/default/simple_2d/fshader.glsl");
		ShaderFunctions.CreateProgram("simple_3d",
				"./Data/Shader/330/default/simple_3d/vshader.glsl",
				"./Data/Shader/330/default/simple_3d/fshader.glsl");

		logger.info("Default programs created.");
	}
	private static void SetDefaultGLProperties() {
		GLWrapper.glEnable(GL.GL_DEPTH_TEST);
		GLWrapper.glDepthFunc(GL.GL_LESS);

		GLWrapper.glEnable(GL.GL_CULL_FACE);
		GLWrapper.glCullFace(GL.GL_BACK);

		GLWrapper.glEnable(GL.GL_BLEND);
		GLWrapper.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);

		logger.info("Default properties set.");
	}
	private static void AddProgramsToFronts() {
		final ShaderProgram texture = new ShaderProgram("texture");
		final ShaderProgram texture2 = new ShaderProgram("texture2");
		final ShaderProgram color = new ShaderProgram("color");
		final ShaderProgram simple_3d = new ShaderProgram("simple_3d");

		CameraFront.AddProgram(texture);
		CameraFront.AddProgram(texture2);
		CameraFront.AddProgram(color);
		CameraFront.AddProgram(simple_3d);
		FogFront.AddProgram(texture);
		FogFront.AddProgram(texture2);
		FogFront.AddProgram(color);
		LightingFront.AddProgram(texture);
		LightingFront.AddProgram(texture2);
	}

	public static void Lock() {
		lock.lock();
	}
	public static void Unlock() {
		lock.unlock();
	}
}
