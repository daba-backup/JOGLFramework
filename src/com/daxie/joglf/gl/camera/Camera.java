package com.daxie.joglf.gl.camera;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import com.daxie.basis.matrix.Matrix;
import com.daxie.basis.vector.Vector;
import com.daxie.basis.vector.VectorFunctions;
import com.daxie.joglf.gl.tool.BufferFunctions;
import com.daxie.joglf.gl.window.WindowCommonInfoStock;
import com.daxie.joglf.gl.wrapper.GLShaderFunctions;
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
	
	private List<String> user_shader_names;
	
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
		
		user_shader_names=new ArrayList<>();
	}
	
	public void AddUserShader(String user_shader_name) {
		user_shader_names.add(user_shader_name);
	}
	public void RemoveUserShader(String user_shader_name) {
		user_shader_names.remove(user_shader_name);
	}
	public void RemoveAllUserShaders() {
		user_shader_names.clear();
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
		projection_matrix=ProjectionMatrix.GetPerspectiveMatrix(fov, aspect, near, far);
		
		camera_mode=CameraMode.PERSPECTIVE;
		this.fov=fov;
	}
	public void SetupCamera_Ortho(float size) {
		projection_matrix=ProjectionMatrix.GetOrthogonalMatrix(-size, size, -size, size, near, far);
		
		camera_mode=CameraMode.ORTHOGRAPHIC;
		this.size=size;
	}
	
	public void SetCameraViewMatrix(Matrix m) {
		view_transformation_matrix=m;
	}
	
	public Matrix GetProjectionMatrix() {
		Matrix ret;
		
		if(camera_mode==CameraMode.PERSPECTIVE) {
			ret=ProjectionMatrix.GetPerspectiveMatrix(fov, aspect, near, far);
		}
		else {
			ret=ProjectionMatrix.GetOrthogonalMatrix(-size, size, -size, size, near, far);
		}
		
		return ret;
	}
	public Matrix GetViewTransformationMatrix() {
		Matrix ret=ViewTransformationMatrix.GetViewTransformationMatrix(position, target, up);
		return ret;
	}
	
	public void UpdateAspect(int width,int height) {
		aspect=(float)width/height;
	}
	public void Update() {
		if(camera_mode==CameraMode.PERSPECTIVE) {
			projection_matrix=ProjectionMatrix.GetPerspectiveMatrix(fov, aspect, near, far);
		}
		
		if(view_transformation_matrix==null) {
			view_transformation_matrix=ViewTransformationMatrix.GetViewTransformationMatrix(position, target, up);
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
		
		camera_position_location=GLWrapper.glGetUniformLocation(program_id, "camera_position");
		camera_target_location=GLWrapper.glGetUniformLocation(program_id, "camera_target");
		projection_location=GLWrapper.glGetUniformLocation(program_id, "projection");
		view_transformation_location=GLWrapper.glGetUniformLocation(program_id, "view_transformation");
		camera_near_location=GLWrapper.glGetUniformLocation(program_id, "camera_near");
		camera_far_location=GLWrapper.glGetUniformLocation(program_id, "camera_far");
		
		GLWrapper.glUniform3fv(camera_position_location, 1, camera_position);
		GLWrapper.glUniform3fv(camera_target_location, 1, camera_target);
		GLWrapper.glUniformMatrix4fv(projection_location, 1, true, projection);
		GLWrapper.glUniformMatrix4fv(view_transformation_location,1,true,view_transformation);
		GLWrapper.glUniform1f(camera_near_location, near);
		GLWrapper.glUniform1f(camera_far_location, far);
		
		//Color program
		GLShaderFunctions.EnableProgram("color");
		program_id=GLShaderFunctions.GetProgramID("color");
		
		projection_location=GLWrapper.glGetUniformLocation(program_id, "projection");
		view_transformation_location=GLWrapper.glGetUniformLocation(program_id, "view_transformation");
		
		GLWrapper.glUniformMatrix4fv(projection_location, 1, true, projection);
		GLWrapper.glUniformMatrix4fv(view_transformation_location,1,true,view_transformation);
		
		//User shaders
		for(String user_shader_name:user_shader_names) {
			GLShaderFunctions.EnableProgram(user_shader_name);
			program_id=GLShaderFunctions.GetProgramID(user_shader_name);
			
			camera_position_location=GLWrapper.glGetUniformLocation(program_id, "camera_position");
			camera_target_location=GLWrapper.glGetUniformLocation(program_id, "camera_target");
			projection_location=GLWrapper.glGetUniformLocation(program_id, "projection");
			view_transformation_location=GLWrapper.glGetUniformLocation(program_id, "view_transformation");
			camera_near_location=GLWrapper.glGetUniformLocation(program_id, "camera_near");
			camera_far_location=GLWrapper.glGetUniformLocation(program_id, "camera_far");
			
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
