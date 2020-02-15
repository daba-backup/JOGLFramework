package com.daxie.joglf.gl.model.loader.obj;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.daxie.joglf.gl.model.buffer.BufferedVertices;
import com.daxie.joglf.gl.texture.TextureMgr;
import com.daxie.log.LogWriter;
import com.daxie.tool.ExceptionFunctions;
import com.daxie.tool.FilenameFunctions;

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
	public static List<BufferedVertices> LoadOBJ(String obj_filename){
		List<BufferedVertices> ret=new ArrayList<>();
		
		String obj_directory=FilenameFunctions.GetFileDirectory(obj_filename);
		
		InputStreamReader isr=null;
		try {
			isr=new InputStreamReader(new FileInputStream(obj_filename));
		}
		catch(IOException e) {
			LogWriter.WriteWarn("[OBJLoader-LoadOBJ] File not found. filename:"+obj_filename,true);
			return ret;
		}
		
		Obj obj=null;
		try {
			obj=ObjReader.read(isr);
		}
		catch(IOException e) {
			String str=ExceptionFunctions.GetPrintStackTraceString(e);
			
			LogWriter.WriteWarn("[OBJLoader-LoadOBJ] Below is the stack trace.",true);
			LogWriter.WriteWarn(str,false);
			
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
				LogWriter.WriteWarn("[OBJLoader-LoadOBJ] MTL file not found. filename:"+mtl_filepath,true);
				continue;
			}
			
			List<Mtl> mtls=null;
			try {
				mtls=MtlReader.read(isr);
			}
			catch(IOException e) {
				String str=ExceptionFunctions.GetPrintStackTraceString(e);
				
				LogWriter.WriteWarn("[OBJLoader-LoadOBJ] Below is the stack trace.",true);
				LogWriter.WriteWarn(str,false);
				
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
				LogWriter.WriteWarn("[OBJLoader-LoadOBJ] No such material. name:"+material_name,true);
				continue;
			}
			
			IntBuffer indices=ObjData.getFaceVertexIndices(material_group,3);
			FloatBuffer pos_buffer=ObjData.getVertices(material_group);
			FloatBuffer uv_buffer=ObjData.getTexCoords(material_group,2);
			FloatBuffer norm_buffer=ObjData.getNormals(material_group);
			buffered_vertices.SetIndices(indices);
			buffered_vertices.SetPosBuffer(pos_buffer);
			buffered_vertices.SetUVBuffer(uv_buffer);
			buffered_vertices.SetNormBuffer(norm_buffer);
			
			String texture_filename=mtl.getMapKd();
			texture_filename=obj_directory+"/"+texture_filename;
			
			int texture_handle=TextureMgr.LoadTexture(texture_filename);
			buffered_vertices.SetTextureHandle(texture_handle);
			
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
