package twg.tactic.base.engine.rendering.resourceManagement;

import static org.lwjgl.opengl.GL15.*;

public class MeshResource {
	
	private int vbo;
	private int ibo;
	private int size;
	private int refCount;
	
	public int getVbo() { return vbo; }
	public int getIbo() { return ibo; }
	public int getSize() { return size; }
	
	public void setVbo(int vbo) { this.vbo = vbo; }
	public void setIbo(int ibo) { this.ibo = ibo; }

	public MeshResource(int size) {
		vbo = glGenBuffers();
		ibo = glGenBuffers();
		this.size = size;
		this.refCount = 1;
	}
	
	@Override
	protected void finalize() {
		glDeleteBuffers(vbo);
		glDeleteBuffers(ibo);
	}
	
	public void addReferance() {
		refCount++;
	}
	
	public boolean removeReferance() {
		refCount--;
		return refCount == 0;
	}
	
}
