package com.daxie.joglf.gl.wrapper;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import com.daxie.joglf.gl.front.WindowFront;
import com.daxie.joglf.gl.wrapper.gl3.GL3Wrapper;
import com.daxie.joglf.gl.wrapper.gl4.GL4Wrapper;
import com.daxie.joglf.gl.wrapper.gles3.GLES3Wrapper;
import com.daxie.log.LogFile;

/**
 * Offers wrapper functions for several GL versions.
 * @author Daba
 *
 */
public class GLWrapper{ 
	private static GLVersion gl_version=GLVersion.GL4;
	
	public static void SetGLVersion(GLVersion version) {
		if(WindowFront.IsInitialized()==true) {
			LogFile.WriteWarn("[GLWrapper-SetGLVersion] This method is disabled after the framework is initialized.", true);
			return;
		}
		
		gl_version=version;
	}
	public static GLVersion GetGLVersion() {
		return gl_version;
	}
	
	public static void glActiveTexture(int arg0) {
		if(gl_version==GLVersion.GL3)GL3Wrapper.glActiveTexture(arg0);
		else if(gl_version==GLVersion.GL4)GL4Wrapper.glActiveTexture(arg0);
		else if(gl_version==GLVersion.GLES3)GLES3Wrapper.glActiveTexture(arg0);
	}
	
	public static void glAttachShader(int arg0,int arg1) {
		if(gl_version==GLVersion.GL3)GL3Wrapper.glAttachShader(arg0, arg1);
		else if(gl_version==GLVersion.GL4)GL4Wrapper.glAttachShader(arg0, arg1);
		else if(gl_version==GLVersion.GLES3)GLES3Wrapper.glAttachShader(arg0, arg1);
	}
	public static void glBindBuffer(int arg0,int arg1) {
		if(gl_version==GLVersion.GL3)GL3Wrapper.glBindBuffer(arg0, arg1);
		else if(gl_version==GLVersion.GL4)GL4Wrapper.glBindBuffer(arg0, arg1);
		else if(gl_version==GLVersion.GLES3)GLES3Wrapper.glBindBuffer(arg0, arg1);
	}
	public static void glBindSampler(int arg0,int arg1) {
		if(gl_version==GLVersion.GL3)GL3Wrapper.glBindSampler(arg0, arg1);
		else if(gl_version==GLVersion.GL4)GL4Wrapper.glBindSampler(arg0, arg1);
		else if(gl_version==GLVersion.GLES3)GLES3Wrapper.glBindSampler(arg0, arg1);
	}
	public static void glBindVertexArray(int arg0) {
		if(gl_version==GLVersion.GL3)GL3Wrapper.glBindVertexArray(arg0);
		else if(gl_version==GLVersion.GL4)GL4Wrapper.glBindVertexArray(arg0);
		else if(gl_version==GLVersion.GLES3)GLES3Wrapper.glBindVertexArray(arg0);
	}
	public static void glBlendFunc(int arg0,int arg1) {
		if(gl_version==GLVersion.GL3)GL3Wrapper.glBlendFunc(arg0, arg1);
		else if(gl_version==GLVersion.GL4)GL4Wrapper.glBlendFunc(arg0, arg1);
		else if(gl_version==GLVersion.GLES3)GLES3Wrapper.glBlendFunc(arg0, arg1);
	}
	public static void glBufferData(int arg0,long arg1,Buffer arg2,int arg3) {
		if(gl_version==GLVersion.GL3)GL3Wrapper.glBufferData(arg0, arg1, arg2, arg3);
		else if(gl_version==GLVersion.GL4)GL4Wrapper.glBufferData(arg0, arg1, arg2, arg3);
		else if(gl_version==GLVersion.GLES3)GLES3Wrapper.glBufferData(arg0, arg1, arg2, arg3);
	}
	public static void glClear(int arg0) {
		if(gl_version==GLVersion.GL3)GL3Wrapper.glClear(arg0);
		else if(gl_version==GLVersion.GL4)GL4Wrapper.glClear(arg0);
		else if(gl_version==GLVersion.GLES3)GLES3Wrapper.glClear(arg0);
	}
	public static void glClearColor(float arg0,float arg1,float arg2,float arg3) {
		if(gl_version==GLVersion.GL3)GL3Wrapper.glClearColor(arg0, arg1, arg2, arg3);
		else if(gl_version==GLVersion.GL4)GL4Wrapper.glClearColor(arg0, arg1, arg2, arg3);
		else if(gl_version==GLVersion.GLES3)GLES3Wrapper.glClearColor(arg0, arg1, arg2, arg3);
	}
	public static void glCompileShader(int arg0) {
		if(gl_version==GLVersion.GL3)GL3Wrapper.glCompileShader(arg0);
		else if(gl_version==GLVersion.GL4)GL4Wrapper.glCompileShader(arg0);
		else if(gl_version==GLVersion.GLES3)GLES3Wrapper.glCompileShader(arg0);
	}
	public static int glCreateProgram() {
		int ret=-1;
		
		if(gl_version==GLVersion.GL3)ret=GL3Wrapper.glCreateProgram();
		else if(gl_version==GLVersion.GL4)ret=GL4Wrapper.glCreateProgram();
		else if(gl_version==GLVersion.GLES3)ret=GLES3Wrapper.glCreateProgram();
		
		return ret;
	}
	public static int glCreateShader(int arg0) {
		int ret=-1;
		
		if(gl_version==GLVersion.GL3)ret=GL3Wrapper.glCreateShader(arg0);
		else if(gl_version==GLVersion.GL4)ret=GL4Wrapper.glCreateShader(arg0);
		else if(gl_version==GLVersion.GLES3)ret=GLES3Wrapper.glCreateShader(arg0);
		
		return ret;
	}
	public static void glCullFace(int arg0) {
		if(gl_version==GLVersion.GL3)GL3Wrapper.glCullFace(arg0);
		else if(gl_version==GLVersion.GL4)GL4Wrapper.glCullFace(arg0);
		else if(gl_version==GLVersion.GLES3)GLES3Wrapper.glCullFace(arg0);
	}
	public static void glDeleteBuffers(int arg0,IntBuffer arg1) {
		if(gl_version==GLVersion.GL3)GL3Wrapper.glDeleteBuffers(arg0, arg1);
		else if(gl_version==GLVersion.GL4)GL4Wrapper.glDeleteBuffers(arg0, arg1);
		else if(gl_version==GLVersion.GLES3)GLES3Wrapper.glDeleteBuffers(arg0, arg1);
	}
	public static void glDeleteShader(int arg0) {
		if(gl_version==GLVersion.GL3)GL3Wrapper.glDeleteShader(arg0);
		else if(gl_version==GLVersion.GL4)GL4Wrapper.glDeleteShader(arg0);
		else if(gl_version==GLVersion.GLES3)GLES3Wrapper.glDeleteShader(arg0);
	}
	public static void glDeleteVertexArrays(int arg0,IntBuffer arg1) {
		if(gl_version==GLVersion.GL3)GL3Wrapper.glDeleteVertexArrays(arg0, arg1);
		else if(gl_version==GLVersion.GL4)GL4Wrapper.glDeleteVertexArrays(arg0, arg1);
		else if(gl_version==GLVersion.GLES3)GLES3Wrapper.glDeleteVertexArrays(arg0, arg1);
	}
	public static void glDepthFunc(int arg0) {
		if(gl_version==GLVersion.GL3)GL3Wrapper.glDepthFunc(arg0);
		else if(gl_version==GLVersion.GL4)GL4Wrapper.glDepthFunc(arg0);
		else if(gl_version==GLVersion.GLES3)GLES3Wrapper.glDepthFunc(arg0);
	}
	public static void glDisableVertexAttribArray(int arg0) {
		if(gl_version==GLVersion.GL3)GL3Wrapper.glDisableVertexAttribArray(arg0);
		else if(gl_version==GLVersion.GL4)GL4Wrapper.glDisableVertexAttribArray(arg0);
		else if(gl_version==GLVersion.GLES3)GLES3Wrapper.glDisableVertexAttribArray(arg0);
	}
	public static void glDrawArrays(int arg0,int arg1,int arg2) {
		if(gl_version==GLVersion.GL3)GL3Wrapper.glDrawArrays(arg0, arg1, arg2);
		else if(gl_version==GLVersion.GL4)GL4Wrapper.glDrawArrays(arg0, arg1, arg2);
		else if(gl_version==GLVersion.GLES3)GLES3Wrapper.glDrawArrays(arg0, arg1, arg2);
	}
	public static void glDrawElements(int arg0,int arg1,int arg2,int arg3) {
		if(gl_version==GLVersion.GL3)GL3Wrapper.glDrawElements(arg0, arg1, arg2, arg3);
		else if(gl_version==GLVersion.GL4)GL4Wrapper.glDrawElements(arg0, arg1, arg2, arg3);
		else if(gl_version==GLVersion.GLES3)GLES3Wrapper.glDrawElements(arg0, arg1, arg2, arg3);
	}
	public static void glEnable(int arg0) {
		if(gl_version==GLVersion.GL3)GL3Wrapper.glEnable(arg0);
		else if(gl_version==GLVersion.GL4)GL4Wrapper.glEnable(arg0);
		else if(gl_version==GLVersion.GLES3)GLES3Wrapper.glEnable(arg0);
	}
	public static void glEnableVertexAttribArray(int arg0) {
		if(gl_version==GLVersion.GL3)GL3Wrapper.glEnableVertexAttribArray(arg0);
		else if(gl_version==GLVersion.GL4)GL4Wrapper.glEnableVertexAttribArray(arg0);
		else if(gl_version==GLVersion.GLES3)GLES3Wrapper.glEnableVertexAttribArray(arg0);
	}
	public static void glFlush() {
		if(gl_version==GLVersion.GL3)GL3Wrapper.glFlush();
		else if(gl_version==GLVersion.GL4)GL4Wrapper.glFlush();
		else if(gl_version==GLVersion.GLES3)GLES3Wrapper.glFlush();
	}
	public static void glGenBuffers(int arg0,IntBuffer arg1) {
		if(gl_version==GLVersion.GL3)GL3Wrapper.glGenBuffers(arg0, arg1);
		else if(gl_version==GLVersion.GL4)GL4Wrapper.glGenBuffers(arg0, arg1);
		else if(gl_version==GLVersion.GLES3)GLES3Wrapper.glGenBuffers(arg0, arg1);
	}
	public static void glGenSamplers(int arg0,IntBuffer arg1) {
		if(gl_version==GLVersion.GL3)GL3Wrapper.glGenSamplers(arg0, arg1);
		else if(gl_version==GLVersion.GL4)GL4Wrapper.glGenSamplers(arg0, arg1);
		else if(gl_version==GLVersion.GLES3)GLES3Wrapper.glGenSamplers(arg0, arg1);
	}
	public static void glGenVertexArrays(int arg0,IntBuffer arg1) {
		if(gl_version==GLVersion.GL3)GL3Wrapper.glGenVertexArrays(arg0, arg1);
		else if(gl_version==GLVersion.GL4)GL4Wrapper.glGenVertexArrays(arg0, arg1);
		else if(gl_version==GLVersion.GLES3)GLES3Wrapper.glGenVertexArrays(arg0, arg1);
	}
	public static void glGetProgramInfoLog(int arg0,int arg1,IntBuffer arg2,ByteBuffer arg3) {
		if(gl_version==GLVersion.GL3)GL3Wrapper.glGetProgramInfoLog(arg0, arg1, arg2, arg3);
		else if(gl_version==GLVersion.GL4)GL4Wrapper.glGetProgramInfoLog(arg0, arg1, arg2, arg3);
		else if(gl_version==GLVersion.GLES3)GLES3Wrapper.glGetProgramInfoLog(arg0, arg1, arg2, arg3);
	}
	public static void glGetProgramiv(int arg0,int arg1,IntBuffer arg2) {
		if(gl_version==GLVersion.GL3)GL3Wrapper.glGetProgramiv(arg0, arg1, arg2);
		else if(gl_version==GLVersion.GL4)GL4Wrapper.glGetProgramiv(arg0, arg1, arg2);
		else if(gl_version==GLVersion.GLES3)GLES3Wrapper.glGetProgramiv(arg0, arg1, arg2);
	}
	public static void glGetShaderInfoLog(int arg0,int arg1,IntBuffer arg2,ByteBuffer arg3) {
		if(gl_version==GLVersion.GL3)GL3Wrapper.glGetShaderInfoLog(arg0, arg1, arg2, arg3);
		else if(gl_version==GLVersion.GL4)GL4Wrapper.glGetShaderInfoLog(arg0, arg1, arg2, arg3);
		else if(gl_version==GLVersion.GLES3)GLES3Wrapper.glGetShaderInfoLog(arg0, arg1, arg2, arg3);
	}
	public static void glGetShaderiv(int arg0,int arg1,IntBuffer arg2) {
		if(gl_version==GLVersion.GL3)GL3Wrapper.glGetShaderiv(arg0, arg1, arg2);
		else if(gl_version==GLVersion.GL4)GL4Wrapper.glGetShaderiv(arg0, arg1, arg2);
		else if(gl_version==GLVersion.GLES3)GLES3Wrapper.glGetShaderiv(arg0, arg1, arg2);
	}
	public static int glGetUniformLocation(int arg0,String arg1) {
		int ret=-1;
		
		if(gl_version==GLVersion.GL3)ret=GL3Wrapper.glGetUniformLocation(arg0, arg1);
		else if(gl_version==GLVersion.GL4)ret=GL4Wrapper.glGetUniformLocation(arg0, arg1);
		else if(gl_version==GLVersion.GLES3)ret=GLES3Wrapper.glGetUniformLocation(arg0, arg1);
		
		return ret;
	}
	public static void glLinkProgram(int arg0) {
		if(gl_version==GLVersion.GL3)GL3Wrapper.glLinkProgram(arg0);
		else if(gl_version==GLVersion.GL4)GL4Wrapper.glLinkProgram(arg0);
		else if(gl_version==GLVersion.GLES3)GLES3Wrapper.glLinkProgram(arg0);
	}
	public static void glSamplerParameteri(int arg0,int arg1,int arg2) {
		if(gl_version==GLVersion.GL3)GL3Wrapper.glSamplerParameteri(arg0, arg1, arg2);
		else if(gl_version==GLVersion.GL4)GL4Wrapper.glSamplerParameteri(arg0, arg1, arg2);
		else if(gl_version==GLVersion.GLES3)GLES3Wrapper.glSamplerParameteri(arg0, arg1, arg2);
	}
	public static void glShaderSource(int arg0,int arg1,String[] arg2,IntBuffer arg3) {
		if(gl_version==GLVersion.GL3)GL3Wrapper.glShaderSource(arg0, arg1, arg2, arg3);
		else if(gl_version==GLVersion.GL4)GL4Wrapper.glShaderSource(arg0, arg1, arg2, arg3);
		else if(gl_version==GLVersion.GLES3)GLES3Wrapper.glShaderSource(arg0, arg1, arg2, arg3);
	}
	public static void glTexParameteri(int arg0,int arg1,int arg2) {
		if(gl_version==GLVersion.GL3)GL3Wrapper.glTexParameteri(arg0, arg1, arg2);
		else if(gl_version==GLVersion.GL4)GL4Wrapper.glTexParameteri(arg0, arg1, arg2);
		else if(gl_version==GLVersion.GLES3)GLES3Wrapper.glTexParameteri(arg0, arg1, arg2);
	}
	public static void glUniform1f(int arg0,float arg1) {
		if(gl_version==GLVersion.GL3)GL3Wrapper.glUniform1f(arg0, arg1);
		else if(gl_version==GLVersion.GL4)GL4Wrapper.glUniform1f(arg0, arg1);
		else if(gl_version==GLVersion.GLES3)GLES3Wrapper.glUniform1f(arg0, arg1);
	}
	public static void glUniform1i(int arg0,int arg1) {
		if(gl_version==GLVersion.GL3)GL3Wrapper.glUniform1i(arg0, arg1);
		else if(gl_version==GLVersion.GL4)GL4Wrapper.glUniform1i(arg0, arg1);
		else if(gl_version==GLVersion.GLES3)GLES3Wrapper.glUniform1i(arg0, arg1);
	}
	public static void glUniform3fv(int arg0,int arg1,FloatBuffer arg2) {
		if(gl_version==GLVersion.GL3)GL3Wrapper.glUniform3fv(arg0, arg1, arg2);
		else if(gl_version==GLVersion.GL4)GL4Wrapper.glUniform3fv(arg0, arg1, arg2);
		else if(gl_version==GLVersion.GLES3)GLES3Wrapper.glUniform3fv(arg0, arg1, arg2);
	}
	public static void glUniform4fv(int arg0,int arg1,FloatBuffer arg2) {
		if(gl_version==GLVersion.GL3)GL3Wrapper.glUniform4fv(arg0, arg1, arg2);
		else if(gl_version==GLVersion.GL4)GL4Wrapper.glUniform4fv(arg0, arg1, arg2);
		else if(gl_version==GLVersion.GLES3)GLES3Wrapper.glUniform4fv(arg0, arg1, arg2);
	}
	public static void glUniformMatrix4fv(int arg0,int arg1,boolean arg2,FloatBuffer arg3) {
		if(gl_version==GLVersion.GL3)GL3Wrapper.glUniformMatrix4fv(arg0, arg1, arg2, arg3);
		else if(gl_version==GLVersion.GL4)GL4Wrapper.glUniformMatrix4fv(arg0, arg1, arg2, arg3);
		else if(gl_version==GLVersion.GLES3)GLES3Wrapper.glUniformMatrix4fv(arg0, arg1, arg2, arg3);
	}
	public static void glUseProgram(int arg0) {
		if(gl_version==GLVersion.GL3)GL3Wrapper.glUseProgram(arg0);
		else if(gl_version==GLVersion.GL4)GL4Wrapper.glUseProgram(arg0);
		else if(gl_version==GLVersion.GLES3)GLES3Wrapper.glUseProgram(arg0);
	}
	public static void glVertexAttribPointer(int arg0,int arg1,int arg2,boolean arg3,int arg4,long arg5) {
		if(gl_version==GLVersion.GL3)GL3Wrapper.glVertexAttribPointer(arg0, arg1, arg2, arg3, arg4, arg5);
		else if(gl_version==GLVersion.GL4)GL4Wrapper.glVertexAttribPointer(arg0, arg1, arg2, arg3, arg4, arg5);
		else if(gl_version==GLVersion.GLES3)GLES3Wrapper.glVertexAttribPointer(arg0, arg1, arg2, arg3, arg4, arg5);
	}
}
