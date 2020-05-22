package com.github.dabasan.joglf.gl.text;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.dabasan.basis.coloru8.ColorU8;
import com.github.dabasan.joglf.gl.window.WindowCommonInfo;
import com.jogamp.graph.curve.Region;
import com.jogamp.graph.curve.opengl.RegionRenderer;
import com.jogamp.graph.curve.opengl.RenderState;
import com.jogamp.graph.curve.opengl.TextRegionUtil;
import com.jogamp.graph.font.Font;
import com.jogamp.graph.font.FontFactory;
import com.jogamp.graph.geom.SVertex;
import com.jogamp.opengl.GL2ES2;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;
import com.jogamp.opengl.util.PMVMatrix;

/**
 * Text manager<br>
 * <br>
 * A backward-compatible profile is required to draw texts with this class
 * because it depends on GL2 implementation.
 * 
 * @author Daba
 *
 */
public class TextMgr {
	private static Logger logger = LoggerFactory.getLogger(TextMgr.class);

	private static int count = 0;
	private static Map<Integer, Font> fonts_map = new HashMap<>();

	private static RenderState render_state;
	private static RegionRenderer region_renderer;
	private static TextRegionUtil text_region_util;

	private static int default_font_handle;

	private static int window_width = WindowCommonInfo.DEFAULT_WIDTH;
	private static int window_height = WindowCommonInfo.DEFAULT_HEIGHT;

	/**
	 * Initialization<br>
	 * You don't have to call this method by yourself.
	 */
	public static void Initialize() {
		render_state = RenderState.createRenderState(SVertex.factory());
		render_state.setColorStatic(1.0f, 1.0f, 1.0f, 1.0f);
		render_state.setHintMask(RenderState.BITHINT_GLOBAL_DEPTH_TEST_ENABLED);

		region_renderer = RegionRenderer.create(render_state, RegionRenderer.defaultBlendEnable,
				RegionRenderer.defaultBlendDisable);

		text_region_util = new TextRegionUtil(Region.VARWEIGHT_RENDERING_BIT);

		final GL2ES2 gl = GLContext.getCurrentGL().getGL2ES2();
		region_renderer.init(gl, Region.VARWEIGHT_RENDERING_BIT);
		region_renderer.enable(gl, false);

		default_font_handle = CreateDefaultFont();

		logger.info("TextureMgr initialized.");
	}
	private static int CreateDefaultFont() {
		Font font = null;
		try {
			font = FontFactory.get(FontFactory.JAVA).getDefault();
		} catch (final IOException e) {
			logger.error("Error while creating a font.", e);
			return -1;
		}

		final int font_handle = count;
		count++;

		fonts_map.put(font_handle, font);

		return font_handle;
	}

	/**
	 * Loads a font.
	 * 
	 * @param font_filename
	 *            Filename
	 * @return -1 on error and font handle on success
	 */
	public static int LoadFont(String font_filename) {
		Font font = null;
		try {
			font = FontFactory.get(new File(font_filename));
		} catch (final IOException e) {
			logger.error("Error while creating a font.", e);
			return -1;
		}

		final int font_handle = count;
		count++;

		fonts_map.put(font_handle, font);

		return font_handle;
	}

	public static int DeleteFont(int font_handle) {
		if (fonts_map.containsKey(font_handle) == false) {
			logger.warn("No such font. font_handle={}", font_handle);
			return -1;
		}
		if (font_handle == default_font_handle) {
			logger.warn("Cannot delete the default font.");
			return -1;
		}

		fonts_map.remove(font_handle);

		return 0;
	}

	/**
	 * Sets the window size.<br>
	 * This method is automatically called in JOGLFWindow and JOGLFSwingWindow.
	 * 
	 * @param width
	 *            Width
	 * @param height
	 *            Height
	 */
	public static void SetWindowSize(int width, int height) {
		window_width = width;
		window_height = height;
	}

	/**
	 * Draws text with the default font.
	 * 
	 * @param x
	 *            Bottom left x
	 * @param y
	 *            Bottom left y
	 * @param text
	 *            Text
	 * @param color
	 *            Color
	 * @param size
	 *            Size
	 * @param resolution
	 *            Display resolution in DPI
	 * @return 0
	 */
	public static int DrawText(int x, int y, String text, ColorU8 color, float size,
			float resolution) {
		final Font font = fonts_map.get(default_font_handle);
		innerDrawText(x, y, text, font, color, size, resolution);

		return 0;
	}
	/**
	 * Draws text with a font specified.
	 * 
	 * @param x
	 *            Bottom left x
	 * @param y
	 *            Bottom left y
	 * @param text
	 *            Text
	 * @param font_handle
	 *            Font handle
	 * @param color
	 *            Color
	 * @param size
	 *            Size
	 * @param resolution
	 *            Display resolution in DPI
	 * @return 0
	 */
	public static int DrawTextWithFont(int x, int y, String text, int font_handle, ColorU8 color,
			float size, float resolution) {
		if (fonts_map.containsKey(font_handle) == false) {
			font_handle = default_font_handle;
		}

		final Font font = fonts_map.get(font_handle);
		innerDrawText(x, y, text, font, color, size, resolution);

		return 0;
	}
	private static void innerDrawText(int x, int y, String text, Font font, ColorU8 color,
			float size, float resolution) {
		final float pixel_size = font.getPixelSize(size, resolution);

		final PMVMatrix pmv = region_renderer.getMatrix();
		pmv.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
		pmv.glLoadIdentity();
		pmv.glTranslatef(x, y, -1.0f);

		render_state.setColorStatic(color.GetR(), color.GetG(), color.GetB(), color.GetA());

		final GL2ES2 gl = GLContext.getCurrentGL().getGL2ES2();

		final int[] sample_count = new int[]{4};
		region_renderer.enable(gl, true);
		region_renderer.reshapeOrtho(window_width, window_height, 0.1f, 1000.0f);
		text_region_util.drawString3D(gl, region_renderer, font, pixel_size, text, null,
				sample_count);
		region_renderer.enable(gl, false);
	}
}
