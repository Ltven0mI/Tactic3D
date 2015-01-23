package twg.tactic.base.engine;

public class Material {
	
	private Texture texture;
	private Vector3f color;
	private float specularInt;
	private float specularExp;
	
	public Texture getTexture() { return texture; }
	public Vector3f getColor() { return color; }
	public float getSpecularInt() { return specularInt; }
	public float getSpecularExp() { return specularExp; }
	
	public void setTexture(Texture texture) { this.texture = texture; }
	public void setColor(Vector3f color) { this.color = color; }
	public void setSpecularInt(float specularInt) { this.specularInt = specularInt; }
	public void setSpecularExp(float specularExpc) { this.specularExp = specularExp; }
	
	public Material(Texture texture) {
		this(texture, new Vector3f(1, 1, 1));
	}
	
	public Material(Texture texture, Vector3f color) {
		this(texture, color, 2, 32);
	}
	
	public Material(Texture texture, Vector3f color, float specularInt, float specularExp) {
		this.texture = texture;
		this.color = color;
		this.specularInt = specularInt;
		this.specularExp = specularExp;
	}
	
}
