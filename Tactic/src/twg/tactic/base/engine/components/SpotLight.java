package twg.tactic.base.engine.components;

import twg.tactic.base.engine.core.Vector3f;
import twg.tactic.base.engine.rendering.ForwardSpot;

public class SpotLight extends PointLight{
	
	private Vector3f direction;
	private float cutOff;
	
	public Vector3f getDirection() { return direction; }
	public float getCutOff() { return cutOff; }
	
	public void setDirection(Vector3f direction) { this.direction = direction.normalized(); }
	public void setCutOff(float cutOff) { this.cutOff = cutOff; }
	
	public SpotLight(Vector3f color, float intensity, Vector3f attenuation, Vector3f direction, float cutOff) {
		super(color, intensity, attenuation);
		this.direction = direction.normalized();
		this.cutOff = cutOff;
		
		setShader(ForwardSpot.getInstance());
	}
	
}
