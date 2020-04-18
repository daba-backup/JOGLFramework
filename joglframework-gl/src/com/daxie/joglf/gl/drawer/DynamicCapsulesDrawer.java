package com.daxie.joglf.gl.drawer;

import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.daxie.basis.coloru8.ColorU8;
import com.daxie.basis.matrix.Matrix;
import com.daxie.basis.matrix.MatrixFunctions;
import com.daxie.basis.vector.Vector;
import com.daxie.basis.vector.VectorFunctions;
import com.daxie.joglf.gl.shader.GLShaderFunctions;
import com.daxie.joglf.gl.shape.Capsule;
import com.daxie.joglf.gl.wrapper.GLWrapper;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL4;

/**
 * Draws capsules.
 * @author Daba
 *
 */
public class DynamicCapsulesDrawer extends Dynamic3DDrawer{
	private Logger logger=LoggerFactory.getLogger(DynamicCapsulesDrawer.class);
	
	private Map<Integer, Capsule> capsules_map;
	private Map<Integer, Integer> indices_sizes_map;
	
	private int buffer_num;
	
	private IntBuffer indices_vbo;
	private IntBuffer pos_vbo;
	private IntBuffer dif_vbo;
	private IntBuffer vao;
	
	public DynamicCapsulesDrawer() {
		capsules_map=new TreeMap<>();
		indices_sizes_map=new HashMap<>();
		
		buffer_num=0;
	}
	
	@Override
	public void SetDefaultProgram() {
		this.RemoveAllPrograms();
		this.AddProgram("color");
	}
	
	@Override
	public void UpdateBuffers() {
		int capsule_num=capsules_map.size();
		
		if(buffer_num!=0)this.DeleteBuffers();
		
		indices_vbo=Buffers.newDirectIntBuffer(capsule_num);
		pos_vbo=Buffers.newDirectIntBuffer(capsule_num);
		dif_vbo=Buffers.newDirectIntBuffer(capsule_num);
		vao=Buffers.newDirectIntBuffer(capsule_num);
		
		GLWrapper.glGenBuffers(capsule_num, indices_vbo);
		GLWrapper.glGenBuffers(capsule_num, pos_vbo);
		GLWrapper.glGenBuffers(capsule_num, dif_vbo);
		GLWrapper.glGenVertexArrays(capsule_num, vao);
		
		buffer_num=capsule_num;
		
		int buffer_count=0;
		for(Capsule capsule:capsules_map.values()) {
			Vector capsule_pos_1=capsule.GetCapsulePos1();
			Vector capsule_pos_2=capsule.GetCapsulePos2();
			float radius=capsule.GetRadius();
			int slice_num=capsule.GetSliceNum();
			int stack_num=capsule.GetStackNum();
			ColorU8 color=capsule.GetColor();
			
			Vector capsule_axis=VectorFunctions.VSub(capsule_pos_2, capsule_pos_1);
			float d=VectorFunctions.VSize(capsule_axis);
			float half_d=d/2.0f;
			
			float th_v=VectorFunctions.VAngleV(capsule_axis);
			float th_h=VectorFunctions.VAngleH(capsule_axis);
			
			Vector center_pos=VectorFunctions.VAdd(capsule_pos_1, capsule_pos_2);
			center_pos=VectorFunctions.VScale(center_pos, 0.5f);
			
			List<Vector> vertices=new ArrayList<>();
			List<Integer> indices=new ArrayList<>();
			
			int vertex_num=slice_num*(stack_num-1)+2;
			
			//North pole
			vertices.add(VectorFunctions.VGet(0.0f, radius+half_d, 0.0f));
			
			//Middle
			for(int i=1;i<stack_num/2;i++) {
				float ph=(float)Math.PI*(float)i/(float)stack_num;
				float y=radius*(float)Math.cos(ph)+half_d;
				float r=radius*(float)Math.sin(ph);
				
				for(int j=0;j<slice_num;j++) {
					float th=2.0f*(float)Math.PI*(float)j/(float)slice_num;
					float x=r*(float)Math.cos(th);
					float z=r*(float)Math.sin(th);
					
					vertices.add(VectorFunctions.VGet(x, y, z));
				}
			}
			for(int i=stack_num/2;i<stack_num;i++) {
				float ph=(float)Math.PI*(float)i/(float)stack_num;
				float y=radius*(float)Math.cos(ph)-half_d;
				float r=radius*(float)Math.sin(ph);
				
				for(int j=0;j<slice_num;j++) {
					float th=2.0f*(float)Math.PI*(float)j/(float)slice_num;
					float x=r*(float)Math.cos(th);
					float z=r*(float)Math.sin(th);
					
					vertices.add(VectorFunctions.VGet(x, y, z));
				}
			}
			
			//South pole
			vertices.add(VectorFunctions.VGet(0.0f,-radius-half_d, 0.0f));
			
			Matrix rot_z=MatrixFunctions.MGetRotZ(th_v-(float)Math.PI/2.0f);
			Matrix rot_y=MatrixFunctions.MGetRotY(-th_h);
			
			for(int i=0;i<vertex_num;i++) {
				Vector vertex=vertices.get(i);
				
				vertex=VectorFunctions.VTransform(vertex, rot_z);
				vertex=VectorFunctions.VTransform(vertex, rot_y);
				vertex=VectorFunctions.VAdd(vertex,center_pos);
				
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
	
	public void AddCapsule(int capsule_id,Capsule capsule) {
		capsules_map.put(capsule_id, capsule);
	}
	public int DeleteCapsule(int capsule_id) {
		if(capsules_map.containsKey(capsule_id)==false) {
			logger.warn("No such capsule. capsule_id={}",capsule_id);
			return -1;
		}
		
		capsules_map.remove(capsule_id);
		
		return 0;
	}
	public void DeleteAllCapsules() {
		capsules_map.clear();
	}
	
	public Capsule GetCapsule(int capsule_id) {
		return capsules_map.get(capsule_id);
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
