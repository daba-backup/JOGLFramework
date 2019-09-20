package com.daxie.joglf.main;

import com.daxie.joglf.al.front.ALFront;
import com.daxie.joglf.gl.front.CameraFront;
import com.daxie.joglf.gl.front.FogFront;
import com.daxie.joglf.gl.front.GLFront;
import com.daxie.joglf.gl.front.KeyboardFront;
import com.daxie.joglf.gl.front.LightingFront;
import com.daxie.joglf.gl.front.MouseFront;
import com.daxie.joglf.gl.front.WindowFront;
import com.daxie.joglf.gl.text.TextMgr;
import com.daxie.joglf.gl.texture.TextureMgr;
import com.daxie.joglf.gl.wrapper.GLVersion;
import com.daxie.joglf.gl.wrapper.GLWrapper;
import com.daxie.log.LogFile;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseListener;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;

/**
 * Base class for Main<br>
 * Extend this class and make a main class with the main() method in it.
 * @author Daba
 *
 */
public class MainBase implements GLEventListener,KeyListener,MouseListener{
	public MainBase(GLVersion gl_version) {
		GLWrapper.SetGLVersion(gl_version);
		
		LogFile.SetLogLevelFlags(LogFile.LOG_LEVEL_FATAL|LogFile.LOG_LEVEL_ERROR);
		
		WindowFront.Initialize();
		
		WindowFront.AddEventListener(this);
		WindowFront.AddKeyListener(this);
		WindowFront.AddMouseListener(this);
	}
	
	@Override
	public void init(GLAutoDrawable drawable) {
		ALFront.Initialize();
		
		GLFront.LoadDefaultShaders();
		GLFront.SetDefaultGLProperties();
		
		TextureMgr.LoadDefaultTexture();
		TextMgr.Initialize();
	}
	@Override
	public void reshape(GLAutoDrawable drawable,int x,int y,int width,int height) {
		CameraFront.Reshape();
	}
	@Override
	public void display(GLAutoDrawable drawable) {
		WindowFront.ClearDrawScreen();
		
		KeyboardFront.Update();
		MouseFront.Update();
		
		CameraFront.Update();
		
		LightingFront.Update();
		FogFront.Update();
	}
	@Override
	public void dispose(GLAutoDrawable drawable) {
		//GLFront.Dispose();
		//ALFront.Dispose();
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		KeyboardFront.onKeyPressed(e);
	}
	@Override
	public void keyReleased(KeyEvent e) {
		KeyboardFront.onKeyReleased(e);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		MouseFront.onMouseClicked(e);
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		MouseFront.onMouseDragged(e);
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		MouseFront.onMouseEntered(e);
	}
	@Override
	public void mouseExited(MouseEvent e) {
		MouseFront.onMouseExited(e);
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		MouseFront.onMouseMoved(e);
	}
	@Override
	public void mousePressed(MouseEvent e) {
		MouseFront.onMousePressed(e);
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		MouseFront.onMouseReleased(e);
	}
	@Override
	public void mouseWheelMoved(MouseEvent e) {
		MouseFront.onMouseWheelMoved(e);
	}
}
