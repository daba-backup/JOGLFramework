package com.daxie.joglf.al.front;

import com.daxie.joglf.al.sound.Sound3D;
import com.daxie.joglf.al.wrapper.ALWrapper;
import com.daxie.log.LogFile;
import com.jogamp.openal.AL;
import com.jogamp.openal.util.ALut;

/**
 * Provides general functions relating to the audio library (AL).
 * @author Daba
 *
 */
public class ALFront {
	private static boolean no_use_alut_flag=false;
	
	/**
	 * Sets the flag to disable ALUT.
	 * @param a_no_use_alut_flag Flag
	 */
	public static void SetNoUseAlutFlag(boolean a_no_use_alut_flag) {
		no_use_alut_flag=a_no_use_alut_flag;
	}
	/**
	 * Returns the flag to disable ALUT.
	 * @return Flag
	 */
	public static boolean GetNoUseAlutFlag() {
		return no_use_alut_flag;
	}
	
	public static void Initialize() {
		if(no_use_alut_flag==false)ALut.alutInit();
		
		Sound3D.SetupListenerProperties();
		ALWrapper.alDistanceModel(AL.AL_INVERSE_DISTANCE);
		
		LogFile.WriteInfo("[ALFront-Initialize] Initialized.", true);
	}
	public static void Dispose() {
		if(no_use_alut_flag==false)ALut.alutExit();
		
		LogFile.WriteInfo("[ALFront-Dispose] Disposed.", true);
	}
}
