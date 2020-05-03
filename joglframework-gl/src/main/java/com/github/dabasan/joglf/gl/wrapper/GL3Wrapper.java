package com.github.dabasan.joglf.gl.wrapper;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL3;
import com.jogamp.opengl.GLContext;

/**
 * Wrapper functions of GL3
 * 
 * @author Daba
 *
 */
class GL3Wrapper {
	private static Logger logger = LoggerFactory.getLogger(GL3Wrapper.class);

	public static void glActiveTexture(int arg0) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glActiveTexture(arg0);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void glAttachShader(int arg0, int arg1) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glAttachShader(arg0, arg1);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void glBindBuffer(int arg0, int arg1) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glBindBuffer(arg0, arg1);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void glBindFramebuffer(int arg0, int arg1) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glBindFramebuffer(arg0, arg1);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void glBindRenderbuffer(int arg0, int arg1) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glBindRenderbuffer(arg0, arg1);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void glBindSampler(int arg0, int arg1) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glBindSampler(arg0, arg1);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void glBindTexture(int arg0, int arg1) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glBindTexture(arg0, arg1);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void glBindVertexArray(int arg0) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glBindVertexArray(arg0);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void glBlendFunc(int arg0, int arg1) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glBlendFunc(arg0, arg1);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void glBufferData(int arg0, long arg1, Buffer arg2,
			int arg3) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glBufferData(arg0, arg1, arg2, arg3);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static int glCheckFramebufferStatus(int arg0) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		final int ret = gl3.glCheckFramebufferStatus(arg0);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}

		return ret;
	}
	public static void glClear(int arg0) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glClear(arg0);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void glClearColor(float arg0, float arg1, float arg2,
			float arg3) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glClearColor(arg0, arg1, arg2, arg3);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void glClearDepth(double arg0) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glClearDepth(arg0);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void glClearDepthf(float arg0) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glClearDepthf(arg0);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void glClearStencil(int arg0) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glClearStencil(arg0);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void glColorMask(boolean arg0, boolean arg1, boolean arg2,
			boolean arg3) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glColorMask(arg0, arg1, arg2, arg3);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void glCompileShader(int arg0) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glCompileShader(arg0);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void glCopyTexImage2D(int arg0, int arg1, int arg2, int arg3,
			int arg4, int arg5, int arg6, int arg7) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glCopyTexImage2D(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static int glCreateProgram() {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		final int ret = gl3.glCreateProgram();

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}

		return ret;
	}
	public static int glCreateShader(int arg0) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		final int ret = gl3.glCreateShader(arg0);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}

		return ret;
	}
	public static void glCullFace(int arg0) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glCullFace(arg0);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void glDeleteBuffers(int arg0, IntBuffer arg1) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glDeleteBuffers(arg0, arg1);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void glDeleteFramebuffers(int arg0, IntBuffer arg1) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glDeleteFramebuffers(arg0, arg1);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void glDeleteRenderbuffers(int arg0, IntBuffer arg1) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glDeleteRenderbuffers(arg0, arg1);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void glDeleteShader(int arg0) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glDeleteShader(arg0);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void glDeleteTextures(int arg0, IntBuffer arg1) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glDeleteTextures(arg0, arg1);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void glDeleteVertexArrays(int arg0, IntBuffer arg1) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glDeleteVertexArrays(arg0, arg1);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void glDepthFunc(int arg0) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glDepthFunc(arg0);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void glDepthMask(boolean arg0) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glDepthMask(arg0);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void glDisable(int arg0) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glDisable(arg0);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void glDisableVertexAttribArray(int arg0) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glDisableVertexAttribArray(arg0);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void glDrawArrays(int arg0, int arg1, int arg2) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glDrawArrays(arg0, arg1, arg2);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void glDrawBuffer(int arg0) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glDrawBuffer(arg0);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void glDrawBuffers(int arg0, IntBuffer arg1) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glDrawBuffers(arg0, arg1);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void glDrawElements(int arg0, int arg1, int arg2, int arg3) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glDrawElements(arg0, arg1, arg2, arg3);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void glEnable(int arg0) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glEnable(arg0);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void glEnableVertexAttribArray(int arg0) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glEnableVertexAttribArray(arg0);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void glFlush() {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glFlush();

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void glFramebufferRenderbuffer(int arg0, int arg1, int arg2,
			int arg3) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glFramebufferRenderbuffer(arg0, arg1, arg2, arg3);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void glFramebufferTexture(int arg0, int arg1, int arg2,
			int arg3) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glFramebufferTexture(arg0, arg1, arg2, arg3);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void glFramebufferTexture2D(int arg0, int arg1, int arg2,
			int arg3, int arg4) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glFramebufferTexture2D(arg0, arg1, arg2, arg3, arg4);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void glGenBuffers(int arg0, IntBuffer arg1) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glGenBuffers(arg0, arg1);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void glGenerateMipmap(int arg0) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glGenerateMipmap(arg0);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void glGenFramebuffers(int arg0, IntBuffer arg1) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glGenFramebuffers(arg0, arg1);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void glGenRenderbuffers(int arg0, IntBuffer arg1) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glGenRenderbuffers(arg0, arg1);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void glGenSamplers(int arg0, IntBuffer arg1) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glGenSamplers(arg0, arg1);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void glGenTextures(int arg0, IntBuffer arg1) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glGenTextures(arg0, arg1);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void glGenVertexArrays(int arg0, IntBuffer arg1) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glGenVertexArrays(arg0, arg1);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void glGetIntegerv(int arg0, IntBuffer arg1) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glGetIntegerv(arg0, arg1);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void glGetProgramInfoLog(int arg0, int arg1, IntBuffer arg2,
			ByteBuffer arg3) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glGetProgramInfoLog(arg0, arg1, arg2, arg3);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void glGetProgramiv(int arg0, int arg1, IntBuffer arg2) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glGetProgramiv(arg0, arg1, arg2);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void glGetShaderInfoLog(int arg0, int arg1, IntBuffer arg2,
			ByteBuffer arg3) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glGetShaderInfoLog(arg0, arg1, arg2, arg3);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void glGetShaderiv(int arg0, int arg1, IntBuffer arg2) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glGetShaderiv(arg0, arg1, arg2);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void glGetTexImage(int arg0, int arg1, int arg2, int arg3,
			Buffer arg4) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glGetTexImage(arg0, arg1, arg2, arg3, arg4);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static int glGetUniformLocation(int arg0, String arg1) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		final int ret = gl3.glGetUniformLocation(arg0, arg1);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}

		return ret;
	}
	public static void glLinkProgram(int arg0) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glLinkProgram(arg0);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static ByteBuffer glMapBuffer(int arg0, int arg1) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		final ByteBuffer ret = gl3.glMapBuffer(arg0, arg1);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}

		return ret;
	}
	public static void glReadBuffer(int arg0) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glReadBuffer(arg0);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void glReadPixels(int arg0, int arg1, int arg2, int arg3,
			int arg4, int arg5, Buffer arg6) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glReadPixels(arg0, arg1, arg2, arg3, arg4, arg5, arg6);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void glRenderbufferStorage(int arg0, int arg1, int arg2,
			int arg3) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glRenderbufferStorage(arg0, arg1, arg2, arg3);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void glSamplerParameteri(int arg0, int arg1, int arg2) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glSamplerParameteri(arg0, arg1, arg2);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void glShaderSource(int arg0, int arg1, String[] arg2,
			IntBuffer arg3) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glShaderSource(arg0, arg1, arg2, arg3);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void glStencilFunc(int arg0, int arg1, int arg2) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glStencilFunc(arg0, arg1, arg2);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void glStencilOp(int arg0, int arg1, int arg2) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glStencilOp(arg0, arg1, arg2);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void glTexImage2D(int arg0, int arg1, int arg2, int arg3,
			int arg4, int arg5, int arg6, int arg7, Buffer arg8) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glTexImage2D(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void glTexParameterf(int arg0, int arg1, float arg2) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glTexParameterf(arg0, arg1, arg2);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void glTexParameterfv(int arg0, int arg1, FloatBuffer arg2) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glTexParameterfv(arg0, arg1, arg2);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void glTexParameteri(int arg0, int arg1, int arg2) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glTexParameteri(arg0, arg1, arg2);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void glUniform1f(int arg0, float arg1) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glUniform1f(arg0, arg1);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void glUniform1fv(int arg0, int arg1, FloatBuffer arg2) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glUniform1fv(arg0, arg1, arg2);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void glUniform1i(int arg0, int arg1) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glUniform1i(arg0, arg1);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void glUniform1iv(int arg0, int arg1, IntBuffer arg2) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glUniform1iv(arg0, arg1, arg2);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void glUniform2f(int arg0, float arg1, float arg2) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glUniform2f(arg0, arg1, arg2);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void glUniform2fv(int arg0, int arg1, FloatBuffer arg2) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glUniform2fv(arg0, arg1, arg2);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void glUniform2i(int arg0, int arg1, int arg2) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glUniform2i(arg0, arg1, arg2);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void glUniform2iv(int arg0, int arg1, IntBuffer arg2) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glUniform2iv(arg0, arg1, arg2);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void glUniform3f(int arg0, float arg1, float arg2,
			float arg3) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glUniform3f(arg0, arg1, arg2, arg3);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void glUniform3fv(int arg0, int arg1, FloatBuffer arg2) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glUniform3fv(arg0, arg1, arg2);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void glUniform3i(int arg0, int arg1, int arg2, int arg3) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glUniform3i(arg0, arg1, arg2, arg3);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void glUniform3iv(int arg0, int arg1, IntBuffer arg2) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glUniform3iv(arg0, arg1, arg2);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void glUniform4f(int arg0, float arg1, float arg2, float arg3,
			float arg4) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glUniform4f(arg0, arg1, arg2, arg3, arg4);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void glUniform4fv(int arg0, int arg1, FloatBuffer arg2) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glUniform4fv(arg0, arg1, arg2);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void glUniform4i(int arg0, int arg1, int arg2, int arg3,
			int arg4) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glUniform4i(arg0, arg1, arg2, arg3, arg4);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void glUniform4iv(int arg0, int arg1, IntBuffer arg2) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glUniform4iv(arg0, arg1, arg2);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void glUniformMatrix2fv(int arg0, int arg1, boolean arg2,
			FloatBuffer arg3) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glUniformMatrix2fv(arg0, arg1, arg2, arg3);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void glUniformMatrix3fv(int arg0, int arg1, boolean arg2,
			FloatBuffer arg3) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glUniformMatrix3fv(arg0, arg1, arg2, arg3);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void glUniformMatrix4fv(int arg0, int arg1, boolean arg2,
			FloatBuffer arg3) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glUniformMatrix4fv(arg0, arg1, arg2, arg3);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static boolean glUnmapBuffer(int arg0) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		final boolean ret = gl3.glUnmapBuffer(arg0);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}

		return ret;
	}
	public static void glUseProgram(int arg0) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glUseProgram(arg0);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void glVertexAttribPointer(int arg0, int arg1, int arg2,
			boolean arg3, int arg4, long arg5) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glVertexAttribPointer(arg0, arg1, arg2, arg3, arg4, arg5);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void glViewport(int arg0, int arg1, int arg2, int arg3) {
		final GL3 gl3 = GLContext.getCurrentGL().getGL3();
		gl3.glViewport(arg0, arg1, arg2, arg3);

		final int code = gl3.glGetError();
		if (code != GL.GL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
}
