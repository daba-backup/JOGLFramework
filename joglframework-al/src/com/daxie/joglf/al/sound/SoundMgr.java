package com.daxie.joglf.al.sound;

import java.nio.ByteBuffer;

import com.daxie.basis.vector.Vector;
import com.daxie.basis.vector.VectorFunctions;
import com.daxie.joglf.al.buffer.SoundBuffer;
import com.daxie.joglf.al.wrapper.ALWrapper;
import com.jogamp.openal.AL;

/**
 * Sound manager
 * @author Daba
 *
 */
class SoundMgr {
	private int buffer;
	private int source;
	
	private Vector source_position;
	private Vector source_velocity;
	
	private boolean loop_flag;
	
	private float reference_distance;
	private float max_distance;
	
	public SoundMgr(SoundBuffer sound_buffer) {
		source_position=VectorFunctions.VGet(0.0f, 0.0f, 0.0f);
		source_velocity=VectorFunctions.VGet(0.0f, 0.0f, 0.0f);
		
		loop_flag=false;
		
		reference_distance=10.0f;
		max_distance=50.0f;
		
		this.GenerateBufferAndSource(sound_buffer);
	}
	private void GenerateBufferAndSource(SoundBuffer sound_buffer) {
		int[] buffers=new int[1];
		int[] sources=new int[1];
		
		int format=sound_buffer.GetFormat();
		int size=sound_buffer.GetSize();
		ByteBuffer data=sound_buffer.GetData();
		int freq=sound_buffer.GetFreq();
		
		ALWrapper.alGenBuffers(1, buffers, 0);
		ALWrapper.alBufferData(buffers[0], format, data, size, freq);
		
		ALWrapper.alGenSources(1, sources, 0);
		ALWrapper.alSourcei(sources[0], AL.AL_BUFFER, buffers[0]);
		ALWrapper.alSourcef(sources[0], AL.AL_PITCH, 1.0f);
		ALWrapper.alSourcef(sources[0], AL.AL_GAIN, 1.0f);
		
		ALWrapper.alSource3f(sources[0], AL.AL_POSITION, 
				source_position.GetX(), source_position.GetY(), source_position.GetZ());
		ALWrapper.alSource3f(sources[0], AL.AL_VELOCITY, 
				source_velocity.GetX(), source_velocity.GetY(), source_velocity.GetZ());
		ALWrapper.alSourcef(sources[0], AL.AL_REFERENCE_DISTANCE, reference_distance);
		ALWrapper.alSourcef(sources[0], AL.AL_MAX_DISTANCE, max_distance);
		
		if(loop_flag==false) {
			ALWrapper.alSourcei(sources[0], AL.AL_LOOPING, AL.AL_FALSE);
		}
		else {
			ALWrapper.alSourcei(sources[0], AL.AL_LOOPING, AL.AL_TRUE);
		}
		
		buffer=buffers[0];
		source=sources[0];
	}
	
	public void DeleteBufferAndSource() {
		int[] buffers=new int[] {buffer};
		int[] sources=new int[] {source};
		
		ALWrapper.alDeleteBuffers(1, buffers, 0);
		ALWrapper.alDeleteSources(1, sources, 0);
	}
	
	public void SetSourcePosition(Vector source_position) {
		this.source_position=source_position;
		
		ALWrapper.alSource3f(source, AL.AL_POSITION, 
				source_position.GetX(), source_position.GetY(), source_position.GetZ());
	}
	public void SetSourceVelocity(Vector source_velocity) {
		this.source_velocity=source_velocity;
		
		ALWrapper.alSource3f(source, AL.AL_VELOCITY, 
				source_velocity.GetX(), source_velocity.GetY(), source_velocity.GetZ());
	}
	public void SetLoopFlag(boolean loop_flag) {
		this.loop_flag=loop_flag;
		
		if(loop_flag==false) {
			ALWrapper.alSourcei(source, AL.AL_LOOPING, AL.AL_FALSE);
		}
		else {
			ALWrapper.alSourcei(source, AL.AL_LOOPING, AL.AL_TRUE);
		}
	}
	public void SetReferenceDistance(float reference_distance) {
		this.reference_distance=reference_distance;
		
		ALWrapper.alSourcef(source, AL.AL_REFERENCE_DISTANCE, reference_distance);
	}
	public void SetMaxDistance(float max_distance) {
		this.max_distance=max_distance;
		
		ALWrapper.alSourcef(source, AL.AL_MAX_DISTANCE, max_distance);
	}
	
	public void Play() {
		ALWrapper.alSourcePlay(source);
	}
	public void Stop() {
		ALWrapper.alSourceStop(source);
	}
	public void Pause() {
		ALWrapper.alSourcePause(source);
	}
	
	public boolean IsPlaying() {
		int[] state=new int[1];
		ALWrapper.alGetSourceiv(source, AL.AL_SOURCE_STATE, state, 0);
		
		boolean ret;
		if(state[0]==AL.AL_PLAYING)ret=true;
		else ret=false;
		
		return ret;
	}
}
