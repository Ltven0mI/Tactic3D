package twg.tactic.base.engine.rendering;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

import java.util.ArrayList;
import java.util.HashMap;

import twg.tactic.base.engine.core.FileInfo;
import twg.tactic.base.engine.core.Util;
import twg.tactic.base.engine.core.Vector3f;
import twg.tactic.base.engine.rendering.meshLoading.IndexedModel;
import twg.tactic.base.engine.rendering.meshLoading.OBJModel;
import twg.tactic.base.engine.rendering.resourceManagement.MeshResource;

public class Mesh {
	
	private static HashMap<String, MeshResource> loadedModels = new HashMap<String, MeshResource>();
	
	private MeshResource resource;
	private String filename;
	
	public Mesh(String filename) {
		this.filename = filename;
		MeshResource oldResource = loadedModels.get(filename);
		if(oldResource != null){
			resource = oldResource;
			resource.addReferance();
		}else{
			loadMesh(filename);
			loadedModels.put(filename, resource);
		}
	}
	
	@Override
	protected void finalize() {
		if(resource.removeReferance() && filename != ""){
			loadedModels.remove(filename);
		}
	}
	
	public Mesh(Vertex[] vertices, int[] indices) {
		this(vertices, indices, false);
	}
	
	public Mesh(Vertex[] vertices, int[] indices, boolean calcNormals) {
		filename = "";
		addVertices(vertices, indices, calcNormals);
	}
	
	private void addVertices(Vertex[] vertices, int[] indices, boolean calcNormals) {
		if(calcNormals)
		{
			calcNormals(vertices, indices);
		}
		
		resource = new MeshResource(indices.length);
		
		glBindBuffer(GL_ARRAY_BUFFER, resource.getVbo());
		glBufferData(GL_ARRAY_BUFFER, Util.createFlippedBuffer(vertices), GL_STATIC_DRAW);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, resource.getIbo());
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, Util.createFlippedBuffer(indices), GL_STATIC_DRAW);
	}
	
	public void draw() {
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glEnableVertexAttribArray(2);
		
		glBindBuffer(GL_ARRAY_BUFFER, resource.getVbo());
		glVertexAttribPointer(0, 3, GL_FLOAT, false, Vertex.size * 4, 0);
		glVertexAttribPointer(1, 2, GL_FLOAT, false, Vertex.size * 4, 12);
		glVertexAttribPointer(2, 3, GL_FLOAT, false, Vertex.size * 4, 20);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, resource.getIbo());
		glDrawElements(GL_TRIANGLES, resource.getSize(), GL_UNSIGNED_INT, 0);
		
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(2);
	}
	
	private void calcNormals(Vertex[] vertices, int[] indices) {
		for(int i = 0; i < indices.length; i += 3){
			int i0 = indices[i];
			int i1 = indices[i + 1];
			int i2 = indices[i + 2];
			
			Vector3f v1 = vertices[i1].getPos().sub(vertices[i0].getPos());
			Vector3f v2 = vertices[i2].getPos().sub(vertices[i0].getPos());
			
			Vector3f normal = v1.cross(v2).normalized();
			
			vertices[i0].setNormal(vertices[i0].getNormal().add(normal));
			vertices[i1].setNormal(vertices[i1].getNormal().add(normal));
			vertices[i2].setNormal(vertices[i2].getNormal().add(normal));
		}
		
		for(int i = 0; i < vertices.length; i++)
			vertices[i].setNormal(vertices[i].getNormal().normalized());
	}
	
	private void loadMesh(String filename) {
		String[] splitArray = filename.split("\\.");
		String extension = splitArray[splitArray.length-1];
				
		if(!extension.equals("obj")){
			System.err.println("Error: File format not supported for mesh data: " + extension);
			new Exception().printStackTrace();
			System.exit(1);
		}
		
		OBJModel test = new OBJModel(FileInfo.meshFolder+filename);
		IndexedModel model = test.toIndexedModel();
		model.calcNormals();
		
		ArrayList<Vertex> vertices = new ArrayList<Vertex>();
		
		for(int i=0; i<model.getPositions().size(); i++){
			vertices.add(new Vertex(model.getPositions().get(i), model.getTexCoords().get(i), model.getNormals().get(i)));	
		}
		
		Vertex[] vertexData = new Vertex[vertices.size()];
		Integer[] indexData = new Integer[model.getIndices().size()];
		vertices.toArray(vertexData);
		model.getIndices().toArray(indexData);
		
		addVertices(vertexData, Util.toIntArray(indexData), false);
	}
}