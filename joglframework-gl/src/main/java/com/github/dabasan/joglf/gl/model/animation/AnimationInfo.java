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
 * 
 * @author Daba
 *
 */
public class AnimationInfo {
	private final Logger logger = LoggerFactory.getLogger(AnimationInfo.class);

	// (frame_index, frame_handle)
	private final Map<Integer, Integer> frames_map;

	public AnimationInfo() {
		frames_map = new TreeMap<>();
	}

	public void AppendFrame(float time, int frame_handle) {
		final int fps = WindowCommonInfo.GetFPS();
		final int frame_index = Math.round(fps * time);

		frames_map.put(frame_index, frame_handle);
	}

	public float GetMaxTime() {
		if (frames_map.size() == 0) {
			logger.warn("No frames registered.");
			return 0.0f;
		}

		final Set<Integer> frame_indices_set = frames_map.keySet();
		final List<Integer> frame_indices = new ArrayList<>(frame_indices_set);

		final int size = frame_indices.size();
		final int max_frame_index = frame_indices.get(size - 1);

		final int fps = WindowCommonInfo.GetFPS();
		final float max_time = (float) max_frame_index / fps;

		return max_time;
	}

	public AnimationBlendInfo GetBlendInfo(float time) {
		final AnimationBlendInfo abi = new AnimationBlendInfo();

		if (frames_map.size() == 0) {
			logger.warn("No frames registered.");
			return abi;
		}

		final int fps = WindowCommonInfo.GetFPS();
		final int frame_index = Math.round(time * fps);

		final Set<Integer> frame_indices_set = frames_map.keySet();
		final List<Integer> frame_indices = new ArrayList<>(frame_indices_set);

		final int size = frame_indices.size();
		final int min_frame_index = frame_indices.get(0);
		final int max_frame_index = frame_indices.get(size - 1);

		if (frame_index <= min_frame_index) {
			final int frame_handle = frames_map.get(min_frame_index);
			abi.SetFrame1Handle(frame_handle);

			return abi;
		} else if (frame_index >= max_frame_index) {
			final int frame_handle = frames_map.get(max_frame_index);
			abi.SetFrame1Handle(frame_handle);

			return abi;
		}

		for (int i = 0; i < size - 1; i++) {
			final int f1 = frame_indices.get(i);
			final int f2 = frame_indices.get(i + 1);

			if (f1 <= frame_index && frame_index < f2) {
				final int frame1_handle = frames_map.get(f1);
				final int frame2_handle = frames_map.get(f2);
				final float blend_ratio = (float) (frame_index - f1)
						/ (f2 - f1);

				abi.SetFrame1Handle(frame1_handle);
				abi.SetFrame2Handle(frame2_handle);
				abi.SetBlendRatio(blend_ratio);

				break;
			}
		}

		return abi;
	}
}
