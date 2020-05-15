package com.github.dabasan.joglf.gl.drawer;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.dabasan.basis.coloru8.ColorU8;
import com.github.dabasan.basis.vector.Vector;
import com.github.dabasan.basis.vector.VectorFunctions;
import com.github.dabasan.joglf.gl.shader.ShaderProgram;
import com.github.dabasan.joglf.gl.shape.Sphere;
import com.github.dabasan.joglf.gl.wrapper.GLWrapper;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL;

/**
 * Draws spheres.
 * 
 * @author Daba
 *
 */
public class DynamicSpheresDrawer extends Dynamic3DDrawer {
	private final Logger logger = LoggerFactory.getLogger(DynamicSpheresDrawer.class);

	private final Map<Integer, Sphere> spheres_map;
	private final Map<Integer, Integer> indices_sizes_map;

	private int buffer_num;

	private IntBuffer indices_vbo;
	private IntBuffer pos_vbo;
	private IntBuffer dif_vbo;
	private IntBuffer vao;

	public DynamicSpheresDrawer() {
		spheres_map = new TreeMap<>();
		indices_sizes_map = new HashMap<>();

		buffer_num = 0;
	}

	@Override
	public void SetDefaultProgram() {
		final ShaderProgram program = new ShaderProgram("color");
		this.AddProgram(program);
	}

	@Override
	public void UpdateBuffers() {
		final int sphere_num = spheres_map.size();

		if (buffer_num != 0) {
			this.DeleteBuffers();
		}

		indices_vbo = Buffers.newDirectIntBuffer(sphere_num);
		pos_vbo = Buffers.newDirectIntBuffer(sphere_num);
		dif_vbo = Buffers.newDirectIntBuffer(sphere_num);
		vao = Buffers.newDirectIntBuffer(sphere_num);

		GLWrapper.glGenBuffers(sphere_num, indices_vbo);
		GLWrapper.glGenBuffers(sphere_num, pos_vbo);
		GLWrapper.glGenBuffers(sphere_num, dif_vbo);
		GLWrapper.glGenVertexArrays(sphere_num, vao);

		buffer_num = sphere_num;

		int buffer_count = 0;
		for (final Sphere sphere : spheres_map.values()) {
			final Vector center = sphere.GetCenter();
			final float radius = sphere.GetRadius();
			final int slice_num = sphere.GetSliceNum();
			final int stack_num = sphere.GetStackNum();
			final ColorU8 color = sphere.GetColor();

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

			final IntBuffer indices_buffer = Buffers.newDirectIntBuffer(indices.size());
			final FloatBuffer pos_buffer = Buffers.newDirectFloatBuffer(vertices.size() * 3);
			final FloatBuffer dif_buffer = Buffers.newDirectFloatBuffer(indices.size() * 4);

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
				dif_buffer.put(color_r);
				dif_buffer.put(color_g);
				dif_buffer.put(color_b);
				dif_buffer.put(color_a);
			}

			indices_buffer.flip();
			pos_buffer.flip();
			dif_buffer.flip();

			GLWrapper.glBindBuffer(GL.GL_ARRAY_BUFFER, pos_vbo.get(buffer_count));
			GLWrapper.glBufferData(GL.GL_ARRAY_BUFFER, Buffers.SIZEOF_FLOAT * pos_buffer.capacity(),
					pos_buffer, GL.GL_DYNAMIC_DRAW);
			GLWrapper.glBindBuffer(GL.GL_ARRAY_BUFFER, dif_vbo.get(buffer_count));
			GLWrapper.glBufferData(GL.GL_ARRAY_BUFFER, Buffers.SIZEOF_FLOAT * dif_buffer.capacity(),
					dif_buffer, GL.GL_DYNAMIC_DRAW);

			GLWrapper.glBindVertexArray(vao.get(buffer_count));

			// Indices
			GLWrapper.glBindBuffer(GL.GL_ELEMENT_ARRAY_BUFFER, indices_vbo.get(buffer_count));
			GLWrapper.glBufferData(GL.GL_ELEMENT_ARRAY_BUFFER,
					Buffers.SIZEOF_INT * indices_buffer.capacity(), indices_buffer,
					GL.GL_DYNAMIC_DRAW);

			// Position attribute
			GLWrapper.glBindBuffer(GL.GL_ARRAY_BUFFER, pos_vbo.get(buffer_count));
			GLWrapper.glEnableVertexAttribArray(0);
			GLWrapper.glVertexAttribPointer(0, 3, GL.GL_FLOAT, false, Buffers.SIZEOF_FLOAT * 3, 0);

			// Color attribute
			GLWrapper.glBindBuffer(GL.GL_ARRAY_BUFFER, dif_vbo.get(buffer_count));
			GLWrapper.glEnableVertexAttribArray(1);
			GLWrapper.glVertexAttribPointer(1, 4, GL.GL_FLOAT, false, Buffers.SIZEOF_FLOAT * 4, 0);

			GLWrapper.glBindBuffer(GL.GL_ARRAY_BUFFER, 0);
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

		buffer_num = 0;
	}

	public void AddSphere(int sphere_id, Sphere sphere) {
		spheres_map.put(sphere_id, sphere);
	}
	public int DeleteSphere(int sphere_id) {
		if (spheres_map.containsKey(sphere_id) == false) {
			logger.warn("No such sphere. sphere_id={}", sphere_id);
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
		final List<ShaderProgram> programs = this.GetPrograms();

		for (final ShaderProgram program : programs) {
			program.Enable();

			for (int i = 0; i < buffer_num; i++) {
				GLWrapper.glBindVertexArray(vao.get(i));

				final int indices_size = indices_sizes_map.get(i);
				GLWrapper.glEnable(GL.GL_BLEND);
				GLWrapper.glDrawElements(GL.GL_LINES, indices_size, GL.GL_UNSIGNED_INT, 0);
				GLWrapper.glDisable(GL.GL_BLEND);

				GLWrapper.glBindVertexArray(0);
			}

			program.Disable();
		}
	}
}
