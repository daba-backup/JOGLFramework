package com.github.dabasan.joglf.gl.model.collision;

import java.util.ArrayList;
import java.util.List;

/**
 * Collision info
 * @author Daba
 *
 */
public class CollInfo {
	private List<CollTriangle> triangles;
	
	public CollInfo() {
		triangles=new ArrayList<>();
	}
	
	public void AddTriangle(CollTriangle triangle) {
		triangles.add(triangle);
	}
	public List<CollTriangle> GetCollTriangles(){
		return new ArrayList<>(triangles);
	}
}
