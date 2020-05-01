package com.github.dabasan.joglf.gl;

import com.github.dabasan.basis.coloru8.ColorU8Functions;
import com.github.dabasan.joglf.gl.draw.DrawFunctions2D;
import com.github.dabasan.joglf.gl.texture.TextureMgr;
import com.github.dabasan.joglf.gl.window.JOGLFWindow;

class Draw2DTestWindow extends JOGLFWindow{
	private int texture_handle;
	
	@Override
	public void Init() {
		texture_handle=TextureMgr.LoadTexture("./Data/Texture/test.jpg");
	}
	
	@Override
	public void Draw() {
		DrawFunctions2D.DrawLine2D(0, 0, 512, 512, ColorU8Functions.GetColorU8(1.0f, 1.0f, 1.0f, 1.0f));
		DrawFunctions2D.DrawTexture(texture_handle, 0, 0, 256, 256);
	}
}
