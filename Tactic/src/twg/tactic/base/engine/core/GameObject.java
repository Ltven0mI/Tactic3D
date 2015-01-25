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
	}
	
	public void addComponent(GameComponent component) {
		components.add(component);
	}
	
	public void input(float delta) {
		for(GameComponent component : components){
			component.input(transform, delta);
		}
		
		for(GameObject child : children){
			child.input(delta);
		}
	}
	
	public void update(float delta) {
		for(GameComponent component : components){
			component.update(transform, delta);
		}
		
		for(GameObject child : children){
			child.update(delta);
		}
	}
	
	public void render(Shader shader) {
		for(GameComponent component : components){
			component.render(transform, shader);
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
