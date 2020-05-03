package com.github.dabasan.joglf.gl;

import com.github.dabasan.basis.vector.Vector;
import com.github.dabasan.basis.vector.VectorFunctions;
import com.github.dabasan.joglf.gl.front.CameraFront;
import com.github.dabasan.tool.MathFunctions;

class FreeCamera {
	private Vector position;
	private float v_rotate;
	private float h_rotate;

	private float translate_speed;
	private float rotate_speed;

	private static final float VROT_MIN = MathFunctions.DegToRad(-80.0f);
	private static final float VROT_MAX = MathFunctions.DegToRad(80.0f);

	public FreeCamera() {
		position = VectorFunctions.VGet(0.0f, 0.0f, 0.0f);
		v_rotate = 0.0f;
		h_rotate = 0.0f;

		translate_speed = 0.3f;
		rotate_speed = 0.02f;
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

	public void Translate(int front, int back, int right, int left) {
		Vector translate = VectorFunctions.VGet(0.0f, 0.0f, 0.0f);

		Vector front_vec = VectorFunctions.VGetFromAngles(v_rotate, h_rotate);
		Vector right_vec = VectorFunctions.VCross(front_vec,
				VectorFunctions.VGet(0.0f, 1.0f, 0.0f));
		right_vec = VectorFunctions.VNorm(right_vec);

		if (front > 0) {
			translate = VectorFunctions.VAdd(translate, front_vec);
		}
		if (back > 0) {
			translate = VectorFunctions.VAdd(translate,
					VectorFunctions.VScale(front_vec, -1.0f));
		}
		if (right > 0) {
			translate = VectorFunctions.VAdd(translate, right_vec);
		}
		if (left > 0) {
			translate = VectorFunctions.VAdd(translate,
					VectorFunctions.VScale(right_vec, -1.0f));
		}

		if (VectorFunctions.VSize(translate) > 1.0E-8f) {
			translate = VectorFunctions.VScale(translate, translate_speed);
			position = VectorFunctions.VAdd(position, translate);
		}
	}
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

	public void Update() {
		CameraFront.SetCameraPositionAndAngle(position, v_rotate, h_rotate,
				0.0f);
	}
}
