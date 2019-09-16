package com.daxie.joglf.gl.texture;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.daxie.joglf.log.LogFile;
import com.daxie.joglf.tool.ExceptionFunctions;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

/**
 * Texture manager
 * @author Daba
 *
 */
public class TextureMgr {
	private static int count=0;
	private static Map<Integer, Texture> images_map=new HashMap<>();
	
	private static int default_texture_handle=-1;
	
	public static void LoadDefaultTexture() {
		default_texture_handle=LoadTexture("./Data/Texture/white.bmp");
	}
	
	public static int LoadTexture(String image_filename) {
		File file=new File(image_filename);
		if(!(file.isFile()&&file.canRead())) {
			LogFile.WriteError("[TextureMgr-LoadTexture] Failed to load an image.", true);
			return -1;
		}
		
		int texture_handle=count;
		try {
			Texture image=TextureIO.newTexture(file,true);
			images_map.put(texture_handle, image);
		}
		catch(IOException e) {
			String str=ExceptionFunctions.GetPrintStackTraceString(e);
			
			LogFile.WriteError("[TextureMgr-LoadTexture] Below is the stack trace.",true);
			LogFile.WriteError(str,false);
			
			return -1;
		}
		
		count++;
		
		return texture_handle;
	}
	public static int DeleteTexture(int texture_handle) {
		if(images_map.containsKey(texture_handle)==false) {
			LogFile.WriteError("[TextureMgr-DeleteTexture] No such image. texture_handle:"+texture_handle, true);
			return -1;
		}
		
		GL gl=GLContext.getCurrentGL();
		Texture image=images_map.get(texture_handle);
		image.destroy(gl);
		
		images_map.remove(texture_handle);
		
		return 0;
	}
	
	public static boolean TextureExists(int texture_handle) {
		return images_map.containsKey(texture_handle);
	}
	
	public static int EnableTexture(int texture_handle) {
		if(images_map.containsKey(texture_handle)==false) {
			LogFile.WriteError("[TextureMgr-EnableTexture] No such image. texture_handle:"+texture_handle, true);
			return -1;
		}
		
		GL gl=GLContext.getCurrentGL();
		Texture image=images_map.get(texture_handle);
		
		image.enable(gl);
		
		return 0;
	}
	public static int BindTexture(int texture_handle) {
		if(images_map.containsKey(texture_handle)==false) {
			LogFile.WriteError("[TextureMgr-BindTexture] No such image. texture_handle:"+texture_handle, true);
			return -1;
		}
		
		GL gl=GLContext.getCurrentGL();
		Texture image=images_map.get(texture_handle);
		
		image.bind(gl);
		
		return 0;
	}
	public static int DisableTexture(int texture_handle) {
		if(images_map.containsKey(texture_handle)==false) {
			LogFile.WriteError("[TextureMgr-DisableTexture] No such image. texture_handle:"+texture_handle, true);
			return -1;
		}
		
		GL gl=GLContext.getCurrentGL();
		Texture image=images_map.get(texture_handle);
		
		image.disable(gl);
		
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
