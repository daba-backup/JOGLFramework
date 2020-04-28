package com.github.dabasan.joglf.al.front;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.dabasan.joglf.al.sound.Sound3D;
import com.github.dabasan.joglf.al.wrapper.ALWrapper;
import com.jogamp.openal.AL;
import com.jogamp.openal.util.ALut;

/**
 * Provides general functions relating to the audio library (AL).
 * @author Daba
 *
 */
public class ALFront {
	private static Logger logger=LoggerFactory.getLogger(ALFront.class);
	
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
		
		logger.info("Initialized.");
	}
	public static void Dispose() {
		if(no_use_alut_flag==false)ALut.alutExit();
		
		logger.info("Disposed.");
	}
}
