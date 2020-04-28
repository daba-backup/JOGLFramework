package com.github.dabasan.joglf.gl.transferrer;

import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import com.github.dabasan.joglf.gl.wrapper.GLWrapper;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL4;

/**
 * Transfers a fullscreen quad with UV coordinates.
 * @author Daba
 *
 */
public class FullscreenQuadTransferrerWithUV implements FullscreenQuadTransferrerInterface{
	private IntBuffer indices_vbo;
	private IntBuffer pos_vbo;
	private IntBuffer uv_vbo;
	private IntBuffer vao;
	
	public FullscreenQuadTransferrerWithUV() {
		indices_vbo=Buffers.newDirectIntBuffer(1);
		pos_vbo=Buffers.newDirectIntBuffer(1);
		uv_vbo=Buffers.newDirectIntBuffer(1);
		vao=Buffers.newDirectIntBuffer(1);
		
		GLWrapper.glGenBuffers(1, indices_vbo);
		GLWrapper.glGenBuffers(1, pos_vbo);
		GLWrapper.glGenBuffers(1, uv_vbo);
		GLWrapper.glGenVertexArrays(1, vao);
		
		IntBuffer indices_buffer=Buffers.newDirectIntBuffer(6);
		FloatBuffer pos_buffer=Buffers.newDirectFloatBuffer(2*4);
		FloatBuffer uv_buffer=Buffers.newDirectFloatBuffer(2*4);
		
		//First triangle
		indices_buffer.put(0);
		indices_buffer.put(1);
		indices_buffer.put(2);
		//Second triangle
		indices_buffer.put(2);
		indices_buffer.put(3);
		indices_buffer.put(0);
		((Buffer)indices_buffer).flip();
		
		//Bottom left
		pos_buffer.put(-1.0f);
		pos_buffer.put(-1.0f);
		//Bottom right
		pos_buffer.put(1.0f);
		pos_buffer.put(-1.0f);
		//Top right
		pos_buffer.put(1.0f);
		pos_buffer.put(1.0f);
		//Top left
		pos_buffer.put(-1.0f);
		pos_buffer.put(1.0f);
		((Buffer)pos_buffer).flip();
		
		//Bottom left
		uv_buffer.put(0.0f);
		uv_buffer.put(0.0f);
		//Bottom right
		uv_buffer.put(1.0f);
		uv_buffer.put(0.0f);
		//Top right
		uv_buffer.put(1.0f);
		uv_buffer.put(1.0f);
		//Top left
		uv_buffer.put(0.0f);
		uv_buffer.put(1.0f);
		((Buffer)uv_buffer).flip();
		
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, pos_vbo.get(0));
		GLWrapper.glBufferData(GL4.GL_ARRAY_BUFFER, 
				Buffers.SIZEOF_FLOAT*pos_buffer.capacity(), pos_buffer, GL4.GL_STATIC_DRAW);
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, uv_vbo.get(0));
		GLWrapper.glBufferData(GL4.GL_ARRAY_BUFFER, 
				Buffers.SIZEOF_FLOAT*uv_buffer.capacity(), uv_buffer, GL4.GL_STATIC_DRAW);
		
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, 0);
		
		GLWrapper.glBindVertexArray(vao.get(0));
		
		GLWrapper.glBindBuffer(GL4.GL_ELEMENT_ARRAY_BUFFER, indices_vbo.get(0));
		GLWrapper.glBufferData(GL4.GL_ELEMENT_ARRAY_BUFFER, 
				Buffers.SIZEOF_INT*indices_buffer.capacity(), indices_buffer, GL4.GL_STATIC_DRAW);
		
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, pos_vbo.get(0));
		GLWrapper.glEnableVertexAttribArray(0);
		GLWrapper.glVertexAttribPointer(0, 2, GL4.GL_FLOAT, false, Buffers.SIZEOF_FLOAT*2, 0);
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, uv_vbo.get(0));
		GLWrapper.glEnableVertexAttribArray(1);
		GLWrapper.glVertexAttribPointer(1, 2, GL4.GL_FLOAT, false, Buffers.SIZEOF_FLOAT*2, 0);
		
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, 0);
		GLWrapper.glBindVertexArray(0);
	}
	
	@Override
	public void DeleteBuffers() {
		GLWrapper.glDeleteBuffers(1, indices_vbo);
		GLWrapper.glDeleteBuffers(1, pos_vbo);
		GLWrapper.glDeleteBuffers(1, uv_vbo);
		GLWrapper.glDeleteVertexArrays(1, vao);
	}
	
	@Override
	public void Transfer() {
		GLWrapper.glBindVertexArray(vao.get(0));
		GLWrapper.glDrawElements(GL4.GL_TRIANGLES, 6, GL4.GL_UNSIGNED_INT, 0);
		GLWrapper.glBindVertexArray(0);
	}
}
