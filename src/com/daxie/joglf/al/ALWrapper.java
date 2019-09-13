package com.daxie.joglf.al;

import java.nio.Buffer;
import java.nio.ByteBuffer;

import com.daxie.joglf.log.LogFile;
import com.jogamp.openal.AL;
import com.jogamp.openal.ALFactory;
import com.jogamp.openal.util.ALut;

public class ALWrapper {
	public static void alDistanceModel(int arg0) {
		AL al=ALFactory.getAL();
		al.alDistanceModel(arg0);
		
		int code=al.alGetError();
		if(code!=AL.AL_NO_ERROR) {
			LogFile.WriteError("[ALWrapper-alDistanceModel] code:"+code, true);
		}
	}
	public static void alGenBuffers(int arg0,int[] arg1,int arg2) {
		AL al=ALFactory.getAL();
		al.alGenBuffers(arg0, arg1,arg2);
		
		int code=al.alGetError();
		if(code!=AL.AL_NO_ERROR) {
			LogFile.WriteError("[ALWrapper-alGenBuffers] code:"+code, true);
		}
	}
	public static void alutLoadWAVFile(String arg0,int[] arg1,ByteBuffer[] arg2,int[] arg3,int[] arg4,int[] arg5) {
		ALut.alutLoadWAVFile(arg0, arg1, arg2, arg3, arg4, arg5);
	}
	public static void alBufferData(int arg0,int arg1,Buffer arg2,int arg3,int arg4) {
		AL al=ALFactory.getAL();
		al.alBufferData(arg0, arg1, arg2, arg3, arg4);
		
		int code=al.alGetError();
		if(code!=AL.AL_NO_ERROR) {
			LogFile.WriteError("[ALWrapper-alBufferData] code:"+code, true);
		}
	}
	public static void alGenSources(int arg0,int[] arg1,int arg2) {
		AL al=ALFactory.getAL();
		al.alGenSources(arg0, arg1, arg2);
		
		int code=al.alGetError();
		if(code!=AL.AL_NO_ERROR) {
			LogFile.WriteError("[ALWrapper-alGenSources] code:"+code, true);
		}
	}
	public static void alSourcei(int arg0,int arg1,int arg2) {
		AL al=ALFactory.getAL();
		al.alSourcei(arg0, arg1, arg2);
		
		int code=al.alGetError();
		if(code!=AL.AL_NO_ERROR) {
			LogFile.WriteError("[ALWrapper-alSourcei] code:"+code, true);
		}
	}
	public static void alSourcef(int arg0,int arg1,float arg2) {
		AL al=ALFactory.getAL();
		al.alSourcef(arg0, arg1, arg2);
		
		int code=al.alGetError();
		if(code!=AL.AL_NO_ERROR) {
			LogFile.WriteError("[ALWrapper-alSourcef] code:"+code, true);
		}
	}
	public static void alSource3f(int arg0,int arg1,float arg2,float arg3,float arg4) {
		AL al=ALFactory.getAL();
		al.alSource3f(arg0, arg1, arg2, arg3, arg4);
		
		int code=al.alGetError();
		if(code!=AL.AL_NO_ERROR) {
			LogFile.WriteError("[ALWrapper-alSource3f] code:"+code, true);
		}
	}
	public static void alGetSourceiv(int arg0,int arg1,int[] arg2,int arg3) {
		AL al=ALFactory.getAL();
		al.alGetSourceiv(arg0, arg1, arg2, arg3);
		
		int code=al.alGetError();
		if(code!=AL.AL_NO_ERROR) {
			LogFile.WriteError("[ALWrapper-alGetSourceiv] code:"+code, true);
		}
	}
	public static void alListener3f(int arg0,float arg1,float arg2,float arg3) {
		AL al=ALFactory.getAL();
		al.alListener3f(arg0, arg1, arg2, arg3);
		
		int code=al.alGetError();
		if(code!=AL.AL_NO_ERROR) {
			LogFile.WriteError("[ALWrapper-alListener3f] code:"+code, true);
		}
	}
	public static void alListenerfv(int arg0,float[] arg1,int arg2) {
		AL al=ALFactory.getAL();
		al.alListenerfv(arg0, arg1, arg2);
		
		int code=al.alGetError();
		if(code!=AL.AL_NO_ERROR) {
			LogFile.WriteError("[ALWrapper-alListenerfv] code:"+code, true);
		}
	}
	public static void alSourcePlay(int arg0) {
		AL al=ALFactory.getAL();
		al.alSourcePlay(arg0);
		
		int code=al.alGetError();
		if(code!=AL.AL_NO_ERROR) {
			LogFile.WriteError("[ALWrapper-alSourcePlay] code:"+code, true);
		}
	}
	public static void alSourceStop(int arg0) {
		AL al=ALFactory.getAL();
		al.alSourceStop(arg0);
		
		int code=al.alGetError();
		if(code!=AL.AL_NO_ERROR) {
			LogFile.WriteError("[ALWrapper-alSourceStop] code:"+code, true);
		}
	}
	public static void alSourcePause(int arg0) {
		AL al=ALFactory.getAL();
		al.alSourcePause(arg0);
		
		int code=al.alGetError();
		if(code!=AL.AL_NO_ERROR) {
			LogFile.WriteError("[ALWrapper-alSourcePause] code:"+code, true);
		}
	}
	public static void alDeleteBuffers(int arg0,int[] arg1,int arg2) {
		AL al=ALFactory.getAL();
		al.alDeleteBuffers(arg0, arg1, arg2);
		
		int code=al.alGetError();
		if(code!=AL.AL_NO_ERROR) {
			LogFile.WriteError("[ALWrapper-alDeleteBuffers] code:"+code, true);
		}
	}
	public static void alDeleteSources(int arg0,int[] arg1,int arg2) {
		AL al=ALFactory.getAL();
		al.alDeleteSources(arg0, arg1, arg2);
		
		int code=al.alGetError();
		if(code!=AL.AL_NO_ERROR) {
			LogFile.WriteError("[ALWrapper-alDeleteSources] code:"+code, true);
		}
	}
}
