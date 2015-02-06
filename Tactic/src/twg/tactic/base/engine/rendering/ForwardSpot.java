package twg.tactic.base.engine.rendering;

import twg.tactic.base.engine.components.BaseLight;
import twg.tactic.base.engine.components.PointLight;
import twg.tactic.base.engine.components.SpotLight;
import twg.tactic.base.engine.core.Matrix4f;
import twg.tactic.base.engine.core.Transform;

public class ForwardSpot extends Shader{
	
private static final ForwardSpot instance = new ForwardSpot();
	
	
	public static ForwardSpot getInstance() { return instance; }
	
	
	private ForwardSpot() {
		super();
		
		addVertexShader("forward-spot.vs");
		addFragmentShader("forward-spot.fs");
		
		setAttribLocation("position", 0);
		setAttribLocation("texCoord", 1);
		setAttribLocation("normal", 2);
		
		compileShader();
		
		addUniform("model");
		addUniform("MVP");
		
		addUniform("eyePos");
		
		addUniform("specInt");
		addUniform("specExp");
		
		addUniform("spotLight.pointLight.base.color");
		addUniform("spotLight.pointLight.base.intensity");
		addUniform("spotLight.pointLight.atten.constant");
		addUniform("spotLight.pointLight.atten.linear");
		addUniform("spotLight.pointLight.atten.exponent");
		addUniform("spotLight.pointLight.position");
		addUniform("spotLight.pointLight.range");
		addUniform("spotLight.direction");
		addUniform("spotLight.cutOff");
	}
	
	public void updateUniforms(Transform transform, Material material) {
		Matrix4f worldMatrix = transform.getTransformation();
		Matrix4f projectedMatrix = getRenderingEngine().getMainCamera().getViewProjection().mul(worldMatrix);
		material.getTexture().bind();
		
		setUniform("MVP", projectedMatrix);
		setUniform("model", worldMatrix);
		
		setUniform("eyePos", getRenderingEngine().getMainCamera().getTransform().getTransformedPos());
		
		setUniformf("specInt", material.getSpecularInt());
		setUniformf("specExp", material.getSpecularExp());
		
		setUniformSpotLight("spotLight", (SpotLight)getRenderingEngine().getActiveLight());
	}
	
	public void setUniformBaseLight(String uniformName, BaseLight baseLight) {
		setUniform(uniformName + ".color", baseLight.getColor());
		setUniformf(uniformName + ".intensity", baseLight.getIntensity());
	}
	
	public void setUniformSpotLight(String uniformName, SpotLight spotLight) {
		setUniformBaseLight(uniformName + ".pointLight.base", spotLight);
		setUniformf(uniformName + ".pointLight.atten.constant", spotLight.getConstant());
		setUniformf(uniformName + ".pointLight.atten.linear", spotLight.getLinear());
		setUniformf(uniformName + ".pointLight.atten.exponent", spotLight.getExponent());
		setUniform(uniformName + ".pointLight.position", spotLight.getTransform().getTransformedPos());
		setUniformf(uniformName + ".pointLight.range", spotLight.getRange());
		setUniform(uniformName + ".direction", spotLight.getDirection());
		setUniformf(uniformName + ".cutOff", spotLight.getCutOff());
	}
	
}
