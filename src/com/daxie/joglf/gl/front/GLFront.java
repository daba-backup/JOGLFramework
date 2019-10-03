package com.daxie.joglf.gl.front;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.daxie.joglf.gl.text.TextMgr;
import com.daxie.joglf.gl.texture.TextureMgr;
import com.daxie.joglf.gl.wrapper.GLShaderFunctions;
import com.daxie.joglf.gl.wrapper.GLVersion;
import com.daxie.joglf.gl.wrapper.GLWrapper;
import com.daxie.log.LogFile;
import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLProfile;

/**
 * Offers methods for GL operations.
 * @author Daba
 *
 */
public class GLFront {
	private static String profile_str="";
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
		if(gl_version==GLVersion.GL3)profile_str=GLProfile.GL3bc;
		else if(gl_version==GLVersion.GL4)profile_str=GLProfile.GL4bc;
		else if(gl_version==GLVersion.GLES3)profile_str=GLProfile.GLES3;
	}
	private static void LoadDefaultShaders() {
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
	private static void SetDefaultGLProperties() {
		GLWrapper.glEnable(GL4.GL_DEPTH_TEST);
		GLWrapper.glDepthFunc(GL4.GL_LESS);
		
		GLWrapper.glEnable(GL4.GL_CULL_FACE);
		GLWrapper.glCullFace(GL4.GL_BACK);
		
		GLWrapper.glEnable(GL4.GL_BLEND);
		GLWrapper.glBlendFunc(GL4.GL_SRC_ALPHA, GL4.GL_ONE_MINUS_SRC_ALPHA);
		
		LogFile.WriteInfo("[GLFront-SetDefaultGLProperties] Default properties set.",true);
	}
	
	public static void Lock() {
		lock.lock();
	}
	public static void Unlock() {
		lock.unlock();
	}
}
