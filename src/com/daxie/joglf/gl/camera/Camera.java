package com.daxie.joglf.gl.camera;

import java.nio.FloatBuffer;

import com.daxie.basis.matrix.Matrix;
import com.daxie.basis.vector.Vector;
import com.daxie.basis.vector.VectorFunctions;
import com.daxie.joglf.gl.GLFront;
import com.daxie.joglf.gl.tool.BufferFunctions;
import com.daxie.joglf.gl.wrapper.GLShaderFunctions;
import com.daxie.joglf.gl.wrapper.gl4.GL4Wrapper;
import com.daxie.tool.MathFunctions;

/**
 * Camera
 * @author Daba
 *
 */
public class Camera {
	private float near;
	private float far;
	
	private CameraMode camera_mode;
	private float fov;
	
	private Vector position;
	private Vector target;
	private Vector up;
	
	private Matrix projection_matrix;
	private Matrix view_transformation_matrix;
	
	public Camera() {
		near=1.0f;
		far=1000.0f;
		
		camera_mode=CameraMode.PERSPECTIVE;
		fov=MathFunctions.DegToRad(60.0f);
		
		position=VectorFunctions.VGet(0.0f, 0.0f, 0.0f);
		target=VectorFunctions.VGet(0.0f, 0.0f, 0.0f);
		up=VectorFunctions.VGet(0.0f, 1.0f, 0.0f);
	}
	
	public void UpdateAspect() {
		if(camera_mode==CameraMode.PERSPECTIVE) {
			float aspect=GLFront.GetWindowAspect();
			projection_matrix=ProjectionMatrix.GetPerspectiveMatrix(fov, aspect, near, far);
		}
	}
	
	public void SetCameraNearFar(float near,float far) {
		this.near=near;
		this.far=far;
	}
	public void SetCameraPosition(Vector position) {
		this.position=position;
	}
	public void SetCameraTarget(Vector target) {
		this.target=target;
	}
	public void SetCameraUpVector(Vector up) {
		this.up=up;
	}
	
	public void SetupCamera_Perspective(float fov) {
		float aspect=GLFront.GetWindowAspect();
		projection_matrix=ProjectionMatrix.GetPerspectiveMatrix(fov, aspect, near, far);
		
		camera_mode=CameraMode.PERSPECTIVE;
		this.fov=fov;
	}
	public void SetupCamera_Ortho(float size) {
		projection_matrix=ProjectionMatrix.GetOrthogonalMatrix(-size, size, -size, size, near, far);
		
		camera_mode=CameraMode.ORTHOGRAPHIC;
	}
	
	public void SetCameraViewMatrix(Matrix m) {
		view_transformation_matrix=m;
	}
	
	public void Update() {
		if(view_transformation_matrix==null) {
			view_transformation_matrix=ViewTransformation.GetViewTransformationMatrix(position, target, up);
		}
		
		FloatBuffer camera_position=BufferFunctions.MakeFloatBufferFromVector(position);
		FloatBuffer camera_target=BufferFunctions.MakeFloatBufferFromVector(target);
		FloatBuffer projection=BufferFunctions.MakeFloatBufferFromMatrix(projection_matrix);
		FloatBuffer view_transformation=BufferFunctions.MakeFloatBufferFromMatrix(view_transformation_matrix);
		
		int program_id;
		
		int camera_position_location;
		int camera_target_location;
		int projection_location;
		int view_transformation_location;
		int camera_near_location;
		int camera_far_location;
		
		//Texture program
		GLShaderFunctions.EnableProgram("texture");
		program_id=GLShaderFunctions.GetProgramID("texture");
		
		camera_position_location=GL4Wrapper.glGetUniformLocation(program_id, "camera_position");
		camera_target_location=GL4Wrapper.glGetUniformLocation(program_id, "camera_target");
		projection_location=GL4Wrapper.glGetUniformLocation(program_id, "projection");
		view_transformation_location=GL4Wrapper.glGetUniformLocation(program_id, "view_transformation");
		camera_near_location=GL4Wrapper.glGetUniformLocation(program_id, "camera_near");
		camera_far_location=GL4Wrapper.glGetUniformLocation(program_id, "camera_far");
		
		GL4Wrapper.glUniform3fv(camera_position_location, 1, camera_position);
		GL4Wrapper.glUniform3fv(camera_target_location, 1, camera_target);
		GL4Wrapper.glUniformMatrix4fv(projection_location, 1, true, projection);
		GL4Wrapper.glUniformMatrix4fv(view_transformation_location,1,true,view_transformation);
		GL4Wrapper.glUniform1f(camera_near_location, near);
		GL4Wrapper.glUniform1f(camera_far_location, far);
		
		//Color program
		GLShaderFunctions.EnableProgram("color");
		program_id=GLShaderFunctions.GetProgramID("color");
		
		projection_location=GL4Wrapper.glGetUniformLocation(program_id, "projection");
		view_transformation_location=GL4Wrapper.glGetUniformLocation(program_id, "view_transformation");
		
		GL4Wrapper.glUniformMatrix4fv(projection_location, 1, true, projection);
		GL4Wrapper.glUniformMatrix4fv(view_transformation_location,1,true,view_transformation);
		
		view_transformation_matrix=null;
	}
}
