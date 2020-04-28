package com.github.dabasan.joglf.gl.front;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.dabasan.joglf.gl.shader.ShaderFunctions;
import com.github.dabasan.joglf.gl.shader.ShaderProgram;
import com.github.dabasan.joglf.gl.text.TextMgr;
import com.github.dabasan.joglf.gl.texture.TextureMgr;
import com.github.dabasan.joglf.gl.wrapper.GLVersion;
import com.github.dabasan.joglf.gl.wrapper.GLWrapper;
import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLProfile;

/**
 * Provides methods for GL operations.
 * @author Daba
 *
 */
public class GLFront {
	private static Logger logger=LoggerFactory.getLogger(GLFront.class);
	
	private static String profile_str=GLProfile.GL3;
	private static Lock lock=new ReentrantLock();
	
	private static boolean setup_flag=false;
	
	public static void Setup(GLVersion gl_version) {
		GLWrapper.SetGLVersion(gl_version);
		CreateGLProfileStr(gl_version);
		
		setup_flag=true;
	}
	public static void Initialize() {
		LoadDefaultShaders();
		SetDefaultGLProperties();
		AddProgramsToFronts();
		
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
		if(gl_version==GLVersion.GL3bc)profile_str=GLProfile.GL3bc;
		else if(gl_version==GLVersion.GL3)profile_str=GLProfile.GL3;
		else if(gl_version==GLVersion.GL4bc)profile_str=GLProfile.GL4bc;
		else if(gl_version==GLVersion.GL4)profile_str=GLProfile.GL4;
	}
	private static void LoadDefaultShaders() {
		ShaderFunctions.CreateProgram(
				"texture", 
				"./Data/Shader/330/texture/gouraud/vshader.glsl",
				"./Data/Shader/330/texture/gouraud/fshader.glsl");
		ShaderFunctions.CreateProgram(
				"color",
				"./Data/Shader/330/color/vshader.glsl",
				"./Data/Shader/330/color/fshader.glsl");
		ShaderFunctions.CreateProgram(
				"texture_drawer", 
				"./Data/Shader/330/texture_drawer/vshader.glsl", 
				"./Data/Shader/330/texture_drawer/fshader.glsl");
		ShaderFunctions.CreateProgram(
				"line_drawer", 
				"./Data/Shader/330/line_drawer/vshader.glsl", 
				"./Data/Shader/330/line_drawer/fshader.glsl");
		
		logger.info("Default shaders loaded.");
	}
	private static void SetDefaultGLProperties() {
		GLWrapper.glEnable(GL4.GL_DEPTH_TEST);
		GLWrapper.glDepthFunc(GL4.GL_LESS);
		
		GLWrapper.glEnable(GL4.GL_CULL_FACE);
		GLWrapper.glCullFace(GL4.GL_BACK);
		
		GLWrapper.glEnable(GL4.GL_BLEND);
		GLWrapper.glBlendFunc(GL4.GL_SRC_ALPHA, GL4.GL_ONE_MINUS_SRC_ALPHA);
		
		logger.info("Default properties set.");
	}
	private static void AddProgramsToFronts() {
		ShaderProgram texture=new ShaderProgram("texture");
		ShaderProgram color=new ShaderProgram("color");
		
		CameraFront.AddProgram(texture);
		CameraFront.AddProgram(color);
		FogFront.AddProgram(texture);
		FogFront.AddProgram(color);
		LightingFront.AddProgram(texture);
	}
	
	public static void Lock() {
		lock.lock();
	}
	public static void Unlock() {
		lock.unlock();
	}
}
