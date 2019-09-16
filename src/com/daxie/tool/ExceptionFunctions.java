package com.daxie.tool;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Methods to handle exceptions.
 * @author Daba
 *
 */
public class ExceptionFunctions {
	public static String GetPrintStackTraceString(Throwable t) {
		StringWriter sw=new StringWriter();
		PrintWriter pw=new PrintWriter(sw);
		
		t.printStackTrace(pw);
		pw.flush();
		
		return sw.toString();
	}
}
