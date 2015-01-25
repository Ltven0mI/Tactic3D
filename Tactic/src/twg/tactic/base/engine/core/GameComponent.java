package twg.tactic.base.engine.core;

import twg.tactic.base.engine.rendering.Shader;

public interface GameComponent {
	
	public void input(Transform transform);
	
	public void update(Transform transform);
	
	public void render(Transform transform, Shader shader);
	
}
