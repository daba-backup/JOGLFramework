package com.github.dabasan.joglf.gl.drawer;

import java.awt.Point;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.dabasan.basis.coloru8.ColorU8;
import com.github.dabasan.joglf.gl.shader.ShaderProgram;
import com.github.dabasan.joglf.gl.shape.Vertex2D;
import com.github.dabasan.joglf.gl.tool.CoordinateFunctions;
import com.github.dabasan.joglf.gl.wrapper.GLWrapper;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL;

/**
 * Draws filled 2D triangles.
 * 
 * @author Daba
 *
 */
public class Dynamic2DFilledTrianglesDrawer extends Dynamic2DDrawer {
	private final Logger logger = LoggerFactory.getLogger(Dynamic2DFilledTrianglesDrawer.class);

	private final Map<Integer, Vertex2D[]> triangles_map;

	private final IntBuffer pos_vbo;
	private final IntBuffer dif_vbo;
	private final IntBuffer vao;

	public Dynamic2DFilledTrianglesDrawer() {
		triangles_map = new TreeMap<>();

		pos_vbo = Buffers.newDirectIntBuffer(1);
		dif_vbo = Buffers.newDirectIntBuffer(1);
		vao = Buffers.newDirectIntBuffer(1);

		GLWrapper.glGenBuffers(1, pos_vbo);
		GLWrapper.glGenBuffers(1, dif_vbo);
		GLWrapper.glGenVertexArrays(1, vao);
	}

	@Override
	public void SetDefaultProgram() {
		final ShaderProgram program = new ShaderProgram("simple_2d");
		this.AddProgram(program);
	}

	@Override
	public void UpdateBuffers() {
		final int triangle_num = triangles_map.size();
		final int point_num = triangle_num * 3;

		final FloatBuffer pos_buffer = Buffers.newDirectFloatBuffer(point_num * 2);
		final FloatBuffer dif_buffer = Buffers.newDirectFloatBuffer(point_num * 4);

		final int window_width = this.GetWindowWidth();
		final int window_height = this.GetWindowHeight();

		for (final Vertex2D[] triangle : triangles_map.values()) {
			for (int i = 0; i < 3; i++) {
				final Point point = triangle[i].GetPoint();
				final ColorU8 dif = triangle[i].GetColor();

				final int x = point.x;
				final int y = point.y;
				final float normalized_x = CoordinateFunctions.NormalizeCoordinate(x, window_width);
				final float normalized_y = CoordinateFunctions.NormalizeCoordinate(y,
						window_height);

				pos_buffer.put(normalized_x);
				pos_buffer.put(normalized_y);
				dif_buffer.put(dif.GetR());
				dif_buffer.put(dif.GetG());
				dif_buffer.put(dif.GetB());
				dif_buffer.put(dif.GetA());
			}
		}

		pos_buffer.flip();
		dif_buffer.flip();

		GLWrapper.glBindBuffer(GL.GL_ARRAY_BUFFER, pos_vbo.get(0));
		GLWrapper.glBufferData(GL.GL_ARRAY_BUFFER, Buffers.SIZEOF_FLOAT * pos_buffer.capacity(),
				pos_buffer, GL.GL_DYNAMIC_DRAW);
		GLWrapper.glBindBuffer(GL.GL_ARRAY_BUFFER, dif_vbo.get(0));
		GLWrapper.glBufferData(GL.GL_ARRAY_BUFFER, Buffers.SIZEOF_FLOAT * dif_buffer.capacity(),
				dif_buffer, GL.GL_DYNAMIC_DRAW);

		GLWrapper.glBindBuffer(GL.GL_ARRAY_BUFFER, 0);

		GLWrapper.glBindVertexArray(vao.get(0));

		GLWrapper.glBindBuffer(GL.GL_ARRAY_BUFFER, pos_vbo.get(0));
		GLWrapper.glEnableVertexAttribArray(0);
		GLWrapper.glVertexAttribPointer(0, 2, GL.GL_FLOAT, false, Buffers.SIZEOF_FLOAT * 2, 0);

		GLWrapper.glBindBuffer(GL.GL_ARRAY_BUFFER, dif_vbo.get(0));
		GLWrapper.glEnableVertexAttribArray(1);
		GLWrapper.glVertexAttribPointer(1, 4, GL.GL_FLOAT, false, Buffers.SIZEOF_FLOAT * 4, 0);

		GLWrapper.glBindBuffer(GL.GL_ARRAY_BUFFER, 0);
		GLWrapper.glBindVertexArray(0);
	}
	@Override
	public void DeleteBuffers() {
		GLWrapper.glDeleteBuffers(1, pos_vbo);
		GLWrapper.glDeleteBuffers(1, dif_vbo);
		GLWrapper.glDeleteVertexArrays(1, vao);
	}

	public void AddTriangle(int triangle_id, Vertex2D v1, Vertex2D v2, Vertex2D v3) {
		final Vertex2D[] vertices = new Vertex2D[3];
		vertices[0] = v1;
		vertices[1] = v2;
		vertices[2] = v3;

		triangles_map.put(triangle_id, vertices);
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

	public Vertex2D[] GetTriangle(int triangle_id) {
		return triangles_map.get(triangle_id);
	}

	@Override
	public void Draw() {
		final List<ShaderProgram> programs = this.GetPrograms();

		for (final ShaderProgram program : programs) {
			program.Enable();

			GLWrapper.glBindVertexArray(vao.get(0));

			final int triangle_num = triangles_map.size();
			final int point_num = triangle_num * 3;

			GLWrapper.glEnable(GL.GL_BLEND);
			GLWrapper.glDrawArrays(GL.GL_TRIANGLES, 0, point_num);
			GLWrapper.glDisable(GL.GL_BLEND);

			GLWrapper.glBindVertexArray(0);

			program.Disable();
		}
	}
}
