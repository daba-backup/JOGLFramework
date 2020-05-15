package com.github.dabasan.joglf.al.loader;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.ByteBuffer;

import com.github.dabasan.joglf.al.buffer.SoundBuffer;
import com.jogamp.openal.util.ALut;

/**
 * WAV loader
 * 
 * @author Daba
 *
 */
public class WAVLoader {
	/**
	 * Loads a WAV file.<br>
	 * This method depends on ALUT.
	 * 
	 * @param wav_filename
	 *            Filename of the WAV file
	 * @return Sound buffer
	 * @throws FileNotFoundException
	 *             System cannot find the file specified.
	 */
	public static SoundBuffer LoadWAV(String wav_filename) throws FileNotFoundException {
		final SoundBuffer sound_buffer = new SoundBuffer();

		final File file = new File(wav_filename);
		if (file.exists() == false) {
			throw new FileNotFoundException(wav_filename);
		}

		final int[] format = new int[1];
		final int[] size = new int[1];
		final ByteBuffer[] data = new ByteBuffer[1];
		final int[] freq = new int[1];
		final int[] loop = new int[1];

		ALut.alutLoadWAVFile(wav_filename, format, data, size, freq, loop);

		sound_buffer.SetFormat(format[0]);
		sound_buffer.SetSize(size[0]);
		sound_buffer.SetData(data[0]);
		sound_buffer.SetFreq(freq[0]);
		sound_buffer.SetLoop(loop[0]);

		return sound_buffer;
	}
}
