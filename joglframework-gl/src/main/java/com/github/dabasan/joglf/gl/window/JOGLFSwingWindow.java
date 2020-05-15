package com.github.dabasan.joglf.gl.window;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
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
import com.github.dabasan.joglf.gl.input.keyboard.KeyboardEnum;
import com.github.dabasan.joglf.gl.input.keyboard.SwingKeyboard;
import com.github.dabasan.joglf.gl.input.mouse.MouseEnum;
import com.github.dabasan.joglf.gl.input.mouse.SwingMouse;
import com.github.dabasan.joglf.gl.text.TextMgr;
import com.github.dabasan.joglf.gl.wrapper.GLWrapper;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

/**
 * Swing window
 * 
 * @author Daba
 *
 */
public class JOGLFSwingWindow
		implements
			JOGLFWindowInterface,
			GLEventListener,
			KeyListener,
			MouseListener,
			MouseMotionListener,
			MouseWheelListener {
	private final Logger logger = LoggerFactory.getLogger(JOGLFSwingWindow.class);

	private final JFrame frame;
	private WindowAdapter adapter;
	private final GLCanvas canvas;
	private final FPSAnimator animator;

	private final SwingKeyboard keyboard;
	private final SwingMouse mouse;
	private Robot robot;

	private ColorU8 background_color;

	private boolean destroyed_flag;

	public JOGLFSwingWindow() {
		final String profile_str = GLFront.GetProfileStr();
		final GLCapabilities capabilities = new GLCapabilities(GLProfile.get(profile_str));

		frame = new JFrame();
		frame.setTitle(WindowCommonInfo.DEFAULT_TITLE);

		adapter = new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				animator.stop();
				destroyed_flag = true;

				OnWindowClosing();
			}
		};
		frame.addWindowListener(adapter);
		frame.addKeyListener(this);
		frame.addMouseListener(this);
		frame.addMouseMotionListener(this);
		frame.addMouseWheelListener(this);

		canvas = new GLCanvas(capabilities);
		canvas.setPreferredSize(
				new Dimension(WindowCommonInfo.DEFAULT_WIDTH, WindowCommonInfo.DEFAULT_HEIGHT));
		canvas.addGLEventListener(this);
		canvas.addKeyListener(this);
		canvas.addMouseListener(this);
		canvas.addMouseMotionListener(this);
		canvas.addMouseWheelListener(this);

		final int fps = WindowCommonInfo.GetFPS();
		animator = new FPSAnimator(fps);
		animator.add(canvas);
		animator.start();
		WindowCommonInfo.FinalizeFPS();

		frame.add(canvas, BorderLayout.CENTER);
		frame.pack();

		logger.info("Window created.");

		keyboard = new SwingKeyboard();
		mouse = new SwingMouse();
		try {
			robot = new Robot();
		} catch (final AWTException e) {
			logger.error("Error", e);
		}

		background_color = ColorU8Functions.GetColorU8(0.0f, 0.0f, 0.0f, 1.0f);

		destroyed_flag = false;

		frame.setVisible(true);
	}

	protected JFrame GetFrame() {
		return frame;
	}
	protected GLCanvas GetCanvas() {
		return canvas;
	}

	class WindowCloser extends Thread {
		private final JFrame frame;

		public WindowCloser(JFrame frame) {
			this.frame = frame;
		}

		@Override
		public void run() {
			frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
			frame.dispose();
		}
	}
	@Override
	public void CloseWindow() {
		final WindowCloser closer = new WindowCloser(frame);
		closer.start();
	}
	@Override
	public void SetExitProcessWhenDestroyed() {
		frame.removeWindowListener(adapter);

		adapter = new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				animator.stop();
				destroyed_flag = true;
				OnWindowClosing();
				System.exit(0);
			}
		};
		frame.addWindowListener(adapter);
	}

	public void ResetKeyboardInputState() {
		keyboard.Reset();
	}
	public void ResetMouseInputState() {
		mouse.Reset();
	}

	protected void ClearDrawScreen() {
		GLWrapper.glClearColor(background_color.GetR(), background_color.GetG(),
				background_color.GetB(), background_color.GetA());
		GLWrapper.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
	}

	@Override
	public String GetTitle() {
		return frame.getTitle();
	}
	@Override
	public int GetX() {
		return frame.getX();
	}
	@Override
	public int GetY() {
		return frame.getY();
	}
	@Override
	public int GetWidth() {
		return frame.getWidth();
	}
	@Override
	public int GetHeight() {
		return frame.getHeight();
	}
	public Point GetCanvasLocation() {
		final Point point = canvas.getLocationOnScreen();
		return point;
	}
	public int GetCanvasX() {
		final Point point = canvas.getLocationOnScreen();
		return point.x;
	}
	public int GetCanvasY() {
		final Point point = canvas.getLocationOnScreen();
		return point.y;
	}
	public Dimension GetCanvasSize() {
		final Container content_pane = frame.getContentPane();
		final Dimension dimension = content_pane.getSize();

		return dimension;
	}
	public int GetCanvasWidth() {
		final Container content_pane = frame.getContentPane();
		final Dimension dimension = content_pane.getSize();

		return dimension.width;
	}
	public int GetCanvasHeight() {
		final Container content_pane = frame.getContentPane();
		final Dimension dimension = content_pane.getSize();

		return dimension.height;
	}
	@Override
	public ColorU8 GetBackgroundColor() {
		return new ColorU8(background_color);
	}

	@Override
	public void SetTitle(String title) {
		frame.setTitle(title);
	}
	@Override
	public void SetPosition(int x, int y) {
		frame.setLocation(x, y);
	}
	@Override
	public void SetSize(int width, int height) {
		frame.setSize(width, height);
	}
	public void SetCanvasSize(int width, int height) {
		canvas.setPreferredSize(new Dimension(width, height));
		frame.pack();
	}
	@Override
	public void SetBackgroundColor(ColorU8 color) {
		background_color = color;
	}

	@Override
	public boolean HasFocus() {
		return frame.hasFocus();
	}
	@Override
	public boolean IsDestroyed() {
		return destroyed_flag;
	}

	@Override
	public void ShowWindow() {
		frame.setVisible(true);
	}
	@Override
	public void HideWindow() {
		frame.setVisible(false);
	}

	@Override
	public void SetWindowMode(WindowMode mode) {
		final GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		final GraphicsDevice device = environment.getDefaultScreenDevice();

		if (mode == WindowMode.WINDOW) {
			device.setFullScreenWindow(null);
		} else {
			if (device.isFullScreenSupported() == false) {
				logger.warn("Full-screen mode is not supported on this system.");
				return;
			} else {
				device.setFullScreenWindow(frame);
			}
		}
	}

	@Override
	public void SetAlwaysOnTop(boolean flag) {
		frame.setAlwaysOnTop(flag);
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

	@Override
	public float GetMouseWheelHorizontalRotation() {
		return mouse.GetHorizontalRotation();
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

		final int width = this.GetCanvasWidth();
		final int height = this.GetCanvasHeight();

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
		this.Update();// User data
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

	@Override
	public void Fit() {
		GLWrapper.glViewport(0, 0, this.GetCanvasWidth(), this.GetCanvasHeight());
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
