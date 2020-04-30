package com.github.dabasan.joglf.gl.model.loader.bd1;

import java.util.List;

import com.github.dabasan.basis.vector.Vector;

/**
 * Inverts z-axis of BD1 files.
 * @author Daba
 *
 */
class BD1Inverter {
	public static void InvertZ(List<BD1Block> blocks){
		//Invert z-coordinate of the vertices.
		for(BD1Block block:blocks) {
			Vector[] vertex_positions=block.GetVertexPositions();
			for(int i=0;i<8;i++) {
				vertex_positions[i].SetZ(vertex_positions[i].GetZ()*(-1.0f));
			}
			for(int i=0;i<8;i++) {
				block.SetVertexPosition(i, vertex_positions[i]);
			}
		}
		//Resolve inconsistencies in the vertices.
		for(BD1Block block:blocks) {
			Vector[] vertex_positions=block.GetVertexPositions();
			float[] us=block.GetUs();
			float[] vs=block.GetVs();
			int[] texture_ids=block.GetTextureIDs();
			
			for(int i=0;i<4;i++) {
				block.SetVertexPosition(i, vertex_positions[3-i]);
			}
			for(int i=0;i<4;i++) {
				block.SetVertexPosition(i+4, vertex_positions[7-i]);
			}
			
			float[] us_orig=us.clone();
			float[] vs_orig=vs.clone();
			
			for(int i=0;i<6;i++) {
				int[] uv_indices;
				
				if(i==2)uv_indices=BD1Functions.GetFaceCorrespondingUVIndices(4);
				else if(i==4)uv_indices=BD1Functions.GetFaceCorrespondingUVIndices(2);
				else uv_indices=BD1Functions.GetFaceCorrespondingUVIndices(i);
				
				for(int j=0;j<4;j++) {
					int index=i*4+j;
					
					us[index]=us_orig[uv_indices[j]];
					vs[index]=vs_orig[uv_indices[j]];
				}
			}
			for(int i=0;i<24;i++) {
				block.SetUVs(i, us[i], vs[i]);
			}
			
			block.SetTextureID(2, texture_ids[4]);
			block.SetTextureID(4, texture_ids[2]);
		}
	}
}
