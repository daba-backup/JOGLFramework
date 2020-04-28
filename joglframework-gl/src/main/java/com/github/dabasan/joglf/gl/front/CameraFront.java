package com.github.dabasan.joglf.gl.front;

import com.daxie.basis.matrix.Matrix;
import com.daxie.basis.matrix.MatrixFunctions;
import com.daxie.basis.vector.Vector;
import com.daxie.basis.vector.VectorFunctions;
import com.github.dabasan.joglf.gl.camera.Camera;
import com.github.dabasan.joglf.gl.tool.CoordinateFunctions;
import com.github.dabasan.joglf.gl.window.WindowCommonInfoStock;
import com.daxie.tool.MathFunctions;

/**
 * Provides methods for camera operations.
 * @author Daba
 *
 */
public class CameraFront {
	private static Camera camera=new Camera();
	
	private static int window_width=WindowCommonInfoStock.DEFAULT_WIDTH;
	private static int window_height=WindowCommonInfoStock.DEFAULT_HEIGHT;
	
	public static void AddProgram(String program_name) {
		camera.AddProgram(program_name);
	}
	public static void RemoveProgram(String program_name) {
		camera.RemoveProgram(program_name);
	}
	public static void RemoveAllPrograms() {
		camera.RemoveAllPrograms();
	}
	
	public static void SetCameraNearFar(float near,float far) {
		camera.SetCameraNearFar(near, far);
	}
	public static void SetCameraPositionAndTarget_UpVecY(Vector position,Vector target) {
		camera.SetCameraPositionAndTarget(position,target);
		camera.SetCameraUpVector(VectorFunctions.VGet(0.0f, 1.0f, 0.0f));
	}
	public static void SetCameraPositionAndTargetAndUpVec(Vector position,Vector target,Vector up) {
		camera.SetCameraPositionAndTarget(position,target);
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
		
		camera.SetCameraPositionAndTarget(position,target);
		camera.SetCameraUpVector(up);
	}
	
	public static void SetupCamera_Perspective(float fov) {
		camera.SetupCamera_Perspective(fov);
	}
	public static void SetupCamera_Ortho(float size) {
		camera.SetupCamera_Ortho(size);
	}
	
	/**
	 * Converts a world position to a screen position.<br>
	 * This method returns a screen position with an origin located at the bottom left of the screen.
	 * @param world_pos World position
	 * @return Screen position
	 */
	public static Vector ConvertWorldPosToScreenPos(Vector world_pos) {
		Matrix projection=camera.GetProjectionMatrix();
		Matrix view_transformation=camera.GetViewTransformationMatrix();
		
		Matrix camera_matrix=MatrixFunctions.MMult(projection, view_transformation);
		
		Matrix world_pos_matrix=new Matrix();
		world_pos_matrix.SetValue(0, 0, world_pos.GetX());
		world_pos_matrix.SetValue(1, 0, world_pos.GetY());
		world_pos_matrix.SetValue(2, 0, world_pos.GetZ());
		world_pos_matrix.SetValue(3, 0, 1.0f);
		Matrix clip_space_matrix=MatrixFunctions.MMult(camera_matrix, world_pos_matrix);
		float w=clip_space_matrix.GetValue(3, 0);
		
		Vector ret=VectorFunctions.VGet(
				clip_space_matrix.GetValue(0, 0), 
				clip_space_matrix.GetValue(1, 0),
				clip_space_matrix.GetValue(2, 0));
		ret=VectorFunctions.VScale(ret, 1.0f/w);
		
		float x=ret.GetX();
		float y=ret.GetY();
		
		x=CoordinateFunctions.ExpandNormalizedCoordinate(x, window_width);
		y=CoordinateFunctions.ExpandNormalizedCoordinate(y, window_height);
		ret.SetX(x);
		ret.SetY(y);
		
		return ret;
	}
	/**
	 * Converts a screen position to a world position.<br>
	 * This method takes the OpenGL coordinate system which has an origin at the bottom left of the screen.<br>
	 * <br>
	 * Z-coordinate of the screen position denotes the depth of the returned value.<br>
	 * If z==-1.0 then the distance between the camera and the point is equal to the near value of the camera.<br>
	 * If z==1.0 then the distance is equal to the far value.<br>
	 * The distance shows non-linear increase when a perspective matrix is used for projection.
	 * @param screen_pos Screen position
	 * @return World position
	 */
	public static Vector ConvertScreenPosToWorldPos(Vector screen_pos) {
		Vector normalized_screen_pos=new Vector();
		float x=CoordinateFunctions.NormalizeCoordinate(screen_pos.GetX(), window_width);
		float y=CoordinateFunctions.NormalizeCoordinate(screen_pos.GetY(), window_height);
		float z=MathFunctions.Clamp(screen_pos.GetZ(), -1.0f, 1.0f);
		normalized_screen_pos.SetVector(x, y, z);
		
		Matrix projection=camera.GetProjectionMatrix();
		Matrix view_transformation=camera.GetViewTransformationMatrix();
		
		Matrix camera_matrix=MatrixFunctions.MMult(projection, view_transformation);
		Matrix inv_camera_matrix=MatrixFunctions.MInverse(camera_matrix);
		
		Matrix clip_space_matrix=new Matrix();
		clip_space_matrix.SetValue(0, 0, normalized_screen_pos.GetX());
		clip_space_matrix.SetValue(1, 0, normalized_screen_pos.GetY());
		clip_space_matrix.SetValue(2, 0, normalized_screen_pos.GetZ());
		clip_space_matrix.SetValue(3, 0, 1.0f);
		Matrix world_pos_matrix=MatrixFunctions.MMult(inv_camera_matrix, clip_space_matrix);
		float w=world_pos_matrix.GetValue(3, 0);
		
		Vector ret=VectorFunctions.VGet(
				world_pos_matrix.GetValue(0, 0),
				world_pos_matrix.GetValue(1, 0),
				world_pos_matrix.GetValue(2, 0));
		ret=VectorFunctions.VScale(ret, 1.0f/w);
		
		return ret;
	}
	
	public static void UpdateAspect(int width,int height) {
		camera.UpdateAspect(width, height);
		window_width=width;
		window_height=height;
	}
	public static void Update() {
		camera.Update();
	}
}
