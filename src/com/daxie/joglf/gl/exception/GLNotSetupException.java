package com.daxie.joglf.gl.exception;

/**
 * Exception thrown when a GL-relating job is going to be done before GL setup.
 * @author Daba
 *
 */
public class GLNotSetupException extends RuntimeException{
	private static final long serialVersionUID=1;
	
	public GLNotSetupException() {
		super("GL has not been set up yet.");
	}
}
