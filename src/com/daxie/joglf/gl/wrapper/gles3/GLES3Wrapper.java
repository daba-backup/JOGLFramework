package com.daxie.joglf.gl.wrapper.gles3;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import com.daxie.joglf.log.LogFile;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.GLES3;

/**
 * Wrapper functions of GLES3
 * @author Daba
 *
 */
public class GLES3Wrapper {
	public static void glActiveTexture(int arg0) {
		GLES3 gles3=GLContext.getCurrentGL().getGLES3();
		gles3.glActiveTexture(arg0);
		
		int code=gles3.glGetError();
		if(code!=GLES3.GL_NO_ERROR) {
			LogFile.WriteWarn("[GLES3Wrapper-glActiveTexture] code:"+code,true);
		}
	}
	public static void glAttachShader(int arg0,int arg1) {
		GLES3 gles3=GLContext.getCurrentGL().getGLES3();
		gles3.glAttachShader(arg0, arg1);
		
		int code=gles3.glGetError();
		if(code!=GLES3.GL_NO_ERROR) {
			LogFile.WriteWarn("[GLES3Wrapper-glAttachShader] code:"+code,true);
		}
	}
	public static void glBindBuffer(int arg0,int arg1) {
		GLES3 gles3=GLContext.getCurrentGL().getGLES3();
		gles3.glBindBuffer(arg0, arg1);
		
		int code=gles3.glGetError();
		if(code!=GLES3.GL_NO_ERROR) {
			LogFile.WriteWarn("[GLES3Wrapper-glBindBuffer] code:"+code,true);
		}
	}
	public static void glBindSampler(int arg0,int arg1) {
		GLES3 gles3=GLContext.getCurrentGL().getGLES3();
		gles3.glBindSampler(arg0, arg1);
		
		int code=gles3.glGetError();
		if(code!=GLES3.GL_NO_ERROR) {
			LogFile.WriteWarn("[GLES3Wrapper-glBindSampler] code:"+code,true);
		}
	}
	public static void glBindVertexArray(int arg0) {
		GLES3 gles3=GLContext.getCurrentGL().getGLES3();
		gles3.glBindVertexArray(arg0);
		
		int code=gles3.glGetError();
		if(code!=GLES3.GL_NO_ERROR) {
			LogFile.WriteWarn("[GLES3Wrapper-glBindVertexArray] code:"+code,true);
		}
	}
	public static void glBlendFunc(int arg0,int arg1) {
		GLES3 gles3=GLContext.getCurrentGL().getGLES3();
		gles3.glBlendFunc(arg0, arg1);
		
		int code=gles3.glGetError();
		if(code!=GLES3.GL_NO_ERROR) {
			LogFile.WriteWarn("[GLES3Wrapper-glBlendFunc] code:"+code,true);
		}
	}
	public static void glBufferData(int arg0,long arg1,Buffer arg2,int arg3) {
		GLES3 gles3=GLContext.getCurrentGL().getGLES3();
		gles3.glBufferData(arg0, arg1, arg2, arg3);
		
		int code=gles3.glGetError();
		if(code!=GLES3.GL_NO_ERROR) {
			LogFile.WriteWarn("[GLES3Wrapper-glBufferData] code:"+code,true);
		}
	}
	public static void glClear(int arg0) {
		GLES3 gles3=GLContext.getCurrentGL().getGLES3();
		gles3.glClear(arg0);
		
		int code=gles3.glGetError();
		if(code!=GLES3.GL_NO_ERROR) {
			LogFile.WriteWarn("[GLES3Wrapper-glClear] code:"+code,true);
		}
	}
	public static void glClearColor(float arg0,float arg1,float arg2,float arg3) {
		GLES3 gles3=GLContext.getCurrentGL().getGLES3();
		gles3.glClearColor(arg0, arg1, arg2, arg3);
		
		int code=gles3.glGetError();
		if(code!=GLES3.GL_NO_ERROR) {
			LogFile.WriteWarn("[GLES3Wrapper-glClearColor] code:"+code,true);
		}
	}
	public static void glCompileShader(int arg0) {
		GLES3 gles3=GLContext.getCurrentGL().getGLES3();
		gles3.glCompileShader(arg0);
		
		int code=gles3.glGetError();
		if(code!=GLES3.GL_NO_ERROR) {
			LogFile.WriteWarn("[GLES3Wrapper-glCompileShader] code:"+code,true);
		}
	}
	public static int glCreateProgram() {
		GLES3 gles3=GLContext.getCurrentGL().getGLES3();
		int ret=gles3.glCreateProgram();
		
		int code=gles3.glGetError();
		if(code!=GLES3.GL_NO_ERROR) {
			LogFile.WriteWarn("[GLES3Wrapper-glCreateProgram] code:"+code,true);
		}
		
		return ret;
	}
	public static int glCreateShader(int arg0) {
		GLES3 gles3=GLContext.getCurrentGL().getGLES3();
		int ret=gles3.glCreateShader(arg0);
		
		int code=gles3.glGetError();
		if(code!=GLES3.GL_NO_ERROR) {
			LogFile.WriteWarn("[GLES3Wrapper-glCreateShader] code:"+code,true);
		}
		
		return ret;
	}
	public static void glCullFace(int arg0) {
		GLES3 gles3=GLContext.getCurrentGL().getGLES3();
		gles3.glCullFace(arg0);
		
		int code=gles3.glGetError();
		if(code!=GLES3.GL_NO_ERROR) {
			LogFile.WriteWarn("[GLES3Wrapper-glCullFace] code:"+code,true);
		}
	}
	public static void glDeleteBuffers(int arg0,IntBuffer arg1) {
		GLES3 gles3=GLContext.getCurrentGL().getGLES3();
		gles3.glDeleteBuffers(arg0, arg1);
		
		int code=gles3.glGetError();
		if(code!=GLES3.GL_NO_ERROR) {
			LogFile.WriteWarn("[GLES3Wrapper-glDeleteBuffers] code:"+code,true);
		}
	}
	public static void glDeleteShader(int arg0) {
		GLES3 gles3=GLContext.getCurrentGL().getGLES3();
		gles3.glDeleteShader(arg0);
		
		int code=gles3.glGetError();
		if(code!=GLES3.GL_NO_ERROR) {
			LogFile.WriteWarn("[GLES3Wrapper-glDeleteShader] code:"+code,true);
		}
	}
	public static void glDeleteVertexArrays(int arg0,IntBuffer arg1) {
		GLES3 gles3=GLContext.getCurrentGL().getGLES3();
		gles3.glDeleteVertexArrays(arg0, arg1);
		
		int code=gles3.glGetError();
		if(code!=GLES3.GL_NO_ERROR) {
			LogFile.WriteWarn("[GLES3Wrapper-glDeleteVertexArrays] code:"+code,true);
		}
	}
	public static void glDepthFunc(int arg0) {
		GLES3 gles3=GLContext.getCurrentGL().getGLES3();
		gles3.glDepthFunc(arg0);
		
		int code=gles3.glGetError();
		if(code!=GLES3.GL_NO_ERROR) {
			LogFile.WriteWarn("[GLES3Wrapper-glDepthFunc] code:"+code,true);
		}
	}
	public static void glDisableVertexAttribArray(int arg0) {
		GLES3 gles3=GLContext.getCurrentGL().getGLES3();
		gles3.glDisableVertexAttribArray(arg0);
		
		int code=gles3.glGetError();
		if(code!=GLES3.GL_NO_ERROR) {
			LogFile.WriteWarn("[GLES3Wrapper-glDisableVertexAttribArray] code:"+code,true);
		}
	}
	public static void glDrawArrays(int arg0,int arg1,int arg2) {
		GLES3 gles3=GLContext.getCurrentGL().getGLES3();
		gles3.glDrawArrays(arg0, arg1, arg2);
		
		int code=gles3.glGetError();
		if(code!=GLES3.GL_NO_ERROR) {
			LogFile.WriteWarn("[GLES3Wrapper-glDrawArrays] code:"+code,true);
		}
	}
	public static void glDrawElements(int arg0,int arg1,int arg2,int arg3) {
		GLES3 gles3=GLContext.getCurrentGL().getGLES3();
		gles3.glDrawElements(arg0, arg1, arg2, arg3);
		
		int code=gles3.glGetError();
		if(code!=GLES3.GL_NO_ERROR) {
			LogFile.WriteWarn("[GLES3Wrapper-glDrawElements] code:"+code,true);
		}
	}
	public static void glEnable(int arg0) {
		GLES3 gles3=GLContext.getCurrentGL().getGLES3();
		gles3.glEnable(arg0);
		
		int code=gles3.glGetError();
		if(code!=GLES3.GL_NO_ERROR) {
			LogFile.WriteWarn("[GLES3Wrapper-glEnable] code:"+code,true);
		}
	}
	public static void glEnableVertexAttribArray(int arg0) {
		GLES3 gles3=GLContext.getCurrentGL().getGLES3();
		gles3.glEnableVertexAttribArray(arg0);
		
		int code=gles3.glGetError();
		if(code!=GLES3.GL_NO_ERROR) {
			LogFile.WriteWarn("[GLES3Wrapper-glEnableVertexAttribArray] code:"+code,true);
		}
	}
	public static void glFlush() {
		GLES3 gles3=GLContext.getCurrentGL().getGLES3();
		gles3.glFlush();
		
		int code=gles3.glGetError();
		if(code!=GLES3.GL_NO_ERROR) {
			LogFile.WriteWarn("[GLES3Wrapper-glFlush] code:"+code,true);
		}
	}
	public static void glGenBuffers(int arg0,IntBuffer arg1) {
		GLES3 gles3=GLContext.getCurrentGL().getGLES3();
		gles3.glGenBuffers(arg0, arg1);
		
		int code=gles3.glGetError();
		if(code!=GLES3.GL_NO_ERROR) {
			LogFile.WriteWarn("[GLES3Wrapper-glGenBuffers] code:"+code,true);
		}
	}
	public static void glGenSamplers(int arg0,IntBuffer arg1) {
		GLES3 gles3=GLContext.getCurrentGL().getGLES3();
		gles3.glGenSamplers(arg0, arg1);
		
		int code=gles3.glGetError();
		if(code!=GLES3.GL_NO_ERROR) {
			LogFile.WriteWarn("[GLES3Wrapper-glGenSamplers] code:"+code,true);
		}
	}
	public static void glGenVertexArrays(int arg0,IntBuffer arg1) {
		GLES3 gles3=GLContext.getCurrentGL().getGLES3();
		gles3.glGenVertexArrays(arg0, arg1);
		
		int code=gles3.glGetError();
		if(code!=GLES3.GL_NO_ERROR) {
			LogFile.WriteWarn("[GLES3Wrapper-glGenVertexArrays] code:"+code,true);
		}
	}
	public static void glGetProgramInfoLog(int arg0,int arg1,IntBuffer arg2,ByteBuffer arg3) {
		GLES3 gles3=GLContext.getCurrentGL().getGLES3();
		gles3.glGetProgramInfoLog(arg0, arg1, arg2, arg3);
		
		int code=gles3.glGetError();
		if(code!=GLES3.GL_NO_ERROR) {
			LogFile.WriteWarn("[GLES3Wrapper-glGetProgramInfoLog] code:"+code,true);
		}
	}
	public static void glGetProgramiv(int arg0,int arg1,IntBuffer arg2) {
		GLES3 gles3=GLContext.getCurrentGL().getGLES3();
		gles3.glGetProgramiv(arg0, arg1, arg2);
		
		int code=gles3.glGetError();
		if(code!=GLES3.GL_NO_ERROR) {
			LogFile.WriteWarn("[GLES3Wrapper-glGetProgramiv] code:"+code,true);
		}
	}
	public static void glGetShaderInfoLog(int arg0,int arg1,IntBuffer arg2,ByteBuffer arg3) {
		GLES3 gles3=GLContext.getCurrentGL().getGLES3();
		gles3.glGetShaderInfoLog(arg0, arg1, arg2, arg3);
		
		int code=gles3.glGetError();
		if(code!=GLES3.GL_NO_ERROR) {
			LogFile.WriteWarn("[GLES3Wrapper-glGetShaderInfoLog] code:"+code,true);
		}
	}
	public static void glGetShaderiv(int arg0,int arg1,IntBuffer arg2) {
		GLES3 gles3=GLContext.getCurrentGL().getGLES3();
		gles3.glGetShaderiv(arg0, arg1, arg2);
		
		int code=gles3.glGetError();
		if(code!=GLES3.GL_NO_ERROR) {
			LogFile.WriteWarn("[GLES3Wrapper-glGetShaderiv] code:"+code,true);
		}
	}
	public static int glGetUniformLocation(int arg0,String arg1) {
		GLES3 gles3=GLContext.getCurrentGL().getGLES3();
		int ret=gles3.glGetUniformLocation(arg0, arg1);
		
		int code=gles3.glGetError();
		if(code!=GLES3.GL_NO_ERROR) {
			LogFile.WriteWarn("[GLES3Wrapper-glGetUniformLocation] code:"+code,true);
		}
		
		return ret;
	}
	public static void glLinkProgram(int arg0) {
		GLES3 gles3=GLContext.getCurrentGL().getGLES3();
		gles3.glLinkProgram(arg0);
		
		int code=gles3.glGetError();
		if(code!=GLES3.GL_NO_ERROR) {
			LogFile.WriteWarn("[GLES3Wrapper-glLinkProgram] code:"+code,true);
		}
	}
	public static void glSamplerParameteri(int arg0,int arg1,int arg2) {
		GLES3 gles3=GLContext.getCurrentGL().getGLES3();
		gles3.glSamplerParameteri(arg0, arg1, arg2);
		
		int code=gles3.glGetError();
		if(code!=GLES3.GL_NO_ERROR) {
			LogFile.WriteWarn("[GLES3Wrapper-glSamplerParameteri] code:"+code,true);
		}
	}
	public static void glShaderSource(int arg0,int arg1,String[] arg2,IntBuffer arg3) {
		GLES3 gles3=GLContext.getCurrentGL().getGLES3();
		gles3.glShaderSource(arg0, arg1, arg2, arg3);
		
		int code=gles3.glGetError();
		if(code!=GLES3.GL_NO_ERROR) {
			LogFile.WriteWarn("[GLES3Wrapper-glShaderSource] code:"+code,true);
		}
	}
	public static void glTexParameteri(int arg0,int arg1,int arg2) {
		GLES3 gles3=GLContext.getCurrentGL().getGLES3();
		gles3.glTexParameteri(arg0, arg1, arg2);
		
		int code=gles3.glGetError();
		if(code!=GLES3.GL_NO_ERROR) {
			LogFile.WriteWarn("[GLES3Wrapper-glTexParameteri] code:"+code,true);
		}
	}
	public static void glUniform1f(int arg0,float arg1) {
		GLES3 gles3=GLContext.getCurrentGL().getGLES3();
		gles3.glUniform1f(arg0, arg1);
		
		int code=gles3.glGetError();
		if(code!=GLES3.GL_NO_ERROR) {
			LogFile.WriteWarn("[GLES3Wrapper-glUniform1f] code:"+code,true);
		}
	}
	public static void glUniform1i(int arg0,int arg1) {
		GLES3 gles3=GLContext.getCurrentGL().getGLES3();
		gles3.glUniform1i(arg0, arg1);
		
		int code=gles3.glGetError();
		if(code!=GLES3.GL_NO_ERROR) {
			LogFile.WriteWarn("[GLES3Wrapper-glUniform1i] code:"+code,true);
		}
	}
	public static void glUniform3fv(int arg0,int arg1,FloatBuffer arg2) {
		GLES3 gles3=GLContext.getCurrentGL().getGLES3();
		gles3.glUniform3fv(arg0, arg1, arg2);
		
		int code=gles3.glGetError();
		if(code!=GLES3.GL_NO_ERROR) {
			LogFile.WriteWarn("[GLES3Wrapper-glUniform3fv] code:"+code,true);
		}
	}
	public static void glUniform4fv(int arg0,int arg1,FloatBuffer arg2) {
		GLES3 gles3=GLContext.getCurrentGL().getGLES3();
		gles3.glUniform4fv(arg0, arg1, arg2);
		
		int code=gles3.glGetError();
		if(code!=GLES3.GL_NO_ERROR) {
			LogFile.WriteWarn("[GLES3Wrapper-glUniform4fv] code:"+code,true);
		}
	}
	public static void glUniformMatrix4fv(int arg0,int arg1,boolean arg2,FloatBuffer arg3) {
		GLES3 gles3=GLContext.getCurrentGL().getGLES3();
		gles3.glUniformMatrix4fv(arg0, arg1, arg2, arg3);
		
		int code=gles3.glGetError();
		if(code!=GLES3.GL_NO_ERROR) {
			LogFile.WriteWarn("[GLES3Wrapper-glUniformMatrix4fv] code:"+code,true);
		}
	}
	public static void glUseProgram(int arg0) {
		GLES3 gles3=GLContext.getCurrentGL().getGLES3();
		gles3.glUseProgram(arg0);
		
		int code=gles3.glGetError();
		if(code!=GLES3.GL_NO_ERROR) {
			LogFile.WriteWarn("[GLES3Wrapper-glUseProgram] code:"+code,true);
		}
	}
	public static void glVertexAttribPointer(int arg0,int arg1,int arg2,boolean arg3,int arg4,long arg5) {
		GLES3 gles3=GLContext.getCurrentGL().getGLES3();
		gles3.glVertexAttribPointer(arg0, arg1, arg2, arg3, arg4, arg5);
		
		int code=gles3.glGetError();
		if(code!=GLES3.GL_NO_ERROR) {
			LogFile.WriteWarn("[GLES3Wrapper-glVertexAttribPointer] code:"+code,true);
		}
	}
}
