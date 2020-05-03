package com.github.dabasan.joglf.gl;

import com.github.dabasan.basis.vector.VectorFunctions;
import com.github.dabasan.joglf.gl.draw.DrawFunctions3D;
import com.github.dabasan.joglf.gl.shape.Quadrangle;
import com.github.dabasan.joglf.gl.texture.TextureMgr;
import com.github.dabasan.joglf.gl.window.JOGLFWindow;

class Draw3DTestWindow extends JOGLFWindow {
	private int texture_handle;
	private Quadrangle quadrangle;

	@Override
	public void Init() {
		texture_handle = TextureMgr.LoadTexture("./Data/Texture/test.jpg");

		quadrangle = new Quadrangle();
		quadrangle.GetVertex(0)
				.SetPos(VectorFunctions.VGet(-30.0f, 0.0f, -30.0f));
		quadrangle.GetVertex(1)
				.SetPos(VectorFunctions.VGet(-30.0f, 0.0f, 30.0f));
		quadrangle.GetVertex(2)
				.SetPos(VectorFunctions.VGet(30.0f, 0.0f, 30.0f));
		quadrangle.GetVertex(3)
				.SetPos(VectorFunctions.VGet(30.0f, 0.0f, -30.0f));
		quadrangle.GetVertex(0).SetU(0.0f);
		quadrangle.GetVertex(0).SetV(0.0f);
		quadrangle.GetVertex(1).SetU(1.0f);
		quadrangle.GetVertex(1).SetV(0.0f);
		quadrangle.GetVertex(2).SetU(1.0f);
		quadrangle.GetVertex(2).SetV(1.0f);
		quadrangle.GetVertex(3).SetU(0.0f);
		quadrangle.GetVertex(3).SetV(1.0f);
	}

	@Override
	public void Draw() {
		DrawFunctions3D.DrawQuadrangle3D(quadrangle);
		DrawFunctions3D.DrawTexturedQuadrangle(quadrangle, texture_handle,
				true);
	}
}
