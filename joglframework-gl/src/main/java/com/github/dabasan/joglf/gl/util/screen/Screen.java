package com.github.dabasan.joglf.gl.util.screen;

import java.nio.IntBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.dabasan.joglf.gl.wrapper.GLWrapper;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2ES2;

/**
 * Screen
 * 
 * @author Daba
 *
 */
public class Screen extends ScreenBase {
	private final Logger logger = LoggerFactory.getLogger(Screen.class);

	private int renderbuffer_id;

	/**
	 * 
	 * @param width
	 *            Screen width
	 * @param height
	 *            Screen height
	 */
	public Screen(int width, int height) {
		super(width, height);

		this.SetupRenderbuffer();
		this.SetupFramebuffer();
	}
	private void SetupRenderbuffer() {
		final IntBuffer renderbuffer_ids = Buffers.newDirectIntBuffer(1);
		GLWrapper.glGenRenderbuffers(1, renderbuffer_ids);
		renderbuffer_id = renderbuffer_ids.get(0);

		GLWrapper.glBindRenderbuffer(GL.GL_RENDERBUFFER, renderbuffer_id);
		GLWrapper.glRenderbufferStorage(GL.GL_RENDERBUFFER, GL2ES2.GL_DEPTH_COMPONENT,
				this.GetScreenWidth(), this.GetScreenHeight());
		GLWrapper.glBindRenderbuffer(GL.GL_RENDERBUFFER, 0);
	}
	private void SetupFramebuffer() {
		final int fbo_id = this.GetFBOID();

		GLWrapper.glBindFramebuffer(GL.GL_FRAMEBUFFER, fbo_id);
		GLWrapper.glFramebufferRenderbuffer(GL.GL_FRAMEBUFFER, GL.GL_DEPTH_ATTACHMENT,
				GL.GL_RENDERBUFFER, renderbuffer_id);
		final int status = GLWrapper.glCheckFramebufferStatus(GL.GL_FRAMEBUFFER);
		if (status != GL.GL_FRAMEBUFFER_COMPLETE) {
			logger.warn("Incomplete framebuffer. status={}", status);
		}
		GLWrapper.glBindFramebuffer(GL.GL_FRAMEBUFFER, 0);
	}

	@Override
	public void Dispose() {
		super.Dispose();

		final IntBuffer renderbuffer_ids = Buffers.newDirectIntBuffer(new int[]{renderbuffer_id});
		GLWrapper.glDeleteRenderbuffers(1, renderbuffer_ids);
	}
}
