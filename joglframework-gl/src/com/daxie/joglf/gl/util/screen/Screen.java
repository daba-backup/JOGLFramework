package com.daxie.joglf.gl.util.screen;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import javax.imageio.ImageIO;

import com.daxie.joglf.gl.shader.ShaderProgram;
import com.daxie.joglf.gl.transferrer.FullscreenQuadTransferrerWithUV;
import com.daxie.joglf.gl.wrapper.GLWrapper;
import com.daxie.log.LogWriter;
import com.daxie.tool.ExceptionFunctions;
import com.daxie.tool.FilenameFunctions;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL4;

/**
 * Screen
 * @author Daba
 *
 */
public class Screen {
	private int fbo_id;
	private int renderbuffer_id;
	private int texture_id;
	
	private int screen_width;
	private int screen_height;
	
	private ShaderProgram program;
	private FullscreenQuadTransferrerWithUV transferrer;
	
	public Screen(int width,int height) {
		screen_width=width;
		screen_height=height;
		
		this.SetupRenderbuffer();
		this.SetupTexture();
		this.SetupFramebuffer();
		
		program=new ShaderProgram("texture_drawer");
		transferrer=new FullscreenQuadTransferrerWithUV();
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
		GLWrapper.glBindTexture(GL4.GL_TEXTURE_2D, texture_id);
		GLWrapper.glTexImage2D(
				GL4.GL_TEXTURE_2D, 0,GL4.GL_RGBA, 
				screen_width, screen_height, 0, GL4.GL_RGBA, GL4.GL_UNSIGNED_BYTE, null);
		GLWrapper.glTexParameteri(GL4.GL_TEXTURE_2D, GL4.GL_TEXTURE_MAG_FILTER, GL4.GL_NEAREST);
		GLWrapper.glTexParameteri(GL4.GL_TEXTURE_2D, GL4.GL_TEXTURE_MIN_FILTER, GL4.GL_NEAREST);
		GLWrapper.glTexParameteri(GL4.GL_TEXTURE_2D, GL4.GL_TEXTURE_WRAP_S, GL4.GL_CLAMP_TO_EDGE);
		GLWrapper.glTexParameteri(GL4.GL_TEXTURE_2D, GL4.GL_TEXTURE_WRAP_T, GL4.GL_CLAMP_TO_EDGE);
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
		if(GLWrapper.glCheckFramebufferStatus(GL4.GL_FRAMEBUFFER)!=GL4.GL_FRAMEBUFFER_COMPLETE) {
			LogWriter.WriteWarn("[Screen-SetupFramebuffer] Incomplete framebuffer", true);
		}
		GLWrapper.glBindFramebuffer(GL4.GL_FRAMEBUFFER, 0);
	}
	
	public void Dispose() {
		IntBuffer fbo_ids=Buffers.newDirectIntBuffer(new int[] {fbo_id});
		IntBuffer renderbuffer_ids=Buffers.newDirectIntBuffer(new int[] {renderbuffer_id});
		IntBuffer texture_ids=Buffers.newDirectIntBuffer(new int[] {texture_id});
		
		GLWrapper.glDeleteFramebuffers(1, fbo_ids);
		GLWrapper.glDeleteRenderbuffers(1, renderbuffer_ids);
		GLWrapper.glDeleteTextures(1, texture_ids);
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
	
	public void Draw() {
		GLWrapper.glBindFramebuffer(GL4.GL_FRAMEBUFFER, 0);
		
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
			String str=ExceptionFunctions.GetPrintStackTraceString(e);
			
			LogWriter.WriteWarn("[Screen-TakeScreenshot] Below is the stack trace.", true);
			LogWriter.WriteWarn(str, false);
			
			return -1;
		}
		
		return 0;
	}
}
