package com.daxie.joglf.gl.model;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import com.daxie.basis.matrix.Matrix;
import com.daxie.basis.vector.Vector;
import com.daxie.basis.vector.VectorFunctions;
import com.daxie.joglf.gl.model.buffer.BufferedVertices;
import com.daxie.joglf.gl.shader.GLShaderFunctions;
import com.daxie.joglf.gl.shape.Triangle;
import com.daxie.joglf.gl.shape.Vertex3D;
import com.daxie.joglf.gl.texture.TextureMgr;
import com.daxie.joglf.gl.wrapper.GLWrapper;
import com.daxie.log.LogFile;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL4;

/**
 * Model manager
 * @author Daba
 *
 */
public class ModelMgr {
	private List<BufferedVertices> buffered_vertices_list;
	
	private boolean property_updated_flag;
	
	private IntBuffer indices_vbo;
	private IntBuffer pos_vbo;
	private IntBuffer uv_vbo;
	private IntBuffer norm_vbo;
	private IntBuffer vao;
	
	private List<String> program_names;
	
	public ModelMgr(List<BufferedVertices> buffered_vertices_list) {
		this.buffered_vertices_list=buffered_vertices_list;
		
		property_updated_flag=false;
		
		program_names=new ArrayList<>();
		program_names.add("texture");
		
		this.GenerateBuffers();
	}
	
	public void AddProgram(String program_name) {
		program_names.add(program_name);
	}
	public void RemoveProgram(String program_name) {
		program_names.remove(program_name);
	}
	public void RemoveAllPrograms() {
		program_names.clear();
	}
	
	public void Interpolate(ModelMgr frame1,ModelMgr frame2,float blend_ratio) {
		List<BufferedVertices> interpolated_bv_list=new ArrayList<>();
		List<BufferedVertices> frame1_bv_list=frame1.buffered_vertices_list;
		List<BufferedVertices> frame2_bv_list=frame2.buffered_vertices_list;
		
		int element_num=frame1_bv_list.size();
		for(int i=0;i<element_num;i++) {
			BufferedVertices frame1_bv=frame1_bv_list.get(i);
			BufferedVertices frame2_bv=frame2_bv_list.get(i);
			
			BufferedVertices interpolated_bv=BufferedVertices.Interpolate(frame1_bv, frame2_bv, blend_ratio);
			interpolated_bv_list.add(interpolated_bv);
		}
		
		this.buffered_vertices_list=interpolated_bv_list;
	}
	
	public ModelMgr Duplicate() {
		List<BufferedVertices> copied_buffered_vertices_list=new ArrayList<>();
		
		for(BufferedVertices buffered_vertices:buffered_vertices_list) {
			BufferedVertices copied_buffered_vertices=buffered_vertices.Copy();
			copied_buffered_vertices_list.add(copied_buffered_vertices);
		}
		
		ModelMgr duplicated_model=new ModelMgr(copied_buffered_vertices_list);
		
		return duplicated_model;
	}
	
	private void GenerateBuffers() {
		int element_num=buffered_vertices_list.size();
		indices_vbo=Buffers.newDirectIntBuffer(element_num);
		pos_vbo=Buffers.newDirectIntBuffer(element_num);
		uv_vbo=Buffers.newDirectIntBuffer(element_num);
		norm_vbo=Buffers.newDirectIntBuffer(element_num);
		vao=Buffers.newDirectIntBuffer(element_num);
		
		GLWrapper.glGenBuffers(element_num, indices_vbo);
		GLWrapper.glGenBuffers(element_num, pos_vbo);
		GLWrapper.glGenBuffers(element_num, uv_vbo);
		GLWrapper.glGenBuffers(element_num, norm_vbo);
		GLWrapper.glGenVertexArrays(element_num, vao);
		
		for(int i=0;i<element_num;i++) {
			BufferedVertices buffered_vertices=buffered_vertices_list.get(i);
			
			FloatBuffer pos_buffer=buffered_vertices.GetPosBuffer();
			FloatBuffer uv_buffer=buffered_vertices.GetUVBuffer();
			FloatBuffer norm_buffer=buffered_vertices.GetNormBuffer();
			
			GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, pos_vbo.get(i));
			GLWrapper.glBufferData(GL4.GL_ARRAY_BUFFER, 
					Buffers.SIZEOF_FLOAT*pos_buffer.capacity(), pos_buffer, GL4.GL_DYNAMIC_DRAW);
			GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, uv_vbo.get(i));
			GLWrapper.glBufferData(GL4.GL_ARRAY_BUFFER, Buffers.SIZEOF_FLOAT*uv_buffer.capacity(), uv_buffer, GL4.GL_STATIC_DRAW);
			GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, norm_vbo.get(i));
			GLWrapper.glBufferData(GL4.GL_ARRAY_BUFFER, 
					Buffers.SIZEOF_FLOAT*norm_buffer.capacity(), norm_buffer, GL4.GL_DYNAMIC_DRAW);
			
			GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, 0);
		}
		for(int i=0;i<element_num;i++) {
			BufferedVertices buffered_vertices=buffered_vertices_list.get(i);
			IntBuffer indices=buffered_vertices.GetIndices();
			
			GLWrapper.glBindVertexArray(vao.get(i));
			
			GLWrapper.glBindBuffer(GL4.GL_ELEMENT_ARRAY_BUFFER, indices_vbo.get(i));
			GLWrapper.glBufferData(GL4.GL_ELEMENT_ARRAY_BUFFER, 
					Buffers.SIZEOF_INT*indices.capacity(), indices, GL4.GL_STATIC_DRAW);
			
			GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, pos_vbo.get(i));
			GLWrapper.glEnableVertexAttribArray(0);
			GLWrapper.glVertexAttribPointer(0, 3, GL4.GL_FLOAT, false, Buffers.SIZEOF_FLOAT*3, 0);
			
			GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, uv_vbo.get(i));
			GLWrapper.glEnableVertexAttribArray(1);
			GLWrapper.glVertexAttribPointer(1, 2, GL4.GL_FLOAT, false, Buffers.SIZEOF_FLOAT*2, 0);
			
			GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, norm_vbo.get(i));
			GLWrapper.glEnableVertexAttribArray(2);
			GLWrapper.glVertexAttribPointer(2, 3, GL4.GL_FLOAT, false, Buffers.SIZEOF_FLOAT*3, 0);
			
			GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, 0);
			GLWrapper.glBindVertexArray(0);
		}
	}
	private void UpdateBuffers() {
		int element_num=buffered_vertices_list.size();
		
		for(int i=0;i<element_num;i++) {
			BufferedVertices buffered_vertices=buffered_vertices_list.get(i);
			
			FloatBuffer pos_buffer=buffered_vertices.GetPosBuffer();
			FloatBuffer norm_buffer=buffered_vertices.GetNormBuffer();
			
			GLWrapper.glBindVertexArray(vao.get(i));
			
			GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, pos_vbo.get(i));
			GLWrapper.glBufferData(GL4.GL_ARRAY_BUFFER, 
					Buffers.SIZEOF_FLOAT*pos_buffer.capacity(), pos_buffer, GL4.GL_DYNAMIC_DRAW);
			GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, norm_vbo.get(i));
			GLWrapper.glBufferData(GL4.GL_ARRAY_BUFFER, 
					Buffers.SIZEOF_FLOAT*norm_buffer.capacity(), norm_buffer, GL4.GL_DYNAMIC_DRAW);
			
			GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, 0);
			GLWrapper.glBindVertexArray(0);
		}
		
		property_updated_flag=false;
	}
	
	public void DeleteBuffers() {
		int element_num=buffered_vertices_list.size();
		
		GLWrapper.glDeleteBuffers(element_num, indices_vbo);
		GLWrapper.glDeleteBuffers(element_num, pos_vbo);
		GLWrapper.glDeleteBuffers(element_num, uv_vbo);
		GLWrapper.glDeleteVertexArrays(element_num, vao);
		
		for(BufferedVertices buffered_vertices:buffered_vertices_list) {
			int texture_handle=buffered_vertices.GetTextureHandle();
			TextureMgr.DeleteTexture(texture_handle);
		}
	}
	
	public void Draw(int texture_unit,String sampler_name) {
		if(property_updated_flag==true) {
			this.UpdateBuffers();
		}
		
		int element_num=buffered_vertices_list.size();
		
		for(String program_name:program_names) {
			GLShaderFunctions.UseProgram(program_name);
			
			int program_id=GLShaderFunctions.GetProgramID(program_name);
			int sampler_location=GLWrapper.glGetUniformLocation(program_id, sampler_name);
			
			for(int i=0;i<element_num;i++) {
				BufferedVertices buffered_vertices=buffered_vertices_list.get(i);
				int texture_handle=buffered_vertices.GetTextureHandle();
				int count=buffered_vertices.GetCount();
				
				GLWrapper.glBindVertexArray(vao.get(i));
				
				GLWrapper.glActiveTexture(GL4.GL_TEXTURE0+texture_unit);
				if(texture_handle<0) {
					TextureMgr.EnableDefaultTexture();
					TextureMgr.BindDefaultTexture();
				}
				else {
					TextureMgr.EnableTexture(texture_handle);
					TextureMgr.BindTexture(texture_handle);
				}
				GLWrapper.glUniform1i(sampler_location, texture_unit);
				
				GLWrapper.glEnable(GL4.GL_BLEND);
				GLWrapper.glDrawElements(GL4.GL_TRIANGLES,count,GL4.GL_UNSIGNED_INT,0);
				GLWrapper.glDisable(GL4.GL_BLEND);
				
				if(texture_handle<0)TextureMgr.DisableDefaultTexture();
				else TextureMgr.DisableTexture(texture_handle);
				
				GLWrapper.glBindVertexArray(0);
			}
		}
	}
	public void Draw() {
		this.Draw(0, "texture_sampler");
	}
	public void Transfer() {
		if(property_updated_flag==true) {
			this.UpdateBuffers();
		}
		
		int element_num=buffered_vertices_list.size();
			
		for(int i=0;i<element_num;i++) {
			BufferedVertices buffered_vertices=buffered_vertices_list.get(i);
			int count=buffered_vertices.GetCount();
			
			GLWrapper.glBindVertexArray(vao.get(i));
			
			GLWrapper.glEnable(GL4.GL_BLEND);
			GLWrapper.glDrawElements(GL4.GL_TRIANGLES,count,GL4.GL_UNSIGNED_INT,0);
			GLWrapper.glDisable(GL4.GL_BLEND);
			
			GLWrapper.glBindVertexArray(0);
		}
	}
	public void DrawElements(int texture_unit,String sampler_name,int bound) {
		if(property_updated_flag==true) {
			this.UpdateBuffers();
		}
		
		int element_num=buffered_vertices_list.size();
		
		int clamped_bound=0;
		if(bound<0)clamped_bound=0;
		else if(bound<element_num)clamped_bound=bound;
		else clamped_bound=element_num;
		
		for(String program_name:program_names) {
			GLShaderFunctions.UseProgram(program_name);
			
			int program_id=GLShaderFunctions.GetProgramID(program_name);
			int sampler_location=GLWrapper.glGetUniformLocation(program_id, sampler_name);
			
			for(int i=0;i<clamped_bound;i++) {
				BufferedVertices buffered_vertices=buffered_vertices_list.get(i);
				int texture_handle=buffered_vertices.GetTextureHandle();
				int count=buffered_vertices.GetCount();
				
				GLWrapper.glBindVertexArray(vao.get(i));
				
				GLWrapper.glActiveTexture(GL4.GL_TEXTURE0+texture_unit);
				if(texture_handle<0) {
					TextureMgr.EnableDefaultTexture();
					TextureMgr.BindDefaultTexture();
				}
				else {
					TextureMgr.EnableTexture(texture_handle);
					TextureMgr.BindTexture(texture_handle);
				}
				GLWrapper.glUniform1i(sampler_location, texture_unit);
				
				GLWrapper.glEnable(GL4.GL_BLEND);
				GLWrapper.glDrawElements(GL4.GL_TRIANGLES,count,GL4.GL_UNSIGNED_INT,0);
				GLWrapper.glDisable(GL4.GL_BLEND);
				
				if(texture_handle<0)TextureMgr.DisableDefaultTexture();
				else TextureMgr.DisableTexture(texture_handle);
				
				GLWrapper.glBindVertexArray(0);
			}
		}
	}
	public void DrawElements(int bound) {
		this.DrawElements(0, "texture_sampler",bound);
	}
	
	public int GetElementNum() {
		return buffered_vertices_list.size();
	}
	
	public void SetMatrix(Matrix m) {
		for(BufferedVertices buffered_vertices:buffered_vertices_list) {
			FloatBuffer pos_buffer=buffered_vertices.GetPosBuffer();
			FloatBuffer norm_buffer=buffered_vertices.GetNormBuffer();
			
			int capacity=pos_buffer.capacity();
			for(int i=0;i<capacity;i+=3) {
				//pos_buffer
				Vector pos=new Vector();
				pos.SetX(pos_buffer.get(i));
				pos.SetY(pos_buffer.get(i+1));
				pos.SetZ(pos_buffer.get(i+2));
				
				pos=VectorFunctions.VTransform(pos, m);
				
				pos_buffer.put(i,pos.GetX());
				pos_buffer.put(i+1,pos.GetY());
				pos_buffer.put(i+2,pos.GetZ());
				
				//norm_buffer
				Vector norm=new Vector();
				norm.SetX(norm_buffer.get(i));
				norm.SetY(norm_buffer.get(i+1));
				norm.SetZ(norm_buffer.get(i+2));
				
				norm=VectorFunctions.VTransform(norm, m);
				norm=VectorFunctions.VNorm(norm);
				
				norm_buffer.put(i,norm.GetX());
				norm_buffer.put(i+1,norm.GetY());
				norm_buffer.put(i+2,norm.GetZ());
			}
			
			buffered_vertices.SetPosBuffer(pos_buffer);
			buffered_vertices.SetNormBuffer(norm_buffer);
		}
		
		property_updated_flag=true;
	}
	
	public void ChangeTexture(int material_index,int new_texture_handle) {
		if(!(0<=material_index&&material_index<buffered_vertices_list.size())) {
			LogFile.WriteWarn("[ModelMgr-ChangeTexture] Index out of bounds.", true);
			return;
		}
		
		BufferedVertices buffered_vertices=buffered_vertices_list.get(material_index);
		buffered_vertices.SetTextureHandle(new_texture_handle);
	}
	
	public List<Triangle> GetFaces(){
		List<Triangle> ret=new ArrayList<>();
		
		for(BufferedVertices buffered_vertices:buffered_vertices_list) {
			FloatBuffer pos_buffer=buffered_vertices.GetPosBuffer();
			FloatBuffer norm_buffer=buffered_vertices.GetNormBuffer();
			FloatBuffer uv_buffer=buffered_vertices.GetUVBuffer();
			
			int capacity=pos_buffer.capacity();
			int triangle_num=capacity/9;
			
			for(int i=0;i<triangle_num;i++) {
				Triangle triangle=new Triangle();
				
				for(int j=0;j<3;j++) {
					int vec_base_index=i*9+j*3;
					int uv_base_index=i*6+j*2;
					
					//pos_buffer
					Vector pos=new Vector();
					pos.SetX(pos_buffer.get(vec_base_index));
					pos.SetY(pos_buffer.get(vec_base_index+1));
					pos.SetZ(pos_buffer.get(vec_base_index+2));
					
					//norm_buffer
					Vector norm=new Vector();
					norm.SetX(norm_buffer.get(vec_base_index));
					norm.SetY(norm_buffer.get(vec_base_index+1));
					norm.SetZ(norm_buffer.get(vec_base_index+2));
					
					//uv buffer
					float u=uv_buffer.get(uv_base_index);
					float v=uv_buffer.get(uv_base_index+1);
					
					Vertex3D vertex=new Vertex3D();
					vertex.SetPos(pos);
					vertex.SetNorm(norm);
					vertex.SetU(u);
					vertex.SetV(v);
					
					triangle.SetVertex(j, vertex);
				}
				
				ret.add(triangle);
			}
		}
		
		return ret;
	}
}
