package com.github.dabasan.joglf.gl.util.screen;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.dabasan.joglf.gl.shader.ShaderProgram;
import com.github.dabasan.joglf.gl.texture.TextureMgr;
import com.github.dabasan.joglf.gl.transferrer.FullscreenQuadTransferrerWithUV;
import com.github.dabasan.joglf.gl.wrapper.GLWrapper;
import com.daxie.tool.FilenameFunctions;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL4;

/**
 * Screen
 * @author Daba
 *
 */
public class Screen {
	private Logger logger=LoggerFactory.getLogger(Screen.class);
	
	private int fbo_id;
	private int renderbuffer_id;
	private int texture_id;
	
	private int screen_width;
	private int screen_height;
	
	private ShaderProgram program;
	private FullscreenQuadTransferrerWithUV transferrer;
	
	private int texture_handle;
	
	public Screen(int width,int height) {
		screen_width=width;
		screen_height=height;
		
		this.SetupRenderbuffer();
		this.SetupTexture();
		this.SetupFramebuffer();
		
		program=new ShaderProgram("texture_drawer");
		transferrer=new FullscreenQuadTransferrerWithUV();
		
		texture_handle=-1;
	}
	private void SetupRenderbuffer() {
		IntBuffer renderbuffer_ids=Buffers.newDirectIntBuffer(1);
		GLWrapper.glGenRenderbuffers(1, renderbuffer_ids);
		renderbuffer_id=renderbuffer_ids.get(0);
		
		GLWrapper.glBindRenderbuffer(GL4.GL_RENDERBUFFER, renderbuffer_id);
		GLWrapper.glRenderbufferStorage(
				GL4.GL_RENDERBUFFER, GL4.GL_DEPTH_COMPONENT, 
				screen_width, screen_height);
		GLWrapper.glBindRenderbuffer(GL4.GL_RENDERBUFFER, 0);
	}
	private void SetupTexture() {
		IntBuffer texture_ids=Buffers.newDirectIntBuffer(1);
		GLWrapper.glGenTextures(1, texture_ids);
		texture_id=texture_ids.get(0);
		
		GLWrapper.glBindTexture(GL4.GL_TEXTURE_2D, texture_id);
		GLWrapper.glTexImage2D(
				GL4.GL_TEXTURE_2D, 0,GL4.GL_RGBA, 
				screen_width, screen_height, 0, GL4.GL_RGBA, GL4.GL_UNSIGNED_BYTE, null);
		GLWrapper.glTexParameteri(GL4.GL_TEXTURE_2D, GL4.GL_TEXTURE_MAG_FILTER, GL4.GL_NEAREST);
		GLWrapper.glTexParameteri(GL4.GL_TEXTURE_2D, GL4.GL_TEXTURE_MIN_FILTER, GL4.GL_NEAREST);
		GLWrapper.glTexParameteri(GL4.GL_TEXTURE_2D, GL4.GL_TEXTURE_WRAP_S, GL4.GL_REPEAT);
		GLWrapper.glTexParameteri(GL4.GL_TEXTURE_2D, GL4.GL_TEXTURE_WRAP_T, GL4.GL_REPEAT);
		GLWrapper.glBindTexture(GL4.GL_TEXTURE_2D, 0);
	}
	private void SetupFramebuffer() {
		IntBuffer fbo_ids=Buffers.newDirectIntBuffer(1);
		GLWrapper.glGenFramebuffers(1, fbo_ids);
		fbo_id=fbo_ids.get(0);
		
		GLWrapper.glBindFramebuffer(GL4.GL_FRAMEBUFFER, fbo_id);
		
		GLWrapper.glFramebufferTexture2D(
				GL4.GL_FRAMEBUFFER, GL4.GL_COLOR_ATTACHMENT0, 
				GL4.GL_TEXTURE_2D, texture_id, 0);
		GLWrapper.glFramebufferRenderbuffer(
				GL4.GL_FRAMEBUFFER, GL4.GL_DEPTH_ATTACHMENT, 
				GL4.GL_RENDERBUFFER, renderbuffer_id);
		IntBuffer draw_buffers=Buffers.newDirectIntBuffer(new int[] {GL4.GL_COLOR_ATTACHMENT0});
		GLWrapper.glDrawBuffers(1, draw_buffers);
		
		int status=GLWrapper.glCheckFramebufferStatus(GL4.GL_FRAMEBUFFER);
		if(status!=GL4.GL_FRAMEBUFFER_COMPLETE) {
			logger.warn("Incomplete framebuffer. status={}",status);
		}
		
		GLWrapper.glBindFramebuffer(GL4.GL_FRAMEBUFFER, 0);
	}
	
	public void Dispose() {
		IntBuffer fbo_ids=Buffers.newDirectIntBuffer(new int[] {fbo_id});
		IntBuffer renderbuffer_ids=Buffers.newDirectIntBuffer(new int[] {renderbuffer_id});
		IntBuffer texture_ids=Buffers.newDirectIntBuffer(new int[] {texture_id});
		
		GLWrapper.glDeleteFramebuffers(1, fbo_ids);
		GLWrapper.glDeleteRenderbuffers(1, renderbuffer_ids);
		if(texture_handle!=-1)TextureMgr.DeleteTexture(texture_handle);
		else GLWrapper.glDeleteTextures(1, texture_ids);
	}
	
	public int GetScreenWidth() {
		return screen_width;
	}
	public int GetScreenHeight() {
		return screen_height;
	}
	
	public void Bind() {
		GLWrapper.glBindFramebuffer(GL4.GL_FRAMEBUFFER, fbo_id);
	}
	public void Unbind() {
		GLWrapper.glBindFramebuffer(GL4.GL_FRAMEBUFFER, 0);
	}
	public void Clear() {
		GLWrapper.glClear(GL4.GL_DEPTH_BUFFER_BIT|GL4.GL_COLOR_BUFFER_BIT);
	}
	public void Fit() {
		GLWrapper.glViewport(0, 0, screen_width, screen_height);
	}
	
	public int Associate(boolean flip_vertically) {
		texture_handle=TextureMgr.AssociateTexture(texture_id, screen_width, screen_height, flip_vertically);
		return texture_handle;
	}
	
	public void Draw() {
		program.Enable();
		
		GLWrapper.glActiveTexture(GL4.GL_TEXTURE0);
		GLWrapper.glBindTexture(GL4.GL_TEXTURE_2D, texture_id);
		program.SetUniform("texture_sampler", 0);
		
		transferrer.Transfer();
	}
	public void Draw(int x,int y,int width,int height) {
		GLWrapper.glViewport(x, y, width, height);
		this.Draw();
	}
	
	public void BindScreenTexture() {
		GLWrapper.glBindTexture(GL4.GL_TEXTURE_2D, texture_id);
	}
	public void UnbindScreenTexture() {
		GLWrapper.glBindTexture(GL4.GL_TEXTURE_2D, 0);
	}
	
	public int TakeScreenshot(String filename) {
		ByteBuffer data=Buffers.newDirectByteBuffer(screen_width*screen_height*4);
		
		GLWrapper.glBindTexture(GL4.GL_TEXTURE_2D, texture_id);
		GLWrapper.glGetTexImage(GL4.GL_TEXTURE_2D, 0, GL4.GL_RGBA, GL4.GL_UNSIGNED_BYTE, data);
		GLWrapper.glBindTexture(GL4.GL_TEXTURE_2D, 0);
		
		BufferedImage image=new BufferedImage(screen_width, screen_height, BufferedImage.TYPE_3BYTE_BGR);
		
		int pos=0;
		for(int y=screen_height-1;y>=0;y--) {
			for(int x=0;x<screen_width;x++) {
				int r=Byte.toUnsignedInt(data.get(pos));
				int g=Byte.toUnsignedInt(data.get(pos+1));
				int b=Byte.toUnsignedInt(data.get(pos+2));
				int a=Byte.toUnsignedInt(data.get(pos+3));
				
				int rgb=(a<<24)|(r<<16)|(g<<8)|b;
				
				image.setRGB(x, y, rgb);
				
				pos+=4;
			}
		}
		
		String extension=FilenameFunctions.GetFileExtension(filename);
		try {
			ImageIO.write(image, extension, new File(filename));
		}
		catch(IOException e) {
			logger.error("Error while writing.",e);
			return -1;
		}
		
		return 0;
	}
}
