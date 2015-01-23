package twg.tactic.base.engine;

public class UnlitShader extends Shader{
	
	public UnlitShader() {
		super();
		
		addVertexShader(ResourceLoader.loadShader("unlitVert.vs"));
		addFragmentShader(ResourceLoader.loadShader("unlitFrag.fs"));
		compileShader();
		
		addUniform("transform");
		addUniform("color");
		addUniform("hasTexture");
	}
	
	public void updateUniforms(Matrix4f worldMatrix, Matrix4f projectedMatrix, Material material) {
		if(material.getTexture() != null){
			material.getTexture().bind();
		}else{
			RenderUtil.unbindTextures();
		}
		
		setUniform("transform", projectedMatrix);
		setUniform("color", material.getColor());
		setUniformi("hasTexture", material.getTexture() == null ? 0 : 1);
	}
	
}
