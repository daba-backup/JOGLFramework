package com.github.dabasan.joglf.gl.shader;

import java.nio.FloatBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.dabasan.basis.coloru8.ColorU8;
import com.github.dabasan.basis.matrix.Matrix;
import com.github.dabasan.basis.vector.Vector;
import com.github.dabasan.joglf.gl.texture.TextureMgr;
import com.github.dabasan.joglf.gl.tool.BufferFunctions;
import com.github.dabasan.joglf.gl.wrapper.GLWrapper;
import com.jogamp.opengl.GL4;

/**
 * Shader program
 * @author Daba
 *
 */
public class ShaderProgram {
	private Logger logger=LoggerFactory.getLogger(ShaderProgram.class);
	
	private String program_name;
	private int program_id;
	
	private boolean logging_enabled_flag;
	
	public ShaderProgram(String program_name) {
		this.program_name=program_name;
		program_id=ShaderFunctions.GetProgramID(program_name);
		if(program_id<0) {
			logger.warn("This program is invalid. program_name={}",program_name);
		}
		
		logging_enabled_flag=false;
	}
	public ShaderProgram(String program_name,String vertex_shader_filename,String fragment_shader_filename) {
		ShaderFunctions.CreateProgram(program_name, vertex_shader_filename, fragment_shader_filename);
		
		this.program_name=program_name;
		program_id=ShaderFunctions.GetProgramID(program_name);
		if(program_id<0) {
			logger.warn("This program is invalid. program_name={}",program_name);
		}
		
		logging_enabled_flag=false;
	}
	
	public void EnableLogging(boolean flag) {
		this.logging_enabled_flag=flag;
	}
	
	public String GetProgramName() {
		return program_name;
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
		if(location<0) {
			if(logging_enabled_flag==true) {
				logger.trace("({}) Invalid uniform name. name={}",program_name,name);
			}
			return -1;
		}
		
		GLWrapper.glUniform1i(location, value);
		
		return 0;
	}
	public int SetUniform(String name,int value0,int value1) {
		int location=GLWrapper.glGetUniformLocation(program_id, name);
		if(location<0) {
			if(logging_enabled_flag==true) {
				logger.trace("({}) Invalid uniform name. name={}",program_name,name);
			}
			return -1;
		}
		
		GLWrapper.glUniform2i(location, value0, value1);
		
		return 0;
	}
	public int SetUniform(String name,int value0,int value1,int value2) {
		int location=GLWrapper.glGetUniformLocation(program_id, name);
		if(location<0) {
			if(logging_enabled_flag==true) {
				logger.trace("({}) Invalid uniform name. name={}",program_name,name);
			}
			return -1;
		}
		
		GLWrapper.glUniform3i(location, value0, value1, value2);
		
		return 0;
	}
	public int SetUniform(String name,int value0,int value1,int value2,int value3) {
		int location=GLWrapper.glGetUniformLocation(program_id, name);
		if(location<0) {
			if(logging_enabled_flag==true) {
				logger.trace("({}) Invalid uniform name. name={}",program_name,name);
			}
			return -1;
		}
		
		GLWrapper.glUniform4i(location, value0, value1, value2, value3);
		
		return 0;
	}
	public int SetUniform(String name,float value) {
		int location=GLWrapper.glGetUniformLocation(program_id, name);
		if(location<0) {
			if(logging_enabled_flag==true) {
				logger.trace("({}) Invalid uniform name. name={}",program_name,name);
			}
			return -1;
		}
		
		GLWrapper.glUniform1f(location, value);
		
		return 0;
	}
	public int SetUniform(String name,float value0,float value1) {
		int location=GLWrapper.glGetUniformLocation(program_id, name);
		if(location<0) {
			if(logging_enabled_flag==true) {
				logger.trace("({}) Invalid uniform name. name={}",program_name,name);
			}
			return -1;
		}
		
		GLWrapper.glUniform2f(location, value0, value1);
		
		return 0;
	}
	public int SetUniform(String name,float value0,float value1,float value2) {
		int location=GLWrapper.glGetUniformLocation(program_id, name);
		if(location<0) {
			if(logging_enabled_flag==true) {
				logger.trace("({}) Invalid uniform name. name={}",program_name,name);
			}
			return -1;
		}
		
		GLWrapper.glUniform3f(location, value0, value1, value2);
		
		return 0;
	}
	public int SetUniform(String name,float value0,float value1,float value2,float value3) {
		int location=GLWrapper.glGetUniformLocation(program_id, name);
		if(location<0) {
			if(logging_enabled_flag==true) {
				logger.trace("({}) Invalid uniform name. name={}",program_name,name);
			}
			return -1;
		}
		
		GLWrapper.glUniform4f(location, value0, value1, value2, value3);
		
		return 0;
	}
	public int SetUniform(String name,Vector value) {
		int location=GLWrapper.glGetUniformLocation(program_id, name);
		if(location<0) {
			if(logging_enabled_flag==true) {
				logger.trace("({}) Invalid uniform name. name={}",program_name,name);
			}
			return -1;
		}
		
		GLWrapper.glUniform3f(location, value.GetX(), value.GetY(), value.GetZ());
		
		return 0;
	}
	public int SetUniform(String name,ColorU8 value) {
		int location=GLWrapper.glGetUniformLocation(program_id, name);
		if(location<0) {
			if(logging_enabled_flag==true) {
				logger.trace("({}) Invalid uniform name. name={}",program_name,name);
			}
			return -1;
		}
		
		GLWrapper.glUniform4f(location, value.GetR(), value.GetG(), value.GetB(), value.GetA());
		
		return 0;
	}
	public int SetUniform(String name,boolean transpose,Matrix value) {
		int location=GLWrapper.glGetUniformLocation(program_id, name);
		if(location<0) {
			if(logging_enabled_flag==true) {
				logger.trace("({}) Invalid uniform name. name={}",program_name,name);
			}
			return -1;
		}
		
		FloatBuffer buffer=BufferFunctions.MakeFloatBufferFromMatrix(value);
		GLWrapper.glUniformMatrix4fv(location, 1, transpose, buffer);
		
		return 0;
	}
	
	public int SetTexture(String name,int texture_unit,int texture_handle) {
		int location=GLWrapper.glGetUniformLocation(program_id, name);
		if(location<0) {
			if(logging_enabled_flag==true) {
				logger.trace("({}) Invalid uniform name. name={}",program_name,name);
			}
			return -1;
		}
		
		GLWrapper.glActiveTexture(GL4.GL_TEXTURE0+texture_unit);
		TextureMgr.EnableTexture(texture_handle);
		TextureMgr.BindTexture(texture_handle);
		GLWrapper.glUniform1i(location, texture_unit);
		TextureMgr.DisableTexture(texture_handle);
		
		return 0;
	}
}
