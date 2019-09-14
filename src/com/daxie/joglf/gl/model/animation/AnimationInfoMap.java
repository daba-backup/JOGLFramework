package com.daxie.joglf.gl.model.animation;

import java.util.HashMap;
import java.util.Map;

/**
 * Map of animation info
 * @author Daba
 *
 */
public class AnimationInfoMap {
	//(anim_index, animation_info)
	private Map<Integer, AnimationInfo> animation_info_map;
	
	public AnimationInfoMap() {
		animation_info_map=new HashMap<>();
	}
	
	public void AppendFrame(int anim_index,int frame_handle,float time) {
		if(animation_info_map.containsKey(anim_index)==false) {
			AnimationInfo aitemp=new AnimationInfo();
			animation_info_map.put(anim_index, aitemp);
		}
		
		AnimationInfo animation_info=animation_info_map.get(anim_index);
		animation_info.AppendFrame(time, frame_handle);
	}
	
	public boolean AnimationInfoExists(int anim_index) {
		return animation_info_map.containsKey(anim_index);
	}
	
	public AnimationInfo GetAnimationInfo(int anim_index) {
		return animation_info_map.get(anim_index);
	}
}
