package com.github.dabasan.joglf.gl.window;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Common information for windows
 * @author Daba
 *
 */
public class WindowCommonInfo {
	private static Logger logger=LoggerFactory.getLogger(WindowCommonInfo.class);
	
	private static int fps=30;
	private static boolean fps_finalized_flag=false;
	
	public static final String DEFAULT_TITLE="JOGLFramework";
	public static final int DEFAULT_WIDTH=640;
	public static final int DEFAULT_HEIGHT=480;
	
	public static int GetFPS() {
		return fps;
	}
	public static void SetFPS(int a_fps) {
		if(fps_finalized_flag==true) {
			logger.warn("This method is disabled after the fps is finalized.");
			return;
		}
		fps=a_fps;
	}
	public static void FinalizeFPS() {
		fps_finalized_flag=true;
	}
}
