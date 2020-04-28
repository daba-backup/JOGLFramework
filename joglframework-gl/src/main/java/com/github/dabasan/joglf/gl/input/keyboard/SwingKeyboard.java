package com.github.dabasan.joglf.gl.input.keyboard;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.github.dabasan.joglf.gl.input.CountsAndFlags;

/**
 * Keyboard for Swing windows
 * @author Daba
 *
 */
public class SwingKeyboard implements KeyListener{
	private final int ALPHABET_KEY_NUM=26;
	private final int ARROW_KEY_NUM=4;
	private final int FUNCTION_KEY_NUM=12;
	private final int NUM_KEY_NUM=10;
	private final int NUMPAD_KEY_NUM=10;
	
	private CountsAndFlags alphabet_keys;
	private CountsAndFlags arrow_keys;
	private CountsAndFlags function_keys;
	private CountsAndFlags num_keys;
	private CountsAndFlags numpad_keys;
	
	private CountsAndFlags alt_key;
	private CountsAndFlags at_key;
	private CountsAndFlags back_slash_key;
	private CountsAndFlags back_space_key;
	private CountsAndFlags colon_key;
	private CountsAndFlags comma_key;
	private CountsAndFlags ctrl_key;
	private CountsAndFlags delete_key;
	private CountsAndFlags enter_key;
	private CountsAndFlags escape_key;
	private CountsAndFlags home_key;
	private CountsAndFlags minus_key;
	private CountsAndFlags num_lock_key;
	private CountsAndFlags period_key;
	private CountsAndFlags semicolon_key;
	private CountsAndFlags shift_key;
	private CountsAndFlags slash_key;
	private CountsAndFlags space_key;
	private CountsAndFlags tab_key;
	
	public SwingKeyboard() {
		alphabet_keys=new CountsAndFlags(ALPHABET_KEY_NUM);
		arrow_keys=new CountsAndFlags(ARROW_KEY_NUM);
		function_keys=new CountsAndFlags(FUNCTION_KEY_NUM);
		num_keys=new CountsAndFlags(NUM_KEY_NUM);
		numpad_keys=new CountsAndFlags(NUMPAD_KEY_NUM);
		
		alt_key=new CountsAndFlags(1);
		at_key=new CountsAndFlags(1);
		back_slash_key=new CountsAndFlags(1);
		back_space_key=new CountsAndFlags(1);
		colon_key=new CountsAndFlags(1);
		comma_key=new CountsAndFlags(1);
		ctrl_key=new CountsAndFlags(1);
		delete_key=new CountsAndFlags(1);
		enter_key=new CountsAndFlags(1);
		escape_key=new CountsAndFlags(1);
		home_key=new CountsAndFlags(1);
		minus_key=new CountsAndFlags(1);
		num_lock_key=new CountsAndFlags(1);
		period_key=new CountsAndFlags(1);
		semicolon_key=new CountsAndFlags(1);
		shift_key=new CountsAndFlags(1);
		slash_key=new CountsAndFlags(1);
		space_key=new CountsAndFlags(1);
		tab_key=new CountsAndFlags(1);
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		//Alphabet keys
		char key_char=e.getKeyChar();
		char ch_temp;
		
		ch_temp='A';
		for(int i=0;i<ALPHABET_KEY_NUM;i++) {
			if(key_char==ch_temp)alphabet_keys.SetPressingFlag(i, true);
			ch_temp++;
		}
		
		ch_temp='a';
		for(int i=0;i<ALPHABET_KEY_NUM;i++) {
			if(key_char==ch_temp)alphabet_keys.SetPressingFlag(i, true);
			ch_temp++;
		}
		
		//Other keys
		switch(e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			arrow_keys.SetPressingFlag(0, true);
			break;
		case KeyEvent.VK_UP:
			arrow_keys.SetPressingFlag(1, true);
			break;
		case KeyEvent.VK_RIGHT:
			arrow_keys.SetPressingFlag(2, true);
			break;
		case KeyEvent.VK_DOWN:
			arrow_keys.SetPressingFlag(3, true);
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
		case KeyEvent.VK_NUMPAD0:
			numpad_keys.SetPressingFlag(0, true);
			break;
		case KeyEvent.VK_NUMPAD1:
			numpad_keys.SetPressingFlag(1, true);
			break;
		case KeyEvent.VK_NUMPAD2:
			numpad_keys.SetPressingFlag(2, true);
			break;
		case KeyEvent.VK_NUMPAD3:
			numpad_keys.SetPressingFlag(3, true);
			break;
		case KeyEvent.VK_NUMPAD4:
			numpad_keys.SetPressingFlag(4, true);
			break;
		case KeyEvent.VK_NUMPAD5:
			numpad_keys.SetPressingFlag(5, true);
			break;
		case KeyEvent.VK_NUMPAD6:
			numpad_keys.SetPressingFlag(6, true);
			break;
		case KeyEvent.VK_NUMPAD7:
			numpad_keys.SetPressingFlag(7, true);
			break;
		case KeyEvent.VK_NUMPAD8:
			numpad_keys.SetPressingFlag(8, true);
			break;
		case KeyEvent.VK_NUMPAD9:
			numpad_keys.SetPressingFlag(9, true);
			break;
		case KeyEvent.VK_ALT:
			alt_key.SetPressingFlag(0, true);
			break;
		case KeyEvent.VK_AT:
			at_key.SetPressingFlag(0, true);
			break;
		case KeyEvent.VK_BACK_SLASH:
			back_slash_key.SetPressingFlag(0, true);
			break;
		case KeyEvent.VK_BACK_SPACE:
			back_space_key.SetPressingFlag(0, true);
			break;
		case KeyEvent.VK_COLON:
			colon_key.SetPressingFlag(0, true);
			break;
		case KeyEvent.VK_COMMA:
			comma_key.SetPressingFlag(0, true);
			break;
		case KeyEvent.VK_CONTROL:
			ctrl_key.SetPressingFlag(0, true);
			break;
		case KeyEvent.VK_DELETE:
			delete_key.SetPressingFlag(0, true);
			break;
		case KeyEvent.VK_ENTER:
			enter_key.SetPressingFlag(0, true);
			break;
		case KeyEvent.VK_ESCAPE:
			escape_key.SetPressingFlag(0, true);
			break;
		case KeyEvent.VK_HOME:
			home_key.SetPressingFlag(0, true);
			break;
		case KeyEvent.VK_MINUS:
			minus_key.SetPressingFlag(0, true);
			break;
		case KeyEvent.VK_NUM_LOCK:
			num_lock_key.SetPressingFlag(0, true);
			break;
		case KeyEvent.VK_PERIOD:
			period_key.SetPressingFlag(0, true);
			break;
		case KeyEvent.VK_SEMICOLON:
			semicolon_key.SetPressingFlag(0, true);
			break;
		case KeyEvent.VK_SHIFT:
			shift_key.SetPressingFlag(0, true);
			break;
		case KeyEvent.VK_SLASH:
			slash_key.SetPressingFlag(0, true);
			break;
		case KeyEvent.VK_SPACE:
			space_key.SetPressingFlag(0, true);
			break;
		case KeyEvent.VK_TAB:
			tab_key.SetPressingFlag(0, true);
			break;
		default:
			break;
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		char key_char=e.getKeyChar();
		
		//Alphabet keys
		char ch_temp;
		
		ch_temp='A';
		for(int i=0;i<ALPHABET_KEY_NUM;i++) {
			if(key_char==ch_temp)alphabet_keys.SetPressingFlag(i, false);
			ch_temp++;
		}
		
		ch_temp='a';
		for(int i=0;i<ALPHABET_KEY_NUM;i++) {
			if(key_char==ch_temp)alphabet_keys.SetPressingFlag(i, false);
			ch_temp++;
		}
		
		//Other keys
		switch(e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			arrow_keys.SetPressingFlag(0, false);
			break;
		case KeyEvent.VK_UP:
			arrow_keys.SetPressingFlag(1, false);
			break;
		case KeyEvent.VK_RIGHT:
			arrow_keys.SetPressingFlag(2, false);
			break;
		case KeyEvent.VK_DOWN:
			arrow_keys.SetPressingFlag(3, false);
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
		case KeyEvent.VK_NUMPAD0:
			numpad_keys.SetPressingFlag(0, false);
			break;
		case KeyEvent.VK_NUMPAD1:
			numpad_keys.SetPressingFlag(1, false);
			break;
		case KeyEvent.VK_NUMPAD2:
			numpad_keys.SetPressingFlag(2, false);
			break;
		case KeyEvent.VK_NUMPAD3:
			numpad_keys.SetPressingFlag(3, false);
			break;
		case KeyEvent.VK_NUMPAD4:
			numpad_keys.SetPressingFlag(4, false);
			break;
		case KeyEvent.VK_NUMPAD5:
			numpad_keys.SetPressingFlag(5, false);
			break;
		case KeyEvent.VK_NUMPAD6:
			numpad_keys.SetPressingFlag(6, false);
			break;
		case KeyEvent.VK_NUMPAD7:
			numpad_keys.SetPressingFlag(7, false);
			break;
		case KeyEvent.VK_NUMPAD8:
			numpad_keys.SetPressingFlag(8, false);
			break;
		case KeyEvent.VK_NUMPAD9:
			numpad_keys.SetPressingFlag(9, false);
			break;
		case KeyEvent.VK_ALT:
			alt_key.SetPressingFlag(0, false);
			break;
		case KeyEvent.VK_AT:
			at_key.SetPressingFlag(0, false);
			break;
		case KeyEvent.VK_BACK_SLASH:
			back_slash_key.SetPressingFlag(0, false);
			break;
		case KeyEvent.VK_BACK_SPACE:
			back_space_key.SetPressingFlag(0, false);
			break;
		case KeyEvent.VK_COLON:
			colon_key.SetPressingFlag(0, false);
			break;
		case KeyEvent.VK_COMMA:
			comma_key.SetPressingFlag(0, false);
			break;
		case KeyEvent.VK_CONTROL:
			ctrl_key.SetPressingFlag(0, false);
			break;
		case KeyEvent.VK_DELETE:
			delete_key.SetPressingFlag(0, false);
			break;
		case KeyEvent.VK_ENTER:
			enter_key.SetPressingFlag(0, false);
			break;
		case KeyEvent.VK_ESCAPE:
			escape_key.SetPressingFlag(0, false);
			break;
		case KeyEvent.VK_HOME:
			home_key.SetPressingFlag(0, false);
			break;
		case KeyEvent.VK_MINUS:
			minus_key.SetPressingFlag(0, false);
			break;
		case KeyEvent.VK_NUM_LOCK:
			num_lock_key.SetPressingFlag(0, false);
			break;
		case KeyEvent.VK_PERIOD:
			period_key.SetPressingFlag(0, false);
			break;
		case KeyEvent.VK_SEMICOLON:
			semicolon_key.SetPressingFlag(0, false);
			break;
		case KeyEvent.VK_SHIFT:
			shift_key.SetPressingFlag(0, false);
			break;
		case KeyEvent.VK_SLASH:
			slash_key.SetPressingFlag(0, false);
			break;
		case KeyEvent.VK_SPACE:
			space_key.SetPressingFlag(0, false);
			break;
		case KeyEvent.VK_TAB:
			tab_key.SetPressingFlag(0, false);
			break;
		default:
			break;
		}
	}
	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
	public void Update() {
		alphabet_keys.UpdateCounts();
		arrow_keys.UpdateCounts();
		function_keys.UpdateCounts();
		num_keys.UpdateCounts();
		
		alt_key.UpdateCounts();
		at_key.UpdateCounts();
		back_slash_key.UpdateCounts();
		colon_key.UpdateCounts();
		comma_key.UpdateCounts();
		ctrl_key.UpdateCounts();
		delete_key.UpdateCounts();
		enter_key.UpdateCounts();
		escape_key.UpdateCounts();
		home_key.UpdateCounts();
		minus_key.UpdateCounts();
		num_keys.UpdateCounts();
		period_key.UpdateCounts();
		semicolon_key.UpdateCounts();
		shift_key.UpdateCounts();
		slash_key.UpdateCounts();
		space_key.UpdateCounts();
		tab_key.UpdateCounts();
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
		case KEY_LEFT:
			count=arrow_keys.GetPressingCount(0);
			break;
		case KEY_UP:
			count=arrow_keys.GetPressingCount(1);
			break;
		case KEY_RIGHT:
			count=arrow_keys.GetPressingCount(2);
			break;
		case KEY_DOWN:
			count=arrow_keys.GetPressingCount(3);
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
		case KEY_NUMPAD_0:
			count=numpad_keys.GetPressingCount(0);
			break;
		case KEY_NUMPAD_1:
			count=numpad_keys.GetPressingCount(1);
			break;
		case KEY_NUMPAD_2:
			count=numpad_keys.GetPressingCount(2);
			break;
		case KEY_NUMPAD_3:
			count=numpad_keys.GetPressingCount(3);
			break;
		case KEY_NUMPAD_4:
			count=numpad_keys.GetPressingCount(4);
			break;
		case KEY_NUMPAD_5:
			count=numpad_keys.GetPressingCount(5);
			break;
		case KEY_NUMPAD_6:
			count=numpad_keys.GetPressingCount(6);
			break;
		case KEY_NUMPAD_7:
			count=numpad_keys.GetPressingCount(7);
			break;
		case KEY_NUMPAD_8:
			count=numpad_keys.GetPressingCount(8);
			break;
		case KEY_NUMPAD_9:
			count=numpad_keys.GetPressingCount(9);
			break;
		case KEY_ALT:
			count=alt_key.GetPressingCount(0);
			break;
		case KEY_AT:
			count=at_key.GetPressingCount(0);
			break;
		case KEY_BACK_SLASH:
			count=back_slash_key.GetPressingCount(0);
			break;
		case KEY_BACK_SPACE:
			count=back_space_key.GetPressingCount(0);
			break;
		case KEY_COLON:
			count=colon_key.GetPressingCount(0);
			break;
		case KEY_COMMA:
			count=comma_key.GetPressingCount(0);
			break;
		case KEY_CTRL:
			count=ctrl_key.GetPressingCount(0);
			break;
		case KEY_DELETE:
			count=delete_key.GetPressingCount(0);
			break;
		case KEY_ENTER:
			count=enter_key.GetPressingCount(0);
			break;
		case KEY_ESCAPE:
			count=escape_key.GetPressingCount(0);
			break;
		case KEY_HOME:
			count=home_key.GetPressingCount(0);
			break;
		case KEY_MINUS:
			count=minus_key.GetPressingCount(0);
			break;
		case KEY_NUM_LOCK:
			count=num_lock_key.GetPressingCount(0);
			break;
		case KEY_PERIOD:
			count=period_key.GetPressingCount(0);
			break;
		case KEY_SEMICOLON:
			count=semicolon_key.GetPressingCount(0);
			break;
		case KEY_SHIFT:
			count=shift_key.GetPressingCount(0);
			break;
		case KEY_SLASH:
			count=slash_key.GetPressingCount(0);
			break;
		case KEY_SPACE:
			count=space_key.GetPressingCount(0);
			break;
		case KEY_TAB:
			count=tab_key.GetPressingCount(0);
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
		case KEY_LEFT:
			count=arrow_keys.GetReleasingCount(0);
			break;
		case KEY_UP:
			count=arrow_keys.GetReleasingCount(1);
			break;
		case KEY_RIGHT:
			count=arrow_keys.GetReleasingCount(2);
			break;
		case KEY_DOWN:
			count=arrow_keys.GetReleasingCount(3);
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
		case KEY_NUMPAD_0:
			count=numpad_keys.GetReleasingCount(0);
			break;
		case KEY_NUMPAD_1:
			count=numpad_keys.GetReleasingCount(1);
			break;
		case KEY_NUMPAD_2:
			count=numpad_keys.GetReleasingCount(2);
			break;
		case KEY_NUMPAD_3:
			count=numpad_keys.GetReleasingCount(3);
			break;
		case KEY_NUMPAD_4:
			count=numpad_keys.GetReleasingCount(4);
			break;
		case KEY_NUMPAD_5:
			count=numpad_keys.GetReleasingCount(5);
			break;
		case KEY_NUMPAD_6:
			count=numpad_keys.GetReleasingCount(6);
			break;
		case KEY_NUMPAD_7:
			count=numpad_keys.GetReleasingCount(7);
			break;
		case KEY_NUMPAD_8:
			count=numpad_keys.GetReleasingCount(8);
			break;
		case KEY_NUMPAD_9:
			count=numpad_keys.GetReleasingCount(9);
			break;
		case KEY_ALT:
			count=alt_key.GetReleasingCount(0);
			break;
		case KEY_AT:
			count=at_key.GetReleasingCount(0);
			break;
		case KEY_BACK_SLASH:
			count=back_slash_key.GetReleasingCount(0);
			break;
		case KEY_BACK_SPACE:
			count=back_space_key.GetReleasingCount(0);
			break;
		case KEY_COLON:
			count=colon_key.GetReleasingCount(0);
			break;
		case KEY_COMMA:
			count=comma_key.GetReleasingCount(0);
			break;
		case KEY_CTRL:
			count=ctrl_key.GetReleasingCount(0);
			break;
		case KEY_DELETE:
			count=delete_key.GetReleasingCount(0);
			break;
		case KEY_ENTER:
			count=enter_key.GetReleasingCount(0);
			break;
		case KEY_ESCAPE:
			count=escape_key.GetReleasingCount(0);
			break;
		case KEY_HOME:
			count=home_key.GetReleasingCount(0);
			break;
		case KEY_MINUS:
			count=minus_key.GetReleasingCount(0);
			break;
		case KEY_NUM_LOCK:
			count=num_lock_key.GetReleasingCount(0);
			break;
		case KEY_PERIOD:
			count=period_key.GetReleasingCount(0);
			break;
		case KEY_SEMICOLON:
			count=semicolon_key.GetReleasingCount(0);
			break;
		case KEY_SHIFT:
			count=shift_key.GetReleasingCount(0);
			break;
		case KEY_SLASH:
			count=slash_key.GetReleasingCount(0);
			break;
		case KEY_SPACE:
			count=space_key.GetReleasingCount(0);
			break;
		case KEY_TAB:
			count=tab_key.GetReleasingCount(0);
			break;
		default:
			count=0;
			break;
		}
		
		return count;
	}
}
