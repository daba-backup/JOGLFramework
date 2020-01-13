package com.daxie.joglf.gl.model.collision;

import java.util.ArrayList;
import java.util.List;

/**
 * Collection of collision information
 * @author Daba
 *
 */
public class CollResultDim {
	private List<CollResult> coll_results;
	
	public CollResultDim() {
		coll_results=new ArrayList<>();
	}
	
	public void AddCollResult(CollResult coll_result) {
		coll_results.add(coll_result);
	}
	
	public List<CollResult> GetCollResults(){
		return new ArrayList<>(coll_results);
	}
	public int GetHitNum() {
		return coll_results.size();
	}
	
	public CollResult GetCollResult(int index) {
		CollResult coll_result=coll_results.get(index);
		return new CollResult(coll_result);
	}
}
