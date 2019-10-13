package com.daxie.joglf.gl.input.mouse;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import com.daxie.joglf.gl.input.CountsAndFlags;

/**
 * Mouse for a Swing window
 * @author Daba
 *
 */
public class SwingMouse implements MouseListener,MouseMotionListener,MouseWheelListener{
	private final int KEY_NUM=3;
	
	private CountsAndFlags mouse_buttons;
	
	private int window_x,window_y;
	
	private int x,y;
	private int diff_x,diff_y;
	
	private float rotation;
	
	private int fix_pos_x;
	private int fix_pos_y;
	private Robot robot;//is used to fix the mouse pointer.
	
	private boolean fix_mouse_pointer_flag;
	
	public SwingMouse() {
		mouse_buttons=new CountsAndFlags(KEY_NUM);
		
		window_x=0;
		window_y=0;
		
		x=0;
		y=0;
		diff_x=0;
		diff_y=0;
		
		rotation=0.0f;
		
		fix_pos_x=0;
		fix_pos_y=0;
		try {
			robot=new Robot();
		}
		catch(AWTException e) {
			e.printStackTrace();
		}
		
		fix_mouse_pointer_flag=false;
	}
	
	public void SetFixMousePointerFlag(boolean fix_mouse_pointer_flag) {
		this.fix_mouse_pointer_flag=fix_mouse_pointer_flag;
	}
	
	public void SetWindowPosition(int window_x,int window_y) {
		this.window_x=window_x;
		this.window_y=window_y;
	}
	public void SetFixMousePointerPosition(int x,int y) {
		fix_pos_x=x;
		fix_pos_y=y;
	}
	public void Update() {
		mouse_buttons.UpdateCounts();
		
		if(fix_mouse_pointer_flag==true) {
			diff_x=x-fix_pos_x;
			diff_y=y-fix_pos_y;
		}
		else {
			diff_x=0;
			diff_y=0;
		}
		
		if(fix_mouse_pointer_flag==true) {
			robot.mouseMove(window_x+fix_pos_x,window_y+fix_pos_y);
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		this.mouseMoved(e);
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		x=e.getX();
		y=e.getY();
	}
	@Override
	public void mousePressed(MouseEvent e) {
		switch(e.getButton()) {
		case MouseEvent.BUTTON1:
			mouse_buttons.SetPressingFlag(0, true);
			break;
		case MouseEvent.BUTTON2:
			mouse_buttons.SetPressingFlag(1, true);
			break;
		case MouseEvent.BUTTON3:
			mouse_buttons.SetPressingFlag(2, true);
			break;
		default:
				break;
		}
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		switch(e.getButton()) {
		case MouseEvent.BUTTON1:
			mouse_buttons.SetPressingFlag(0, false);
			break;
		case MouseEvent.BUTTON2:
			mouse_buttons.SetPressingFlag(1, false);
			break;
		case MouseEvent.BUTTON3:
			mouse_buttons.SetPressingFlag(2, false);
			break;
		default:
				break;
		}
	}
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		rotation=(float)e.getWheelRotation()*(-1.0f);
	}
	
	public int GetX() {
		return x;
	}
	public int GetY() {
		return y;
	}
	public int GetDiffX() {
		return diff_x;
	}
	public int GetDiffY() {
		return diff_y;
	}
	public float GetHorizontalRotation() {
		float rotation=this.rotation;
		this.rotation=0.0f;
		
		return rotation;
	}
	
	public int GetButtonPressingCount(MouseEnum key) {
		int count;
		
		switch(key) {
		case MOUSE_LEFT:
			count=mouse_buttons.GetPressingCount(0);
			break;
		case MOUSE_MIDDLE:
			count=mouse_buttons.GetPressingCount(1);
			break;
		case MOUSE_RIGHT:
			count=mouse_buttons.GetPressingCount(2);
			break;
		default:
			count=0;
			break;
		}
		
		return count;
	}
	public int GetButtonReleasingCount(MouseEnum key) {
		int count;
		
		switch(key) {
		case MOUSE_LEFT:
			count=mouse_buttons.GetReleasingCount(0);
			break;
		case MOUSE_MIDDLE:
			count=mouse_buttons.GetReleasingCount(1);
			break;
		case MOUSE_RIGHT:
			count=mouse_buttons.GetReleasingCount(2);
			break;
		default:
			count=0;
			break;
		}
		
		return count;
	}
}
