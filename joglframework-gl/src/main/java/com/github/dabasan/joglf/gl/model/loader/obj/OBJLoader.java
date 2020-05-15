package com.github.dabasan.joglf.gl.model.loader.obj;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.dabasan.basis.coloru8.ColorU8;
import com.github.dabasan.basis.coloru8.ColorU8Functions;
import com.github.dabasan.joglf.gl.model.buffer.BufferedVertices;
import com.github.dabasan.joglf.gl.texture.TextureMgr;
import com.github.dabasan.tool.FilenameFunctions;

import de.javagl.obj.FloatTuple;
import de.javagl.obj.Mtl;
import de.javagl.obj.MtlReader;
import de.javagl.obj.Obj;
import de.javagl.obj.ObjData;
import de.javagl.obj.ObjReader;
import de.javagl.obj.ObjSplitting;
import de.javagl.obj.ObjUtils;

/**
 * OBJ loader
 * 
 * @author Daba
 *
 */
public class OBJLoader {
	private static Logger logger = LoggerFactory.getLogger(OBJLoader.class);

	public static List<BufferedVertices> LoadOBJ(String obj_filename) throws IOException{
		final List<BufferedVertices> ret = new ArrayList<>();

		final String obj_directory = FilenameFunctions
				.GetFileDirectory(obj_filename);

		Obj obj;
		try(InputStreamReader isr=new InputStreamReader(new FileInputStream(obj_filename))){
			obj=ObjReader.read(isr);
		}
		obj = ObjUtils.convertToRenderable(obj);

		final List<Mtl> all_mtls = new ArrayList<>();
		for (final String mtl_filename : obj.getMtlFileNames()) {
			final String mtl_filepath = obj_directory + "/" + mtl_filename;
			
			try(InputStreamReader isr=new InputStreamReader(new FileInputStream(mtl_filepath))){
				List<Mtl> mtls = MtlReader.read(isr);
				all_mtls.addAll(mtls);
			}
		}

		final Map<String, Obj> material_groups = ObjSplitting
				.splitByMaterialGroups(obj);

		for (final Map.Entry<String, Obj> entry : material_groups.entrySet()) {
			final BufferedVertices buffered_vertices = new BufferedVertices();

			final String material_name = entry.getKey();
			final Obj material_group = entry.getValue();

			final Mtl mtl = FindMTLByName(all_mtls, material_name);
			if (mtl == null) {
				logger.warn("No such material. material_name={}",
						material_name);
				continue;
			}

			final FloatTuple ft_ambient_color = mtl.getKa();
			final FloatTuple ft_diffuse_color = mtl.getKd();
			final FloatTuple ft_specular_color = mtl.getKs();

			final ColorU8 ambient_color = ColorU8Functions.GetColorU8(
					ft_ambient_color.get(0), ft_ambient_color.get(1),
					ft_ambient_color.get(2), 1.0f);
			final ColorU8 diffuse_color = ColorU8Functions.GetColorU8(
					ft_diffuse_color.get(0), ft_diffuse_color.get(1),
					ft_diffuse_color.get(2), 1.0f);
			final ColorU8 specular_color = ColorU8Functions.GetColorU8(
					ft_specular_color.get(0), ft_specular_color.get(1),
					ft_specular_color.get(2), 1.0f);
			final float specular_exponent = mtl.getNs();
			final float dissolve = mtl.getD();
			final String diffuse_texture_map = mtl.getMapKd();

			buffered_vertices.SetAmbientColor(ambient_color);
			buffered_vertices.SetDiffuseColor(diffuse_color);
			buffered_vertices.SetSpecularColor(specular_color);
			buffered_vertices.SetSpecularExponent(specular_exponent);
			buffered_vertices.SetDissolve(dissolve);
			buffered_vertices.SetDiffuseTextureMap(diffuse_texture_map);

			final IntBuffer indices = ObjData
					.getFaceVertexIndices(material_group, 3);
			final FloatBuffer pos_buffer = ObjData.getVertices(material_group);
			final FloatBuffer uv_buffer = ObjData.getTexCoords(material_group,
					2);
			final FloatBuffer norm_buffer = ObjData.getNormals(material_group);
			buffered_vertices.SetIndicesBuffer(indices);
			buffered_vertices.SetPosBuffer(pos_buffer);
			buffered_vertices.SetUVBuffer(uv_buffer);
			buffered_vertices.SetNormBuffer(norm_buffer);

			String texture_filename = mtl.getMapKd();
			if (texture_filename == null) {
				buffered_vertices.SetTextureHandle(-1);
			} else if (texture_filename.equals("")) {
				buffered_vertices.SetTextureHandle(-1);
			} else {
				texture_filename = obj_directory + "/" + texture_filename;

				final int texture_handle = TextureMgr
						.LoadTexture(texture_filename);
				buffered_vertices.SetTextureHandle(texture_handle);
			}

			ret.add(buffered_vertices);
		}

		return ret;
	}

	private static Mtl FindMTLByName(Iterable<? extends Mtl> mtls,
			String name) {
		for (final Mtl mtl : mtls) {
			if (mtl.getName().equals(name)) {
				return mtl;
			}
		}

		return null;
	}
}
