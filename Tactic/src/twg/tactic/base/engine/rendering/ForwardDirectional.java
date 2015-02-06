package twg.tactic.base.engine.rendering;

import twg.tactic.base.engine.components.BaseLight;
import twg.tactic.base.engine.components.DirectionalLight;
import twg.tactic.base.engine.core.Matrix4f;
import twg.tactic.base.engine.core.Transform;

public class ForwardDirectional extends Shader{
	
private static final ForwardDirectional instance = new ForwardDirectional();
	
	
	public static ForwardDirectional getInstance() { return instance; }
	
	
	private ForwardDirectional() {
		super();
		
		addVertexShader("forward-directional.vs");
		addFragmentShader("forward-directional.fs");
		
		setAttribLocation("position", 0);
		setAttribLocation("texCoord", 1);
		setAttribLocation("normal", 2);
		
		compileShader();
		
		addUniform("model");
		addUniform("MVP");
		
		addUniform("eyePos");
		
		addUniform("specInt");
		addUniform("specExp");
		
		addUniform("directionalLight.base.color");
		addUniform("directionalLight.base.intensity");
		addUniform("directionalLight.direction");
	}
	
	public void updateUniforms(Transform transform, Material material) {
		Matrix4f worldMatrix = transform.getTransformation();
		Matrix4f projectedMatrix = getRenderingEngine().getMainCamera().getViewProjection().mul(worldMatrix);
		material.getTexture().bind();
		
		setUniform("MVP", projectedMatrix);
		setUniform("model", worldMatrix);
		
		setUniform("eyePos", getRenderingEngine().getMainCamera().getTransform().getPos());
		
		setUniformf("specInt", material.getSpecularInt());
		setUniformf("specExp", material.getSpecularExp());
		
		setUniformDirectionalLight("directionalLight", (DirectionalLight)getRenderingEngine().getActiveLight());
	}
	
	public void setUniformBaseLight(String uniformName, BaseLight light) {
		setUniform(uniformName + ".color", light.getColor());
		setUniformf(uniformName + ".intensity", light.getIntensity());
	}
	
	public void setUniformDirectionalLight(String uniformName, DirectionalLight light) {
		setUniformBaseLight(uniformName + ".base", light);
		setUniform(uniformName + ".direction", light.getDirection());
	}
	
}