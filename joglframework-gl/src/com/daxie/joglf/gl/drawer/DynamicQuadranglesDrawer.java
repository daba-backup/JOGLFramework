package com.daxie.joglf.gl.drawer;

import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.daxie.basis.vector.Vector;
import com.daxie.joglf.gl.shader.GLShaderFunctions;
import com.daxie.joglf.gl.shape.Quadrangle;
import com.daxie.joglf.gl.shape.Vertex3D;
import com.daxie.joglf.gl.texture.TextureMgr;
import com.daxie.joglf.gl.wrapper.GLWrapper;
import com.daxie.log.LogFile;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL4;

/**
 * Draws quadrangles.
 * @author Daba
 *
 */
public class DynamicQuadranglesDrawer extends Dynamic3DDrawer{
	private int texture_handle;
	private Map<Integer, Quadrangle> quadrangles_map;
	
	private IntBuffer indices_vbo;
	private IntBuffer pos_vbo;
	private IntBuffer uv_vbo;
	private IntBuffer norm_vbo;
	private IntBuffer vao;
	
	public DynamicQuadranglesDrawer() {
		texture_handle=-1;
		quadrangles_map=new TreeMap<>();
		
		indices_vbo=Buffers.newDirectIntBuffer(1);
		pos_vbo=Buffers.newDirectIntBuffer(1);
		uv_vbo=Buffers.newDirectIntBuffer(1);
		norm_vbo=Buffers.newDirectIntBuffer(1);
		vao=Buffers.newDirectIntBuffer(1);
		
		GLWrapper.glGenBuffers(1, indices_vbo);
		GLWrapper.glGenBuffers(1, pos_vbo);
		GLWrapper.glGenBuffers(1, uv_vbo);
		GLWrapper.glGenBuffers(1, norm_vbo);
		GLWrapper.glGenVertexArrays(1, vao);
	}
	
	@Override
	public void SetDefaultProgram() {
		this.RemoveAllShaders();
		this.AddProgram("texture");
	}
	
	@Override
	public void UpdateBuffers() {
		int quadrangle_num=quadrangles_map.size();
		int triangle_num=quadrangle_num*2;
		int vertex_num=quadrangle_num*4;
		
		IntBuffer indices_buffer=Buffers.newDirectIntBuffer(triangle_num*3);
		FloatBuffer pos_buffer=Buffers.newDirectFloatBuffer(vertex_num*3);
		FloatBuffer uv_buffer=Buffers.newDirectFloatBuffer(vertex_num*2);
		FloatBuffer norm_buffer=Buffers.newDirectFloatBuffer(vertex_num*3);
		
		int count=0;
		for(Quadrangle quadrangle:quadrangles_map.values()) {
			Vertex3D[] vertices=quadrangle.GetVertices();
			
			for(int i=0;i<4;i++) {
				Vector position=vertices[i].GetPos();
				float u=vertices[i].GetU();
				float v=vertices[i].GetV();
				Vector normal=vertices[i].GetNorm();
				
				pos_buffer.put(position.GetX());
				pos_buffer.put(position.GetY());
				pos_buffer.put(position.GetZ());
				uv_buffer.put(u);
				uv_buffer.put(v);
				norm_buffer.put(normal.GetX());
				norm_buffer.put(normal.GetY());
				norm_buffer.put(normal.GetZ());
			}
			
			indices_buffer.put(count);
			indices_buffer.put(count+1);
			indices_buffer.put(count+2);
			
			indices_buffer.put(count+2);
			indices_buffer.put(count+3);
			indices_buffer.put(count);
			
			count+=4;
		}
		((Buffer)indices_buffer).flip();
		((Buffer)pos_buffer).flip();
		((Buffer)uv_buffer).flip();
		((Buffer)norm_buffer).flip();
		
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, pos_vbo.get(0));
		GLWrapper.glBufferData(GL4.GL_ARRAY_BUFFER, 
				Buffers.SIZEOF_FLOAT*pos_buffer.capacity(), pos_buffer, GL4.GL_DYNAMIC_DRAW);
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, uv_vbo.get(0));
		GLWrapper.glBufferData(GL4.GL_ARRAY_BUFFER, 
				Buffers.SIZEOF_FLOAT*uv_buffer.capacity(), uv_buffer, GL4.GL_DYNAMIC_DRAW);
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, norm_vbo.get(0));
		GLWrapper.glBufferData(GL4.GL_ARRAY_BUFFER, 
				Buffers.SIZEOF_FLOAT*norm_buffer.capacity(), norm_buffer, GL4.GL_DYNAMIC_DRAW);
		
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, 0);
		
		GLWrapper.glBindVertexArray(vao.get(0));
		
		GLWrapper.glBindBuffer(GL4.GL_ELEMENT_ARRAY_BUFFER, indices_vbo.get(0));
		GLWrapper.glBufferData(GL4.GL_ELEMENT_ARRAY_BUFFER, 
				Buffers.SIZEOF_INT*indices_buffer.capacity(), indices_buffer, GL4.GL_DYNAMIC_DRAW);
		
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, pos_vbo.get(0));
		GLWrapper.glEnableVertexAttribArray(0);
		GLWrapper.glVertexAttribPointer(0, 3, GL4.GL_FLOAT, false, Buffers.SIZEOF_FLOAT*3, 0);
		
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, uv_vbo.get(0));
		GLWrapper.glEnableVertexAttribArray(1);
		GLWrapper.glVertexAttribPointer(1, 2, GL4.GL_FLOAT, false, Buffers.SIZEOF_FLOAT*2, 0);
		
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, norm_vbo.get(0));
		GLWrapper.glEnableVertexAttribArray(2);
		GLWrapper.glVertexAttribPointer(2, 3, GL4.GL_FLOAT, false, Buffers.SIZEOF_FLOAT*3, 0);
		
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, 0);
		GLWrapper.glBindVertexArray(0);
	}
	@Override
	public void DeleteBuffers() {
		GLWrapper.glDeleteBuffers(1, indices_vbo);
		GLWrapper.glDeleteBuffers(1, pos_vbo);
		GLWrapper.glDeleteBuffers(1, uv_vbo);
		GLWrapper.glDeleteBuffers(1, norm_vbo);
		GLWrapper.glDeleteVertexArrays(1, vao);
	}
	
	public void SetTextureHandle(int texture_handle) {
		this.texture_handle=texture_handle;
	}
	public void AddQuadrangle(int quadrangle_id,Quadrangle quadrangle) {
		quadrangles_map.put(quadrangle_id, quadrangle);
	}
	public int DeleteQuadrangle(int quadrangle_id) {
		if(quadrangles_map.containsKey(quadrangle_id)==false) {
			LogFile.WriteWarn("[DynamicQuadranglesDrawer-DeleteTriangle] No such quadrangle. quadrangle_id:"+quadrangle_id, true);
			return -1;
		}
		
		quadrangles_map.remove(quadrangle_id);
		
		return 0;
	}
	public void DeleteAllQuadrangles() {
		quadrangles_map.clear();
	}
	
	@Override
	public void Draw() {
		this.Draw(0, "texture_sampler");
	}
	public void Draw(int texture_unit,String sampler_name) {
		List<String> program_names=this.GetProgramNames();
		
		GLWrapper.glActiveTexture(GL4.GL_TEXTURE0+texture_unit);
		
		for(String program_name:program_names) {
			GLShaderFunctions.UseProgram(program_name);
			
			int program_id=GLShaderFunctions.GetProgramID(program_name);
			int sampler_location=GLWrapper.glGetUniformLocation(program_id, sampler_name);
			
			GLWrapper.glBindVertexArray(vao.get(0));
			
			if(texture_handle<0) {
				TextureMgr.EnableDefaultTexture();
				TextureMgr.BindDefaultTexture();
			}
			else {
				TextureMgr.EnableTexture(texture_handle);
				TextureMgr.BindTexture(texture_handle);
			}
			
			GLWrapper.glUniform1i(sampler_location, texture_unit);
			
			int quadrangle_num=quadrangles_map.size();
			int triangle_num=quadrangle_num*2;
			int indices_size=triangle_num*3;
			
			GLWrapper.glEnable(GL4.GL_BLEND);
			GLWrapper.glDrawElements(GL4.GL_TRIANGLES, indices_size, GL4.GL_UNSIGNED_INT, 0);
			GLWrapper.glDisable(GL4.GL_BLEND);
			
			if(texture_handle<0)TextureMgr.DisableDefaultTexture();
			else TextureMgr.DisableTexture(texture_handle);
			
			GLWrapper.glBindVertexArray(0);	
		}
	}
	public void Transfer() {
		GLWrapper.glBindVertexArray(vao.get(0));
		
		int quadrangle_num=quadrangles_map.size();
		int triangle_num=quadrangle_num*2;
		int indices_size=triangle_num*3;
		
		GLWrapper.glEnable(GL4.GL_BLEND);
		GLWrapper.glDrawElements(GL4.GL_TRIANGLES, indices_size, GL4.GL_UNSIGNED_INT, 0);
		GLWrapper.glDisable(GL4.GL_BLEND);
		
		GLWrapper.glBindVertexArray(0);	
	}
}
