package com.github.dabasan.joglf.gl.model;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.dabasan.basis.matrix.Matrix;
import com.github.dabasan.basis.matrix.MatrixFunctions;
import com.github.dabasan.basis.vector.Vector;
import com.github.dabasan.basis.vector.VectorFunctions;
import com.github.dabasan.joglf.gl.model.buffer.BufferedVertices;
import com.github.dabasan.joglf.gl.shader.ShaderProgram;
import com.github.dabasan.joglf.gl.shape.Triangle;
import com.github.dabasan.joglf.gl.texture.TextureMgr;
import com.github.dabasan.joglf.gl.wrapper.GLWrapper;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL;

/**
 * Model manager
 * 
 * @author Daba
 *
 */
public class ModelMgr {
	private final Logger logger = LoggerFactory.getLogger(ModelMgr.class);

	private List<BufferedVertices> buffered_vertices_list;

	private boolean property_updated_flag;

	private IntBuffer indices_vbo;
	private IntBuffer pos_vbo;
	private IntBuffer uv_vbo;
	private IntBuffer norm_vbo;
	private IntBuffer vao;

	private final List<ShaderProgram> programs;

	public ModelMgr(List<BufferedVertices> buffered_vertices_list, FlipVOption option) {
		this.buffered_vertices_list = buffered_vertices_list;

		property_updated_flag = false;

		programs = new ArrayList<>();
		final ShaderProgram program = new ShaderProgram("texture");
		programs.add(program);

		this.GenerateBuffers(option);
	}
	public ModelMgr(List<BufferedVertices> buffered_vertices_list) {
		this(buffered_vertices_list, FlipVOption.MUST_FLIP_VERTICALLY);
	}

	public void AddProgram(ShaderProgram program) {
		programs.add(program);
	}
	public void RemoveAllPrograms() {
		programs.clear();
	}

	public void Interpolate(ModelMgr frame1, ModelMgr frame2, float blend_ratio) {
		final List<BufferedVertices> interpolated_bv_list = new ArrayList<>();
		final List<BufferedVertices> frame1_bv_list = frame1.buffered_vertices_list;
		final List<BufferedVertices> frame2_bv_list = frame2.buffered_vertices_list;

		final int element_num = frame1_bv_list.size();
		for (int i = 0; i < element_num; i++) {
			final BufferedVertices frame1_bv = frame1_bv_list.get(i);
			final BufferedVertices frame2_bv = frame2_bv_list.get(i);

			final BufferedVertices interpolated_bv = BufferedVertices.Interpolate(frame1_bv,
					frame2_bv, blend_ratio);
			interpolated_bv_list.add(interpolated_bv);
		}

		this.buffered_vertices_list = interpolated_bv_list;
	}

	public ModelMgr Copy() {
		final List<BufferedVertices> copied_buffered_vertices_list = new ArrayList<>();

		for (final BufferedVertices buffered_vertices : buffered_vertices_list) {
			final BufferedVertices copied_buffered_vertices = buffered_vertices.Copy();
			copied_buffered_vertices_list.add(copied_buffered_vertices);
		}

		final ModelMgr copied_model = new ModelMgr(copied_buffered_vertices_list);

		return copied_model;
	}

	private void GenerateBuffers(FlipVOption option) {
		final int element_num = buffered_vertices_list.size();
		if (element_num == 0) {
			logger.warn("This model does not contain any elements.");
			return;
		}

		indices_vbo = Buffers.newDirectIntBuffer(element_num);
		pos_vbo = Buffers.newDirectIntBuffer(element_num);
		uv_vbo = Buffers.newDirectIntBuffer(element_num);
		norm_vbo = Buffers.newDirectIntBuffer(element_num);
		vao = Buffers.newDirectIntBuffer(element_num);

		GLWrapper.glGenBuffers(element_num, indices_vbo);
		GLWrapper.glGenBuffers(element_num, pos_vbo);
		GLWrapper.glGenBuffers(element_num, uv_vbo);
		GLWrapper.glGenBuffers(element_num, norm_vbo);
		GLWrapper.glGenVertexArrays(element_num, vao);

		for (int i = 0; i < element_num; i++) {
			final BufferedVertices buffered_vertices = buffered_vertices_list.get(i);

			final FloatBuffer pos_buffer = buffered_vertices.GetPosBuffer();
			final FloatBuffer uv_buffer = buffered_vertices.GetUVBuffer();
			final FloatBuffer norm_buffer = buffered_vertices.GetNormBuffer();

			if (option == FlipVOption.MUST_FLIP_VERTICALLY) {
				final int texture_handle = buffered_vertices.GetTextureHandle();
				final boolean texture_exists = TextureMgr.TextureExists(texture_handle);
				if (texture_exists == true) {
					final boolean must_flip_vertically = TextureMgr
							.GetMustFlipVertically(texture_handle);
					if (must_flip_vertically == true) {
						final int cap = uv_buffer.capacity();

						for (int j = 0; j < cap; j += 2) {
							float v = uv_buffer.get(j + 1);
							v *= (-1.0f);
							uv_buffer.put(j + 1, v);
						}
					}
				}
			} else if (option == FlipVOption.ALL) {
				final int cap = uv_buffer.capacity();

				for (int j = 0; j < cap; j += 2) {
					float v = uv_buffer.get(j + 1);
					v *= (-1.0f);
					uv_buffer.put(j + 1, v);
				}
			}

			GLWrapper.glBindBuffer(GL.GL_ARRAY_BUFFER, pos_vbo.get(i));
			GLWrapper.glBufferData(GL.GL_ARRAY_BUFFER, Buffers.SIZEOF_FLOAT * pos_buffer.capacity(),
					pos_buffer, GL.GL_DYNAMIC_DRAW);
			GLWrapper.glBindBuffer(GL.GL_ARRAY_BUFFER, uv_vbo.get(i));
			GLWrapper.glBufferData(GL.GL_ARRAY_BUFFER, Buffers.SIZEOF_FLOAT * uv_buffer.capacity(),
					uv_buffer, GL.GL_STATIC_DRAW);
			GLWrapper.glBindBuffer(GL.GL_ARRAY_BUFFER, norm_vbo.get(i));
			GLWrapper.glBufferData(GL.GL_ARRAY_BUFFER,
					Buffers.SIZEOF_FLOAT * norm_buffer.capacity(), norm_buffer, GL.GL_DYNAMIC_DRAW);
			GLWrapper.glBindBuffer(GL.GL_ARRAY_BUFFER, 0);
		}
		for (int i = 0; i < element_num; i++) {
			final BufferedVertices buffered_vertices = buffered_vertices_list.get(i);
			final IntBuffer indices_buffer = buffered_vertices.GetIndicesBuffer();

			GLWrapper.glBindVertexArray(vao.get(i));

			GLWrapper.glBindBuffer(GL.GL_ELEMENT_ARRAY_BUFFER, indices_vbo.get(i));
			GLWrapper.glBufferData(GL.GL_ELEMENT_ARRAY_BUFFER,
					Buffers.SIZEOF_INT * indices_buffer.capacity(), indices_buffer,
					GL.GL_STATIC_DRAW);

			GLWrapper.glBindBuffer(GL.GL_ARRAY_BUFFER, pos_vbo.get(i));
			GLWrapper.glEnableVertexAttribArray(0);
			GLWrapper.glVertexAttribPointer(0, 3, GL.GL_FLOAT, false, Buffers.SIZEOF_FLOAT * 3, 0);

			GLWrapper.glBindBuffer(GL.GL_ARRAY_BUFFER, uv_vbo.get(i));
			GLWrapper.glEnableVertexAttribArray(1);
			GLWrapper.glVertexAttribPointer(1, 2, GL.GL_FLOAT, false, Buffers.SIZEOF_FLOAT * 2, 0);

			GLWrapper.glBindBuffer(GL.GL_ARRAY_BUFFER, norm_vbo.get(i));
			GLWrapper.glEnableVertexAttribArray(2);
			GLWrapper.glVertexAttribPointer(2, 3, GL.GL_FLOAT, false, Buffers.SIZEOF_FLOAT * 3, 0);

			GLWrapper.glBindBuffer(GL.GL_ARRAY_BUFFER, 0);
			GLWrapper.glBindVertexArray(0);
		}
	}
	private void UpdateBuffers() {
		final int element_num = buffered_vertices_list.size();

		for (int i = 0; i < element_num; i++) {
			final BufferedVertices buffered_vertices = buffered_vertices_list.get(i);

			final FloatBuffer pos_buffer = buffered_vertices.GetPosBuffer();
			final FloatBuffer norm_buffer = buffered_vertices.GetNormBuffer();

			GLWrapper.glBindVertexArray(vao.get(i));

			GLWrapper.glBindBuffer(GL.GL_ARRAY_BUFFER, pos_vbo.get(i));
			GLWrapper.glBufferData(GL.GL_ARRAY_BUFFER, Buffers.SIZEOF_FLOAT * pos_buffer.capacity(),
					pos_buffer, GL.GL_DYNAMIC_DRAW);
			GLWrapper.glBindBuffer(GL.GL_ARRAY_BUFFER, norm_vbo.get(i));
			GLWrapper.glBufferData(GL.GL_ARRAY_BUFFER,
					Buffers.SIZEOF_FLOAT * norm_buffer.capacity(), norm_buffer, GL.GL_DYNAMIC_DRAW);

			GLWrapper.glBindBuffer(GL.GL_ARRAY_BUFFER, 0);
			GLWrapper.glBindVertexArray(0);
		}

		property_updated_flag = false;
	}

	public void DeleteBuffers() {
		final int element_num = buffered_vertices_list.size();

		GLWrapper.glDeleteBuffers(element_num, indices_vbo);
		GLWrapper.glDeleteBuffers(element_num, pos_vbo);
		GLWrapper.glDeleteBuffers(element_num, uv_vbo);
		GLWrapper.glDeleteBuffers(element_num, norm_vbo);
		GLWrapper.glDeleteVertexArrays(element_num, vao);

		for (final BufferedVertices buffered_vertices : buffered_vertices_list) {
			final int texture_handle = buffered_vertices.GetTextureHandle();
			TextureMgr.DeleteTexture(texture_handle);
		}
	}

	public void DrawWithProgram(ShaderProgram program, String sampler_name, int texture_unit) {
		if (property_updated_flag == true) {
			this.UpdateBuffers();
		}

		final int element_num = buffered_vertices_list.size();

		program.Enable();

		for (int i = 0; i < element_num; i++) {
			final BufferedVertices buffered_vertices = buffered_vertices_list.get(i);
			final int texture_handle = buffered_vertices.GetTextureHandle();
			final int indices_count = buffered_vertices.GetIndicesCount();

			GLWrapper.glBindVertexArray(vao.get(i));

			program.SetTexture(sampler_name, texture_unit, texture_handle);

			GLWrapper.glEnable(GL.GL_BLEND);
			GLWrapper.glDrawElements(GL.GL_TRIANGLES, indices_count, GL.GL_UNSIGNED_INT, 0);
			GLWrapper.glDisable(GL.GL_BLEND);

			GLWrapper.glBindVertexArray(0);
		}

		program.Disable();
	}
	public void Draw(String sampler_name, int texture_unit) {
		for (final ShaderProgram program : programs) {
			this.DrawWithProgram(program, sampler_name, texture_unit);
		}
	}
	public void Draw() {
		this.Draw("texture_sampler", 0);
	}

	public void Transfer() {
		if (property_updated_flag == true) {
			this.UpdateBuffers();
		}

		final int element_num = buffered_vertices_list.size();

		for (int i = 0; i < element_num; i++) {
			final BufferedVertices buffered_vertices = buffered_vertices_list.get(i);
			final int indices_count = buffered_vertices.GetIndicesCount();

			GLWrapper.glBindVertexArray(vao.get(i));

			GLWrapper.glEnable(GL.GL_BLEND);
			GLWrapper.glDrawElements(GL.GL_TRIANGLES, indices_count, GL.GL_UNSIGNED_INT, 0);
			GLWrapper.glDisable(GL.GL_BLEND);

			GLWrapper.glBindVertexArray(0);
		}
	}

	public void DrawElements(String sampler_name, int texture_unit, int bound) {
		if (property_updated_flag == true) {
			this.UpdateBuffers();
		}

		final int element_num = buffered_vertices_list.size();

		int clamped_bound;
		if (bound < 0) {
			clamped_bound = 0;
		} else if (bound < element_num) {
			clamped_bound = bound;
		} else {
			clamped_bound = element_num;
		}

		for (final ShaderProgram program : programs) {
			program.Enable();

			for (int i = 0; i < clamped_bound; i++) {
				final BufferedVertices buffered_vertices = buffered_vertices_list.get(i);
				final int texture_handle = buffered_vertices.GetTextureHandle();
				final int indices_count = buffered_vertices.GetIndicesCount();

				GLWrapper.glBindVertexArray(vao.get(i));

				program.SetTexture(sampler_name, texture_unit, texture_handle);

				GLWrapper.glEnable(GL.GL_BLEND);
				GLWrapper.glDrawElements(GL.GL_TRIANGLES, indices_count, GL.GL_UNSIGNED_INT, 0);
				GLWrapper.glDisable(GL.GL_BLEND);

				GLWrapper.glBindVertexArray(0);
			}

			program.Disable();
		}
	}
	public void DrawElements(int bound) {
		this.DrawElements("texture_sampler", 0, bound);
	}

	public int GetElementNum() {
		return buffered_vertices_list.size();
	}

	public void SetMatrix(Matrix m) {
		for (final BufferedVertices buffered_vertices : buffered_vertices_list) {
			final FloatBuffer pos_buffer = buffered_vertices.GetPosBuffer();
			final FloatBuffer norm_buffer = buffered_vertices.GetNormBuffer();

			final int capacity = pos_buffer.capacity();
			for (int i = 0; i < capacity; i += 3) {
				// pos_buffer
				Vector pos = new Vector();
				pos.SetX(pos_buffer.get(i));
				pos.SetY(pos_buffer.get(i + 1));
				pos.SetZ(pos_buffer.get(i + 2));

				pos = MatrixFunctions.VTransform(pos, m);

				pos_buffer.put(i, pos.GetX());
				pos_buffer.put(i + 1, pos.GetY());
				pos_buffer.put(i + 2, pos.GetZ());

				// norm_buffer
				Vector norm = new Vector();
				norm.SetX(norm_buffer.get(i));
				norm.SetY(norm_buffer.get(i + 1));
				norm.SetZ(norm_buffer.get(i + 2));

				norm = MatrixFunctions.VTransformSR(norm, m);
				norm = VectorFunctions.VNorm(norm);

				norm_buffer.put(i, norm.GetX());
				norm_buffer.put(i + 1, norm.GetY());
				norm_buffer.put(i + 2, norm.GetZ());
			}

			buffered_vertices.SetPosBuffer(pos_buffer);
			buffered_vertices.SetNormBuffer(norm_buffer);
		}

		property_updated_flag = true;
	}

	public int ChangeTexture(int material_index, int new_texture_handle) {
		if (!(0 <= material_index && material_index < buffered_vertices_list.size())) {
			logger.warn("Index out of bounds. material_index={}", material_index);
			return -1;
		}

		final BufferedVertices buffered_vertices = buffered_vertices_list.get(material_index);
		buffered_vertices.SetTextureHandle(new_texture_handle);

		return 0;
	}

	public int[] GetTextureHandles() {
		final int element_num = buffered_vertices_list.size();
		final int[] texture_handles = new int[element_num];

		for (int i = 0; i < element_num; i++) {
			texture_handles[i] = buffered_vertices_list.get(i).GetTextureHandle();
		}

		return texture_handles;
	}

	public List<Triangle> GetFaces() {
		final List<Triangle> ret = new ArrayList<>();

		for (final BufferedVertices buffered_vertices : buffered_vertices_list) {
			final IntBuffer indices_buffer = buffered_vertices.GetIndicesBuffer();
			final FloatBuffer pos_buffer = buffered_vertices.GetPosBuffer();
			final FloatBuffer norm_buffer = buffered_vertices.GetNormBuffer();
			final FloatBuffer uv_buffer = buffered_vertices.GetUVBuffer();

			final int indices_count = indices_buffer.capacity();
			final int triangle_num = indices_count / 3;

			for (int i = 0; i < triangle_num; i++) {
				final Triangle triangle = new Triangle();

				for (int j = 0; j < 3; j++) {
					final int index = indices_buffer.get(i * 3 + j);

					final int vec_base_index = index * 3;
					final int uv_base_index = index * 2;

					// pos_buffer
					final Vector pos = new Vector();
					pos.SetX(pos_buffer.get(vec_base_index));
					pos.SetY(pos_buffer.get(vec_base_index + 1));
					pos.SetZ(pos_buffer.get(vec_base_index + 2));

					// norm_buffer
					final Vector norm = new Vector();
					norm.SetX(norm_buffer.get(vec_base_index));
					norm.SetY(norm_buffer.get(vec_base_index + 1));
					norm.SetZ(norm_buffer.get(vec_base_index + 2));

					// uv buffer
					final float u = uv_buffer.get(uv_base_index);
					final float v = uv_buffer.get(uv_base_index + 1);

					triangle.GetVertex(j).SetPos(pos);
					triangle.GetVertex(j).SetNorm(norm);
					triangle.GetVertex(j).SetU(u);
					triangle.GetVertex(j).SetV(v);
				}

				ret.add(triangle);
			}
		}

		return ret;
	}
}
