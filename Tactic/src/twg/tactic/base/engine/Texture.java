package twg.tactic.base.engine;

import static org.lwjgl.opengl.GL11.*;

import java.io.File;
import java.io.FileInputStream;

import org.newdawn.slick.opengl.TextureLoader;

public class Texture {
	
	private int id;
	
	public int getId() { return id; }
	
	public Texture(String fileName) {
		this(loadTexture(fileName));
	}
	
	public Texture(int id) {
		this.id = id;
	}
	
	public void bind() {
		glBindTexture(GL_TEXTURE_2D, id);
	}
	
	private static int loadTexture(String filename) {
		String[] splitArray = filename.split("\\.");
		String extension = splitArray[splitArray.length-1];
		
		try{
			return TextureLoader.getTexture(extension, new FileInputStream(new File(FileInfo.textureFolder + filename))).getTextureID();
		}catch(Exception e){
			e.printStackTrace();
			System.exit(1);
		}
		return 0;
	}

}
