package twg.tactic.base.engine.rendering.resourceManagement;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL11.*;

public class TextureResource {
	
	private int id;
	private int refCount;
	
	public int getId() { return id; }

	public TextureResource() {
		this.id = glGenTextures();
		this.refCount = 1;
	}
	
	@Override
	protected void finalize() {
		glDeleteBuffers(id);
	}
	
	public void addReferance() {
		refCount++;
	}
	
	public boolean removeReferance() {
		refCount--;
		return refCount == 0;
	}
	
}
