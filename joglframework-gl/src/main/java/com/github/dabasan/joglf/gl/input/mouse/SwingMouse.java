package com.github.dabasan.joglf.gl.input.mouse;

import java.awt.MouseInfo;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import com.github.dabasan.joglf.gl.input.CountsAndFlags;

/**
 * Mouse for a Swing window
 * 
 * @author Daba
 *
 */
public class SwingMouse implements MouseListener, MouseMotionListener, MouseWheelListener {
	private final int KEY_NUM = 3;

	private final CountsAndFlags mouse_buttons;

	private int last_cursor_pos_x, last_cursor_pos_y;
	private int cursor_diff_x, cursor_diff_y;
	private float rotation;

	public SwingMouse() {
		mouse_buttons = new CountsAndFlags(KEY_NUM);

		last_cursor_pos_x = MouseInfo.getPointerInfo().getLocation().x;
		last_cursor_pos_y = MouseInfo.getPointerInfo().getLocation().y;
		cursor_diff_x = 0;
		cursor_diff_y = 0;

		rotation = 0.0f;
	}

	public void Reset() {
		mouse_buttons.Reset();
	}

	public void Update() {
		mouse_buttons.UpdateCounts();

		final int cursor_pos_x = MouseInfo.getPointerInfo().getLocation().x;
		final int cursor_pos_y = MouseInfo.getPointerInfo().getLocation().y;
		cursor_diff_x = cursor_pos_x - last_cursor_pos_x;
		cursor_diff_y = cursor_pos_y - last_cursor_pos_y;
		last_cursor_pos_x = cursor_pos_x;
		last_cursor_pos_y = cursor_pos_y;
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
		switch (e.getButton()) {
			case MouseEvent.BUTTON1 :
				mouse_buttons.SetPressingFlag(0, true);
				break;
			case MouseEvent.BUTTON2 :
				mouse_buttons.SetPressingFlag(1, true);
				break;
			case MouseEvent.BUTTON3 :
				mouse_buttons.SetPressingFlag(2, true);
				break;
			default :
				break;
		}
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		switch (e.getButton()) {
			case MouseEvent.BUTTON1 :
				mouse_buttons.SetPressingFlag(0, false);
				break;
			case MouseEvent.BUTTON2 :
				mouse_buttons.SetPressingFlag(1, false);
				break;
			case MouseEvent.BUTTON3 :
				mouse_buttons.SetPressingFlag(2, false);
				break;
			default :
				break;
		}
	}
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		rotation = e.getWheelRotation() * (-1.0f);
	}

	public int GetDiffX() {
		return cursor_diff_x;
	}
	public int GetDiffY() {
		return cursor_diff_y;
	}
	public float GetHorizontalRotation() {
		final float rotation = this.rotation;
		this.rotation = 0.0f;

		return rotation;
	}

	public int GetButtonPressingCount(MouseEnum key) {
		int count;

		switch (key) {
			case MOUSE_LEFT :
				count = mouse_buttons.GetPressingCount(0);
				break;
			case MOUSE_MIDDLE :
				count = mouse_buttons.GetPressingCount(1);
				break;
			case MOUSE_RIGHT :
				count = mouse_buttons.GetPressingCount(2);
				break;
			default :
				count = 0;
				break;
		}

		return count;
	}
	public int GetButtonReleasingCount(MouseEnum key) {
		int count;

		switch (key) {
			case MOUSE_LEFT :
				count = mouse_buttons.GetReleasingCount(0);
				break;
			case MOUSE_MIDDLE :
				count = mouse_buttons.GetReleasingCount(1);
				break;
			case MOUSE_RIGHT :
				count = mouse_buttons.GetReleasingCount(2);
				break;
			default :
				count = 0;
				break;
		}

		return count;
	}
}
