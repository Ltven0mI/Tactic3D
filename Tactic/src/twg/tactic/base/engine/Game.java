package twg.tactic.base.engine;

import org.lwjgl.input.Keyboard;

public class Game {
	
	public Game() {
		
	}
	
	public void Input() {
		if(Input.GetKeyDown(Keyboard.KEY_UP)){
			System.out.println("Pressed Up");
		}
		if(Input.GetKeyUp(Keyboard.KEY_UP)){
			System.out.println("Released Up");
		}
		
		if(Input.GetMouseDown(1)){
			System.out.println("Pressed RMB At: "+Input.GetMousePosition().ToString());
		}
		if(Input.GetMouseUp(1)){
			System.out.println("Released RMB: "+Input.GetMousePosition().ToString());
		}
	}
	
	public void Update() {
		
	}
	
	public void Render() {
		
	}
	
}
