package com.github.dabasan.joglf.gl.model.buffer;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import com.github.dabasan.basis.coloru8.ColorU8;
import com.github.dabasan.basis.coloru8.ColorU8Functions;
import com.github.dabasan.joglf.gl.tool.BufferFunctions;
import com.jogamp.common.nio.Buffers;

/**
 * Set of buffers for shaders.
 * 
 * @author Daba
 *
 */
public class BufferedVertices {
	private int texture_handle;
	private int indices_count;

	private IntBuffer indices_buffer;
	private FloatBuffer pos_buffer;
	private FloatBuffer uv_buffer;
	private FloatBuffer norm_buffer;

	private ColorU8 ambient_color;
	private ColorU8 diffuse_color;
	private ColorU8 specular_color;
	private float specular_exponent;
	private float dissolve;
	private String diffuse_texture_map;

	public BufferedVertices Copy() {
		final BufferedVertices buffered_vertices = new BufferedVertices();

		buffered_vertices.texture_handle = this.texture_handle;
		buffered_vertices.indices_count = this.indices_count;

		buffered_vertices.indices_buffer = BufferFunctions.CopyIntBuffer(this.indices_buffer);
		buffered_vertices.pos_buffer = BufferFunctions.CopyFloatBuffer(this.pos_buffer);
		buffered_vertices.uv_buffer = BufferFunctions.CopyFloatBuffer(this.uv_buffer);
		buffered_vertices.norm_buffer = BufferFunctions.CopyFloatBuffer(this.norm_buffer);

		buffered_vertices.ambient_color = this.GetAmbientColor();
		buffered_vertices.diffuse_color = this.GetDiffuseColor();
		buffered_vertices.specular_color = this.GetSpecularColor();
		buffered_vertices.specular_exponent = this.GetSpecularExponent();
		buffered_vertices.dissolve = this.GetDissolve();
		buffered_vertices.diffuse_texture_map = this.GetDiffuseTextureMap();

		return buffered_vertices;
	}

	public BufferedVertices() {
		texture_handle = -1;
		indices_count = 0;

		ambient_color = ColorU8Functions.GetColorU8(1.0f, 1.0f, 1.0f, 1.0f);
		diffuse_color = ColorU8Functions.GetColorU8(1.0f, 1.0f, 1.0f, 1.0f);
		specular_color = ColorU8Functions.GetColorU8(0.0f, 0.0f, 0.0f, 0.0f);
		specular_exponent = 10.0f;
		dissolve = 1.0f;
		diffuse_texture_map = "";
	}

	public static BufferedVertices Interpolate(BufferedVertices bv1, BufferedVertices bv2,
			float blend_ratio) {
		final BufferedVertices interpolated = new BufferedVertices();
		interpolated.SetAmbientColor(bv1.GetAmbientColor());
		interpolated.SetDiffuseColor(bv1.GetDiffuseColor());
		interpolated.SetSpecularColor(bv1.GetSpecularColor());
		interpolated.SetSpecularExponent(bv1.GetSpecularExponent());
		interpolated.SetDissolve(bv1.GetDissolve());
		interpolated.SetDiffuseTextureMap(bv1.GetDiffuseTextureMap());

		final int texture_handle = bv1.GetTextureHandle();
		final IntBuffer indices_buffer = bv1.GetIndicesBuffer();
		final FloatBuffer uv_buffer = bv1.GetUVBuffer();

		// Interpolate positions and normals.
		final FloatBuffer pos_buffer_1 = bv1.GetPosBuffer();
		final FloatBuffer pos_buffer_2 = bv2.GetPosBuffer();
		final FloatBuffer norm_buffer_1 = bv1.GetNormBuffer();
		final FloatBuffer norm_buffer_2 = bv2.GetNormBuffer();

		final int pos_buffer_cap = pos_buffer_1.capacity();
		final int norm_buffer_cap = norm_buffer_1.capacity();

		final FloatBuffer interpolated_pos_buffer = Buffers.newDirectFloatBuffer(pos_buffer_cap);
		final FloatBuffer interpolated_norm_buffer = Buffers.newDirectFloatBuffer(norm_buffer_cap);

		float ftemp;
		for (int i = 0; i < pos_buffer_cap; i++) {
			ftemp = pos_buffer_1.get(i) * (1.0f - blend_ratio) + pos_buffer_2.get(i) * blend_ratio;
			interpolated_pos_buffer.put(ftemp);
		}
		for (int i = 0; i < norm_buffer_cap; i++) {
			ftemp = norm_buffer_1.get(i) * (1.0f - blend_ratio)
					+ norm_buffer_2.get(i) * blend_ratio;
			interpolated_norm_buffer.put(ftemp);
		}
		interpolated_pos_buffer.flip();
		interpolated_norm_buffer.flip();

		interpolated.SetTextureHandle(texture_handle);
		interpolated.SetIndicesBuffer(indices_buffer);
		interpolated.SetUVBuffer(uv_buffer);
		interpolated.SetPosBuffer(interpolated_pos_buffer);
		interpolated.SetNormBuffer(interpolated_norm_buffer);

		return interpolated;
	}

	public void SetTextureHandle(int texture_handle) {
		this.texture_handle = texture_handle;
	}
	public void SetIndicesBuffer(IntBuffer indices_buffer) {
		this.indices_buffer = indices_buffer;
		indices_count = indices_buffer.capacity();
	}
	public void SetPosBuffer(FloatBuffer pos_buffer) {
		this.pos_buffer = pos_buffer;
	}
	public void SetUVBuffer(FloatBuffer uv_buffer) {
		this.uv_buffer = uv_buffer;
	}
	public void SetNormBuffer(FloatBuffer norm_buffer) {
		this.norm_buffer = norm_buffer;
	}
	public void SetAmbientColor(ColorU8 ambient_color) {
		this.ambient_color = ambient_color;
	}
	public void SetDiffuseColor(ColorU8 diffuse_color) {
		this.diffuse_color = diffuse_color;
	}
	public void SetSpecularColor(ColorU8 specular_color) {
		this.specular_color = specular_color;
	}
	public void SetSpecularExponent(float specular_exponent) {
		this.specular_exponent = specular_exponent;
	}
	public void SetDissolve(float dissolve) {
		this.dissolve = dissolve;
	}
	public void SetDiffuseTextureMap(String diffuse_texture_map) {
		this.diffuse_texture_map = diffuse_texture_map;
	}

	public int GetTextureHandle() {
		return texture_handle;
	}
	public int GetIndicesCount() {
		return indices_count;
	}
	public IntBuffer GetIndicesBuffer() {
		return indices_buffer;
	}
	public FloatBuffer GetPosBuffer() {
		return pos_buffer;
	}
	public FloatBuffer GetUVBuffer() {
		return uv_buffer;
	}
	public FloatBuffer GetNormBuffer() {
		return norm_buffer;
	}
	public ColorU8 GetAmbientColor() {
		return new ColorU8(ambient_color);
	}
	public ColorU8 GetDiffuseColor() {
		return new ColorU8(diffuse_color);
	}
	public ColorU8 GetSpecularColor() {
		return new ColorU8(specular_color);
	}
	public float GetSpecularExponent() {
		return specular_exponent;
	}
	public float GetDissolve() {
		return dissolve;
	}
	public String GetDiffuseTextureMap() {
		return diffuse_texture_map;
	}
}
