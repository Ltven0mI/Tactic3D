package twg.tactic.base.engine.core;

import twg.tactic.base.engine.rendering.Shader;

public interface GameComponent {
	
	public void input(Transform transform, float delta);
	
	public void update(Transform transform, float delta);
	
	public void render(Transform transform, Shader shader);
	
}
