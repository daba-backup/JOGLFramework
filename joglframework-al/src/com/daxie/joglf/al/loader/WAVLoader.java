package com.daxie.joglf.al.loader;

import java.io.File;
import java.nio.ByteBuffer;

import com.daxie.joglf.al.buffer.SoundBuffer;
import com.daxie.joglf.al.front.ALFront;
import com.daxie.joglf.al.wrapper.ALWrapper;
import com.daxie.log.LogWriter;

/**
 * WAV loader
 * @author Daba
 *
 */
public class WAVLoader {
	/**
	 * Loads a WAV file.<br>
	 * This method depends on ALUT.<br>
	 * Therefore, this method fails if ALUT is disabled.
	 * @param wav_filename Filename of a WAV file
	 * @return SoundBuffer
	 */
	public static SoundBuffer LoadWAV(String wav_filename) {
		SoundBuffer sound_buffer=new SoundBuffer();
		
		File file=new File(wav_filename);
		if(file.exists()==false) {
			LogWriter.WriteWarn("[WAVLoader-LoadWAV] File does not exist. filename:"+wav_filename, true);
			return sound_buffer;
		}
		
		if(ALFront.GetNoUseAlutFlag()==true) {
			LogWriter.WriteWarn("[WAVLoader-LoadWAV] ALUT is disabled.", true);
			return sound_buffer;
		}
		
		int[] format=new int[1];
		int[] size=new int[1];
		ByteBuffer[] data=new ByteBuffer[1];
		int[] freq=new int[1];
		int[] loop=new int[1];
		
		ALWrapper.alutLoadWAVFile(wav_filename,format,data,size,freq,loop);
		
		sound_buffer.SetFormat(format[0]);
		sound_buffer.SetSize(size[0]);
		sound_buffer.SetData(data[0]);
		sound_buffer.SetFreq(freq[0]);
		sound_buffer.SetLoop(loop[0]);
		
		return sound_buffer;
	}
}
