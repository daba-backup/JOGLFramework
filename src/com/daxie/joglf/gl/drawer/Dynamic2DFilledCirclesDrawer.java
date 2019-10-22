package com.daxie.joglf.gl.drawer;

import java.awt.Point;
import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.daxie.basis.coloru8.ColorU8;
import com.daxie.joglf.gl.shape.Circle2D;
import com.daxie.joglf.gl.window.WindowCommonInfoStock;
import com.daxie.joglf.gl.wrapper.GLShaderFunctions;
import com.daxie.joglf.gl.wrapper.GLWrapper;
import com.daxie.log.LogFile;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL4;

/**
 * Draws filled circles.
 * @author Daba
 *
 */
public class Dynamic2DFilledCirclesDrawer implements Dynamic2DDrawer{
	private Map<Integer, Circle2D> circles_map;
	private Map<Integer, Integer> indices_sizes_map;
	
	private int buffer_num;
	
	private IntBuffer indices_vbo;
	private IntBuffer pos_vbo;
	private IntBuffer dif_vbo;
	private IntBuffer vao;
	
	private int window_width;
	private int window_height;
	
	public Dynamic2DFilledCirclesDrawer() {
		circles_map=new TreeMap<>();
		indices_sizes_map=new HashMap<>();
		
		buffer_num=0;
		
		window_width=WindowCommonInfoStock.DEFAULT_WIDTH;
		window_height=WindowCommonInfoStock.DEFAULT_HEIGHT;
	}
	
	@Override
	public void UpdateBuffers() {
		int circle_num=circles_map.size();
		
		if(buffer_num!=0)this.DeleteBuffers();
		
		indices_vbo=Buffers.newDirectIntBuffer(circle_num);
		pos_vbo=Buffers.newDirectIntBuffer(circle_num);
		dif_vbo=Buffers.newDirectIntBuffer(circle_num);
		vao=Buffers.newDirectIntBuffer(circle_num);
		
		GLWrapper.glGenBuffers(circle_num, indices_vbo);
		GLWrapper.glGenBuffers(circle_num, pos_vbo);
		GLWrapper.glGenBuffers(circle_num, dif_vbo);
		GLWrapper.glGenVertexArrays(circle_num, vao);
		
		buffer_num=circle_num;
		
		int count=0;
		for(Circle2D circle:circles_map.values()) {
			Point center=circle.GetCenter();
			int radius=circle.GetRadius();
			ColorU8 color=circle.GetColor();
			int div_num=circle.GetDivNum();
			
			FloatBuffer pos_buffer=Buffers.newDirectFloatBuffer(2*(div_num+1));
			FloatBuffer dif_buffer=Buffers.newDirectFloatBuffer(4*(div_num+1));
			
			int center_x=center.x;
			int center_y=center.y;
			float normalized_center_x=2.0f*center_x/window_width-1.0f;
			float normalized_center_y=2.0f*center_y/window_height-1.0f;
			pos_buffer.put(normalized_center_x);
			pos_buffer.put(normalized_center_y);
			
			for(int i=0;i<div_num;i++) {
				float th=(float)Math.PI*2.0f/div_num*i;
				
				float x=radius*(float)Math.cos(th)+center_x;
				float y=radius*(float)Math.sin(th)+center_y;
				
				float normalized_x=2.0f*x/window_width-1.0f;
				float normalized_y=2.0f*y/window_height-1.0f;
				
				pos_buffer.put(normalized_x);
				pos_buffer.put(normalized_y);
			}
			
			float color_r=color.GetR();
			float color_g=color.GetG();
			float color_b=color.GetB();
			float color_a=color.GetA();
			for(int i=0;i<=div_num;i++) {
				dif_buffer.put(color_r);
				dif_buffer.put(color_g);
				dif_buffer.put(color_b);
				dif_buffer.put(color_a);
			}
			
			((Buffer)pos_buffer).flip();
			((Buffer)dif_buffer).flip();
			
			IntBuffer indices_buffer=Buffers.newDirectIntBuffer(3*div_num);
			for(int i=1;i<div_num;i++) {
				indices_buffer.put(i);
				indices_buffer.put(i+1);
				indices_buffer.put(0);
			}
			indices_buffer.put(div_num);
			indices_buffer.put(1);
			indices_buffer.put(0);
			
			((Buffer)indices_buffer).flip();
			
			GLShaderFunctions.EnableProgram("line_drawer");
			
			GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, pos_vbo.get(count));
			GLWrapper.glBufferData(GL4.GL_ARRAY_BUFFER, 
					Buffers.SIZEOF_FLOAT*pos_buffer.capacity(),pos_buffer,GL4.GL_DYNAMIC_DRAW);
			GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, dif_vbo.get(count));
			GLWrapper.glBufferData(GL4.GL_ARRAY_BUFFER, 
					Buffers.SIZEOF_FLOAT*dif_buffer.capacity(),dif_buffer,GL4.GL_DYNAMIC_DRAW);
			
			GLWrapper.glBindVertexArray(vao.get(count));
			
			//Indices
			GLWrapper.glBindBuffer(GL4.GL_ELEMENT_ARRAY_BUFFER, indices_vbo.get(count));
			GLWrapper.glBufferData(GL4.GL_ELEMENT_ARRAY_BUFFER, 
					Buffers.SIZEOF_INT*indices_buffer.capacity(), indices_buffer, GL4.GL_DYNAMIC_DRAW);
			
			//Position attribute
			GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, pos_vbo.get(count));
			GLWrapper.glEnableVertexAttribArray(0);
			GLWrapper.glVertexAttribPointer(0, 2, GL4.GL_FLOAT, false, Buffers.SIZEOF_FLOAT*2, 0);
			
			//Color attribute
			GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, dif_vbo.get(count));
			GLWrapper.glEnableVertexAttribArray(1);
			GLWrapper.glVertexAttribPointer(1, 4, GL4.GL_FLOAT, false, Buffers.SIZEOF_FLOAT*4, 0);
			
			GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, 0);
			GLWrapper.glBindVertexArray(0);
			
			int indices_size=indices_buffer.capacity();
			indices_sizes_map.put(count, indices_size);
			
			count++;
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
	
	public void AddCircle(int circle_id,Circle2D circle) {
		circles_map.put(circle_id, circle);
	}
	public int DeleteCircle(int circle_id) {
		if(circles_map.containsKey(circle_id)==false) {
			LogFile.WriteWarn("[Dynamic2DFilledCirclesDrawer-DeleteCircle] No such circle. circle_id:"+circle_id, true);
			return -1;
		}
		
		circles_map.remove(circle_id);
		
		return 0;
	}
	public void DeleteAllCircles() {
		circles_map.clear();
	}
	
	@Override
	public void SetWindowSize(int width,int height) {
		window_width=width;
		window_height=height;
	}
	
	@Override
	public void Draw() {
		GLShaderFunctions.EnableProgram("line_drawer");
		
		for(int i=0;i<buffer_num;i++) {
			GLWrapper.glBindVertexArray(vao.get(i));
			
			int indices_size=indices_sizes_map.get(i);
			GLWrapper.glEnable(GL4.GL_BLEND);
			GLWrapper.glDrawElements(GL4.GL_TRIANGLES, indices_size, GL4.GL_UNSIGNED_INT, 0);
			GLWrapper.glDisable(GL4.GL_BLEND);
			
			GLWrapper.glBindVertexArray(0);
		}
	}
}
