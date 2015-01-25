package twg.tactic.base.engine.core;

public abstract class Game {
	
	private GameObject root = new GameObject();
	
	public GameObject getRootObject() { if(root == null) root = new GameObject(); return root; }
	
	public void init() {
		
	}
	
	public void input(float delta) {
		getRootObject().input(delta);
	}
	
	public void update(float delta) {
		getRootObject().update(delta);
	}
	
}