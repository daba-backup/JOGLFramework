package com.daxie.joglf.gl.window;

import com.daxie.log.LogWriter;

/**
 * Common information for windows
 * @author Daba
 *
 */
public class WindowCommonInfoStock {
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
			LogWriter.WriteWarn("[WindowCommonInfoStock-SetFPS] ", true);
			LogWriter.WriteWarn("This method is disabled after the FPS is finalized.", false);
			return;
		}
		fps=a_fps;
	}
	public static void FinalizeFPS() {
		fps_finalized_flag=true;
	}
}
