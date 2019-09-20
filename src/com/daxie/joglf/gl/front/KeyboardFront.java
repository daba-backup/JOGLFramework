package com.daxie.joglf.gl.front;

import com.daxie.joglf.gl.input.Keyboard;
import com.daxie.joglf.gl.input.KeyboardEnum;
import com.jogamp.newt.event.KeyEvent;

/**
 * Offers methods for keyboard operations.
 * @author Daba
 *
 */
public class KeyboardFront {
	private static Keyboard keyboard=new Keyboard();
	
	public static int GetKeyboardPressingCount(KeyboardEnum key) {
		return keyboard.GetPressingCount(key);
	}
	public static int GetKeyboardReleasingCount(KeyboardEnum key) {
		return keyboard.GetReleasingCount(key);
	}
	
	public static void onKeyPressed(KeyEvent e) {
		keyboard.keyPressed(e);
	}
	public static void onKeyReleased(KeyEvent e) {
		keyboard.keyReleased(e);
	}
	
	public static void Update() {
		keyboard.Update();
	}
}
