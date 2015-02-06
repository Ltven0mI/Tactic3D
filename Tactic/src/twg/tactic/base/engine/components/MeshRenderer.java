package twg.tactic.base.engine.components;

import twg.tactic.base.engine.rendering.Material;
import twg.tactic.base.engine.rendering.Mesh;
import twg.tactic.base.engine.rendering.Shader;

public class MeshRenderer extends GameComponent{
	
	private Mesh mesh;
	private Material material;
	
	public MeshRenderer(Mesh mesh, Material material) {
		this.mesh = mesh;
		this.material = material;
	}
	
	@Override
	public void render(Shader shader) {
		shader.bind();
		shader.updateUniforms(getTransform(), material);
		mesh.draw();
	}
	
}
