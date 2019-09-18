package com.daxie.joglf.gl.texture;

import java.io.File;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;

import com.daxie.joglf.gl.GLFront;
import com.daxie.joglf.gl.wrapper.GLShaderFunctions;
import com.daxie.joglf.gl.wrapper.GLWrapper;
import com.daxie.log.LogFile;
import com.daxie.tool.ExceptionFunctions;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL4;
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
	
	public static int DrawTexture(int texture_handle,int x,int y,int width,int height) {
		if(textures_map.containsKey(texture_handle)==false) {
			LogFile.WriteWarn("[TextureMgr-DrawTexture] No such texture. texture_handle:"+texture_handle, true);
			return -1;
		}
		
		GL gl=GLContext.getCurrentGL();
		Texture texture=textures_map.get(texture_handle);
		
		IntBuffer indices=Buffers.newDirectIntBuffer(6);
		FloatBuffer pos_buffer=Buffers.newDirectFloatBuffer(2*4);
		FloatBuffer uv_buffer=Buffers.newDirectFloatBuffer(2*4);
		
		indices.put(0);
		indices.put(1);
		indices.put(2);
		indices.put(2);
		indices.put(3);
		indices.put(0);
		
		int window_width=GLFront.GetWindowWidth();
		int window_height=GLFront.GetWindowHeight();
		
		float normalized_x=(float)x/window_width-1.0f;
		float normalized_y=(float)y/window_height-1.0f;
		float normalized_width=(float)width/window_width*2.0f;
		float normalized_height=(float)height/window_height*2.0f;
		
		//Bottom left
		pos_buffer.put(normalized_x);
		pos_buffer.put(normalized_y);
		uv_buffer.put(0.0f);
		uv_buffer.put(1.0f);
		//Bottom right
		pos_buffer.put(normalized_x+normalized_width);
		pos_buffer.put(normalized_y);
		uv_buffer.put(1.0f);
		uv_buffer.put(1.0f);
		//Top right
		pos_buffer.put(normalized_x+normalized_width);
		pos_buffer.put(normalized_y+normalized_height);
		uv_buffer.put(1.0f);
		uv_buffer.put(0.0f);
		//Top left
		pos_buffer.put(normalized_x);
		pos_buffer.put(normalized_y+normalized_height);
		uv_buffer.put(0.0f);
		uv_buffer.put(0.0f);
		
		indices.flip();
		pos_buffer.flip();
		uv_buffer.flip();
		
		GLShaderFunctions.EnableProgram("texture_drawer");
		
		int sampler=GLShaderFunctions.GetSampler();
		GLWrapper.glBindSampler(0, sampler);
		
		IntBuffer indices_vbo=Buffers.newDirectIntBuffer(1);
		IntBuffer pos_vbo=Buffers.newDirectIntBuffer(1);
		IntBuffer uv_vbo=Buffers.newDirectIntBuffer(1);
		IntBuffer vao=Buffers.newDirectIntBuffer(1);
		
		GLWrapper.glGenBuffers(1, indices_vbo);
		GLWrapper.glGenBuffers(1, pos_vbo);
		GLWrapper.glGenBuffers(1, uv_vbo);
		GLWrapper.glGenVertexArrays(1, vao);
		
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, pos_vbo.get(0));
		GLWrapper.glBufferData(GL4.GL_ARRAY_BUFFER, 
				Buffers.SIZEOF_FLOAT*pos_buffer.capacity(), pos_buffer, GL4.GL_STATIC_DRAW);
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, uv_vbo.get(0));
		GLWrapper.glBufferData(GL4.GL_ARRAY_BUFFER, 
				Buffers.SIZEOF_FLOAT*uv_buffer.capacity(), uv_buffer, GL4.GL_STATIC_DRAW);
		
		GLWrapper.glBindVertexArray(vao.get(0));
		
		GLWrapper.glBindBuffer(GL4.GL_ELEMENT_ARRAY_BUFFER, indices_vbo.get(0));
		GLWrapper.glBufferData(GL4.GL_ELEMENT_ARRAY_BUFFER, 
				Buffers.SIZEOF_INT*indices.capacity(), indices, GL4.GL_STATIC_DRAW);
		
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, pos_vbo.get(0));
		GLWrapper.glEnableVertexAttribArray(0);
		GLWrapper.glVertexAttribPointer(0, 2, GL4.GL_FLOAT, false, Buffers.SIZEOF_FLOAT*2, 0);
		
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, uv_vbo.get(0));
		GLWrapper.glEnableVertexAttribArray(1);
		GLWrapper.glVertexAttribPointer(1, 2, GL4.GL_FLOAT, false, Buffers.SIZEOF_FLOAT*2, 0);
		
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, 0);
		
		texture.enable(gl);
		texture.bind(gl);
		GLWrapper.glDrawElements(GL4.GL_TRIANGLES, 6, GL4.GL_UNSIGNED_INT, 0);
		texture.disable(gl);
		
		GLWrapper.glBindVertexArray(0);
		
		GLWrapper.glDeleteBuffers(1, pos_vbo);
		GLWrapper.glDeleteBuffers(1, uv_vbo);
		GLWrapper.glDeleteVertexArrays(1, vao);
		
		return 0;
	}
}
