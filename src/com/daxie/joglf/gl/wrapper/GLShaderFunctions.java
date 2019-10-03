package com.daxie.joglf.gl.wrapper;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.daxie.joglf.gl.tool.BufferFunctions;
import com.daxie.log.LogFile;
import com.daxie.tool.ExceptionFunctions;
import com.daxie.tool.FileFunctions;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL4;

/**
 * Shader functions
 * @author Daba
 *
 */
public class GLShaderFunctions {
	private static Map<String, Integer> program_ids_map=new HashMap<>();
	private static IntBuffer sampler=Buffers.newDirectIntBuffer(1);
	
	/**
	 * Creates a program after compiling shaders.
	 * @param program_name Name of the program
	 * @param vertex_shader_filename Filename of the vertex shader
	 * @param fragment_shader_filename Filename of the fragment shader
	 * @return -1 on error and 0 on success
	 */
	public static int CreateProgram(String program_name,String vertex_shader_filename,String fragment_shader_filename) {
		LogFile.WriteInfo("[GLShaderFunctions-CreateProgram] Start creating a program.",true);
		LogFile.WriteInfo("vertex_shader_filename:"+vertex_shader_filename,false);
		LogFile.WriteInfo("fragment_shader_filename:"+fragment_shader_filename,false);
		
		int vertex_shader_id=GLWrapper.glCreateShader(GL4.GL_VERTEX_SHADER);
		int fragment_shader_id=GLWrapper.glCreateShader(GL4.GL_FRAGMENT_SHADER);
		
		//Load the code files of shaders.
		String[] vertex_shader_code=null;
		String[] fragment_shader_code=null;
		try {
			List<String> vertex_shader_code_list=FileFunctions.GetFileAllLines(vertex_shader_filename, "UTF-8");
			List<String> fragment_shader_code_list=FileFunctions.GetFileAllLines(fragment_shader_filename, "UTF-8");
			
			vertex_shader_code=new String[vertex_shader_code_list.size()];
			fragment_shader_code=new String[fragment_shader_code_list.size()];
			vertex_shader_code_list.toArray(vertex_shader_code);
			fragment_shader_code_list.toArray(fragment_shader_code);
		}
		catch(FileNotFoundException e) {
			String str=ExceptionFunctions.GetPrintStackTraceString(e);
			
			LogFile.WriteWarn("[GLShaderFunctions-CreateProgram] Failed to load a file. Below is the stack trace.",true);
			LogFile.WriteWarn(str,false);
			
			return -1;
		}
		catch(UnsupportedEncodingException e) {
			LogFile.WriteWarn("[GLShaderFunctions-CreateProgram] Internal error. Unknown encoding specified.",true);
			return -1;
		}
		
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
			
			LogFile.WriteWarn("[Shaders-LoadShaders] Vertex shader compilation failed. Below is the information log.",true);
			LogFile.WriteWarn(error_message_str,false);
			
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
			
			LogFile.WriteWarn("[Shaders-LoadShaders] Fragment shader compilation failed. Below is the information log.",true);
			LogFile.WriteWarn(error_message_str,false);
			
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
			
			LogFile.WriteWarn("[Shaders-LoadShaders] Program link failed. Below is the information log.",true);
			LogFile.WriteWarn(error_message_str,false);
			
			return -1;
		}
		
		GLWrapper.glDeleteShader(vertex_shader_id);
		GLWrapper.glDeleteShader(fragment_shader_id);
		
		program_ids_map.put(program_name, program_id);
		
		return 0;
	}
	/**
	 * Initializes a sampler.
	 */
	public static void InitializeSampler() {
		GLWrapper.glGenSamplers(1, sampler);
		
		GLWrapper.glSamplerParameteri(sampler.get(0),GL4.GL_TEXTURE_MAG_FILTER,GL4.GL_NEAREST);
		GLWrapper.glSamplerParameteri(sampler.get(0),GL4.GL_TEXTURE_MIN_FILTER,GL4.GL_NEAREST);
		
		GLWrapper.glSamplerParameteri(sampler.get(0),GL4.GL_TEXTURE_WRAP_S,GL4.GL_REPEAT);
		GLWrapper.glSamplerParameteri(sampler.get(0),GL4.GL_TEXTURE_WRAP_T,GL4.GL_REPEAT);
	}
	
	public static int GetProgramID(String program_name) {
		if(program_ids_map.containsKey(program_name)==false)return -1;
		return program_ids_map.get(program_name);
	}
	public static int GetSampler() {
		return sampler.get(0);
	}
	
	public static int EnableProgram(String program_name) {
		if(program_ids_map.containsKey(program_name)==false) {
			LogFile.WriteWarn("[GLShaderFunctions-EnableProgram] Invalid program name. name:"+program_name,true);
			return -1;
		}
		
		int program_id=program_ids_map.get(program_name);
		GLWrapper.glUseProgram(program_id);
		
		return 0;
	}
}
