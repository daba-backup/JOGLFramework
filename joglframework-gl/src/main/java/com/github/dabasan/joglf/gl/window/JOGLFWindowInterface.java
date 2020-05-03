package com.github.dabasan.joglf.gl.window;

import com.github.dabasan.basis.coloru8.ColorU8;
import com.github.dabasan.joglf.gl.input.keyboard.KeyboardEnum;
import com.github.dabasan.joglf.gl.input.mouse.MouseEnum;

/**
 * Interface for JOGLFWindow and JOGLSwingWindow
 * 
 * @author Daba
 *
 */
public interface JOGLFWindowInterface {
	public void CloseWindow();
	public void SetExitProcessWhenDestroyed();

	public String GetTitle();
	public int GetX();
	public int GetY();
	public int GetWidth();
	public int GetHeight();
	public ColorU8 GetBackgroundColor();
	public void SetTitle(String title);
	public void SetPosition(int x, int y);
	public void SetSize(int width, int height);
	public void SetBackgroundColor(ColorU8 color);

	public boolean HasFocus();
	public boolean IsDestroyed();

	public void ShowWindow();
	public void HideWindow();

	public void SetWindowMode(WindowMode mode);
	public void SetAlwaysOnTop(boolean flag);

	public int GetKeyboardPressingCount(KeyboardEnum key);
	public int GetKeyboardReleasingCount(KeyboardEnum key);
	public int GetMousePressingCount(MouseEnum key);
	public int GetMouseReleasingCount(MouseEnum key);
	public int GetCursorX();
	public int GetCursorY();
	public int GetCursorDiffX();
	public int GetCursorDiffY();
	public void SetCursorPos(int x, int y);
	public float GetMouseWheelHorizontalRotation();

	public void Fit();

	public void Init();
	public void Reshape(int x, int y, int width, int height);
	public void Update();
	public void Draw();
	public void Dispose();
	public void OnWindowClosing();
}
