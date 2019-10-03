package com.daxie.joglf.gl.window;

import com.daxie.basis.coloru8.ColorU8;
import com.daxie.basis.coloru8.ColorU8Functions;
import com.daxie.basis.vector.VectorFunctions;
import com.daxie.joglf.gl.exception.GLNotSetupException;
import com.daxie.joglf.gl.front.CameraFront;
import com.daxie.joglf.gl.front.FogFront;
import com.daxie.joglf.gl.front.GLFront;
import com.daxie.joglf.gl.front.LightingFront;
import com.daxie.joglf.gl.input.keyboard.Keyboard;
import com.daxie.joglf.gl.input.keyboard.KeyboardEnum;
import com.daxie.joglf.gl.input.mouse.Mouse;
import com.daxie.joglf.gl.input.mouse.MouseEnum;
import com.daxie.joglf.gl.text.TextMgr;
import com.daxie.joglf.gl.texture.TextureMgr;
import com.daxie.joglf.gl.wrapper.GLWrapper;
import com.daxie.log.LogFile;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseListener;
import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.FPSAnimator;

/**
 * Window
 * @author Daba
 *
 */
public class JOGLFWindow implements GLEventListener,KeyListener,MouseListener{
	private GLWindow window;
	private WindowAdapter adapter;
	private FPSAnimator animator;
	
	private Keyboard keyboard;
	private Mouse mouse;
	
	private static final String DEFAULT_TITLE="JOGLFramework";
	private static final int DEFAULT_WIDTH=640;
	private static final int DEFAULT_HEIGHT=480;
	
	private static int fps=30;
	private static boolean fps_finalized_flag=false;
	
	private ColorU8 background_color;
	
	private boolean destroyed_flag;
	
	public JOGLFWindow() {
		if(GLFront.IsSetup()==false) {
			throw new GLNotSetupException();
		}
		
		String profile_str=GLFront.GetProfileStr();
		GLCapabilities capabilities=new GLCapabilities(GLProfile.get(profile_str));
		
		window=GLWindow.create(capabilities);
		window.setTitle(DEFAULT_TITLE);
		window.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		window.addGLEventListener(this);
		window.addKeyListener(this);
		window.addMouseListener(this);
		
		adapter=new WindowAdapter() {
			@Override
			public void windowDestroyed(WindowEvent e) {
				animator.stop();
			}
		};
		window.addWindowListener(adapter);
		
		animator=new FPSAnimator(fps);
		animator.add(window);
		animator.start();
		fps_finalized_flag=true;
		
		LogFile.WriteInfo("[NEWTWindow-<init>] Window created.", true);
		
		keyboard=new Keyboard();
		mouse=new Mouse();
		
		background_color=ColorU8Functions.GetColorU8(0.0f, 0.0f, 0.0f, 1.0f);
		
		destroyed_flag=false;
		
		window.setVisible(true);
	}
	
	class WindowCloser extends Thread{
		private JOGLFWindow window;
		
		public WindowCloser(JOGLFWindow window) {
			this.window=window;
		}
		@Override
		public void run() {
			window.window.destroy();
		}
	}
	public void CloseWindow() {
		WindowCloser closer=new WindowCloser(this);
		closer.start();
		
		destroyed_flag=true;
	}
	
	public void SetExitProcessWhenDestroyed() {
		window.removeWindowListener(adapter);
		
		adapter=new WindowAdapter() {
			@Override
			public void windowDestroyed(WindowEvent e) {
				animator.stop();
				LogFile.CloseLogFile();
				
				System.exit(0);
			}
		};
		window.addWindowListener(adapter);
	}
	
	protected void ClearDrawScreen() {
		GLWrapper.glClearColor(
				background_color.GetR(),background_color.GetG(),
				background_color.GetB(),background_color.GetA());
		GLWrapper.glClear(GL4.GL_COLOR_BUFFER_BIT|GL4.GL_DEPTH_BUFFER_BIT);
	}
	
	public String GetTitle() {
		return window.getTitle();
	}
	public int GetX() {
		return window.getX();
	}
	public int GetY() {
		return window.getY();
	}
	public int GetWidth() {
		return window.getWidth();
	}
	public int GetHeight() {
		return window.getHeight();
	}
	public static int GetFPS() {
		return fps;
	}
	public ColorU8 GetBackgroundColor() {
		return new ColorU8(background_color);
	}
	public void SetTitle(String title) {
		window.setTitle(title);
	}
	public void SetPosition(int x,int y) {
		window.setPosition(x, y);
	}
	public void SetSize(int width,int height) {
		window.setSize(width, height);
	}
	public static void SetFPS(int a_fps) {
		if(fps_finalized_flag==true) {
			LogFile.WriteWarn("[Window-SetFPS] This method is disabled after a window is created.", true);
			return;
		}
		fps=a_fps;
	}
	public void SetBackgroundColor(ColorU8 color) {
		background_color=color;
	}
	
	public boolean HasFocus() {
		return window.hasFocus();
	}
	
	public boolean IsDestroyed() {
		return destroyed_flag;
	}
	
	public void ShowWindow() {
		window.setVisible(true);
	}
	public void HideWindow() {
		window.setVisible(false);
	}
	
	public void ShowCursor() {
		window.setPointerVisible(true);
	}
	public void HideCursor() {
		window.setPointerVisible(false);
	}
	
	public void SetWindowMode(WindowMode mode) {
		if(mode==WindowMode.WINDOW) {
			window.setFullscreen(false);
		}
		else {
			window.setFullscreen(true);
		}
	}
	public void SetAlwaysOnTop(boolean flag) {
		window.setAlwaysOnTop(flag);
	}
	public void SetAlwaysOnBottom(boolean flag) {
		window.setAlwaysOnBottom(flag);
	}
	
	public int GetKeyboardPressingCount(KeyboardEnum key) {
		return keyboard.GetPressingCount(key);
	}
	public int GetKeyboardReleasingCount(KeyboardEnum key) {
		return keyboard.GetReleasingCount(key);
	}
	public int GetMouseX() {
		return mouse.GetX();
	}
	public int GetMouseY() {
		return mouse.GetY();
	}
	public int GetMouseDiffX() {
		return mouse.GetDiffX();
	}
	public int GetMouseDiffY() {
		return mouse.GetDiffY();
	}
	public int GetMousePressingCount(MouseEnum key) {
		return mouse.GetButtonPressingCount(key);
	}
	public int GetMouseReleasingCount(MouseEnum key) {
		return mouse.GetButtonReleasingCount(key);
	}
	public float GetMouseWheelVerticalRotation() {
		return mouse.GetVerticalRotation();
	}
	public float GetMouseWheelHorizontalRotation() {
		return mouse.GetHorizontalRotation();
	}
	public float GetMouseWheelZAxisRotation() {
		return mouse.GetZAxisRotation();
	}
	public void SetFixMousePointerFlag(boolean fix_mouse_pointer_flag) {
		mouse.SetFixMousePointerFlag(fix_mouse_pointer_flag);
	}
	
	@Override
	public void init(GLAutoDrawable drawable) {
		GLFront.Initialize();
		this.Init();
	}
	protected void Init() {
		
	}
	
	@Override
	public void reshape(GLAutoDrawable drawable,int x,int y,int width,int height) {
		this.Reshape(x, y, width, height);
	}
	protected void Reshape(int x,int y,int width,int height) {
		
	}
	@Override
	public void display(GLAutoDrawable drawable) {
		//Update input.
		keyboard.Update();
		
		int x=window.getX();
		int y=window.getY();
		int width=window.getWidth();
		int height=window.getHeight();
		mouse.SetFixMousePointerPosition(x+width/2,y+height/2);
		mouse.Update();
		
		//Update shader-relating properties with lock
		//in order to avoid interference from concurrent display() in other windows.
		GLFront.Lock();
		this.DefaultUpdate();
		this.Update();
		if(destroyed_flag==false) {
			this.Draw();
			CameraFront.Update(width, height);
		}
		GLFront.Unlock();
	}
	private void DefaultUpdate() {
		this.ClearDrawScreen();
		
		int width=window.getWidth();
		int height=window.getHeight();
		
		TextureMgr.SetWindowSize(width, height);
		TextMgr.SetWindowSize(width, height);
		
		LightingFront.Update();
		FogFront.Update();
		
		TextureMgr.SetWindowSize(width, height);
		TextMgr.SetWindowSize(width, height);
	}
	protected void Update() {
		CameraFront.SetCameraPositionAndTarget_UpVecY(
				VectorFunctions.VGet(50.0f, 50.0f, 50.0f),VectorFunctions.VGet(0.0f, 0.0f, 0.0f));
	}
	protected void Draw() {
		
	}
	
	@Override
	public void dispose(GLAutoDrawable drawable) {
		this.Dispose();
	}
	protected void Dispose() {
		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		keyboard.keyPressed(e);
	}
	@Override
	public void keyReleased(KeyEvent e) {
		keyboard.keyReleased(e);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		mouse.mouseClicked(e);
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		mouse.mouseDragged(e);
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		mouse.mouseEntered(e);
	}
	@Override
	public void mouseExited(MouseEvent e) {
		mouse.mouseExited(e);
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		mouse.mouseMoved(e);
	}
	@Override
	public void mousePressed(MouseEvent e) {
		mouse.mousePressed(e);
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		mouse.mouseReleased(e);
	}
	@Override
	public void mouseWheelMoved(MouseEvent e) {
		mouse.mouseWheelMoved(e);
	}
}
