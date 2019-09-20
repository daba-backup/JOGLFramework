package com.daxie.joglf.gl.front;

import com.daxie.basis.coloru8.ColorU8;
import com.daxie.basis.coloru8.ColorU8Functions;
import com.daxie.joglf.al.front.ALFront;
import com.daxie.joglf.gl.wrapper.GLVersion;
import com.daxie.joglf.gl.wrapper.GLWrapper;
import com.daxie.log.LogFile;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.MouseListener;
import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.FPSAnimator;

/**
 * Offers methods for window operations.
 * @author Daba
 *
 */
public class WindowFront {
	private static FPSAnimator animator;
	private static GLWindow window;
	
	private static final String DEFAULT_WINDOW_TEXT="JOGLFramework";
	private static final int DEFAULT_WINDOW_WIDTH=640;
	private static final int DEFAULT_WINDOW_HEIGHT=480;
	
	private static int fps=30;
	
	private static ColorU8 background_color=ColorU8Functions.GetColorU8(0.0f, 0.0f, 0.0f, 1.0f);
	
	public static void Initialize() {
		GLVersion gl_version=GLWrapper.GetGLVersion();
		
		GLCapabilities capabilities=null;
		String profile_str="";
		if(gl_version==GLVersion.GL3)profile_str=GLProfile.GL3bc;
		else if(gl_version==GLVersion.GL4)profile_str=GLProfile.GL4bc;
		else if(gl_version==GLVersion.GLES3)profile_str=GLProfile.GLES3;
		
		capabilities=new GLCapabilities(GLProfile.get(profile_str));
		
		LogFile.WriteInfo("[WindowFront-Initialize] GL profile:"+profile_str, true);
		
		//Create a window.
		window=GLWindow.create(capabilities);
		window.setTitle(DEFAULT_WINDOW_TEXT);
		window.setSize(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);
		window.addWindowListener(new WindowAdapter() {
			@Override
			public void windowDestroyed(WindowEvent e) {
				WindowFront.Dispose();
				ALFront.Dispose();
				
				System.exit(0);
			}
		});
		
		//Create an animator.
		animator=new FPSAnimator(fps);
		animator.add(window);
		animator.start();
		
		window.setVisible(true);
		
		LogFile.WriteInfo("[WindowFront-Initialize] Initialized.", true);
	}
	public static void Dispose() {
		if(animator!=null)animator.stop();
		
		LogFile.WriteInfo("[WindowFront-Dispose] Disposed.",true);
	}
	
	public static void AddEventListener(GLEventListener event_listener) {
		if(window==null) {
			LogFile.WriteWarn("[WindowFront-AddEventListener] Window is null.", true);
			return;
		}
		window.addGLEventListener(event_listener);
	}
	public static void AddKeyListener(KeyListener key_listener) {
		if(window==null) {
			LogFile.WriteWarn("[WindowFront-AddKeyListener] Window is null.", true);
			return;
		}
		window.addKeyListener(key_listener);
	}
	public static void AddMouseListener(MouseListener mouse_listener) {
		if(window==null) {
			LogFile.WriteWarn("[WindowFront-AddMouseListener] Window is null.", true);
			return;
		}
		window.addMouseListener(mouse_listener);
	}
	
	public static boolean IsInitialized() {
		if(animator==null)return false;
		else return true;
	}
	
	public static void CloseWindow() {
		window.destroy();
	}
	
	public static int GetFPS() {
		return fps;
	}
	public static void SetFPS(int a_fps) {
		if(IsInitialized()==true) {
			LogFile.WriteWarn("[WindowFront-SetFPS] This method is disabled after the framework is initialized.", true);
			return;
		}
		
		fps=a_fps;
	}
	
	public static void SetBackgroundColor(ColorU8 color) {
		background_color=color;
	}
	
	public static void ClearDrawScreen() {
		GLWrapper.glClearColor(
				background_color.GetR(),background_color.GetG(),
				background_color.GetB(),background_color.GetA());
		GLWrapper.glClear(GL4.GL_COLOR_BUFFER_BIT|GL4.GL_DEPTH_BUFFER_BIT);
	}
	
	public static void HideCursor() {
		window.setPointerVisible(false);
	}
	public static void ShowCursor() {
		window.setPointerVisible(true);
	}
	
	public static float GetWindowAspect() {
		int window_width=window.getWidth();
		int window_height=window.getHeight();
		
		return (float)window_width/window_height;
	}
	public static int GetWindowX() {
		return window.getX();
	}
	public static int GetWindowY() {
		return window.getY();
	}
	public static int GetWindowWidth() {
		return window.getWidth();
	}
	public static int GetWindowHeight() {
		return window.getHeight();
	}
	public static void SetWindowPosition(int x,int y) {
		window.setPosition(x, y);
	}
	public static void SetWindowSize(int width,int height) {
		window.setSize(width, height);
	}
	public static void SetWindowText(String text) {
		window.setTitle(text);
	}
}
