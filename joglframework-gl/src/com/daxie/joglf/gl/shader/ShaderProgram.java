package com.daxie.joglf.gl.shader;

import java.nio.FloatBuffer;

import com.daxie.basis.coloru8.ColorU8;
import com.daxie.basis.matrix.Matrix;
import com.daxie.basis.vector.Vector;
import com.daxie.joglf.gl.tool.BufferFunctions;
import com.daxie.joglf.gl.wrapper.GLWrapper;
import com.daxie.log.LogWriter;

/**
 * Shader program
 * @author Daba
 *
 */
public class ShaderProgram {
	private int program_id;
	
	public ShaderProgram(String program_name) {
		program_id=GLShaderFunctions.GetProgramID(program_name);
		if(program_id<0) {
			LogWriter.WriteWarn("[ShaderProgram-<init>] This program is invalid. program_name:"+program_name, true);
		}
	}
	
	public boolean IsValid() {
		if(program_id>=0)return true;
		else return false;
	}
	
	public void Enable() {
		if(program_id<0)return;
		GLWrapper.glUseProgram(program_id);
	}
	public void Disable() {
		if(program_id<0)return;
		GLWrapper.glUseProgram(0);
	}
	
	public int SetUniform(String name,int value) {
		int location=GLWrapper.glGetUniformLocation(program_id, name);
		if(location<0)return -1;
		
		GLWrapper.glUniform1i(location, value);
		
		return 0;
	}
	public int SetUniform(String name,float value) {
		int location=GLWrapper.glGetUniformLocation(program_id, name);
		if(location<0)return -1;
		
		GLWrapper.glUniform1f(location, value);
		
		return 0;
	}
	public int SetUniform(String name,Vector value) {
		int location=GLWrapper.glGetUniformLocation(program_id, name);
		if(location<0)return -1;
		
		FloatBuffer buffer=BufferFunctions.MakeFloatBufferFromVector(value);
		GLWrapper.glUniform3fv(location, 1, buffer);
		
		return 0;
	}
	public int SetUniform(String name,ColorU8 value) {
		int location=GLWrapper.glGetUniformLocation(program_id, name);
		if(location<0)return -1;
		
		FloatBuffer buffer=BufferFunctions.MakeFloatBufferFromColorU8(value);
		GLWrapper.glUniform4fv(location, 1, buffer);
		
		return 0;
	}
	public int SetUniform(String name,boolean transpose,Matrix value) {
		int location=GLWrapper.glGetUniformLocation(program_id, name);
		if(location<0)return -1;
		
		FloatBuffer buffer=BufferFunctions.MakeFloatBufferFromMatrix(value);
		GLWrapper.glUniformMatrix4fv(location, 1, transpose, buffer);
		
		return 0;
	}
}
