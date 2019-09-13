package com.daxie.joglf.main;

import com.daxie.joglf.al.ALFront;
import com.daxie.joglf.gl.GLFront;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseListener;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;

public class MainBase implements GLEventListener,KeyListener,MouseListener{
	public MainBase() {
		GLFront.Initialize();
		ALFront.Initialize();
		
		GLFront.AddEventListener(this);
		GLFront.AddKeyListener(this);
		GLFront.AddMouseListener(this);
	}
	
	@Override
	public void init(GLAutoDrawable drawable) {
		GLFront.LoadDefaultShaders();
		GLFront.SetDefaultGLProperties();
	}
	@Override
	public void reshape(GLAutoDrawable drawable,int x,int y,int width,int height) {
		GLFront.Reshape();
	}
	@Override
	public void display(GLAutoDrawable drawable) {
		GLFront.ClearDrawScreen();
		GLFront.Update();
	}
	@Override
	public void dispose(GLAutoDrawable drawable) {
		GLFront.Dispose();
		ALFront.Dispose();
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		GLFront.onKeyPressed(e);
	}
	@Override
	public void keyReleased(KeyEvent e) {
		GLFront.onKeyReleased(e);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		GLFront.onMouseClicked(e);
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		GLFront.onMouseDragged(e);
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		GLFront.onMouseEntered(e);
	}
	@Override
	public void mouseExited(MouseEvent e) {
		GLFront.onMouseExited(e);
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		GLFront.onMouseMoved(e);
	}
	@Override
	public void mousePressed(MouseEvent e) {
		GLFront.onMousePressed(e);
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		GLFront.onMouseReleased(e);
	}
	@Override
	public void mouseWheelMoved(MouseEvent e) {
		GLFront.onMouseWheelMoved(e);
	}
}
