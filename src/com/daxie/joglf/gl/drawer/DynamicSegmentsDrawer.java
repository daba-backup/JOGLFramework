package com.daxie.joglf.gl.drawer;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Map;
import java.util.TreeMap;

import com.daxie.basis.coloru8.ColorU8;
import com.daxie.basis.vector.Vector;
import com.daxie.joglf.gl.shape.Vertex3D;
import com.daxie.joglf.gl.wrapper.GLShaderFunctions;
import com.daxie.joglf.gl.wrapper.GLWrapper;
import com.daxie.log.LogFile;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL4;

/**
 * Draws segments.
 * @author Daba
 *
 */
public class DynamicSegmentsDrawer {
	private Map<Integer, Vertex3D[]> segments_map;
	
	private IntBuffer pos_vbo;
	private IntBuffer dif_vbo;
	private IntBuffer vao;
	
	public DynamicSegmentsDrawer() {
		segments_map=new TreeMap<>();
		
		pos_vbo=Buffers.newDirectIntBuffer(1);
		dif_vbo=Buffers.newDirectIntBuffer(1);
		vao=Buffers.newDirectIntBuffer(1);
		
		GLWrapper.glGenBuffers(1, pos_vbo);
		GLWrapper.glGenBuffers(1, dif_vbo);
		GLWrapper.glGenVertexArrays(1, vao);
	}
	
	public void UpdateBuffers() {
		int point_num=segments_map.size()*2;
		
		FloatBuffer pos_buffer=Buffers.newDirectFloatBuffer(point_num*3*2);
		FloatBuffer dif_buffer=Buffers.newDirectFloatBuffer(point_num*4*2);
		
		for(Vertex3D[] segment:segments_map.values()) {
			for(int i=0;i<2;i++) {
				Vector pos=segment[i].GetPos();
				ColorU8 dif=segment[i].GetDif();
				
				pos_buffer.put(pos.GetX());
				pos_buffer.put(pos.GetY());
				pos_buffer.put(pos.GetZ());
				dif_buffer.put(dif.GetR());
				dif_buffer.put(dif.GetG());
				dif_buffer.put(dif.GetB());
				dif_buffer.put(dif.GetA());
			}
		}
		pos_buffer.flip();
		dif_buffer.flip();
		
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, pos_vbo.get(0));
		GLWrapper.glBufferData(GL4.GL_ARRAY_BUFFER, 
				Buffers.SIZEOF_FLOAT*pos_buffer.capacity(), pos_buffer, GL4.GL_DYNAMIC_DRAW);
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, dif_vbo.get(0));
		GLWrapper.glBufferData(GL4.GL_ARRAY_BUFFER, 
				Buffers.SIZEOF_FLOAT*dif_buffer.capacity(), dif_buffer, GL4.GL_DYNAMIC_DRAW);
		
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, 0);
		
		GLWrapper.glBindVertexArray(vao.get(0));
		
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, pos_vbo.get(0));
		GLWrapper.glEnableVertexAttribArray(0);
		GLWrapper.glVertexAttribPointer(0, 3, GL4.GL_FLOAT, false, Buffers.SIZEOF_FLOAT*3, 0);
		
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, dif_vbo.get(0));
		GLWrapper.glEnableVertexAttribArray(1);
		GLWrapper.glVertexAttribPointer(1, 4, GL4.GL_FLOAT, false, Buffers.SIZEOF_FLOAT*4, 0);
		
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, 0);
		GLWrapper.glBindVertexArray(0);
	}
	public void DeleteBuffers() {
		GLWrapper.glDeleteBuffers(1, pos_vbo);
		GLWrapper.glDeleteBuffers(1, dif_vbo);
		GLWrapper.glDeleteVertexArrays(1, vao);
	}
	
	public void AddSegment(int segment_id,Vertex3D vertex1,Vertex3D vertex2) {
		Vertex3D[] vertices=new Vertex3D[2];
		vertices[0]=vertex1;
		vertices[1]=vertex2;
		
		segments_map.put(segment_id, vertices);
	}
	public int DeleteSegment(int segment_id) {
		if(segments_map.containsKey(segment_id)==false) {
			LogFile.WriteWarn("[DynamicSegmentsDrawer-DeleteSegment] No such segment. segment_id:"+segment_id, true);
			return -1;
		}
		
		segments_map.remove(segment_id);
		
		return 0;
	}
	
	public void Draw() {
		GLShaderFunctions.EnableProgram("color");
		
		GLWrapper.glBindVertexArray(vao.get(0));
		
		int point_num=segments_map.size()*2;
		GLWrapper.glEnable(GL4.GL_BLEND);
		GLWrapper.glDrawArrays(GL4.GL_LINES, 0, point_num);
		GLWrapper.glDisable(GL4.GL_BLEND);
		
		GLWrapper.glBindVertexArray(0);
	}
}
