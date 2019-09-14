package com.daxie.joglf.gl.gl4;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import com.daxie.joglf.basis.coloru8.ColorU8;
import com.daxie.joglf.basis.coloru8.ColorU8Functions;
import com.daxie.joglf.basis.vector.Vector;
import com.daxie.joglf.basis.vector.VectorFunctions;
import com.daxie.joglf.gl.shape.Triangle;
import com.daxie.joglf.gl.shape.Vertex3D;
import com.daxie.joglf.gl.texture.TextureMgr;
import com.daxie.joglf.log.LogFile;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL4;

/**
 * Draw functions of GL4.
 * @author Daba
 *
 */
public class GL4DrawFunctions {
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
		
		pos_buffer.flip();
		color_buffer.flip();
		
		GL4ShaderFunctions.EnableProgram("color");
		
		GL4Wrapper.glGenBuffers(1, pos_vbo);
		GL4Wrapper.glGenBuffers(1, color_vbo);
		
		GL4Wrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, pos_vbo.get(0));
		GL4Wrapper.glBufferData(GL4.GL_ARRAY_BUFFER, Buffers.SIZEOF_FLOAT*3*2,pos_buffer,GL4.GL_STATIC_DRAW);
		GL4Wrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, color_vbo.get(0));
		GL4Wrapper.glBufferData(GL4.GL_ARRAY_BUFFER, Buffers.SIZEOF_FLOAT*4*2,color_buffer,GL4.GL_STATIC_DRAW);
		
		GL4Wrapper.glGenVertexArrays(1, vao);
		GL4Wrapper.glBindVertexArray(vao.get(0));
		
		//Position attribute
		GL4Wrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, pos_vbo.get(0));
		GL4Wrapper.glEnableVertexAttribArray(0);
		GL4Wrapper.glVertexAttribPointer(0, 3, GL4.GL_FLOAT, false, Buffers.SIZEOF_FLOAT*3, 0);
		
		//Color attribute
		GL4Wrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, color_vbo.get(0));
		GL4Wrapper.glEnableVertexAttribArray(1);
		GL4Wrapper.glVertexAttribPointer(1, 4, GL4.GL_FLOAT, false, Buffers.SIZEOF_FLOAT*4, 0);
		
		GL4Wrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, 0);
		GL4Wrapper.glBindVertexArray(0);
		
		//Draw
		GL4Wrapper.glBindVertexArray(vao.get(0));
		GL4Wrapper.glDrawArrays(GL4.GL_LINES, 0, 2);
		GL4Wrapper.glBindVertexArray(0);
		
		//Delete buffers
		GL4Wrapper.glDeleteBuffers(1, pos_vbo);
		GL4Wrapper.glDeleteBuffers(1, color_vbo);
		GL4Wrapper.glDeleteVertexArrays(1, vao);
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
		pos_buffer.flip();
		color_buffer.flip();
		
		GL4ShaderFunctions.EnableProgram("color");
		
		GL4Wrapper.glGenBuffers(1, pos_vbo);
		GL4Wrapper.glGenBuffers(1, color_vbo);
		
		GL4Wrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, pos_vbo.get(0));
		GL4Wrapper.glBufferData(GL4.GL_ARRAY_BUFFER, Buffers.SIZEOF_FLOAT*3*2,pos_buffer,GL4.GL_STATIC_DRAW);
		GL4Wrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, color_vbo.get(0));
		GL4Wrapper.glBufferData(GL4.GL_ARRAY_BUFFER, Buffers.SIZEOF_FLOAT*4*2,color_buffer,GL4.GL_STATIC_DRAW);
		
		GL4Wrapper.glGenVertexArrays(1, vao);
		GL4Wrapper.glBindVertexArray(vao.get(0));
		
		//Position attribute
		GL4Wrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, pos_vbo.get(0));
		GL4Wrapper.glEnableVertexAttribArray(0);
		GL4Wrapper.glVertexAttribPointer(0, 3, GL4.GL_FLOAT, false, Buffers.SIZEOF_FLOAT*3, 0);
		
		//Color attribute
		GL4Wrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, color_vbo.get(0));
		GL4Wrapper.glEnableVertexAttribArray(1);
		GL4Wrapper.glVertexAttribPointer(1, 4, GL4.GL_FLOAT, false, Buffers.SIZEOF_FLOAT*4, 0);
		
		GL4Wrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, 0);
		GL4Wrapper.glBindVertexArray(0);
		
		//Draw
		GL4Wrapper.glBindVertexArray(vao.get(0));
		GL4Wrapper.glDrawArrays(GL4.GL_LINES, 0, 2);
		GL4Wrapper.glBindVertexArray(0);
		
		//Delete buffers
		GL4Wrapper.glDeleteBuffers(1, pos_vbo);
		GL4Wrapper.glDeleteBuffers(1, color_vbo);
		GL4Wrapper.glDeleteVertexArrays(1, vao);
	}
	
	public static void DrawTexturedTriangle3D(Triangle triangle,int texture_handle,boolean use_face_normal_flag) {
		if(TextureMgr.TextureExists(texture_handle)==false) {
			LogFile.WriteError(
					"[GL4DrawFunctions-DrawTexturedTriangle3D] No such texture. texture_handle:"+texture_handle,true);
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
		pos_buffer.flip();
		uv_buffer.flip();
		norm_buffer.flip();
		
		GL4ShaderFunctions.EnableProgram("texture");
		
		GL4Wrapper.glGenBuffers(1, pos_vbo);
		GL4Wrapper.glGenBuffers(1, uv_vbo);
		GL4Wrapper.glGenBuffers(1, norm_vbo);
		
		GL4Wrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, pos_vbo.get(0));
		GL4Wrapper.glBufferData(GL4.GL_ARRAY_BUFFER, Buffers.SIZEOF_FLOAT*pos_buffer.capacity(), pos_buffer, GL4.GL_STATIC_DRAW);
		GL4Wrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, uv_vbo.get(0));
		GL4Wrapper.glBufferData(GL4.GL_ARRAY_BUFFER, Buffers.SIZEOF_FLOAT*uv_buffer.capacity(), uv_buffer, GL4.GL_STATIC_DRAW);
		GL4Wrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, norm_vbo.get(0));
		GL4Wrapper.glBufferData(GL4.GL_ARRAY_BUFFER, Buffers.SIZEOF_FLOAT*norm_buffer.capacity(), norm_buffer, GL4.GL_STATIC_DRAW);
		
		GL4Wrapper.glGenVertexArrays(1, vao);
		GL4Wrapper.glBindVertexArray(vao.get(0));
		
		//Position attribute
		GL4Wrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, pos_vbo.get(0));
		GL4Wrapper.glEnableVertexAttribArray(0);
		GL4Wrapper.glVertexAttribPointer(0, 3, GL4.GL_FLOAT, false, Buffers.SIZEOF_FLOAT*3, 0);
		
		//UVs attribute
		GL4Wrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, uv_vbo.get(0));
		GL4Wrapper.glEnableVertexAttribArray(1);
		GL4Wrapper.glVertexAttribPointer(1, 2, GL4.GL_FLOAT, false, Buffers.SIZEOF_FLOAT*2, 0);
		
		//Normal attribute
		GL4Wrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, norm_vbo.get(0));
		GL4Wrapper.glEnableVertexAttribArray(2);
		GL4Wrapper.glVertexAttribPointer(2, 3, GL4.GL_FLOAT, false, Buffers.SIZEOF_FLOAT*3, 0);
		
		GL4Wrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, 0);
		GL4Wrapper.glBindVertexArray(0);
		
		GL4Wrapper.glBindVertexArray(vao.get(0));
		
		int sampler=GL4ShaderFunctions.GetSampler();
		GL4Wrapper.glBindSampler(0, sampler);
		
		//Draw
		if(texture_handle<0) {
			TextureMgr.EnableDefaultTexture();
			TextureMgr.BindDefaultTexture();
		}
		else {
			TextureMgr.EnableTexture(texture_handle);
			TextureMgr.BindTexture(texture_handle);
		}
		
		GL4Wrapper.glDrawArrays(GL4.GL_TRIANGLES, 0, 3);
		
		GL4Wrapper.glBindVertexArray(0);
		
		if(texture_handle<0)TextureMgr.DisableDefaultTexture();
		else TextureMgr.DisableTexture(texture_handle);
		
		//Delete buffers
		GL4Wrapper.glDeleteBuffers(1, pos_vbo);
		GL4Wrapper.glDeleteBuffers(1, uv_vbo);
		GL4Wrapper.glDeleteVertexArrays(1, vao);
	}
}
