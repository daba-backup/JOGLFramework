package com.github.dabasan.joglf.gl.model.loader.bd1;

import com.github.dabasan.joglf.gl.shape.Triangle;

/**
 * Triangle for BD1
 * 
 * @author Daba
 *
 */
class BD1Triangle extends Triangle {
	private int texture_id;

	public BD1Triangle() {
		texture_id = -1;
	}

	public void SetTextureID(int texture_id) {
		this.texture_id = texture_id;
	}
	public int GetTextureID() {
		return texture_id;
	}
}
