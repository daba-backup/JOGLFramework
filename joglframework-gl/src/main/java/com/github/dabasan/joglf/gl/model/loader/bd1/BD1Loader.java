package com.github.dabasan.joglf.gl.model.loader.bd1;

import java.io.IOException;
import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.dabasan.basis.vector.Vector;
import com.github.dabasan.joglf.gl.model.buffer.BufferedVertices;
import com.github.dabasan.joglf.gl.shape.Triangle;
import com.github.dabasan.joglf.gl.shape.Vertex3D;
import com.github.dabasan.joglf.gl.texture.TextureMgr;
import com.github.dabasan.tool.FilenameFunctions;
import com.github.dabasan.xops.bd1.BD1Block;
import com.github.dabasan.xops.bd1.BD1Manipulator;
import com.jogamp.common.nio.Buffers;

/**
 * BD1 loader
 * 
 * @author Daba
 *
 */
public class BD1Loader {
	private static Logger logger = LoggerFactory.getLogger(BD1Loader.class);

	public static List<BufferedVertices> LoadBD1(String bd1_filename) {
		final List<BufferedVertices> ret = new ArrayList<>();

		BD1Manipulator manipulator;
		try {
			manipulator = new BD1Manipulator(bd1_filename);
		} catch (final IOException e) {
			logger.error("Error while reading.", e);
			return ret;
		}

		final String bd1_directory = FilenameFunctions
				.GetFileDirectory(bd1_filename);

		final Map<Integer, String> texture_filenames = manipulator
				.GetTextureFilenames();
		final Map<Integer, Integer> texture_handles_map = new HashMap<>();
		for (final Map.Entry<Integer, String> entry : texture_filenames
				.entrySet()) {
			final int texture_id = entry.getKey();

			String texture_filename = entry.getValue();
			if (texture_filename.equals("")) {
				texture_handles_map.put(texture_id, -1);
				continue;
			}

			texture_filename = bd1_directory + "/" + texture_filename;

			final int texture_handle = TextureMgr.LoadTexture(texture_filename);
			texture_handles_map.put(texture_id, texture_handle);
		}

		manipulator.InvertZ();
		final List<BD1Block> blocks = manipulator.GetBlocks();

		final BD1Triangulator triangulator = new BD1Triangulator();
		triangulator.TriangulateBlocks(blocks);

		final Map<Integer, List<BD1Triangle>> triangles_map = triangulator
				.GetTrianglesMap();
		for (final Map.Entry<Integer, List<BD1Triangle>> entry : triangles_map
				.entrySet()) {
			final int texture_id = entry.getKey();
			final List<BD1Triangle> triangles = entry.getValue();

			int texture_handle;
			if (texture_handles_map.containsKey(texture_id) == true) {
				texture_handle = texture_handles_map.get(texture_id);
			} else {
				texture_handle = -1;
			}

			final int triangle_num = triangles.size();
			final int vertex_num = triangle_num * 3;

			final BufferedVertices buffered_vertices = new BufferedVertices();
			final IntBuffer indices = Buffers.newDirectIntBuffer(vertex_num);
			final FloatBuffer pos_buffer = Buffers
					.newDirectFloatBuffer(vertex_num * 3);
			final FloatBuffer uv_buffer = Buffers
					.newDirectFloatBuffer(vertex_num * 2);
			final FloatBuffer norm_buffer = Buffers
					.newDirectFloatBuffer(vertex_num * 3);

			for (int i = 0; i < triangle_num; i++) {
				final Triangle triangle = triangles.get(i);

				for (int j = 0; j < 3; j++) {
					final Vertex3D vertex = triangle.GetVertex(j);

					final Vector position = vertex.GetPos();
					final float u = vertex.GetU();
					final float v = vertex.GetV();
					final Vector normal = vertex.GetNorm();

					indices.put(i * 3 + j);
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

			((Buffer) indices).flip();
			((Buffer) pos_buffer).flip();
			((Buffer) uv_buffer).flip();
			((Buffer) norm_buffer).flip();

			buffered_vertices.SetIndicesBuffer(indices);
			buffered_vertices.SetPosBuffer(pos_buffer);
			buffered_vertices.SetUVBuffer(uv_buffer);
			buffered_vertices.SetNormBuffer(norm_buffer);
			buffered_vertices.SetTextureHandle(texture_handle);

			ret.add(buffered_vertices);
		}

		return ret;
	}
	public static List<BufferedVertices> LoadBD1_KeepOrder(
			String bd1_filename) {
		final List<BufferedVertices> ret = new ArrayList<>();

		BD1Manipulator manipulator;
		try {
			manipulator = new BD1Manipulator(bd1_filename);
		} catch (final IOException e) {
			logger.error("Error while reading.", e);
			return ret;
		}

		final String bd1_directory = FilenameFunctions
				.GetFileDirectory(bd1_filename);

		final Map<Integer, String> texture_filenames = manipulator
				.GetTextureFilenames();
		final Map<Integer, Integer> texture_handles_map = new HashMap<>();
		for (final Map.Entry<Integer, String> entry : texture_filenames
				.entrySet()) {
			final int texture_id = entry.getKey();

			String texture_filename = entry.getValue();
			texture_filename = bd1_directory + "/" + texture_filename;

			final int texture_handle = TextureMgr.LoadTexture(texture_filename);
			texture_handles_map.put(texture_id, texture_handle);
		}

		manipulator.InvertZ();
		final List<BD1Block> blocks = manipulator.GetBlocks();

		final BD1Triangulator triangulator = new BD1Triangulator();
		triangulator.TriangulateBlocks(blocks);

		final List<BD1Triangle> triangles_list = triangulator
				.GetTrianglesList();
		for (final BD1Triangle triangle : triangles_list) {
			final int texture_id = triangle.GetTextureID();
			final int texture_handle = texture_handles_map.get(texture_id);

			final int vertex_num = 3;

			final BufferedVertices buffered_vertices = new BufferedVertices();
			final IntBuffer indices = Buffers.newDirectIntBuffer(vertex_num);
			final FloatBuffer pos_buffer = Buffers
					.newDirectFloatBuffer(vertex_num * 3);
			final FloatBuffer uv_buffer = Buffers
					.newDirectFloatBuffer(vertex_num * 2);
			final FloatBuffer norm_buffer = Buffers
					.newDirectFloatBuffer(vertex_num * 3);

			for (int i = 0; i < 3; i++) {
				final Vertex3D vertex = triangle.GetVertex(i);

				final Vector position = vertex.GetPos();
				final float u = vertex.GetU();
				final float v = vertex.GetV();
				final Vector normal = vertex.GetNorm();

				indices.put(i);
				pos_buffer.put(position.GetX());
				pos_buffer.put(position.GetY());
				pos_buffer.put(position.GetZ());
				uv_buffer.put(u);
				uv_buffer.put(v);
				norm_buffer.put(normal.GetX());
				norm_buffer.put(normal.GetY());
				norm_buffer.put(normal.GetZ());
			}

			((Buffer) indices).flip();
			((Buffer) pos_buffer).flip();
			((Buffer) uv_buffer).flip();
			((Buffer) norm_buffer).flip();

			buffered_vertices.SetIndicesBuffer(indices);
			buffered_vertices.SetPosBuffer(pos_buffer);
			buffered_vertices.SetUVBuffer(uv_buffer);
			buffered_vertices.SetNormBuffer(norm_buffer);
			buffered_vertices.SetTextureHandle(texture_handle);

			ret.add(buffered_vertices);
		}

		return ret;
	}
}
