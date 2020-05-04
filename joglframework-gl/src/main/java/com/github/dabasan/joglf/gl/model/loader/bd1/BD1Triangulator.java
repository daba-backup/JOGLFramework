package com.github.dabasan.joglf.gl.model.loader.bd1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.dabasan.basis.coloru8.ColorU8Functions;
import com.github.dabasan.basis.vector.Vector;
import com.github.dabasan.basis.vector.VectorFunctions;
import com.github.dabasan.joglf.gl.shape.Vertex3D;
import com.github.dabasan.xops.bd1.BD1Block;
import com.github.dabasan.xops.bd1.BD1Functions;

/**
 * Triangulates faces of BD1 blocks.
 * 
 * @author Daba
 *
 */
class BD1Triangulator {
	private final Logger logger = LoggerFactory
			.getLogger(BD1Triangulator.class);

	private final List<BD1Triangle> triangles_list;

	public BD1Triangulator() {
		triangles_list = new ArrayList<>();
	}

	public void TriangulateBlock(BD1Block block) {
		if (block == null) {
			logger.warn("Null argument where non-null required.");
			return;
		}

		final Vector[] positions = block.GetVertexPositions();
		final float[] us = block.GetUs();
		final float[] vs = block.GetVs();
		final int[] texture_ids = block.GetTextureIDs();

		final Vertex3D[] vertices = new Vertex3D[24];
		for (int i = 0; i < 24; i++) {
			vertices[i] = new Vertex3D();
		}

		for (int i = 0; i < 6; i++) {
			final int[] vertex_indices = BD1Functions
					.GetFaceCorrespondingVertexIndices(i);
			final int[] uv_indices = BD1Functions
					.GetFaceCorrespondingUVIndices(i);

			final Vector v1 = VectorFunctions.VSub(positions[vertex_indices[3]],
					positions[vertex_indices[0]]);
			final Vector v2 = VectorFunctions.VSub(positions[vertex_indices[1]],
					positions[vertex_indices[0]]);
			Vector face_normal = VectorFunctions.VCross(v1, v2);
			face_normal = VectorFunctions.VNorm(face_normal);

			for (int j = 0; j < 4; j++) {
				final int array_index = i * 4 + j;
				final int vertex_index = vertex_indices[j];
				final int uv_index = uv_indices[j];

				vertices[array_index].SetPos(positions[vertex_index]);
				vertices[array_index].SetDif(
						ColorU8Functions.GetColorU8(1.0f, 1.0f, 1.0f, 1.0f));
				vertices[array_index].SetSpc(
						ColorU8Functions.GetColorU8(0.0f, 0.0f, 0.0f, 0.0f));
				vertices[array_index].SetU(us[uv_index]);
				vertices[array_index].SetV(vs[uv_index] * (-1.0f));

				vertices[array_index].SetNorm(face_normal);
			}
		}

		final Vertex3D[] inverted_vertices = new Vertex3D[24];
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 4; j++) {
				inverted_vertices[i * 4 + j] = vertices[(i + 1) * 4 - 1 - j];
			}
		}

		final BD1Triangle[] triangles = new BD1Triangle[12];
		for (int i = 0; i < 12; i++) {
			triangles[i] = new BD1Triangle();
		}

		for (int i = 0; i < 12; i += 2) {
			int vertex_array_index;
			for (int j = 0; j < 3; j++) {
				vertex_array_index = (i / 2) * 4 + j;
				triangles[i].SetVertex(j,
						inverted_vertices[vertex_array_index]);
			}
			for (int j = 0; j < 3; j++) {
				vertex_array_index = (i / 2) * 4 + (j + 2) % 4;
				triangles[i + 1].SetVertex(j,
						inverted_vertices[vertex_array_index]);
			}
		}

		for (int i = 0; i < 12; i += 2) {
			final int texture_id = texture_ids[i / 2];

			triangles[i].SetTextureID(texture_id);
			triangles[i + 1].SetTextureID(texture_id);

			triangles_list.add(triangles[i]);
			triangles_list.add(triangles[i + 1]);
		}
	}
	public void TriangulateBlocks(List<BD1Block> blocks) {
		if (blocks == null) {
			logger.warn("Null argument where non-null required.");
			return;
		}

		for (final BD1Block block : blocks) {
			TriangulateBlock(block);
		}
	}

	public List<BD1Triangle> GetTrianglesList() {
		return triangles_list;
	}
	public Map<Integer, List<BD1Triangle>> GetTrianglesMap() {
		final Map<Integer, List<BD1Triangle>> ret = new HashMap<>();

		for (final BD1Triangle triangle : triangles_list) {
			final int texture_id = triangle.GetTextureID();

			if (ret.containsKey(texture_id) == false) {
				final List<BD1Triangle> list_temp = new ArrayList<>();
				ret.put(texture_id, list_temp);
			}
			final List<BD1Triangle> triangles = ret.get(texture_id);
			triangles.add(triangle);
		}

		return ret;
	}
}
