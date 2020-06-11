package com.github.dabasan.joglf.gl.camera;

import java.util.ArrayList;
import java.util.List;

import com.github.dabasan.basis.matrix.Matrix;
import com.github.dabasan.basis.vector.Vector;
import com.github.dabasan.basis.vector.VectorFunctions;
import com.github.dabasan.joglf.gl.shader.ShaderProgram;
import com.github.dabasan.joglf.gl.tool.matrix.ProjectionMatrixFunctions;
import com.github.dabasan.joglf.gl.tool.matrix.TransformationMatrixFunctions;
import com.github.dabasan.joglf.gl.window.WindowCommonInfo;
import com.github.dabasan.tool.MathFunctions;

/**
 * Camera
 * 
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

	private Matrix projection;
	private Matrix view_transformation;

	private final List<ShaderProgram> programs;

	public Camera() {
		near = 1.0f;
		far = 1000.0f;

		camera_mode = CameraMode.PERSPECTIVE;
		fov = MathFunctions.DegToRad(60.0f);
		size = 10.0f;
		aspect = WindowCommonInfo.DEFAULT_WIDTH / WindowCommonInfo.DEFAULT_HEIGHT;

		position = VectorFunctions.VGet(50.0f, 50.0f, 50.0f);
		target = VectorFunctions.VGet(0.0f, 0.0f, 0.0f);
		up = VectorFunctions.VGet(0.0f, 1.0f, 0.0f);

		programs = new ArrayList<>();
	}

	public void AddProgram(ShaderProgram program) {
		programs.add(program);
	}
	public void RemoveProgram(ShaderProgram program) {
		programs.remove(program);
	}
	public void RemoveAllPrograms() {
		programs.clear();
	}

	public void SetCameraNearFar(float near, float far) {
		this.near = near;
		this.far = far;
	}
	public void SetCameraPositionAndTarget(Vector position, Vector target) {
		this.position = position;
		this.target = target;
	}
	public void SetCameraUpVector(Vector up) {
		this.up = up;
	}
	public float GetCameraNear() {
		return near;
	}
	public float GetCameraFar() {
		return far;
	}
	public Vector GetCameraPosition() {
		return new Vector(position);
	}
	public Vector GetCameraTarget() {
		return new Vector(target);
	}
	public Vector GetCameraFrontVector() {
		Vector front = VectorFunctions.VSub(target, position);
		front = VectorFunctions.VNorm(front);

		return front;
	}
	public Vector GetCameraUpVector() {
		return new Vector(up);
	}

	/**
	 * Sets up a perspective camera.
	 * 
	 * @param fov
	 *            Field of view (rad)
	 */
	public void SetupCamera_Perspective(float fov) {
		projection = ProjectionMatrixFunctions.GetPerspectiveMatrix(fov, aspect, near, far);

		camera_mode = CameraMode.PERSPECTIVE;
		this.fov = fov;
	}
	/**
	 * Sets up a orthographic camera.
	 * 
	 * @param size
	 *            Size
	 */
	public void SetupCamera_Ortho(float size) {
		projection = ProjectionMatrixFunctions.GetOrthogonalMatrix(-size, size, -size, size, near,
				far);

		camera_mode = CameraMode.ORTHOGRAPHIC;
		this.size = size;
	}

	/**
	 * Sets a view transformation matrix for this camera.
	 * 
	 * @param m
	 *            View transformation matrix
	 */
	@Deprecated
	public void SetCameraViewMatrix(Matrix m) {
		view_transformation = m;
	}

	public Matrix GetProjectionMatrix() {
		Matrix ret;

		if (camera_mode == CameraMode.PERSPECTIVE) {
			ret = ProjectionMatrixFunctions.GetPerspectiveMatrix(fov, aspect, near, far);
		} else {
			ret = ProjectionMatrixFunctions.GetOrthogonalMatrix(-size, size, -size, size, near,
					far);
		}

		return ret;
	}
	public Matrix GetViewTransformationMatrix() {
		final Matrix ret = TransformationMatrixFunctions.GetViewTransformationMatrix(position,
				target, up);
		return ret;
	}

	/**
	 * Updates the aspect of this camera.
	 * 
	 * @param width
	 *            Width
	 * @param height
	 *            Height
	 */
	public void UpdateAspect(int width, int height) {
		aspect = (float) width / height;
	}
	/**
	 * Transfers data to the programs.
	 */
	public void Update() {
		this.MakeMatrices();
		for (final ShaderProgram program : programs) {
			innerUpdate(program);
		}
		view_transformation = null;
	}
	/**
	 * Transfers data to a program.
	 * 
	 * @param program
	 *            Program
	 */
	public void Update(ShaderProgram program) {
		this.MakeMatrices();
		innerUpdate(program);
		view_transformation = null;
	}
	private void MakeMatrices() {
		if (camera_mode == CameraMode.PERSPECTIVE) {
			projection = ProjectionMatrixFunctions.GetPerspectiveMatrix(fov, aspect, near, far);
		}
		if (view_transformation == null) {
			view_transformation = TransformationMatrixFunctions
					.GetViewTransformationMatrix(position, target, up);
		}
	}
	private void innerUpdate(ShaderProgram program) {
		program.Enable();
		program.SetUniform("camera.position", position);
		program.SetUniform("camera.target", target);
		program.SetUniform("camera.projection", true, projection);
		program.SetUniform("camera.view_transformation", true, view_transformation);
		program.SetUniform("camera.near", near);
		program.SetUniform("camera.far", far);
		program.Disable();
	}
}
