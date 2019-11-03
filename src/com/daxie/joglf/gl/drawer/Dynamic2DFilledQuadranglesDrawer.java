package com.daxie.joglf.gl.drawer;

import java.awt.Point;
import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.daxie.basis.coloru8.ColorU8;
import com.daxie.joglf.gl.shader.GLShaderFunctions;
import com.daxie.joglf.gl.shape.Vertex2D;
import com.daxie.joglf.gl.tool.CoordinateFunctions;
import com.daxie.joglf.gl.wrapper.GLWrapper;
import com.daxie.log.LogFile;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL4;

/**
 * Draws 2D filled quadrangles.
 * @author Daba
 *
 */
public class Dynamic2DFilledQuadranglesDrawer extends Dynamic2DDrawer{
	private Map<Integer, Vertex2D[]> quadrangles_map;
	
	private IntBuffer indices_vbo;
	private IntBuffer pos_vbo;
	private IntBuffer dif_vbo;
	private IntBuffer vao;
	
	public Dynamic2DFilledQuadranglesDrawer() {
		quadrangles_map=new TreeMap<>();
		
		indices_vbo=Buffers.newDirectIntBuffer(1);
		pos_vbo=Buffers.newDirectIntBuffer(1);
		dif_vbo=Buffers.newDirectIntBuffer(1);
		vao=Buffers.newDirectIntBuffer(1);
		
		GLWrapper.glGenBuffers(1, indices_vbo);
		GLWrapper.glGenBuffers(1, pos_vbo);
		GLWrapper.glGenBuffers(1, dif_vbo);
		GLWrapper.glGenVertexArrays(1, vao);
	}
	
	@Override
	public void SetDefaultShader() {
		this.RemoveAllShaders();
		this.AddShader("line_drawer");
	}
	
	@Override
	public void UpdateBuffers() {
		int quadrangle_num=quadrangles_map.size();
		int triangle_num=quadrangle_num*2;
		int point_num=quadrangle_num*4;
		
		IntBuffer indices_buffer=Buffers.newDirectIntBuffer(triangle_num*3);
		FloatBuffer pos_buffer=Buffers.newDirectFloatBuffer(point_num*2);
		FloatBuffer dif_buffer=Buffers.newDirectFloatBuffer(point_num*4);
		
		int window_width=this.GetWindowWidth();
		int window_height=this.GetWindowHeight();
		
		int count=0;
		for(Vertex2D[] quadrangle:quadrangles_map.values()) {
			for(int i=0;i<4;i++) {
				Point point=quadrangle[i].GetPoint();
				ColorU8 dif=quadrangle[i].GetColor();
				
				int x=point.x;
				int y=point.y;
				float normalized_x=CoordinateFunctions.NormalizeCoordinate(x, window_width);
				float normalized_y=CoordinateFunctions.NormalizeCoordinate(y, window_height);
				
				pos_buffer.put(normalized_x);
				pos_buffer.put(normalized_y);
				dif_buffer.put(dif.GetR());
				dif_buffer.put(dif.GetG());
				dif_buffer.put(dif.GetB());
				dif_buffer.put(dif.GetA());
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
		((Buffer)dif_buffer).flip();
		
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, pos_vbo.get(0));
		GLWrapper.glBufferData(GL4.GL_ARRAY_BUFFER, 
				Buffers.SIZEOF_FLOAT*pos_buffer.capacity(), pos_buffer, GL4.GL_DYNAMIC_DRAW);
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, dif_vbo.get(0));
		GLWrapper.glBufferData(GL4.GL_ARRAY_BUFFER, 
				Buffers.SIZEOF_FLOAT*dif_buffer.capacity(), dif_buffer, GL4.GL_DYNAMIC_DRAW);
		
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, 0);
		
		GLWrapper.glBindVertexArray(vao.get(0));
		
		GLWrapper.glBindBuffer(GL4.GL_ELEMENT_ARRAY_BUFFER, indices_vbo.get(0));
		GLWrapper.glBufferData(GL4.GL_ELEMENT_ARRAY_BUFFER, 
				Buffers.SIZEOF_INT*indices_buffer.capacity(), indices_buffer, GL4.GL_DYNAMIC_DRAW);
		
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, pos_vbo.get(0));
		GLWrapper.glEnableVertexAttribArray(0);
		GLWrapper.glVertexAttribPointer(0, 2, GL4.GL_FLOAT, false, Buffers.SIZEOF_FLOAT*2, 0);
		
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, dif_vbo.get(0));
		GLWrapper.glEnableVertexAttribArray(1);
		GLWrapper.glVertexAttribPointer(1, 4, GL4.GL_FLOAT, false, Buffers.SIZEOF_FLOAT*4, 0);
		
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, 0);
		GLWrapper.glBindVertexArray(0);
	}
	@Override
	public void DeleteBuffers() {
		GLWrapper.glDeleteBuffers(1, indices_vbo);
		GLWrapper.glDeleteBuffers(1, pos_vbo);
		GLWrapper.glDeleteBuffers(1, dif_vbo);
		GLWrapper.glDeleteVertexArrays(1, vao);
	}
	
	public void AddQuadrangle(int quadrangle_id,Vertex2D v1,Vertex2D v2,Vertex2D v3,Vertex2D v4) {
		Vertex2D[] vertices=new Vertex2D[4];
		vertices[0]=v1;
		vertices[1]=v2;
		vertices[2]=v3;
		vertices[3]=v4;
		
		quadrangles_map.put(quadrangle_id, vertices);
	}
	public int DeleteQuadrangle(int quadrangle_id) {
		if(quadrangles_map.containsKey(quadrangle_id)==false) {
			LogFile.WriteWarn("[Dynamic2DFilledQuadrangleDrawer-DeleteQuadrangle] ", true);
			LogFile.WriteWarn("No such quadrangle. quadrangle_id:"+quadrangle_id, false);
			
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
		List<String> shader_names=this.GetShaderNames();
		
		for(String shader_name:shader_names) {
			GLShaderFunctions.EnableProgram(shader_name);
			
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
}
