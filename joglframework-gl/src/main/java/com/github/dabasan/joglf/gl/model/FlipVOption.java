package com.github.dabasan.joglf.gl.model;

/**
 * Options for flip of texture v-coordinate<br>
 * MUST_FLIP_VERTICALLY flips v-coordinate only if getMustFlipVertically() of
 * the underlying JOGL Texture instance returns true.
 * 
 * @author Daba
 *
 */
public enum FlipVOption {
	NONE, MUST_FLIP_VERTICALLY, ALL
}
