package com.github.dabasan.joglf.gl;

import com.github.dabasan.joglf.gl.window.JOGLFWindowInterface;

public class TestMain {
	public static void main(String[] args) {
		new TestMain();
	}
	public TestMain() {
		final JOGLFWindowInterface window = new GetFacesTestWindow();
		window.SetExitProcessWhenDestroyed();
	}
}
