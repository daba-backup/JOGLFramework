package com.github.dabasan.joglf.gl.draw;

import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import com.github.dabasan.basis.coloru8.ColorU8;
import com.github.dabasan.joglf.gl.shader.ShaderProgram;
import com.github.dabasan.joglf.gl.tool.CoordinateFunctions;
import com.github.dabasan.joglf.gl.window.WindowCommonInfo;
import com.github.dabasan.joglf.gl.wrapper.GLWrapper;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL4;

/**
 * Draw functions for 2D primitives
 * @author Daba
 *
 */
public class DrawFunctions2D {
	private static ShaderProgram simple_2d_program;
	private static ShaderProgram texture_drawer_program;
	
	private static int window_width=WindowCommonInfo.DEFAULT_WIDTH;
	private static int window_height=WindowCommonInfo.DEFAULT_HEIGHT;
	
	public static void Initialize() {
		simple_2d_program=new ShaderProgram("simple_2d");
		simple_2d_program.Enable();
		simple_2d_program.SetUniform("z", -1.0f);
		simple_2d_program.Disable();
		
		texture_drawer_program=new ShaderProgram("texture_drawer");
		texture_drawer_program.Enable();
		texture_drawer_program.SetUniform("z", -1.0f);
		texture_drawer_program.Disable();
	}
	
	public static void SetSimple2DZ(float z) {
		simple_2d_program.Enable();
		simple_2d_program.SetUniform("z", z);
		simple_2d_program.Disable();
	}
	public static void SetTextureDrawerZ(float z) {
		texture_drawer_program.Enable();
		texture_drawer_program.SetUniform("z", z);
		texture_drawer_program.Disable();
	}
	
	public static void SetWindowSize(int width,int height) {
		window_width=width;
		window_height=height;
	}
	
	public static void DrawLine2D(int x1,int y1,int x2,int y2,ColorU8 color) {
		IntBuffer pos_vbo=Buffers.newDirectIntBuffer(1);
		IntBuffer color_vbo=Buffers.newDirectIntBuffer(1);
		IntBuffer vao=Buffers.newDirectIntBuffer(1);
		
		FloatBuffer pos_buffer=Buffers.newDirectFloatBuffer(4);
		FloatBuffer color_buffer=Buffers.newDirectFloatBuffer(8);
		
		float normalized_x1=CoordinateFunctions.NormalizeCoordinate(x1, window_width);
		float normalized_y1=CoordinateFunctions.NormalizeCoordinate(y1, window_height);
		float normalized_x2=CoordinateFunctions.NormalizeCoordinate(x2, window_width);
		float normalized_y2=CoordinateFunctions.NormalizeCoordinate(y2, window_height);
		
		pos_buffer.put(normalized_x1);
		pos_buffer.put(normalized_y1);
		pos_buffer.put(normalized_x2);
		pos_buffer.put(normalized_y2);
		
		float color_r=color.GetR();
		float color_g=color.GetG();
		float color_b=color.GetB();
		float color_a=color.GetA();
		for(int i=0;i<2;i++) {
			color_buffer.put(color_r);
			color_buffer.put(color_g);
			color_buffer.put(color_b);
			color_buffer.put(color_a);
		}
		
		((Buffer)pos_buffer).flip();
		((Buffer)color_buffer).flip();
		
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
		GLWrapper.glVertexAttribPointer(0, 2, GL4.GL_FLOAT, false, Buffers.SIZEOF_FLOAT*2, 0);
		
		//Color attribute
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, color_vbo.get(0));
		GLWrapper.glEnableVertexAttribArray(1);
		GLWrapper.glVertexAttribPointer(1, 4, GL4.GL_FLOAT, false, Buffers.SIZEOF_FLOAT*4, 0);
		
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, 0);
		GLWrapper.glBindVertexArray(0);
		
		//Draw
		GLWrapper.glBindVertexArray(vao.get(0));
		GLWrapper.glEnable(GL4.GL_BLEND);
		simple_2d_program.Enable();
		GLWrapper.glDrawArrays(GL4.GL_LINES, 0, 2);
		simple_2d_program.Disable();
		GLWrapper.glDisable(GL4.GL_BLEND);
		GLWrapper.glBindVertexArray(0);
		
		//Delete buffers
		GLWrapper.glDeleteBuffers(1, pos_vbo);
		GLWrapper.glDeleteBuffers(1, color_vbo);
		GLWrapper.glDeleteVertexArrays(1, vao);
	}
	
	/**
	 * Draws a rectangle.
	 * @param x1 Bottom left x-coordinate
	 * @param y1 Bottom left y-coordinate
	 * @param x2 Top right x-coordinate
	 * @param y2 Top right y-coordinate
	 * @param color Color
	 */
	public static void DrawRectangle2D(int x1,int y1,int x2,int y2,ColorU8 color) {
		IntBuffer pos_vbo=Buffers.newDirectIntBuffer(1);
		IntBuffer color_vbo=Buffers.newDirectIntBuffer(1);
		IntBuffer vao=Buffers.newDirectIntBuffer(1);
		
		FloatBuffer pos_buffer=Buffers.newDirectFloatBuffer(2*4);
		FloatBuffer color_buffer=Buffers.newDirectFloatBuffer(4*4);

		float normalized_x1=CoordinateFunctions.NormalizeCoordinate(x1, window_width);
		float normalized_y1=CoordinateFunctions.NormalizeCoordinate(y1, window_height);
		float normalized_x2=CoordinateFunctions.NormalizeCoordinate(x2, window_width);
		float normalized_y2=CoordinateFunctions.NormalizeCoordinate(y2, window_height);
		
		//Bottom left
		pos_buffer.put(normalized_x1);
		pos_buffer.put(normalized_y1);
		//Bottom right
		pos_buffer.put(normalized_x2);
		pos_buffer.put(normalized_y1);
		//Top right
		pos_buffer.put(normalized_x2);
		pos_buffer.put(normalized_y2);
		//Top left
		pos_buffer.put(normalized_x1);
		pos_buffer.put(normalized_y2);
		
		float color_r=color.GetR();
		float color_g=color.GetG();
		float color_b=color.GetB();
		float color_a=color.GetA();
		for(int i=0;i<4;i++) {
			color_buffer.put(color_r);
			color_buffer.put(color_g);
			color_buffer.put(color_b);
			color_buffer.put(color_a);
		}
		
		((Buffer)pos_buffer).flip();
		((Buffer)color_buffer).flip();
		
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
		GLWrapper.glVertexAttribPointer(0, 2, GL4.GL_FLOAT, false, Buffers.SIZEOF_FLOAT*2, 0);
		
		//Color attribute
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, color_vbo.get(0));
		GLWrapper.glEnableVertexAttribArray(1);
		GLWrapper.glVertexAttribPointer(1, 4, GL4.GL_FLOAT, false, Buffers.SIZEOF_FLOAT*4, 0);
		
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, 0);
		GLWrapper.glBindVertexArray(0);
		
		//Draw
		GLWrapper.glBindVertexArray(vao.get(0));
		GLWrapper.glEnable(GL4.GL_BLEND);
		simple_2d_program.Enable();
		GLWrapper.glDrawArrays(GL4.GL_LINE_LOOP, 0, 4);
		simple_2d_program.Disable();
		GLWrapper.glDisable(GL4.GL_BLEND);
		GLWrapper.glBindVertexArray(0);
		
		//Delete buffers
		GLWrapper.glDeleteBuffers(1, pos_vbo);
		GLWrapper.glDeleteBuffers(1, color_vbo);
		GLWrapper.glDeleteVertexArrays(1, vao);
	}
	
	public static void DrawFilledRectangle2D(int x1,int y1,int x2,int y2,ColorU8 color) {
		IntBuffer indices_vbo=Buffers.newDirectIntBuffer(1);
		IntBuffer pos_vbo=Buffers.newDirectIntBuffer(1);
		IntBuffer color_vbo=Buffers.newDirectIntBuffer(1);
		IntBuffer vao=Buffers.newDirectIntBuffer(1);
		
		FloatBuffer pos_buffer=Buffers.newDirectFloatBuffer(2*4);
		FloatBuffer color_buffer=Buffers.newDirectFloatBuffer(4*4);

		float normalized_x1=CoordinateFunctions.NormalizeCoordinate(x1, window_width);
		float normalized_y1=CoordinateFunctions.NormalizeCoordinate(y1, window_height);
		float normalized_x2=CoordinateFunctions.NormalizeCoordinate(x2, window_width);
		float normalized_y2=CoordinateFunctions.NormalizeCoordinate(y2, window_height);
		
		//Bottom left
		pos_buffer.put(normalized_x1);
		pos_buffer.put(normalized_y1);
		//Bottom right
		pos_buffer.put(normalized_x2);
		pos_buffer.put(normalized_y1);
		//Top right
		pos_buffer.put(normalized_x2);
		pos_buffer.put(normalized_y2);
		//Top left
		pos_buffer.put(normalized_x1);
		pos_buffer.put(normalized_y2);
		
		float color_r=color.GetR();
		float color_g=color.GetG();
		float color_b=color.GetB();
		float color_a=color.GetA();
		for(int i=0;i<4;i++) {
			color_buffer.put(color_r);
			color_buffer.put(color_g);
			color_buffer.put(color_b);
			color_buffer.put(color_a);
		}
		
		((Buffer)pos_buffer).flip();
		((Buffer)color_buffer).flip();
		
		IntBuffer indices_buffer=Buffers.newDirectIntBuffer(3*2);
		indices_buffer.put(0);
		indices_buffer.put(1);
		indices_buffer.put(2);
		indices_buffer.put(2);
		indices_buffer.put(3);
		indices_buffer.put(0);
		((Buffer)indices_buffer).flip();
		
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
		GLWrapper.glVertexAttribPointer(0, 2, GL4.GL_FLOAT, false, Buffers.SIZEOF_FLOAT*2, 0);
		
		//Color attribute
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, color_vbo.get(0));
		GLWrapper.glEnableVertexAttribArray(1);
		GLWrapper.glVertexAttribPointer(1, 4, GL4.GL_FLOAT, false, Buffers.SIZEOF_FLOAT*4, 0);
		
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, 0);
		GLWrapper.glBindVertexArray(0);
		
		//Draw
		GLWrapper.glBindVertexArray(vao.get(0));
		GLWrapper.glEnable(GL4.GL_BLEND);
		simple_2d_program.Enable();
		GLWrapper.glDrawElements(GL4.GL_TRIANGLES, indices_buffer.capacity(), GL4.GL_UNSIGNED_INT, 0);
		simple_2d_program.Disable();
		GLWrapper.glDisable(GL4.GL_BLEND);
		GLWrapper.glBindVertexArray(0);
		
		//Delete buffers
		GLWrapper.glDeleteBuffers(1, indices_vbo);
		GLWrapper.glDeleteBuffers(1, pos_vbo);
		GLWrapper.glDeleteBuffers(1, color_vbo);
		GLWrapper.glDeleteVertexArrays(1, vao);
	}
	
	public static void DrawCircle2D(int center_x,int center_y,int radius,int div_num,ColorU8 color) {
		IntBuffer pos_vbo=Buffers.newDirectIntBuffer(1);
		IntBuffer color_vbo=Buffers.newDirectIntBuffer(1);
		IntBuffer vao=Buffers.newDirectIntBuffer(1);
		
		FloatBuffer pos_buffer=Buffers.newDirectFloatBuffer(2*div_num);
		FloatBuffer color_buffer=Buffers.newDirectFloatBuffer(4*div_num);
		
		for(int i=0;i<div_num;i++) {
			float th=(float)Math.PI*2.0f/div_num*i;
			
			float x=radius*(float)Math.cos(th)+center_x;
			float y=radius*(float)Math.sin(th)+center_y;
			
			float normalized_x=CoordinateFunctions.NormalizeCoordinate(x, window_width);
			float normalized_y=CoordinateFunctions.NormalizeCoordinate(y, window_height);
			
			pos_buffer.put(normalized_x);
			pos_buffer.put(normalized_y);
		}
		
		float color_r=color.GetR();
		float color_g=color.GetG();
		float color_b=color.GetB();
		float color_a=color.GetA();
		for(int i=0;i<div_num;i++) {
			color_buffer.put(color_r);
			color_buffer.put(color_g);
			color_buffer.put(color_b);
			color_buffer.put(color_a);
		}
		
		((Buffer)pos_buffer).flip();
		((Buffer)color_buffer).flip();
		
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
		GLWrapper.glVertexAttribPointer(0, 2, GL4.GL_FLOAT, false, Buffers.SIZEOF_FLOAT*2, 0);
		
		//Color attribute
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, color_vbo.get(0));
		GLWrapper.glEnableVertexAttribArray(1);
		GLWrapper.glVertexAttribPointer(1, 4, GL4.GL_FLOAT, false, Buffers.SIZEOF_FLOAT*4, 0);
		
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, 0);
		GLWrapper.glBindVertexArray(0);
		
		//Draw
		GLWrapper.glBindVertexArray(vao.get(0));
		GLWrapper.glEnable(GL4.GL_BLEND);
		simple_2d_program.Enable();
		GLWrapper.glDrawArrays(GL4.GL_LINE_LOOP, 0, div_num);
		simple_2d_program.Disable();
		GLWrapper.glDisable(GL4.GL_BLEND);
		GLWrapper.glBindVertexArray(0);
		
		//Delete buffers
		GLWrapper.glDeleteBuffers(1, pos_vbo);
		GLWrapper.glDeleteBuffers(1, color_vbo);
		GLWrapper.glDeleteVertexArrays(1, vao);
	}
	
	public static void DrawFilledCircle2D(int center_x,int center_y,int radius,int div_num,ColorU8 color) {
		IntBuffer indices_vbo=Buffers.newDirectIntBuffer(1);
		IntBuffer pos_vbo=Buffers.newDirectIntBuffer(1);
		IntBuffer color_vbo=Buffers.newDirectIntBuffer(1);
		IntBuffer vao=Buffers.newDirectIntBuffer(1);
		
		FloatBuffer pos_buffer=Buffers.newDirectFloatBuffer(2*(div_num+1));
		FloatBuffer color_buffer=Buffers.newDirectFloatBuffer(4*(div_num+1));
		
		float normalized_center_x=CoordinateFunctions.NormalizeCoordinate(center_x, window_width);
		float normalized_center_y=CoordinateFunctions.NormalizeCoordinate(center_y, window_height);
		pos_buffer.put(normalized_center_x);
		pos_buffer.put(normalized_center_y);
		
		for(int i=0;i<div_num;i++) {
			float th=(float)Math.PI*2.0f/div_num*i;
			
			float x=radius*(float)Math.cos(th)+center_x;
			float y=radius*(float)Math.sin(th)+center_y;
			
			float normalized_x=CoordinateFunctions.NormalizeCoordinate(x, window_width);
			float normalized_y=CoordinateFunctions.NormalizeCoordinate(y, window_height);
			
			pos_buffer.put(normalized_x);
			pos_buffer.put(normalized_y);
		}
		
		float color_r=color.GetR();
		float color_g=color.GetG();
		float color_b=color.GetB();
		float color_a=color.GetA();
		for(int i=0;i<=div_num;i++) {
			color_buffer.put(color_r);
			color_buffer.put(color_g);
			color_buffer.put(color_b);
			color_buffer.put(color_a);
		}
		
		((Buffer)pos_buffer).flip();
		((Buffer)color_buffer).flip();
		
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
		GLWrapper.glVertexAttribPointer(0, 2, GL4.GL_FLOAT, false, Buffers.SIZEOF_FLOAT*2, 0);
		
		//Color attribute
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, color_vbo.get(0));
		GLWrapper.glEnableVertexAttribArray(1);
		GLWrapper.glVertexAttribPointer(1, 4, GL4.GL_FLOAT, false, Buffers.SIZEOF_FLOAT*4, 0);
		
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, 0);
		GLWrapper.glBindVertexArray(0);
		
		//Draw
		GLWrapper.glBindVertexArray(vao.get(0));
		GLWrapper.glEnable(GL4.GL_BLEND);
		simple_2d_program.Enable();
		GLWrapper.glDrawElements(GL4.GL_TRIANGLES, indices_buffer.capacity(), GL4.GL_UNSIGNED_INT, 0);
		simple_2d_program.Disable();
		GLWrapper.glDisable(GL4.GL_BLEND);
		GLWrapper.glBindVertexArray(0);
		
		//Delete buffers
		GLWrapper.glDeleteBuffers(1, indices_vbo);
		GLWrapper.glDeleteBuffers(1, pos_vbo);
		GLWrapper.glDeleteBuffers(1, color_vbo);
		GLWrapper.glDeleteVertexArrays(1, vao);
	}
	
	public static void TransferQuad(float bottom_left_x,float bottom_left_y,float top_right_x,float top_right_y) {
		IntBuffer indices=Buffers.newDirectIntBuffer(6);
		FloatBuffer pos_buffer=Buffers.newDirectFloatBuffer(8);
		FloatBuffer uv_buffer=Buffers.newDirectFloatBuffer(8);
		
		indices.put(0);
		indices.put(1);
		indices.put(2);
		indices.put(2);
		indices.put(3);
		indices.put(0);
		
		//Bottom left
		pos_buffer.put(bottom_left_x);
		pos_buffer.put(bottom_left_y);
		uv_buffer.put(0.0f);
		uv_buffer.put(0.0f);
		//Bottom right
		pos_buffer.put(top_right_x);
		pos_buffer.put(bottom_left_y);
		uv_buffer.put(1.0f);
		uv_buffer.put(0.0f);
		//Top right
		pos_buffer.put(top_right_x);
		pos_buffer.put(top_right_y);
		uv_buffer.put(1.0f);
		uv_buffer.put(1.0f);
		//Top left
		pos_buffer.put(bottom_left_x);
		pos_buffer.put(top_right_y);
		uv_buffer.put(0.0f);
		uv_buffer.put(1.0f);
		
		((Buffer)indices).flip();
		((Buffer)pos_buffer).flip();
		((Buffer)uv_buffer).flip();
		
		IntBuffer indices_vbo=Buffers.newDirectIntBuffer(1);
		IntBuffer pos_vbo=Buffers.newDirectIntBuffer(1);
		IntBuffer uv_vbo=Buffers.newDirectIntBuffer(1);
		IntBuffer vao=Buffers.newDirectIntBuffer(1);
		
		GLWrapper.glGenBuffers(1, indices_vbo);
		GLWrapper.glGenBuffers(1, pos_vbo);
		GLWrapper.glGenBuffers(1, uv_vbo);
		GLWrapper.glGenVertexArrays(1, vao);
		
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, pos_vbo.get(0));
		GLWrapper.glBufferData(GL4.GL_ARRAY_BUFFER, 
				Buffers.SIZEOF_FLOAT*pos_buffer.capacity(), pos_buffer, GL4.GL_STATIC_DRAW);
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, uv_vbo.get(0));
		GLWrapper.glBufferData(GL4.GL_ARRAY_BUFFER, 
				Buffers.SIZEOF_FLOAT*uv_buffer.capacity(), uv_buffer, GL4.GL_STATIC_DRAW);
		
		GLWrapper.glBindVertexArray(vao.get(0));
		
		GLWrapper.glBindBuffer(GL4.GL_ELEMENT_ARRAY_BUFFER, indices_vbo.get(0));
		GLWrapper.glBufferData(GL4.GL_ELEMENT_ARRAY_BUFFER, 
				Buffers.SIZEOF_INT*indices.capacity(), indices, GL4.GL_STATIC_DRAW);
		
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, pos_vbo.get(0));
		GLWrapper.glEnableVertexAttribArray(0);
		GLWrapper.glVertexAttribPointer(0, 2, GL4.GL_FLOAT, false, Buffers.SIZEOF_FLOAT*2, 0);
		
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, uv_vbo.get(0));
		GLWrapper.glEnableVertexAttribArray(1);
		GLWrapper.glVertexAttribPointer(1, 2, GL4.GL_FLOAT, false, Buffers.SIZEOF_FLOAT*2, 0);
		
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, 0);
		
		GLWrapper.glEnable(GL4.GL_BLEND);
		GLWrapper.glDrawElements(GL4.GL_TRIANGLES, 6, GL4.GL_UNSIGNED_INT, 0);
		GLWrapper.glDisable(GL4.GL_BLEND);
		
		GLWrapper.glBindVertexArray(0);
		
		GLWrapper.glDeleteBuffers(1, indices_vbo);
		GLWrapper.glDeleteBuffers(1, pos_vbo);
		GLWrapper.glDeleteBuffers(1, uv_vbo);
		GLWrapper.glDeleteVertexArrays(1, vao);
	}
	public static void TransferFullscreenQuad() {
		TransferQuad(-1.0f, -1.0f, 1.0f, 1.0f);
	}
	
	public static int DrawTexture(
			int texture_handle,int x,int y,int width,int height,
			float bottom_left_u,float bottom_left_v,
			float bottom_right_u,float bottom_right_v,
			float top_right_u,float top_right_v,
			float top_left_u,float top_left_v) {
		IntBuffer indices=Buffers.newDirectIntBuffer(6);
		FloatBuffer pos_buffer=Buffers.newDirectFloatBuffer(8);
		FloatBuffer uv_buffer=Buffers.newDirectFloatBuffer(8);
		
		indices.put(0);
		indices.put(1);
		indices.put(2);
		indices.put(2);
		indices.put(3);
		indices.put(0);
		
		float normalized_x=2.0f*x/window_width-1.0f;
		float normalized_y=2.0f*y/window_height-1.0f;
		float normalized_width=(float)width/window_width*2.0f;
		float normalized_height=(float)height/window_height*2.0f;
		
		//Bottom left
		pos_buffer.put(normalized_x);
		pos_buffer.put(normalized_y);
		uv_buffer.put(bottom_left_u);
		uv_buffer.put(bottom_left_v);
		//Bottom right
		pos_buffer.put(normalized_x+normalized_width);
		pos_buffer.put(normalized_y);
		uv_buffer.put(bottom_right_u);
		uv_buffer.put(bottom_right_v);
		//Top right
		pos_buffer.put(normalized_x+normalized_width);
		pos_buffer.put(normalized_y+normalized_height);
		uv_buffer.put(top_right_u);
		uv_buffer.put(top_right_v);
		//Top left
		pos_buffer.put(normalized_x);
		pos_buffer.put(normalized_y+normalized_height);
		uv_buffer.put(top_left_u);
		uv_buffer.put(top_left_v);
		
		((Buffer)indices).flip();
		((Buffer)pos_buffer).flip();
		((Buffer)uv_buffer).flip();
		
		IntBuffer indices_vbo=Buffers.newDirectIntBuffer(1);
		IntBuffer pos_vbo=Buffers.newDirectIntBuffer(1);
		IntBuffer uv_vbo=Buffers.newDirectIntBuffer(1);
		IntBuffer vao=Buffers.newDirectIntBuffer(1);
		
		GLWrapper.glGenBuffers(1, indices_vbo);
		GLWrapper.glGenBuffers(1, pos_vbo);
		GLWrapper.glGenBuffers(1, uv_vbo);
		GLWrapper.glGenVertexArrays(1, vao);
		
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, pos_vbo.get(0));
		GLWrapper.glBufferData(GL4.GL_ARRAY_BUFFER, 
				Buffers.SIZEOF_FLOAT*pos_buffer.capacity(), pos_buffer, GL4.GL_STATIC_DRAW);
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, uv_vbo.get(0));
		GLWrapper.glBufferData(GL4.GL_ARRAY_BUFFER, 
				Buffers.SIZEOF_FLOAT*uv_buffer.capacity(), uv_buffer, GL4.GL_STATIC_DRAW);
		
		GLWrapper.glBindVertexArray(vao.get(0));
		
		GLWrapper.glBindBuffer(GL4.GL_ELEMENT_ARRAY_BUFFER, indices_vbo.get(0));
		GLWrapper.glBufferData(GL4.GL_ELEMENT_ARRAY_BUFFER, 
				Buffers.SIZEOF_INT*indices.capacity(), indices, GL4.GL_STATIC_DRAW);
		
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, pos_vbo.get(0));
		GLWrapper.glEnableVertexAttribArray(0);
		GLWrapper.glVertexAttribPointer(0, 2, GL4.GL_FLOAT, false, Buffers.SIZEOF_FLOAT*2, 0);
		
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, uv_vbo.get(0));
		GLWrapper.glEnableVertexAttribArray(1);
		GLWrapper.glVertexAttribPointer(1, 2, GL4.GL_FLOAT, false, Buffers.SIZEOF_FLOAT*2, 0);
		
		GLWrapper.glBindBuffer(GL4.GL_ARRAY_BUFFER, 0);
		
		GLWrapper.glEnable(GL4.GL_BLEND);
		texture_drawer_program.Enable();
		texture_drawer_program.SetTexture("texture_sampler", 0, texture_handle);
		GLWrapper.glDrawElements(GL4.GL_TRIANGLES, 6, GL4.GL_UNSIGNED_INT, 0);
		texture_drawer_program.Disable();
		GLWrapper.glDisable(GL4.GL_BLEND);
		
		GLWrapper.glBindVertexArray(0);
		
		GLWrapper.glDeleteBuffers(1, indices_vbo);
		GLWrapper.glDeleteBuffers(1, pos_vbo);
		GLWrapper.glDeleteBuffers(1, uv_vbo);
		GLWrapper.glDeleteVertexArrays(1, vao);
		
		return 0;
	}
	public static int DrawTexture(int texture_handle,int x,int y,int width,int height) {
		int ret=DrawTexture(
				texture_handle, x, y, width, height, 
				0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f);
		
		return ret;
	}
}
