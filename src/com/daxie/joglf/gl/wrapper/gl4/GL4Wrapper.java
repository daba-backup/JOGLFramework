package com.daxie.joglf.gl.wrapper.gl4;

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
public class GL4Wrapper {
	public static void glActiveTexture(int arg0) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glActiveTexture(arg0);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteWarn("[GL4Wrapper-glActiveTexture] code:"+code,true);
		}
	}
	public static void glAttachShader(int arg0,int arg1) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glAttachShader(arg0, arg1);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteWarn("[GL4Wrapper-glAttachShader] code:"+code,true);
		}
	}
	public static void glBindBuffer(int arg0,int arg1) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glBindBuffer(arg0, arg1);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteWarn("[GL4Wrapper-glBindBuffer] code:"+code,true);
		}
	}
	public static void glBindSampler(int arg0,int arg1) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glBindSampler(arg0, arg1);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteWarn("[GL4Wrapper-glBindSampler] code:"+code,true);
		}
	}
	public static void glBindVertexArray(int arg0) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glBindVertexArray(arg0);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteWarn("[GL4Wrapper-glBindVertexArray] code:"+code,true);
		}
	}
	public static void glBlendFunc(int arg0,int arg1) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glBlendFunc(arg0, arg1);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteWarn("[GL4Wrapper-glBlendFunc] code:"+code,true);
		}
	}
	public static void glBufferData(int arg0,long arg1,Buffer arg2,int arg3) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glBufferData(arg0, arg1, arg2, arg3);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteWarn("[GL4Wrapper-glBufferData] code:"+code,true);
		}
	}
	public static void glClear(int arg0) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glClear(arg0);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteWarn("[GL4Wrapper-glClear] code:"+code,true);
		}
	}
	public static void glClearColor(float arg0,float arg1,float arg2,float arg3) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glClearColor(arg0, arg1, arg2, arg3);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteWarn("[GL4Wrapper-glClearColor] code:"+code,true);
		}
	}
	public static void glCompileShader(int arg0) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glCompileShader(arg0);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteWarn("[GL4Wrapper-glCompileShader] code:"+code,true);
		}
	}
	public static int glCreateProgram() {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		int ret=gl4.glCreateProgram();
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteWarn("[GL4Wrapper-glCreateProgram] code:"+code,true);
		}
		
		return ret;
	}
	public static int glCreateShader(int arg0) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		int ret=gl4.glCreateShader(arg0);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteWarn("[GL4Wrapper-glCreateShader] code:"+code,true);
		}
		
		return ret;
	}
	public static void glCullFace(int arg0) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glCullFace(arg0);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteWarn("[GL4Wrapper-glCullFace] code:"+code,true);
		}
	}
	public static void glDeleteBuffers(int arg0,IntBuffer arg1) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glDeleteBuffers(arg0, arg1);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteWarn("[GL4Wrapper-glDeleteBuffers] code:"+code,true);
		}
	}
	public static void glDeleteShader(int arg0) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glDeleteShader(arg0);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteWarn("[GL4Wrapper-glDeleteShader] code:"+code,true);
		}
	}
	public static void glDeleteVertexArrays(int arg0,IntBuffer arg1) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glDeleteVertexArrays(arg0, arg1);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteWarn("[GL4Wrapper-glDeleteVertexArrays] code:"+code,true);
		}
	}
	public static void glDepthFunc(int arg0) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glDepthFunc(arg0);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteWarn("[GL4Wrapper-glDepthFunc] code:"+code,true);
		}
	}
	public static void glDisableVertexAttribArray(int arg0) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glDisableVertexAttribArray(arg0);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteWarn("[GL4Wrapper-glDisableVertexAttribArray] code:"+code,true);
		}
	}
	public static void glDrawArrays(int arg0,int arg1,int arg2) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glDrawArrays(arg0, arg1, arg2);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteWarn("[GL4Wrapper-glDrawArrays] code:"+code,true);
		}
	}
	public static void glDrawElements(int arg0,int arg1,int arg2,int arg3) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glDrawElements(arg0, arg1, arg2, arg3);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteWarn("[GL4Wrapper-glDrawElements] code:"+code,true);
		}
	}
	public static void glEnable(int arg0) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glEnable(arg0);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteWarn("[GL4Wrapper-glEnable] code:"+code,true);
		}
	}
	public static void glEnableVertexAttribArray(int arg0) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glEnableVertexAttribArray(arg0);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteWarn("[GL4Wrapper-glEnableVertexAttribArray] code:"+code,true);
		}
	}
	public static void glFlush() {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glFlush();
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteWarn("[GL4Wrapper-glFlush] code:"+code,true);
		}
	}
	public static void glGenBuffers(int arg0,IntBuffer arg1) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glGenBuffers(arg0, arg1);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteWarn("[GL4Wrapper-glGenBuffers] code:"+code,true);
		}
	}
	public static void glGenSamplers(int arg0,IntBuffer arg1) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glGenSamplers(arg0, arg1);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteWarn("[GL4Wrapper-glGenSamplers] code:"+code,true);
		}
	}
	public static void glGenVertexArrays(int arg0,IntBuffer arg1) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glGenVertexArrays(arg0, arg1);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteWarn("[GL4Wrapper-glGenVertexArrays] code:"+code,true);
		}
	}
	public static void glGetProgramInfoLog(int arg0,int arg1,IntBuffer arg2,ByteBuffer arg3) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glGetProgramInfoLog(arg0, arg1, arg2, arg3);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteWarn("[GL4Wrapper-glGetProgramInfoLog] code:"+code,true);
		}
	}
	public static void glGetProgramiv(int arg0,int arg1,IntBuffer arg2) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glGetProgramiv(arg0, arg1, arg2);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteWarn("[GL4Wrapper-glGetProgramiv] code:"+code,true);
		}
	}
	public static void glGetShaderInfoLog(int arg0,int arg1,IntBuffer arg2,ByteBuffer arg3) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glGetShaderInfoLog(arg0, arg1, arg2, arg3);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteWarn("[GL4Wrapper-glGetShaderInfoLog] code:"+code,true);
		}
	}
	public static void glGetShaderiv(int arg0,int arg1,IntBuffer arg2) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glGetShaderiv(arg0, arg1, arg2);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteWarn("[GL4Wrapper-glGetShaderiv] code:"+code,true);
		}
	}
	public static int glGetUniformLocation(int arg0,String arg1) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		int ret=gl4.glGetUniformLocation(arg0, arg1);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteWarn("[GL4Wrapper-glGetUniformLocation] code:"+code,true);
		}
		
		return ret;
	}
	public static void glLinkProgram(int arg0) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glLinkProgram(arg0);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteWarn("[GL4Wrapper-glLinkProgram] code:"+code,true);
		}
	}
	public static void glSamplerParameteri(int arg0,int arg1,int arg2) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glSamplerParameteri(arg0, arg1, arg2);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteWarn("[GL4Wrapper-glSamplerParameteri] code:"+code,true);
		}
	}
	public static void glShaderSource(int arg0,int arg1,String[] arg2,IntBuffer arg3) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glShaderSource(arg0, arg1, arg2, arg3);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteWarn("[GL4Wrapper-glShaderSource] code:"+code,true);
		}
	}
	public static void glTexParameteri(int arg0,int arg1,int arg2) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glTexParameteri(arg0, arg1, arg2);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteWarn("[GL4Wrapper-glTexParameteri] code:"+code,true);
		}
	}
	public static void glUniform1f(int arg0,float arg1) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glUniform1f(arg0, arg1);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteWarn("[GL4Wrapper-glUniform1f] code:"+code,true);
		}
	}
	public static void glUniform1i(int arg0,int arg1) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glUniform1i(arg0, arg1);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteWarn("[GL4Wrapper-glUniform1i] code:"+code,true);
		}
	}
	public static void glUniform3fv(int arg0,int arg1,FloatBuffer arg2) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glUniform3fv(arg0, arg1, arg2);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteWarn("[GL4Wrapper-glUniform3fv] code:"+code,true);
		}
	}
	public static void glUniform4fv(int arg0,int arg1,FloatBuffer arg2) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glUniform4fv(arg0, arg1, arg2);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteWarn("[GL4Wrapper-glUniform4fv] code:"+code,true);
		}
	}
	public static void glUniformMatrix4fv(int arg0,int arg1,boolean arg2,FloatBuffer arg3) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glUniformMatrix4fv(arg0, arg1, arg2, arg3);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteWarn("[GL4Wrapper-glUniformMatrix4fv] code:"+code,true);
		}
	}
	public static void glUseProgram(int arg0) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glUseProgram(arg0);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteWarn("[GL4Wrapper-glUseProgram] code:"+code,true);
		}
	}
	public static void glVertexAttribPointer(int arg0,int arg1,int arg2,boolean arg3,int arg4,long arg5) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		gl4.glVertexAttribPointer(arg0, arg1, arg2, arg3, arg4, arg5);
		
		int code=gl4.glGetError();
		if(code!=GL4.GL_NO_ERROR) {
			LogFile.WriteWarn("[GL4Wrapper-glVertexAttribPointer] code:"+code,true);
		}
	}
}
