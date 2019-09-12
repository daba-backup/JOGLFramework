package com.daxie.joglf.gl.gl4;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;

import com.daxie.joglf.gl.tool.BufferFunctions;
import com.daxie.joglf.log.LogFile;
import com.daxie.joglf.tool.ExceptionFunctions;
import com.daxie.joglf.tool.FileFunctions;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLContext;

/**
 * Shader functions of GL4.
 * @author Daba
 *
 */
public class GL4ShaderFunctions {
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
		LogFile.WriteInfo("[GL4ShaderFunctions-CreateProgram] Start creating a program.",true);
		LogFile.WriteInfo("vertex_shader_filename:"+vertex_shader_filename,false);
		LogFile.WriteInfo("fragment_shader_filename:"+fragment_shader_filename,false);
		
		int vertex_shader_id=GL4Wrapper.glCreateShader(GL4.GL_VERTEX_SHADER);
		int fragment_shader_id=GL4Wrapper.glCreateShader(GL4.GL_FRAGMENT_SHADER);
		
		//Load the code files of shaders.
		String[] vertex_shader_code=null;
		String[] fragment_shader_code=null;
		try {
			vertex_shader_code=FileFunctions.GetFileAllLines(vertex_shader_filename, "UTF-8");
			fragment_shader_code=FileFunctions.GetFileAllLines(fragment_shader_filename, "UTF-8");
		}
		catch(FileNotFoundException e) {
			String str=ExceptionFunctions.GetPrintStackTraceString(e);
			
			LogFile.WriteError("Failed to load a file. Below is the stack trace.",true);
			LogFile.WriteError(str,false);
			
			return -1;
		}
		catch(UnsupportedEncodingException e) {
			LogFile.WriteError("[GL4ShaderFunctions-CreateProgram] Internal error. Unknown encoding specified.",true);
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
		GL4Wrapper.glShaderSource(vertex_shader_id,vertex_shader_code.length,vertex_shader_code,null);
		GL4Wrapper.glCompileShader(vertex_shader_id);
		
		//Check vertex shader.
		GL4Wrapper.glGetShaderiv(vertex_shader_id, GL4.GL_COMPILE_STATUS, result);
		if(result.get(0)==GL4.GL_FALSE){
			GL4Wrapper.glGetShaderiv(vertex_shader_id, GL4.GL_INFO_LOG_LENGTH, info_log_length);
			error_message=Buffers.newDirectByteBuffer(info_log_length.get(0));
			GL4Wrapper.glGetShaderInfoLog(vertex_shader_id,info_log_length.get(0),null,error_message);
			error_message_str=BufferFunctions.GetStringFromByteBuffer(error_message);
			
			LogFile.WriteError("[Shaders-LoadShaders] Vertex shader compilation failed. Below is the information log.",true);
			LogFile.WriteError(error_message_str,false);
			
			return -1;
		}
		
		//Compile fragment shader.
		GL4Wrapper.glShaderSource(fragment_shader_id,fragment_shader_code.length,fragment_shader_code,null);
		GL4Wrapper.glCompileShader(fragment_shader_id);
		
		//Check fragment shader.
		GL4Wrapper.glGetShaderiv(fragment_shader_id, GL4.GL_COMPILE_STATUS, result);
		if(result.get(0)==GL4.GL_FALSE) {
			GL4Wrapper.glGetShaderiv(fragment_shader_id, GL4.GL_INFO_LOG_LENGTH, info_log_length);
			error_message=Buffers.newDirectByteBuffer(info_log_length.get(0));
			GL4Wrapper.glGetShaderInfoLog(fragment_shader_id,info_log_length.get(0),null,error_message);
			error_message_str=BufferFunctions.GetStringFromByteBuffer(error_message);
			
			LogFile.WriteError("[Shaders-LoadShaders] Fragment shader compilation failed. Below is the information log.",true);
			LogFile.WriteError(error_message_str,false);
			
			return -1;
		}
		
		//Link program.
		int program_id=GL4Wrapper.glCreateProgram();
		GL4Wrapper.glAttachShader(program_id, vertex_shader_id);
		GL4Wrapper.glAttachShader(program_id, fragment_shader_id);
		
		GL4Wrapper.glLinkProgram(program_id);
		
		//Check program.
		GL4Wrapper.glGetProgramiv(program_id, GL4.GL_LINK_STATUS, result);
		if(result.get(0)==GL4.GL_FALSE) {
			GL4Wrapper.glGetProgramiv(program_id,GL4.GL_INFO_LOG_LENGTH,info_log_length);
			error_message=Buffers.newDirectByteBuffer(info_log_length.get(0));
			GL4Wrapper.glGetProgramInfoLog(program_id, info_log_length.get(0), null, error_message);
			error_message_str=BufferFunctions.GetStringFromByteBuffer(error_message);
			
			LogFile.WriteError("[Shaders-LoadShaders] Program link failed. Below is the information log.",true);
			LogFile.WriteError(error_message_str,false);
			
			return -1;
		}
		
		GL4Wrapper.glDeleteShader(vertex_shader_id);
		GL4Wrapper.glDeleteShader(fragment_shader_id);
		
		program_ids_map.put(program_name, program_id);
		
		return 0;
	}
	/**
	 * Initialize a sampler.
	 */
	public static void InitializeSampler() {
		GL4Wrapper.glGenSamplers(1, sampler);
		
		GL4Wrapper.glSamplerParameteri(sampler.get(0),GL4.GL_TEXTURE_MAG_FILTER,GL4.GL_NEAREST);
		GL4Wrapper.glSamplerParameteri(sampler.get(0),GL4.GL_TEXTURE_MIN_FILTER,GL4.GL_NEAREST);
		
		GL4Wrapper.glSamplerParameteri(sampler.get(0),GL4.GL_TEXTURE_WRAP_S,GL4.GL_REPEAT);
		GL4Wrapper.glSamplerParameteri(sampler.get(0),GL4.GL_TEXTURE_WRAP_T,GL4.GL_REPEAT);
	}
	
	public static int GetProgramID(String program_name) {
		if(program_ids_map.containsKey(program_name)==false)return -1;
		return program_ids_map.get(program_name);
	}
	public static int GetSampler() {
		return sampler.get(0);
	}
	
	public static int EnableProgram(String program_name) {
		GL4 gl4=GLContext.getCurrentGL().getGL4();
		
		if(program_ids_map.containsKey(program_name)==false) {
			LogFile.WriteError("[GL4ShaderFunctions-EnableProgram] Invalid program name. name:"+program_name,true);
			return -1;
		}
		
		int program_id=program_ids_map.get(program_name);
		GL4Wrapper.glUseProgram(program_id);
		
		return 0;
	}
}
