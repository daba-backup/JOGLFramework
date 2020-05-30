package com.github.dabasan.joglf.gl.window;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Robot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.dabasan.basis.coloru8.ColorU8;
import com.github.dabasan.basis.coloru8.ColorU8Functions;
import com.github.dabasan.basis.vector.VectorFunctions;
import com.github.dabasan.joglf.gl.draw.DrawFunctions2D;
import com.github.dabasan.joglf.gl.draw.DrawFunctions3D;
import com.github.dabasan.joglf.gl.front.CameraFront;
import com.github.dabasan.joglf.gl.front.FogFront;
import com.github.dabasan.joglf.gl.front.GLFront;
import com.github.dabasan.joglf.gl.front.LightingFront;
import com.github.dabasan.joglf.gl.input.keyboard.Keyboard;
import com.github.dabasan.joglf.gl.input.keyboard.KeyboardEnum;
import com.github.dabasan.joglf.gl.input.mouse.Mouse;
import com.github.dabasan.joglf.gl.input.mouse.MouseEnum;
import com.github.dabasan.joglf.gl.text.TextMgr;
import com.github.dabasan.joglf.gl.wrapper.GLWrapper;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseListener;
import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.FPSAnimator;

/**
 * Window
 * 
 * @author Daba
 *
 */
public class JOGLFWindow
		implements
			JOGLFWindowInterface,
			GLEventListener,
			KeyListener,
			MouseListener {
	private final Logger logger = LoggerFactory.getLogger(JOGLFWindow.class);

	private final GLWindow window;
	private WindowAdapter adapter;
	private final FPSAnimator animator;

	private final Keyboard keyboard;
	private final Mouse mouse;
	private Robot robot;

	private ColorU8 background_color;
	private boolean destroyed_flag;

	public JOGLFWindow() {
		GLFront.Lock();

		final String profile_str = GLFront.GetProfileStr();
		final GLCapabilities capabilities = new GLCapabilities(GLProfile.get(profile_str));

		window = GLWindow.create(capabilities);
		window.setTitle(WindowCommonInfo.DEFAULT_TITLE);
		window.setSize(WindowCommonInfo.DEFAULT_WIDTH, WindowCommonInfo.DEFAULT_HEIGHT);
		window.addGLEventListener(this);
		window.addKeyListener(this);
		window.addMouseListener(this);

		adapter = new WindowAdapter() {
			@Override
			public void windowDestroyed(WindowEvent e) {
				animator.stop();
				destroyed_flag = true;

				OnWindowClosing();
			}
		};
		window.addWindowListener(adapter);

		final int fps = WindowCommonInfo.GetFPS();
		animator = new FPSAnimator(fps);
		animator.add(window);
		animator.start();
		WindowCommonInfo.FinalizeFPS();

		keyboard = new Keyboard();
		mouse = new Mouse();
		try {
			robot = new Robot();
		} catch (final AWTException e) {
			logger.error("Error", e);
		}

		background_color = ColorU8Functions.GetColorU8(0.0f, 0.0f, 0.0f, 1.0f);
		destroyed_flag = false;

		window.setVisible(true);

		if (GLFront.IsSetup() == false) {
			GLFront.SetSetupFlag(true);
		}

		logger.info("Window created.");

		GLFront.Unlock();
	}

	protected GLWindow GetWindow() {
		return window;
	}

	class WindowCloser extends Thread {
		private final GLWindow window;

		public WindowCloser(GLWindow window) {
			this.window = window;
		}

		@Override
		public void run() {
			window.destroy();
		}
	}
	@Override
	public void CloseWindow() {
		final WindowCloser closer = new WindowCloser(window);
		closer.start();
	}
	/**
	 * Terminates JVM when this window is destroyed.
	 */
	@Override
	public void SetExitProcessWhenDestroyed() {
		window.removeWindowListener(adapter);

		adapter = new WindowAdapter() {
			@Override
			public void windowDestroyed(WindowEvent e) {
				animator.stop();
				destroyed_flag = true;
				OnWindowClosing();
				System.exit(0);
			}
		};
		window.addWindowListener(adapter);
	}

	public void ResetKeyboardInputState() {
		keyboard.Reset();
	}
	public void ResetMouseInputState() {
		mouse.Reset();
	}

	public void AddChildWindow(JOGLFWindow child) {
		window.addChild(child.window);
	}

	protected void ClearDrawScreen() {
		GLWrapper.glClearColor(background_color.GetR(), background_color.GetG(),
				background_color.GetB(), background_color.GetA());
		GLWrapper.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
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
	public void SetPosition(int x, int y) {
		window.setPosition(x, y);
	}
	@Override
	public void SetSize(int width, int height) {
		window.setSize(width, height);
	}
	@Override
	public void SetBackgroundColor(ColorU8 color) {
		background_color = color;
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

	public void ShowCursor() {
		window.setPointerVisible(true);
	}
	public void HideCursor() {
		window.setPointerVisible(false);
	}

	@Override
	public void SetWindowMode(WindowMode mode) {
		if (mode == WindowMode.WINDOW) {
			window.setFullscreen(false);
		} else {
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
		final int x = this.GetX();
		final int cursor_x = MouseInfo.getPointerInfo().getLocation().x;
		final int cursor_window_x = cursor_x - x;

		return cursor_window_x;
	}
	@Override
	public int GetCursorY() {
		final int y = this.GetY();
		final int cursor_y = MouseInfo.getPointerInfo().getLocation().y;
		final int cursor_window_y = cursor_y - y;

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
	public void SetCursorPos(int x, int y) {
		final int window_x = this.GetX();
		final int window_y = this.GetY();

		robot.mouseMove(window_x + x, window_y + y);
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
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		GLFront.Lock();
		this.Reshape(x, y, width, height);
		GLFront.Unlock();
	}
	@Override
	public void display(GLAutoDrawable drawable) {
		if (destroyed_flag == true) {
			return;
		}

		// Update input.
		keyboard.Update();
		mouse.Update();

		final int width = this.GetWidth();
		final int height = this.GetHeight();

		// Update shader-relating properties with lock
		// in order to avoid interference from concurrent display() methods in
		// other windows.
		GLFront.Lock();

		// Default updates==========
		this.ClearDrawScreen();
		CameraFront.UpdateAspect(width, height);
		TextMgr.SetWindowSize(width, height);
		DrawFunctions2D.SetWindowSize(width, height);
		LightingFront.Update();
		FogFront.Update();
		// ====================
		this.Update();// User update
		CameraFront.Update();
		this.Draw();// User draw

		GLFront.Unlock();
	}
	@Override
	public void dispose(GLAutoDrawable drawable) {
		GLFront.Lock();
		this.Dispose();
		GLFront.Unlock();
	}

	/**
	 * Sets the viewport.
	 */
	@Override
	public void Fit() {
		GLWrapper.glViewport(0, 0, this.GetWidth(), this.GetHeight());
	}

	@Override
	public void Init() {

	}
	@Override
	public void Reshape(int x, int y, int width, int height) {

	}
	@Override
	public void Update() {
		CameraFront.SetCameraPositionAndTarget_UpVecY(VectorFunctions.VGet(50.0f, 50.0f, 50.0f),
				VectorFunctions.VGet(0.0f, 0.0f, 0.0f));
	}
	@Override
	public void Draw() {
		DrawFunctions3D.DrawAxes(100.0f);
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
