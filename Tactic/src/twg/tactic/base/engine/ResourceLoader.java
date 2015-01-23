package twg.tactic.base.engine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.ArrayList;

import org.newdawn.slick.opengl.TextureLoader;

public class ResourceLoader {
	
	private static final String shaderFolder = "./res/shaders/";
	private static final String meshFolder = "./res/models/";
	private static final String textureFolder = "./res/textures/";
	
	public static Texture loadTexture(String filename) {
		String[] splitArray = filename.split("\\.");
		String extension = splitArray[splitArray.length-1];
		
		try{
			return new Texture(TextureLoader.getTexture(extension, new FileInputStream(new File(textureFolder + filename))).getTextureID());
		}catch(Exception e){
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}
	
	public static String loadShader(String fileName) {
		StringBuilder shaderSource = new StringBuilder();
		BufferedReader shaderReader = null;
		
		try{
			shaderReader = new BufferedReader(new FileReader(shaderFolder+fileName));
			String line;
			while((line = shaderReader.readLine()) != null){
				shaderSource.append(line).append("\n");
			}
			shaderReader.close();
		}catch(Exception e){
			e.printStackTrace();
			System.exit(1);
		}
		
		return shaderSource.toString();
	}
	
	public static Mesh loadMesh(String filename) {
		String[] splitArray = filename.split("\\.");
		String extension = splitArray[splitArray.length-1];
		
		if(!extension.equals("obj")){
			System.err.println("Error: File format not supported for mesh data: " + extension);
			new Exception().printStackTrace();
			System.exit(1);
		}
		
		ArrayList<Vertex> vertices = new ArrayList<Vertex>();
		ArrayList<Integer> indices = new ArrayList<Integer>();
		
		BufferedReader meshReader = null;
		
		try{
			meshReader = new BufferedReader(new FileReader(meshFolder+filename));
			String line;
			while((line = meshReader.readLine()) != null){
				String[] tokens = line.split(" ");
				tokens = Util.removeEmptyStrings(tokens);
				
				if(tokens.length == 0 || tokens[0].equals("#")){
					continue;
				}else if(tokens[0].equals("v")){
					vertices.add(new Vertex(new Vector3f(Float.valueOf(tokens[1]), Float.valueOf(tokens[2]), Float.valueOf(tokens[3]))));
				}else if(tokens[0].equals("f")){
					indices.add(Integer.parseInt(tokens[1].split("/")[0])-1);
					indices.add(Integer.parseInt(tokens[2].split("/")[0])-1);
					indices.add(Integer.parseInt(tokens[3].split("/")[0])-1);
					
					if(tokens.length > 4){
						indices.add(Integer.parseInt(tokens[1].split("/")[0])-1);
						indices.add(Integer.parseInt(tokens[3].split("/")[0])-1);
						indices.add(Integer.parseInt(tokens[4].split("/")[0])-1);
					}
				}
			}
			meshReader.close();
			
			Mesh res = new Mesh();
			Vertex[] vertexData = new Vertex[vertices.size()];
			Integer[] indexData = new Integer[indices.size()];
			vertices.toArray(vertexData);
			indices.toArray(indexData);
			
			res.addVertices(vertexData, Util.toIntArray(indexData));
			return res;
		}catch(Exception e){
			e.printStackTrace();
			System.exit(1);
		}
		
		return null;
	}
	
}