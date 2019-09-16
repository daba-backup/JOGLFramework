package com.daxie.tool;

import java.nio.ByteBuffer;

/**
 * Methods to handle binary representation.
 * @author Daba
 *
 */
public class ByteFunctions {
	/**
	 * Converts a 4-byte byte array to a float value.
	 * @param b A 4-byte big-endian byte array
	 * @return A float value
	 */
	public static float byte_to_float(byte[] b) {
		if(b.length!=4)return 0.0f;//If the size of b is inappropriate, then return 0.
		
		int s=0;//Sign
		int e=0;//Exponent
		float m=0;//Mantissa
		
		float res;
		
		int bit_op_res;
		int rhs;
		
		//Get the sign.
		bit_op_res=b[0]&0b10000000;
		if(bit_op_res==0)s=1;
		else s=-1;
		
		//Get the exponent.
		bit_op_res=b[1]&0b10000000;
		if(bit_op_res!=0)e+=1;
		
		rhs=0b00000001;
		for(int i=1;i<=7;i++) {
			bit_op_res=b[0]&rhs;
			if(bit_op_res!=0)e+=Math.pow(2, i);
			
			rhs=rhs<<1;
		}
		
		//Get the mantissa.
		rhs=0b01000000;
		for(int i=1;i<=7;i++) {
			bit_op_res=b[1]&rhs;
			if(bit_op_res!=0)m+=Math.pow(2, -i);
			
			rhs=rhs>>1;
		}
		rhs=0b10000000;
		for(int i=8;i<=15;i++) {
			bit_op_res=b[2]&rhs;
			if(bit_op_res!=0)m+=Math.pow(2, -i);
			
			rhs=rhs>>1;
		}
		rhs=0b10000000;
		for(int i=16;i<=23;i++) {
			bit_op_res=b[3]&rhs;
			if(bit_op_res!=0)m+=Math.pow(2, -i);
			
			rhs=rhs>>1;
		}
		
		if(e==0&&Math.abs(m)<1.0E-16)res=0.0f;
		else res=s*(1.0f+m)*(float)Math.pow(2, e-127);
		
		return res;
	}
	/**
	 * Converts a 4-byte byte array to a float value.
	 * @see byte_to_float(byte[])
	 * @param b A 4-byte little-endian byte array
	 * @return A float value
	 */
	public static float byte_to_float_le(byte[] b) {
		if(b.length!=4)return 0.0f;//If the size of b is inappropriate, then return 0.
		
		byte[] buffer=new byte[4];
		buffer[0]=b[3];
		buffer[1]=b[2];
		buffer[2]=b[1];
		buffer[3]=b[0];
		
		float res;
		res=ByteFunctions.byte_to_float(buffer);
		
		return res;
	}
	
	/**
	 * Converts a float value to a 4-byte byte array.
	 * @param f A float value
	 * @return A 4-byte big-endian byte array
	 */
	public static byte[] float_to_byte(float f) {
		return ByteBuffer.allocate(4).putFloat(f).array();
	}
	/**
	 * Converts a float value to a 4-byte byte array.
	 * @see float_to_byte(float)
	 * @param f A float value
	 * @return A 4-byte little-endian byte array
	 */
	public static byte[] float_to_byte_le(float f) {
		byte[] b=float_to_byte(f);
		
		byte[] res=new byte[4];
		res[0]=b[3];
		res[1]=b[2];
		res[2]=b[1];
		res[3]=b[0];
		
		return res;
	}
	
	/**
	 * Converts a 2-byte byte array to a short value.
	 * @param b A 2-byte byte array
	 * @return A short value
	 */
	public static short byte_to_short(byte[] b) {
		if(b.length!=2)return 0;
		
		short ret;
		
		//Check the MSB.
		byte temp=(byte)(b[0]>>7);
		
		//Positive value
		if((temp&0b00000001)==0) {
			ret=(short)((b[0]<<8)+Byte.toUnsignedInt(b[1]));
		}
		//Negative value
		else {
			byte[] bcopy=b.clone();
			
			bcopy[0]=(byte)~bcopy[0];
			bcopy[1]=(byte)~bcopy[1];
			bcopy[1]++;
			
			short abs=(short)((bcopy[0]<<8)+Byte.toUnsignedInt(bcopy[1]));
			ret=(short)(abs*(-1));
		}
		
		return ret;
	}
	/**
	 * Converts a 2-byte byte array to a short value.
	 * @see byte_to_short(byte[])
	 * @param b A 2-byte little-endian byte array
	 * @return A short value
	 */
	public static short byte_to_short_le(byte[] b) {
		if(b.length!=2)return 0;
		
		short ret;
		
		byte[] buffer=new byte[2];
		buffer[0]=b[1];
		buffer[1]=b[0];
		
		ret=byte_to_short(buffer);
		
		return ret;
	}
	
	/**
	 * Converts a short value to a 2-byte byte array.
	 * @param s A short value
	 * @return A 2-byte byte array
	 */
	public static byte[] short_to_byte(short s) {
		byte[] b=new byte[2];
		
		if(s>=0) {
			b[0]=(byte)(s>>8);
			b[1]=(byte)s;
		}
		else {
			short abs=(short)(s*(-1));
			abs--;
			
			b[0]=(byte)(abs>>8);
			b[1]=(byte)abs;
			
			b[0]=(byte)~b[0];
			b[1]=(byte)~b[1];
		}
		
		return b;
	}
	/**
	 * Converts a short value to a 2-byte byte array.
	 * @see short_to_byte(short)
	 * @param s A short value
	 * @return A 2-byte little-endian byte array
	 */
	public static byte[] short_to_byte_le(short s) {
		byte[] b=new byte[2];
		
		if(s>=0) {
			b[1]=(byte)(s>>8);
			b[0]=(byte)s;
		}
		else {
			short abs=(short)(s*(-1));
			abs--;
			
			b[1]=(byte)(abs>>8);
			b[0]=(byte)abs;
			
			b[1]=(byte)~b[1];
			b[0]=(byte)~b[0];
		}
		
		return b;
	}
	
	/**
	 * Converts a 2-byte byte array to an unsigned short value.
	 * @param b A 2-byte byte array
	 * @return An unsigned short value
	 */
	public static int byte_to_ushort(byte[] b) {
		if(b.length!=2)return 0;
		
		int ret;
		int first,second;
		
		first=Byte.toUnsignedInt(b[0]);
		second=Byte.toUnsignedInt(b[1]);
		
		ret=(first<<8)+second;
		
		return ret;
	}
	/**
	 * Converts a 2-byte byte array to an unsigned short value.
	 * @see byte_to_ushort(byte[])
	 * @param b A 2-byte little-endian byte array
	 * @return An unsigned short value
	 */
	public static int byte_to_ushort_le(byte[] b) {
		if(b.length!=2)return 0;
		
		int ret;
		
		byte[] buffer=new byte[2];
		buffer[0]=b[1];
		buffer[1]=b[0];
		
		ret=byte_to_ushort(buffer);
		
		return ret;
	}
	
	/**
	 * Converts an unsigned short value to a 2-byte byte array.
	 * @param s An unsigned short value
	 * @return A 2-byte byte array
	 */
	public static byte[] ushort_to_byte(int s) {
		byte[] b=new byte[2];
		
		b[0]=(byte)(s>>8);
		b[1]=(byte)s;
		
		return b;
	}
	/**
	 * Converts an unsigned short value to a 2-byte byte array.
	 * @see ushort_to_byte(int)
	 * @param s An unsigned short value
	 * @return A 2-byte little-endian byte array
	 */
	public static byte[] ushort_to_byte_le(int s) {
		byte[] b=new byte[2];
		
		b[1]=(byte)(s>>8);
		b[0]=(byte)s;
		
		return b;
	}
}
