package twg.tactic.base.engine;

public class SpotLight {
	
	private PointLight pointLight;
	private Vector3f direction;
	private float cutOff;
	
	public PointLight getPointLight() { return pointLight; }
	public Vector3f getDirection() { return direction; }
	public float getCutOff() { return cutOff; }
	
	public void setPointLight(PointLight pointLight) { this.pointLight = pointLight; }
	public void setDirection(Vector3f direction) { this.direction = direction.normalized(); }
	public void setCutOff(float cutOff) { this.cutOff = cutOff; }
	
	public SpotLight(PointLight pointLight, Vector3f direction, float cutOff) {
		this.pointLight = pointLight;
		this.direction = direction.normalized();
		this.cutOff = cutOff;
	}
	
}
