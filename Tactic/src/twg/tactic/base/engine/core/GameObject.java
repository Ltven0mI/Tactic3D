package twg.tactic.base.engine.core;

import java.util.ArrayList;

import twg.tactic.base.engine.components.GameComponent;
import twg.tactic.base.engine.rendering.RenderingEngine;
import twg.tactic.base.engine.rendering.Shader;

public class GameObject {
	
	private ArrayList<GameObject> children;
	private ArrayList<GameComponent> components;
	
	private Transform transform;
	
	public Transform getTransform() { return transform; }
	
	public GameObject() {
		children = new ArrayList<GameObject>();
		components = new ArrayList<GameComponent>();
		transform = new Transform();
	}
	
	public void addChild(GameObject child) {
		children.add(child);
		child.getTransform().setParent(transform);
	}
	
	public GameObject addComponent(GameComponent component) {
		components.add(component);
		component.setParent(this);
		return this;
	}
	
	public void input(float delta) {
		for(GameComponent component : components){
			component.input(delta);
		}
		
		for(GameObject child : children){
			child.input(delta);
		}
	}
	
	public void update(float delta) {
		transform.update();
		
		for(GameComponent component : components){
			component.update(delta);
		}
		
		for(GameObject child : children){
			child.update(delta);
		}
	}
	
	public void render(Shader shader) {
		for(GameComponent component : components){
			component.render(shader);
		}
		
		for(GameObject child : children){
			child.render(shader);
		}
	}
	
	public void addToRenderingEngine(RenderingEngine renderingEngine) {
		for(GameComponent component : components){
			component.addToRenderingEngine(renderingEngine);
		}
		
		for(GameObject child : children){
			child.addToRenderingEngine(renderingEngine);
		}
	}
	
}
