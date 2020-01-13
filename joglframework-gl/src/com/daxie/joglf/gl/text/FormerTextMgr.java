package com.daxie.joglf.gl.text;

import java.awt.Font;
import java.util.HashMap;
import java.util.Map;

import com.daxie.basis.coloru8.ColorU8;
import com.daxie.log.LogFile;
import com.jogamp.opengl.util.awt.TextRenderer;

/**
 * TextMgr that uses TextRenderer
 * @author Daba
 *
 */
public class FormerTextMgr {
	private static int count=0;
	private static Map<Integer, Font> fonts_map=new HashMap<>();
	
	private static int window_width=640;
	private static int window_height=480;
	
	public static int CreateFont(String font_name,int style,int size) {
		Font font=new Font(font_name, style, size);
		
		int font_handle=count;
		count++;
		
		fonts_map.put(font_handle, font);
		
		return font_handle;
	}
	
	public static int DeleteFont(int font_handle) {
		if(fonts_map.containsKey(font_handle)==false) {
			LogFile.WriteWarn("[FormerTextMgr-DeleteFont] No such font. font_handle:"+font_handle, true);
			return -1;
		}
		
		fonts_map.remove(font_handle);
		
		return 0;
	}
	
	public static void SetWindowSize(int width,int height) {
		window_width=width;
		window_height=height;
	}
	
	public static int DrawTextWithFont(int x,int y,String text,int font_handle,ColorU8 color) {
		if(fonts_map.containsKey(font_handle)==false) {
			LogFile.WriteWarn("[FormerTextMgr-DrawTextWithFont] No such font. font_handle:"+font_handle, true);
			return -1;
		}
		
		Font font=fonts_map.get(font_handle);
		
		TextRenderer text_renderer=new TextRenderer(font);
		text_renderer.beginRendering(window_width, window_height);
		text_renderer.setColor(color.GetR(), color.GetG(), color.GetB(), color.GetA());
		text_renderer.setSmoothing(true);
		text_renderer.draw(text, x, y);
		text_renderer.endRendering();
		
		return 0;
	}
}
