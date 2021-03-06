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
import com.github.dabasan.joglf.gl.shape.Quadrangle;
import com.github.dabasan.joglf.gl.shape.Vertex3D;
import com.github.dabasan.joglf.gl.texture.TextureMgr;
import com.github.dabasan.joglf.gl.wrapper.GLWrapper;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL;

/**
 * Draws quadrangles.
 * 
 * @author Daba
 *
 */
public class DynamicQuadranglesDrawer extends Dynamic3DDrawer {
	private final Logger logger = LoggerFactory.getLogger(DynamicQuadranglesDrawer.class);

	private int texture_handle;
	private final Map<Integer, Quadrangle> quadrangles_map;

	private final IntBuffer indices_vbo;
	private final IntBuffer pos_vbo;
	private final IntBuffer uv_vbo;
	private final IntBuffer norm_vbo;
	private final IntBuffer vao;

	public DynamicQuadranglesDrawer() {
		texture_handle = -1;
		quadrangles_map = new TreeMap<>();

		indices_vbo = Buffers.newDirectIntBuffer(1);
		pos_vbo = Buffers.newDirectIntBuffer(1);
		uv_vbo = Buffers.newDirectIntBuffer(1);
		norm_vbo = Buffers.newDirectIntBuffer(1);
		vao = Buffers.newDirectIntBuffer(1);

		GLWrapper.glGenBuffers(1, indices_vbo);
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
		final int quadrangle_num = quadrangles_map.size();
		final int triangle_num = quadrangle_num * 2;
		final int vertex_num = quadrangle_num * 4;

		final IntBuffer indices_buffer = Buffers.newDirectIntBuffer(triangle_num * 3);
		final FloatBuffer pos_buffer = Buffers.newDirectFloatBuffer(vertex_num * 3);
		final FloatBuffer uv_buffer = Buffers.newDirectFloatBuffer(vertex_num * 2);
		final FloatBuffer norm_buffer = Buffers.newDirectFloatBuffer(vertex_num * 3);

		int count = 0;
		for (final Quadrangle quadrangle : quadrangles_map.values()) {
			final Vertex3D[] vertices = quadrangle.GetVertices();

			for (int i = 0; i < 4; i++) {
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

			indices_buffer.put(count);
			indices_buffer.put(count + 1);
			indices_buffer.put(count + 2);

			indices_buffer.put(count + 2);
			indices_buffer.put(count + 3);
			indices_buffer.put(count);

			count += 4;
		}
		indices_buffer.flip();
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

		GLWrapper.glBindBuffer(GL.GL_ELEMENT_ARRAY_BUFFER, indices_vbo.get(0));
		GLWrapper.glBufferData(GL.GL_ELEMENT_ARRAY_BUFFER,
				Buffers.SIZEOF_INT * indices_buffer.capacity(), indices_buffer, GL.GL_DYNAMIC_DRAW);

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
		GLWrapper.glDeleteBuffers(1, indices_vbo);
		GLWrapper.glDeleteBuffers(1, pos_vbo);
		GLWrapper.glDeleteBuffers(1, uv_vbo);
		GLWrapper.glDeleteBuffers(1, norm_vbo);
		GLWrapper.glDeleteVertexArrays(1, vao);
	}

	public void SetTextureHandle(int texture_handle) {
		this.texture_handle = texture_handle;
	}
	public void AddQuadrangle(int quadrangle_id, Quadrangle quadrangle) {
		quadrangles_map.put(quadrangle_id, quadrangle);
	}
	public int DeleteQuadrangle(int quadrangle_id) {
		if (quadrangles_map.containsKey(quadrangle_id) == false) {
			logger.warn("No such quadrangle. quadrangle_id={}", quadrangle_id);
			return -1;
		}

		quadrangles_map.remove(quadrangle_id);

		return 0;
	}
	public void DeleteAllQuadrangles() {
		quadrangles_map.clear();
	}

	public Quadrangle GetQuadrangle(int quadrangle_id) {
		return quadrangles_map.get(quadrangle_id);
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

			final int quadrangle_num = quadrangles_map.size();
			final int triangle_num = quadrangle_num * 2;
			final int indices_size = triangle_num * 3;

			GLWrapper.glEnable(GL.GL_BLEND);
			GLWrapper.glDrawElements(GL.GL_TRIANGLES, indices_size, GL.GL_UNSIGNED_INT, 0);
			GLWrapper.glDisable(GL.GL_BLEND);

			TextureMgr.UnbindTexture();

			GLWrapper.glBindVertexArray(0);

			program.Disable();
		}
	}
	public void Transfer() {
		GLWrapper.glBindVertexArray(vao.get(0));

		final int quadrangle_num = quadrangles_map.size();
		final int triangle_num = quadrangle_num * 2;
		final int indices_size = triangle_num * 3;

		GLWrapper.glEnable(GL.GL_BLEND);
		GLWrapper.glDrawElements(GL.GL_TRIANGLES, indices_size, GL.GL_UNSIGNED_INT, 0);
		GLWrapper.glDisable(GL.GL_BLEND);

		GLWrapper.glBindVertexArray(0);
	}
}
