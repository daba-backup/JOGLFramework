package com.daxie.joglf.gl.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.daxie.joglf.basis.vector.Vector;
import com.daxie.joglf.gl.model.animation.AnimationBlendInfo;
import com.daxie.joglf.gl.model.animation.AnimationInfo;
import com.daxie.joglf.gl.model.animation.AnimationInfoMap;
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
	private static Map<Integer, AnimationInfoMap> animation_info_map=new HashMap<>();
	
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
		animation_info_map.remove(model_handle);
		
		return 0;
	}
	public static void DeleteAllModels() {
		for(ModelMgr model:models_map.values()) {
			model.DeleteBuffers();
		}
		
		models_map.clear();
		animation_info_map.clear();
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
	
	public static int AttachAnimation(int model_handle,int anim_index,int anim_src_handle,float time) {
		if(models_map.containsKey(model_handle)==false) {
			LogFile.WriteError("[Model3D-AttachAnimation] No such model. model_handle:"+model_handle, true);
			return -1;
		}
		if(models_map.containsKey(model_handle)==false) {
			LogFile.WriteError("[Model3D-AttachAnimation] No such model. anim_src_handle:"+model_handle, true);
			return -1;
		}
		
		if(animation_info_map.containsKey(model_handle)==false) {
			AnimationInfoMap aimtemp=new AnimationInfoMap();
			animation_info_map.put(model_handle, aimtemp);
		}
		AnimationInfoMap aim=animation_info_map.get(model_handle);
		aim.AppendFrame(anim_index, anim_src_handle, time);
		
		return 0;
	}
	
	public static int SetAttachAnimationTime(int model_handle,int anim_index,float time) {
		if(animation_info_map.containsKey(model_handle)==false) {
			LogFile.WriteError("[Model3D-SetAttachAnimationTime]", true);
			LogFile.WriteError("No animation info exists for this model. model_handle:"+model_handle,false);
			return -1;
		}
		
		AnimationInfoMap aim=animation_info_map.get(model_handle);
		if(aim.AnimationInfoExists(anim_index)==false) {
			LogFile.WriteError("[Model3D-SetAttachAnimationTime]", true);
			LogFile.WriteError("No corresponding animation for this index exists. anim_index:"+anim_index, false);
			return -1;
		}
		
		AnimationInfo ai=aim.GetAnimationInfo(anim_index);
		AnimationBlendInfo blend_info=ai.GetBlendInfo(time);
		
		int frame1_handle=blend_info.GetFrame1Handle();
		int frame2_handle=blend_info.GetFrame2Handle();
		float blend_ratio=blend_info.GetBlendRatio();
		if(frame1_handle<0) {
			LogFile.WriteError("[Model3D-SetAttachAnimationTime] No frames registered.", true);
			return -1;
		}
		
		ModelMgr model=models_map.get(model_handle);
		
		ModelMgr frame1;
		ModelMgr frame2;
		//Use frame1 only
		if(frame2_handle<0) {
			frame1=models_map.get(frame1_handle);
			frame2=frame1;
		}
		//Interpolate
		else {
			frame1=models_map.get(frame1_handle);
			frame2=models_map.get(frame2_handle);
		}
		
		model.Interpolate(frame1, frame2, blend_ratio);
		
		return 0;
	}
	
	public static float GetAnimationMaxTime(int model_handle,int anim_index) {
		if(animation_info_map.containsKey(model_handle)==false) {
			LogFile.WriteError("[Model3D-GetAnimationTotalTime] No such model. model_handle:"+model_handle, true);
			return -1;
		}
		
		AnimationInfoMap aim=animation_info_map.get(model_handle);
		if(aim.AnimationInfoExists(anim_index)==false) {
			LogFile.WriteError("[Model3D-GetAnimationTotalTime]", true);
			LogFile.WriteError("No corresponding animation for this index exists. anim_index:"+anim_index, false);
			return -1;
		}
		
		AnimationInfo ai=aim.GetAnimationInfo(anim_index);
		
		float max_time=ai.GetMaxTime();
		return max_time;
	}
}
