package com.github.dabasan.joglf.gl.draw;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import com.github.dabasan.basis.coloru8.ColorU8;
import com.github.dabasan.basis.coloru8.ColorU8Functions;
import com.github.dabasan.basis.matrix.Matrix;
import com.github.dabasan.basis.matrix.MatrixFunctions;
import com.github.dabasan.basis.vector.Vector;
import com.github.dabasan.basis.vector.VectorFunctions;
import com.github.dabasan.joglf.gl.shader.ShaderProgram;
import com.github.dabasan.joglf.gl.shape.Quadrangle;
import com.github.dabasan.joglf.gl.shape.Triangle;
import com.github.dabasan.joglf.gl.shape.Vertex3D;
import com.github.dabasan.joglf.gl.wrapper.GLWrapper;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL;

/**
 * Draw functions for 3D primitives
 * 
 * @author Daba
 *
 */
public class DrawFunctions3D {
	private static ShaderProgram color_program;
	private static ShaderProgram texture_program;

	public static void Initialize() {
		color_program = new ShaderProgram("color");
		texture_program = new ShaderProgram("texture");
	}

	public static void DrawLine3D(Vector line_pos_1, Vector line_pos_2, ColorU8 color_1,
			ColorU8 color_2) {
		final IntBuffer pos_vbo = Buffers.newDirectIntBuffer(1);
		final IntBuffer color_vbo = Buffers.newDirectIntBuffer(1);
		final IntBuffer vao = Buffers.newDirectIntBuffer(1);

		final FloatBuffer pos_buffer = Buffers.newDirectFloatBuffer(6);
		final FloatBuffer color_buffer = Buffers.newDirectFloatBuffer(8);
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

		GLWrapper.glGenBuffers(1, pos_vbo);
		GLWrapper.glGenBuffers(1, color_vbo);

		GLWrapper.glBindBuffer(GL.GL_ARRAY_BUFFER, pos_vbo.get(0));
		GLWrapper.glBufferData(GL.GL_ARRAY_BUFFER, Buffers.SIZEOF_FLOAT * pos_buffer.capacity(),
				pos_buffer, GL.GL_STATIC_DRAW);
		GLWrapper.glBindBuffer(GL.GL_ARRAY_BUFFER, color_vbo.get(0));
		GLWrapper.glBufferData(GL.GL_ARRAY_BUFFER, Buffers.SIZEOF_FLOAT * color_buffer.capacity(),
				color_buffer, GL.GL_STATIC_DRAW);

		GLWrapper.glGenVertexArrays(1, vao);
		GLWrapper.glBindVertexArray(vao.get(0));

		// Position attribute
		GLWrapper.glBindBuffer(GL.GL_ARRAY_BUFFER, pos_vbo.get(0));
		GLWrapper.glEnableVertexAttribArray(0);
		GLWrapper.glVertexAttribPointer(0, 3, GL.GL_FLOAT, false, Buffers.SIZEOF_FLOAT * 3, 0);

		// Color attribute
		GLWrapper.glBindBuffer(GL.GL_ARRAY_BUFFER, color_vbo.get(0));
		GLWrapper.glEnableVertexAttribArray(1);
		GLWrapper.glVertexAttribPointer(1, 4, GL.GL_FLOAT, false, Buffers.SIZEOF_FLOAT * 4, 0);

		GLWrapper.glBindBuffer(GL.GL_ARRAY_BUFFER, 0);
		GLWrapper.glBindVertexArray(0);

		// Draw
		GLWrapper.glBindVertexArray(vao.get(0));
		GLWrapper.glEnable(GL.GL_BLEND);
		color_program.Enable();
		GLWrapper.glDrawArrays(GL.GL_LINES, 0, 2);
		color_program.Disable();
		GLWrapper.glDisable(GL.GL_BLEND);
		GLWrapper.glBindVertexArray(0);

		// Delete buffers
		GLWrapper.glDeleteBuffers(1, pos_vbo);
		GLWrapper.glDeleteBuffers(1, color_vbo);
		GLWrapper.glDeleteVertexArrays(1, vao);
	}
	public static void DrawLine3D(Vector line_pos_1, Vector line_pos_2, ColorU8 color) {
		DrawLine3D(line_pos_1, line_pos_2, color, color);
	}

	public static void DrawAxes(float length) {
		DrawLine3D(VectorFunctions.VGet(-length, 0.0f, 0.0f),
				VectorFunctions.VGet(length, 0.0f, 0.0f),
				ColorU8Functions.GetColorU8(1.0f, 0.0f, 0.0f, 1.0f));
		DrawLine3D(VectorFunctions.VGet(0.0f, -length, 0.0f),
				VectorFunctions.VGet(0.0f, length, 0.0f),
				ColorU8Functions.GetColorU8(0.0f, 1.0f, 0.0f, 1.0f));
		DrawLine3D(VectorFunctions.VGet(0.0f, 0.0f, -length),
				VectorFunctions.VGet(0.0f, 0.0f, length),
				ColorU8Functions.GetColorU8(0.0f, 0.0f, 1.0f, 1.0f));
	}
	public static void DrawAxes_Positive(float length) {
		DrawLine3D(VectorFunctions.VGet(0.0f, 0.0f, 0.0f), VectorFunctions.VGet(length, 0.0f, 0.0f),
				ColorU8Functions.GetColorU8(1.0f, 0.0f, 0.0f, 1.0f));
		DrawLine3D(VectorFunctions.VGet(0.0f, 0.0f, 0.0f), VectorFunctions.VGet(0.0f, length, 0.0f),
				ColorU8Functions.GetColorU8(0.0f, 1.0f, 0.0f, 1.0f));
		DrawLine3D(VectorFunctions.VGet(0.0f, 0.0f, 0.0f), VectorFunctions.VGet(0.0f, 0.0f, length),
				ColorU8Functions.GetColorU8(0.0f, 0.0f, 1.0f, 1.0f));
	}
	public static void DrawAxes_Negative(float length) {
		DrawLine3D(VectorFunctions.VGet(-length, 0.0f, 0.0f),
				VectorFunctions.VGet(0.0f, 0.0f, 0.0f),
				ColorU8Functions.GetColorU8(1.0f, 0.0f, 0.0f, 1.0f));
		DrawLine3D(VectorFunctions.VGet(0.0f, -length, 0.0f),
				VectorFunctions.VGet(0.0f, 0.0f, 0.0f),
				ColorU8Functions.GetColorU8(0.0f, 1.0f, 0.0f, 1.0f));
		DrawLine3D(VectorFunctions.VGet(0.0f, 0.0f, -length),
				VectorFunctions.VGet(0.0f, 0.0f, 0.0f),
				ColorU8Functions.GetColorU8(0.0f, 0.0f, 1.0f, 1.0f));
	}

	public static void DrawTriangle3D(Triangle triangle) {
		final IntBuffer pos_vbo = Buffers.newDirectIntBuffer(1);
		final IntBuffer color_vbo = Buffers.newDirectIntBuffer(1);
		final IntBuffer vao = Buffers.newDirectIntBuffer(1);

		final Vertex3D[] vertices = triangle.GetVertices();

		final FloatBuffer pos_buffer = Buffers.newDirectFloatBuffer(9);
		final FloatBuffer color_buffer = Buffers.newDirectFloatBuffer(12);
		for (int i = 0; i < 3; i++) {
			final Vector pos = vertices[i].GetPos();
			final ColorU8 dif = vertices[i].GetDif();

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

		GLWrapper.glGenBuffers(1, pos_vbo);
		GLWrapper.glGenBuffers(1, color_vbo);

		GLWrapper.glBindBuffer(GL.GL_ARRAY_BUFFER, pos_vbo.get(0));
		GLWrapper.glBufferData(GL.GL_ARRAY_BUFFER, Buffers.SIZEOF_FLOAT * pos_buffer.capacity(),
				pos_buffer, GL.GL_STATIC_DRAW);
		GLWrapper.glBindBuffer(GL.GL_ARRAY_BUFFER, color_vbo.get(0));
		GLWrapper.glBufferData(GL.GL_ARRAY_BUFFER, Buffers.SIZEOF_FLOAT * color_buffer.capacity(),
				color_buffer, GL.GL_STATIC_DRAW);

		GLWrapper.glGenVertexArrays(1, vao);
		GLWrapper.glBindVertexArray(vao.get(0));

		// Position attribute
		GLWrapper.glBindBuffer(GL.GL_ARRAY_BUFFER, pos_vbo.get(0));
		GLWrapper.glEnableVertexAttribArray(0);
		GLWrapper.glVertexAttribPointer(0, 3, GL.GL_FLOAT, false, Buffers.SIZEOF_FLOAT * 3, 0);

		// Color attribute
		GLWrapper.glBindBuffer(GL.GL_ARRAY_BUFFER, color_vbo.get(0));
		GLWrapper.glEnableVertexAttribArray(1);
		GLWrapper.glVertexAttribPointer(1, 4, GL.GL_FLOAT, false, Buffers.SIZEOF_FLOAT * 4, 0);

		GLWrapper.glBindBuffer(GL.GL_ARRAY_BUFFER, 0);
		GLWrapper.glBindVertexArray(0);

		// Draw
		GLWrapper.glBindVertexArray(vao.get(0));
		GLWrapper.glEnable(GL.GL_BLEND);
		color_program.Enable();
		GLWrapper.glDrawArrays(GL.GL_LINE_LOOP, 0, 3);
		color_program.Disable();
		GLWrapper.glDisable(GL.GL_BLEND);
		GLWrapper.glBindVertexArray(0);

		// Delete buffers
		GLWrapper.glDeleteBuffers(1, pos_vbo);
		GLWrapper.glDeleteBuffers(1, color_vbo);
		GLWrapper.glDeleteVertexArrays(1, vao);
	}
	public static void DrawTriangle3D(Vector triangle_pos_1, Vector triangle_pos_2,
			Vector triangle_pos_3, ColorU8 color) {
		final Triangle triangle = new Triangle();

		triangle.GetVertex(0).SetPos(triangle_pos_1);
		triangle.GetVertex(1).SetPos(triangle_pos_2);
		triangle.GetVertex(2).SetPos(triangle_pos_3);

		for (int i = 0; i < 3; i++) {
			triangle.GetVertex(i).SetDif(color);
		}

		DrawTriangle3D(triangle);
	}

	public static void DrawQuadrangle3D(Quadrangle quadrangle) {
		final IntBuffer pos_vbo = Buffers.newDirectIntBuffer(1);
		final IntBuffer color_vbo = Buffers.newDirectIntBuffer(1);
		final IntBuffer vao = Buffers.newDirectIntBuffer(1);

		final Vertex3D[] vertices = quadrangle.GetVertices();

		final FloatBuffer pos_buffer = Buffers.newDirectFloatBuffer(12);
		final FloatBuffer color_buffer = Buffers.newDirectFloatBuffer(16);
		for (int i = 0; i < 4; i++) {
			final Vector pos = vertices[i].GetPos();
			final ColorU8 dif = vertices[i].GetDif();

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

		GLWrapper.glGenBuffers(1, pos_vbo);
		GLWrapper.glGenBuffers(1, color_vbo);

		GLWrapper.glBindBuffer(GL.GL_ARRAY_BUFFER, pos_vbo.get(0));
		GLWrapper.glBufferData(GL.GL_ARRAY_BUFFER, Buffers.SIZEOF_FLOAT * pos_buffer.capacity(),
				pos_buffer, GL.GL_STATIC_DRAW);
		GLWrapper.glBindBuffer(GL.GL_ARRAY_BUFFER, color_vbo.get(0));
		GLWrapper.glBufferData(GL.GL_ARRAY_BUFFER, Buffers.SIZEOF_FLOAT * color_buffer.capacity(),
				color_buffer, GL.GL_STATIC_DRAW);

		GLWrapper.glGenVertexArrays(1, vao);
		GLWrapper.glBindVertexArray(vao.get(0));

		// Position attribute
		GLWrapper.glBindBuffer(GL.GL_ARRAY_BUFFER, pos_vbo.get(0));
		GLWrapper.glEnableVertexAttribArray(0);
		GLWrapper.glVertexAttribPointer(0, 3, GL.GL_FLOAT, false, Buffers.SIZEOF_FLOAT * 3, 0);

		// Color attribute
		GLWrapper.glBindBuffer(GL.GL_ARRAY_BUFFER, color_vbo.get(0));
		GLWrapper.glEnableVertexAttribArray(1);
		GLWrapper.glVertexAttribPointer(1, 4, GL.GL_FLOAT, false, Buffers.SIZEOF_FLOAT * 4, 0);

		GLWrapper.glBindBuffer(GL.GL_ARRAY_BUFFER, 0);
		GLWrapper.glBindVertexArray(0);

		// Draw
		GLWrapper.glBindVertexArray(vao.get(0));
		GLWrapper.glEnable(GL.GL_BLEND);
		color_program.Enable();
		GLWrapper.glDrawArrays(GL.GL_LINE_LOOP, 0, 4);
		color_program.Disable();
		GLWrapper.glDisable(GL.GL_BLEND);
		GLWrapper.glBindVertexArray(0);

		// Delete buffers
		GLWrapper.glDeleteBuffers(1, pos_vbo);
		GLWrapper.glDeleteBuffers(1, color_vbo);
		GLWrapper.glDeleteVertexArrays(1, vao);
	}
	public static void DrawQuadrangle3D(Vector quadrangle_pos_1, Vector quadrangle_pos_2,
			Vector quadrangle_pos_3, Vector quadrangle_pos_4, ColorU8 color) {
		final Quadrangle quadrangle = new Quadrangle();

		quadrangle.GetVertex(0).SetPos(quadrangle_pos_1);
		quadrangle.GetVertex(1).SetPos(quadrangle_pos_2);
		quadrangle.GetVertex(2).SetPos(quadrangle_pos_3);
		quadrangle.GetVertex(3).SetPos(quadrangle_pos_4);

		for (int i = 0; i < 4; i++) {
			quadrangle.GetVertex(i).SetDif(color);
		}

		DrawQuadrangle3D(quadrangle);
	}

	public static void DrawSphere3D(Vector center, float radius, int slice_num, int stack_num,
			ColorU8 color) {
		final List<Vector> vertices = new ArrayList<>();
		final List<Integer> indices = new ArrayList<>();

		final int vertex_num = slice_num * (stack_num - 1) + 2;

		// North pole
		vertices.add(VectorFunctions.VGet(0.0f, 1.0f, 0.0f));

		// Middle
		for (int i = 1; i < stack_num; i++) {
			final float ph = (float) Math.PI * i / stack_num;
			final float y = (float) Math.cos(ph);
			final float r = (float) Math.sin(ph);

			for (int j = 0; j < slice_num; j++) {
				final float th = 2.0f * (float) Math.PI * j / slice_num;
				final float x = r * (float) Math.cos(th);
				final float z = r * (float) Math.sin(th);

				vertices.add(VectorFunctions.VGet(x, y, z));
			}
		}

		// South pole
		vertices.add(VectorFunctions.VGet(0.0f, -1.0f, 0.0f));

		for (int i = 0; i < vertex_num; i++) {
			Vector vertex = vertices.get(i);

			vertex = VectorFunctions.VScale(vertex, radius);
			vertex = VectorFunctions.VAdd(vertex, center);

			vertices.set(i, vertex);
		}

		// Ridgelines around the north pole
		for (int i = 1; i <= slice_num; i++) {
			indices.add(0);
			indices.add(i);
		}

		// Ridgelines in the middle
		int count = 1;
		for (int i = 2; i < stack_num; i++) {
			for (int j = 1; j < slice_num; j++) {
				indices.add(count);
				indices.add(count + 1);

				indices.add(count);
				indices.add(count + slice_num);

				count++;
			}

			indices.add(count);
			indices.add(count - slice_num + 1);

			indices.add(count);
			indices.add(count + slice_num);

			count++;
		}

		// Ridgelines in the bottom
		for (int i = 1; i < slice_num; i++) {
			indices.add(count);
			indices.add(count + 1);

			indices.add(count);
			indices.add(vertex_num - 1);

			count++;
		}

		indices.add(count);
		indices.add(count - slice_num + 1);

		indices.add(count);
		indices.add(vertex_num - 1);

		// Make buffers.
		final IntBuffer indices_vbo = Buffers.newDirectIntBuffer(1);
		final IntBuffer pos_vbo = Buffers.newDirectIntBuffer(1);
		final IntBuffer color_vbo = Buffers.newDirectIntBuffer(1);
		final IntBuffer vao = Buffers.newDirectIntBuffer(1);

		final IntBuffer indices_buffer = Buffers.newDirectIntBuffer(indices.size());
		final FloatBuffer pos_buffer = Buffers.newDirectFloatBuffer(vertices.size() * 3);
		final FloatBuffer color_buffer = Buffers.newDirectFloatBuffer(indices.size() * 4);

		final int indices_size = indices.size();
		for (int i = 0; i < indices_size; i++) {
			indices_buffer.put(indices.get(i));
		}
		for (int i = 0; i < vertex_num; i++) {
			final Vector vertex = vertices.get(i);

			pos_buffer.put(vertex.GetX());
			pos_buffer.put(vertex.GetY());
			pos_buffer.put(vertex.GetZ());
		}

		final float color_r = color.GetR();
		final float color_g = color.GetG();
		final float color_b = color.GetB();
		final float color_a = color.GetA();
		for (int i = 0; i < indices_size; i++) {
			color_buffer.put(color_r);
			color_buffer.put(color_g);
			color_buffer.put(color_b);
			color_buffer.put(color_a);
		}

		indices_buffer.flip();
		pos_buffer.flip();
		color_buffer.flip();

		GLWrapper.glGenBuffers(1, indices_vbo);
		GLWrapper.glGenBuffers(1, pos_vbo);
		GLWrapper.glGenBuffers(1, color_vbo);

		GLWrapper.glBindBuffer(GL.GL_ARRAY_BUFFER, pos_vbo.get(0));
		GLWrapper.glBufferData(GL.GL_ARRAY_BUFFER, Buffers.SIZEOF_FLOAT * pos_buffer.capacity(),
				pos_buffer, GL.GL_STATIC_DRAW);
		GLWrapper.glBindBuffer(GL.GL_ARRAY_BUFFER, color_vbo.get(0));
		GLWrapper.glBufferData(GL.GL_ARRAY_BUFFER, Buffers.SIZEOF_FLOAT * color_buffer.capacity(),
				color_buffer, GL.GL_STATIC_DRAW);

		GLWrapper.glGenVertexArrays(1, vao);
		GLWrapper.glBindVertexArray(vao.get(0));

		// Indices
		GLWrapper.glBindBuffer(GL.GL_ELEMENT_ARRAY_BUFFER, indices_vbo.get(0));
		GLWrapper.glBufferData(GL.GL_ELEMENT_ARRAY_BUFFER,
				Buffers.SIZEOF_INT * indices_buffer.capacity(), indices_buffer, GL.GL_STATIC_DRAW);

		// Position attribute
		GLWrapper.glBindBuffer(GL.GL_ARRAY_BUFFER, pos_vbo.get(0));
		GLWrapper.glEnableVertexAttribArray(0);
		GLWrapper.glVertexAttribPointer(0, 3, GL.GL_FLOAT, false, Buffers.SIZEOF_FLOAT * 3, 0);

		// Color attribute
		GLWrapper.glBindBuffer(GL.GL_ARRAY_BUFFER, color_vbo.get(0));
		GLWrapper.glEnableVertexAttribArray(1);
		GLWrapper.glVertexAttribPointer(1, 4, GL.GL_FLOAT, false, Buffers.SIZEOF_FLOAT * 4, 0);

		GLWrapper.glEnable(GL.GL_BLEND);
		color_program.Enable();
		GLWrapper.glDrawElements(GL.GL_LINES, indices_size, GL.GL_UNSIGNED_INT, 0);
		color_program.Disable();
		GLWrapper.glDisable(GL.GL_BLEND);

		GLWrapper.glBindBuffer(GL.GL_ARRAY_BUFFER, 0);
		GLWrapper.glBindVertexArray(0);

		GLWrapper.glDeleteBuffers(1, indices_vbo);
		GLWrapper.glDeleteBuffers(1, pos_vbo);
		GLWrapper.glDeleteBuffers(1, color_vbo);
		GLWrapper.glDeleteVertexArrays(1, vao);
	}

	public static void DrawCapsule3D(Vector capsule_pos_1, Vector capsule_pos_2, float radius,
			int slice_num, int stack_num, ColorU8 color) {
		final Vector capsule_axis = VectorFunctions.VSub(capsule_pos_2, capsule_pos_1);
		final float d = VectorFunctions.VSize(capsule_axis);
		final float half_d = d / 2.0f;

		final float th_v = VectorFunctions.VAngleV(capsule_axis);
		final float th_h = VectorFunctions.VAngleH(capsule_axis);

		Vector center_pos = VectorFunctions.VAdd(capsule_pos_1, capsule_pos_2);
		center_pos = VectorFunctions.VScale(center_pos, 0.5f);

		final List<Vector> vertices = new ArrayList<>();
		final List<Integer> indices = new ArrayList<>();

		final int vertex_num = slice_num * (stack_num - 1) + 2;

		// North pole
		vertices.add(VectorFunctions.VGet(0.0f, radius + half_d, 0.0f));

		// Middle
		for (int i = 1; i < stack_num / 2; i++) {
			final float ph = (float) Math.PI * i / stack_num;
			final float y = radius * (float) Math.cos(ph) + half_d;
			final float r = radius * (float) Math.sin(ph);

			for (int j = 0; j < slice_num; j++) {
				final float th = 2.0f * (float) Math.PI * j / slice_num;
				final float x = r * (float) Math.cos(th);
				final float z = r * (float) Math.sin(th);

				vertices.add(VectorFunctions.VGet(x, y, z));
			}
		}
		for (int i = stack_num / 2; i < stack_num; i++) {
			final float ph = (float) Math.PI * i / stack_num;
			final float y = radius * (float) Math.cos(ph) - half_d;
			final float r = radius * (float) Math.sin(ph);

			for (int j = 0; j < slice_num; j++) {
				final float th = 2.0f * (float) Math.PI * j / slice_num;
				final float x = r * (float) Math.cos(th);
				final float z = r * (float) Math.sin(th);

				vertices.add(VectorFunctions.VGet(x, y, z));
			}
		}

		// South pole
		vertices.add(VectorFunctions.VGet(0.0f, -radius - half_d, 0.0f));

		final Matrix rot_z = MatrixFunctions.MGetRotZ(th_v - (float) Math.PI / 2.0f);
		final Matrix rot_y = MatrixFunctions.MGetRotY(-th_h);

		for (int i = 0; i < vertex_num; i++) {
			Vector vertex = vertices.get(i);

			vertex = MatrixFunctions.VTransform(vertex, rot_z);
			vertex = MatrixFunctions.VTransform(vertex, rot_y);
			vertex = VectorFunctions.VAdd(vertex, center_pos);

			vertices.set(i, vertex);
		}

		// Ridgelines around the north pole
		for (int i = 1; i <= slice_num; i++) {
			indices.add(0);
			indices.add(i);
		}

		// Ridgelines in the middle
		int count = 1;
		for (int i = 2; i < stack_num; i++) {
			for (int j = 1; j < slice_num; j++) {
				indices.add(count);
				indices.add(count + 1);

				indices.add(count);
				indices.add(count + slice_num);

				count++;
			}

			indices.add(count);
			indices.add(count - slice_num + 1);

			indices.add(count);
			indices.add(count + slice_num);

			count++;
		}

		// Ridgelines in the bottom
		for (int i = 1; i < slice_num; i++) {
			indices.add(count);
			indices.add(count + 1);

			indices.add(count);
			indices.add(vertex_num - 1);

			count++;
		}

		indices.add(count);
		indices.add(count - slice_num + 1);

		indices.add(count);
		indices.add(vertex_num - 1);

		// Make buffers.
		final IntBuffer indices_vbo = Buffers.newDirectIntBuffer(1);
		final IntBuffer pos_vbo = Buffers.newDirectIntBuffer(1);
		final IntBuffer color_vbo = Buffers.newDirectIntBuffer(1);
		final IntBuffer vao = Buffers.newDirectIntBuffer(1);

		final IntBuffer indices_buffer = Buffers.newDirectIntBuffer(indices.size());
		final FloatBuffer pos_buffer = Buffers.newDirectFloatBuffer(vertices.size() * 3);
		final FloatBuffer color_buffer = Buffers.newDirectFloatBuffer(indices.size() * 4);

		final int indices_size = indices.size();
		for (int i = 0; i < indices_size; i++) {
			indices_buffer.put(indices.get(i));
		}
		for (int i = 0; i < vertex_num; i++) {
			final Vector vertex = vertices.get(i);

			pos_buffer.put(vertex.GetX());
			pos_buffer.put(vertex.GetY());
			pos_buffer.put(vertex.GetZ());
		}

		final float color_r = color.GetR();
		final float color_g = color.GetG();
		final float color_b = color.GetB();
		final float color_a = color.GetA();
		for (int i = 0; i < indices_size; i++) {
			color_buffer.put(color_r);
			color_buffer.put(color_g);
			color_buffer.put(color_b);
			color_buffer.put(color_a);
		}

		indices_buffer.flip();
		pos_buffer.flip();
		color_buffer.flip();

		GLWrapper.glGenBuffers(1, indices_vbo);
		GLWrapper.glGenBuffers(1, pos_vbo);
		GLWrapper.glGenBuffers(1, color_vbo);

		GLWrapper.glBindBuffer(GL.GL_ARRAY_BUFFER, pos_vbo.get(0));
		GLWrapper.glBufferData(GL.GL_ARRAY_BUFFER, Buffers.SIZEOF_FLOAT * pos_buffer.capacity(),
				pos_buffer, GL.GL_STATIC_DRAW);
		GLWrapper.glBindBuffer(GL.GL_ARRAY_BUFFER, color_vbo.get(0));
		GLWrapper.glBufferData(GL.GL_ARRAY_BUFFER, Buffers.SIZEOF_FLOAT * color_buffer.capacity(),
				color_buffer, GL.GL_STATIC_DRAW);

		GLWrapper.glGenVertexArrays(1, vao);
		GLWrapper.glBindVertexArray(vao.get(0));

		// Indices
		GLWrapper.glBindBuffer(GL.GL_ELEMENT_ARRAY_BUFFER, indices_vbo.get(0));
		GLWrapper.glBufferData(GL.GL_ELEMENT_ARRAY_BUFFER,
				Buffers.SIZEOF_INT * indices_buffer.capacity(), indices_buffer, GL.GL_STATIC_DRAW);

		// Position attribute
		GLWrapper.glBindBuffer(GL.GL_ARRAY_BUFFER, pos_vbo.get(0));
		GLWrapper.glEnableVertexAttribArray(0);
		GLWrapper.glVertexAttribPointer(0, 3, GL.GL_FLOAT, false, Buffers.SIZEOF_FLOAT * 3, 0);

		// Color attribute
		GLWrapper.glBindBuffer(GL.GL_ARRAY_BUFFER, color_vbo.get(0));
		GLWrapper.glEnableVertexAttribArray(1);
		GLWrapper.glVertexAttribPointer(1, 4, GL.GL_FLOAT, false, Buffers.SIZEOF_FLOAT * 4, 0);

		GLWrapper.glEnable(GL.GL_BLEND);
		color_program.Enable();
		GLWrapper.glDrawElements(GL.GL_LINES, indices_size, GL.GL_UNSIGNED_INT, 0);
		color_program.Disable();
		GLWrapper.glDisable(GL.GL_BLEND);

		GLWrapper.glBindBuffer(GL.GL_ARRAY_BUFFER, 0);
		GLWrapper.glBindVertexArray(0);

		GLWrapper.glDeleteBuffers(1, indices_vbo);
		GLWrapper.glDeleteBuffers(1, pos_vbo);
		GLWrapper.glDeleteBuffers(1, color_vbo);
		GLWrapper.glDeleteVertexArrays(1, vao);
	}

	public static void DrawTexturedTriangle3D(Triangle triangle, int texture_handle,
			boolean use_face_normal_flag) {
		final IntBuffer pos_vbo = Buffers.newDirectIntBuffer(1);
		final IntBuffer uv_vbo = Buffers.newDirectIntBuffer(1);
		final IntBuffer norm_vbo = Buffers.newDirectIntBuffer(1);
		final IntBuffer vao = Buffers.newDirectIntBuffer(1);

		final Vertex3D[] vertices = triangle.GetVertices();
		final FloatBuffer pos_buffer = Buffers.newDirectFloatBuffer(9);
		final FloatBuffer uv_buffer = Buffers.newDirectFloatBuffer(6);
		final FloatBuffer norm_buffer = Buffers.newDirectFloatBuffer(9);

		// Calculate the face normal.
		final Vector v1 = VectorFunctions.VSub(vertices[1].GetPos(), vertices[0].GetPos());
		final Vector v2 = VectorFunctions.VSub(vertices[2].GetPos(), vertices[0].GetPos());
		Vector face_norm = VectorFunctions.VCross(v1, v2);
		face_norm = VectorFunctions.VNorm(face_norm);

		for (int i = 0; i < 3; i++) {
			final Vector pos = vertices[i].GetPos();
			final float u = vertices[i].GetU();
			final float v = vertices[i].GetV();

			pos_buffer.put(pos.GetX());
			pos_buffer.put(pos.GetY());
			pos_buffer.put(pos.GetZ());
			uv_buffer.put(u);
			uv_buffer.put(v);

			if (use_face_normal_flag == true) {
				norm_buffer.put(face_norm.GetX());
				norm_buffer.put(face_norm.GetY());
				norm_buffer.put(face_norm.GetZ());
			} else {
				final Vector norm = vertices[i].GetNorm();

				norm_buffer.put(norm.GetX());
				norm_buffer.put(norm.GetY());
				norm_buffer.put(norm.GetZ());
			}
		}
		pos_buffer.flip();
		uv_buffer.flip();
		norm_buffer.flip();

		GLWrapper.glGenBuffers(1, pos_vbo);
		GLWrapper.glGenBuffers(1, uv_vbo);
		GLWrapper.glGenBuffers(1, norm_vbo);

		GLWrapper.glBindBuffer(GL.GL_ARRAY_BUFFER, pos_vbo.get(0));
		GLWrapper.glBufferData(GL.GL_ARRAY_BUFFER, Buffers.SIZEOF_FLOAT * pos_buffer.capacity(),
				pos_buffer, GL.GL_STATIC_DRAW);
		GLWrapper.glBindBuffer(GL.GL_ARRAY_BUFFER, uv_vbo.get(0));
		GLWrapper.glBufferData(GL.GL_ARRAY_BUFFER, Buffers.SIZEOF_FLOAT * uv_buffer.capacity(),
				uv_buffer, GL.GL_STATIC_DRAW);
		GLWrapper.glBindBuffer(GL.GL_ARRAY_BUFFER, norm_vbo.get(0));
		GLWrapper.glBufferData(GL.GL_ARRAY_BUFFER, Buffers.SIZEOF_FLOAT * norm_buffer.capacity(),
				norm_buffer, GL.GL_STATIC_DRAW);

		GLWrapper.glGenVertexArrays(1, vao);
		GLWrapper.glBindVertexArray(vao.get(0));

		// Position attribute
		GLWrapper.glBindBuffer(GL.GL_ARRAY_BUFFER, pos_vbo.get(0));
		GLWrapper.glEnableVertexAttribArray(0);
		GLWrapper.glVertexAttribPointer(0, 3, GL.GL_FLOAT, false, Buffers.SIZEOF_FLOAT * 3, 0);

		// UV attribute
		GLWrapper.glBindBuffer(GL.GL_ARRAY_BUFFER, uv_vbo.get(0));
		GLWrapper.glEnableVertexAttribArray(1);
		GLWrapper.glVertexAttribPointer(1, 2, GL.GL_FLOAT, false, Buffers.SIZEOF_FLOAT * 2, 0);

		// Normal attribute
		GLWrapper.glBindBuffer(GL.GL_ARRAY_BUFFER, norm_vbo.get(0));
		GLWrapper.glEnableVertexAttribArray(2);
		GLWrapper.glVertexAttribPointer(2, 3, GL.GL_FLOAT, false, Buffers.SIZEOF_FLOAT * 3, 0);

		GLWrapper.glBindBuffer(GL.GL_ARRAY_BUFFER, 0);
		GLWrapper.glBindVertexArray(0);

		GLWrapper.glBindVertexArray(vao.get(0));

		// Draw
		texture_program.Enable();
		texture_program.SetTexture("texture_sampler", 0, texture_handle);
		GLWrapper.glEnable(GL.GL_BLEND);
		GLWrapper.glDrawArrays(GL.GL_TRIANGLES, 0, 3);
		GLWrapper.glDisable(GL.GL_BLEND);
		texture_program.Disable();

		GLWrapper.glBindVertexArray(0);

		// Delete buffers
		GLWrapper.glDeleteBuffers(1, pos_vbo);
		GLWrapper.glDeleteBuffers(1, uv_vbo);
		GLWrapper.glDeleteBuffers(1, norm_vbo);
		GLWrapper.glDeleteVertexArrays(1, vao);
	}

	public static void DrawTexturedQuadrangle(Quadrangle quadrangle, int texture_handle,
			boolean use_face_normal_flag) {
		final IntBuffer indices_vbo = Buffers.newDirectIntBuffer(1);
		final IntBuffer pos_vbo = Buffers.newDirectIntBuffer(1);
		final IntBuffer uv_vbo = Buffers.newDirectIntBuffer(1);
		final IntBuffer norm_vbo = Buffers.newDirectIntBuffer(1);
		final IntBuffer vao = Buffers.newDirectIntBuffer(1);

		final Vertex3D[] vertices = quadrangle.GetVertices();
		final IntBuffer indices_buffer = Buffers.newDirectIntBuffer(6);
		final FloatBuffer pos_buffer = Buffers.newDirectFloatBuffer(12);
		final FloatBuffer uv_buffer = Buffers.newDirectFloatBuffer(8);
		final FloatBuffer norm_buffer = Buffers.newDirectFloatBuffer(12);

		indices_buffer.put(0);
		indices_buffer.put(1);
		indices_buffer.put(2);
		indices_buffer.put(2);
		indices_buffer.put(3);
		indices_buffer.put(0);
		indices_buffer.flip();

		// Calculate the face normal.
		final Vector v1 = VectorFunctions.VSub(vertices[1].GetPos(), vertices[0].GetPos());
		final Vector v2 = VectorFunctions.VSub(vertices[3].GetPos(), vertices[0].GetPos());
		Vector face_norm = VectorFunctions.VCross(v1, v2);
		face_norm = VectorFunctions.VNorm(face_norm);

		for (int i = 0; i < 4; i++) {
			final Vector pos = vertices[i].GetPos();
			final float u = vertices[i].GetU();
			final float v = vertices[i].GetV();

			pos_buffer.put(pos.GetX());
			pos_buffer.put(pos.GetY());
			pos_buffer.put(pos.GetZ());
			uv_buffer.put(u);
			uv_buffer.put(v);

			if (use_face_normal_flag == true) {
				norm_buffer.put(face_norm.GetX());
				norm_buffer.put(face_norm.GetY());
				norm_buffer.put(face_norm.GetZ());
			} else {
				final Vector norm = vertices[i].GetNorm();

				norm_buffer.put(norm.GetX());
				norm_buffer.put(norm.GetY());
				norm_buffer.put(norm.GetZ());
			}
		}
		pos_buffer.flip();
		uv_buffer.flip();
		norm_buffer.flip();

		GLWrapper.glGenBuffers(1, indices_vbo);
		GLWrapper.glGenBuffers(1, pos_vbo);
		GLWrapper.glGenBuffers(1, uv_vbo);
		GLWrapper.glGenBuffers(1, norm_vbo);

		GLWrapper.glBindBuffer(GL.GL_ARRAY_BUFFER, pos_vbo.get(0));
		GLWrapper.glBufferData(GL.GL_ARRAY_BUFFER, Buffers.SIZEOF_FLOAT * pos_buffer.capacity(),
				pos_buffer, GL.GL_STATIC_DRAW);
		GLWrapper.glBindBuffer(GL.GL_ARRAY_BUFFER, uv_vbo.get(0));
		GLWrapper.glBufferData(GL.GL_ARRAY_BUFFER, Buffers.SIZEOF_FLOAT * uv_buffer.capacity(),
				uv_buffer, GL.GL_STATIC_DRAW);
		GLWrapper.glBindBuffer(GL.GL_ARRAY_BUFFER, norm_vbo.get(0));
		GLWrapper.glBufferData(GL.GL_ARRAY_BUFFER, Buffers.SIZEOF_FLOAT * norm_buffer.capacity(),
				norm_buffer, GL.GL_STATIC_DRAW);

		GLWrapper.glGenVertexArrays(1, vao);
		GLWrapper.glBindVertexArray(vao.get(0));

		// Indices
		GLWrapper.glBindBuffer(GL.GL_ELEMENT_ARRAY_BUFFER, indices_vbo.get(0));
		GLWrapper.glBufferData(GL.GL_ELEMENT_ARRAY_BUFFER,
				Buffers.SIZEOF_INT * indices_buffer.capacity(), indices_buffer, GL.GL_STATIC_DRAW);

		// Position attribute
		GLWrapper.glBindBuffer(GL.GL_ARRAY_BUFFER, pos_vbo.get(0));
		GLWrapper.glEnableVertexAttribArray(0);
		GLWrapper.glVertexAttribPointer(0, 3, GL.GL_FLOAT, false, Buffers.SIZEOF_FLOAT * 3, 0);

		// UV attribute
		GLWrapper.glBindBuffer(GL.GL_ARRAY_BUFFER, uv_vbo.get(0));
		GLWrapper.glEnableVertexAttribArray(1);
		GLWrapper.glVertexAttribPointer(1, 2, GL.GL_FLOAT, false, Buffers.SIZEOF_FLOAT * 2, 0);

		// Normal attribute
		GLWrapper.glBindBuffer(GL.GL_ARRAY_BUFFER, norm_vbo.get(0));
		GLWrapper.glEnableVertexAttribArray(2);
		GLWrapper.glVertexAttribPointer(2, 3, GL.GL_FLOAT, false, Buffers.SIZEOF_FLOAT * 3, 0);

		GLWrapper.glBindBuffer(GL.GL_ARRAY_BUFFER, 0);
		GLWrapper.glBindVertexArray(0);

		GLWrapper.glBindVertexArray(vao.get(0));

		// Draw
		texture_program.Enable();
		texture_program.SetTexture("texture_sampler", 0, texture_handle);
		GLWrapper.glEnable(GL.GL_BLEND);
		GLWrapper.glDrawElements(GL.GL_TRIANGLES, 6, GL.GL_UNSIGNED_INT, 0);
		GLWrapper.glDisable(GL.GL_BLEND);
		texture_program.Disable();

		GLWrapper.glBindVertexArray(0);

		// Delete buffers
		GLWrapper.glDeleteBuffers(1, indices_vbo);
		GLWrapper.glDeleteBuffers(1, pos_vbo);
		GLWrapper.glDeleteBuffers(1, uv_vbo);
		GLWrapper.glDeleteBuffers(1, norm_vbo);
		GLWrapper.glDeleteVertexArrays(1, vao);
	}
}
