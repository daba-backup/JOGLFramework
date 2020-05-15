package com.github.dabasan.joglf.gl;

import java.util.List;

import com.github.dabasan.basis.vector.VectorFunctions;
import com.github.dabasan.joglf.gl.drawer.DynamicSegmentsDrawer;
import com.github.dabasan.joglf.gl.model.Model3DFunctions;
import com.github.dabasan.joglf.gl.shape.Triangle;
import com.github.dabasan.joglf.gl.window.JOGLFWindow;

class GetFacesTestWindow extends JOGLFWindow {
	private int model_handle;
	private DynamicSegmentsDrawer pos_drawer;

	@Override
	public void Init() {
		model_handle = Model3DFunctions.LoadModel("./Data/Model/BD1/map0/temp.bd1");
		Model3DFunctions.RescaleModel(model_handle, VectorFunctions.VGet(0.1f, 0.1f, 0.1f));

		pos_drawer = new DynamicSegmentsDrawer();

		final List<Triangle> faces = Model3DFunctions.GetModelFaces(model_handle);
		int count = 0;
		for (final Triangle face : faces) {
			pos_drawer.AddSegment(count, face.GetVertex(0), face.GetVertex(1));
			pos_drawer.AddSegment(count + 1, face.GetVertex(1), face.GetVertex(2));
			pos_drawer.AddSegment(count + 2, face.GetVertex(2), face.GetVertex(0));
			count += 3;
		}
		pos_drawer.UpdateBuffers();
	}

	@Override
	public void Draw() {
		pos_drawer.Draw();
	}
}
