package com.github.dabasan.joglf.gl.input.mouse;

import java.awt.MouseInfo;

import com.github.dabasan.joglf.gl.input.CountsAndFlags;
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
	
	private int last_cursor_pos_x,last_cursor_pos_y;
	private int cursor_diff_x,cursor_diff_y;
	private float[] rotations;
	
	public Mouse() {
		mouse_buttons=new CountsAndFlags(KEY_NUM);
		
		last_cursor_pos_x=MouseInfo.getPointerInfo().getLocation().x;
		last_cursor_pos_y=MouseInfo.getPointerInfo().getLocation().y;
		cursor_diff_x=0;
		cursor_diff_y=0;
		
		rotations=new float[]{0.0f,0.0f,0.0f};
	}
	
	public void Update() {
		mouse_buttons.UpdateCounts();
		
		int cursor_pos_x=MouseInfo.getPointerInfo().getLocation().x;
		int cursor_pos_y=MouseInfo.getPointerInfo().getLocation().y;
		cursor_diff_x=cursor_pos_x-last_cursor_pos_x;
		cursor_diff_y=cursor_pos_y-last_cursor_pos_y;
		last_cursor_pos_x=cursor_pos_x;
		last_cursor_pos_y=cursor_pos_y;
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
		rotations=e.getRotation();
	}
	
	public int GetDiffX() {
		return cursor_diff_x;
	}
	public int GetDiffY() {
		return cursor_diff_y;
	}
	public float GetHorizontalRotation() {
		float rotation=rotations[0];
		rotations[0]=0.0f;
		
		return rotation;
	}
	public float GetVerticalRotation() {
		float rotation=rotations[1];
		rotations[1]=0.0f;
		
		return rotation;
	}
	public float GetZAxisRotation() {
		float rotation=rotations[2];
		rotations[2]=0.0f;
		
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
