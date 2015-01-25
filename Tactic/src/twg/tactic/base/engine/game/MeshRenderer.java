package twg.tactic.base.engine.game;

import twg.tactic.base.engine.core.GameComponent;
import twg.tactic.base.engine.core.Transform;
import twg.tactic.base.engine.rendering.Material;
import twg.tactic.base.engine.rendering.Mesh;
import twg.tactic.base.engine.rendering.Shader;

public class MeshRenderer implements GameComponent{
	
	private Mesh mesh;
	private Material material;
	
	public MeshRenderer(Mesh mesh, Material material) {
		this.mesh = mesh;
		this.material = material;
	}

	@Override
	public void input(Transform transform, float delta) {}

	@Override
	public void update(Transform transform, float delta) {}
	
	@Override
	public void render(Transform transform, Shader shader) {
		shader.bind();
		shader.updateUniforms(transform, material);
		mesh.draw();
	}
	
}
