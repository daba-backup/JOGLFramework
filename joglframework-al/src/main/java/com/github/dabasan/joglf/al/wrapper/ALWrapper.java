package com.github.dabasan.joglf.al.wrapper;

import java.nio.Buffer;
import java.nio.ByteBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jogamp.openal.AL;
import com.jogamp.openal.ALConstants;
import com.jogamp.openal.ALFactory;
import com.jogamp.openal.util.ALut;

/**
 * Wrapper functions for OpenAL
 * 
 * @author Daba
 *
 */
public class ALWrapper {
	private static Logger logger = LoggerFactory.getLogger(ALWrapper.class);

	@Deprecated
	// This method should be removed in the next major update as ALUT is not
	// part of OpenAL.
	public static void alutLoadWAVFile(String arg0, int[] arg1, ByteBuffer[] arg2, int[] arg3,
			int[] arg4, int[] arg5) {
		ALut.alutLoadWAVFile(arg0, arg1, arg2, arg3, arg4, arg5);
	}

	public static void alDistanceModel(int arg0) {
		final AL al = ALFactory.getAL();
		al.alDistanceModel(arg0);

		final int code = al.alGetError();
		if (code != ALConstants.AL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void alGenBuffers(int arg0, int[] arg1, int arg2) {
		final AL al = ALFactory.getAL();
		al.alGenBuffers(arg0, arg1, arg2);

		final int code = al.alGetError();
		if (code != ALConstants.AL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void alBufferData(int arg0, int arg1, Buffer arg2, int arg3, int arg4) {
		final AL al = ALFactory.getAL();
		al.alBufferData(arg0, arg1, arg2, arg3, arg4);

		final int code = al.alGetError();
		if (code != ALConstants.AL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void alGenSources(int arg0, int[] arg1, int arg2) {
		final AL al = ALFactory.getAL();
		al.alGenSources(arg0, arg1, arg2);

		final int code = al.alGetError();
		if (code != ALConstants.AL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void alSourcei(int arg0, int arg1, int arg2) {
		final AL al = ALFactory.getAL();
		al.alSourcei(arg0, arg1, arg2);

		final int code = al.alGetError();
		if (code != ALConstants.AL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void alSourcef(int arg0, int arg1, float arg2) {
		final AL al = ALFactory.getAL();
		al.alSourcef(arg0, arg1, arg2);

		final int code = al.alGetError();
		if (code != ALConstants.AL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void alSource3f(int arg0, int arg1, float arg2, float arg3, float arg4) {
		final AL al = ALFactory.getAL();
		al.alSource3f(arg0, arg1, arg2, arg3, arg4);

		final int code = al.alGetError();
		if (code != ALConstants.AL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void alGetSourceiv(int arg0, int arg1, int[] arg2, int arg3) {
		final AL al = ALFactory.getAL();
		al.alGetSourceiv(arg0, arg1, arg2, arg3);

		final int code = al.alGetError();
		if (code != ALConstants.AL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void alListener3f(int arg0, float arg1, float arg2, float arg3) {
		final AL al = ALFactory.getAL();
		al.alListener3f(arg0, arg1, arg2, arg3);

		final int code = al.alGetError();
		if (code != ALConstants.AL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void alListenerfv(int arg0, float[] arg1, int arg2) {
		final AL al = ALFactory.getAL();
		al.alListenerfv(arg0, arg1, arg2);

		final int code = al.alGetError();
		if (code != ALConstants.AL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void alSourcePlay(int arg0) {
		final AL al = ALFactory.getAL();
		al.alSourcePlay(arg0);

		final int code = al.alGetError();
		if (code != ALConstants.AL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void alSourceStop(int arg0) {
		final AL al = ALFactory.getAL();
		al.alSourceStop(arg0);

		final int code = al.alGetError();
		if (code != ALConstants.AL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void alSourcePause(int arg0) {
		final AL al = ALFactory.getAL();
		al.alSourcePause(arg0);

		final int code = al.alGetError();
		if (code != ALConstants.AL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void alDeleteBuffers(int arg0, int[] arg1, int arg2) {
		final AL al = ALFactory.getAL();
		al.alDeleteBuffers(arg0, arg1, arg2);

		final int code = al.alGetError();
		if (code != ALConstants.AL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
	public static void alDeleteSources(int arg0, int[] arg1, int arg2) {
		final AL al = ALFactory.getAL();
		al.alDeleteSources(arg0, arg1, arg2);

		final int code = al.alGetError();
		if (code != ALConstants.AL_NO_ERROR) {
			logger.trace("code={}", code);
		}
	}
}
