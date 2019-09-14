package com.daxie.joglf.gl.model.buffer;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import com.daxie.joglf.gl.tool.BufferFunctions;
import com.jogamp.common.nio.Buffers;

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
	
	public static BufferedVertices Interpolate(BufferedVertices bv1,BufferedVertices bv2,float blend_ratio) {
		BufferedVertices interpolated=new BufferedVertices();
		
		int texture_handle=bv1.GetTextureHandle();
		IntBuffer indices=bv1.GetIndices();
		FloatBuffer uv_buffer=bv1.GetUVBuffer();
		
		//Interpolate positions and normals.
		FloatBuffer pos_buffer_1=bv1.GetPosBuffer();
		FloatBuffer pos_buffer_2=bv2.GetPosBuffer();
		FloatBuffer norm_buffer_1=bv1.GetNormBuffer();
		FloatBuffer norm_buffer_2=bv2.GetNormBuffer();
		
		int pos_buffer_cap=pos_buffer_1.capacity();
		int norm_buffer_cap=norm_buffer_1.capacity();
		
		FloatBuffer interpolated_pos_buffer=Buffers.newDirectFloatBuffer(pos_buffer_cap);
		FloatBuffer interpolated_norm_buffer=Buffers.newDirectFloatBuffer(norm_buffer_cap);
		
		float ftemp;
		for(int i=0;i<pos_buffer_cap;i++) {
			ftemp=pos_buffer_1.get(i)*(1.0f-blend_ratio)+pos_buffer_2.get(i)*blend_ratio;
			interpolated_pos_buffer.put(ftemp);
		}
		for(int i=0;i<norm_buffer_cap;i++) {
			ftemp=norm_buffer_1.get(i)*(1.0f-blend_ratio)+norm_buffer_2.get(i)*blend_ratio;
			interpolated_norm_buffer.put(ftemp);
		}
		interpolated_pos_buffer.flip();
		interpolated_norm_buffer.flip();
		
		interpolated.SetTextureHandle(texture_handle);
		interpolated.SetIndices(indices);
		interpolated.SetUVBuffer(uv_buffer);
		interpolated.SetPosBuffer(interpolated_pos_buffer);
		interpolated.SetNormBuffer(interpolated_norm_buffer);
		
		return interpolated;
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
