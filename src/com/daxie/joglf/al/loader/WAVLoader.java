package com.daxie.joglf.al.loader;

import java.nio.ByteBuffer;

import com.daxie.joglf.al.ALWrapper;
import com.daxie.joglf.al.buffer.SoundBuffer;

/**
 * WAV loader
 * @author Daba
 *
 */
public class WAVLoader {
	public static SoundBuffer LoadWAV(String wav_filename) {
		int[] format=new int[1];
		int[] size=new int[1];
		ByteBuffer[] data=new ByteBuffer[1];
		int[] freq=new int[1];
		int[] loop=new int[1];
		
		ALWrapper.alutLoadWAVFile(wav_filename,format,data,size,freq,loop);
		
		SoundBuffer sound_buffer=new SoundBuffer();
		sound_buffer.SetFormat(format[0]);
		sound_buffer.SetSize(size[0]);
		sound_buffer.SetData(data[0]);
		sound_buffer.SetFreq(freq[0]);
		sound_buffer.SetLoop(loop[0]);
		
		return sound_buffer;
	}
}
