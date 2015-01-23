package twg.tactic.base.engine;

public class PhongShader extends Shader{
	
	private static Vector3f ambientLight;
	
	public static Vector3f getAmbientLight() { return ambientLight; }

	public static void setAmbientLight(Vector3f ambientLight) { PhongShader.ambientLight = ambientLight; }

	public PhongShader() {
		super();
		
		addVertexShader(ResourceLoader.loadShader("phongVert.vs"));
		addFragmentShader(ResourceLoader.loadShader("phongFrag.fs"));
		compileShader();
		
		addUniform("transform");
		addUniform("baseColor");
		addUniform("hasTexture");
		addUniform("ambientLight");
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
		setUniform("ambientLight", PhongShader.ambientLight);
	}
	
}
