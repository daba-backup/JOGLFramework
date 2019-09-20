package com.daxie.joglf.gl.front;

import com.daxie.basis.matrix.Matrix;
import com.daxie.basis.matrix.MatrixFunctions;
import com.daxie.basis.vector.Vector;
import com.daxie.basis.vector.VectorFunctions;
import com.daxie.joglf.gl.camera.Camera;

/**
 * Offers methods for camera operations.
 * @author Daba
 *
 */
public class CameraFront {
	private static Camera camera=new Camera();
	
	public static void Reshape() {
		camera.UpdateAspect();
	}
	
	public static void SetCameraNearFar(float near,float far) {
		camera.SetCameraNearFar(near, far);
	}
	public static void SetCameraPositionAndTarget_UpVecY(Vector position,Vector target) {
		camera.SetCameraPosition(position);
		camera.SetCameraTarget(target);
		camera.SetCameraUpVector(VectorFunctions.VGet(0.0f, 1.0f, 0.0f));
	}
	public static void SetCameraPositionAndTargetAndUpVec(Vector position,Vector target,Vector up) {
		camera.SetCameraPosition(position);
		camera.SetCameraTarget(target);
		camera.SetCameraUpVector(up);
	}
	public static void SetCameraViewMatrix(Matrix m) {
		camera.SetCameraViewMatrix(m);
	}
	public static void SetCameraPositionAndAngle(Vector position,float v_rotate,float h_rotate,float t_rotate) {
		Vector direction=new Vector();
		
		direction.SetX((float)Math.cos(h_rotate));
		direction.SetY((float)Math.sin(v_rotate));
		direction.SetZ(-(float)Math.sin(h_rotate));
		
		direction=VectorFunctions.VNorm(direction);
		
		Vector target=VectorFunctions.VAdd(position, direction);
		
		Matrix rot_direction=MatrixFunctions.MGetRotAxis(direction, t_rotate);
		Vector up=VectorFunctions.VTransform(VectorFunctions.VGet(0.0f, 1.0f, 0.0f), rot_direction);
		
		camera.SetCameraPosition(position);
		camera.SetCameraTarget(target);
		camera.SetCameraUpVector(up);
	}
	
	public static Vector GetCameraPosition() {
		return camera.GetCameraPosition();
	}
	public static Vector GetCameraTarget() {
		return camera.GetCameraTarget();
	}
	public static Vector GetCameraUpVector() {
		return camera.GetCameraUpVector();
	}
	
	public static void SetupCamera_Perspective(float fov) {
		camera.SetupCamera_Perspective(fov);
	}
	public static void SetupCamera_Ortho(float size) {
		camera.SetupCamera_Ortho(size);
	}
	
	public static void Update() {
		camera.Update();
	}
}