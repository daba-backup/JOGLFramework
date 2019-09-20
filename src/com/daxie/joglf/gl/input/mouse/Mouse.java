package com.daxie.joglf.gl.input.mouse;

import java.awt.AWTException;
import java.awt.Robot;

import com.daxie.joglf.gl.input.CountsAndFlags;
import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseListener;

/**
 * Mouse
 * @author Daba
 *
 */
public class Mouse implements MouseListener{
	private final int KEY_NUM=9;
	
	private CountsAndFlags mouse_buttons;
	
	private int x,y;
	private int diff_x,diff_y;
	
	private int window_x,window_y;//window position (screen coordinates).
	private int center_x,center_y;//center position of the window (client coordinates).
	private Robot robot;//is used to fix the mouse pointer.
	
	private boolean fix_mouse_pointer_flag;
	
	public Mouse() {
		mouse_buttons=new CountsAndFlags(KEY_NUM);
		
		x=0;
		y=0;
		diff_x=0;
		diff_y=0;
		
		window_x=0;
		window_y=0;
		center_x=0;
		center_y=0;
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
	public void SetWindowPositionAndSize(int x,int y,int width,int height) {
		window_x=x;
		window_y=y;
		center_x=width/2;
		center_y=height/2;
	}
	public void Update() {
		mouse_buttons.UpdateCounts();
		
		if(fix_mouse_pointer_flag==true) {
			diff_x=x-center_x;
			diff_y=y-center_y;
		}
		else {
			diff_x=0;
			diff_y=0;
		}
		
		if(fix_mouse_pointer_flag==true) {
			robot.mouseMove(window_x+center_x,window_y+center_y);
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
		case MouseEvent.BUTTON4:
			mouse_buttons.SetPressingFlag(3, true);
			break;
		case MouseEvent.BUTTON5:
			mouse_buttons.SetPressingFlag(4, true);
			break;
		case MouseEvent.BUTTON6:
			mouse_buttons.SetPressingFlag(5, true);
			break;
		case MouseEvent.BUTTON7:
			mouse_buttons.SetPressingFlag(6, true);
			break;
		case MouseEvent.BUTTON8:
			mouse_buttons.SetPressingFlag(7, true);
			break;
		case MouseEvent.BUTTON9:
			mouse_buttons.SetPressingFlag(8, true);
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
		case MouseEvent.BUTTON4:
			mouse_buttons.SetPressingFlag(3, false);
			break;
		case MouseEvent.BUTTON5:
			mouse_buttons.SetPressingFlag(4, false);
			break;
		case MouseEvent.BUTTON6:
			mouse_buttons.SetPressingFlag(5, false);
			break;
		case MouseEvent.BUTTON7:
			mouse_buttons.SetPressingFlag(6, false);
			break;
		case MouseEvent.BUTTON8:
			mouse_buttons.SetPressingFlag(7, false);
			break;
		case MouseEvent.BUTTON9:
			mouse_buttons.SetPressingFlag(8, false);
			break;
		default:
				break;
		}
	}
	@Override
	public void mouseWheelMoved(MouseEvent e) {
		
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
		case MOUSE_BUTTON_4:
			count=mouse_buttons.GetPressingCount(3);
			break;
		case MOUSE_BUTTON_5:
			count=mouse_buttons.GetPressingCount(4);
			break;
		case MOUSE_BUTTON_6:
			count=mouse_buttons.GetPressingCount(5);
			break;
		case MOUSE_BUTTON_7:
			count=mouse_buttons.GetPressingCount(6);
			break;
		case MOUSE_BUTTON_8:
			count=mouse_buttons.GetPressingCount(7);
			break;
		case MOUSE_BUTTON_9:
			count=mouse_buttons.GetPressingCount(8);
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
		case MOUSE_BUTTON_4:
			count=mouse_buttons.GetReleasingCount(3);
			break;
		case MOUSE_BUTTON_5:
			count=mouse_buttons.GetReleasingCount(4);
			break;
		case MOUSE_BUTTON_6:
			count=mouse_buttons.GetReleasingCount(5);
			break;
		case MOUSE_BUTTON_7:
			count=mouse_buttons.GetReleasingCount(6);
			break;
		case MOUSE_BUTTON_8:
			count=mouse_buttons.GetReleasingCount(7);
			break;
		case MOUSE_BUTTON_9:
			count=mouse_buttons.GetReleasingCount(8);
			break;
		default:
			count=0;
			break;
		}
		
		return count;
	}
}
