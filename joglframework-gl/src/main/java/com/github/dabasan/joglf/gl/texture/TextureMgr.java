package com.github.dabasan.joglf.gl.texture;

import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.dabasan.joglf.gl.wrapper.GLWrapper;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

/**
 * Texture manager
 * 
 * @author Daba
 *
 */
public class TextureMgr {
	private static Logger logger = LoggerFactory.getLogger(TextureMgr.class);

	private static int count = 0;
	private static Map<Integer, Texture> textures_map = new HashMap<>();

	private static int default_texture_handle = -1;

	private static boolean generate_mipmap_flag = true;

	public static void Initialize() {
		default_texture_handle = LoadTexture("./Data/Texture/white.bmp");
		logger.info("TextureMgr initialized.");
	}

	public static int LoadTexture(String texture_filename) {
		final File file = new File(texture_filename);
		if (!(file.isFile() && file.canRead())) {
			logger.error("Failed to load a texture. texture_filename={}",
					texture_filename);
			return -1;
		}

		Texture texture = null;
		try {
			texture = TextureIO.newTexture(file, true);
		} catch (final IOException e) {
			logger.error("Error while creating a texture.", e);
			return -1;
		}

		final GL gl = GLContext.getCurrentGL();
		texture.bind(gl);
		GLWrapper.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S,
				GL.GL_REPEAT);
		GLWrapper.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T,
				GL.GL_REPEAT);
		if (generate_mipmap_flag == true) {
			GLWrapper.glGenerateMipmap(GL.GL_TEXTURE_2D);
			GLWrapper.glTexParameteri(GL.GL_TEXTURE_2D,
					GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR_MIPMAP_LINEAR);
		} else {
			GLWrapper.glTexParameteri(GL.GL_TEXTURE_2D,
					GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
		}
		GLWrapper.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER,
				GL.GL_LINEAR);

		final int texture_handle = count;
		textures_map.put(texture_handle, texture);
		count++;

		return texture_handle;
	}

	public static int DeleteTexture(int texture_handle) {
		if (textures_map.containsKey(texture_handle) == false) {
			logger.warn("No such texture. texture_handle={}", texture_handle);
			return -1;
		}

		final GL gl = GLContext.getCurrentGL();
		final Texture texture = textures_map.get(texture_handle);
		texture.destroy(gl);

		textures_map.remove(texture_handle);

		return 0;
	}
	public static void DeleteAllTextures() {
		final GL gl = GLContext.getCurrentGL();

		for (final Texture texture : textures_map.values()) {
			texture.destroy(gl);
		}

		textures_map.clear();
	}

	public static int FlipTexture(int texture_handle, boolean flip_vertically,
			boolean flip_horizontally) {
		if (textures_map.containsKey(texture_handle) == false) {
			logger.warn("No such texture. texture_handle={}", texture_handle);
			return -1;
		}

		final GL gl = GLContext.getCurrentGL();
		final Texture texture = textures_map.get(texture_handle);
		final int texture_object = texture.getTextureObject(gl);
		final int width = texture.getWidth();
		final int height = texture.getHeight();

		final ByteBuffer data = Buffers.newDirectByteBuffer(width * height * 4);

		GLWrapper.glBindTexture(GL.GL_TEXTURE_2D, texture_object);
		GLWrapper.glGetTexImage(GL.GL_TEXTURE_2D, 0, GL.GL_RGBA,
				GL.GL_UNSIGNED_BYTE, data);
		GLWrapper.glBindTexture(GL.GL_TEXTURE_2D, 0);

		final ByteBuffer data_r = Buffers.newDirectByteBuffer(width * height);
		final ByteBuffer data_g = Buffers.newDirectByteBuffer(width * height);
		final ByteBuffer data_b = Buffers.newDirectByteBuffer(width * height);
		final ByteBuffer data_a = Buffers.newDirectByteBuffer(width * height);

		final int bound = width * height * 4;
		for (int i = 0; i < bound; i += 4) {
			data_r.put(data.get());
			data_g.put(data.get());
			data_b.put(data.get());
			data_a.put(data.get());
		}
		((Buffer) data_r).flip();
		((Buffer) data_g).flip();
		((Buffer) data_b).flip();
		((Buffer) data_a).flip();

		final ByteBuffer flipped_data = Buffers
				.newDirectByteBuffer(width * height * 4);

		if (flip_vertically == true && flip_horizontally == true) {
			for (int y = height - 1; y >= 0; y--) {
				for (int x = width - 1; x >= 0; x--) {
					flipped_data.put(data_r.get(y * width + x));
					flipped_data.put(data_g.get(y * width + x));
					flipped_data.put(data_b.get(y * width + x));
					flipped_data.put(data_a.get(y * width + x));
				}
			}
		} else if (flip_vertically == true) {
			for (int y = height - 1; y >= 0; y--) {
				for (int x = 0; x < width; x++) {
					flipped_data.put(data_r.get(y * width + x));
					flipped_data.put(data_g.get(y * width + x));
					flipped_data.put(data_b.get(y * width + x));
					flipped_data.put(data_a.get(y * width + x));
				}
			}
		} else if (flip_horizontally == true) {
			for (int y = 0; y < height; y++) {
				for (int x = width - 1; x >= 0; x--) {
					flipped_data.put(data_r.get(y * width + x));
					flipped_data.put(data_g.get(y * width + x));
					flipped_data.put(data_b.get(y * width + x));
					flipped_data.put(data_a.get(y * width + x));
				}
			}
		} else {
			return 0;
		}
		((Buffer) flipped_data).flip();

		GLWrapper.glBindTexture(GL.GL_TEXTURE_2D, texture_object);
		GLWrapper.glTexImage2D(GL.GL_TEXTURE_2D, 0, GL.GL_RGBA, width, height,
				0, GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, flipped_data);
		GLWrapper.glBindTexture(GL.GL_TEXTURE_2D, 0);

		return 0;
	}

	public static boolean GetMustFlipVertically(int texture_handle) {
		if (textures_map.containsKey(texture_handle) == false) {
			logger.warn("No such texture. texture_handle={}", texture_handle);
			return false;
		}

		final Texture texture = textures_map.get(texture_handle);
		return texture.getMustFlipVertically();
	}

	public static int GetTextureWidth(int texture_handle) {
		if (textures_map.containsKey(texture_handle) == false) {
			logger.warn("No such texture. texture_handle={}", texture_handle);
			return -1;
		}

		final Texture texture = textures_map.get(texture_handle);
		final int width = texture.getWidth();

		return width;
	}
	public static int GetTextureHeight(int texture_handle) {
		if (textures_map.containsKey(texture_handle) == false) {
			logger.warn("No such texture. texture_handle={}", texture_handle);
			return -1;
		}

		final Texture texture = textures_map.get(texture_handle);
		final int height = texture.getHeight();

		return height;
	}

	public static int AssociateTexture(int texture_object, int texture_width,
			int texture_height, boolean flip_vertically) {
		final int texture_handle = count;
		final Texture texture = new Texture(texture_object, GL.GL_TEXTURE_2D,
				texture_width, texture_height, texture_width, texture_height,
				flip_vertically);

		textures_map.put(texture_handle, texture);
		count++;

		return texture_handle;
	}

	public static ByteBuffer GetTextureImage(int texture_handle) {
		if (textures_map.containsKey(texture_handle) == false) {
			logger.warn("No such texture. texture_handle={}", texture_handle);
			return Buffers.newDirectByteBuffer(0);
		}

		final GL gl = GLContext.getCurrentGL();
		final Texture texture = textures_map.get(texture_handle);
		final int texture_object = texture.getTextureObject(gl);
		final int width = texture.getWidth();
		final int height = texture.getHeight();

		final ByteBuffer data = Buffers.newDirectByteBuffer(width * height * 4);

		GLWrapper.glBindTexture(GL.GL_TEXTURE_2D, texture_object);
		GLWrapper.glGetTexImage(GL.GL_TEXTURE_2D, 0, GL.GL_RGBA,
				GL.GL_UNSIGNED_BYTE, data);
		GLWrapper.glBindTexture(GL.GL_TEXTURE_2D, 0);

		return data;
	}

	public static boolean TextureExists(int texture_handle) {
		return textures_map.containsKey(texture_handle);
	}

	public static void SetGenerateMipmapFlag(boolean flag) {
		generate_mipmap_flag = flag;
	}

	public static int BindTexture(int texture_handle) {
		if (textures_map.containsKey(texture_handle) == false) {
			texture_handle = default_texture_handle;
		}

		final GL gl = GLContext.getCurrentGL();
		final Texture texture = textures_map.get(texture_handle);

		texture.bind(gl);

		return 0;
	}
	public static void UnbindTexture() {
		GLWrapper.glBindTexture(GL.GL_TEXTURE_2D, 0);
	}
}
