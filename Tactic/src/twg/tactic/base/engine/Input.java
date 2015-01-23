package twg.tactic.base.engine;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class Input {
	
	public static final int m_numKeyCodes = 256;
	public static final int m_numMouseButtons = 5;
	
	private static ArrayList<Integer> m_currentKeys = new ArrayList<Integer>();
	private static ArrayList<Integer> m_downKeys = new ArrayList<Integer>();
	private static ArrayList<Integer> m_upKeys = new ArrayList<Integer>();
	
	private static ArrayList<Integer> m_currentMouse = new ArrayList<Integer>();
	private static ArrayList<Integer> m_downMouse = new ArrayList<Integer>();
	private static ArrayList<Integer> m_upMouse = new ArrayList<Integer>();
	
	public static void Update() {
		//Keyboard
		m_upKeys.clear();
		for(int i=0; i<m_numKeyCodes; i++){
			if(!GetKey(i) && m_currentKeys.contains(i)){
				m_upKeys.add(i);
			}
		}
		
		m_downKeys.clear();
		for(int i=0; i<m_numKeyCodes; i++){
			if(GetKey(i) && !m_currentKeys.contains(i)){
				m_downKeys.add(i);
			}
		}
		
		m_currentKeys.clear();
		for(int i=0; i<m_numKeyCodes; i++){
			if(GetKey(i)){
				m_currentKeys.add(i);
			}
		}
		
		//Mouse
		m_upMouse.clear();
		for(int i=0; i<m_numMouseButtons; i++){
			if(!GetMouse(i) && m_currentMouse.contains(i)){
				m_upMouse.add(i);
			}
		}
		
		m_downMouse.clear();
		for(int i=0; i<m_numMouseButtons; i++){
			if(GetMouse(i) && !m_currentMouse.contains(i)){
				m_downMouse.add(i);
			}
		}
		
		m_currentMouse.clear();
		for(int i=0; i<m_numMouseButtons; i++){
			if(GetMouse(i)){
				m_currentMouse.add(i);
			}
		}
	}
	
	public static boolean GetKey(int key) {
		return Keyboard.isKeyDown(key);
	}
	
	public static boolean GetKeyDown(int key) {
		return m_downKeys.contains(key);
	}
	
	public static boolean GetKeyUp(int key) {
		return m_upKeys.contains(key);
	}
	
	public static boolean GetMouse(int mb) {
		return Mouse.isButtonDown(mb);
	}
	
	public static boolean GetMouseDown(int mb) {
		return m_downMouse.contains(mb);
	}
	
	public static boolean GetMouseUp(int mb) {
		return m_upMouse.contains(mb);
	}
	
	public static Vector2f GetMousePosition() {
		return new Vector2f(Mouse.getX(), Mouse.getY());
	}
	
}