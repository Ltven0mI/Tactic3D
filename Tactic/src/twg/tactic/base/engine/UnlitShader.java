package twg.tactic.base.engine;

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
	
	public void updateUniforms(Matrix4f worldMatrix, Matrix4f projectedMatrix, Material material) {
		if(material.getTexture() != null){
			material.getTexture().bind();
		}else{
			RenderUtil.unbindTextures();
		}
		
		setUniform("transform", projectedMatrix);
		setUniform("baseColor", material.getColor());
		setUniformi("hasTexture", material.getTexture() == null ? 0 : 1);
	}
	
}
