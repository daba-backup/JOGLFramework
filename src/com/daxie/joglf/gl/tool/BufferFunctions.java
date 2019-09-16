package com.daxie.joglf.gl.tool;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import com.daxie.basis.coloru8.ColorU8;
import com.daxie.basis.matrix.Matrix;
import com.daxie.basis.vector.Vector;
import com.jogamp.common.nio.Buffers;

/**
 * Offers methods to handle buffers.
 * @author Daba
 *
 */
public class BufferFunctions {
	public static String GetStringFromByteBuffer(ByteBuffer b) {
		byte[] byte_array=new byte[b.remaining()];
		b.get(byte_array);
		
		b.flip();
		
		return new String(byte_array,0,byte_array.length-1);
	}
	
	public static IntBuffer CopyIntBuffer(IntBuffer b) {
		IntBuffer copied=Buffers.newDirectIntBuffer(b.capacity());
		
		int cap=b.capacity();
		for(int i=0;i<cap;i++) {
			copied.put(b.get(i));
		}
		copied.flip();
		
		return copied;
	}
	public static FloatBuffer CopyFloatBuffer(FloatBuffer b) {
		FloatBuffer copied=Buffers.newDirectFloatBuffer(b.capacity());
		
		int cap=b.capacity();
		for(int i=0;i<cap;i++) {
			copied.put(b.get(i));
		}
		copied.flip();
		
		return copied;
	}
	
	public static FloatBuffer MakeFloatBufferFromColorU8(ColorU8 c) {
		FloatBuffer buffer=Buffers.newDirectFloatBuffer(4);
		
		buffer.put(c.GetR());
		buffer.put(c.GetG());
		buffer.put(c.GetB());
		buffer.put(c.GetA());
		
		buffer.flip();
		
		return buffer;
	}
	public static FloatBuffer MakeFloatBufferFromVector(Vector v) {
		FloatBuffer buffer=Buffers.newDirectFloatBuffer(3);
		
		buffer.put(v.GetX());
		buffer.put(v.GetY());
		buffer.put(v.GetZ());
		
		buffer.flip();
		
		return buffer;
	}
	public static FloatBuffer MakeFloatBufferFromMatrix(Matrix m) {
		FloatBuffer buffer=Buffers.newDirectFloatBuffer(16);
		
		for(int i=0;i<4;i++) {
			for(int j=0;j<4;j++) {
				buffer.put(m.GetValue(i, j));
			}
		}
		
		buffer.flip();
		
		return buffer;
	}
}
