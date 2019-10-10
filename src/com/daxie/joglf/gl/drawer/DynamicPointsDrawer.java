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
 * Draws points.
 * @author Daba
 *
 */
public class DynamicPointsDrawer {
	private Map<Integer, Vertex3D> points_map;
	
	private IntBuffer pos_vbo;
	private IntBuffer dif_vbo;
	private IntBuffer vao;
	
	public DynamicPointsDrawer() {
		points_map=new TreeMap<>();
		
		pos_vbo=Buffers.newDirectIntBuffer(1);
		dif_vbo=Buffers.newDirectIntBuffer(1);
		vao=Buffers.newDirectIntBuffer(1);
		
		GLWrapper.glGenBuffers(1, pos_vbo);
		GLWrapper.glGenBuffers(1, dif_vbo);
		GLWrapper.glGenVertexArrays(1, vao);
	}
	
	public void UpdateBuffers() {
		int point_num=points_map.size();
		
		FloatBuffer pos_buffer=Buffers.newDirectFloatBuffer(point_num*3);
		FloatBuffer dif_buffer=Buffers.newDirectFloatBuffer(point_num*4);
		
		for(Vertex3D point:points_map.values()) {
			Vector pos=point.GetPos();
			ColorU8 dif=point.GetDif();
			
			pos_buffer.put(pos.GetX());
			pos_buffer.put(pos.GetY());
			pos_buffer.put(pos.GetZ());
			dif_buffer.put(dif.GetR());
			dif_buffer.put(dif.GetG());
			dif_buffer.put(dif.GetB());
			dif_buffer.put(dif.GetA());
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
	
	public void AddPoint(int point_id,Vertex3D point) {
		points_map.put(point_id, point);
	}
	public int DeletePoint(int point_id) {
		if(points_map.containsKey(point_id)==false) {
			LogFile.WriteWarn("[DynamicPointsDrawer-DeletePoint] No such point. point_id:"+point_id, true);
			return -1;
		}
		
		points_map.remove(point_id);
		
		return 0;
	}
	
	public void Draw() {
		GLShaderFunctions.EnableProgram("color");
		
		GLWrapper.glBindVertexArray(vao.get(0));
		
		int point_num=points_map.size();
		GLWrapper.glEnable(GL4.GL_BLEND);
		GLWrapper.glDrawArrays(GL4.GL_POINTS, 0, point_num);
		GLWrapper.glDisable(GL4.GL_BLEND);
		
		GLWrapper.glBindVertexArray(0);
	}
}
