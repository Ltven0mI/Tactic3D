package twg.tactic.base.engine.components;

import twg.tactic.base.engine.core.Vector3f;
import twg.tactic.base.engine.rendering.Shader;

public class SpotLight extends PointLight{
	
	private float cutOff;
	
	public Vector3f getDirection() { return getTransform().getTransformedRot().getForward(); }
	public float getCutOff() { return cutOff; }
	
	public void setCutOff(float cutOff) { this.cutOff = cutOff; }
	
	public SpotLight(Vector3f color, float intensity, Vector3f attenuation, float cutOff) {
		super(color, intensity, attenuation);
		this.cutOff = cutOff;
		
		setShader(new Shader("forward-spot"));
	}
	
}
