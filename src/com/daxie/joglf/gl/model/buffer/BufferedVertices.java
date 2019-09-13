package com.daxie.joglf.gl.model.buffer;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import com.daxie.joglf.gl.tool.BufferFunctions;

/**
 * Set of buffers for shaders.
 * @author Daba
 *
 */
public class BufferedVertices {
	private int texture_handle;
	private int count;
	
	private IntBuffer indices;
	private FloatBuffer pos_buffer;
	private FloatBuffer uv_buffer;
	private FloatBuffer norm_buffer;
	
	public BufferedVertices Copy() {
		BufferedVertices buffered_vertices=new BufferedVertices();
		
		buffered_vertices.texture_handle=this.texture_handle;
		buffered_vertices.count=this.count;
		
		buffered_vertices.indices=BufferFunctions.CopyIntBuffer(this.indices);
		buffered_vertices.pos_buffer=BufferFunctions.CopyFloatBuffer(this.pos_buffer);
		buffered_vertices.uv_buffer=BufferFunctions.CopyFloatBuffer(this.uv_buffer);
		buffered_vertices.norm_buffer=BufferFunctions.CopyFloatBuffer(this.norm_buffer);
		
		return buffered_vertices;
	}
	
	public BufferedVertices() {
		texture_handle=-1;
		count=0;
	}
	
	public void SetTextureHandle(int texture_handle) {
		this.texture_handle=texture_handle;
	}
	public void SetIndices(IntBuffer indices) {
		this.indices=indices;
		count=indices.capacity();
	}
	public void SetPosBuffer(FloatBuffer pos_buffer) {
		this.pos_buffer=pos_buffer;
	}
	public void SetUVBuffer(FloatBuffer uv_buffer) {
		this.uv_buffer=uv_buffer;
	}
	public void SetNormBuffer(FloatBuffer norm_buffer) {
		this.norm_buffer=norm_buffer;
	}
	
	public int GetTextureHandle() {
		return texture_handle;
	}
	public int GetCount() {
		return count;
	}
	public IntBuffer GetIndices() {
		return indices;
	}
	public FloatBuffer GetPosBuffer() {
		return pos_buffer;
	}
	public FloatBuffer GetUVBuffer() {
		return uv_buffer;
	}
	public FloatBuffer GetNormBuffer() {
		return norm_buffer;
	}
}
