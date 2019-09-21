package com.daxie.tool;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.daxie.log.LogFile;

/**
 * Methods to handle files.
 * @author Daba
 *
 */
public class FileFunctions {
	/**
	 * Returns all lines of a text file.
	 * @param filename Filename to load
	 * @param encoding Encoding of the file
	 * @return A list of strings that are split per line
	 * @throws FileNotFoundException Specified file not found
	 * @throws UnsupportedEncodingException Specified encoding not supported
	 */
	public static List<String> GetFileAllLines(String filename,String encoding) throws FileNotFoundException,UnsupportedEncodingException{
		List<String> ret=new ArrayList<>();
		
		StringBuilder sb=new StringBuilder("");
		BufferedReader br=null;
		
		br=new BufferedReader(
				new InputStreamReader(
						new FileInputStream(filename),encoding));
		
		try {	
			while(true) {
				String line;
				
				line=br.readLine();
				if(line==null)break;
				
				sb.append(line);
				sb.append("\n");
			}
		}
		catch(IOException e) {
			String str=ExceptionFunctions.GetPrintStackTraceString(e);
			LogFile.WriteWarn("[FileFunctions-GetFileAllLines] Below is the stack trace.",true);
			LogFile.WriteWarn(str, false);
			
			return ret;
		}
		finally {
			try {
				if(br!=null) {
					br.close();
				}
			}
			catch(IOException e) {
				String str=ExceptionFunctions.GetPrintStackTraceString(e);
				LogFile.WriteWarn("[FileFunctions-GetFileAllLines] Below is the stack trace.",true);
				LogFile.WriteWarn(str, false);
				
				return ret;
			}
		}
		
		String[] lines=sb.toString().split("\n");
		for(String line:lines) {
			ret.add(line);
		}
		
		return ret;
	}
	/**
	 * Returns all bytes of a binary file.
	 * @param filename Filename to load
	 * @return A list of bytes
	 * @throws FileNotFoundException Specified file not found
	 */
	public static List<Byte> GetFileAllBin(String filename) throws FileNotFoundException{
		List<Byte> bin=new ArrayList<Byte>();
		
		DataInputStream dis;	
		dis=new DataInputStream(
				new BufferedInputStream(
						new FileInputStream(filename)));
		
		try {
			byte read_byte;
			while(true) {
				read_byte=dis.readByte();
				bin.add(read_byte);
			}
		}
		catch(EOFException e) {
			//to the finally block.
		}
		catch(IOException e) {
			String str=ExceptionFunctions.GetPrintStackTraceString(e);
			LogFile.WriteWarn("[FileFunctions-GetFileAllBin] Below is the stack trace.",true);
			LogFile.WriteWarn(str,false);
			
			return bin;
		}
		finally {
			try {
				if(dis!=null) {
					dis.close();
				}
			}
			catch(IOException e) {
				String str=ExceptionFunctions.GetPrintStackTraceString(e);
				LogFile.WriteWarn("[FileFunctions-GetFileAllBin] Below is the stack trace.",true);
				LogFile.WriteWarn(str,false);
				
				return bin;
			}
		}
		
		return bin;
	}
	/**
	 * Creates a text file.
	 * @param filename Filename
	 * @param encoding Encoding
	 * @param lines Lines to write in the file
	 * @exception FileNotFoundException Thrown when it fails to write
	 * @exception UnsupportedEncodingException Unknown encoding specified
	 */
	public static void CreateTextFile(String filename,String encoding,List<String> lines) 
			throws FileNotFoundException,UnsupportedEncodingException{
		BufferedWriter br=null;
		br=new BufferedWriter(
				new OutputStreamWriter(
						new FileOutputStream(filename),encoding));
		
		try {
			for(String line:lines) {
				br.write(line);
				br.newLine();
			}
			
			br.flush();
		}
		catch(IOException e) {
			String str=ExceptionFunctions.GetPrintStackTraceString(e);
			LogFile.WriteWarn("[FileFunctions-CreateTextFile] Below is the stack trace.",true);
			LogFile.WriteWarn(str,false);
			
			return;
		}
		finally {
			try {
				br.close();
			}
			catch(IOException e) {
				String str=ExceptionFunctions.GetPrintStackTraceString(e);
				LogFile.WriteWarn("[FileFunctions-CreateTextFile] Below is the stack trace.",true);
				LogFile.WriteWarn(str,false);
				
				return;
			}
		}
	}
	/**
	 * Creates a binary file.
	 * @param filename Filename
	 * @param bin A list of bytes
	 * @exception FileNotFoundException Thrown when it fails to write
	 */
	public static void CreateBinFile(String filename,List<Byte> bin) throws FileNotFoundException{
		DataOutputStream dos=null;
		dos=new DataOutputStream(
				new BufferedOutputStream(
						new FileOutputStream(filename)));
		
		try {
			for(Byte b:bin) {
				dos.writeByte(b);
			}
		}
		catch(IOException e) {
			String str=ExceptionFunctions.GetPrintStackTraceString(e);
			LogFile.WriteWarn("[FileFunctions-CreateBinFile] Below is the stack trace.",true);
			LogFile.WriteWarn(str,false);
			
			return;
		}
		finally {
			try {
				dos.close();
			}
			catch(IOException e) {
				String str=ExceptionFunctions.GetPrintStackTraceString(e);
				LogFile.WriteWarn("[FileFunctions-CreateBinFile] Below is the stack trace.",true);
				LogFile.WriteWarn(str,false);
				
				return;
			}
		}
	}
}
