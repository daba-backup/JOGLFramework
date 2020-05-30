package com.github.dabasan.joglf.gl.util.screen;

import java.nio.IntBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.dabasan.joglf.gl.wrapper.GLWrapper;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL4;

/**
 * Screen with a depth texture
 * 
 * @author Daba
 *
 */
public class ScreenWithDepth extends ScreenBase {
	private final Logger logger = LoggerFactory.getLogger(ScreenWithDepth.class);

	private int depth_texture_id;

	/**
	 * 
	 * @param width
	 *            Screen width
	 * @param height
	 *            Screen height
	 */
	public ScreenWithDepth(int width, int height) {
		super(width, height);

		this.SetupDepthTexture(width, height);
		this.SetupFramebuffer();
	}
	private void SetupDepthTexture(int width, int height) {
		IntBuffer texture_ids = Buffers.newDirectIntBuffer(1);
		GLWrapper.glGenTextures(1, texture_ids);
		depth_texture_id = texture_ids.get(0);

		GLWrapper.glBindTexture(GL4.GL_TEXTURE_2D, depth_texture_id);
		GLWrapper.glTexImage2D(GL4.GL_TEXTURE_2D, 0, GL4.GL_DEPTH_COMPONENT, width, height, 0,
				GL4.GL_DEPTH_COMPONENT, GL4.GL_FLOAT, null);
		GLWrapper.glTexParameteri(GL4.GL_TEXTURE_2D, GL4.GL_TEXTURE_MAG_FILTER, GL4.GL_LINEAR);
		GLWrapper.glTexParameteri(GL4.GL_TEXTURE_2D, GL4.GL_TEXTURE_MIN_FILTER, GL4.GL_LINEAR);
		GLWrapper.glTexParameteri(GL4.GL_TEXTURE_2D, GL4.GL_TEXTURE_WRAP_S, GL4.GL_CLAMP_TO_EDGE);
		GLWrapper.glTexParameteri(GL4.GL_TEXTURE_2D, GL4.GL_TEXTURE_WRAP_T, GL4.GL_CLAMP_TO_EDGE);
		GLWrapper.glBindTexture(GL4.GL_TEXTURE_2D, 0);
	}
	private void SetupFramebuffer() {
		int fbo_id = this.GetFBOID();

		GLWrapper.glBindFramebuffer(GL.GL_FRAMEBUFFER, fbo_id);
		GLWrapper.glFramebufferTexture2D(GL4.GL_FRAMEBUFFER, GL4.GL_DEPTH_ATTACHMENT,
				GL4.GL_TEXTURE_2D, depth_texture_id, 0);
		final int status = GLWrapper.glCheckFramebufferStatus(GL.GL_FRAMEBUFFER);
		if (status != GL.GL_FRAMEBUFFER_COMPLETE) {
			logger.warn("Incomplete framebuffer. status={}", status);
		}
		GLWrapper.glBindFramebuffer(GL.GL_FRAMEBUFFER, 0);
	}

	@Override
	public void Dispose() {
		super.Dispose();

		final IntBuffer texture_ids = Buffers.newDirectIntBuffer(new int[]{depth_texture_id});
		GLWrapper.glDeleteTextures(1, texture_ids);
	}

	public void BindDepthTexture() {
		GLWrapper.glBindTexture(GL4.GL_TEXTURE_2D, depth_texture_id);
	}
	public void UnbindDepthTexture() {
		GLWrapper.glBindTexture(GL4.GL_TEXTURE_2D, 0);
	}
}
