package twg.tactic.base.engine.components;

import twg.tactic.base.engine.core.GameObject;
import twg.tactic.base.engine.core.Transform;
import twg.tactic.base.engine.rendering.RenderingEngine;
import twg.tactic.base.engine.rendering.Shader;

public abstract class GameComponent {
	
	private GameObject parent;
	
	
	public void setParent(GameObject parent) { this.parent = parent; }
	
	public Transform getTransform() { return parent.getTransform(); }
	
	
	public void input(float delta) {}
	
	public void update(float delta) {}
	
	public void render(Shader shader, RenderingEngine renderingEngine) {}
	
	public void addToRenderingEngine(RenderingEngine renderingEngine) {
		
	}
	
}
