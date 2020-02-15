package com.daxie.joglf.gl.drawer;

import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.daxie.basis.coloru8.ColorU8;
import com.daxie.basis.vector.Vector;
import com.daxie.basis.vector.VectorFunctions;
import com.daxie.joglf.gl.shader.GLShaderFunctions;
import com.daxie.joglf.gl.shape.Sphere;
import com.daxie.joglf.gl.wrapper.GLWrapper;
import com.daxie.log.LogWriter;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL4;

/**
 * Draws spheres.
 * @author Daba
 *
 */
public class DynamicSpheresDrawer extends Dynamic3DDrawer{
	private Map<Integer, Sphere> spheres_map;
	private Map<Integer, Integer> indices_sizes_map;
	
	private int buffer_num;
	
	private IntBuffer indices_vbo;
	private IntBuffer pos_vbo;
	private IntBuffer dif_vbo;
	private IntBuffer vao;
	
	public DynamicSpheresDrawer() {
		spheres_map=new TreeMap<>();
		indices_sizes_map=new HashMap<>();
		
		buffer_num=0;
	}
	
	@Override
	public void SetDefaultProgram() {
		this.RemoveAllShaders();
		this.AddProgram("color");
	}
	
	@Override
	public void UpdateBuffers() {
		int sphere_num=spheres_map.size();
		
		if(buffer_num!=0)this.DeleteBuffers();
		
		indices_vbo=Buffers.newDirectIntBuffer(sphere_num);
		pos_vbo=Buffers.newDirectIntBuffer(sphere_num);
		dif_vbo=Buffers.newDirectIntBuffer(sphere_num);
		vao=Buffers.newDirectIntBuffer(sphere_num);
		
		GLWrapper.glGenBuffers(sphere_num, indices_vbo);
		GLWrapper.glGenBuffers(sphere_num, pos_vbo);
		GLWrapper.glGenBuffers(sphere_num, dif_vbo);
		GLWrapper.glGenVertexArrays(sphere_num, vao);
		
		buffer_num=sphere_num;
		
		int buffer_count=0;
		for(Sphere sphere:spheres_map.values()) {
			Vector center=sphere.GetCenter();
			float radius=sphere.GetRadius();
			int slice_num=sphere.GetSliceNum();
			int stack_num=sphere.GetStackNum();
			ColorU8 color=sphere.GetColor();
			
			List<Vector> vertices=new ArrayList<>();
			List<Integer> indices=new ArrayList<>();
			
			int vertex_num=slice_num*(stack_num-1)+2;
			
			//North pole
			vertices.add(VectorFunctions.VGet(0.0f, 1.0f, 0.0f));
			
			//Middle
			for(int i=1;i<stack_num;i++) {
				float ph=(float)Math.PI*(float)i/(float)stack_num;
				float y=(float)Math.cos(ph);
				float r=(float)Math.sin(ph);
				
				for(int j=0;j<slice_num;j++) {
					float th=2.0f*(float)Math.PI*(float)j/(float)slice_num;
					float x=r*(float)Math.cos(th);
					float z=r*(float)Math.sin(th);
					
					vertices.add(VectorFunctions.VGet(x, y, z));
				}
			}
			
			//South pole
			vertices.add(VectorFunctions.VGet(0.0f,-1.0f, 0.0f));
			
			for(int i=0;i<vertex_num;i++) {
				Vector vertex=vertices.get(i);
				
				vertex=VectorFunctions.VScale(vertex, radius);
				vertex=VectorFunctions.VAdd(vertex, center);
				
				vertices.set(i, vertex);
			}
			
			//Ridgelines around the north pole
			for(int i=1;i<=slice_num;i++) {
				indices.add(0);
				indices.add(i);
			}
			
			//Ridgelines in the middle
			int count=1;
			for(int i=2;i<stack_num;i++) {
				for(int j=1;j<slice_num;j++) {
					indices.add(count);
					indices.add(count+1);
					
					indices.add(count);
					indices.add(count+slice_num);
					
					count++;
				}
				
				indices.add(count);
				indices.add(count-slice_num+1);
				
				indices.add(count);
				indices.add(count+slice_num);
				
				count++;
			}
			
			//Ridgelines in the bottom
			for(int i=1;i<slice_num;i++) {
				indices.add(count);
				indices.add(count+1);
				
				indices.add(count);
				indices.add(vertex_num-1);
				
				count++;
			}
			
			indices.add(count);
			indices.add(count-slice_num+1);
			
			indices.add(count);
			indices.add(vertex_num-1);
			
			IntBuffer indices_buffer=Buffers.newDirectIntBuffer(indices.size());
			FloatBuffer pos_buffer=Buffers.newDirectFloatBuffer(vertices.size()*3);
			FloatBuffer dif_buffer=Buffers.newDirectFloatBuffer(indices.size()*4);
			
			int indices_size=indices.size();
			for(int i=0;i<indices_size;i++) {
				indices_buffer.put(indices.get(i));
			}
			for(int i=0;i<vertex_num;i++) {
				Vector vertex=vertices.get(i);
				
				pos_buffer.put(vertex.GetX());
				pos_buffer.put(vertex.GetY());
				pos_buffer.put(vertex.GetZ());
			}
			
			float color_r=color.GetR();
			float color_g=color.GetG();
			float color_b=color.GetB();
			float color_a=color.GetA();
			for(int i=0;i<indices_size;i++) {
				dif_buffer.put(color_r);
				dif_buffer.put(color_g);
				dif_buffer.put(color_b);
				dif_buffer.put(color_a);
			}
			
			((Buffer)indices_buffer).flip();
			((Buffer)pos_buffer).flip();
			((Buffer)dif_buffer).flip();
			
			GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, pos_vbo.get(buffer_count));
			GLWrapper.glBufferData(GL4.GL_ARRAY_BUFFER, 
					Buffers.SIZEOF_FLOAT*pos_buffer.capacity(),pos_buffer,GL4.GL_DYNAMIC_DRAW);
			GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, dif_vbo.get(buffer_count));
			GLWrapper.glBufferData(GL4.GL_ARRAY_BUFFER, 
					Buffers.SIZEOF_FLOAT*dif_buffer.capacity(),dif_buffer,GL4.GL_DYNAMIC_DRAW);
			
			GLWrapper.glBindVertexArray(vao.get(buffer_count));
			
			//Indices
			GLWrapper.glBindBuffer(GL4.GL_ELEMENT_ARRAY_BUFFER, indices_vbo.get(buffer_count));
			GLWrapper.glBufferData(GL4.GL_ELEMENT_ARRAY_BUFFER, 
					Buffers.SIZEOF_INT*indices_buffer.capacity(), indices_buffer, GL4.GL_DYNAMIC_DRAW);
			
			//Position attribute
			GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, pos_vbo.get(buffer_count));
			GLWrapper.glEnableVertexAttribArray(0);
			GLWrapper.glVertexAttribPointer(0, 3, GL4.GL_FLOAT, false, Buffers.SIZEOF_FLOAT*3, 0);
			
			//Color attribute
			GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, dif_vbo.get(buffer_count));
			GLWrapper.glEnableVertexAttribArray(1);
			GLWrapper.glVertexAttribPointer(1, 4, GL4.GL_FLOAT, false, Buffers.SIZEOF_FLOAT*4, 0);
			
			GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, 0);
			GLWrapper.glBindVertexArray(0);
			
			indices_sizes_map.put(buffer_count, indices_size);
			buffer_count++;
		}
	}
	@Override
	public void DeleteBuffers() {
		GLWrapper.glDeleteBuffers(buffer_num, indices_vbo);
		GLWrapper.glDeleteBuffers(buffer_num, pos_vbo);
		GLWrapper.glDeleteBuffers(buffer_num, dif_vbo);
		GLWrapper.glDeleteVertexArrays(buffer_num, vao);
		
		buffer_num=0;
	}
	
	public void AddSphere(int sphere_id,Sphere sphere) {
		spheres_map.put(sphere_id, sphere);
	}
	public int DeleteSphere(int sphere_id) {
		if(spheres_map.containsKey(sphere_id)==false) {
			LogWriter.WriteWarn("[DynamicSpheresDrawer-DeleteSphere] No such sphere. sphere_id:"+sphere_id, true);
			return -1;
		}
		
		spheres_map.remove(sphere_id);
		
		return 0;
	}
	public void DeleteAllSpheres() {
		spheres_map.clear();
	}
	
	public Sphere GetSphere(int sphere_id) {
		return spheres_map.get(sphere_id);
	}
	
	@Override
	public void Draw() {
		List<String> program_names=this.GetProgramNames();
		
		for(String program_name:program_names) {
			GLShaderFunctions.UseProgram(program_name);
			
			for(int i=0;i<buffer_num;i++) {
				GLWrapper.glBindVertexArray(vao.get(i));
				
				int indices_size=indices_sizes_map.get(i);
				GLWrapper.glEnable(GL4.GL_BLEND);
				GLWrapper.glDrawElements(GL4.GL_LINES, indices_size, GL4.GL_UNSIGNED_INT, 0);
				GLWrapper.glDisable(GL4.GL_BLEND);
				
				GLWrapper.glBindVertexArray(0);
			}	
		}
	}
}
