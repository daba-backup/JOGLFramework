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
import com.jogamp.opengl.GL;

/**
 * Shader program
 * 
 * @author Daba
 *
 */
public class ShaderProgram {
	private final Logger logger = LoggerFactory.getLogger(ShaderProgram.class);

	private final String program_name;
	private final int program_id;

	private boolean logging_enabled_flag;

	/**
	 * Wraps an already existing program.
	 * 
	 * @param program_name
	 *            Program name
	 */
	public ShaderProgram(String program_name) {
		this.program_name = program_name;
		program_id = ShaderFunctions.GetProgramID(program_name);
		if (program_id < 0) {
			logger.warn("This program is invalid. program_name={}", program_name);
		}

		logging_enabled_flag = false;
	}
	/**
	 * Creates a new program.<br>
	 * Wraps the program specified if it already exists.
	 * 
	 * @param program_name
	 *            Program name
	 * @param vertex_shader_filename
	 *            Filename of the vertex shader
	 * @param fragment_shader_filename
	 *            Filename of the fragment shader
	 */
	public ShaderProgram(String program_name, String vertex_shader_filename,
			String fragment_shader_filename) {
		// Create a program if not exists.
		if (ShaderFunctions.ProgramExists(program_name) == false) {
			ShaderFunctions.CreateProgram(program_name, vertex_shader_filename,
					fragment_shader_filename);
		}

		this.program_name = program_name;
		program_id = ShaderFunctions.GetProgramID(program_name);
		if (program_id < 0) {
			logger.warn("This program is invalid. program_name={}", program_name);
		}

		logging_enabled_flag = false;
	}

	/**
	 * Enables log output from this program.
	 * 
	 * @param flag
	 *            Flag
	 */
	public void EnableLogging(boolean flag) {
		this.logging_enabled_flag = flag;
	}

	public String GetProgramName() {
		return program_name;
	}

	public boolean IsValid() {
		if (program_id >= 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Enables the program.
	 */
	public void Enable() {
		if (program_id < 0) {
			return;
		}
		GLWrapper.glUseProgram(program_id);
	}
	/**
	 * Disables the program.
	 */
	public void Disable() {
		if (program_id < 0) {
			return;
		}
		GLWrapper.glUseProgram(0);
	}

	public int SetUniform(String name, int value) {
		final int location = GLWrapper.glGetUniformLocation(program_id, name);
		if (location < 0) {
			if (logging_enabled_flag == true) {
				logger.trace("({}) Invalid uniform name. name={}", program_name, name);
			}
			return -1;
		}

		GLWrapper.glUniform1i(location, value);

		return 0;
	}
	public int SetUniform(String name, int value0, int value1) {
		final int location = GLWrapper.glGetUniformLocation(program_id, name);
		if (location < 0) {
			if (logging_enabled_flag == true) {
				logger.trace("({}) Invalid uniform name. name={}", program_name, name);
			}
			return -1;
		}

		GLWrapper.glUniform2i(location, value0, value1);

		return 0;
	}
	public int SetUniform(String name, int value0, int value1, int value2) {
		final int location = GLWrapper.glGetUniformLocation(program_id, name);
		if (location < 0) {
			if (logging_enabled_flag == true) {
				logger.trace("({}) Invalid uniform name. name={}", program_name, name);
			}
			return -1;
		}

		GLWrapper.glUniform3i(location, value0, value1, value2);

		return 0;
	}
	public int SetUniform(String name, int value0, int value1, int value2, int value3) {
		final int location = GLWrapper.glGetUniformLocation(program_id, name);
		if (location < 0) {
			if (logging_enabled_flag == true) {
				logger.trace("({}) Invalid uniform name. name={}", program_name, name);
			}
			return -1;
		}

		GLWrapper.glUniform4i(location, value0, value1, value2, value3);

		return 0;
	}
	public int SetUniform(String name, float value) {
		final int location = GLWrapper.glGetUniformLocation(program_id, name);
		if (location < 0) {
			if (logging_enabled_flag == true) {
				logger.trace("({}) Invalid uniform name. name={}", program_name, name);
			}
			return -1;
		}

		GLWrapper.glUniform1f(location, value);

		return 0;
	}
	public int SetUniform(String name, float value0, float value1) {
		final int location = GLWrapper.glGetUniformLocation(program_id, name);
		if (location < 0) {
			if (logging_enabled_flag == true) {
				logger.trace("({}) Invalid uniform name. name={}", program_name, name);
			}
			return -1;
		}

		GLWrapper.glUniform2f(location, value0, value1);

		return 0;
	}
	public int SetUniform(String name, float value0, float value1, float value2) {
		final int location = GLWrapper.glGetUniformLocation(program_id, name);
		if (location < 0) {
			if (logging_enabled_flag == true) {
				logger.trace("({}) Invalid uniform name. name={}", program_name, name);
			}
			return -1;
		}

		GLWrapper.glUniform3f(location, value0, value1, value2);

		return 0;
	}
	public int SetUniform(String name, float value0, float value1, float value2, float value3) {
		final int location = GLWrapper.glGetUniformLocation(program_id, name);
		if (location < 0) {
			if (logging_enabled_flag == true) {
				logger.trace("({}) Invalid uniform name. name={}", program_name, name);
			}
			return -1;
		}

		GLWrapper.glUniform4f(location, value0, value1, value2, value3);

		return 0;
	}
	public int SetUniform(String name, Vector value) {
		final int location = GLWrapper.glGetUniformLocation(program_id, name);
		if (location < 0) {
			if (logging_enabled_flag == true) {
				logger.trace("({}) Invalid uniform name. name={}", program_name, name);
			}
			return -1;
		}

		GLWrapper.glUniform3f(location, value.GetX(), value.GetY(), value.GetZ());

		return 0;
	}
	public int SetUniform(String name, ColorU8 value) {
		final int location = GLWrapper.glGetUniformLocation(program_id, name);
		if (location < 0) {
			if (logging_enabled_flag == true) {
				logger.trace("({}) Invalid uniform name. name={}", program_name, name);
			}
			return -1;
		}

		GLWrapper.glUniform4f(location, value.GetR(), value.GetG(), value.GetB(), value.GetA());

		return 0;
	}
	public int SetUniform(String name, boolean transpose, Matrix value) {
		final int location = GLWrapper.glGetUniformLocation(program_id, name);
		if (location < 0) {
			if (logging_enabled_flag == true) {
				logger.trace("({}) Invalid uniform name. name={}", program_name, name);
			}
			return -1;
		}

		final FloatBuffer buffer = BufferFunctions.MakeFloatBufferFromMatrix(value);
		GLWrapper.glUniformMatrix4fv(location, 1, transpose, buffer);

		return 0;
	}

	/**
	 * Sets a texture to use in the program.
	 * 
	 * @param name
	 *            Sampler name
	 * @param texture_unit
	 *            Texture unit
	 * @param texture_handle
	 *            Texture handle
	 * @return -1 on error and 0 on success
	 */
	public int SetTexture(String name, int texture_unit, int texture_handle) {
		final int location = GLWrapper.glGetUniformLocation(program_id, name);
		if (location < 0) {
			if (logging_enabled_flag == true) {
				logger.trace("({}) Invalid uniform name. name={}", program_name, name);
			}
			return -1;
		}

		GLWrapper.glActiveTexture(GL.GL_TEXTURE0 + texture_unit);
		TextureMgr.BindTexture(texture_handle);
		GLWrapper.glUniform1i(location, texture_unit);

		return 0;
	}
}
