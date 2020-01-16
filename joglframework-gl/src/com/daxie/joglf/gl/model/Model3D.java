package com.daxie.joglf.gl.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.daxie.basis.matrix.Matrix;
import com.daxie.basis.vector.Vector;
import com.daxie.joglf.gl.model.animation.AnimationBlendInfo;
import com.daxie.joglf.gl.model.animation.AnimationInfo;
import com.daxie.joglf.gl.model.animation.AnimationInfoMap;
import com.daxie.joglf.gl.model.buffer.BufferedVertices;
import com.daxie.joglf.gl.model.collision.CollResult;
import com.daxie.joglf.gl.model.collision.CollResultDim;
import com.daxie.joglf.gl.model.collision.CollTriangle;
import com.daxie.joglf.gl.model.loader.bd1.BD1Loader;
import com.daxie.joglf.gl.model.loader.obj.OBJLoader;
import com.daxie.joglf.hitcheck.HitCheckFunctions;
import com.daxie.joglf.hitcheck.entity.HitResult;
import com.daxie.log.LogFile;
import com.daxie.tool.FilenameFunctions;

/**
 * Model3D
 * @author Daba
 *
 */
public class Model3D {
	private static int count=0;
	private static Map<Integer, ModelMgr> models_map=new HashMap<>();
	private static Map<Integer, AnimationInfoMap> animation_info_map=new HashMap<>();
	
	private static boolean keep_order_if_possible=false;
	
	public static void SetKeepOrderIfPossible(boolean a_keep_order_if_possible) {
		keep_order_if_possible=a_keep_order_if_possible;
	}
	
	public static int LoadModel(String model_filename) {
		String extension=FilenameFunctions.GetFileExtension(model_filename);
		
		int model_handle=count;
		if(extension.equals("obj")||extension.equals("OBJ")) {
			List<BufferedVertices> buffered_vertices_list=OBJLoader.LoadOBJ(model_filename);
			ModelMgr model=new ModelMgr(buffered_vertices_list);
			
			models_map.put(model_handle, model);
		}
		else if(extension.equals("bd1")||extension.equals("BD1")) {
			List<BufferedVertices> buffered_vertices_list;
			if(keep_order_if_possible==false) {
				buffered_vertices_list=BD1Loader.LoadBD1(model_filename);
			}
			else {
				buffered_vertices_list=BD1Loader.LoadBD1_KeepOrder(model_filename);
			}
			ModelMgr model=new ModelMgr(buffered_vertices_list);
			
			models_map.put(model_handle, model);
		}
		else {
			LogFile.WriteWarn("[Model3D-LoadModel] Unsupported model format. extension:"+extension, true);
			return -1;
		}
		
		count++;
		
		return model_handle;
	}
	public static int DuplicateModel(int model_handle) {
		if(models_map.containsKey(model_handle)==false) {
			LogFile.WriteWarn("[Model3D-DuplicateModel] No such model. model_handle:"+model_handle, true);
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
			LogFile.WriteWarn("[Model3D-DeleteModel] No such model. model_handle:"+model_handle, true);
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
	
	public static boolean ModelExists(int model_handle) {
		boolean ret;
		
		if(models_map.containsKey(model_handle)==true)ret=true;
		else ret=false;
		
		return ret;
	}
	
	public static int AddProgram(int model_handle,String program_name) {
		if(models_map.containsKey(model_handle)==false) {
			LogFile.WriteWarn("[Model3D-AddProgram] No such model. model_handle:"+model_handle, true);
			return -1;
		}
		
		ModelMgr model=models_map.get(model_handle);
		model.AddProgram(program_name);
		
		return 0;
	}
	public static int RemoveProgram(int model_handle,String program_name) {
		if(models_map.containsKey(model_handle)==false) {
			LogFile.WriteWarn("[Model3D-RemoveProgram] No such model. model_handle:"+model_handle, true);
			return -1;
		}
		
		ModelMgr model=models_map.get(model_handle);
		model.RemoveProgram(program_name);
		
		return 0;
	}
	public static int SetDefaultProgram(int model_handle) {
		if(models_map.containsKey(model_handle)==false) {
			LogFile.WriteWarn("[Model3D-SetDefaultProgram] No such model. model_handle:"+model_handle, true);
			return -1;
		}
		
		ModelMgr model=models_map.get(model_handle);
		model.SetDefaultProgram();
		
		return 0;
	}
	public static int RemoveAllPrograms(int model_handle) {
		if(models_map.containsKey(model_handle)==false) {
			LogFile.WriteWarn("[Model3D-RemoveAllPrograms] No such model. model_handle:"+model_handle, true);
			return -1;
		}
		
		ModelMgr model=models_map.get(model_handle);
		model.RemoveAllPrograms();
		
		return 0;
	}
	
	public static int DrawModel(int model_handle) {
		if(models_map.containsKey(model_handle)==false) {
			LogFile.WriteWarn("[Model3D-DrawModel] No such model. model_handle:"+model_handle, true);
			return -1;
		}
		
		ModelMgr model=models_map.get(model_handle);
		model.Draw();
		
		return 0;
	}
	public static int DrawModelElements(int model_handle,int bound) {
		if(models_map.containsKey(model_handle)==false) {
			LogFile.WriteWarn("[Model3D-DrawModelElements] No such model. model_handle:"+model_handle, true);
			return -1;
		}
		
		ModelMgr model=models_map.get(model_handle);
		model.DrawElements(bound);
		
		return 0;
	}
	
	public static Vector GetModelPosition(int model_handle) {
		Vector ret=new Vector();
		
		if(models_map.containsKey(model_handle)==false) {
			LogFile.WriteWarn("[Model3D-GetModelPosition] No such model. model_handle:"+model_handle, true);
			return ret;
		}
		
		ModelMgr model=models_map.get(model_handle);
		ret=model.GetPosition();
		
		return ret;
	}
	public static Vector GetModelRotation(int model_handle) {
		Vector ret=new Vector();
		
		if(models_map.containsKey(model_handle)==false) {
			LogFile.WriteWarn("[Model3D-GetModelRotation] No such model. model_handle:"+model_handle, true);
			return ret;
		}
		
		ModelMgr model=models_map.get(model_handle);
		ret=model.GetRotation();
		
		return ret;
	}
	public static Vector GetModelScale(int model_handle) {
		Vector ret=new Vector();
		
		if(models_map.containsKey(model_handle)==false) {
			LogFile.WriteWarn("[Model3D-GetModelScale] No such model. model_handle:"+model_handle, true);
			return ret;
		}
		
		ModelMgr model=models_map.get(model_handle);
		ret=model.GetScale();
		
		return ret;
	}
	public static int GetModelElementNum(int model_handle) {
		if(models_map.containsKey(model_handle)==false) {
			LogFile.WriteWarn("[Model3D-GetModelElementNum] No such model. model_handle:"+model_handle, true);
			return -1;
		}
		
		int ret=-1;
		ModelMgr model=models_map.get(model_handle);
		ret=model.GetElementNum();
		
		return ret;
	}
	
	public static int SetModelPosition(int model_handle,Vector position) {
		if(models_map.containsKey(model_handle)==false) {
			LogFile.WriteWarn("[Model3D-SetModelPosition] No such model. model_handle:"+model_handle, true);
			return -1;
		}
		
		ModelMgr model=models_map.get(model_handle);
		model.SetPosition(position);
		
		return 0;
	}
	public static int SetModelRotation(int model_handle,Vector rotation) {
		if(models_map.containsKey(model_handle)==false) {
			LogFile.WriteWarn("[Model3D-SetModelRotation] No such model. model_handle:"+model_handle, true);
			return -1;
		}
		
		ModelMgr model=models_map.get(model_handle);
		model.SetRotation(rotation);
		
		return 0;
	}
	public static int SetModelScale(int model_handle,Vector scale) {
		if(models_map.containsKey(model_handle)==false) {
			LogFile.WriteWarn("[Model3D-SetModelScale] No such model. model_handle:"+model_handle, true);
			return -1;
		}
		
		ModelMgr model=models_map.get(model_handle);
		model.SetScale(scale);
		
		return 0;
	}
	/**
	 * Applies a matrix to manipulate a model.<br>
	 * Once this method is called with a non-identity matrix,
	 * {@link #SetModelPosition(int, Vector)}, {@link #SetModelRotation(int, Vector)} and {@link #SetModelScale(int, Vector)} are disabled.<br>
	 * Pass an identity matrix to this method in order to re-enable those methods.
	 * @param model_handle Model handle
	 * @param m Matrix
	 * @return -1 on error and 0 on success
	 */
	public static int SetModelMatrix(int model_handle,Matrix m) {
		if(models_map.containsKey(model_handle)==false) {
			LogFile.WriteWarn("[Model3D-SetModelMatrix] No such model. model_handle:"+model_handle, true);
			return -1;
		}
		
		ModelMgr model=models_map.get(model_handle);
		model.SetMatrix(m);
		
		return 0;
	}
	
	public static int ChangeModelTexture(int model_handle,int material_index,int new_texture_handle) {
		if(models_map.containsKey(model_handle)==false) {
			LogFile.WriteWarn("[Model3D-ChangeModelTexture] No such model. model_handle:"+model_handle, true);
			return -1;
		}
		
		ModelMgr model=models_map.get(model_handle);
		model.ChangeTexture(material_index, new_texture_handle);
		
		return 0;
	}
	
	public static int AttachAnimation(int model_handle,int anim_index,int anim_src_handle,float time) {
		if(models_map.containsKey(model_handle)==false) {
			LogFile.WriteWarn("[Model3D-AttachAnimation] No such model. model_handle:"+model_handle, true);
			return -1;
		}
		if(models_map.containsKey(anim_src_handle)==false) {
			LogFile.WriteWarn("[Model3D-AttachAnimation] No such model. anim_src_handle:"+model_handle, true);
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
	
	public static int SetAttachedAnimationTime(int model_handle,int anim_index,float time) {
		if(animation_info_map.containsKey(model_handle)==false) {
			LogFile.WriteWarn("[Model3D-SetAttachedAnimationTime]", true);
			LogFile.WriteWarn("No animation info exists for this model. model_handle:"+model_handle,false);
			return -1;
		}
		
		AnimationInfoMap aim=animation_info_map.get(model_handle);
		if(aim.AnimationInfoExists(anim_index)==false) {
			LogFile.WriteWarn("[Model3D-SetAttachedAnimationTime]", true);
			LogFile.WriteWarn("No corresponding animation for this index exists. anim_index:"+anim_index, false);
			return -1;
		}
		
		AnimationInfo ai=aim.GetAnimationInfo(anim_index);
		AnimationBlendInfo blend_info=ai.GetBlendInfo(time);
		
		int frame1_handle=blend_info.GetFrame1Handle();
		int frame2_handle=blend_info.GetFrame2Handle();
		float blend_ratio=blend_info.GetBlendRatio();
		if(frame1_handle<0) {
			LogFile.WriteWarn("[Model3D-SetAttachedAnimationTime] No frames registered.", true);
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
			LogFile.WriteWarn("[Model3D-GetAnimationTotalTime] No such model. model_handle:"+model_handle, true);
			return -1;
		}
		
		AnimationInfoMap aim=animation_info_map.get(model_handle);
		if(aim.AnimationInfoExists(anim_index)==false) {
			LogFile.WriteWarn("[Model3D-GetAnimationTotalTime]", true);
			LogFile.WriteWarn("No corresponding animation for this index exists. anim_index:"+anim_index, false);
			return -1;
		}
		
		AnimationInfo ai=aim.GetAnimationInfo(anim_index);
		
		float max_time=ai.GetMaxTime();
		return max_time;
	}
	
	public static boolean IsCollInfoSetup(int model_handle) {
		if(models_map.containsKey(model_handle)==false) {
			LogFile.WriteWarn("[Model3D-IsCollInfoSetup] No such model. model_handle:"+model_handle, true);
			return false;
		}
		
		ModelMgr model=models_map.get(model_handle);
		
		return model.IsCollInfoSetup();
	}
	public static int SetupCollInfo(int model_handle) {
		if(models_map.containsKey(model_handle)==false) {
			LogFile.WriteWarn("[Model3D-SetupCollInfo] No such model. model_handle:"+model_handle, true);
			return -1;
		}
		
		ModelMgr model=models_map.get(model_handle);
		model.SetupCollInfo();
		
		return 0;
	}
	public static int RefreshCollInfo(int model_handle) {
		return SetupCollInfo(model_handle);
	}
	public static List<CollTriangle> GetCollTriangles(int model_handle){
		List<CollTriangle> ret=new ArrayList<>();
		
		if(models_map.containsKey(model_handle)==false) {
			LogFile.WriteWarn("[Model3D-GetCollTriangles] No such model. model_handle:"+model_handle, true);
			return ret;
		}
		
		ModelMgr model=models_map.get(model_handle);
		if(model.IsCollInfoSetup()==false) {
			LogFile.WriteWarn("[Model3D-GetCollTriangles] Collision info not set up. model_handle:"+model_handle, true);
			return ret;
		}
		
		ret=model.GetCollTriangles();
		
		return ret;
	}
	
	public static CollResult CollCheck_Segment(int model_handle,Vector segment_pos_1,Vector segment_pos_2) {
		CollResult coll_result=new CollResult();
		coll_result.SetHitFlag(false);
		
		if(models_map.containsKey(model_handle)==false) {
			LogFile.WriteWarn("[Model3D-CollCheck_Segment] No such model. model_handle:"+model_handle, true);
			return coll_result;
		}
		
		ModelMgr model=models_map.get(model_handle);
		if(model.IsCollInfoSetup()==false) {
			LogFile.WriteWarn("[Model3D-CollCheck_Segment] Collision info not set up. model_handle:"+model_handle, true);
			return coll_result;
		}
		
		List<CollTriangle> coll_triangles=model.GetCollTriangles();
		for(CollTriangle coll_triangle:coll_triangles) {
			Vector[] vertices=coll_triangle.GetVertices();
			Vector face_normal=coll_triangle.GetFaceNormal();
			
			HitResult hit_result=HitCheckFunctions.HitCheck_Segment_Triangle(
					segment_pos_1, segment_pos_2, vertices[0],vertices[1],vertices[2]);
			if(hit_result.GetHitFlag()==true) {
				coll_result.SetHitFlag(true);
				coll_result.SetHitPosition(hit_result.GetHitPosition());
				coll_result.SetFaceNormal(face_normal);
				
				for(int i=0;i<3;i++) {
					coll_result.SetVertex(i, vertices[i]);
				}
				
				break;
			}
		}
		
		return coll_result;
	}
	/**
	 * Collision check of a capsule against a model.<br>
	 * Note that returned CollResultDim does not contain valid hit positions. 
	 * @param model_handle Model handle
	 * @param capsule_pos_1 Capsule pos 1
	 * @param capsule_pos_2 Capsule pos 2
	 * @param r Radius of the capsule
	 * @return CollResultDim
	 */
	public static CollResultDim CollCheck_Capsule(int model_handle,Vector capsule_pos_1,Vector capsule_pos_2,float r) {
		CollResultDim coll_result_dim=new CollResultDim();
		
		if(models_map.containsKey(model_handle)==false) {
			LogFile.WriteWarn("[Model3D-CollCheck_Capsule] No such model. model_handle:"+model_handle, true);
			return coll_result_dim;
		}
		
		ModelMgr model=models_map.get(model_handle);
		if(model.IsCollInfoSetup()==false) {
			LogFile.WriteWarn("[Model3D-CollCheck_Capsule] Collision info not set up. model_handle:"+model_handle, true);
			return coll_result_dim;
		}
		
		List<CollTriangle> coll_triangles=model.GetCollTriangles();
		for(CollTriangle coll_triangle:coll_triangles) {
			Vector[] vertices=coll_triangle.GetVertices();
			Vector face_normal=coll_triangle.GetFaceNormal();
			
			if(HitCheckFunctions.HitCheck_Capsule_Triangle(
					capsule_pos_1, capsule_pos_2, r, vertices[0],vertices[1],vertices[2])==true) {
				CollResult coll_result=new CollResult();
				
				coll_result.SetHitFlag(true);
				coll_result.SetFaceNormal(face_normal);
				
				for(int i=0;i<3;i++) {
					coll_result.SetVertex(i, vertices[i]);
				}
				
				coll_result_dim.AddCollResult(coll_result);
			}
		}
		
		return coll_result_dim;
	}
	/**
	 * Collision check of a sphere against a model.<br>
	 * Note that returned CollResultDim does not contain valid hit positions. 
	 * @param model_handle Model handle
	 * @param center Center of the sphere
	 * @param r Radius of the sphere
	 * @return CollResultDim
	 */
	public static CollResultDim CollCheck_Sphere(int model_handle,Vector center,float r) {
		CollResultDim coll_result_dim=new CollResultDim();
		
		if(models_map.containsKey(model_handle)==false) {
			LogFile.WriteWarn("[Model3D-CollCheck_Sphere] No such model. model_handle:"+model_handle, true);
			return coll_result_dim;
		}
		
		ModelMgr model=models_map.get(model_handle);
		if(model.IsCollInfoSetup()==false) {
			LogFile.WriteWarn("[Model3D-CollCheck_Sphere] Collision info not set up. model_handle:"+model_handle, true);
			return coll_result_dim;
		}
		
		List<CollTriangle> coll_triangles=model.GetCollTriangles();
		for(CollTriangle coll_triangle:coll_triangles) {
			Vector[] vertices=coll_triangle.GetVertices();
			Vector face_normal=coll_triangle.GetFaceNormal();
			
			if(HitCheckFunctions.HitCheck_Sphere_Triangle(
					center, r, vertices[0],vertices[1],vertices[2])==true) {
				CollResult coll_result=new CollResult();
				
				coll_result.SetHitFlag(true);
				coll_result.SetFaceNormal(face_normal);
				
				for(int i=0;i<3;i++) {
					coll_result.SetVertex(i, vertices[i]);
				}
				
				coll_result_dim.AddCollResult(coll_result);
			}
		}
		
		return coll_result_dim;
	}
}
