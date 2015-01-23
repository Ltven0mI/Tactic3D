package twg.tactic.base.engine;

public class Material {
	
	private Texture texture;
	private Vector3f color;
	
	public Texture getTexture() { return texture; }
	public Vector3f getColor() { return color; }
	
	public void setTexture(Texture texture) { this.texture = texture; }
	public void setColor(Vector3f color) { this.color = color; }
	
	public Material(Texture texture, Vector3f color) {
		this.texture = texture;
		this.color = color;
	}
	
}
