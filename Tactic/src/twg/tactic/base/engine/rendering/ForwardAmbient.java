package twg.tactic.base.engine.rendering;

import twg.tactic.base.engine.core.Matrix4f;
import twg.tactic.base.engine.core.Transform;

public class ForwardAmbient extends Shader{
	
	private static final ForwardAmbient instance = new ForwardAmbient();
	
	
	public static ForwardAmbient getInstance() { return instance; }
	
	
	private ForwardAmbient() {
		super();
		
		addVertexShader("forward-ambient.vs");
		addFragmentShader("forward-ambient.fs");
		
		setAttribLocation("position", 0);
		setAttribLocation("texCoord", 1);
		
		compileShader();
		
		addUniform("MVP");
		addUniform("ambientIntensity");
	}
	
	public void updateUniforms(Transform transform, Material material, RenderingEngine renderingEngine) {
		Matrix4f worldMatrix = transform.getTransformation();
		Matrix4f projectedMatrix = renderingEngine.getMainCamera().getViewProjection().mul(worldMatrix);
		material.getTexture("diffuse").bind();
		
		setUniform("MVP", projectedMatrix);
		setUniform("ambientIntensity", renderingEngine.getAmbientLight());
	}
	
}
