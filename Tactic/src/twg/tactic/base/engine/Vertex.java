package twg.tactic.base.engine;

public class Vertex {
	
	public static final int size = 3;
	
	private Vector3f pos;
	
	public Vector3f GetPos() { return pos; }
	
	public void SetPos(Vector3f pos) { this.pos = pos; }
	
	public Vertex(Vector3f pos) {
		this.pos = pos;
	}
	
}
