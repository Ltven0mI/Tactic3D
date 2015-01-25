package twg.tactic.base.engine.rendering;

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
		
		setUniform("pointLight", getRenderingEngine().getPointLight());
	}
	
	public void setUniform(String uniformName, BaseLight baseLight) {
		setUniform(uniformName + ".color", baseLight.getColor());
		setUniformf(uniformName + ".intensity", baseLight.getIntensity());
	}
	
	public void setUniform(String uniformName, PointLight pointLight) {
		setUniform(uniformName + ".base", pointLight.getBase());
		setUniformf(uniformName + ".atten.constant", pointLight.getAtten().getConstant());
		setUniformf(uniformName + ".atten.linear", pointLight.getAtten().getLinear());
		setUniformf(uniformName + ".atten.exponent", pointLight.getAtten().getExponent());
		setUniform(uniformName + ".position", pointLight.getPos());
		setUniformf(uniformName + ".range", pointLight.getRange());
	}
	
}
