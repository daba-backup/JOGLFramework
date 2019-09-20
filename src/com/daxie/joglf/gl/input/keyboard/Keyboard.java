package com.daxie.joglf.gl.input.keyboard;

import com.daxie.joglf.gl.input.CountsAndFlags;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;

/**
 * Keyboard
 * @author Daba
 *
 */
public class Keyboard implements KeyListener{
	private final int ALPHABET_NUM=26;
	private final int NUM_KEY_NUM=10;
	private final int FUNCTION_KEY_NUM=12;
	
	private final int KEY_ESC=0x1b;
	private final int KEY_SPACE=0x20;
	
	private CountsAndFlags alphabet_keys;
	private CountsAndFlags num_keys;
	private CountsAndFlags function_keys;
	private CountsAndFlags space_key;
	private CountsAndFlags escape_key;
	private CountsAndFlags shift_key;
	private CountsAndFlags ctrl_key;
	private CountsAndFlags alt_key;
	
	public Keyboard() {
		alphabet_keys=new CountsAndFlags(ALPHABET_NUM);
		num_keys=new CountsAndFlags(NUM_KEY_NUM);
		function_keys=new CountsAndFlags(FUNCTION_KEY_NUM);
		space_key=new CountsAndFlags(1);
		escape_key=new CountsAndFlags(1);
		shift_key=new CountsAndFlags(1);
		ctrl_key=new CountsAndFlags(1);
		alt_key=new CountsAndFlags(1);
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.isAutoRepeat()==true)return;
		
		char key_char=e.getKeyChar();
		
		//Alphabet keys
		char ch_temp;
		
		ch_temp='A';
		for(int i=0;i<ALPHABET_NUM;i++) {
			if(key_char==ch_temp)alphabet_keys.SetPressingFlag(i, true);
			ch_temp++;
		}
		
		ch_temp='a';
		for(int i=0;i<ALPHABET_NUM;i++) {
			if(key_char==ch_temp)alphabet_keys.SetPressingFlag(i, true);
			ch_temp++;
		}
		
		//Num keys and Function keys
		switch(e.getKeyCode()) {
		case KeyEvent.VK_0:
			num_keys.SetPressingFlag(0, true);
			break;
		case KeyEvent.VK_1:
			num_keys.SetPressingFlag(1, true);
			break;
		case KeyEvent.VK_2:
			num_keys.SetPressingFlag(2, true);
			break;
		case KeyEvent.VK_3:
			num_keys.SetPressingFlag(3, true);
			break;
		case KeyEvent.VK_4:
			num_keys.SetPressingFlag(4, true);
			break;
		case KeyEvent.VK_5:
			num_keys.SetPressingFlag(5, true);
			break;
		case KeyEvent.VK_6:
			num_keys.SetPressingFlag(6, true);
			break;
		case KeyEvent.VK_7:
			num_keys.SetPressingFlag(7, true);
			break;
		case KeyEvent.VK_8:
			num_keys.SetPressingFlag(8, true);
			break;
		case KeyEvent.VK_9:
			num_keys.SetPressingFlag(9, true);
			break;
		case KeyEvent.VK_F1:
			function_keys.SetPressingFlag(0, true);
			break;
		case KeyEvent.VK_F2:
			function_keys.SetPressingFlag(1, true);
			break;
		case KeyEvent.VK_F3:
			function_keys.SetPressingFlag(2, true);
			break;
		case KeyEvent.VK_F4:
			function_keys.SetPressingFlag(3, true);
			break;
		case KeyEvent.VK_F5:
			function_keys.SetPressingFlag(4, true);
			break;
		case KeyEvent.VK_F6:
			function_keys.SetPressingFlag(5, true);
			break;
		case KeyEvent.VK_F7:
			function_keys.SetPressingFlag(6, true);
			break;
		case KeyEvent.VK_F8:
			function_keys.SetPressingFlag(7, true);
			break;
		case KeyEvent.VK_F9:
			function_keys.SetPressingFlag(8, true);
			break;
		case KeyEvent.VK_F10:
			function_keys.SetPressingFlag(9, true);
			break;
		case KeyEvent.VK_F11:
			function_keys.SetPressingFlag(10, true);
			break;
		case KeyEvent.VK_F12:
			function_keys.SetPressingFlag(11, true);
			break;
		default:
			break;
		}
		
		//Space key
		if(key_char==KEY_SPACE) {
			space_key.SetPressingFlag(0, true);
		}
		
		//ESC key
		if(key_char==KEY_ESC) {
			escape_key.SetPressingFlag(0, true);
		}
		
		//Shift key
		if(e.isShiftDown()==true) {
			shift_key.SetPressingFlag(0, true);
		}
		
		//Ctrl key
		if(e.isControlDown()==true) {
			ctrl_key.SetPressingFlag(0, true);
		}
		
		//Alt key
		if(e.isAltDown()==true) {
			alt_key.SetPressingFlag(0, true);
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		if(e.isAutoRepeat()==true)return;
		
		char key_char=e.getKeyChar();
		
		//Alphabet keys
		char ch_temp;
		
		ch_temp='A';
		for(int i=0;i<ALPHABET_NUM;i++) {
			if(key_char==ch_temp)alphabet_keys.SetPressingFlag(i, false);
			ch_temp++;
		}
		
		ch_temp='a';
		for(int i=0;i<ALPHABET_NUM;i++) {
			if(key_char==ch_temp)alphabet_keys.SetPressingFlag(i, false);
			ch_temp++;
		}
		
		//Num keys and Function keys
		switch(e.getKeyCode()) {
		case KeyEvent.VK_0:
			num_keys.SetPressingFlag(0, false);
			break;
		case KeyEvent.VK_1:
			num_keys.SetPressingFlag(1, false);
			break;
		case KeyEvent.VK_2:
			num_keys.SetPressingFlag(2, false);
			break;
		case KeyEvent.VK_3:
			num_keys.SetPressingFlag(3, false);
			break;
		case KeyEvent.VK_4:
			num_keys.SetPressingFlag(4, false);
			break;
		case KeyEvent.VK_5:
			num_keys.SetPressingFlag(5, false);
			break;
		case KeyEvent.VK_6:
			num_keys.SetPressingFlag(6, false);
			break;
		case KeyEvent.VK_7:
			num_keys.SetPressingFlag(7, false);
			break;
		case KeyEvent.VK_8:
			num_keys.SetPressingFlag(8, false);
			break;
		case KeyEvent.VK_9:
			num_keys.SetPressingFlag(9, false);
			break;
		case KeyEvent.VK_F1:
			function_keys.SetPressingFlag(0, false);
			break;
		case KeyEvent.VK_F2:
			function_keys.SetPressingFlag(1, false);
			break;
		case KeyEvent.VK_F3:
			function_keys.SetPressingFlag(2, false);
			break;
		case KeyEvent.VK_F4:
			function_keys.SetPressingFlag(3, false);
			break;
		case KeyEvent.VK_F5:
			function_keys.SetPressingFlag(4, false);
			break;
		case KeyEvent.VK_F6:
			function_keys.SetPressingFlag(5, false);
			break;
		case KeyEvent.VK_F7:
			function_keys.SetPressingFlag(6, false);
			break;
		case KeyEvent.VK_F8:
			function_keys.SetPressingFlag(7, false);
			break;
		case KeyEvent.VK_F9:
			function_keys.SetPressingFlag(8, false);
			break;
		case KeyEvent.VK_F10:
			function_keys.SetPressingFlag(9, false);
			break;
		case KeyEvent.VK_F11:
			function_keys.SetPressingFlag(10, false);
			break;
		case KeyEvent.VK_F12:
			function_keys.SetPressingFlag(11, false);
			break;
		default:
			break;
		}
		
		//Space key
		if(key_char==KEY_SPACE) {
			space_key.SetPressingFlag(0, false);
		}
		
		//ESC key
		if(key_char==KEY_ESC) {
			escape_key.SetPressingFlag(0, false);
		}
		
		//Shift key
		if(e.isShiftDown()==false) {
			shift_key.SetPressingFlag(0, false);
		}
		
		//Ctrl key
		if(e.isControlDown()==false) {
			ctrl_key.SetPressingFlag(0, false);
		}
		
		//Alt key
		if(e.isAltDown()==true) {
			alt_key.SetPressingFlag(0, false);
		}
	}
	
	public void Update() {
		alphabet_keys.UpdateCounts();
		num_keys.UpdateCounts();
		function_keys.UpdateCounts();
		space_key.UpdateCounts();
		escape_key.UpdateCounts();
		shift_key.UpdateCounts();
		ctrl_key.UpdateCounts();
		alt_key.UpdateCounts();
	}
	
	public int GetPressingCount(KeyboardEnum key) {
		int count;
		
		switch(key) {
		case KEY_A:
			count=alphabet_keys.GetPressingCount(0);
			break;
		case KEY_B:
			count=alphabet_keys.GetPressingCount(1);
			break;
		case KEY_C:
			count=alphabet_keys.GetPressingCount(2);
			break;
		case KEY_D:
			count=alphabet_keys.GetPressingCount(3);
			break;
		case KEY_E:
			count=alphabet_keys.GetPressingCount(4);
			break;
		case KEY_F:
			count=alphabet_keys.GetPressingCount(5);
			break;
		case KEY_G:
			count=alphabet_keys.GetPressingCount(6);
			break;
		case KEY_H:
			count=alphabet_keys.GetPressingCount(7);
			break;
		case KEY_I:
			count=alphabet_keys.GetPressingCount(8);
			break;
		case KEY_J:
			count=alphabet_keys.GetPressingCount(9);
			break;
		case KEY_K:
			count=alphabet_keys.GetPressingCount(10);
			break;
		case KEY_L:
			count=alphabet_keys.GetPressingCount(11);
			break;
		case KEY_M:
			count=alphabet_keys.GetPressingCount(12);
			break;
		case KEY_N:
			count=alphabet_keys.GetPressingCount(13);
			break;
		case KEY_O:
			count=alphabet_keys.GetPressingCount(14);
			break;
		case KEY_P:
			count=alphabet_keys.GetPressingCount(15);
			break;
		case KEY_Q:
			count=alphabet_keys.GetPressingCount(16);
			break;
		case KEY_R:
			count=alphabet_keys.GetPressingCount(17);
			break;
		case KEY_S:
			count=alphabet_keys.GetPressingCount(18);
			break;
		case KEY_T:
			count=alphabet_keys.GetPressingCount(19);
			break;
		case KEY_U:
			count=alphabet_keys.GetPressingCount(20);
			break;
		case KEY_V:
			count=alphabet_keys.GetPressingCount(21);
			break;
		case KEY_W:
			count=alphabet_keys.GetPressingCount(22);
			break;
		case KEY_X:
			count=alphabet_keys.GetPressingCount(23);
			break;
		case KEY_Y:
			count=alphabet_keys.GetPressingCount(24);
			break;
		case KEY_Z:
			count=alphabet_keys.GetPressingCount(25);
			break;
		case KEY_0:
			count=num_keys.GetPressingCount(0);
			break;
		case KEY_1:
			count=num_keys.GetPressingCount(1);
			break;
		case KEY_2:
			count=num_keys.GetPressingCount(2);
			break;
		case KEY_3:
			count=num_keys.GetPressingCount(3);
			break;
		case KEY_4:
			count=num_keys.GetPressingCount(4);
			break;
		case KEY_5:
			count=num_keys.GetPressingCount(5);
			break;
		case KEY_6:
			count=num_keys.GetPressingCount(6);
			break;
		case KEY_7:
			count=num_keys.GetPressingCount(7);
			break;
		case KEY_8:
			count=num_keys.GetPressingCount(8);
			break;
		case KEY_9:
			count=num_keys.GetPressingCount(9);
			break;
		case KEY_F1:
			count=function_keys.GetPressingCount(0);
			break;
		case KEY_F2:
			count=function_keys.GetPressingCount(1);
			break;
		case KEY_F3:
			count=function_keys.GetPressingCount(2);
			break;
		case KEY_F4:
			count=function_keys.GetPressingCount(3);
			break;
		case KEY_F5:
			count=function_keys.GetPressingCount(4);
			break;
		case KEY_F6:
			count=function_keys.GetPressingCount(5);
			break;
		case KEY_F7:
			count=function_keys.GetPressingCount(6);
			break;
		case KEY_F8:
			count=function_keys.GetPressingCount(7);
			break;
		case KEY_F9:
			count=function_keys.GetPressingCount(8);
			break;
		case KEY_F10:
			count=function_keys.GetPressingCount(9);
			break;
		case KEY_F11:
			count=function_keys.GetPressingCount(10);
			break;
		case KEY_F12:
			count=function_keys.GetPressingCount(11);
			break;
		case KEY_SPACE:
			count=space_key.GetPressingCount(0);
			break;
		case KEY_ESC:
			count=escape_key.GetPressingCount(0);
			break;
		case KEY_SHIFT:
			count=shift_key.GetPressingCount(0);
			break;
		case KEY_CTRL:
			count=ctrl_key.GetPressingCount(0);
			break;
		case KEY_ALT:
			count=alt_key.GetPressingCount(0);
			break;
		default:
			count=0;
			break;
		}
		
		return count;
	}
	public int GetReleasingCount(KeyboardEnum key) {
		int count;
		
		switch(key) {
		case KEY_A:
			count=alphabet_keys.GetReleasingCount(0);
			break;
		case KEY_B:
			count=alphabet_keys.GetReleasingCount(1);
			break;
		case KEY_C:
			count=alphabet_keys.GetReleasingCount(2);
			break;
		case KEY_D:
			count=alphabet_keys.GetReleasingCount(3);
			break;
		case KEY_E:
			count=alphabet_keys.GetReleasingCount(4);
			break;
		case KEY_F:
			count=alphabet_keys.GetReleasingCount(5);
			break;
		case KEY_G:
			count=alphabet_keys.GetReleasingCount(6);
			break;
		case KEY_H:
			count=alphabet_keys.GetReleasingCount(7);
			break;
		case KEY_I:
			count=alphabet_keys.GetReleasingCount(8);
			break;
		case KEY_J:
			count=alphabet_keys.GetReleasingCount(9);
			break;
		case KEY_K:
			count=alphabet_keys.GetReleasingCount(10);
			break;
		case KEY_L:
			count=alphabet_keys.GetReleasingCount(11);
			break;
		case KEY_M:
			count=alphabet_keys.GetReleasingCount(12);
			break;
		case KEY_N:
			count=alphabet_keys.GetReleasingCount(13);
			break;
		case KEY_O:
			count=alphabet_keys.GetReleasingCount(14);
			break;
		case KEY_P:
			count=alphabet_keys.GetReleasingCount(15);
			break;
		case KEY_Q:
			count=alphabet_keys.GetReleasingCount(16);
			break;
		case KEY_R:
			count=alphabet_keys.GetReleasingCount(17);
			break;
		case KEY_S:
			count=alphabet_keys.GetReleasingCount(18);
			break;
		case KEY_T:
			count=alphabet_keys.GetReleasingCount(19);
			break;
		case KEY_U:
			count=alphabet_keys.GetReleasingCount(20);
			break;
		case KEY_V:
			count=alphabet_keys.GetReleasingCount(21);
			break;
		case KEY_W:
			count=alphabet_keys.GetReleasingCount(22);
			break;
		case KEY_X:
			count=alphabet_keys.GetReleasingCount(23);
			break;
		case KEY_Y:
			count=alphabet_keys.GetReleasingCount(24);
			break;
		case KEY_Z:
			count=alphabet_keys.GetReleasingCount(25);
			break;
		case KEY_0:
			count=num_keys.GetReleasingCount(0);
			break;
		case KEY_1:
			count=num_keys.GetReleasingCount(1);
			break;
		case KEY_2:
			count=num_keys.GetReleasingCount(2);
			break;
		case KEY_3:
			count=num_keys.GetReleasingCount(3);
			break;
		case KEY_4:
			count=num_keys.GetReleasingCount(4);
			break;
		case KEY_5:
			count=num_keys.GetReleasingCount(5);
			break;
		case KEY_6:
			count=num_keys.GetReleasingCount(6);
			break;
		case KEY_7:
			count=num_keys.GetReleasingCount(7);
			break;
		case KEY_8:
			count=num_keys.GetReleasingCount(8);
			break;
		case KEY_9:
			count=num_keys.GetReleasingCount(9);
			break;
		case KEY_F1:
			count=function_keys.GetReleasingCount(0);
			break;
		case KEY_F2:
			count=function_keys.GetReleasingCount(1);
			break;
		case KEY_F3:
			count=function_keys.GetReleasingCount(2);
			break;
		case KEY_F4:
			count=function_keys.GetReleasingCount(3);
			break;
		case KEY_F5:
			count=function_keys.GetReleasingCount(4);
			break;
		case KEY_F6:
			count=function_keys.GetReleasingCount(5);
			break;
		case KEY_F7:
			count=function_keys.GetReleasingCount(6);
			break;
		case KEY_F8:
			count=function_keys.GetReleasingCount(7);
			break;
		case KEY_F9:
			count=function_keys.GetReleasingCount(8);
			break;
		case KEY_F10:
			count=function_keys.GetReleasingCount(9);
			break;
		case KEY_F11:
			count=function_keys.GetReleasingCount(10);
			break;
		case KEY_F12:
			count=function_keys.GetReleasingCount(11);
			break;
		case KEY_SPACE:
			count=space_key.GetReleasingCount(0);
			break;
		case KEY_ESC:
			count=escape_key.GetReleasingCount(0);
			break;
		case KEY_SHIFT:
			count=shift_key.GetReleasingCount(0);
			break;
		case KEY_CTRL:
			count=ctrl_key.GetReleasingCount(0);
			break;
		case KEY_ALT:
			count=alt_key.GetReleasingCount(0);
			break;
		default:
			count=0;
			break;
		}
		
		return count;
	}
}
