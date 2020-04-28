package com.github.dabasan.joglf.al.loader;

import java.io.File;
import java.nio.ByteBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.dabasan.joglf.al.buffer.SoundBuffer;
import com.github.dabasan.joglf.al.front.ALFront;
import com.github.dabasan.joglf.al.wrapper.ALWrapper;

/**
 * WAV loader
 * @author Daba
 *
 */
public class WAVLoader {
	private static Logger logger=LoggerFactory.getLogger(WAVLoader.class);
	
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
			logger.error("File does not exist. filename={}",wav_filename);
			return sound_buffer;
		}
		
		if(ALFront.GetNoUseAlutFlag()==true) {
			logger.error("ALUT is disabled.");
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
