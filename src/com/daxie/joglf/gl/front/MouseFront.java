package com.daxie.joglf.gl.front;

import com.daxie.joglf.gl.input.mouse.Mouse;
import com.daxie.joglf.gl.input.mouse.MouseEnum;
import com.jogamp.newt.event.MouseEvent;

/**
 * Offers methods for mouse operations.
 * @author Daba
 *
 */
public class MouseFront {
	private static Mouse mouse=new Mouse();
	
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
	
	public static void Update() {
		int x=WindowFront.GetWindowX();
		int y=WindowFront.GetWindowY();
		int width=WindowFront.GetWindowWidth();
		int height=WindowFront.GetWindowHeight();
		
		mouse.SetWindowPositionAndSize(x, y, width, height);
		
		mouse.Update();
	}
}
