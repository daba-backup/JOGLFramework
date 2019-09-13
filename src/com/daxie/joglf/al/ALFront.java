package com.daxie.joglf.al;

import com.daxie.joglf.al.sound.Sound3D;
import com.daxie.joglf.log.LogFile;
import com.jogamp.openal.AL;
import com.jogamp.openal.util.ALut;

/**
 * Offers general functions relating to sound.
 * @author Daba
 *
 */
public class ALFront {
	public static void Initialize() {
		ALut.alutInit();
		Sound3D.SetupListenerProperties();
		
		ALWrapper.alDistanceModel(AL.AL_INVERSE_DISTANCE);
		
		LogFile.WriteInfo("[ALFront-Initialize] Initialized.", true);
	}
	public static void Dispose() {
		ALut.alutExit();
		
		LogFile.WriteInfo("[ALFront-Dispose] Disposed.", true);
	}
}
