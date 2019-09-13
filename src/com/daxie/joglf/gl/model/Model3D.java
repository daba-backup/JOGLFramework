package com.daxie.joglf.gl.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.daxie.joglf.basis.vector.Vector;
import com.daxie.joglf.gl.model.buffer.BufferedVertices;
import com.daxie.joglf.gl.model.loader.OBJLoader;
import com.daxie.joglf.log.LogFile;
import com.daxie.joglf.tool.FilenameFunctions;

/**
 * Manages models.
 * @author Daba
 *
 */
public class Model3D {
	private static int count=0;
	private static Map<Integer, ModelMgr> models_map=new HashMap<>();
	
	public static int LoadModel(String model_filename) {
		String extension=FilenameFunctions.GetFileExtension(model_filename);
		
		int model_handle=count;
		if(extension.equals("obj")||extension.equals("OBJ")) {
			List<BufferedVertices> buffered_vertices_list=OBJLoader.LoadOBJ(model_filename);
			ModelMgr model=new ModelMgr(buffered_vertices_list);
			
			models_map.put(model_handle, model);
		}
		else {
			LogFile.WriteError("[Model3D-LoadModel] Unsupported model format. extension:"+extension, true);
			return -1;
		}
		
		count++;
		
		return model_handle;
	}
	public static int DuplicateModel(int model_handle) {
		if(models_map.containsKey(model_handle)==false) {
			LogFile.WriteError("[Model3D-DuplicateModel] No such model. model_handle:"+model_handle, true);
			return -1;
		}
		
		ModelMgr model=models_map.get(model_handle);
		ModelMgr duplicated_model=model.Duplicate();
		
		int duplicated_model_handle=count;
		count++;
		
		models_map.put(duplicated_model_handle, duplicated_model);
		
		return duplicated_model_handle;
	}
	public static int DeleteModel(int model_handle) {
		if(models_map.containsKey(model_handle)==false) {
			LogFile.WriteError("[Model3D-DeleteModel] No such model. model_handle:"+model_handle, true);
			return -1;
		}
		
		ModelMgr model=models_map.get(model_handle);
		model.DeleteBuffers();
		
		models_map.remove(model_handle);
		
		return 0;
	}
	public static void DeleteAllModels() {
		for(ModelMgr model:models_map.values()) {
			model.DeleteBuffers();
		}
		
		models_map.clear();
		count=0;
	}
	
	public static int DrawModel(int model_handle) {
		if(models_map.containsKey(model_handle)==false) {
			LogFile.WriteError("[Model3D-Draw] No such model. model_handle:"+model_handle, true);
			return -1;
		}
		
		ModelMgr model=models_map.get(model_handle);
		model.UpdateBuffers();
		model.Draw();
		
		return 0;
	}
	
	public static int SetModelPosition(int model_handle,Vector position) {
		if(models_map.containsKey(model_handle)==false) {
			LogFile.WriteError("[Model3D-SetModelPosition] No such model. model_handle:"+model_handle, true);
			return -1;
		}
		
		ModelMgr model=models_map.get(model_handle);
		model.SetPosition(position);
		
		return 0;
	}
	public static int SetModelRotation(int model_handle,Vector rotation) {
		if(models_map.containsKey(model_handle)==false) {
			LogFile.WriteError("[Model3D-SetModelRotation] No such model. model_handle:"+model_handle, true);
			return -1;
		}
		
		ModelMgr model=models_map.get(model_handle);
		model.SetRotation(rotation);
		
		return 0;
	}
	public static int SetModelScale(int model_handle,Vector scale) {
		if(models_map.containsKey(model_handle)==false) {
			LogFile.WriteError("[Model3D-SetModelScale] No such model. model_handle:"+model_handle, true);
			return -1;
		}
		
		ModelMgr model=models_map.get(model_handle);
		model.SetScale(scale);
		
		return 0;
	}
	
	public static int ChangeModelTexture(int model_handle,int material_index,int new_texture_handle) {
		if(models_map.containsKey(model_handle)==false) {
			LogFile.WriteError("[Model3D-ChangeModelTexture] No such model. model_handle:"+model_handle, true);
			return -1;
		}
		
		ModelMgr model=models_map.get(model_handle);
		model.ChangeTexture(material_index, new_texture_handle);
		
		return 0;
	}
}
