package twg.tactic.base.engine.components;

import twg.tactic.base.engine.core.Vector3f;
import twg.tactic.base.engine.rendering.ForwardDirectional;
import twg.tactic.base.engine.rendering.RenderingEngine;

public class DirectionalLight extends BaseLight{

	public Vector3f getDirection() { return getTransform().getTransformedRot().getForward(); }
	

	public DirectionalLight(Vector3f color, float intensity) {
		super(color, intensity);
		
		setShader(ForwardDirectional.getInstance());
	}

}
