package com.daxie.log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import com.daxie.tool.DateFunctions;

/**
 * Offers log functions.<br>
 * <br>
 * This class has six flags to set to what extent events should be logged.<br>
 * Flags are assigned as follows.<br>
 * |0|0|TRACE|DEBUG|INFO|WARN|ERROR|FATAL|
 * @author Daba
 *
 */
public class LogFile {
	private static String directory="./";
	private static String filename="log.txt";
	
	//|0|0|TRACE|DEBUG|INFO|WARN|ERROR|FATAL|
	public static final int LOG_LEVEL_FATAL=0b00000001;
	public static final int LOG_LEVEL_ERROR=0b00000010;
	public static final int LOG_LEVEL_WARN=0b00000100;
	public static final int LOG_LEVEL_INFO=0b00001000;
	public static final int LOG_LEVEL_DEBUG=0b00010000;
	public static final int LOG_LEVEL_TRACE=0b00100000;
	
	public static final int LOG_LEVEL_NONE=0b00000000;
	public static final int LOG_LEVEL_ALL=0b00111111;
	
	private static int log_level_flags=LOG_LEVEL_FATAL;
	
	private static boolean output_log_flag=true;
	
	private static BufferedWriter bw=null;
	
	/**
	 * Sets the directory where the log file will be created.<br>
	 * @param a_directory Directory name
	 */
	public static void SetLogDirectory(String a_directory) {
		directory=a_directory;
	}
	/**
	 * Sets the filename of the log file.
	 * @param a_filename Filename
	 */
	public static void SetLogFilename(String a_filename) {
		filename=a_filename;
	}
	/**
	 * Sets the flags to specify to what extent events should be logged.
	 * @param flags Flags
	 */
	public static void SetLogLevelFlags(int flags) {
		log_level_flags=flags;
	}
	/**
	 * Sets the flag to determine whether to use log output.
	 * @param a_output_log_flag Output log flag
	 */
	public static void SetOutputLogFlag(boolean a_output_log_flag) {
		output_log_flag=a_output_log_flag;
	}
	
	/**
	 * Creates a log file.<br>
	 * Call this method before logging.
	 */
	public static void OpenLogFile() {
		//Close the opened log file first.
		if(bw!=null) {
			CloseLogFile();
		}
		
		File log_directory=new java.io.File(directory);
		if(log_directory.exists()==false)log_directory.mkdirs();
		
		try {
			bw=new BufferedWriter(
					new OutputStreamWriter(
							new FileOutputStream(directory+"/"+filename)));
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Closes the log file.
	 */
	public static void CloseLogFile() {
		if(bw!=null) {
			try {
				bw.flush();
				bw.close();
				bw=null;
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * Flushes the stream.
	 */
	public static void Flush() {
		if(bw!=null) {
			try {
				bw.flush();
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Writes a string.
	 * @param str String
	 */
	public static void WriteString(String str) {
		if(output_log_flag==false)return;
		
		if(bw!=null) {
			try {
				bw.write(str);
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
		else {
			System.out.print(str);
		}
	}
	/**
	 * Writes a line.
	 * @param line Line
	 */
	public static void WriteLine(String line) {
		if(output_log_flag==false)return;
		
		if(bw!=null) {
			try {
				bw.write(line);
				bw.newLine();
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
		else {
			System.out.println(line);
		}
	}
	
	/**
	 * Writes a trace string.
	 * @param str String
	 * @param output_date_flag Specifies whether to output the date
	 */
	//TRACE
	public static void WriteTrace(String str,boolean output_date_flag) {
		int op=log_level_flags&LOG_LEVEL_TRACE;
		if(op==0)return;
		
		String message="";
		
		message+="TRACE ";
		if(output_date_flag==true) {
			message+=DateFunctions.GetDateStringToMilliseconds();
			message+=" ";
		}
		message+=str;
		
		WriteLine(message);
	}
	/**
	 * Writes a debug string.
	 * @param str String
	 * @param output_date_flag Specifies whether to output the date
	 */
	//DEBUG
	public static void WriteDebug(String str,boolean output_date_flag) {
		int op=log_level_flags&LOG_LEVEL_DEBUG;
		if(op==0)return;
		
		String message="";
		
		message+="DEBUG ";
		if(output_date_flag==true) {
			message+=DateFunctions.GetDateStringToMilliseconds();
			message+=" ";
		}
		message+=str;
		
		WriteLine(message);
	}
	/**
	 * Writes an info string.
	 * @param str String
	 * @param output_date_flag Specifies whether to output the date
	 */
	//INFO
	public static void WriteInfo(String str,boolean output_date_flag) {
		int op=log_level_flags&LOG_LEVEL_INFO;
		if(op==0)return;
		
		String message="";
		
		message+="INFO ";
		if(output_date_flag==true) {
			message+=DateFunctions.GetDateStringToMilliseconds();
			message+=" ";
		}
		message+=str;
		
		WriteLine(message);
	}
	/**
	 * Writes a warning string.
	 * @param str String
	 * @param output_date_flag Specifies whether to output the date
	 */
	//WARN
	public static void WriteWarn(String str,boolean output_date_flag) {
		int op=log_level_flags&LOG_LEVEL_WARN;
		if(op==0)return;
		
		String message="";
		
		message+="WARN ";
		if(output_date_flag==true) {
			message+=DateFunctions.GetDateStringToMilliseconds();
			message+=" ";
		}
		message+=str;
		
		WriteLine(message);
	}
	/**
	 * Writes an error string.
	 * @param str String
	 * @param output_date_flag Specifies whether to output the date
	 */
	//ERROR
	public static void WriteError(String str,boolean output_date_flag) {
		int op=log_level_flags&LOG_LEVEL_ERROR;
		if(op==0)return;
		
		String message="";
		
		message+="ERROR ";
		if(output_date_flag==true) {
			message+=DateFunctions.GetDateStringToMilliseconds();
			message+=" ";
		}
		message+=str;
		
		WriteLine(message);
	}
	/**
	 * Writes a fatal string.
	 * @param str String
	 * @param output_date_flag Specifies whether to output the date
	 */
	//FATAL
	public static void WriteFatal(String str,boolean output_date_flag) {
		int op=log_level_flags&LOG_LEVEL_FATAL;
		if(op==0)return;
		
		String message="";
		
		message+="FATAL ";
		if(output_date_flag==true) {
			message+=DateFunctions.GetDateStringToMilliseconds();
			message+=" ";
		}
		message+=str;
		
		WriteLine(message);
	}
}
