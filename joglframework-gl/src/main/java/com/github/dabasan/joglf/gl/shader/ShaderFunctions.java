package com.github.dabasan.joglf.gl.shader;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.daxie.tool.FileFunctions;
import com.github.dabasan.joglf.gl.tool.BufferFunctions;
import com.github.dabasan.joglf.gl.wrapper.GLWrapper;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL4;

/**
 * Shader functions
 * @author Daba
 *
 */
public class ShaderFunctions {
	private static Logger logger=LoggerFactory.getLogger(ShaderFunctions.class);
	
	private static Map<String, Integer> program_ids_map=new HashMap<>();
	
	/**
	 * Creates a program after compiling shaders.
	 * @param program_name Name of the program
	 * @param vertex_shader_filename Filename of the vertex shader
	 * @param fragment_shader_filename Filename of the fragment shader
	 * @return -1 on error and 0 on success
	 */
	public static int CreateProgram(String program_name,String vertex_shader_filename,String fragment_shader_filename) {
		logger.info("Start creating a program. program_name={}",program_name);
		logger.info("vertex_shader_filename={} fragment_shader_filename={}",vertex_shader_filename,fragment_shader_filename);
		
		int vertex_shader_id=GLWrapper.glCreateShader(GL4.GL_VERTEX_SHADER);
		int fragment_shader_id=GLWrapper.glCreateShader(GL4.GL_FRAGMENT_SHADER);
		
		//Load the code files of shaders.
		List<String> vertex_shader_code_list;
		List<String> fragment_shader_code_list;
		String[] vertex_shader_code;
		String[] fragment_shader_code;
		try {
			vertex_shader_code_list=FileFunctions.GetFileAllLines(vertex_shader_filename, "UTF-8");
			fragment_shader_code_list=FileFunctions.GetFileAllLines(fragment_shader_filename, "UTF-8");
		}
		catch(IOException e) {
			logger.error("Error while reading.",e);
			return -1;
		}
		vertex_shader_code=new String[vertex_shader_code_list.size()];
		fragment_shader_code=new String[fragment_shader_code_list.size()];
		vertex_shader_code_list.toArray(vertex_shader_code);
		fragment_shader_code_list.toArray(fragment_shader_code);
		
		//Add LF to every line of the code.
		for(int i=0;i<vertex_shader_code.length;i++) {
			vertex_shader_code[i]+="\n";
		}
		for(int i=0;i<fragment_shader_code.length;i++) {
			fragment_shader_code[i]+="\n";
		}
		
		IntBuffer info_log_length=Buffers.newDirectIntBuffer(1);
		IntBuffer result=Buffers.newDirectIntBuffer(1);
		ByteBuffer error_message;
		String error_message_str;
		
		//Compile vertex shader.
		GLWrapper.glShaderSource(vertex_shader_id,vertex_shader_code.length,vertex_shader_code,null);
		GLWrapper.glCompileShader(vertex_shader_id);
		
		//Check vertex shader.
		GLWrapper.glGetShaderiv(vertex_shader_id, GL4.GL_COMPILE_STATUS, result);
		if(result.get(0)==GL4.GL_FALSE){
			GLWrapper.glGetShaderiv(vertex_shader_id, GL4.GL_INFO_LOG_LENGTH, info_log_length);
			error_message=Buffers.newDirectByteBuffer(info_log_length.get(0));
			GLWrapper.glGetShaderInfoLog(vertex_shader_id,info_log_length.get(0),null,error_message);
			error_message_str=BufferFunctions.GetStringFromByteBuffer(error_message);
			
			logger.error("Vertex shader compilation failed.");
			logger.error(error_message_str);
			
			return -1;
		}
		
		//Compile fragment shader.
		GLWrapper.glShaderSource(fragment_shader_id,fragment_shader_code.length,fragment_shader_code,null);
		GLWrapper.glCompileShader(fragment_shader_id);
		
		//Check fragment shader.
		GLWrapper.glGetShaderiv(fragment_shader_id, GL4.GL_COMPILE_STATUS, result);
		if(result.get(0)==GL4.GL_FALSE) {
			GLWrapper.glGetShaderiv(fragment_shader_id, GL4.GL_INFO_LOG_LENGTH, info_log_length);
			error_message=Buffers.newDirectByteBuffer(info_log_length.get(0));
			GLWrapper.glGetShaderInfoLog(fragment_shader_id,info_log_length.get(0),null,error_message);
			error_message_str=BufferFunctions.GetStringFromByteBuffer(error_message);
			
			logger.error("Fragment shader compilation failed.");
			logger.error(error_message_str);
			
			return -1;
		}
		
		//Link program.
		int program_id=GLWrapper.glCreateProgram();
		GLWrapper.glAttachShader(program_id, vertex_shader_id);
		GLWrapper.glAttachShader(program_id, fragment_shader_id);
		
		GLWrapper.glLinkProgram(program_id);
		
		//Check program.
		GLWrapper.glGetProgramiv(program_id, GL4.GL_LINK_STATUS, result);
		if(result.get(0)==GL4.GL_FALSE) {
			GLWrapper.glGetProgramiv(program_id,GL4.GL_INFO_LOG_LENGTH,info_log_length);
			error_message=Buffers.newDirectByteBuffer(info_log_length.get(0));
			GLWrapper.glGetProgramInfoLog(program_id, info_log_length.get(0), null, error_message);
			error_message_str=BufferFunctions.GetStringFromByteBuffer(error_message);
			
			logger.error("Program link failed.");
			logger.error(error_message_str);
			
			return -1;
		}
		
		GLWrapper.glDeleteShader(vertex_shader_id);
		GLWrapper.glDeleteShader(fragment_shader_id);
		
		program_ids_map.put(program_name, program_id);
		logger.info("Successfully created a program. program_name={} program_id={}",program_name,program_id);
		
		return 0;
	}
	
	public static int GetProgramID(String program_name) {
		if(program_ids_map.containsKey(program_name)==false)return -1;
		return program_ids_map.get(program_name);
	}
	
	public static int UseProgram(String program_name) {
		if(program_ids_map.containsKey(program_name)==false) {
			logger.trace("Invalid program name. program_name={}",program_name);
			return -1;
		}
		
		int program_id=program_ids_map.get(program_name);
		GLWrapper.glUseProgram(program_id);
		
		return 0;
	}
}
