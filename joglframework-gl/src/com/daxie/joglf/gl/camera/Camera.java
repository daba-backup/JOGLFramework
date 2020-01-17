package com.daxie.joglf.gl.camera;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import com.daxie.basis.matrix.Matrix;
import com.daxie.basis.vector.Vector;
import com.daxie.basis.vector.VectorFunctions;
import com.daxie.joglf.gl.shader.GLShaderFunctions;
import com.daxie.joglf.gl.tool.BufferFunctions;
import com.daxie.joglf.gl.tool.matrix.ProjectionMatrixFunctions;
import com.daxie.joglf.gl.tool.matrix.TransformationMatrixFunctions;
import com.daxie.joglf.gl.window.WindowCommonInfoStock;
import com.daxie.joglf.gl.wrapper.GLWrapper;
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
	private float size;
	
	private float aspect;
	
	private Vector position;
	private Vector target;
	private Vector up;
	
	private Matrix projection_matrix;
	private Matrix view_transformation_matrix;
	
	private List<String> program_names;
	
	public Camera() {
		near=1.0f;
		far=1000.0f;
		
		camera_mode=CameraMode.PERSPECTIVE;
		fov=MathFunctions.DegToRad(60.0f);
		size=10.0f;
		
		aspect=WindowCommonInfoStock.DEFAULT_WIDTH/WindowCommonInfoStock.DEFAULT_HEIGHT;
		
		position=VectorFunctions.VGet(-50.0f, 50.0f, -50.0f);
		target=VectorFunctions.VGet(0.0f, 0.0f, 0.0f);
		up=VectorFunctions.VGet(0.0f, 1.0f, 0.0f);
		
		program_names=new ArrayList<>();
	}
	
	public void AddProgram(String program_name) {
		program_names.add(program_name);
	}
	public void RemoveProgram(String program_name) {
		program_names.remove(program_name);
	}
	public void RemoveAllPrograms() {
		program_names.clear();
	}
	
	public void SetCameraNearFar(float near,float far) {
		this.near=near;
		this.far=far;
	}
	public void SetCameraPositionAndTarget(Vector position,Vector target) {
		this.position=position;
		this.target=target;
	}
	public void SetCameraUpVector(Vector up) {
		this.up=up;
	}
	public Vector GetCameraPosition() {
		return new Vector(position);
	}
	public Vector GetCameraTarget() {
		return new Vector(target);
	}
	public Vector GetCameraFrontVector() {
		Vector front=VectorFunctions.VSub(target,position);
		front=VectorFunctions.VNorm(front);
		
		return front;
	}
	public Vector GetCameraUpVector() {
		return new Vector(up);
	}
	
	public void SetupCamera_Perspective(float fov) {
		projection_matrix=ProjectionMatrixFunctions.GetPerspectiveMatrix(fov, aspect, near, far);
		
		camera_mode=CameraMode.PERSPECTIVE;
		this.fov=fov;
	}
	public void SetupCamera_Ortho(float size) {
		projection_matrix=ProjectionMatrixFunctions.GetOrthogonalMatrix(-size, size, -size, size, near, far);
		
		camera_mode=CameraMode.ORTHOGRAPHIC;
		this.size=size;
	}
	
	public void SetCameraViewMatrix(Matrix m) {
		view_transformation_matrix=m;
	}
	
	public Matrix GetProjectionMatrix() {
		Matrix ret;
		
		if(camera_mode==CameraMode.PERSPECTIVE) {
			ret=ProjectionMatrixFunctions.GetPerspectiveMatrix(fov, aspect, near, far);
		}
		else {
			ret=ProjectionMatrixFunctions.GetOrthogonalMatrix(-size, size, -size, size, near, far);
		}
		
		return ret;
	}
	public Matrix GetViewTransformationMatrix() {
		Matrix ret=TransformationMatrixFunctions.GetViewTransformationMatrix(position, target, up);
		return ret;
	}
	
	public void UpdateAspect(int width,int height) {
		aspect=(float)width/height;
	}
	public void Update() {
		if(camera_mode==CameraMode.PERSPECTIVE) {
			projection_matrix=ProjectionMatrixFunctions.GetPerspectiveMatrix(fov, aspect, near, far);
		}
		
		if(view_transformation_matrix==null) {
			view_transformation_matrix=TransformationMatrixFunctions.GetViewTransformationMatrix(position, target, up);
		}
		
		FloatBuffer camera_position=BufferFunctions.MakeFloatBufferFromVector(position);
		FloatBuffer camera_target=BufferFunctions.MakeFloatBufferFromVector(target);
		FloatBuffer projection=BufferFunctions.MakeFloatBufferFromMatrix(projection_matrix);
		FloatBuffer view_transformation=BufferFunctions.MakeFloatBufferFromMatrix(view_transformation_matrix);
		
		for(String program_name:program_names) {
			GLShaderFunctions.UseProgram(program_name);
			int program_id=GLShaderFunctions.GetProgramID(program_name);
			
			int camera_position_location=GLWrapper.glGetUniformLocation(program_id, "camera_position");
			int camera_target_location=GLWrapper.glGetUniformLocation(program_id, "camera_target");
			int projection_location=GLWrapper.glGetUniformLocation(program_id, "projection");
			int view_transformation_location=GLWrapper.glGetUniformLocation(program_id, "view_transformation");
			int camera_near_location=GLWrapper.glGetUniformLocation(program_id, "camera_near");
			int camera_far_location=GLWrapper.glGetUniformLocation(program_id, "camera_far");
			
			GLWrapper.glUniform3fv(camera_position_location, 1, camera_position);
			GLWrapper.glUniform3fv(camera_target_location, 1, camera_target);
			GLWrapper.glUniformMatrix4fv(projection_location, 1, true, projection);
			GLWrapper.glUniformMatrix4fv(view_transformation_location,1,true,view_transformation);
			GLWrapper.glUniform1f(camera_near_location, near);
			GLWrapper.glUniform1f(camera_far_location, far);
		}
		
		view_transformation_matrix=null;
	}
}
