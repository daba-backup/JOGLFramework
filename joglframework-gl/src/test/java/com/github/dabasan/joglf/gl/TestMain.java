package com.github.dabasan.joglf.gl;

public class TestMain {
	public static void main(String[] args) {
		new TestMain();
	}
	public TestMain() {
		for (int i = 0; i < 5; i++) {
			var window = new Viewer();
			window.SetTitle("Window " + i);
			if (i == 0) {
				window.SetExitProcessWhenDestroyed();
			}
		}
	}
}
