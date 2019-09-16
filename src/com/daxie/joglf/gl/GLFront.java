package com.daxie.joglf.gl;

import com.daxie.joglf.basis.coloru8.ColorU8;
import com.daxie.joglf.basis.coloru8.ColorU8Functions;
import com.daxie.joglf.basis.matrix.Matrix;
import com.daxie.joglf.basis.matrix.MatrixFunctions;
import com.daxie.joglf.basis.vector.Vector;
import com.daxie.joglf.basis.vector.VectorFunctions;
import com.daxie.joglf.gl.camera.Camera;
import com.daxie.joglf.gl.fog.Fog;
import com.daxie.joglf.gl.input.Keyboard;
import com.daxie.joglf.gl.input.KeyboardEnum;
import com.daxie.joglf.gl.input.Mouse;
import com.daxie.joglf.gl.input.MouseEnum;
import com.daxie.joglf.gl.lighting.Lighting;
import com.daxie.joglf.gl.wrapper.GLShaderFunctions;
import com.daxie.joglf.gl.wrapper.GLVersion;
import com.daxie.joglf.gl.wrapper.GLWrapper;
import com.daxie.joglf.gl.wrapper.gl4.GL4Wrapper;
import com.daxie.joglf.log.LogFile;
import com.daxie.joglf.tool.MathFunctions;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.MouseEvent;
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
 * Offers general functions that users may need for fundamental operations.
 * @author Daba
 *
 */
public class GLFront {
	private static FPSAnimator animator;
	private static GLWindow window;
	
	private static Keyboard keyboard;
	private static Mouse mouse;
	
	private static Camera camera;
	
	private static Lighting lighting;
	private static Fog fog;
	
	private static final String DEFAULT_WINDOW_TEXT="JOGLFramework";
	private static final int DEFAULT_WINDOW_WIDTH=640;
	private static final int DEFAULT_WINDOW_HEIGHT=480;
	
	private static int fps=30;
	
	private static ColorU8 background_color=ColorU8Functions.GetColorU8(0.0f, 0.0f, 0.0f, 1.0f);
	
	//General
	public static void Initialize() {
		GLVersion gl_version=GLWrapper.GetGLVersion();
		
		GLCapabilities capabilities=null;
		String profile_str="";
		if(gl_version==GLVersion.GL3)profile_str=GLProfile.GL3bc;
		else if(gl_version==GLVersion.GL4)profile_str=GLProfile.GL4bc;
		else if(gl_version==GLVersion.GLES3)profile_str=GLProfile.GLES3;
		
		capabilities=new GLCapabilities(GLProfile.get(profile_str));
		
		LogFile.WriteInfo("[GLFront-Initialize] GL profile:"+profile_str, true);
		
		//Create a window.
		window=GLWindow.create(capabilities);
		window.setTitle(DEFAULT_WINDOW_TEXT);
		window.setSize(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);
		window.addWindowListener(new WindowAdapter() {
			@Override
			public void windowDestroyed(WindowEvent e) {
				System.exit(0);
			}
		});
		
		//Create an animator.
		animator=new FPSAnimator(fps);
		animator.add(window);
		animator.start();
		
		window.setVisible(true);
		
		//Setup keyboard and mouse.
		keyboard=new Keyboard();
		mouse=new Mouse();
		
		//Setup a camera.
		camera=new Camera();
		camera.SetupCamera_Perspective(MathFunctions.DegToRad(60.0f));
		
		//Setup lighting.
		lighting=new Lighting();
		
		//Setup fog.
		fog=new Fog();
		
		LogFile.WriteInfo("[GLFront-Initialize] Initialized.", true);
	}
	public static void Dispose() {
		if(animator!=null)animator.stop();
		
		LogFile.WriteInfo("[GLFront-Dispose] Disposed.",true);
	}
	public static void Reshape() {
		int window_x=window.getX();
		int window_y=window.getY();
		int window_width=window.getWidth();
		int window_height=window.getHeight();
		mouse.SetWindowPositionAndSize(window_x, window_y, window_width, window_height);
		
		camera.UpdateAspect();
	}
	public static void Update() {
		keyboard.Update();
		mouse.Update();
		
		camera.Update();
		
		lighting.Update();
		fog.Update();
	}
	
	public static void LoadDefaultShaders() {
		GLShaderFunctions.CreateProgram(
				"texture", 
				"./Data/Shader/330/texture/vshader.glsl",
				"./Data/Shader/330/texture/fshader.glsl");
		GLShaderFunctions.CreateProgram(
				"color",
				"./Data/Shader/330/color/vshader.glsl",
				"./Data/Shader/330/color/fshader.glsl");
		
		GLShaderFunctions.InitializeSampler();
		
		LogFile.WriteInfo("[GLFront-LoadDefaultShaders] Default shaders loaded.",true);
	}
	public static void SetDefaultGLProperties() {
		GL4Wrapper.glEnable(GL4.GL_DEPTH_TEST);
		GL4Wrapper.glDepthFunc(GL4.GL_LESS);
		
		GL4Wrapper.glEnable(GL4.GL_CULL_FACE);
		GL4Wrapper.glCullFace(GL4.GL_BACK);
		
		GL4Wrapper.glEnable(GL4.GL_BLEND);
		GL4Wrapper.glBlendFunc(GL4.GL_SRC_ALPHA, GL4.GL_ONE_MINUS_SRC_ALPHA);
		
		LogFile.WriteInfo("[GLFront-SetDefaultGLProperties] Default properties set.",true);
	}
	
	public static int GetFPS() {
		return fps;
	}
	
	//Listener
	public static void AddEventListener(GLEventListener event_listener) {
		if(window==null) {
			LogFile.WriteWarn("[GLFront-AddEventListener] Window is null.", true);
			return;
		}
		window.addGLEventListener(event_listener);
	}
	public static void AddKeyListener(KeyListener key_listener) {
		if(window==null) {
			LogFile.WriteWarn("[GLFront-AddKeyListener] Window is null.", true);
			return;
		}
		window.addKeyListener(key_listener);
	}
	public static void AddMouseListener(MouseListener mouse_listener) {
		if(window==null) {
			LogFile.WriteWarn("[GLFront-AddMouseListener] Window is null.", true);
			return;
		}
		window.addMouseListener(mouse_listener);
	}
	
	public static void onKeyPressed(KeyEvent e) {
		keyboard.keyPressed(e);
	}
	public static void onKeyReleased(KeyEvent e) {
		keyboard.keyReleased(e);
	}
	public static void onMouseClicked(MouseEvent e) {
		mouse.mouseClicked(e);
	}
	public static void onMouseDragged(MouseEvent e) {
		mouse.mouseDragged(e);
	}
	public static void onMouseEntered(MouseEvent e) {
		mouse.mouseEntered(e);
	}
	public static void onMouseExited(MouseEvent e) {
		mouse.mouseExited(e);
	}
	public static void onMouseMoved(MouseEvent e) {
		mouse.mouseMoved(e);
	}
	public static void onMousePressed(MouseEvent e) {
		mouse.mousePressed(e);
	}
	public static void onMouseReleased(MouseEvent e) {
		mouse.mouseReleased(e);
	}
	public static void onMouseWheelMoved(MouseEvent e) {
		mouse.mouseWheelMoved(e);
	}
	
	//Screen
	public static void ClearDrawScreen() {
		GL4Wrapper.glClearColor(
				background_color.GetR(),background_color.GetG(),
				background_color.GetB(),background_color.GetA());
		GL4Wrapper.glClear(GL4.GL_COLOR_BUFFER_BIT|GL4.GL_DEPTH_BUFFER_BIT);
	}
	
	//Cursor
	public static void HideCursor() {
		window.setPointerVisible(false);
	}
	public static void ShowCursor() {
		window.setPointerVisible(true);
	}
	
	//Window
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
	
	public static void SetBackgroundColor(ColorU8 color) {
		background_color=color;
	}
	
	public static void CloseWindow() {
		window.destroy();
	}
	
	//Camera
	public static void SetCameraNearFar(float near,float far) {
		camera.SetCameraNearFar(near, far);
	}
	public static void SetCameraPositionAndTarget_UpVecY(Vector position,Vector target) {
		camera.SetCameraPosition(position);
		camera.SetCameraTarget(target);
		camera.SetCameraUpVector(VectorFunctions.VGet(0.0f, 1.0f, 0.0f));
	}
	public static void SetCameraPositionAndTargetAndUpVec(Vector position,Vector target,Vector up) {
		camera.SetCameraPosition(position);
		camera.SetCameraTarget(target);
		camera.SetCameraUpVector(up);
	}
	public static void SetCameraViewMatrix(Matrix m) {
		camera.SetCameraViewMatrix(m);
	}
	public static void SetCameraPositionAndAngle(Vector position,float v_rotate,float h_rotate,float t_rotate) {
		Vector direction=new Vector();
		
		direction.SetX((float)Math.cos(h_rotate));
		direction.SetY((float)Math.sin(v_rotate));
		direction.SetZ(-(float)Math.sin(h_rotate));
		
		direction=VectorFunctions.VNorm(direction);
		
		Vector target=VectorFunctions.VAdd(position, direction);
		
		Matrix rot_direction=MatrixFunctions.MGetRotAxis(direction, t_rotate);
		Vector up=VectorFunctions.VTransform(VectorFunctions.VGet(0.0f, 1.0f, 0.0f), rot_direction);
		
		camera.SetCameraPosition(position);
		camera.SetCameraTarget(target);
		camera.SetCameraUpVector(up);
	}
	public static void SetupCamera_Perspective(float fov) {
		camera.SetupCamera_Perspective(fov);
	}
	public static void SetupCamera_Ortho(float size) {
		camera.SetupCamera_Ortho(size);
	}
	
	//Keyboard
	public static int GetKeyboardPressingCount(KeyboardEnum key) {
		return keyboard.GetPressingCount(key);
	}
	public static int GetKeyboardReleasingCount(KeyboardEnum key) {
		return keyboard.GetReleasingCount(key);
	}
	
	//Mouse
	public static int GetMousePressingCount(MouseEnum key) {
		return mouse.GetButtonPressingCount(key);
	}
	public static int GetMouseReleasingCount(MouseEnum key) {
		return mouse.GetButtonReleasingCount(key);
	}
	public static int GetMouseX() {
		return mouse.GetX();
	}
	public static int GetMouseY() {
		return mouse.GetY();
	}
	public static int GetMouseDiffX() {
		return mouse.GetDiffX();
	}
	public static int GetMouseDiffY() {
		return mouse.GetDiffY();
	}
	public static void SetFixMousePointerFlag(boolean fix_mouse_pointer_flag) {
		mouse.SetFixMousePointerFlag(fix_mouse_pointer_flag);
	}
}
