package com.github.dabasan.joglf.gl.model.loader.bd1;

import com.github.dabasan.basis.vector.Vector;
import com.github.dabasan.basis.vector.VectorFunctions;

/**
 * A block contained in the BD1 format.
 * @author Daba
 *
 */
class BD1Block {
	private Vector[] vertex_positions;
	private float[] us;
	private float[] vs;
	private int[] texture_ids;
	private boolean enabled_flag;
	
	public BD1Block() {
		vertex_positions=new Vector[8];
		us=new float[24];
		vs=new float[24];
		texture_ids=new int[6];
		
		for(int i=0;i<vertex_positions.length;i++)vertex_positions[i]=VectorFunctions.VGet(0.0f, 0.0f, 0.0f);
		for(int i=0;i<us.length;i++)us[i]=0.0f;
		for(int i=0;i<vs.length;i++)vs[i]=0.0f;
		for(int i=0;i<texture_ids.length;i++)texture_ids[i]=0;
		enabled_flag=false;
	}
	public BD1Block(BD1Block block) {
		vertex_positions=block.GetVertexPositions();
		us=block.GetUs();
		vs=block.GetVs();
		texture_ids=block.GetTextureIDs();
		enabled_flag=block.GetEnabledFlag();
	}
	
	/**
	 * @param index Index of array vertex_positions
	 * @param x X-coordinate
	 */
	public void SetVertexPositionX(int index,float x) {
		vertex_positions[index].SetX(x);
	}
	/**
	 * @param index Index of array vertex_positions
	 * @param y Y-coordinate
	 */
	public void SetVertexPositionY(int index,float y) {
		vertex_positions[index].SetY(y);
	}
	/**
	 * @param index Index of array vertex_positions
	 * @param z Z-coordinate
	 */
	public void SetVertexPositionZ(int index,float z) {
		vertex_positions[index].SetZ(z);
	}
	/**
	 * @param index Index of array us
	 * @param u U-coordinate
	 */
	public void SetU(int index,float u) {
		us[index]=u;
	}
	/**
	 * @param index Index of array vs
	 * @param v V-coordinate
	 */
	public void SetV(int index,float v) {
		vs[index]=v;
	}
	/**
	 * @param index Index of array texture_ids
	 * @param texture_id Texture ID
	 */
	public void SetTextureID(int index,int texture_id) {
		texture_ids[index]=texture_id;
	}
	/**
	 * @param enabled_flag Enabled flag
	 */
	public void SetEnabledFlag(boolean enabled_flag) {
		this.enabled_flag=enabled_flag;
	}
	
	/**
	 * @param index Index of array vertex_positions
	 * @param pos Position
	 */
	public void SetVertexPosition(int index,Vector pos) {
		vertex_positions[index]=pos;
	}
	/**
	 * @param index Index of array us and vs
	 * @param u U-coordinate
	 * @param v V-coordinate
	 */
	public void SetUVs(int index,float u,float v) {
		us[index]=u;
		vs[index]=v;
	}
	
	/**
	 * @return An array of vertex positions
	 */
	public Vector[] GetVertexPositions() {
		Vector[] ret=new Vector[8];
		for(int i=0;i<8;i++) {
			ret[i]=new Vector(vertex_positions[i]);
		}
		
		return ret;
	}
	/**
	 * @return An array of u-coordinates
	 */
	public float[] GetUs() {
		return us.clone();
	}
	/**
	 * @return An array of v-coordinates
	 */
	public float[] GetVs() {
		return vs.clone();
	}
	/**
	 * @return An array of texture IDs
	 */
	public int[] GetTextureIDs() {
		return texture_ids.clone();
	}
	/**
	 * @return Enabled flag
	 */
	public boolean GetEnabledFlag() {
		return enabled_flag;
	}
}
