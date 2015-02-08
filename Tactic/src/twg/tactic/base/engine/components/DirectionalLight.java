package twg.tactic.base.engine.components;

import twg.tactic.base.engine.core.Vector3f;
import twg.tactic.base.engine.rendering.Shader;

public class DirectionalLight extends BaseLight{

	public Vector3f getDirection() { return getTransform().getTransformedRot().getForward(); }
	

	public DirectionalLight(Vector3f color, float intensity) {
		super(color, intensity);
		
		setShader(new Shader("forward-directional"));
	}

}
