package twg.tactic.base.engine.components;

import twg.tactic.base.engine.core.Vector3f;
import twg.tactic.base.engine.rendering.RenderingEngine;
import twg.tactic.base.engine.rendering.Shader;

public class BaseLight extends GameComponent{
	
	private Vector3f color;
	private float intensity;
	private Shader shader;
	
	
	public Vector3f getColor() { return color; }
	public float getIntensity() { return intensity; }
	public Shader getShader() { return shader; }

	public void setColor(Vector3f color) { this.color = color; }
	public void setIntensity(float intensity) { this.intensity = intensity; }
	public void setShader(Shader shader) { this.shader = shader; }
	

	public BaseLight(Vector3f color, float intensity) {
		this.color = color;
		this.intensity = intensity;
	}
	
	public void addToRenderingEngine(RenderingEngine renderingEngine) {
		renderingEngine.addLight(this);
	}
	
}
