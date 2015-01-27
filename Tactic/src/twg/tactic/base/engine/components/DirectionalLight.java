package twg.tactic.base.engine.components;

import twg.tactic.base.engine.core.Vector3f;
import twg.tactic.base.engine.rendering.ForwardDirectional;
import twg.tactic.base.engine.rendering.RenderingEngine;

public class DirectionalLight extends BaseLight{
	
	private Vector3f direction;
	

	public Vector3f getDirection() { return direction; }

	public void setDirection(Vector3f direction) { this.direction = direction.normalized(); }
	

	public DirectionalLight(Vector3f color, float intensity, Vector3f direction) {
		super(color, intensity);
		this.direction = direction.normalized();
		
		setShader(ForwardDirectional.getInstance());
	}

}
