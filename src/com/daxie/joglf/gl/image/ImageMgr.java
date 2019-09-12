package com.daxie.joglf.gl.image;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.daxie.joglf.log.LogFile;
import com.daxie.joglf.tool.ExceptionFunctions;
import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

/**
 * Image manager
 * @author Daba
 *
 */
public class ImageMgr {
	private static int count=0;
	private static Map<Integer, Texture> images_map=new HashMap<>();
	
	public static int LoadImage(String image_filename) {
		File file=new File(image_filename);
		if(!(file.isFile()&&file.canRead())) {
			LogFile.WriteError("[ImageMgr-LoadImage] Failed to load an image.", true);
			return -1;
		}
		
		int image_handle=count;
		try {
			Texture image=TextureIO.newTexture(file,true);
			images_map.put(image_handle, image);
		}
		catch(IOException e) {
			String str=ExceptionFunctions.GetPrintStackTraceString(e);
			
			LogFile.WriteError("[ImageMgr-LoadImage] Below is the stack trace.",true);
			LogFile.WriteError(str,false);
			
			return -1;
		}
		
		count++;
		
		return image_handle;
	}
	public static int DeleteImage(int image_handle) {
		if(images_map.containsKey(image_handle)==false) {
			LogFile.WriteError("[ImageMgr-DeleteImage] No such image. image_handle:"+image_handle, true);
			return -1;
		}
		
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		Texture image=images_map.get(image_handle);
		image.destroy(gl4);
		
		images_map.remove(image_handle);
		
		return 0;
	}
	public static void DeleteAllImages() {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		for(Texture image:images_map.values()) {
			image.destroy(gl4);
		}
		
		images_map.clear();
	}
	
	public static boolean ImageExists(int image_handle) {
		return images_map.containsKey(image_handle);
	}
	
	public static int EnableImage(int image_handle) {
		if(images_map.containsKey(image_handle)==false) {
			LogFile.WriteError("[ImageMgr-EnableImage] No such image. image_handle:"+image_handle, true);
			return -1;
		}
		
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		Texture image=images_map.get(image_handle);
		
		image.enable(gl4);
		
		return 0;
	}
	public static int BindImage(int image_handle) {
		if(images_map.containsKey(image_handle)==false) {
			LogFile.WriteError("[ImageMgr-BindImage] No such image. image_handle:"+image_handle, true);
			return -1;
		}
		
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		Texture image=images_map.get(image_handle);
		
		image.bind(gl4);
		
		return 0;
	}
	public static int DisableImage(int image_handle) {
		if(images_map.containsKey(image_handle)==false) {
			LogFile.WriteError("[ImageMgr-DisableImage] No such image. image_handle:"+image_handle, true);
			return -1;
		}
		
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		Texture image=images_map.get(image_handle);
		
		image.disable(gl4);
		
		return 0;
	}
}
