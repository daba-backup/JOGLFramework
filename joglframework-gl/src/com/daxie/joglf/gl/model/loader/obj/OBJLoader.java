package com.daxie.joglf.gl.model.loader.obj;

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

import com.daxie.basis.coloru8.ColorU8;
import com.daxie.basis.coloru8.ColorU8Functions;
import com.daxie.joglf.gl.model.buffer.BufferedVertices;
import com.daxie.joglf.gl.texture.TextureMgr;
import com.daxie.tool.FilenameFunctions;

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
 * @author Daba
 *
 */
public class OBJLoader {
	private static Logger logger=LoggerFactory.getLogger(OBJLoader.class);
	
	public static List<BufferedVertices> LoadOBJ(String obj_filename){
		List<BufferedVertices> ret=new ArrayList<>();
		
		String obj_directory=FilenameFunctions.GetFileDirectory(obj_filename);
		
		InputStreamReader isr=null;
		try {
			isr=new InputStreamReader(new FileInputStream(obj_filename));
		}
		catch(IOException e) {
			logger.error("Error while reading.",e);
			return ret;
		}
		
		Obj obj=null;
		try {
			obj=ObjReader.read(isr);
		}
		catch(IOException e) {
			logger.error("Error while reading.",e);
			return ret;
		}
		
		obj=ObjUtils.convertToRenderable(obj);
		
		List<Mtl> all_mtls=new ArrayList<>();
		for(String mtl_filename:obj.getMtlFileNames()) {
			String mtl_filepath=obj_directory+"/"+mtl_filename;
			try {
				isr=new InputStreamReader(new FileInputStream(mtl_filepath));
			}
			catch(IOException e) {
				logger.warn("Error while reading.",e);
				continue;
			}
			
			List<Mtl> mtls=null;
			try {
				mtls=MtlReader.read(isr);
			}
			catch(IOException e) {
				logger.error("Error while reading.",e);
				return ret;
			}
			
			all_mtls.addAll(mtls);
		}
		
		Map<String, Obj> material_groups=ObjSplitting.splitByMaterialGroups(obj);
	
		for(Map.Entry<String, Obj> entry:material_groups.entrySet()) {
			BufferedVertices buffered_vertices=new BufferedVertices();
			
			String material_name=entry.getKey();
			Obj material_group=entry.getValue();
			
			Mtl mtl=FindMTLByName(all_mtls, material_name);
			if(mtl==null) {
				logger.warn("No such material. material_name={}",material_name);
				continue;
			}
			
			FloatTuple ft_ambient_color=mtl.getKa();
			FloatTuple ft_diffuse_color=mtl.getKd();
			FloatTuple ft_specular_color=mtl.getKs();
			
			ColorU8 ambient_color=ColorU8Functions.GetColorU8(
					ft_ambient_color.get(0), ft_ambient_color.get(1), ft_ambient_color.get(2), 1.0f);
			ColorU8 diffuse_color=ColorU8Functions.GetColorU8(
					ft_diffuse_color.get(0), ft_diffuse_color.get(1), ft_diffuse_color.get(2), 1.0f);
			ColorU8 specular_color=ColorU8Functions.GetColorU8(
					ft_specular_color.get(0), ft_specular_color.get(1), ft_specular_color.get(2), 1.0f);
			float specular_exponent=mtl.getNs();
			float dissolve=mtl.getD();
			String diffuse_texture_map=mtl.getMapKd();
			
			buffered_vertices.SetAmbientColor(ambient_color);
			buffered_vertices.SetDiffuseColor(diffuse_color);
			buffered_vertices.SetSpecularColor(specular_color);
			buffered_vertices.SetSpecularExponent(specular_exponent);
			buffered_vertices.SetDissolve(dissolve);
			buffered_vertices.SetDiffuseTextureMap(diffuse_texture_map);
			
			IntBuffer indices=ObjData.getFaceVertexIndices(material_group,3);
			FloatBuffer pos_buffer=ObjData.getVertices(material_group);
			FloatBuffer uv_buffer=ObjData.getTexCoords(material_group,2);
			FloatBuffer norm_buffer=ObjData.getNormals(material_group);
			buffered_vertices.SetIndices(indices);
			buffered_vertices.SetPosBuffer(pos_buffer);
			buffered_vertices.SetUVBuffer(uv_buffer);
			buffered_vertices.SetNormBuffer(norm_buffer);
			
			String texture_filename=mtl.getMapKd();
			if(texture_filename.equals("")) {
				buffered_vertices.SetTextureHandle(-1);
			}
			else {
				texture_filename=obj_directory+"/"+texture_filename;
				
				int texture_handle=TextureMgr.LoadTexture(texture_filename);
				buffered_vertices.SetTextureHandle(texture_handle);
			}
			
			ret.add(buffered_vertices);
		}
		
		return ret;
	}
	
	private static Mtl FindMTLByName(Iterable<? extends Mtl> mtls,String name) {
		for(Mtl mtl:mtls) {
			if(mtl.getName().equals(name)) {
				return mtl;
			}
		}
		
		return null;
	}
}
