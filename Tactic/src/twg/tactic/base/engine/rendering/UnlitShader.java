package twg.tactic.base.engine.rendering;

import twg.tactic.base.engine.core.Matrix4f;
import twg.tactic.base.engine.core.Transform;

public class UnlitShader extends Shader{
	
	private static final UnlitShader instance = new UnlitShader();
	
	public static UnlitShader getInstance() { return instance; }
	
	private UnlitShader() {
		super();
		
		addVertexShader("unlitVert.vs");
		addFragmentShader("unlitFrag.fs");
		compileShader();
		
		addUniform("transform");
		addUniform("baseColor");
		addUniform("hasTexture");
	}
	
	public void updateUniforms(Transform transform, Material material) {
		Matrix4f worldMatrix = transform.getTransformation();
		Matrix4f projectedMatrix = getRenderingEngine().getMainCamera().getViewProjection().mul(worldMatrix);
		material.getTexture().bind();
		
		setUniform("transform", projectedMatrix);
		setUniform("baseColor", material.getColor());
		setUniformi("hasTexture", material.getTexture() == null ? 0 : 1);
	}
	
}
