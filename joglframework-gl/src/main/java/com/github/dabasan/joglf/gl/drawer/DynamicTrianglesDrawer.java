package com.github.dabasan.joglf.gl.drawer;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.dabasan.basis.vector.Vector;
import com.github.dabasan.joglf.gl.shader.ShaderProgram;
import com.github.dabasan.joglf.gl.shape.Triangle;
import com.github.dabasan.joglf.gl.shape.Vertex3D;
import com.github.dabasan.joglf.gl.texture.TextureMgr;
import com.github.dabasan.joglf.gl.wrapper.GLWrapper;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL;

/**
 * Draws textured triangles.
 * 
 * @author Daba
 *
 */
public class DynamicTrianglesDrawer extends Dynamic3DDrawer {
	private final Logger logger = LoggerFactory.getLogger(DynamicTrianglesDrawer.class);

	private int texture_handle;
	private final Map<Integer, Triangle> triangles_map;

	private final IntBuffer pos_vbo;
	private final IntBuffer uv_vbo;
	private final IntBuffer norm_vbo;
	private final IntBuffer vao;

	public DynamicTrianglesDrawer() {
		texture_handle = -1;
		triangles_map = new TreeMap<>();

		pos_vbo = Buffers.newDirectIntBuffer(1);
		uv_vbo = Buffers.newDirectIntBuffer(1);
		norm_vbo = Buffers.newDirectIntBuffer(1);
		vao = Buffers.newDirectIntBuffer(1);

		GLWrapper.glGenBuffers(1, pos_vbo);
		GLWrapper.glGenBuffers(1, uv_vbo);
		GLWrapper.glGenBuffers(1, norm_vbo);
		GLWrapper.glGenVertexArrays(1, vao);
	}

	@Override
	public void SetDefaultProgram() {
		final ShaderProgram program = new ShaderProgram("texture");
		this.AddProgram(program);
	}

	@Override
	public void UpdateBuffers() {
		final int triangle_num = triangles_map.size();
		final int vertex_num = triangle_num * 3;

		final FloatBuffer pos_buffer = Buffers.newDirectFloatBuffer(vertex_num * 3);
		final FloatBuffer uv_buffer = Buffers.newDirectFloatBuffer(vertex_num * 2);
		final FloatBuffer norm_buffer = Buffers.newDirectFloatBuffer(vertex_num * 3);

		for (final Triangle triangle : triangles_map.values()) {
			final Vertex3D[] vertices = triangle.GetVertices();

			for (int i = 0; i < 3; i++) {
				final Vector position = vertices[i].GetPos();
				final float u = vertices[i].GetU();
				final float v = vertices[i].GetV();
				final Vector normal = vertices[i].GetNorm();

				pos_buffer.put(position.GetX());
				pos_buffer.put(position.GetY());
				pos_buffer.put(position.GetZ());
				uv_buffer.put(u);
				uv_buffer.put(v);
				norm_buffer.put(normal.GetX());
				norm_buffer.put(normal.GetY());
				norm_buffer.put(normal.GetZ());
			}
		}
		pos_buffer.flip();
		uv_buffer.flip();
		norm_buffer.flip();

		GLWrapper.glBindBuffer(GL.GL_ARRAY_BUFFER, pos_vbo.get(0));
		GLWrapper.glBufferData(GL.GL_ARRAY_BUFFER, Buffers.SIZEOF_FLOAT * pos_buffer.capacity(),
				pos_buffer, GL.GL_DYNAMIC_DRAW);
		GLWrapper.glBindBuffer(GL.GL_ARRAY_BUFFER, uv_vbo.get(0));
		GLWrapper.glBufferData(GL.GL_ARRAY_BUFFER, Buffers.SIZEOF_FLOAT * uv_buffer.capacity(),
				uv_buffer, GL.GL_DYNAMIC_DRAW);
		GLWrapper.glBindBuffer(GL.GL_ARRAY_BUFFER, norm_vbo.get(0));
		GLWrapper.glBufferData(GL.GL_ARRAY_BUFFER, Buffers.SIZEOF_FLOAT * norm_buffer.capacity(),
				norm_buffer, GL.GL_DYNAMIC_DRAW);

		GLWrapper.glBindBuffer(GL.GL_ARRAY_BUFFER, 0);

		GLWrapper.glBindVertexArray(vao.get(0));

		GLWrapper.glBindBuffer(GL.GL_ARRAY_BUFFER, pos_vbo.get(0));
		GLWrapper.glEnableVertexAttribArray(0);
		GLWrapper.glVertexAttribPointer(0, 3, GL.GL_FLOAT, false, Buffers.SIZEOF_FLOAT * 3, 0);

		GLWrapper.glBindBuffer(GL.GL_ARRAY_BUFFER, uv_vbo.get(0));
		GLWrapper.glEnableVertexAttribArray(1);
		GLWrapper.glVertexAttribPointer(1, 2, GL.GL_FLOAT, false, Buffers.SIZEOF_FLOAT * 2, 0);

		GLWrapper.glBindBuffer(GL.GL_ARRAY_BUFFER, norm_vbo.get(0));
		GLWrapper.glEnableVertexAttribArray(2);
		GLWrapper.glVertexAttribPointer(2, 3, GL.GL_FLOAT, false, Buffers.SIZEOF_FLOAT * 3, 0);

		GLWrapper.glBindBuffer(GL.GL_ARRAY_BUFFER, 0);
		GLWrapper.glBindVertexArray(0);
	}
	@Override
	public void DeleteBuffers() {
		GLWrapper.glDeleteBuffers(1, pos_vbo);
		GLWrapper.glDeleteBuffers(1, uv_vbo);
		GLWrapper.glDeleteBuffers(1, norm_vbo);
		GLWrapper.glDeleteVertexArrays(1, vao);
	}

	public void SetTextureHandle(int texture_handle) {
		this.texture_handle = texture_handle;
	}
	public void AddTriangle(int triangle_id, Triangle triangle) {
		triangles_map.put(triangle_id, triangle);
	}
	public int DeleteTriangle(int triangle_id) {
		if (triangles_map.containsKey(triangle_id) == false) {
			logger.warn("No such triangle. triangle_id={}", triangle_id);
			return -1;
		}

		triangles_map.remove(triangle_id);

		return 0;
	}
	public void DeleteAllTriangles() {
		triangles_map.clear();
	}

	public Triangle GetTriangle(int triangle_id) {
		return triangles_map.get(triangle_id);
	}

	@Override
	public void Draw() {
		this.Draw(0, "texture_sampler");
	}
	public void Draw(int texture_unit, String sampler_name) {
		final List<ShaderProgram> programs = this.GetPrograms();

		for (final ShaderProgram program : programs) {
			program.Enable();

			GLWrapper.glBindVertexArray(vao.get(0));

			program.SetTexture(sampler_name, texture_unit, texture_handle);

			final int triangle_num = triangles_map.size();
			final int vertex_num = triangle_num * 3;

			GLWrapper.glEnable(GL.GL_BLEND);
			GLWrapper.glDrawArrays(GL.GL_TRIANGLES, 0, vertex_num);
			GLWrapper.glDisable(GL.GL_BLEND);

			TextureMgr.UnbindTexture();

			GLWrapper.glBindVertexArray(0);

			program.Disable();
		}
	}
	public void Transfer() {
		GLWrapper.glBindVertexArray(vao.get(0));

		final int triangle_num = triangles_map.size();
		final int vertex_num = triangle_num * 3;

		GLWrapper.glEnable(GL.GL_BLEND);
		GLWrapper.glDrawArrays(GL.GL_TRIANGLES, 0, vertex_num);
		GLWrapper.glDisable(GL.GL_BLEND);

		GLWrapper.glBindVertexArray(0);
	}
}
