package twg.tactic.base.engine;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class Input {
	
	public static final int numKeyCodes = 256;
	public static final int numMouseButtons = 5;
	
	private static ArrayList<Integer> currentKeys = new ArrayList<Integer>();
	private static ArrayList<Integer> downKeys = new ArrayList<Integer>();
	private static ArrayList<Integer> upKeys = new ArrayList<Integer>();
	
	private static ArrayList<Integer> currentMouse = new ArrayList<Integer>();
	private static ArrayList<Integer> downMouse = new ArrayList<Integer>();
	private static ArrayList<Integer> upMouse = new ArrayList<Integer>();
	
	public static void Update() {
		//Keyboard
		upKeys.clear();
		for(int i=0; i<numKeyCodes; i++){
			if(!GetKey(i) && currentKeys.contains(i)){
				upKeys.add(i);
			}
		}
		
		downKeys.clear();
		for(int i=0; i<numKeyCodes; i++){
			if(GetKey(i) && !currentKeys.contains(i)){
				downKeys.add(i);
			}
		}
		
		currentKeys.clear();
		for(int i=0; i<numKeyCodes; i++){
			if(GetKey(i)){
				currentKeys.add(i);
			}
		}
		
		//Mouse
		upMouse.clear();
		for(int i=0; i<numMouseButtons; i++){
			if(!GetMouse(i) && currentMouse.contains(i)){
				upMouse.add(i);
			}
		}
		
		downMouse.clear();
		for(int i=0; i<numMouseButtons; i++){
			if(GetMouse(i) && !currentMouse.contains(i)){
				downMouse.add(i);
			}
		}
		
		currentMouse.clear();
		for(int i=0; i<numMouseButtons; i++){
			if(GetMouse(i)){
				currentMouse.add(i);
			}
		}
	}
	
	public static boolean GetKey(int key) {
		return Keyboard.isKeyDown(key);
	}
	
	public static boolean GetKeyDown(int key) {
		return downKeys.contains(key);
	}
	
	public static boolean GetKeyUp(int key) {
		return upKeys.contains(key);
	}
	
	public static boolean GetMouse(int mb) {
		return Mouse.isButtonDown(mb);
	}
	
	public static boolean GetMouseDown(int mb) {
		return downMouse.contains(mb);
	}
	
	public static boolean GetMouseUp(int mb) {
		return upMouse.contains(mb);
	}
	
	public static Vector2f GetMousePosition() {
		return new Vector2f(Mouse.getX(), Mouse.getY());
	}
	
}
