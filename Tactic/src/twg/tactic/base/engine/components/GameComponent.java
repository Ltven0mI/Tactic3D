package twg.tactic.base.engine.components;

import twg.tactic.base.engine.core.Transform;
import twg.tactic.base.engine.rendering.RenderingEngine;
import twg.tactic.base.engine.rendering.Shader;

public abstract class GameComponent {
	
	public void input(Transform transform, float delta) {}
	
	public void update(Transform transform, float delta) {}
	
	public void render(Transform transform, Shader shader) {}
	
	public void addToRenderingEngine(RenderingEngine renderingEngine) {
		
	}
	
}
