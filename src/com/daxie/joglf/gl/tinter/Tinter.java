package com.daxie.joglf.gl.tinter;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import com.daxie.basis.coloru8.ColorU8;
import com.daxie.basis.coloru8.ColorU8Functions;
import com.daxie.joglf.gl.tool.BufferFunctions;
import com.daxie.joglf.gl.wrapper.GLShaderFunctions;
import com.daxie.joglf.gl.wrapper.GLWrapper;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL4;

/**
 * Tints the whole screen with a specified color.
 * @author Daba
 *
 */
public class Tinter {
	private ColorU8 tint_color;
	
	private IntBuffer pos_vbo;
	private IntBuffer vao;
	
	public Tinter() {
		tint_color=ColorU8Functions.GetColorU8(0.0f, 0.0f, 0.0f, 0.0f);
	}
	
	public void SetTintColor(ColorU8 color) {
		tint_color=color;
	}
	
	public void Initialize() {
		pos_vbo=Buffers.newDirectIntBuffer(1);
		vao=Buffers.newDirectIntBuffer(1);
		
		GLWrapper.glGenBuffers(1, pos_vbo);
		GLWrapper.glGenVertexArrays(1, vao);
		
		FloatBuffer pos_buffer=Buffers.newDirectFloatBuffer(2*6);
		//Bottom left
		pos_buffer.put(-1.0f);
		pos_buffer.put(-1.0f);
		//Bottom right
		pos_buffer.put(1.0f);
		pos_buffer.put(-1.0f);
		//Top right
		pos_buffer.put(1.0f);
		pos_buffer.put(1.0f);
		//Top right
		pos_buffer.put(1.0f);
		pos_buffer.put(1.0f);
		//Top left
		pos_buffer.put(-1.0f);
		pos_buffer.put(1.0f);
		//Bottom left
		pos_buffer.put(-1.0f);
		pos_buffer.put(-1.0f);
		
		pos_buffer.flip();
		
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, pos_vbo.get(0));
		GLWrapper.glBufferData(GL4.GL_ARRAY_BUFFER, 
				Buffers.SIZEOF_FLOAT*pos_buffer.capacity(), pos_buffer, GL4.GL_STATIC_DRAW);
		
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, 0);
		
		GLWrapper.glBindVertexArray(vao.get(0));
		
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, pos_vbo.get(0));
		GLWrapper.glEnableVertexAttribArray(0);
		GLWrapper.glVertexAttribPointer(0, 2, GL4.GL_FLOAT, false, Buffers.SIZEOF_FLOAT*2, 0);
		
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, 0);
		GLWrapper.glBindVertexArray(0);
	}
	public void Dispose() {
		GLWrapper.glDeleteBuffers(1, pos_vbo);
		GLWrapper.glDeleteVertexArrays(1, vao);
	}
	
	public void Tint() {
		FloatBuffer tint_color_buffer=BufferFunctions.MakeFloatBufferFromColorU8(tint_color);
		
		int program_id;
		int tint_color_location;
		
		//Tinter program
		GLShaderFunctions.EnableProgram("tinter");
		program_id=GLShaderFunctions.GetProgramID("tinter");
		
		tint_color_location=GLWrapper.glGetUniformLocation(program_id, "tint_color");
		
		GLWrapper.glUniform4fv(tint_color_location, 1, tint_color_buffer);
		
		GLWrapper.glBindVertexArray(vao.get(0));
		GLWrapper.glEnable(GL4.GL_BLEND);
		GLWrapper.glDrawArrays(GL4.GL_TRIANGLES, 0, 6);
		GLWrapper.glDisable(GL4.GL_BLEND);
		GLWrapper.glBindVertexArray(0);
	}
}
