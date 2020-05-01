package com.github.dabasan.joglf.gl.model.loader.bd1;

import java.io.IOException;
import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.dabasan.basis.vector.Vector;
import com.github.dabasan.joglf.gl.model.buffer.BufferedVertices;
import com.github.dabasan.joglf.gl.shape.Triangle;
import com.github.dabasan.joglf.gl.shape.Vertex3D;
import com.github.dabasan.joglf.gl.texture.TextureMgr;
import com.github.dabasan.tool.FilenameFunctions;
import com.jogamp.common.nio.Buffers;

/**
 * BD1 loader
 * @author Daba
 *
 */
public class BD1Loader {
	private static Logger logger=LoggerFactory.getLogger(BD1Loader.class);
	
	public static List<BufferedVertices> LoadBD1(String bd1_filename){
		List<BufferedVertices> ret=new ArrayList<>();
		
		BD1Parser bd1_parser=null;
		try {
			bd1_parser=new BD1Parser(bd1_filename);
		}
		catch(IOException e) {
			logger.error("Error while reading.",e);
			return ret;
		}
		
		String bd1_directory=FilenameFunctions.GetFileDirectory(bd1_filename);
		
		Map<Integer, String> texture_filenames_map=bd1_parser.GetTextureFilenamesMap();
		Map<Integer, Integer> texture_handles_map=new HashMap<>();
		for(Map.Entry<Integer, String> entry:texture_filenames_map.entrySet()) {
			int texture_id=entry.getKey();
			
			String texture_filename=entry.getValue();
			if(texture_filename.equals("")) {
				texture_handles_map.put(texture_id, -1);
				continue;
			}
			
			texture_filename=bd1_directory+"/"+texture_filename;
			
			int texture_handle=TextureMgr.LoadTexture(texture_filename);
			texture_handles_map.put(texture_id, texture_handle);
		}
		
		List<BD1Block> blocks=bd1_parser.GetBlocks();
		BD1Inverter.InvertZ(blocks);
		
		BD1Triangulator triangulator=new BD1Triangulator();
		triangulator.TriangulateBlocks(blocks);
		
		Map<Integer, List<BD1Triangle>> triangles_map=triangulator.GetTrianglesMap();
		for(Map.Entry<Integer, List<BD1Triangle>> entry:triangles_map.entrySet()) {
			int texture_id=entry.getKey();
			List<BD1Triangle> triangles=entry.getValue();
			
			int texture_handle;
			if(texture_handles_map.containsKey(texture_id)==true) {
				texture_handle=texture_handles_map.get(texture_id);
			}
			else {
				texture_handle=-1;
			}
			
			int triangle_num=triangles.size();
			int vertex_num=triangle_num*3;
			
			BufferedVertices buffered_vertices=new BufferedVertices();
			IntBuffer indices=Buffers.newDirectIntBuffer(vertex_num);
			FloatBuffer pos_buffer=Buffers.newDirectFloatBuffer(vertex_num*3);
			FloatBuffer uv_buffer=Buffers.newDirectFloatBuffer(vertex_num*2);
			FloatBuffer norm_buffer=Buffers.newDirectFloatBuffer(vertex_num*3);
			
			for(int i=0;i<triangle_num;i++) {
				Triangle triangle=triangles.get(i);
				
				for(int j=0;j<3;j++) {
					Vertex3D vertex=triangle.GetVertex(j);
					
					Vector position=vertex.GetPos();
					float u=vertex.GetU();
					float v=vertex.GetV();
					Vector normal=vertex.GetNorm();
					
					indices.put(i*3+j);
					pos_buffer.put(position.GetX());
					pos_buffer.put(position.GetY());
					pos_buffer.put(position.GetZ());
					uv_buffer.put(u);
					uv_buffer.put(v);
					norm_buffer.put(normal.GetX());
					norm_buffer.put(normal.GetY());
					norm_buffer.put(normal.GetZ());
				}
			}
			
			((Buffer)indices).flip();
			((Buffer)pos_buffer).flip();
			((Buffer)uv_buffer).flip();
			((Buffer)norm_buffer).flip();
			
			buffered_vertices.SetIndicesBuffer(indices);
			buffered_vertices.SetPosBuffer(pos_buffer);
			buffered_vertices.SetUVBuffer(uv_buffer);
			buffered_vertices.SetNormBuffer(norm_buffer);
			buffered_vertices.SetTextureHandle(texture_handle);
			
			ret.add(buffered_vertices);
		}
		
		return ret;
	}
	public static List<BufferedVertices> LoadBD1_KeepOrder(String bd1_filename){
		List<BufferedVertices> ret=new ArrayList<>();
		
		BD1Parser bd1_parser=null;
		try {
			bd1_parser=new BD1Parser(bd1_filename);
		}
		catch(IOException e) {
			logger.error("Error while reading.",e);
			return ret;
		}
		
		String bd1_directory=FilenameFunctions.GetFileDirectory(bd1_filename);
		
		Map<Integer, String> texture_filenames_map=bd1_parser.GetTextureFilenamesMap();
		Map<Integer, Integer> texture_handles_map=new HashMap<>();
		for(Map.Entry<Integer, String> entry:texture_filenames_map.entrySet()) {
			int texture_id=entry.getKey();
			
			String texture_filename=entry.getValue();
			texture_filename=bd1_directory+"/"+texture_filename;
			
			int texture_handle=TextureMgr.LoadTexture(texture_filename);
			texture_handles_map.put(texture_id, texture_handle);
		}
		
		List<BD1Block> blocks=bd1_parser.GetBlocks();
		BD1Inverter.InvertZ(blocks);
		
		BD1Triangulator triangulator=new BD1Triangulator();
		triangulator.TriangulateBlocks(blocks);
		
		List<BD1Triangle> triangles_list=triangulator.GetTrianglesList();
		for(BD1Triangle triangle:triangles_list) {
			int texture_id=triangle.GetTextureID();
			int texture_handle=texture_handles_map.get(texture_id);
			
			int vertex_num=3;
			
			BufferedVertices buffered_vertices=new BufferedVertices();
			IntBuffer indices=Buffers.newDirectIntBuffer(vertex_num);
			FloatBuffer pos_buffer=Buffers.newDirectFloatBuffer(vertex_num*3);
			FloatBuffer uv_buffer=Buffers.newDirectFloatBuffer(vertex_num*2);
			FloatBuffer norm_buffer=Buffers.newDirectFloatBuffer(vertex_num*3);
			
			for(int i=0;i<3;i++) {
				Vertex3D vertex=triangle.GetVertex(i);
				
				Vector position=vertex.GetPos();
				float u=vertex.GetU();
				float v=vertex.GetV();
				Vector normal=vertex.GetNorm();
				
				indices.put(i);
				pos_buffer.put(position.GetX());
				pos_buffer.put(position.GetY());
				pos_buffer.put(position.GetZ());
				uv_buffer.put(u);
				uv_buffer.put(v);
				norm_buffer.put(normal.GetX());
				norm_buffer.put(normal.GetY());
				norm_buffer.put(normal.GetZ());
			}
			
			((Buffer)indices).flip();
			((Buffer)pos_buffer).flip();
			((Buffer)uv_buffer).flip();
			((Buffer)norm_buffer).flip();
			
			buffered_vertices.SetIndicesBuffer(indices);
			buffered_vertices.SetPosBuffer(pos_buffer);
			buffered_vertices.SetUVBuffer(uv_buffer);
			buffered_vertices.SetNormBuffer(norm_buffer);
			buffered_vertices.SetTextureHandle(texture_handle);
			
			ret.add(buffered_vertices);
		}
		
		return ret;
	}
}
