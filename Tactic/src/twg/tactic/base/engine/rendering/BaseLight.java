package twg.tactic.base.engine.rendering;

import twg.tactic.base.engine.core.Vector3f;

public class BaseLight {
	
	private Vector3f color;
	private float intensity;
	
	public Vector3f getColor() { return color; }
	public float getIntensity() { return intensity; }

	public void setColor(Vector3f color) { this.color = color; }
	public void setIntensity(float intensity) { this.intensity = intensity; }

	public BaseLight(Vector3f color, float intensity) {
		this.color = color;
		this.intensity = intensity;
	}
	
}
