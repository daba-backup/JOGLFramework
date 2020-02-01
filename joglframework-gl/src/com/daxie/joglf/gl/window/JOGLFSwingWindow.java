package com.daxie.joglf.gl.window;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import com.daxie.basis.coloru8.ColorU8;
import com.daxie.basis.coloru8.ColorU8Functions;
import com.daxie.basis.vector.VectorFunctions;
import com.daxie.joglf.gl.draw.GLDrawFunctions2D;
import com.daxie.joglf.gl.exception.GLNotSetupException;
import com.daxie.joglf.gl.front.CameraFront;
import com.daxie.joglf.gl.front.FogFront;
import com.daxie.joglf.gl.front.GLFront;
import com.daxie.joglf.gl.front.LightingFront;
import com.daxie.joglf.gl.input.keyboard.KeyboardEnum;
import com.daxie.joglf.gl.input.keyboard.SwingKeyboard;
import com.daxie.joglf.gl.input.mouse.MouseEnum;
import com.daxie.joglf.gl.input.mouse.SwingMouse;
import com.daxie.joglf.gl.text.FormerTextMgr;
import com.daxie.joglf.gl.text.TextMgr;
import com.daxie.joglf.gl.texture.TextureMgr;
import com.daxie.joglf.gl.wrapper.GLWrapper;
import com.daxie.log.LogFile;
import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

/**
 * Swing window
 * @author Daba
 *
 */
public class JOGLFSwingWindow 
implements GLEventListener,KeyListener,MouseListener,MouseMotionListener,MouseWheelListener{
	private JFrame frame;
	private WindowAdapter adapter;
	private GLCanvas canvas;
	private FPSAnimator animator;
	
	private SwingKeyboard keyboard;
	private SwingMouse mouse;
	
	private ColorU8 background_color;
	
	private boolean destroyed_flag;
	
	public JOGLFSwingWindow() {
		if(GLFront.IsSetup()==false) {
			throw new GLNotSetupException();
		}
		
		String profile_str=GLFront.GetProfileStr();
		GLCapabilities capabilities=new GLCapabilities(GLProfile.get(profile_str));
		
		frame=new JFrame();
		frame.setTitle(WindowCommonInfoStock.DEFAULT_TITLE);
		
		adapter=new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				animator.stop();
				destroyed_flag=true;
				
				onWindowClosing();
			}
		};
		frame.addWindowListener(adapter);
		frame.addKeyListener(this);
		frame.addMouseListener(this);
		frame.addMouseMotionListener(this);
		frame.addMouseWheelListener(this);
		
		canvas=new GLCanvas(capabilities);
		canvas.setPreferredSize(new Dimension(WindowCommonInfoStock.DEFAULT_WIDTH, WindowCommonInfoStock.DEFAULT_HEIGHT));
		canvas.addGLEventListener(this);
		canvas.addKeyListener(this);
		canvas.addMouseListener(this);
		canvas.addMouseMotionListener(this);
		canvas.addMouseWheelListener(this);
		
		int fps=WindowCommonInfoStock.GetFPS();
		animator=new FPSAnimator(fps);
		animator.add(canvas);
		animator.start();
		WindowCommonInfoStock.FinalizeFPS();
		
		frame.add(canvas,BorderLayout.CENTER);
		frame.pack();
		
		LogFile.WriteInfo("[JOGLFSwingWindow-<init>] Window created.", true);
		
		keyboard=new SwingKeyboard();
		mouse=new SwingMouse();
		
		background_color=ColorU8Functions.GetColorU8(0.0f, 0.0f, 0.0f, 1.0f);
		
		destroyed_flag=false;
		
		frame.setVisible(true);
	}
	
	protected JFrame GetFrame() {
		return frame;
	}
	protected GLCanvas GetCanvas() {
		return canvas;
	}
	
	class WindowCloser extends Thread{
		private JFrame frame;
		
		public WindowCloser(JFrame frame) {
			this.frame=frame;
		}
		@Override
		public void run() {
			frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
			frame.dispose();
		}
	}
	public void CloseWindow() {
		WindowCloser closer=new WindowCloser(frame);
		closer.start();
	}
	
	public void SetExitProcessWhenDestroyed() {
		frame.removeWindowListener(adapter);
		
		adapter=new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				animator.stop();
				LogFile.CloseLogFile();
				
				destroyed_flag=true;
				
				onWindowClosing();
				
				System.exit(0);
			}
		};
		frame.addWindowListener(adapter);
	}
	
	protected void onWindowClosing() {
		
	}
	
	protected void ClearDrawScreen() {
		GLWrapper.glClearColor(
				background_color.GetR(),background_color.GetG(),
				background_color.GetB(),background_color.GetA());
		GLWrapper.glClear(GL4.GL_COLOR_BUFFER_BIT|GL4.GL_DEPTH_BUFFER_BIT);
	}
	
	public String GetTitle() {
		return frame.getTitle();
	}
	public int GetX() {
		return frame.getX();
	}
	public int GetY() {
		return frame.getY();
	}
	public int GetWidth() {
		return frame.getWidth();
	}
	public int GetHeight() {
		return frame.getHeight();
	}
	public Point GetCanvasLocation() {
		Point point=canvas.getLocationOnScreen();
		return point;
	}
	public int GetCanvasX() {
		Point point=canvas.getLocationOnScreen();
		return point.x;
	}
	public int GetCanvasY() {
		Point point=canvas.getLocationOnScreen();
		return point.y;
	}
	public Dimension GetCanvasSize() {
		Container content_pane=frame.getContentPane();
		Dimension dimension=content_pane.getSize();
		
		return dimension;
	}
	public int GetCanvasWidth() {
		Container content_pane=frame.getContentPane();
		Dimension dimension=content_pane.getSize();
		
		return dimension.width;
	}
	public int GetCanvasHeight() {
		Container content_pane=frame.getContentPane();
		Dimension dimension=content_pane.getSize();
		
		return dimension.height;
	}
	public ColorU8 GetBackgroundColor() {
		return new ColorU8(background_color);
	}
	public void SetTitle(String title) {
		frame.setTitle(title);
	}
	public void SetPosition(int x,int y) {
		frame.setLocation(x, y);
	}
	public void SetSize(int width,int height) {
		canvas.setPreferredSize(new Dimension(width, height));
		frame.pack();
	}
	public void SetBackgroundColor(ColorU8 color) {
		background_color=color;
	}
	
	public boolean HasFocus() {
		return frame.hasFocus();
	}
	
	public boolean IsDestroyed() {
		return destroyed_flag;
	}
	
	public void ShowWindow() {
		frame.setVisible(true);
	}
	public void HideWindow() {
		frame.setVisible(false);
	}
	
	public void SetWindowMode(WindowMode mode) {
		GraphicsEnvironment environment=GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice device=environment.getDefaultScreenDevice();
		
		if(mode==WindowMode.WINDOW) {
			device.setFullScreenWindow(null);
		}
		else {
			if(device.isFullScreenSupported()==false) {
				LogFile.WriteWarn("[JOGLFSwingWindow-SetWindowMode] Full screen mode is not supported on this system.", true);
				return;
			}
			else {
				device.setFullScreenWindow(frame);
			}
		}
	}
	
	public void SetAlwaysOnTop(boolean flag) {
		frame.setAlwaysOnTop(flag);
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
	public float GetMouseWheelHorizontalRotation() {
		return mouse.GetHorizontalRotation();
	}
	public void SetFixMousePointerFlag(boolean fix_mouse_pointer_flag) {
		mouse.SetFixMousePointerFlag(fix_mouse_pointer_flag);
	}
	
	@Override
	public void init(GLAutoDrawable drawable) {
		GLFront.Lock();
		GLFront.Initialize();
		this.Init();
		GLFront.Unlock();
	}
	protected void Init() {
		
	}
	@Override
	public void reshape(GLAutoDrawable drawable,int x,int y,int width,int height) {
		GLFront.Lock();
		this.Reshape(x, y, width, height);
		GLFront.Unlock();
	}
	protected void Reshape(int x,int y,int width,int height) {
		
	}
	@Override
	public void display(GLAutoDrawable drawable) {
		//Update input.
		keyboard.Update();
		
		Point point=canvas.getLocationOnScreen();
		Container content_pane=frame.getContentPane();
		Dimension dimension=content_pane.getSize();
		
		int x=point.x;
		int y=point.y;
		int width=dimension.width;
		int height=dimension.height;
		mouse.SetWindowPosition(x, y);
		mouse.SetFixMousePointerPosition(width/2, height/2);
		mouse.Update();
		
		//Update shader-relating properties with lock
		//in order to avoid interference from concurrent display() methods in other windows.
		GLFront.Lock();
		this.DefaultUpdate();
		this.Update();
		CameraFront.Update();
		this.Draw();
		GLFront.Unlock();
	}
	private void DefaultUpdate() {
		this.ClearDrawScreen();
		
		Container content_pane=frame.getContentPane();
		Dimension dimension=content_pane.getSize();
		
		int width=dimension.width;
		int height=dimension.height;
		
		CameraFront.UpdateAspect(width, height);
		
		TextureMgr.SetWindowSize(width, height);
		TextMgr.SetWindowSize(width, height);
		FormerTextMgr.SetWindowSize(width, height);
		GLDrawFunctions2D.SetWindowSize(width, height);
		
		LightingFront.Update();
		FogFront.Update();
	}
	protected void Update() {
		CameraFront.SetCameraPositionAndTarget_UpVecY(
				VectorFunctions.VGet(50.0f, 50.0f, 50.0f),VectorFunctions.VGet(0.0f, 0.0f, 0.0f));
	}
	protected void Draw() {
		
	}
	@Override
	public void dispose(GLAutoDrawable drawable) {
		GLFront.Lock();
		this.Dispose();
		GLFront.Unlock();
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
	public void keyTyped(KeyEvent e) {
		keyboard.keyTyped(e);
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		mouse.mouseClicked(e);
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
	public void mouseDragged(MouseEvent e) {
		mouse.mouseDragged(e);
	}
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		mouse.mouseWheelMoved(e);
	}
}
