package com.daxie.joglf.al.buffer;

import java.nio.ByteBuffer;

/**
 * Sound buffer
 * @author Daba
 *
 */
public class SoundBuffer {
	private int format;
	private int size;
	private ByteBuffer data;
	private int freq;
	private int loop;
	
	public SoundBuffer() {
		format=0;
		size=0;
		freq=0;
		loop=0;
	}
	
	public int GetFormat() {
		return format;
	}
	public int GetSize() {
		return size;
	}
	public ByteBuffer GetData() {
		return data;
	}
	public int GetFreq() {
		return freq;
	}
	public int GetLoop() {
		return loop;
	}
	public void SetFormat(int format) {
		this.format=format;
	}
	public void SetSize(int size) {
		this.size=size;
	}
	public void SetData(ByteBuffer data) {
		this.data=data;
	}
	public void SetFreq(int freq) {
		this.freq=freq;
	}
	public void SetLoop(int loop) {
		this.loop=loop;
	}
}
