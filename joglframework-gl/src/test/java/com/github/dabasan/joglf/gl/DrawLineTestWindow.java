package com.github.dabasan.joglf.gl;

import com.daxie.basis.vector.VectorFunctions;
import com.github.dabasan.joglf.gl.drawer.DynamicSegmentsDrawer;
import com.github.dabasan.joglf.gl.front.CameraFront;
import com.github.dabasan.joglf.gl.front.FogFront;
import com.github.dabasan.joglf.gl.shape.Vertex3D;
import com.github.dabasan.joglf.gl.window.JOGLFWindow;

class DrawLineTestWindow extends JOGLFWindow{
	private DynamicSegmentsDrawer drawer;
	
	@Override
	public void Init() {
		drawer=new DynamicSegmentsDrawer();
		
		int count=0;
		for(int x=-100;x<=100;x++) {
			Vertex3D v1=new Vertex3D();
			Vertex3D v2=new Vertex3D();
			v1.SetPos(VectorFunctions.VGet(x, 0.0f, -100.0f));
			v2.SetPos(VectorFunctions.VGet(x, 0.0f, 100.0f));
			drawer.AddSegment(count, v1, v2);
			count++;
		}
		for(int z=-100;z<=100;z++) {
			Vertex3D v1=new Vertex3D();
			Vertex3D v2=new Vertex3D();
			v1.SetPos(VectorFunctions.VGet(-100.0f, 0.0f, z));
			v2.SetPos(VectorFunctions.VGet(100.0f, 0.0f, z));
			drawer.AddSegment(count, v1, v2);
			count++;
		}
		drawer.UpdateBuffers();
		
		FogFront.SetFogStartEnd(50.0f, 100.0f);
	}
	
	@Override
	public void Update() {
		CameraFront.SetCameraPositionAndTarget_UpVecY(
				VectorFunctions.VGet(-50.0f, 20.0f, -50.0f), 
				VectorFunctions.VGet(50.0f, 0.0f, 50.0f));
	}
	
	@Override
	public void Draw() {
		drawer.Draw();
	}
}
