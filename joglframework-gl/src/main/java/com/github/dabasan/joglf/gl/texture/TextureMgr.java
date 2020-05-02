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
	private static Logger logger=LoggerFactory.getLogger(TextureMgr.class);
	
	private static int count=0;
	private static Map<Integer, Texture> textures_map=new HashMap<>();
	
	private static int default_texture_handle=-1;
	
	private static boolean generate_mipmap_flag=true;
	
	public static void Initialize() {
		default_texture_handle=LoadTexture("./Data/Texture/white.bmp");
		logger.info("TextureMgr initialized.");
	}
	
	public static int LoadTexture(String texture_filename) {
		File file=new File(texture_filename);
		if(!(file.isFile()&&file.canRead())) {
			logger.error("Failed to load a texture. texture_filename={}",texture_filename);
			return -1;
		}
		
		Texture texture=null;
		try {
			texture=TextureIO.newTexture(file,true);
		}
		catch(IOException e) {
			logger.error("Error while creating a texture.",e);
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
		
		int texture_handle=count;
		textures_map.put(texture_handle, texture);
		count++;
		
		return texture_handle;
	}
	
	public static int DeleteTexture(int texture_handle) {
		if(textures_map.containsKey(texture_handle)==false) {
			logger.warn("No such texture. texture_handle={}",texture_handle);
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
			logger.warn("No such texture. texture_handle={}",texture_handle);
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
				GL4.GL_TEXTURE_2D, 0, GL4.GL_RGBA, 
				width, height, 0, GL4.GL_RGBA, GL4.GL_UNSIGNED_BYTE, flipped_data);
		GLWrapper.glBindTexture(GL4.GL_TEXTURE_2D, 0);
		
		return 0;
	}
	
	public static boolean GetMustFlipVertically(int texture_handle) {
		if(textures_map.containsKey(texture_handle)==false) {
			logger.warn("No such texture. texture_handle={}",texture_handle);
			return false;
		}
		
		Texture texture=textures_map.get(texture_handle);
		return texture.getMustFlipVertically();
	}
	
	public static int GetTextureWidth(int texture_handle) {
		if(textures_map.containsKey(texture_handle)==false) {
			logger.warn("No such texture. texture_handle={}",texture_handle);
			return -1;
		}
		
		Texture texture=textures_map.get(texture_handle);
		int width=texture.getWidth();
		
		return width;
	}
	public static int GetTextureHeight(int texture_handle) {
		if(textures_map.containsKey(texture_handle)==false) {
			logger.warn("No such texture. texture_handle={}",texture_handle);
			return -1;
		}
		
		Texture texture=textures_map.get(texture_handle);
		int height=texture.getHeight();
		
		return height;
	}
	
	public static int AssociateTexture(int texture_object,int texture_width,int texture_height,boolean flip_vertically) {
		int texture_handle=count;
		Texture texture=new Texture(texture_object, GL4.GL_TEXTURE_2D, 
				texture_width, texture_height, texture_width, texture_height, flip_vertically);
		
		textures_map.put(texture_handle, texture);
		count++;
		
		return texture_handle;
	}
	
	public static ByteBuffer GetTextureImage(int texture_handle) {
		if(textures_map.containsKey(texture_handle)==false) {
			logger.warn("No such texture. texture_handle={}",texture_handle);
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
	
	public static void SetGenerateMipmapFlag(boolean flag) {
		generate_mipmap_flag=flag;
	}
	
	public static int BindTexture(int texture_handle) {
		if(textures_map.containsKey(texture_handle)==false) {
			texture_handle=default_texture_handle;
		}
		
		GL gl=GLContext.getCurrentGL();
		Texture texture=textures_map.get(texture_handle);
		
		texture.bind(gl);
		
		return 0;
	}
	public static void UnbindTexture() {
		GLWrapper.glBindTexture(GL4.GL_TEXTURE_2D, 0);
	}
}
