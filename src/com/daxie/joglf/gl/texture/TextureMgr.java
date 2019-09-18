package com.daxie.joglf.gl.texture;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.daxie.joglf.gl.GLFront;
import com.daxie.log.LogFile;
import com.daxie.tool.ExceptionFunctions;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.util.awt.TextureRenderer;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

/**
 * Texture manager
 * @author Daba
 *
 */
public class TextureMgr {
	private static int count=0;
	private static Map<Integer, Texture> textures_map=new HashMap<>();
	
	private static int default_texture_handle=-1;
	
	public static void LoadDefaultTexture() {
		default_texture_handle=LoadTexture("./Data/Texture/white.bmp");
	}
	
	public static int LoadTexture(String texture_filename) {
		File file=new File(texture_filename);
		if(!(file.isFile()&&file.canRead())) {
			LogFile.WriteWarn("[TextureMgr-LoadTexture] Failed to load a texture.", true);
			return -1;
		}
		
		int texture_handle=count;
		try {
			Texture texture=TextureIO.newTexture(file,true);
			textures_map.put(texture_handle, texture);
		}
		catch(IOException e) {
			String str=ExceptionFunctions.GetPrintStackTraceString(e);
			
			LogFile.WriteWarn("[TextureMgr-LoadTexture] Below is the stack trace.",true);
			LogFile.WriteWarn(str,false);
			
			return -1;
		}
		
		count++;
		
		return texture_handle;
	}
	public static int DeleteTexture(int texture_handle) {
		if(textures_map.containsKey(texture_handle)==false) {
			LogFile.WriteWarn("[TextureMgr-DeleteTexture] No such texture. texture_handle:"+texture_handle, true);
			return -1;
		}
		
		GL gl=GLContext.getCurrentGL();
		Texture texture=textures_map.get(texture_handle);
		texture.destroy(gl);
		
		textures_map.remove(texture_handle);
		
		return 0;
	}
	
	public static boolean TextureExists(int texture_handle) {
		return textures_map.containsKey(texture_handle);
	}
	
	public static int EnableTexture(int texture_handle) {
		if(textures_map.containsKey(texture_handle)==false) {
			LogFile.WriteWarn("[TextureMgr-EnableTexture] No such texture. texture_handle:"+texture_handle, true);
			return -1;
		}
		
		GL gl=GLContext.getCurrentGL();
		Texture texture=textures_map.get(texture_handle);
		
		texture.enable(gl);
		
		return 0;
	}
	public static int BindTexture(int texture_handle) {
		if(textures_map.containsKey(texture_handle)==false) {
			LogFile.WriteWarn("[TextureMgr-BindTexture] No such texture. texture_handle:"+texture_handle, true);
			return -1;
		}
		
		GL gl=GLContext.getCurrentGL();
		Texture texture=textures_map.get(texture_handle);
		
		texture.bind(gl);
		
		return 0;
	}
	public static int DisableTexture(int texture_handle) {
		if(textures_map.containsKey(texture_handle)==false) {
			LogFile.WriteWarn("[TextureMgr-DisableTexture] No such texture. texture_handle:"+texture_handle, true);
			return -1;
		}
		
		GL gl=GLContext.getCurrentGL();
		Texture texture=textures_map.get(texture_handle);
		
		texture.disable(gl);
		
		return 0;
	}
	
	public static void EnableDefaultTexture() {
		EnableTexture(default_texture_handle);
	}
	public static void BindDefaultTexture() {
		BindTexture(default_texture_handle);
	}
	public static void DisableDefaultTexture() {
		DisableTexture(default_texture_handle);
	}
}
