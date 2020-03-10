package com.daxie.joglf.gl.texture;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.daxie.joglf.gl.shader.GLShaderFunctions;
import com.daxie.joglf.gl.window.WindowCommonInfoStock;
import com.daxie.joglf.gl.wrapper.GLWrapper;
import com.daxie.log.LogWriter;
import com.daxie.tool.ExceptionFunctions;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.util.awt.ImageUtil;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;

/**
 * Texture manager
 * @author Daba
 *
 */
public class TextureMgr {
	private static int count=0;
	private static Map<Integer, Texture> textures_map=new HashMap<>();
	
	private static int default_texture_handle=-1;
	
	private static int window_width=WindowCommonInfoStock.DEFAULT_WIDTH;
	private static int window_height=WindowCommonInfoStock.DEFAULT_HEIGHT;
	
	private static boolean generate_mipmap_flag=true;
	
	public static void Initialize() {
		default_texture_handle=LoadTexture("./Data/Texture/white.bmp");
		
		LogWriter.WriteInfo("[TextureMgr-Initialize] TextureMgr initialized.", true);
	}
	
	public static int LoadTexture(String texture_filename) {
		File file=new File(texture_filename);
		if(!(file.isFile()&&file.canRead())) {
			LogWriter.WriteWarn("[TextureMgr-LoadTexture] Failed to load a texture. filename:"+texture_filename, true);
			return -1;
		}
		
		int texture_handle=count;
		Texture texture=null;
		try {
			texture=TextureIO.newTexture(file,true);
			if(texture.getMustFlipVertically()==true) {
				BufferedImage image=ImageIO.read(file);
				ImageUtil.flipImageVertically(image);
				
				GL gl=GLContext.getCurrentGL();
				texture=AWTTextureIO.newTexture(gl.getGLProfile(), image, true);
			}
		}
		catch(IOException e) {
			String str=ExceptionFunctions.GetPrintStackTraceString(e);
			
			LogWriter.WriteWarn("[TextureMgr-LoadTexture] Below is the stack trace.",true);
			LogWriter.WriteWarn(str,false);
			
			return -1;
		}
		
		GL gl=GLContext.getCurrentGL();
		texture.enable(gl);
		texture.bind(gl);
		GLWrapper.glTexParameteri(GL4.GL_TEXTURE_2D, GL4.GL_TEXTURE_WRAP_S, GL4.GL_REPEAT);
		GLWrapper.glTexParameteri(GL4.GL_TEXTURE_2D, GL4.GL_TEXTURE_WRAP_T, GL4.GL_REPEAT);
		if(generate_mipmap_flag==true) {
			GLWrapper.glGenerateMipmap(GL4.GL_TEXTURE_2D);
			GLWrapper.glTexParameteri(GL4.GL_TEXTURE_2D, GL4.GL_TEXTURE_MIN_FILTER, GL4.GL_NEAREST_MIPMAP_NEAREST);
		}
		else {
			GLWrapper.glTexParameteri(GL4.GL_TEXTURE_2D, GL4.GL_TEXTURE_MIN_FILTER, GL4.GL_NEAREST);
		}
		GLWrapper.glTexParameteri(GL4.GL_TEXTURE_2D, GL4.GL_TEXTURE_MAG_FILTER, GL4.GL_NEAREST);
		texture.disable(gl);
		
		textures_map.put(texture_handle, texture);
		count++;
		
		return texture_handle;
	}
	
	public static int DeleteTexture(int texture_handle) {
		if(textures_map.containsKey(texture_handle)==false) {
			LogWriter.WriteWarn("[TextureMgr-DeleteTexture] No such texture. texture_handle:"+texture_handle, true);
			return -1;
		}
		
		GL gl=GLContext.getCurrentGL();
		Texture texture=textures_map.get(texture_handle);
		texture.destroy(gl);
		
		textures_map.remove(texture_handle);
		
		return 0;
	}
	public static void DeleteAllTextures() {
		GL gl=GLContext.getCurrentGL();
		
		for(Texture texture:textures_map.values()) {
			texture.destroy(gl);
		}
		
		textures_map.clear();
	}
	
	public static int FlipTexture(int texture_handle,boolean flip_vertically,boolean flip_horizontally) {
		if(textures_map.containsKey(texture_handle)==false) {
			LogWriter.WriteWarn("[TextureMgr-FlipTexture] No such texture. texture_handle:"+texture_handle, true);
			return -1;
		}
		
		GL gl=GLContext.getCurrentGL();
		Texture texture=textures_map.get(texture_handle);
		int texture_object=texture.getTextureObject(gl);
		int width=texture.getWidth();
		int height=texture.getHeight();
			
		ByteBuffer data=Buffers.newDirectByteBuffer(width*height*4);
		
		GLWrapper.glBindTexture(GL4.GL_TEXTURE_2D, texture_object);
		GLWrapper.glGetTexImage(GL4.GL_TEXTURE_2D, 0, GL4.GL_RGBA, GL4.GL_UNSIGNED_BYTE, data);
		GLWrapper.glBindTexture(GL4.GL_TEXTURE_2D, 0);
		
		ByteBuffer data_r=Buffers.newDirectByteBuffer(width*height);
		ByteBuffer data_g=Buffers.newDirectByteBuffer(width*height);
		ByteBuffer data_b=Buffers.newDirectByteBuffer(width*height);
		ByteBuffer data_a=Buffers.newDirectByteBuffer(width*height);
		
		int bound=width*height*4;
		for(int i=0;i<bound;i+=4) {
			data_r.put(data.get());
			data_g.put(data.get());
			data_b.put(data.get());
			data_a.put(data.get());
		}
		((Buffer)data_r).flip();
		((Buffer)data_g).flip();
		((Buffer)data_b).flip();
		((Buffer)data_a).flip();
		
		ByteBuffer flipped_data=Buffers.newDirectByteBuffer(width*height*4);
		
		if(flip_vertically==true&&flip_horizontally==true) {
			for(int y=height-1;y>=0;y--) {
				for(int x=width-1;x>=0;x--) {
					flipped_data.put(data_r.get(y*width+x));
					flipped_data.put(data_g.get(y*width+x));
					flipped_data.put(data_b.get(y*width+x));
					flipped_data.put(data_a.get(y*width+x));
				}
			}
		}
		else if(flip_vertically==true) {
			for(int y=height-1;y>=0;y--) {
				for(int x=0;x<width;x++) {
					flipped_data.put(data_r.get(y*width+x));
					flipped_data.put(data_g.get(y*width+x));
					flipped_data.put(data_b.get(y*width+x));
					flipped_data.put(data_a.get(y*width+x));
				}
			}
		}
		else if(flip_horizontally==true) {
			for(int y=0;y<height;y++) {
				for(int x=width-1;x>=0;x--) {
					flipped_data.put(data_r.get(y*width+x));
					flipped_data.put(data_g.get(y*width+x));
					flipped_data.put(data_b.get(y*width+x));
					flipped_data.put(data_a.get(y*width+x));
				}
			}
		}
		else {
			return 0;
		}
		((Buffer)flipped_data).flip();
		
		GLWrapper.glBindTexture(GL4.GL_TEXTURE_2D, texture_object);
		GLWrapper.glTexImage2D(
				GL4.GL_TEXTURE_2D, 0,GL4.GL_RGBA, 
				width, height, 0, GL4.GL_RGBA, GL4.GL_UNSIGNED_BYTE, flipped_data);
		GLWrapper.glBindTexture(GL4.GL_TEXTURE_2D, 0);
		
		return 0;
	}
	
	public static int AssociateTexture(int texture_id,int texture_width,int texture_height,boolean flip_vertically) {
		int texture_handle=count;
		Texture texture=new Texture(texture_id, GL4.GL_TEXTURE_2D, 
				texture_width, texture_height, texture_width, texture_height, flip_vertically);
		
		textures_map.put(texture_handle, texture);
		count++;
		
		return texture_handle;
	}
	
	public static ByteBuffer GetTextureImage(int texture_handle) {
		if(textures_map.containsKey(texture_handle)==false) {
			LogWriter.WriteWarn("[TextureMgr-GetTextureImage] No such texture. texture_handle:"+texture_handle, true);
			return Buffers.newDirectByteBuffer(0);
		}
		
		GL gl=GLContext.getCurrentGL();
		Texture texture=textures_map.get(texture_handle);
		int texture_object=texture.getTextureObject(gl);
		int width=texture.getWidth();
		int height=texture.getHeight();
			
		ByteBuffer data=Buffers.newDirectByteBuffer(width*height*4);
		
		GLWrapper.glBindTexture(GL4.GL_TEXTURE_2D, texture_object);
		GLWrapper.glGetTexImage(GL4.GL_TEXTURE_2D, 0, GL4.GL_RGBA, GL4.GL_UNSIGNED_BYTE, data);
		GLWrapper.glBindTexture(GL4.GL_TEXTURE_2D, 0);
		
		return data;
	}
	
	public static boolean TextureExists(int texture_handle) {
		return textures_map.containsKey(texture_handle);
	}
	
	public static void SetWindowSize(int width,int height) {
		window_width=width;
		window_height=height;
	}
	
	public static void SetGenerateMipmapFlag(boolean flag) {
		generate_mipmap_flag=flag;
	}
	
	public static int EnableTexture(int texture_handle) {
		if(textures_map.containsKey(texture_handle)==false) {
			LogWriter.WriteWarn("[TextureMgr-EnableTexture] No such texture. texture_handle:"+texture_handle, true);
			return -1;
		}
		
		GL gl=GLContext.getCurrentGL();
		Texture texture=textures_map.get(texture_handle);
		
		texture.enable(gl);
		
		return 0;
	}
	public static int BindTexture(int texture_handle) {
		if(textures_map.containsKey(texture_handle)==false) {
			LogWriter.WriteWarn("[TextureMgr-BindTexture] No such texture. texture_handle:"+texture_handle, true);
			return -1;
		}
		
		GL gl=GLContext.getCurrentGL();
		Texture texture=textures_map.get(texture_handle);
		
		texture.bind(gl);
		
		return 0;
	}
	public static int DisableTexture(int texture_handle) {
		if(textures_map.containsKey(texture_handle)==false) {
			LogWriter.WriteWarn("[TextureMgr-DisableTexture] No such texture. texture_handle:"+texture_handle, true);
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
	
	public static int DrawTexture(
			int texture_handle,int x,int y,int width,int height,
			float bottom_left_u,float bottom_left_v,
			float bottom_right_u,float bottom_right_v,
			float top_right_u,float top_right_v,
			float top_left_u,float top_left_v) {
		if(textures_map.containsKey(texture_handle)==false) {
			LogWriter.WriteWarn("[TextureMgr-DrawTexture] No such texture. texture_handle:"+texture_handle, true);
			return -1;
		}
		
		GL gl=GLContext.getCurrentGL();
		Texture texture=textures_map.get(texture_handle);
		
		IntBuffer indices=Buffers.newDirectIntBuffer(6);
		FloatBuffer pos_buffer=Buffers.newDirectFloatBuffer(8);
		FloatBuffer uv_buffer=Buffers.newDirectFloatBuffer(8);
		
		indices.put(0);
		indices.put(1);
		indices.put(2);
		indices.put(2);
		indices.put(3);
		indices.put(0);
		
		float normalized_x=2.0f*x/window_width-1.0f;
		float normalized_y=2.0f*y/window_height-1.0f;
		float normalized_width=(float)width/window_width*2.0f;
		float normalized_height=(float)height/window_height*2.0f;
		
		//Bottom left
		pos_buffer.put(normalized_x);
		pos_buffer.put(normalized_y);
		uv_buffer.put(bottom_left_u);
		uv_buffer.put(bottom_left_v);
		//Bottom right
		pos_buffer.put(normalized_x+normalized_width);
		pos_buffer.put(normalized_y);
		uv_buffer.put(bottom_right_u);
		uv_buffer.put(bottom_right_v);
		//Top right
		pos_buffer.put(normalized_x+normalized_width);
		pos_buffer.put(normalized_y+normalized_height);
		uv_buffer.put(top_right_u);
		uv_buffer.put(top_right_v);
		//Top left
		pos_buffer.put(normalized_x);
		pos_buffer.put(normalized_y+normalized_height);
		uv_buffer.put(top_left_u);
		uv_buffer.put(top_left_v);
		
		((Buffer)indices).flip();
		((Buffer)pos_buffer).flip();
		((Buffer)uv_buffer).flip();
		
		GLShaderFunctions.UseProgram("texture_drawer");
		
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
		GLWrapper.glEnable(GL4.GL_BLEND);
		GLWrapper.glDrawElements(GL4.GL_TRIANGLES, 6, GL4.GL_UNSIGNED_INT, 0);
		GLWrapper.glDisable(GL4.GL_BLEND);
		texture.disable(gl);
		
		GLWrapper.glBindVertexArray(0);
		
		GLWrapper.glDeleteBuffers(1, pos_vbo);
		GLWrapper.glDeleteBuffers(1, uv_vbo);
		GLWrapper.glDeleteVertexArrays(1, vao);
		
		return 0;
	}
	public static int DrawTexture(int texture_handle,int x,int y,int width,int height) {
		int ret=DrawTexture(
				texture_handle, x, y, width, height, 
				0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f);
		
		return ret;
	}
}
