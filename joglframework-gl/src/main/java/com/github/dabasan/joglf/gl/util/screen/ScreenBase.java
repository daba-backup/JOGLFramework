package com.github.dabasan.joglf.gl.util.screen;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.dabasan.basis.coloru8.ColorU8;
import com.github.dabasan.basis.coloru8.ColorU8Functions;
import com.github.dabasan.joglf.gl.shader.ShaderProgram;
import com.github.dabasan.joglf.gl.texture.TextureMgr;
import com.github.dabasan.joglf.gl.transferrer.FullscreenQuadTransferrerWithUV;
import com.github.dabasan.joglf.gl.wrapper.GLWrapper;
import com.github.dabasan.tool.FilenameFunctions;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL;

/**
 * Base class for screens
 * 
 * @author Daba
 *
 */
public class ScreenBase {
	private final Logger logger = LoggerFactory.getLogger(ScreenBase.class);

	private final int screen_width;
	private final int screen_height;
	private ColorU8 background_color;

	private int fbo_id;
	private int texture_id;
	private int texture_handle;
	private final ShaderProgram program;
	private final FullscreenQuadTransferrerWithUV transferrer;

	/**
	 * 
	 * @param width
	 *            Screen width
	 * @param height
	 *            Screen height
	 */
	public ScreenBase(int width, int height) {
		screen_width = width;
		screen_height = height;
		background_color = ColorU8Functions.GetColorU8(0.0f, 0.0f, 0.0f, 0.0f);

		this.SetupTexture();
		this.SetupFramebuffer();
		texture_handle = -1;

		program = new ShaderProgram("texture_drawer");
		transferrer = new FullscreenQuadTransferrerWithUV(true);
	}
	private void SetupTexture() {
		final IntBuffer texture_ids = Buffers.newDirectIntBuffer(1);
		GLWrapper.glGenTextures(1, texture_ids);
		texture_id = texture_ids.get(0);

		GLWrapper.glBindTexture(GL.GL_TEXTURE_2D, texture_id);
		GLWrapper.glTexImage2D(GL.GL_TEXTURE_2D, 0, GL.GL_RGBA, screen_width, screen_height, 0,
				GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, null);
		GLWrapper.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
		GLWrapper.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
		GLWrapper.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT);
		GLWrapper.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_REPEAT);
		GLWrapper.glBindTexture(GL.GL_TEXTURE_2D, 0);
	}
	private void SetupFramebuffer() {
		final IntBuffer fbo_ids = Buffers.newDirectIntBuffer(1);
		GLWrapper.glGenFramebuffers(1, fbo_ids);
		fbo_id = fbo_ids.get(0);

		GLWrapper.glBindFramebuffer(GL.GL_FRAMEBUFFER, fbo_id);
		GLWrapper.glFramebufferTexture2D(GL.GL_FRAMEBUFFER, GL.GL_COLOR_ATTACHMENT0,
				GL.GL_TEXTURE_2D, texture_id, 0);
		final IntBuffer draw_buffers = Buffers
				.newDirectIntBuffer(new int[]{GL.GL_COLOR_ATTACHMENT0});
		GLWrapper.glDrawBuffers(1, draw_buffers);
		GLWrapper.glBindFramebuffer(GL.GL_FRAMEBUFFER, 0);
	}

	public void Dispose() {
		final IntBuffer fbo_ids = Buffers.newDirectIntBuffer(new int[]{fbo_id});
		final IntBuffer texture_ids = Buffers.newDirectIntBuffer(new int[]{texture_id});
		GLWrapper.glDeleteFramebuffers(1, fbo_ids);
		if (texture_handle != -1) {
			TextureMgr.DeleteTexture(texture_handle);
		} else {
			GLWrapper.glDeleteTextures(1, texture_ids);
		}
	}

	public int GetScreenWidth() {
		return screen_width;
	}
	public int GetScreenHeight() {
		return screen_height;
	}
	public ColorU8 GetBackgroundColor() {
		return new ColorU8(background_color);
	}

	protected int GetFBOID() {
		return fbo_id;
	}
	protected int GetTextureID() {
		return texture_id;
	}
	protected int GetTextureHandle() {
		return texture_handle;
	}
	protected ShaderProgram GetProgram() {
		return program;
	}
	protected FullscreenQuadTransferrerWithUV GetTransferrer() {
		return transferrer;
	}

	public void SetBackgroundColor(ColorU8 color) {
		background_color = color;
	}

	/**
	 * Enables the screen.
	 */
	public void Enable() {
		GLWrapper.glBindFramebuffer(GL.GL_FRAMEBUFFER, fbo_id);
		GLWrapper.glViewport(0, 0, screen_width, screen_height);
	}
	/**
	 * Disables the screen.
	 */
	public void Disable() {
		GLWrapper.glBindFramebuffer(GL.GL_FRAMEBUFFER, 0);
	}
	/**
	 * Clears the screen.
	 */
	public void Clear() {
		GLWrapper.glClearColor(background_color.GetR(), background_color.GetG(),
				background_color.GetB(), background_color.GetA());
		GLWrapper.glClear(GL.GL_DEPTH_BUFFER_BIT | GL.GL_COLOR_BUFFER_BIT);
	}

	/**
	 * Associates the underlying texture object with TextureMgr.
	 * 
	 * @param flip_vertically
	 *            Flip vertically
	 * @return Texture handle
	 */
	public int Associate(boolean flip_vertically) {
		texture_handle = TextureMgr.AssociateTexture(texture_id, screen_width, screen_height,
				flip_vertically);
		return texture_handle;
	}

	/**
	 * Draws the current contents of the screen.
	 */
	public void Draw() {
		program.Enable();

		GLWrapper.glActiveTexture(GL.GL_TEXTURE0);
		GLWrapper.glBindTexture(GL.GL_TEXTURE_2D, texture_id);
		program.SetUniform("texture_sampler", 0);

		transferrer.Transfer();
	}
	/**
	 * Draws the current contents of the screen onto the specified rect.
	 * 
	 * @param x
	 *            Bottom left x
	 * @param y
	 *            Bottom left y
	 * @param width
	 *            Width
	 * @param height
	 *            Height
	 */
	public void Draw(int x, int y, int width, int height) {
		GLWrapper.glViewport(x, y, width, height);
		this.Draw();
	}

	/**
	 * Binds the screen texture.
	 */
	public void BindScreenTexture() {
		GLWrapper.glBindTexture(GL.GL_TEXTURE_2D, texture_id);
	}
	/**
	 * Unbinds the screen texture.
	 */
	public void UnbindScreenTexture() {
		GLWrapper.glBindTexture(GL.GL_TEXTURE_2D, 0);
	}

	/**
	 * Takes a screenshot of the screen.
	 * 
	 * @param filename
	 *            Screenshot filename
	 * @return -1 on error and 0 on success
	 */
	public int TakeScreenshot(String filename) {
		logger.info("Taking a screenshot. filename={}", filename);

		final ByteBuffer data = Buffers.newDirectByteBuffer(screen_width * screen_height * 4);

		GLWrapper.glBindTexture(GL.GL_TEXTURE_2D, texture_id);
		GLWrapper.glGetTexImage(GL.GL_TEXTURE_2D, 0, GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, data);
		GLWrapper.glBindTexture(GL.GL_TEXTURE_2D, 0);

		final BufferedImage image = new BufferedImage(screen_width, screen_height,
				BufferedImage.TYPE_3BYTE_BGR);

		int pos = 0;
		for (int y = screen_height - 1; y >= 0; y--) {
			for (int x = 0; x < screen_width; x++) {
				final int r = Byte.toUnsignedInt(data.get(pos));
				final int g = Byte.toUnsignedInt(data.get(pos + 1));
				final int b = Byte.toUnsignedInt(data.get(pos + 2));
				final int a = Byte.toUnsignedInt(data.get(pos + 3));

				final int rgb = (a << 24) | (r << 16) | (g << 8) | b;

				image.setRGB(x, y, rgb);

				pos += 4;
			}
		}

		final String extension = FilenameFunctions.GetFileExtension(filename);
		try {
			ImageIO.write(image, extension, new File(filename));
		} catch (final IOException e) {
			logger.error("Error while writing.", e);
			return -1;
		}

		return 0;
	}
}
