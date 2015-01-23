package twg.tactic.base.engine;

public class PhongShader extends Shader{
	
	private static Vector3f ambientLight = new Vector3f(0.1f, 0.1f, 0.1f);
	private static DirectionalLight directionalLight = new DirectionalLight(new BaseLight(new Vector3f(1, 1, 1), 0), new Vector3f(0, 0, 0));
	
	public static Vector3f getAmbientLight() { return ambientLight; }

	public static void setAmbientLight(Vector3f ambientLight) { PhongShader.ambientLight = ambientLight; }
	public static void setDirectionalLight(DirectionalLight directionalLight) { PhongShader.directionalLight = directionalLight; }

	public PhongShader() {
		super();
		
		addVertexShader(ResourceLoader.loadShader("phongVert.vs"));
		addFragmentShader(ResourceLoader.loadShader("phongFrag.fs"));
		compileShader();
		
		addUniform("transform");
		addUniform("transformProjected");
		addUniform("baseColor");
		addUniform("hasTexture");
		addUniform("ambientLight");
		
		addUniform("specInt");
		addUniform("specExp");
		addUniform("eyePos");
		
		addUniform("directionalLight.base.color");
		addUniform("directionalLight.base.intensity");
		addUniform("directionalLight.direction");
	}
	
	public void updateUniforms(Matrix4f worldMatrix, Matrix4f projectedMatrix, Material material) {
		if(material.getTexture() != null){
			material.getTexture().bind();
		}else{
			RenderUtil.unbindTextures();
		}
		
		setUniform("transform", worldMatrix);
		setUniform("transformProjected", projectedMatrix);
		setUniform("baseColor", material.getColor());
		setUniformi("hasTexture", material.getTexture() == null ? 0 : 1);
		
		setUniform("ambientLight", PhongShader.ambientLight);
		setUniform("directionalLight", directionalLight);
		
		setUniformf("specInt", material.getSpecularInt());
		setUniformf("specExp", material.getSpecularExp());
		
		setUniform("eyePos", Transform.getCamera().getPos());
	}
	
	public void setUniform(String uniformName, BaseLight baseLight) {
		setUniform(uniformName + ".color", baseLight.getColor());
		setUniformf(uniformName + ".intensity", baseLight.getIntensity());
	}
	
	public void setUniform(String uniformName, DirectionalLight directionalLight) {
		setUniform(uniformName+".base", directionalLight.getBase());
		setUniform(uniformName+".direction", directionalLight.getDirection());
	}
	
}
