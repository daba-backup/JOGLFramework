package com.daxie.joglf.gl.wrapper;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import com.daxie.log.LogFile;
import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLContext;

/**
 * Wrapper functions of GL4
 * @author Daba
 *
 */
class GL4Wrapper {
	public static void glActiveTexture(int arg0) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glActiveTexture(arg0);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteTrace("[GL4Wrapper-glActiveTexture] code:"+code,true);
		}
	}
	public static void glAttachShader(int arg0,int arg1) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glAttachShader(arg0, arg1);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteTrace("[GL4Wrapper-glAttachShader] code:"+code,true);
		}
	}
	public static void glBindBuffer(int arg0,int arg1) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glBindBuffer(arg0, arg1);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteTrace("[GL4Wrapper-glBindBuffer] code:"+code,true);
		}
	}
	public static void glBindFramebuffer(int arg0,int arg1) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glBindFramebuffer(arg0, arg1);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteTrace("[GL4Wrapper-glBindFramebuffer] code:"+code,true);
		}
	}
	public static void glBindSampler(int arg0,int arg1) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glBindSampler(arg0, arg1);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteTrace("[GL4Wrapper-glBindSampler] code:"+code,true);
		}
	}
	public static void glBindTexture(int arg0,int arg1) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glBindTexture(arg0, arg1);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteTrace("[GL4Wrapper-glBindTexture] code:"+code,true);
		}
	}
	public static void glBindVertexArray(int arg0) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glBindVertexArray(arg0);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteTrace("[GL4Wrapper-glBindVertexArray] code:"+code,true);
		}
	}
	public static void glBlendFunc(int arg0,int arg1) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glBlendFunc(arg0, arg1);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteTrace("[GL4Wrapper-glBlendFunc] code:"+code,true);
		}
	}
	public static void glBufferData(int arg0,long arg1,Buffer arg2,int arg3) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glBufferData(arg0, arg1, arg2, arg3);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteTrace("[GL4Wrapper-glBufferData] code:"+code,true);
		}
	}
	public static int glCheckFramebufferStatus(int arg0) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		int ret=gl4.glCheckFramebufferStatus(arg0);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteTrace("[GL4Wrapper-glCheckFramebufferStatus] code:"+code,true);
		}
		
		return ret;
	}
	public static void glClear(int arg0) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glClear(arg0);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteTrace("[GL4Wrapper-glClear] code:"+code,true);
		}
	}
	public static void glClearColor(float arg0,float arg1,float arg2,float arg3) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glClearColor(arg0, arg1, arg2, arg3);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteTrace("[GL4Wrapper-glClearColor] code:"+code,true);
		}
	}
	public static void glColorMask(boolean arg0,boolean arg1,boolean arg2,boolean arg3) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glColorMask(arg0,arg1,arg2,arg3);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteTrace("[GL4Wrapper-glColorMask] code:"+code,true);
		}
	}
	public static void glCompileShader(int arg0) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glCompileShader(arg0);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteTrace("[GL4Wrapper-glCompileShader] code:"+code,true);
		}
	}
	public static int glCreateProgram() {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		int ret=gl4.glCreateProgram();
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteTrace("[GL4Wrapper-glCreateProgram] code:"+code,true);
		}
		
		return ret;
	}
	public static int glCreateShader(int arg0) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		int ret=gl4.glCreateShader(arg0);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteTrace("[GL4Wrapper-glCreateShader] code:"+code,true);
		}
		
		return ret;
	}
	public static void glCullFace(int arg0) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glCullFace(arg0);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteTrace("[GL4Wrapper-glCullFace] code:"+code,true);
		}
	}
	public static void glDeleteBuffers(int arg0,IntBuffer arg1) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glDeleteBuffers(arg0, arg1);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteTrace("[GL4Wrapper-glDeleteBuffers] code:"+code,true);
		}
	}
	public static void glDeleteShader(int arg0) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glDeleteShader(arg0);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteTrace("[GL4Wrapper-glDeleteShader] code:"+code,true);
		}
	}
	public static void glDeleteVertexArrays(int arg0,IntBuffer arg1) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glDeleteVertexArrays(arg0, arg1);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteTrace("[GL4Wrapper-glDeleteVertexArrays] code:"+code,true);
		}
	}
	public static void glDepthFunc(int arg0) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glDepthFunc(arg0);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteTrace("[GL4Wrapper-glDepthFunc] code:"+code,true);
		}
	}
	public static void glDepthMask(boolean arg0) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glDepthMask(arg0);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteTrace("[GL4Wrapper-glDepthMask] code:"+code,true);
		}
	}
	public static void glDisable(int arg0) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glDisable(arg0);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteTrace("[GL4Wrapper-glDisable] code:"+code,true);
		}
	}
	public static void glDisableVertexAttribArray(int arg0) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glDisableVertexAttribArray(arg0);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteTrace("[GL4Wrapper-glDisableVertexAttribArray] code:"+code,true);
		}
	}
	public static void glDrawArrays(int arg0,int arg1,int arg2) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glDrawArrays(arg0, arg1, arg2);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteTrace("[GL4Wrapper-glDrawArrays] code:"+code,true);
		}
	}
	public static void glDrawBuffer(int arg0) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glDrawBuffer(arg0);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteTrace("[GL4Wrapper-glDrawBuffer] code:"+code,true);
		}
	}
	public static void glDrawBuffers(int arg0,IntBuffer arg1) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glDrawBuffers(arg0, arg1);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteTrace("[GL4Wrapper-glDrawBuffers] code:"+code,true);
		}
	}
	public static void glDrawElements(int arg0,int arg1,int arg2,int arg3) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glDrawElements(arg0, arg1, arg2, arg3);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteTrace("[GL4Wrapper-glDrawElements] code:"+code,true);
		}
	}
	public static void glEnable(int arg0) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glEnable(arg0);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteTrace("[GL4Wrapper-glEnable] code:"+code,true);
		}
	}
	public static void glEnableVertexAttribArray(int arg0) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glEnableVertexAttribArray(arg0);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteTrace("[GL4Wrapper-glEnableVertexAttribArray] code:"+code,true);
		}
	}
	public static void glFlush() {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glFlush();
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteTrace("[GL4Wrapper-glFlush] code:"+code,true);
		}
	}
	public static void glFramebufferTexture(int arg0,int arg1,int arg2,int arg3) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glFramebufferTexture(arg0, arg1, arg2, arg3);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteTrace("[GL4Wrapper-glFramebufferTexture] code:"+code,true);
		}
	}
	public static void glFramebufferTexture2D(int arg0,int arg1,int arg2,int arg3,int arg4) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glFramebufferTexture2D(arg0, arg1, arg2, arg3, arg4);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteTrace("[GL4Wrapper-glFramebufferTexture2D] code:"+code,true);
		}
	}
	public static void glGenBuffers(int arg0,IntBuffer arg1) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glGenBuffers(arg0, arg1);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteTrace("[GL4Wrapper-glGenBuffers] code:"+code,true);
		}
	}
	public static void glGenerateMipmap(int arg0) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glGenerateMipmap(arg0);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteTrace("[GL4Wrapper-glGenerateMipmap] code:"+code,true);
		}
	}
	public static void glGenFramebuffers(int arg0,IntBuffer arg1) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glGenFramebuffers(arg0, arg1);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteTrace("[GL4Wrapper-glGenFramebuffers] code:"+code,true);
		}
	}
	public static void glGenSamplers(int arg0,IntBuffer arg1) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glGenSamplers(arg0, arg1);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteTrace("[GL4Wrapper-glGenSamplers] code:"+code,true);
		}
	}
	public static void glGenTextures(int arg0,IntBuffer arg1) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glGenTextures(arg0,arg1);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteTrace("[GL4Wrapper-glGenTextures] code:"+code,true);
		}
	}
	public static void glGenVertexArrays(int arg0,IntBuffer arg1) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glGenVertexArrays(arg0, arg1);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteTrace("[GL4Wrapper-glGenVertexArrays] code:"+code,true);
		}
	}
	public static void glGetProgramInfoLog(int arg0,int arg1,IntBuffer arg2,ByteBuffer arg3) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glGetProgramInfoLog(arg0, arg1, arg2, arg3);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteTrace("[GL4Wrapper-glGetProgramInfoLog] code:"+code,true);
		}
	}
	public static void glGetProgramiv(int arg0,int arg1,IntBuffer arg2) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glGetProgramiv(arg0, arg1, arg2);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteTrace("[GL4Wrapper-glGetProgramiv] code:"+code,true);
		}
	}
	public static void glGetShaderInfoLog(int arg0,int arg1,IntBuffer arg2,ByteBuffer arg3) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glGetShaderInfoLog(arg0, arg1, arg2, arg3);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteTrace("[GL4Wrapper-glGetShaderInfoLog] code:"+code,true);
		}
	}
	public static void glGetShaderiv(int arg0,int arg1,IntBuffer arg2) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glGetShaderiv(arg0, arg1, arg2);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteTrace("[GL4Wrapper-glGetShaderiv] code:"+code,true);
		}
	}
	public static int glGetUniformLocation(int arg0,String arg1) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		int ret=gl4.glGetUniformLocation(arg0, arg1);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteTrace("[GL4Wrapper-glGetUniformLocation] code:"+code,true);
		}
		
		return ret;
	}
	public static void glLinkProgram(int arg0) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glLinkProgram(arg0);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteTrace("[GL4Wrapper-glLinkProgram] code:"+code,true);
		}
	}
	public static ByteBuffer glMapBuffer(int arg0,int arg1) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		ByteBuffer ret=gl4.glMapBuffer(arg0, arg1);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteTrace("[GL4Wrapper-glMapBuffer] code:"+code,true);
		}
		
		return ret;
	}
	public static void glReadBuffer(int arg0) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glReadBuffer(arg0);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteTrace("[GL4Wrapper-glReadBuffer] code:"+code,true);
		}
	}
	public static void glReadPixels(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,Buffer arg6) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glReadPixels(arg0, arg1, arg2, arg3, arg4, arg5, arg6);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteTrace("[GL4Wrapper-glReadPixels] code:"+code,true);
		}
	}
	public static void glSamplerParameteri(int arg0,int arg1,int arg2) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glSamplerParameteri(arg0, arg1, arg2);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteTrace("[GL4Wrapper-glSamplerParameteri] code:"+code,true);
		}
	}
	public static void glShaderSource(int arg0,int arg1,String[] arg2,IntBuffer arg3) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glShaderSource(arg0, arg1, arg2, arg3);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteTrace("[GL4Wrapper-glShaderSource] code:"+code,true);
		}
	}
	public static void glTexImage2D(int arg0,int arg1,int arg2,int arg3,int arg4,int arg5,int arg6,int arg7,Buffer arg8) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glTexImage2D(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteTrace("[GL4Wrapper-glTexImage2D] code:"+code,true);
		}
	}
	public static void glTexParameterf(int arg0,int arg1,float arg2) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glTexParameterf(arg0, arg1, arg2);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteTrace("[GL4Wrapper-glTexParameterf] code:"+code,true);
		}
	}
	public static void glTexParameterfv(int arg0,int arg1,FloatBuffer arg2) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glTexParameterfv(arg0,arg1,arg2);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteTrace("[GL4Wrapper-glTexParameterfv] code:"+code,true);
		}
	}
	public static void glTexParameteri(int arg0,int arg1,int arg2) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glTexParameteri(arg0, arg1, arg2);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteTrace("[GL4Wrapper-glTexParameteri] code:"+code,true);
		}
	}
	public static void glUniform1f(int arg0,float arg1) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glUniform1f(arg0, arg1);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteTrace("[GL4Wrapper-glUniform1f] code:"+code,true);
		}
	}
	public static void glUniform1i(int arg0,int arg1) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glUniform1i(arg0, arg1);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteTrace("[GL4Wrapper-glUniform1i] code:"+code,true);
		}
	}
	public static void glUniform3fv(int arg0,int arg1,FloatBuffer arg2) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glUniform3fv(arg0, arg1, arg2);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteTrace("[GL4Wrapper-glUniform3fv] code:"+code,true);
		}
	}
	public static void glUniform4fv(int arg0,int arg1,FloatBuffer arg2) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glUniform4fv(arg0, arg1, arg2);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteTrace("[GL4Wrapper-glUniform4fv] code:"+code,true);
		}
	}
	public static void glUniformMatrix4fv(int arg0,int arg1,boolean arg2,FloatBuffer arg3) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glUniformMatrix4fv(arg0, arg1, arg2, arg3);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteTrace("[GL4Wrapper-glUniformMatrix4fv] code:"+code,true);
		}
	}
	public static boolean glUnmapBuffer(int arg0) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		boolean ret=gl4.glUnmapBuffer(arg0);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteTrace("[GL4Wrapper-glUnmapBuffer] code:"+code,true);
		}
		
		return ret;
	}
	public static void glUseProgram(int arg0) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glUseProgram(arg0);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteTrace("[GL4Wrapper-glUseProgram] code:"+code,true);
		}
	}
	public static void glVertexAttribPointer(int arg0,int arg1,int arg2,boolean arg3,int arg4,long arg5) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glVertexAttribPointer(arg0, arg1, arg2, arg3, arg4, arg5);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteTrace("[GL4Wrapper-glVertexAttribPointer] code:"+code,true);
		}
	}
}
