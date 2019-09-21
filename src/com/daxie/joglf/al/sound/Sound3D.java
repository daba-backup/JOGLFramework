package com.daxie.joglf.al.sound;

import java.util.HashMap;
import java.util.Map;

import com.daxie.basis.vector.Vector;
import com.daxie.basis.vector.VectorFunctions;
import com.daxie.joglf.al.buffer.SoundBuffer;
import com.daxie.joglf.al.loader.WAVLoader;
import com.daxie.joglf.al.wrapper.ALWrapper;
import com.daxie.log.LogFile;
import com.daxie.tool.FilenameFunctions;
import com.jogamp.openal.AL;

/**
 * Manages sounds.
 * @author Daba
 *
 */
public class Sound3D {
	private static int count=0;
	private static Map<Integer, SoundMgr> sounds_map=new HashMap<>();
	
	private static Vector listener_position=VectorFunctions.VGet(0.0f, 0.0f, 0.0f);
	private static Vector listener_velocity=VectorFunctions.VGet(0.0f, 0.0f, 0.0f);
	private static Vector listener_target=VectorFunctions.VGet(0.0f, 0.0f, 0.0f);
	private static Vector listener_up=VectorFunctions.VGet(0.0f, 1.0f, 0.0f);
	
	public static void SetDistanceModel(int model) {
		ALWrapper.alDistanceModel(model);
	}
	
	public static void SetupListenerProperties() {
		ALWrapper.alListener3f(AL.AL_POSITION, listener_position.GetX(), listener_position.GetY(), listener_position.GetZ());
		ALWrapper.alListener3f(AL.AL_VELOCITY, listener_velocity.GetX(), listener_velocity.GetY(), listener_velocity.GetZ());
		
		float[] orientation=new float[] {listener_target.GetX(),listener_target.GetY(),listener_target.GetZ(),listener_up.GetX(),listener_up.GetY(),listener_up.GetZ()};
		ALWrapper.alListenerfv(AL.AL_ORIENTATION, orientation, 0);
	}
	
	public static void SetListenerPosition(Vector position) {
		listener_position=position;
		
		ALWrapper.alListener3f(AL.AL_POSITION, position.GetX(), position.GetY(), position.GetZ());
	}
	public static void SetListenerVelocity(Vector velocity) {
		listener_velocity=velocity;
		
		ALWrapper.alListener3f(AL.AL_VELOCITY, velocity.GetX(), velocity.GetY(), velocity.GetZ());
	}
	public static void SetListenerOrientation(Vector target,Vector up) {
		listener_target=target;
		listener_up=up;
		
		float[] orientation=new float[] {target.GetX(),target.GetY(),target.GetZ(),up.GetX(),up.GetY(),up.GetZ()};
		
		ALWrapper.alListenerfv(AL.AL_ORIENTATION, orientation, 0);
	}
	
	public static int LoadSound(String sound_filename) {
		String extension=FilenameFunctions.GetFileExtension(sound_filename);
		
		int sound_handle=count;
		if(extension.equals("wav")||extension.equals("WAV")) {
			SoundBuffer sound_buffer=WAVLoader.LoadWAV(sound_filename);
			SoundMgr sound=new SoundMgr(sound_buffer);
			
			sounds_map.put(sound_handle, sound);
		}
		else {
			LogFile.WriteWarn("[Sound3D-LoadSound] Unsupported sound format. extension:"+extension, true);
			return -1;
		}
		
		count++;
		
		return sound_handle;
	}
	public static int DeleteSound(int sound_handle) {
		if(sounds_map.containsKey(sound_handle)==false) {
			LogFile.WriteWarn("[Sound3D-DeleteSound] No such sound. sound_handle:"+sound_handle, true);
			return -1;
		}
		
		SoundMgr sound=sounds_map.get(sound_handle);
		sound.DeleteBufferAndSource();
		
		sounds_map.remove(sound_handle);
		
		return 0;
	}
	public static void DeleteAllSounds() {
		for(SoundMgr sound:sounds_map.values()) {
			sound.DeleteBufferAndSource();
		}
		
		sounds_map.clear();
		count=0;
	}
	
	public static int SetSoundSourcePosition(int sound_handle,Vector position) {
		if(sounds_map.containsKey(sound_handle)==false) {
			LogFile.WriteWarn("[Sound3D-SetSoundSourcePosition] No such sound. sound_handle:"+sound_handle, true);
			return -1;
		}
		
		SoundMgr sound=sounds_map.get(sound_handle);
		sound.SetSourcePosition(position);
		
		return 0;
	}
	public static int SetSoundSourceVelocity(int sound_handle,Vector velocity) {
		if(sounds_map.containsKey(sound_handle)==false) {
			LogFile.WriteWarn("[Sound3D-SetSoundSourceVelocity] No such sound. sound_handle:"+sound_handle, true);
			return -1;
		}
		
		SoundMgr sound=sounds_map.get(sound_handle);
		sound.SetSourceVelocity(velocity);
		
		return 0;
	}
	public static int SetSoundLoopFlag(int sound_handle,boolean loop_flag) {
		if(sounds_map.containsKey(sound_handle)==false) {
			LogFile.WriteWarn("[Sound3D-SetSoundLoopFlag] No such sound. sound_handle:"+sound_handle, true);
			return -1;
		}
		
		SoundMgr sound=sounds_map.get(sound_handle);
		sound.SetLoopFlag(loop_flag);
		
		return 0;
	}
	public static int SetSoundReferenceDistance(int sound_handle,float reference_distance) {
		if(sounds_map.containsKey(sound_handle)==false) {
			LogFile.WriteWarn("[Sound3D-SetSoundReferenceDistance] No such sound. sound_handle:"+sound_handle, true);
			return -1;
		}
		
		SoundMgr sound=sounds_map.get(sound_handle);
		sound.SetReferenceDistance(reference_distance);
		
		return 0;
	}
	public static int SetSoundMaxDistance(int sound_handle,float max_distance) {
		if(sounds_map.containsKey(sound_handle)==false) {
			LogFile.WriteWarn("[Sound3D-SetSoundMaxDistance] No such sound. sound_handle:"+sound_handle, true);
			return -1;
		}
		
		SoundMgr sound=sounds_map.get(sound_handle);
		sound.SetMaxDistance(max_distance);
		
		return 0;
	}
	
	public static int PlaySound(int sound_handle) {
		if(sounds_map.containsKey(sound_handle)==false) {
			LogFile.WriteWarn("[Sound3D-PlaySound] No such sound. sound_handle:"+sound_handle, true);
			return -1;
		}
		
		SoundMgr sound=sounds_map.get(sound_handle);
		sound.Play();
		
		return 0;
	}
	public static int StopSound(int sound_handle) {
		if(sounds_map.containsKey(sound_handle)==false) {
			LogFile.WriteWarn("[Sound3D-StopSound] No such sound. sound_handle:"+sound_handle, true);
			return -1;
		}
		
		SoundMgr sound=sounds_map.get(sound_handle);
		sound.Stop();
		
		return 0;
	}
	public static int PauseSound(int sound_handle) {
		if(sounds_map.containsKey(sound_handle)==false) {
			LogFile.WriteWarn("[Sound3D-PauseSound] No such sound. sound_handle:"+sound_handle, true);
			return -1;
		}
		
		SoundMgr sound=sounds_map.get(sound_handle);
		sound.Pause();
		
		return 0;
	}
	
	public static boolean IsSoundPlaying(int sound_handle) {
		if(sounds_map.containsKey(sound_handle)==false) {
			LogFile.WriteWarn("[Sound3D-IsSoundPlaying] No such sound. sound_handle:"+sound_handle, true);
			return false;
		}
		
		SoundMgr sound=sounds_map.get(sound_handle);
		
		return sound.IsPlaying();
	}
}
