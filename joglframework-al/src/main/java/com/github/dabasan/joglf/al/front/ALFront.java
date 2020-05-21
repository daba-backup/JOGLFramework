package com.github.dabasan.joglf.al.front;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.dabasan.joglf.al.sound.Sound3DFunctions;
import com.github.dabasan.joglf.al.wrapper.ALWrapper;
import com.jogamp.openal.ALConstants;
import com.jogamp.openal.util.ALut;

/**
 * Provides general functions relating to the audio library (AL).
 * 
 * @author Daba
 *
 */
public class ALFront {
	private static Logger logger = LoggerFactory.getLogger(ALFront.class);

	/**
	 * Initializes ALut and sets some default values.<br>
	 * ALut.alutInit() call inside this method may throw ALException at runtime.
	 */
	public static void Initialize() {
		ALut.alutInit();

		Sound3DFunctions.SetupListenerProperties();
		ALWrapper.alDistanceModel(ALConstants.AL_INVERSE_DISTANCE);

		logger.info("ALFront initialized.");
	}
	/**
	 * Disposes ALut.
	 */
	public static void Dispose() {
		ALut.alutExit();

		logger.info("ALFront disposed.");
	}
}
