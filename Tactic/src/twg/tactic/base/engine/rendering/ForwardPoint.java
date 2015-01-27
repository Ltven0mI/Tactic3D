package twg.tactic.base.engine.rendering;

import twg.tactic.base.engine.components.BaseLight;
import twg.tactic.base.engine.components.PointLight;
import twg.tactic.base.engine.core.Matrix4f;
import twg.tactic.base.engine.core.Transform;

public class ForwardPoint extends Shader{
	
private static final ForwardPoint instance = new ForwardPoint();
	
	
	public static ForwardPoint getInstance() { return instance; }
	
	
	private ForwardPoint() {
		super();
		
		addVertexShader("forward-point.vs");
		addFragmentShader("forward-point.fs");
		
		setAttribLocation("position", 0);
		setAttribLocation("texCoord", 1);
		setAttribLocation("normal", 2);
		
		compileShader();
		
		addUniform("model");
		addUniform("MVP");
		
		addUniform("eyePos");
		
		addUniform("specInt");
		addUniform("specExp");
		
		addUniform("pointLight.base.color");
		addUniform("pointLight.base.intensity");
		addUniform("pointLight.atten.constant");
		addUniform("pointLight.atten.linear");
		addUniform("pointLight.atten.exponent");
		addUniform("pointLight.position");
		addUniform("pointLight.range");
	}
	
	public void updateUniforms(Transform transform, Material material) {
		Matrix4f worldMatrix = transform.getTransformation();
		Matrix4f projectedMatrix = getRenderingEngine().getMainCamera().getViewProjection().mul(worldMatrix);
		material.getTexture().bind();
		
		setUniform("MVP", projectedMatrix);
		setUniform("model", worldMatrix);
		
		setUniform("eyePos", getRenderingEngine().getMainCamera().getPos());
		
		setUniformf("specInt", material.getSpecularInt());
		setUniformf("specExp", material.getSpecularExp());
		
		setUniformPointLight("pointLight", (PointLight)getRenderingEngine().getActiveLight());
	}
	
	public void setUniformBaseLight(String uniformName, BaseLight baseLight) {
		setUniform(uniformName + ".color", baseLight.getColor());
		setUniformf(uniformName + ".intensity", baseLight.getIntensity());
	}
	
	public void setUniformPointLight(String uniformName, PointLight pointLight) {
		setUniformBaseLight(uniformName + ".base", pointLight);
		setUniformf(uniformName + ".atten.constant", pointLight.getConstant());
		setUniformf(uniformName + ".atten.linear", pointLight.getLinear());
		setUniformf(uniformName + ".atten.exponent", pointLight.getExponent());
		setUniform(uniformName + ".position", pointLight.getTransform().getPos());
		setUniformf(uniformName + ".range", pointLight.getRange());
	}
	
}
