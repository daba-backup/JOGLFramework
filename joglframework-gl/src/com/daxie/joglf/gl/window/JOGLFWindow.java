package com.daxie.joglf.gl.window;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Robot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.daxie.basis.coloru8.ColorU8;
import com.daxie.basis.coloru8.ColorU8Functions;
import com.daxie.basis.vector.VectorFunctions;
import com.daxie.joglf.gl.draw.GLDrawFunctions2D;
import com.daxie.joglf.gl.draw.GLDrawFunctions3D;
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
public class JOGLFWindow implements JOGLFWindowInterface,GLEventListener,KeyListener,MouseListener{
	private Logger logger=LoggerFactory.getLogger(JOGLFWindow.class);
	
	private GLWindow window;
	private WindowAdapter adapter;
	private FPSAnimator animator;
	
	private Keyboard keyboard;
	private Mouse mouse;
	private Robot robot;
	
	private ColorU8 background_color;
	
	private boolean destroyed_flag;
	
	public JOGLFWindow() {
		String profile_str=GLFront.GetProfileStr();
		GLCapabilities capabilities=new GLCapabilities(GLProfile.get(profile_str));
		
		window=GLWindow.create(capabilities);
		window.setTitle(WindowCommonInfoStock.DEFAULT_TITLE);
		window.setSize(WindowCommonInfoStock.DEFAULT_WIDTH, WindowCommonInfoStock.DEFAULT_HEIGHT);
		window.addGLEventListener(this);
		window.addKeyListener(this);
		window.addMouseListener(this);
		
		adapter=new WindowAdapter() {
			@Override
			public void windowDestroyed(WindowEvent e) {
				animator.stop();
				destroyed_flag=true;
				
				OnWindowClosing();
			}
		};
		window.addWindowListener(adapter);
		
		int fps=WindowCommonInfoStock.GetFPS();
		animator=new FPSAnimator(fps);
		animator.add(window);
		animator.start();
		WindowCommonInfoStock.FinalizeFPS();
		
		logger.info("Window created.");
		
		keyboard=new Keyboard();
		mouse=new Mouse();
		try {
			robot=new Robot();
		}
		catch(AWTException e) {
			logger.error("Error",e);
		}
		
		background_color=ColorU8Functions.GetColorU8(0.0f, 0.0f, 0.0f, 1.0f);
		
		destroyed_flag=false;
		
		window.setVisible(true);
	}
	
	protected GLWindow GetWindow() {
		return window;
	}
	
	class WindowCloser extends Thread{
		private GLWindow window;
		
		public WindowCloser(GLWindow window) {
			this.window=window;
		}
		@Override
		public void run() {
			window.destroy();
		}
	}
	@Override
	public void CloseWindow() {
		WindowCloser closer=new WindowCloser(window);
		closer.start();
	}
	@Override
	public void SetExitProcessWhenDestroyed() {
		window.removeWindowListener(adapter);
		
		adapter=new WindowAdapter() {
			@Override
			public void windowDestroyed(WindowEvent e) {
				animator.stop();
				destroyed_flag=true;				
				OnWindowClosing();				
				System.exit(0);
			}
		};
		window.addWindowListener(adapter);
	}
	
	public void AddChildWindow(JOGLFWindow child) {
		window.addChild(child.window);
	}
	
	protected void ClearDrawScreen() {
		GLWrapper.glClearColor(
				background_color.GetR(),background_color.GetG(),
				background_color.GetB(),background_color.GetA());
		GLWrapper.glClear(GL4.GL_COLOR_BUFFER_BIT|GL4.GL_DEPTH_BUFFER_BIT);
	}
	
	@Override
	public String GetTitle() {
		return window.getTitle();
	}
	@Override
	public int GetX() {
		return window.getX();
	}
	@Override
	public int GetY() {
		return window.getY();
	}
	@Override
	public int GetWidth() {
		return window.getWidth();
	}
	@Override
	public int GetHeight() {
		return window.getHeight();
	}
	@Override
	public ColorU8 GetBackgroundColor() {
		return new ColorU8(background_color);
	}
	@Override
	public void SetTitle(String title) {
		window.setTitle(title);
	}
	@Override
	public void SetPosition(int x,int y) {
		window.setPosition(x, y);
	}
	@Override
	public void SetSize(int width,int height) {
		window.setSize(width, height);
	}
	@Override
	public void SetBackgroundColor(ColorU8 color) {
		background_color=color;
	}
	
	@Override
	public boolean HasFocus() {
		return window.hasFocus();
	}
	@Override
	public boolean IsDestroyed() {
		return destroyed_flag;
	}
	
	@Override
	public void ShowWindow() {
		window.setVisible(true);
	}
	@Override
	public void HideWindow() {
		window.setVisible(false);
	}
	@Override
	public void ShowCursor() {
		window.setPointerVisible(true);
	}
	@Override
	public void HideCursor() {
		window.setPointerVisible(false);
	}
	
	@Override
	public void SetWindowMode(WindowMode mode) {
		if(mode==WindowMode.WINDOW) {
			window.setFullscreen(false);
		}
		else {
			window.setFullscreen(true);
		}
	}
	@Override
	public void SetAlwaysOnTop(boolean flag) {
		window.setAlwaysOnTop(flag);
	}
	public void SetAlwaysOnBottom(boolean flag) {
		window.setAlwaysOnBottom(flag);
	}
	
	@Override
	public int GetKeyboardPressingCount(KeyboardEnum key) {
		return keyboard.GetPressingCount(key);
	}
	@Override
	public int GetKeyboardReleasingCount(KeyboardEnum key) {
		return keyboard.GetReleasingCount(key);
	}
	@Override
	public int GetMousePressingCount(MouseEnum key) {
		return mouse.GetButtonPressingCount(key);
	}
	@Override
	public int GetMouseReleasingCount(MouseEnum key) {
		return mouse.GetButtonReleasingCount(key);
	}
	@Override
	public int GetCursorX() {
		int x=this.GetX();
		int cursor_x=MouseInfo.getPointerInfo().getLocation().x;
		int cursor_window_x=cursor_x-x;
		
		return cursor_window_x;
	}
	@Override
	public int GetCursorY() {
		int y=this.GetY();
		int cursor_y=MouseInfo.getPointerInfo().getLocation().y;
		int cursor_window_y=cursor_y-y;
		
		return cursor_window_y;
	}
	@Override
	public int GetCursorDiffX() {
		return mouse.GetDiffX();
	}
	@Override
	public int GetCursorDiffY() {
		return mouse.GetDiffY();
	}
	@Override
	public void SetCursorPos(int x,int y) {
		int window_x=this.GetX();
		int window_y=this.GetY();
		
		robot.mouseMove(window_x+x, window_y+y);
	}
	
	public float GetMouseWheelVerticalRotation() {
		return mouse.GetVerticalRotation();
	}
	@Override
	public float GetMouseWheelHorizontalRotation() {
		return mouse.GetHorizontalRotation();
	}
	public float GetMouseWheelZAxisRotation() {
		return mouse.GetZAxisRotation();
	}
	
	@Override
	public void init(GLAutoDrawable drawable) {
		GLFront.Lock();
		GLFront.Initialize();
		this.Init();
		GLFront.Unlock();
	}
	@Override
	public void reshape(GLAutoDrawable drawable,int x,int y,int width,int height) {
		GLFront.Lock();
		this.Reshape(x, y, width, height);
		GLFront.Unlock();
	}
	@Override
	public void display(GLAutoDrawable drawable) {
		if(destroyed_flag==true) {
			return;
		}
		
		//Update input.
		keyboard.Update();
		mouse.Update();
		
		int width=window.getWidth();
		int height=window.getHeight();
		
		//Update shader-relating properties with lock
		//in order to avoid interference from concurrent display() methods in other windows.
		GLFront.Lock();
		
		//Default updates==========
		this.ClearDrawScreen();
		CameraFront.UpdateAspect(width, height);
		TextureMgr.SetWindowSize(width, height);
		TextMgr.SetWindowSize(width, height);
		GLDrawFunctions2D.SetWindowSize(width, height);
		LightingFront.Update();
		FogFront.Update();
		//====================
		this.Update();//User update
		CameraFront.Update();
		this.Draw();//User draw
		
		GLFront.Unlock();
	}
	@Override
	public void dispose(GLAutoDrawable drawable) {
		GLFront.Lock();
		this.Dispose();
		GLFront.Unlock();
	}
	
	@Override
	public void Init() {
		
	}
	@Override
	public void Reshape(int x,int y,int width,int height) {
		
	}
	@Override
	public void Update() {
		CameraFront.SetCameraPositionAndTarget_UpVecY(
				VectorFunctions.VGet(50.0f, 50.0f, 50.0f),VectorFunctions.VGet(0.0f, 0.0f, 0.0f));
	}
	@Override
	public void Draw() {
		GLDrawFunctions3D.DrawAxes(100.0f);
	}
	@Override
	public void Dispose() {
		
	}
	@Override
	public void OnWindowClosing() {
		
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
