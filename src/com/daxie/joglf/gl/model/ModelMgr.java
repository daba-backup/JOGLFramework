package com.daxie.joglf.gl.model;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import com.daxie.basis.matrix.Matrix;
import com.daxie.basis.matrix.MatrixFunctions;
import com.daxie.basis.vector.Vector;
import com.daxie.basis.vector.VectorFunctions;
import com.daxie.joglf.gl.model.buffer.BufferedVertices;
import com.daxie.joglf.gl.model.collision.CollInfo;
import com.daxie.joglf.gl.model.collision.CollTriangle;
import com.daxie.joglf.gl.texture.TextureMgr;
import com.daxie.joglf.gl.wrapper.GLShaderFunctions;
import com.daxie.joglf.gl.wrapper.GLWrapper;
import com.daxie.log.LogFile;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL4;

/**
 * Model manager
 * @author Daba
 *
 */
class ModelMgr {
	private List<BufferedVertices> buffered_vertices_list;
	private CollInfo coll_info;
	
	private Vector position;
	private Vector rotation;
	private Vector scale;
	
	private boolean property_updated_flag;
	
	private IntBuffer indices_vbo;
	private IntBuffer pos_vbo;
	private IntBuffer uv_vbo;
	private IntBuffer norm_vbo;
	private IntBuffer vao;
	
	public ModelMgr(List<BufferedVertices> buffered_vertices_list) {
		this.buffered_vertices_list=buffered_vertices_list;
		
		position=VectorFunctions.VGet(0.0f, 0.0f, 0.0f);
		rotation=VectorFunctions.VGet(0.0f, 0.0f, 0.0f);
		scale=VectorFunctions.VGet(1.0f, 1.0f, 1.0f);
		
		property_updated_flag=false;
		
		this.GenerateBuffers();
	}
	
	public void Interpolate(ModelMgr frame1,ModelMgr frame2,float blend_ratio) {
		Vector orig_position=VectorFunctions.VGet(position.GetX(), position.GetY(), position.GetZ());
		Vector orig_rotation=VectorFunctions.VGet(rotation.GetX(), rotation.GetY(), rotation.GetZ());
		
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
		
		this.SetPosition(orig_position);
		this.SetRotation(orig_rotation);
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
	
	public void Draw() {
		if(property_updated_flag==true) {
			this.UpdateBuffers();
		}
		
		int element_num=buffered_vertices_list.size();
		
		GLShaderFunctions.EnableProgram("texture");
		
		int sampler=GLShaderFunctions.GetSampler();
		GLWrapper.glBindSampler(0, sampler);
		
		for(int i=0;i<element_num;i++) {
			BufferedVertices buffered_vertices=buffered_vertices_list.get(i);
			int texture_handle=buffered_vertices.GetTextureHandle();
			int count=buffered_vertices.GetCount();
			
			GLWrapper.glBindVertexArray(vao.get(i));
			
			if(texture_handle<0) {
				TextureMgr.EnableDefaultTexture();
				TextureMgr.BindDefaultTexture();
			}
			else {
				TextureMgr.EnableTexture(texture_handle);
				TextureMgr.BindTexture(texture_handle);
			}
			
			GLWrapper.glDrawElements(GL4.GL_TRIANGLES,count,GL4.GL_UNSIGNED_INT,0);
			
			if(texture_handle<0)TextureMgr.DisableDefaultTexture();
			else TextureMgr.DisableTexture(texture_handle);
			
			GLWrapper.glBindVertexArray(0);
		}
	}
	public void DrawElements(int bound) {
		if(property_updated_flag==true) {
			this.UpdateBuffers();
		}
		
		int element_num=buffered_vertices_list.size();
		
		int clamped_bound=0;
		if(bound<0)clamped_bound=0;
		else if(bound<element_num)clamped_bound=bound;
		else clamped_bound=element_num;
		
		GLShaderFunctions.EnableProgram("texture");
		
		int sampler=GLShaderFunctions.GetSampler();
		GLWrapper.glBindSampler(0, sampler);
		
		for(int i=0;i<clamped_bound;i++) {
			BufferedVertices buffered_vertices=buffered_vertices_list.get(i);
			int texture_handle=buffered_vertices.GetTextureHandle();
			int count=buffered_vertices.GetCount();
			
			GLWrapper.glBindVertexArray(vao.get(i));
			
			if(texture_handle<0) {
				TextureMgr.EnableDefaultTexture();
				TextureMgr.BindDefaultTexture();
			}
			else {
				TextureMgr.EnableTexture(texture_handle);
				TextureMgr.BindTexture(texture_handle);
			}
			
			GLWrapper.glDrawElements(GL4.GL_TRIANGLES,count,GL4.GL_UNSIGNED_INT,0);
			
			if(texture_handle<0)TextureMgr.DisableDefaultTexture();
			else TextureMgr.DisableTexture(texture_handle);
			
			GLWrapper.glBindVertexArray(0);
		}
	}
	
	public Vector GetPosition() {
		return new Vector(position);
	}
	public Vector GetRotation() {
		return new Vector(rotation);
	}
	public Vector GetScale() {
		return new Vector(scale);
	}
	
	public int GetElementNum() {
		return buffered_vertices_list.size();
	}
	
	public void SetPosition(Vector position) {
		Vector translate=VectorFunctions.VSub(position, this.position);
		
		float translate_x=translate.GetX();
		float translate_y=translate.GetY();
		float translate_z=translate.GetZ();
		
		for(BufferedVertices buffered_vertices:buffered_vertices_list) {
			FloatBuffer pos_buffer=buffered_vertices.GetPosBuffer();
			int capacity=pos_buffer.capacity();
			
			for(int i=0;i<capacity;i++) {
				float value=pos_buffer.get(i);
				
				if(i%3==0)pos_buffer.put(i,value+translate_x);
				else if(i%3==1)pos_buffer.put(i,value+translate_y);
				else pos_buffer.put(i,value+translate_z);
			}
			
			buffered_vertices.SetPosBuffer(pos_buffer);
		}
		
		this.position=position;
		property_updated_flag=true;
	}
	public void SetRotation(Vector rotation) {
		Vector displacement=VectorFunctions.VSub(rotation, this.rotation);
		
		Matrix rot_x=MatrixFunctions.MGetRotX(displacement.GetX());
		Matrix rot_y=MatrixFunctions.MGetRotY(displacement.GetY());
		Matrix rot_z=MatrixFunctions.MGetRotZ(displacement.GetZ());
		
		for(BufferedVertices buffered_vertices:buffered_vertices_list) {
			FloatBuffer pos_buffer=buffered_vertices.GetPosBuffer();
			FloatBuffer norm_buffer=buffered_vertices.GetNormBuffer();
			
			int capacity;
			
			capacity=pos_buffer.capacity();
			for(int i=0;i<capacity;i+=3) {
				Vector vtemp=new Vector();
				
				vtemp.SetX(pos_buffer.get(i));
				vtemp.SetY(pos_buffer.get(i+1));
				vtemp.SetZ(pos_buffer.get(i+2));
				
				vtemp=VectorFunctions.VTransform(vtemp, rot_x);
				vtemp=VectorFunctions.VTransform(vtemp, rot_y);
				vtemp=VectorFunctions.VTransform(vtemp, rot_z);
				
				pos_buffer.put(i,vtemp.GetX());
				pos_buffer.put(i+1,vtemp.GetY());
				pos_buffer.put(i+2,vtemp.GetZ());
			}
			
			capacity=norm_buffer.capacity();
			for(int i=0;i<capacity;i+=3) {
				Vector vtemp=new Vector();
				
				vtemp.SetX(norm_buffer.get(i));
				vtemp.SetY(norm_buffer.get(i+1));
				vtemp.SetZ(norm_buffer.get(i+2));
				
				vtemp=VectorFunctions.VTransform(vtemp, rot_x);
				vtemp=VectorFunctions.VTransform(vtemp, rot_y);
				vtemp=VectorFunctions.VTransform(vtemp, rot_z);
				
				norm_buffer.put(i,vtemp.GetX());
				norm_buffer.put(i+1,vtemp.GetY());
				norm_buffer.put(i+2,vtemp.GetZ());
			}
			
			buffered_vertices.SetPosBuffer(pos_buffer);
			buffered_vertices.SetNormBuffer(norm_buffer);
		}
		
		this.rotation=rotation;
		property_updated_flag=true;
	}
	public void SetScale(Vector scale) {
		float displacement_x=scale.GetX()/this.scale.GetX();
		float displacement_y=scale.GetY()/this.scale.GetY();
		float displacement_z=scale.GetZ()/this.scale.GetZ();
		
		for(BufferedVertices buffered_vertices:buffered_vertices_list) {
			FloatBuffer pos_buffer=buffered_vertices.GetPosBuffer();
			int capacity=pos_buffer.capacity();
			
			for(int i=0;i<capacity;i++) {
				float value=pos_buffer.get(i);
				
				if(i%3==0)pos_buffer.put(i,value*displacement_x);
				else if(i%3==1)pos_buffer.put(i,value*displacement_y);
				else pos_buffer.put(i,value*displacement_z);
			}
			
			buffered_vertices.SetPosBuffer(pos_buffer);
		}
		
		this.scale=scale;
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
	
	public boolean IsCollInfoSetup() {
		if(coll_info==null)return false;
		else return true;
	}
	public void SetupCollInfo() {
		coll_info=new CollInfo();
		
		List<Vector> vertices=new ArrayList<>();
		List<Vector> vertex_normals=new ArrayList<>();
		for(BufferedVertices buffered_vertices:buffered_vertices_list) {
			FloatBuffer pos_buffer=buffered_vertices.GetPosBuffer();
			FloatBuffer norm_buffer=buffered_vertices.GetNormBuffer();
			
			int capacity=pos_buffer.capacity();
			
			for(int i=0;i<capacity;i+=3) {
				Vector vertex=new Vector();
				Vector vertex_normal=new Vector();
				
				vertex.SetX(pos_buffer.get(i));
				vertex.SetY(pos_buffer.get(i+1));
				vertex.SetZ(pos_buffer.get(i+2));
				vertices.add(vertex);
				
				vertex_normal.SetX(norm_buffer.get(i));
				vertex_normal.SetY(norm_buffer.get(i+1));
				vertex_normal.SetZ(norm_buffer.get(i+2));
				vertex_normals.add(vertex_normal);
			}
		}
		
		int vertex_num=vertices.size();
		for(int i=0;i<vertex_num;i+=3) {
			Vector[] vertices_temp=new Vector[3];
			Vector[] vertex_normals_temp=new Vector[3];
			vertices_temp[0]=vertices.get(i);
			vertices_temp[1]=vertices.get(i+1);
			vertices_temp[2]=vertices.get(i+2);
			vertex_normals_temp[0]=vertex_normals.get(i);
			vertex_normals_temp[1]=vertex_normals.get(i+1);
			vertex_normals_temp[2]=vertex_normals.get(i+2);
			
			Vector face_normal=VectorFunctions.VAverage(vertex_normals_temp);
			face_normal=VectorFunctions.VNorm(face_normal);
			
			CollTriangle coll_triangle=new CollTriangle();
			coll_triangle.SetVertex(0, vertices_temp[0]);
			coll_triangle.SetVertex(1, vertices_temp[1]);
			coll_triangle.SetVertex(2, vertices_temp[2]);
			coll_triangle.SetFaceNormal(face_normal);
			
			coll_info.AddTriangle(coll_triangle);
		}
	}
	
	public List<CollTriangle> GetCollTriangles(){
		return coll_info.GetCollTriangles();
	}
}
