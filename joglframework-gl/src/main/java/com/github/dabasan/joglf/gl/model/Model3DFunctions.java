package com.github.dabasan.joglf.gl.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.dabasan.basis.matrix.Matrix;
import com.github.dabasan.basis.matrix.MatrixFunctions;
import com.github.dabasan.basis.vector.Vector;
import com.github.dabasan.basis.vector.VectorFunctions;
import com.github.dabasan.joglf.gl.model.animation.AnimationBlendInfo;
import com.github.dabasan.joglf.gl.model.animation.AnimationInfo;
import com.github.dabasan.joglf.gl.model.animation.AnimationInfoMap;
import com.github.dabasan.joglf.gl.model.buffer.BufferedVertices;
import com.github.dabasan.joglf.gl.model.loader.bd1.BD1Loader;
import com.github.dabasan.joglf.gl.model.loader.obj.OBJLoader;
import com.github.dabasan.joglf.gl.shader.ShaderProgram;
import com.github.dabasan.joglf.gl.shape.Triangle;
import com.github.dabasan.tool.FilenameFunctions;

/**
 * Functions for 3D models
 * 
 * @author Daba
 *
 */
public class Model3DFunctions {
	private static Logger logger = LoggerFactory.getLogger(Model3DFunctions.class);

	private static int count = 0;
	private static Map<Integer, ModelMgr> models_map = new HashMap<>();
	private static Map<Integer, AnimationInfoMap> animation_info_map = new HashMap<>();

	private static boolean keep_order_if_possible = false;

	public static void SetKeepOrderIfPossible(boolean a_keep_order_if_possible) {
		keep_order_if_possible = a_keep_order_if_possible;
	}

	public static int LoadModel(String model_filename, FlipVOption option) {
		logger.info("Start loading a model. model_filename={}", model_filename);

		final String extension = FilenameFunctions.GetFileExtension(model_filename).toLowerCase();

		ModelMgr model;
		try {
			switch (extension) {
				case "bd1" :
					if (keep_order_if_possible == true) {
						model = LoadBD1_KeepOrder(model_filename, option);
					} else {
						model = LoadBD1(model_filename, option);
					}
					break;
				case "obj" :
					model = LoadOBJ(model_filename, option);
					break;
				default :
					logger.error("Unsupported model format. extension={}", extension);
					return -1;
			}
		} catch (final IOException e) {
			logger.error("Failed to load a model.", e);
			return -1;
		}

		final int model_handle = count;
		models_map.put(model_handle, model);
		count++;

		return model_handle;
	}
	public static int LoadModel(String model_filename) {
		return LoadModel(model_filename, FlipVOption.MUST_FLIP_VERTICALLY);
	}
	private static ModelMgr LoadBD1(String model_filename, FlipVOption option) throws IOException {
		final List<BufferedVertices> buffered_vertices_list = BD1Loader.LoadBD1(model_filename);
		final ModelMgr model = new ModelMgr(buffered_vertices_list, option);

		return model;
	}
	private static ModelMgr LoadBD1_KeepOrder(String model_filename, FlipVOption option)
			throws IOException {
		final List<BufferedVertices> buffered_vertices_list = BD1Loader
				.LoadBD1_KeepOrder(model_filename);
		final ModelMgr model = new ModelMgr(buffered_vertices_list, option);

		return model;
	}
	private static ModelMgr LoadOBJ(String model_filename, FlipVOption option) throws IOException {
		final List<BufferedVertices> buffered_vertices_list = OBJLoader.LoadOBJ(model_filename);
		final ModelMgr model = new ModelMgr(buffered_vertices_list, option);

		return model;
	}

	public static int CopyModel(int model_handle) {
		if (models_map.containsKey(model_handle) == false) {
			logger.trace("No such model. model_handle={}", model_handle);
			return -1;
		}

		final ModelMgr model = models_map.get(model_handle);
		final ModelMgr copied_model = model.Copy();

		final int copied_model_handle = count;
		count++;

		models_map.put(copied_model_handle, copied_model);

		return copied_model_handle;
	}
	public static int DeleteModel(int model_handle) {
		if (models_map.containsKey(model_handle) == false) {
			logger.trace("No such model. model_handle={}", model_handle);
			return -1;
		}

		final ModelMgr model = models_map.get(model_handle);
		model.DeleteBuffers();

		models_map.remove(model_handle);
		animation_info_map.remove(model_handle);

		return 0;
	}
	public static void DeleteAllModels() {
		for (final ModelMgr model : models_map.values()) {
			model.DeleteBuffers();
		}

		models_map.clear();
		animation_info_map.clear();
		count = 0;
	}

	public static boolean ModelExists(int model_handle) {
		boolean ret;

		if (models_map.containsKey(model_handle) == true) {
			ret = true;
		} else {
			ret = false;
		}

		return ret;
	}

	public static int AddProgram(int model_handle, ShaderProgram program) {
		if (models_map.containsKey(model_handle) == false) {
			logger.trace("No such model. model_handle={}", model_handle);
			return -1;
		}

		final ModelMgr model = models_map.get(model_handle);
		model.AddProgram(program);

		return 0;
	}
	public static int RemoveAllPrograms(int model_handle) {
		if (models_map.containsKey(model_handle) == false) {
			logger.trace("No such model. model_handle={}", model_handle);
			return -1;
		}

		final ModelMgr model = models_map.get(model_handle);
		model.RemoveAllPrograms();

		return 0;
	}
	public static int SetDefaultProgram(int model_handle) {
		if (models_map.containsKey(model_handle) == false) {
			logger.trace("No such model. model_handle={}", model_handle);
			return -1;
		}

		final ModelMgr model = models_map.get(model_handle);
		final ShaderProgram program = new ShaderProgram("texture");
		model.RemoveAllPrograms();
		model.AddProgram(program);

		return 0;
	}

	public static int DrawModelWithProgram(int model_handle, ShaderProgram program,
			String sampler_name, int texture_unit) {
		if (models_map.containsKey(model_handle) == false) {
			logger.trace("No such model. model_handle={}", model_handle);
			return -1;
		}

		final ModelMgr model = models_map.get(model_handle);
		model.DrawWithProgram(program, sampler_name, texture_unit);

		return 0;
	}
	public static int DrawModel(int model_handle) {
		if (models_map.containsKey(model_handle) == false) {
			logger.trace("No such model. model_handle={}", model_handle);
			return -1;
		}

		final ModelMgr model = models_map.get(model_handle);
		model.Draw();

		return 0;
	}
	public static int DrawModel(int model_handle, String sampler_name, int texture_unit) {
		if (models_map.containsKey(model_handle) == false) {
			logger.trace("No such model. model_handle={}", model_handle);
			return -1;
		}

		final ModelMgr model = models_map.get(model_handle);
		model.Draw(sampler_name, texture_unit);

		return 0;
	}
	public static int TransferModel(int model_handle) {
		if (models_map.containsKey(model_handle) == false) {
			logger.trace("No such model. model_handle={}", model_handle);
			return -1;
		}

		final ModelMgr model = models_map.get(model_handle);
		model.Transfer();

		return 0;
	}
	public static int DrawModelElements(int model_handle, int bound) {
		if (models_map.containsKey(model_handle) == false) {
			logger.trace("No such model. model_handle={}", model_handle);
			return -1;
		}

		final ModelMgr model = models_map.get(model_handle);
		model.DrawElements(bound);

		return 0;
	}
	public static int DrawModelElements(int model_handle, String sampler_name, int texture_unit,
			int bound) {
		if (models_map.containsKey(model_handle) == false) {
			logger.trace("No such model. model_handle={}", model_handle);
			return -1;
		}

		final ModelMgr model = models_map.get(model_handle);
		model.DrawElements(sampler_name, texture_unit, bound);

		return 0;
	}

	public static int GetModelElementNum(int model_handle) {
		if (models_map.containsKey(model_handle) == false) {
			logger.trace("No such model. model_handle={}", model_handle);
			return -1;
		}

		int ret = -1;
		final ModelMgr model = models_map.get(model_handle);
		ret = model.GetElementNum();

		return ret;
	}

	/**
	 * Applies a matrix to manipulate a model.
	 * 
	 * @param model_handle
	 *            Model handle
	 * @param m
	 *            Matrix
	 * @return -1 on error and 0 on success
	 */
	public static int SetModelMatrix(int model_handle, Matrix m) {
		if (models_map.containsKey(model_handle) == false) {
			logger.trace("No such model. model_handle={}", model_handle);
			return -1;
		}

		final ModelMgr model = models_map.get(model_handle);
		model.SetMatrix(m);

		return 0;
	}

	/**
	 * Translates a model.
	 * 
	 * @param model_handle
	 *            Model handle
	 * @param translate
	 *            Translation vector
	 * @return -1 on error and 0 on success
	 */
	public static int TranslateModel(int model_handle, Vector translate) {
		if (models_map.containsKey(model_handle) == false) {
			logger.trace("No such model. model_handle={}", model_handle);
			return -1;
		}

		final ModelMgr model = models_map.get(model_handle);

		final Matrix translate_mat = MatrixFunctions.MGetTranslate(translate);
		model.SetMatrix(translate_mat);

		return 0;
	}
	/**
	 * Rotates a model.<br>
	 * The order of rotation:X→Y→Z
	 * 
	 * @param model_handle
	 *            Model handle
	 * @param rotate
	 *            Rotation angles
	 * @return -1 on error and 0 on success
	 */
	public static int RotateModel(int model_handle, Vector rotate) {
		if (models_map.containsKey(model_handle) == false) {
			logger.trace("No such model. model_handle={}", model_handle);
			return -1;
		}

		final ModelMgr model = models_map.get(model_handle);

		final Matrix rot_x = MatrixFunctions.MGetRotX(rotate.GetX());
		final Matrix rot_y = MatrixFunctions.MGetRotY(rotate.GetY());
		final Matrix rot_z = MatrixFunctions.MGetRotZ(rotate.GetZ());
		Matrix rot = MatrixFunctions.MMult(rot_y, rot_x);
		rot = MatrixFunctions.MMult(rot_z, rot);

		model.SetMatrix(rot);

		return 0;
	}
	/**
	 * Rotates a model around the specified origin.
	 * 
	 * @param model_handle
	 *            Model handle
	 * @param origin
	 *            Origin (world space)
	 * @param rotate
	 *            Rotation angles
	 * @return -1 on error and 0 on success
	 */
	public static int RotateModelLocally(int model_handle, Vector origin, Vector rotate) {
		if (models_map.containsKey(model_handle) == false) {
			logger.trace("No such model. model_handle={}", model_handle);
			return -1;
		}

		final ModelMgr model = models_map.get(model_handle);

		final Vector to_world_origin_vec = VectorFunctions
				.VSub(VectorFunctions.VGet(0.0f, 0.0f, 0.0f), origin);
		final Matrix to_world_origin_mat = MatrixFunctions.MGetTranslate(to_world_origin_vec);
		final Matrix rot_x = MatrixFunctions.MGetRotX(rotate.GetX());
		final Matrix rot_y = MatrixFunctions.MGetRotY(rotate.GetY());
		final Matrix rot_z = MatrixFunctions.MGetRotZ(rotate.GetZ());
		final Matrix to_local_origin_mat = MatrixFunctions.MGetTranslate(origin);

		Matrix mat = MatrixFunctions.MMult(rot_x, to_world_origin_mat);
		mat = MatrixFunctions.MMult(rot_y, mat);
		mat = MatrixFunctions.MMult(rot_z, mat);
		mat = MatrixFunctions.MMult(to_local_origin_mat, mat);

		model.SetMatrix(mat);

		return 0;
	}
	/**
	 * Rescales a model.
	 * 
	 * @param model_handle
	 *            Model handle
	 * @param scale
	 *            Scale
	 * @return -1 on error and 0 on success
	 */
	public static int RescaleModel(int model_handle, Vector scale) {
		if (models_map.containsKey(model_handle) == false) {
			logger.trace("No such model. model_handle={}", model_handle);
			return -1;
		}

		final ModelMgr model = models_map.get(model_handle);

		final Matrix scale_mat = MatrixFunctions.MGetScale(scale);
		model.SetMatrix(scale_mat);

		return 0;
	}

	public static int ChangeModelTexture(int model_handle, int material_index,
			int new_texture_handle) {
		if (models_map.containsKey(model_handle) == false) {
			logger.trace("No such model. model_handle={}", model_handle);
			return -1;
		}

		final ModelMgr model = models_map.get(model_handle);
		model.ChangeTexture(material_index, new_texture_handle);

		return 0;
	}

	public static int[] GetModelTextureHandles(int model_handle) {
		if (models_map.containsKey(model_handle) == false) {
			logger.trace("No such model. model_handle={}", model_handle);
			return null;
		}

		final ModelMgr model = models_map.get(model_handle);
		final int[] texture_handles = model.GetTextureHandles();

		return texture_handles;
	}

	public static List<Triangle> GetModelFaces(int model_handle) {
		if (models_map.containsKey(model_handle) == false) {
			logger.trace("No such model. model_handle={}", model_handle);
			return new ArrayList<>();
		}

		final ModelMgr model = models_map.get(model_handle);
		final List<Triangle> ret = model.GetFaces();

		return ret;
	}

	public static int AttachAnimation(int model_handle, int anim_index, int anim_src_handle,
			float time) {
		if (models_map.containsKey(model_handle) == false) {
			logger.trace("No such model. model_handle={}", model_handle);
			return -1;
		}
		if (models_map.containsKey(anim_src_handle) == false) {
			logger.trace("No such model. anim_src_handle={}", anim_src_handle);
			return -1;
		}

		if (animation_info_map.containsKey(model_handle) == false) {
			final AnimationInfoMap aimtemp = new AnimationInfoMap();
			animation_info_map.put(model_handle, aimtemp);
		}
		final AnimationInfoMap aim = animation_info_map.get(model_handle);
		aim.AppendFrame(anim_index, anim_src_handle, time);

		return 0;
	}

	public static int SetAttachedAnimationTime(int model_handle, int anim_index, float time) {
		if (animation_info_map.containsKey(model_handle) == false) {
			logger.trace("No animation info exists for this model. model_handle={}", model_handle);
			return -1;
		}

		final AnimationInfoMap aim = animation_info_map.get(model_handle);
		if (aim.AnimationInfoExists(anim_index) == false) {
			logger.trace("No corresponding animation for this index exists. anim_index={}",
					anim_index);
			return -1;
		}

		final AnimationInfo ai = aim.GetAnimationInfo(anim_index);
		final AnimationBlendInfo blend_info = ai.GetBlendInfo(time);

		final int frame1_handle = blend_info.GetFrame1Handle();
		final int frame2_handle = blend_info.GetFrame2Handle();
		final float blend_ratio = blend_info.GetBlendRatio();
		if (frame1_handle < 0) {
			logger.trace("No frames registered.");
			return -1;
		}

		final ModelMgr model = models_map.get(model_handle);

		ModelMgr frame1;
		ModelMgr frame2;
		// Use frame1 only
		if (frame2_handle < 0) {
			frame1 = models_map.get(frame1_handle);
			frame2 = frame1;
		}
		// Interpolate
		else {
			frame1 = models_map.get(frame1_handle);
			frame2 = models_map.get(frame2_handle);
		}

		model.Interpolate(frame1, frame2, blend_ratio);

		return 0;
	}

	public static float GetAnimationMaxTime(int model_handle, int anim_index) {
		if (animation_info_map.containsKey(model_handle) == false) {
			logger.trace("No such model. model_handle={}", model_handle);
			return -1;
		}

		final AnimationInfoMap aim = animation_info_map.get(model_handle);
		if (aim.AnimationInfoExists(anim_index) == false) {
			logger.trace("No corresponding animation for this index exists. anim_index={}",
					anim_index);
			return -1;
		}

		final AnimationInfo ai = aim.GetAnimationInfo(anim_index);

		final float max_time = ai.GetMaxTime();
		return max_time;
	}
}
