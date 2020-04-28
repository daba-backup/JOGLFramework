package com.github.dabasan.joglf.gl.model.animation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.dabasan.joglf.gl.window.WindowCommonInfo;

/**
 * Animation information
 * @author Daba
 *
 */
public class AnimationInfo {
	private Logger logger=LoggerFactory.getLogger(AnimationInfo.class);
	
	//(frame_index, frame_handle)
	private Map<Integer, Integer> frames_map;
	
	public AnimationInfo() {
		frames_map=new TreeMap<>();
	}
	
	public void AppendFrame(float time,int frame_handle) {
		int fps=WindowCommonInfo.GetFPS();
		int frame_index=Math.round(fps*time);
		
		frames_map.put(frame_index, frame_handle);
	}
	
	public float GetMaxTime() {
		if(frames_map.size()==0) {
			logger.warn("No frames registered.");
			return 0.0f;
		}
		
		Set<Integer> frame_indices_set=frames_map.keySet();
		List<Integer> frame_indices=new ArrayList<>(frame_indices_set);
		
		int size=frame_indices.size();
		int max_frame_index=frame_indices.get(size-1);
		
		int fps=WindowCommonInfo.GetFPS();
		float max_time=(float)max_frame_index/fps;
		
		return max_time;
	}
	
	public AnimationBlendInfo GetBlendInfo(float time) {
		AnimationBlendInfo abi=new AnimationBlendInfo();
		
		if(frames_map.size()==0) {
			logger.warn("No frames registered.");
			return abi;
		}
		
		int fps=WindowCommonInfo.GetFPS();
		int frame_index=Math.round(time*fps);
		
		Set<Integer> frame_indices_set=frames_map.keySet();
		List<Integer> frame_indices=new ArrayList<>(frame_indices_set);
		
		int size=frame_indices.size();
		int min_frame_index=frame_indices.get(0);
		int max_frame_index=frame_indices.get(size-1);
		
		if(frame_index<=min_frame_index) {
			int frame_handle=frames_map.get(min_frame_index);
			abi.SetFrame1Handle(frame_handle);
			
			return abi;
		}
		else if(frame_index>=max_frame_index) {
			int frame_handle=frames_map.get(max_frame_index);
			abi.SetFrame1Handle(frame_handle);
			
			return abi;
		}
		
		for(int i=0;i<size-1;i++) {
			int f1=frame_indices.get(i);
			int f2=frame_indices.get(i+1);
			
			if(f1<=frame_index&&frame_index<f2) {
				int frame1_handle=frames_map.get(f1);
				int frame2_handle=frames_map.get(f2);
				float blend_ratio=(float)(frame_index-f1)/(f2-f1);
				
				abi.SetFrame1Handle(frame1_handle);
				abi.SetFrame2Handle(frame2_handle);
				abi.SetBlendRatio(blend_ratio);
				
				break;
			}
		}
		
		return abi;
	}
}
