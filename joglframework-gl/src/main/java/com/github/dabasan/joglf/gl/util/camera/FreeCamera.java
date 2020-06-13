package com.github.dabasan.joglf.gl.util.camera;

import static com.github.dabasan.basis.vector.VectorFunctions.*;

import com.github.dabasan.basis.vector.Vector;
import com.github.dabasan.joglf.gl.front.CameraFront;
import com.github.dabasan.tool.MathFunctions;

/**
 * Free camera
 * 
 * @author Daba
 *
 */
public class FreeCamera {
	private Vector position;
	private float v_rotate;
	private float h_rotate;

	private float translate_speed;
	private float rotate_speed;

	private static final float VROT_MIN = MathFunctions.DegToRad(-80.0f);
	private static final float VROT_MAX = MathFunctions.DegToRad(80.0f);

	public FreeCamera() {
		position = VGet(50.0f, 50.0f, 50.0f);
		v_rotate = 0.0f;
		h_rotate = 0.0f;

		translate_speed = 0.3f;
		rotate_speed = 0.01f;
	}

	public Vector GetPosition() {
		return new Vector(position);
	}
	public float GetVRotate() {
		return v_rotate;
	}
	public float GetHRotate() {
		return h_rotate;
	}

	public void SetPosition(Vector position) {
		this.position = position;
	}
	public void SetRotation(float v_rotate, float h_rotate) {
		this.v_rotate = v_rotate;
		this.h_rotate = h_rotate;
	}
	public void SetTranslateSpeed(float translate_speed) {
		this.translate_speed = translate_speed;
	}
	public void SetRotateSpeed(float rotate_speed) {
		this.rotate_speed = rotate_speed;
	}

	/**
	 * Translates the camera.<br>
	 * Set each argument to 1 or larger if you intend to move the camera in each
	 * direction.
	 * 
	 * @param front
	 *            Move forward
	 * @param back
	 *            Move backward
	 * @param right
	 *            Move right
	 * @param left
	 *            Move left
	 */
	public void Translate(int front, int back, int right, int left) {
		Vector translate = VGet(0.0f, 0.0f, 0.0f);

		final Vector front_vec = VGetFromAngles(v_rotate, h_rotate);
		Vector right_vec = VCross(front_vec, VGet(0.0f, 1.0f, 0.0f));
		right_vec = VNorm(right_vec);

		if (front > 0) {
			translate = VAdd(translate, front_vec);
		}
		if (back > 0) {
			translate = VAdd(translate, VScale(front_vec, -1.0f));
		}
		if (right > 0) {
			translate = VAdd(translate, right_vec);
		}
		if (left > 0) {
			translate = VAdd(translate, VScale(right_vec, -1.0f));
		}

		if (VSize(translate) > 1.0E-8f) {
			translate = VScale(translate, translate_speed);
			position = VAdd(position, translate);
		}
	}
	/**
	 * Rotates the camera.<br>
	 * Amount of rotation increases in proportion to the input arguments.
	 * 
	 * @param diff_x
	 *            Diff x
	 * @param diff_y
	 *            Diff y
	 */
	public void Rotate(int diff_x, int diff_y) {
		h_rotate += rotate_speed * (-diff_x);
		v_rotate += rotate_speed * (-diff_y);

		if (h_rotate > Math.PI) {
			h_rotate -= 2.0f * Math.PI;
		} else if (h_rotate < -Math.PI) {
			h_rotate += 2.0f * Math.PI;
		}

		if (v_rotate < VROT_MIN) {
			v_rotate = VROT_MIN;
		} else if (v_rotate > VROT_MAX) {
			v_rotate = VROT_MAX;
		}
	}

	/**
	 * Updates the camera.
	 */
	public void Update() {
		CameraFront.SetCameraPositionAndAngle(position, v_rotate, h_rotate, 0.0f);
	}
}
