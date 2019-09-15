package com.daxie.joglf.gl.text;

import java.awt.Font;
import java.util.HashMap;
import java.util.Map;

import com.daxie.joglf.basis.coloru8.ColorU8;
import com.daxie.joglf.basis.vector.Vector;
import com.daxie.joglf.gl.GLFront;
import com.daxie.joglf.log.LogFile;
import com.jogamp.opengl.util.awt.TextRenderer;

/**
 * Text manager
 * @author Daba
 *
 */
public class TextMgr {
	private static int count=0;
	private static Map<Integer, Font> fonts_map=new HashMap<>();
	
	public static int CreateFont(String font_name,int weight,int size) {
		Font font=new Font(font_name, weight, size);
		
		int font_handle=count;
		count++;
		
		fonts_map.put(font_handle, font);
		
		return font_handle;
	}
	public static int DeleteFont(int font_handle) {
		if(fonts_map.containsKey(font_handle)==false) {
			LogFile.WriteError("[FontMgr-DeleteFont] No such font. font_handle:"+font_handle, true);
			return -1;
		}
		
		fonts_map.remove(font_handle);
		
		return 0;
	}
	public static void DeleteAllFonts() {
		fonts_map.clear();
	}
	
	public static int DrawText(int x,int y,String text,ColorU8 color,int font_handle,boolean antialiased_flag) {
		if(fonts_map.containsKey(font_handle)==false) {
			LogFile.WriteError("[FontMgr-DrawText] No such font. font_handle:"+font_handle, true);
			return -1;
		}
		
		int window_width=GLFront.GetWindowWidth();
		int window_height=GLFront.GetWindowHeight();
		
		Font font=fonts_map.get(font_handle);
		TextRenderer tr=new TextRenderer(font,antialiased_flag,true);
		tr.beginRendering(window_width, window_height);
		tr.setColor(color.GetR(), color.GetG(), color.GetB(), color.GetA());
		tr.draw(text, x, y);
		tr.endRendering();
		
		return 0;
	}
	public static int DrawText3D(Vector position,String text,ColorU8 color,int font_handle,boolean antialiased_flag) {
		if(fonts_map.containsKey(font_handle)==false) {
			LogFile.WriteError("[FontMgr-DrawText3D] No such font. font_handle:"+font_handle, true);
			return -1;
		}
		
		Font font=fonts_map.get(font_handle);
		TextRenderer tr=new TextRenderer(font,antialiased_flag,true);
		tr.begin3DRendering();
		tr.setColor(color.GetR(), color.GetG(), color.GetB(), color.GetA());
		tr.draw3D(text, position.GetX(), position.GetY(), position.GetZ(), 1.0f);
		tr.end3DRendering();
		
		return 0;
	}
}
