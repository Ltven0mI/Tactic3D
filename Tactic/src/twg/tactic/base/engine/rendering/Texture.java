package twg.tactic.base.engine.rendering;

import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_REPEAT;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_RGBA8;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameterf;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.ByteBuffer;
import java.util.HashMap;

import javax.imageio.ImageIO;

import twg.tactic.base.engine.core.FileInfo;
import twg.tactic.base.engine.core.Util;
import twg.tactic.base.engine.rendering.resourceManagement.TextureResource;

public class Texture {
	
	private static HashMap<String, TextureResource> loadedTextures = new HashMap<String, TextureResource>();
	
	private TextureResource resource;
	private String filename;
	
	public int getId() { return resource.getId(); }
	
	public Texture(String filename) {
		this.filename = filename;
		TextureResource oldResource = loadedTextures.get(filename);
		if(oldResource != null){
			resource = oldResource;
			resource.addReferance();
		}else{
			resource = loadTexture(filename);
			loadedTextures.put(filename, resource);
		}
	}
	
	@Override
	protected void finalize() {
		if(resource.removeReferance() && filename != ""){
			loadedTextures.remove(filename);
		}
	}
	
	public void bind() {
		bind(0);
	}
	
	public void bind(int samplerSlot) {
		assert(samplerSlot >= 0 && samplerSlot <= 31);
		glActiveTexture(GL_TEXTURE0 + samplerSlot);
		glBindTexture(GL_TEXTURE_2D, resource.getId());
	}
	
	private static TextureResource loadTexture(String filename) {
		String[] splitArray = filename.split("\\.");
		String extension = splitArray[splitArray.length-1];
		
		try{
			BufferedImage image = ImageIO.read(new File(FileInfo.textureFolder + filename));
			int[] pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
			
			ByteBuffer buffer = Util.createByteBuffer(image.getWidth() * image.getHeight() * 4);
			boolean hasAlpha = image.getColorModel().hasAlpha();
			
			for(int y=0; y<image.getHeight(); y++){
				for(int x=0; x<image.getWidth(); x++){
					int pixel = pixels[y * image.getWidth() + x];
					buffer.put((byte)((pixel >> 16) & 0xFF));
					buffer.put((byte)((pixel >> 8) & 0xFF));
					buffer.put((byte)((pixel) & 0xFF));
					if(hasAlpha){
						buffer.put((byte)((pixel >> 24) & 0xFF));
					}else{
						buffer.put((byte)(0xFF));
					}
				}
			}
			
			buffer.flip();
			
			TextureResource resource = new TextureResource();
			glBindTexture(GL_TEXTURE_2D, resource.getId());
			
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
			
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
			
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
			
			return resource;
		}catch(Exception e){
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}

}
