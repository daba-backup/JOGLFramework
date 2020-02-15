package com.daxie.joglf.gl.draw;

import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import com.daxie.basis.coloru8.ColorU8;
import com.daxie.basis.coloru8.ColorU8Functions;
import com.daxie.basis.matrix.Matrix;
import com.daxie.basis.matrix.MatrixFunctions;
import com.daxie.basis.vector.Vector;
import com.daxie.basis.vector.VectorFunctions;
import com.daxie.joglf.gl.shader.GLShaderFunctions;
import com.daxie.joglf.gl.shape.Quadrangle;
import com.daxie.joglf.gl.shape.Triangle;
import com.daxie.joglf.gl.shape.Vertex3D;
import com.daxie.joglf.gl.texture.TextureMgr;
import com.daxie.joglf.gl.wrapper.GLWrapper;
import com.daxie.log.LogWriter;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL4;

/**
 * Draw functions for 3D primitives
 * @author Daba
 *
 */
public class GLDrawFunctions3D {
	public static void DrawLine3D(Vector line_pos_1,Vector line_pos_2,ColorU8 color_1,ColorU8 color_2) {
		IntBuffer pos_vbo=Buffers.newDirectIntBuffer(1);
		IntBuffer color_vbo=Buffers.newDirectIntBuffer(1);
		IntBuffer vao=Buffers.newDirectIntBuffer(1);
		
		FloatBuffer pos_buffer=Buffers.newDirectFloatBuffer(6);
		FloatBuffer color_buffer=Buffers.newDirectFloatBuffer(8);
		pos_buffer.put(line_pos_1.GetX());
		pos_buffer.put(line_pos_1.GetY());
		pos_buffer.put(line_pos_1.GetZ());
		pos_buffer.put(line_pos_2.GetX());
		pos_buffer.put(line_pos_2.GetY());
		pos_buffer.put(line_pos_2.GetZ());
		color_buffer.put(color_1.GetR());
		color_buffer.put(color_1.GetG());
		color_buffer.put(color_1.GetB());
		color_buffer.put(color_1.GetA());
		color_buffer.put(color_2.GetR());
		color_buffer.put(color_2.GetG());
		color_buffer.put(color_2.GetB());
		color_buffer.put(color_2.GetA());
		
		((Buffer)pos_buffer).flip();
		((Buffer)color_buffer).flip();
		
		GLShaderFunctions.UseProgram("color");
		
		GLWrapper.glGenBuffers(1, pos_vbo);
		GLWrapper.glGenBuffers(1, color_vbo);
		
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, pos_vbo.get(0));
		GLWrapper.glBufferData(GL4.GL_ARRAY_BUFFER, 
				Buffers.SIZEOF_FLOAT*pos_buffer.capacity(),pos_buffer,GL4.GL_STATIC_DRAW);
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, color_vbo.get(0));
		GLWrapper.glBufferData(GL4.GL_ARRAY_BUFFER, 
				Buffers.SIZEOF_FLOAT*color_buffer.capacity(),color_buffer,GL4.GL_STATIC_DRAW);
		
		GLWrapper.glGenVertexArrays(1, vao);
		GLWrapper.glBindVertexArray(vao.get(0));
		
		//Position attribute
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, pos_vbo.get(0));
		GLWrapper.glEnableVertexAttribArray(0);
		GLWrapper.glVertexAttribPointer(0, 3, GL4.GL_FLOAT, false, Buffers.SIZEOF_FLOAT*3, 0);
		
		//Color attribute
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, color_vbo.get(0));
		GLWrapper.glEnableVertexAttribArray(1);
		GLWrapper.glVertexAttribPointer(1, 4, GL4.GL_FLOAT, false, Buffers.SIZEOF_FLOAT*4, 0);
		
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, 0);
		GLWrapper.glBindVertexArray(0);
		
		//Draw
		GLWrapper.glBindVertexArray(vao.get(0));
		GLWrapper.glEnable(GL4.GL_BLEND);
		GLWrapper.glDrawArrays(GL4.GL_LINES, 0, 2);
		GLWrapper.glDisable(GL4.GL_BLEND);
		GLWrapper.glBindVertexArray(0);
		
		//Delete buffers
		GLWrapper.glDeleteBuffers(1, pos_vbo);
		GLWrapper.glDeleteBuffers(1, color_vbo);
		GLWrapper.glDeleteVertexArrays(1, vao);
	}
	public static void DrawLine3D(Vector line_pos_1,Vector line_pos_2,ColorU8 color) {
		DrawLine3D(line_pos_1, line_pos_2, color,color);
	}
	
	public static void DrawAxes(float length) {
		DrawLine3D(
				VectorFunctions.VGet(-length, 0.0f, 0.0f),
				VectorFunctions.VGet(length, 0.0f, 0.0f),
				ColorU8Functions.GetColorU8(1.0f, 0.0f, 0.0f, 1.0f));
		DrawLine3D(
				VectorFunctions.VGet(0.0f, -length, 0.0f),
				VectorFunctions.VGet(0.0f, length, 0.0f),
				ColorU8Functions.GetColorU8(0.0f, 1.0f, 0.0f, 1.0f));
		DrawLine3D(
				VectorFunctions.VGet(0.0f, 0.0f, -length),
				VectorFunctions.VGet(0.0f, 0.0f, length),
				ColorU8Functions.GetColorU8(0.0f, 0.0f, 1.0f, 1.0f));
	}
	public static void DrawAxes_Positive(float length) {
		DrawLine3D(
				VectorFunctions.VGet(0.0f, 0.0f, 0.0f),
				VectorFunctions.VGet(length, 0.0f, 0.0f),
				ColorU8Functions.GetColorU8(1.0f, 0.0f, 0.0f, 1.0f));
		DrawLine3D(
				VectorFunctions.VGet(0.0f, 0.0f, 0.0f),
				VectorFunctions.VGet(0.0f, length, 0.0f),
				ColorU8Functions.GetColorU8(0.0f, 1.0f, 0.0f, 1.0f));
		DrawLine3D(
				VectorFunctions.VGet(0.0f, 0.0f, 0.0f),
				VectorFunctions.VGet(0.0f, 0.0f, length),
				ColorU8Functions.GetColorU8(0.0f, 0.0f, 1.0f, 1.0f));
	}
	public static void DrawAxes_Negative(float length) {
		DrawLine3D(
				VectorFunctions.VGet(-length, 0.0f, 0.0f),
				VectorFunctions.VGet(0.0f, 0.0f, 0.0f),
				ColorU8Functions.GetColorU8(1.0f, 0.0f, 0.0f, 1.0f));
		DrawLine3D(
				VectorFunctions.VGet(0.0f, -length, 0.0f),
				VectorFunctions.VGet(0.0f, 0.0f, 0.0f),
				ColorU8Functions.GetColorU8(0.0f, 1.0f, 0.0f, 1.0f));
		DrawLine3D(
				VectorFunctions.VGet(0.0f, 0.0f, -length),
				VectorFunctions.VGet(0.0f, 0.0f, 0.0f),
				ColorU8Functions.GetColorU8(0.0f, 0.0f, 1.0f, 1.0f));
	}
	
	public static void DrawTriangle3D(Vector triangle_pos_1,Vector triangle_pos_2,Vector triangle_pos_3,ColorU8 color) {
		Triangle triangle=new Triangle();
		
		Vertex3D[] vertices=new Vertex3D[3];
		for(int i=0;i<3;i++) {
			vertices[i]=new Vertex3D();
		}
		
		vertices[0].SetPos(triangle_pos_1);
		vertices[1].SetPos(triangle_pos_2);
		vertices[2].SetPos(triangle_pos_3);
		for(int i=0;i<3;i++) {
			vertices[i].SetDif(color);
		}
		
		for(int i=0;i<3;i++) {
			triangle.SetVertex(i, vertices[i]);
		}
		
		DrawTriangle3D(triangle);
	}
	public static void DrawTriangle3D(Triangle triangle) {
		IntBuffer pos_vbo=Buffers.newDirectIntBuffer(1);
		IntBuffer color_vbo=Buffers.newDirectIntBuffer(1);
		IntBuffer vao=Buffers.newDirectIntBuffer(1);
		
		Vertex3D[] vertices=triangle.GetVertices();
		
		FloatBuffer pos_buffer=Buffers.newDirectFloatBuffer(9);
		FloatBuffer color_buffer=Buffers.newDirectFloatBuffer(12);
		for(int i=0;i<3;i++) {
			Vector pos=vertices[i].GetPos();
			ColorU8 dif=vertices[i].GetDif();
			
			pos_buffer.put(pos.GetX());
			pos_buffer.put(pos.GetY());
			pos_buffer.put(pos.GetZ());
			color_buffer.put(dif.GetR());
			color_buffer.put(dif.GetG());
			color_buffer.put(dif.GetB());
			color_buffer.put(dif.GetA());
		}
		((Buffer)pos_buffer).flip();
		((Buffer)color_buffer).flip();
		
		GLShaderFunctions.UseProgram("color");
		
		GLWrapper.glGenBuffers(1, pos_vbo);
		GLWrapper.glGenBuffers(1, color_vbo);
		
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, pos_vbo.get(0));
		GLWrapper.glBufferData(GL4.GL_ARRAY_BUFFER, 
				Buffers.SIZEOF_FLOAT*pos_buffer.capacity(),pos_buffer,GL4.GL_STATIC_DRAW);
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, color_vbo.get(0));
		GLWrapper.glBufferData(GL4.GL_ARRAY_BUFFER, 
				Buffers.SIZEOF_FLOAT*color_buffer.capacity(),color_buffer,GL4.GL_STATIC_DRAW);
		
		GLWrapper.glGenVertexArrays(1, vao);
		GLWrapper.glBindVertexArray(vao.get(0));
		
		//Position attribute
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, pos_vbo.get(0));
		GLWrapper.glEnableVertexAttribArray(0);
		GLWrapper.glVertexAttribPointer(0, 3, GL4.GL_FLOAT, false, Buffers.SIZEOF_FLOAT*3, 0);
		
		//Color attribute
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, color_vbo.get(0));
		GLWrapper.glEnableVertexAttribArray(1);
		GLWrapper.glVertexAttribPointer(1, 4, GL4.GL_FLOAT, false, Buffers.SIZEOF_FLOAT*4, 0);
		
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, 0);
		GLWrapper.glBindVertexArray(0);
		
		//Draw
		GLWrapper.glBindVertexArray(vao.get(0));
		GLWrapper.glEnable(GL4.GL_BLEND);
		GLWrapper.glDrawArrays(GL4.GL_LINE_LOOP, 0, 3);
		GLWrapper.glDisable(GL4.GL_BLEND);
		GLWrapper.glBindVertexArray(0);
		
		//Delete buffers
		GLWrapper.glDeleteBuffers(1, pos_vbo);
		GLWrapper.glDeleteBuffers(1, color_vbo);
		GLWrapper.glDeleteVertexArrays(1, vao);
	}
	public static void DrawQuadrangle3D(
			Vector quadrangle_pos_1,Vector quadrangle_pos_2,
			Vector quadrangle_pos_3,Vector quadrangle_pos_4,ColorU8 color) {
		Quadrangle quadrangle=new Quadrangle();
		
		Vertex3D[] vertices=new Vertex3D[4];
		for(int i=0;i<4;i++) {
			vertices[i]=new Vertex3D();
		}
		
		vertices[0].SetPos(quadrangle_pos_1);
		vertices[1].SetPos(quadrangle_pos_2);
		vertices[2].SetPos(quadrangle_pos_3);
		vertices[3].SetPos(quadrangle_pos_4);
		
		for(int i=0;i<4;i++) {
			vertices[i].SetDif(color);
		}
		
		for(int i=0;i<4;i++) {
			quadrangle.SetVertex(i, vertices[i]);
		}
		
		DrawQuadrangle3D(quadrangle);
	}
	public static void DrawQuadrangle3D(Quadrangle quadrangle) {
		IntBuffer pos_vbo=Buffers.newDirectIntBuffer(1);
		IntBuffer color_vbo=Buffers.newDirectIntBuffer(1);
		IntBuffer vao=Buffers.newDirectIntBuffer(1);
		
		Vertex3D[] vertices=quadrangle.GetVertices();
		
		FloatBuffer pos_buffer=Buffers.newDirectFloatBuffer(12);
		FloatBuffer color_buffer=Buffers.newDirectFloatBuffer(16);
		for(int i=0;i<4;i++) {
			Vector pos=vertices[i].GetPos();
			ColorU8 dif=vertices[i].GetDif();
			
			pos_buffer.put(pos.GetX());
			pos_buffer.put(pos.GetY());
			pos_buffer.put(pos.GetZ());
			color_buffer.put(dif.GetR());
			color_buffer.put(dif.GetG());
			color_buffer.put(dif.GetB());
			color_buffer.put(dif.GetA());
		}
		((Buffer)pos_buffer).flip();
		((Buffer)color_buffer).flip();
		
		GLShaderFunctions.UseProgram("color");
		
		GLWrapper.glGenBuffers(1, pos_vbo);
		GLWrapper.glGenBuffers(1, color_vbo);
		
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, pos_vbo.get(0));
		GLWrapper.glBufferData(GL4.GL_ARRAY_BUFFER, 
				Buffers.SIZEOF_FLOAT*pos_buffer.capacity(),pos_buffer,GL4.GL_STATIC_DRAW);
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, color_vbo.get(0));
		GLWrapper.glBufferData(GL4.GL_ARRAY_BUFFER, 
				Buffers.SIZEOF_FLOAT*color_buffer.capacity(),color_buffer,GL4.GL_STATIC_DRAW);
		
		GLWrapper.glGenVertexArrays(1, vao);
		GLWrapper.glBindVertexArray(vao.get(0));
		
		//Position attribute
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, pos_vbo.get(0));
		GLWrapper.glEnableVertexAttribArray(0);
		GLWrapper.glVertexAttribPointer(0, 3, GL4.GL_FLOAT, false, Buffers.SIZEOF_FLOAT*3, 0);
		
		//Color attribute
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, color_vbo.get(0));
		GLWrapper.glEnableVertexAttribArray(1);
		GLWrapper.glVertexAttribPointer(1, 4, GL4.GL_FLOAT, false, Buffers.SIZEOF_FLOAT*4, 0);
		
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, 0);
		GLWrapper.glBindVertexArray(0);
		
		//Draw
		GLWrapper.glBindVertexArray(vao.get(0));
		GLWrapper.glEnable(GL4.GL_BLEND);
		GLWrapper.glDrawArrays(GL4.GL_LINE_LOOP, 0, 4);
		GLWrapper.glDisable(GL4.GL_BLEND);
		GLWrapper.glBindVertexArray(0);
		
		//Delete buffers
		GLWrapper.glDeleteBuffers(1, pos_vbo);
		GLWrapper.glDeleteBuffers(1, color_vbo);
		GLWrapper.glDeleteVertexArrays(1, vao);
	}
	public static void DrawSphere3D(Vector center,float radius,int slice_num,int stack_num,ColorU8 color) {
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
		
		//Make buffers.
		IntBuffer indices_vbo=Buffers.newDirectIntBuffer(1);
		IntBuffer pos_vbo=Buffers.newDirectIntBuffer(1);
		IntBuffer color_vbo=Buffers.newDirectIntBuffer(1);
		IntBuffer vao=Buffers.newDirectIntBuffer(1);
		
		IntBuffer indices_buffer=Buffers.newDirectIntBuffer(indices.size());
		FloatBuffer pos_buffer=Buffers.newDirectFloatBuffer(vertices.size()*3);
		FloatBuffer color_buffer=Buffers.newDirectFloatBuffer(indices.size()*4);
		
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
			color_buffer.put(color_r);
			color_buffer.put(color_g);
			color_buffer.put(color_b);
			color_buffer.put(color_a);
		}
		
		((Buffer)indices_buffer).flip();
		((Buffer)pos_buffer).flip();
		((Buffer)color_buffer).flip();
		
		GLShaderFunctions.UseProgram("color");
		
		GLWrapper.glGenBuffers(1, indices_vbo);
		GLWrapper.glGenBuffers(1, pos_vbo);
		GLWrapper.glGenBuffers(1, color_vbo);
		
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, pos_vbo.get(0));
		GLWrapper.glBufferData(GL4.GL_ARRAY_BUFFER, 
				Buffers.SIZEOF_FLOAT*pos_buffer.capacity(),pos_buffer,GL4.GL_STATIC_DRAW);
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, color_vbo.get(0));
		GLWrapper.glBufferData(GL4.GL_ARRAY_BUFFER, 
				Buffers.SIZEOF_FLOAT*color_buffer.capacity(),color_buffer,GL4.GL_STATIC_DRAW);
		
		GLWrapper.glGenVertexArrays(1, vao);
		GLWrapper.glBindVertexArray(vao.get(0));
		
		//Indices
		GLWrapper.glBindBuffer(GL4.GL_ELEMENT_ARRAY_BUFFER, indices_vbo.get(0));
		GLWrapper.glBufferData(GL4.GL_ELEMENT_ARRAY_BUFFER, 
				Buffers.SIZEOF_INT*indices_buffer.capacity(), indices_buffer, GL4.GL_STATIC_DRAW);
		
		//Position attribute
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, pos_vbo.get(0));
		GLWrapper.glEnableVertexAttribArray(0);
		GLWrapper.glVertexAttribPointer(0, 3, GL4.GL_FLOAT, false, Buffers.SIZEOF_FLOAT*3, 0);
		
		//Color attribute
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, color_vbo.get(0));
		GLWrapper.glEnableVertexAttribArray(1);
		GLWrapper.glVertexAttribPointer(1, 4, GL4.GL_FLOAT, false, Buffers.SIZEOF_FLOAT*4, 0);
		
		GLWrapper.glEnable(GL4.GL_BLEND);
		GLWrapper.glDrawElements(GL4.GL_LINES,indices_size,GL4.GL_UNSIGNED_INT,0);
		GLWrapper.glDisable(GL4.GL_BLEND);
		
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, 0);
		GLWrapper.glBindVertexArray(0);
		
		GLWrapper.glDeleteBuffers(1, indices_vbo);
		GLWrapper.glDeleteBuffers(1, pos_vbo);
		GLWrapper.glDeleteBuffers(1, color_vbo);
		GLWrapper.glDeleteVertexArrays(1, vao);
	}
	public static void DrawCapsule3D(
			Vector capsule_pos_1,Vector capsule_pos_2,
			float radius,int slice_num,int stack_num,ColorU8 color) {
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
		
		//Make buffers.
		IntBuffer indices_vbo=Buffers.newDirectIntBuffer(1);
		IntBuffer pos_vbo=Buffers.newDirectIntBuffer(1);
		IntBuffer color_vbo=Buffers.newDirectIntBuffer(1);
		IntBuffer vao=Buffers.newDirectIntBuffer(1);
		
		IntBuffer indices_buffer=Buffers.newDirectIntBuffer(indices.size());
		FloatBuffer pos_buffer=Buffers.newDirectFloatBuffer(vertices.size()*3);
		FloatBuffer color_buffer=Buffers.newDirectFloatBuffer(indices.size()*4);
		
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
			color_buffer.put(color_r);
			color_buffer.put(color_g);
			color_buffer.put(color_b);
			color_buffer.put(color_a);
		}
		
		((Buffer)indices_buffer).flip();
		((Buffer)pos_buffer).flip();
		((Buffer)color_buffer).flip();
		
		GLShaderFunctions.UseProgram("color");
		
		GLWrapper.glGenBuffers(1, indices_vbo);
		GLWrapper.glGenBuffers(1, pos_vbo);
		GLWrapper.glGenBuffers(1, color_vbo);
		
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, pos_vbo.get(0));
		GLWrapper.glBufferData(GL4.GL_ARRAY_BUFFER, 
				Buffers.SIZEOF_FLOAT*pos_buffer.capacity(),pos_buffer,GL4.GL_STATIC_DRAW);
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, color_vbo.get(0));
		GLWrapper.glBufferData(GL4.GL_ARRAY_BUFFER, 
				Buffers.SIZEOF_FLOAT*color_buffer.capacity(),color_buffer,GL4.GL_STATIC_DRAW);
		
		GLWrapper.glGenVertexArrays(1, vao);
		GLWrapper.glBindVertexArray(vao.get(0));
		
		//Indices
		GLWrapper.glBindBuffer(GL4.GL_ELEMENT_ARRAY_BUFFER, indices_vbo.get(0));
		GLWrapper.glBufferData(GL4.GL_ELEMENT_ARRAY_BUFFER, 
				Buffers.SIZEOF_INT*indices_buffer.capacity(), indices_buffer, GL4.GL_STATIC_DRAW);
		
		//Position attribute
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, pos_vbo.get(0));
		GLWrapper.glEnableVertexAttribArray(0);
		GLWrapper.glVertexAttribPointer(0, 3, GL4.GL_FLOAT, false, Buffers.SIZEOF_FLOAT*3, 0);
		
		//Color attribute
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, color_vbo.get(0));
		GLWrapper.glEnableVertexAttribArray(1);
		GLWrapper.glVertexAttribPointer(1, 4, GL4.GL_FLOAT, false, Buffers.SIZEOF_FLOAT*4, 0);
		
		GLWrapper.glEnable(GL4.GL_BLEND);
		GLWrapper.glDrawElements(GL4.GL_LINES,indices_size,GL4.GL_UNSIGNED_INT,0);
		GLWrapper.glDisable(GL4.GL_BLEND);
		
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, 0);
		GLWrapper.glBindVertexArray(0);
		
		GLWrapper.glDeleteBuffers(1, indices_vbo);
		GLWrapper.glDeleteBuffers(1, pos_vbo);
		GLWrapper.glDeleteBuffers(1, color_vbo);
		GLWrapper.glDeleteVertexArrays(1, vao);
	}
	
	public static void DrawTexturedTriangle3D(Triangle triangle,int texture_handle,boolean use_face_normal_flag) {
		if(TextureMgr.TextureExists(texture_handle)==false) {
			LogWriter.WriteWarn("[GLDrawFunctions-DrawTexturedTriangle3D] No such texture. texture_handle:"+texture_handle,true);
			return;
		}
		
		IntBuffer pos_vbo=Buffers.newDirectIntBuffer(1);
		IntBuffer uv_vbo=Buffers.newDirectIntBuffer(1);
		IntBuffer norm_vbo=Buffers.newDirectIntBuffer(1);
		IntBuffer vao=Buffers.newDirectIntBuffer(1);
		
		Vertex3D[] vertices=triangle.GetVertices();
		FloatBuffer pos_buffer=Buffers.newDirectFloatBuffer(9);
		FloatBuffer uv_buffer=Buffers.newDirectFloatBuffer(6);
		FloatBuffer norm_buffer=Buffers.newDirectFloatBuffer(9);
		
		//Calculate the face normal.
		Vector v1=VectorFunctions.VSub(vertices[1].GetPos(), vertices[0].GetPos());
		Vector v2=VectorFunctions.VSub(vertices[2].GetPos(), vertices[0].GetPos());
		Vector face_norm=VectorFunctions.VCross(v1, v2);
		face_norm=VectorFunctions.VNorm(face_norm);
		
		for(int i=0;i<3;i++) {
			Vector pos=vertices[i].GetPos();
			float u=vertices[i].GetU();
			float v=vertices[i].GetV();
			
			pos_buffer.put(pos.GetX());
			pos_buffer.put(pos.GetY());
			pos_buffer.put(pos.GetZ());
			uv_buffer.put(u);
			uv_buffer.put(v);
			
			if(use_face_normal_flag==true) {
				norm_buffer.put(face_norm.GetX());
				norm_buffer.put(face_norm.GetY());
				norm_buffer.put(face_norm.GetZ());
			}
			else {
				Vector norm=vertices[i].GetNorm();
				
				norm_buffer.put(norm.GetX());
				norm_buffer.put(norm.GetY());
				norm_buffer.put(norm.GetZ());
			}
		}
		((Buffer)pos_buffer).flip();
		((Buffer)uv_buffer).flip();
		((Buffer)norm_buffer).flip();
		
		GLShaderFunctions.UseProgram("texture");
		
		int program_id=GLShaderFunctions.GetProgramID("texture");
		int sampler_location=GLWrapper.glGetUniformLocation(program_id, "texture_sampler");
		
		GLWrapper.glGenBuffers(1, pos_vbo);
		GLWrapper.glGenBuffers(1, uv_vbo);
		GLWrapper.glGenBuffers(1, norm_vbo);
		
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, pos_vbo.get(0));
		GLWrapper.glBufferData(GL4.GL_ARRAY_BUFFER, 
				Buffers.SIZEOF_FLOAT*pos_buffer.capacity(), pos_buffer, GL4.GL_STATIC_DRAW);
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, uv_vbo.get(0));
		GLWrapper.glBufferData(GL4.GL_ARRAY_BUFFER, 
				Buffers.SIZEOF_FLOAT*uv_buffer.capacity(), uv_buffer, GL4.GL_STATIC_DRAW);
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, norm_vbo.get(0));
		GLWrapper.glBufferData(GL4.GL_ARRAY_BUFFER, 
				Buffers.SIZEOF_FLOAT*norm_buffer.capacity(), norm_buffer, GL4.GL_STATIC_DRAW);
		
		GLWrapper.glGenVertexArrays(1, vao);
		GLWrapper.glBindVertexArray(vao.get(0));
		
		//Position attribute
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, pos_vbo.get(0));
		GLWrapper.glEnableVertexAttribArray(0);
		GLWrapper.glVertexAttribPointer(0, 3, GL4.GL_FLOAT, false, Buffers.SIZEOF_FLOAT*3, 0);
		
		//UVs attribute
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, uv_vbo.get(0));
		GLWrapper.glEnableVertexAttribArray(1);
		GLWrapper.glVertexAttribPointer(1, 2, GL4.GL_FLOAT, false, Buffers.SIZEOF_FLOAT*2, 0);
		
		//Normal attribute
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, norm_vbo.get(0));
		GLWrapper.glEnableVertexAttribArray(2);
		GLWrapper.glVertexAttribPointer(2, 3, GL4.GL_FLOAT, false, Buffers.SIZEOF_FLOAT*3, 0);
		
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, 0);
		GLWrapper.glBindVertexArray(0);
		
		GLWrapper.glBindVertexArray(vao.get(0));
		
		//Draw
		if(texture_handle<0) {
			TextureMgr.EnableDefaultTexture();
			TextureMgr.BindDefaultTexture();
		}
		else {
			TextureMgr.EnableTexture(texture_handle);
			TextureMgr.BindTexture(texture_handle);
		}
		
		GLWrapper.glActiveTexture(GL4.GL_TEXTURE0);
		GLWrapper.glUniform1i(sampler_location, 0);
		
		GLWrapper.glEnable(GL4.GL_BLEND);
		GLWrapper.glDrawArrays(GL4.GL_TRIANGLES, 0, 3);
		GLWrapper.glDisable(GL4.GL_BLEND);
		
		GLWrapper.glBindVertexArray(0);
		
		if(texture_handle<0)TextureMgr.DisableDefaultTexture();
		else TextureMgr.DisableTexture(texture_handle);
		
		//Delete buffers
		GLWrapper.glDeleteBuffers(1, pos_vbo);
		GLWrapper.glDeleteBuffers(1, uv_vbo);
		GLWrapper.glDeleteVertexArrays(1, vao);
	}
}
