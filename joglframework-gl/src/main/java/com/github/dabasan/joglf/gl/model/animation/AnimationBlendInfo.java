package com.github.dabasan.joglf.gl.model.animation;

/**
 * Blend information for animation
 * @author Daba
 *
 */
public class AnimationBlendInfo {
	private int frame1_handle;
	private int frame2_handle;
	private float blend_ratio;
	
	public AnimationBlendInfo() {
		frame1_handle=-1;
		frame2_handle=-1;
		blend_ratio=0.0f;
	}
	
	public int GetFrame1Handle() {
		return frame1_handle;
	}
	public int GetFrame2Handle() {
		return frame2_handle;
	}
	public float GetBlendRatio() {
		return blend_ratio;
	}
	
	public void SetFrame1Handle(int frame1_handle) {
		this.frame1_handle=frame1_handle;
	}
	public void SetFrame2Handle(int frame2_handle) {
		this.frame2_handle=frame2_handle;
	}
	public void SetBlendRatio(float blend_ratio) {
		this.blend_ratio=blend_ratio;
	}
}
