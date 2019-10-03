package com.daxie.joglf.gl.text;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.daxie.basis.coloru8.ColorU8;
import com.daxie.log.LogFile;
import com.daxie.tool.ExceptionFunctions;
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
 * Text manager
 * @author Daba
 *
 */
public class TextMgr {
	private static int count=0;
	private static Map<Integer, Font> fonts_map=new HashMap<>();
	
	private static RenderState render_state;
	private static RegionRenderer region_renderer;
	private static TextRegionUtil text_region_util;
	
	private static int default_font_handle;
	
	private static int window_width=640;
	private static int window_height=480;
	
	public static void Initialize() {
		render_state=RenderState.createRenderState(SVertex.factory());
		render_state.setColorStatic(1.0f, 1.0f, 1.0f, 1.0f);
		render_state.setHintMask(RenderState.BITHINT_GLOBAL_DEPTH_TEST_ENABLED);
	
		region_renderer=RegionRenderer.create(render_state, RegionRenderer.defaultBlendEnable, RegionRenderer.defaultBlendDisable);
		
		text_region_util=new TextRegionUtil(Region.VARWEIGHT_RENDERING_BIT);
		
		GL2ES2 gl=GLContext.getCurrentGL().getGL2ES2();
		region_renderer.init(gl, Region.VARWEIGHT_RENDERING_BIT);
		region_renderer.enable(gl, false);
		
		default_font_handle=CreateDefaultFont();
		
		LogFile.WriteInfo("[TextMgr-Initialize] TextMgr initialized.", true);
	}
	private static int CreateDefaultFont() {
		Font font=null;
		try {
			font=FontFactory.get(FontFactory.JAVA).getDefault();
		}
		catch(IOException e) {
			String str=ExceptionFunctions.GetPrintStackTraceString(e);
			
			LogFile.WriteWarn("[TextureMgr-CreateDefaultFont] Below is the stack trace.", true);
			LogFile.WriteWarn(str, false);
			
			return -1;
		}
		
		int font_handle=count;
		count++;
		
		fonts_map.put(font_handle, font);
		
		return font_handle;
	}
	
	public static int LoadFont(String font_filename) {
		Font font=null;
		try {
			font=FontFactory.get(new File(font_filename));
		}
		catch(IOException e) {
			String str=ExceptionFunctions.GetPrintStackTraceString(e);
			
			LogFile.WriteWarn("[TextureMgr-LoadFont] Below is the stack trace.", true);
			LogFile.WriteWarn(str, false);
			
			return -1;
		}
		
		int font_handle=count;
		count++;
		
		fonts_map.put(font_handle, font);
		
		return font_handle;
	}
	
	public static int DeleteFont(int font_handle) {
		if(fonts_map.containsKey(font_handle)==false) {
			LogFile.WriteWarn("[FontMgr-DeleteFont] No such font. font_handle:"+font_handle, true);
			return -1;
		}
		if(font_handle==default_font_handle) {
			LogFile.WriteWarn("[FontMgr-DeleteFont] Cannot delete the default font.", true);
			return -1;
		}
		
		fonts_map.remove(font_handle);
		
		return 0;
	}
	
	public static void SetWindowSize(int width,int height) {
		window_width=width;
		window_height=height;
	}
	
	public static int DrawText(int x,int y,String text,ColorU8 color,int size,int weight) {
		Font font=fonts_map.get(default_font_handle);
		innerDrawText(x, y, text, font, color, size, weight);
		
		return 0;
	}
	public static int DrawTextWithFont(int x,int y,String text,int font_handle,ColorU8 color,int size,int weight) {
		if(fonts_map.containsKey(font_handle)==false) {
			LogFile.WriteWarn("[TextMgr-DrawTextWithFont] No such font. font_handle:"+font_handle,true);
			return -1;
		}
		
		Font font=fonts_map.get(font_handle);
		innerDrawText(x, y, text, font, color, size, weight);
		
		return 0;
	}
	private static void innerDrawText(int x,int y,String text,Font font,ColorU8 color,int size,int weight) {
		float pixel_size=font.getPixelSize(size,weight);
		
		PMVMatrix pmv=region_renderer.getMatrix();
		pmv.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
		pmv.glLoadIdentity();
		pmv.glTranslatef((float)x, (float)y, -1.0f);
		
		render_state.setColorStatic(color.GetR(), color.GetG(), color.GetB(), color.GetA());
		
		GL2ES2 gl=GLContext.getCurrentGL().getGL2ES2();
		
		int[] sample_count=new int[] {4};
		region_renderer.enable(gl,true);
		region_renderer.reshapeOrtho(window_width, window_height, 0.1f, 1000.0f);
		text_region_util.drawString3D(gl, region_renderer, font, pixel_size, text, null, sample_count);
		region_renderer.enable(gl, false);
	}
}
