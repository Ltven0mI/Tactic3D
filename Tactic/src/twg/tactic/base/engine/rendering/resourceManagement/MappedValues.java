package twg.tactic.base.engine.rendering.resourceManagement;

import java.util.HashMap;

import twg.tactic.base.engine.core.Vector3f;

public abstract class MappedValues {
	
	private HashMap<String, Vector3f> vector3fHashMap;
	private HashMap<String, Float> floatHashMap;
	
	public void addVector3f(String name, Vector3f vector3f) { vector3fHashMap.put(name, vector3f); }
	public void addFloat(String name, Float floatVal) { floatHashMap.put(name, floatVal); }
	
	public Vector3f getVector3f(String name) {
		Vector3f result = vector3fHashMap.get(name);
		if(result != null)
			return result;
		return new Vector3f(0, 0, 0);
	}
	public float getFloat(String name) {
		Float result = floatHashMap.get(name);
		if(result != null)
			return result;
		return 0.0f;
	}
	
	public MappedValues() {
		vector3fHashMap = new HashMap<String, Vector3f>();
		floatHashMap = new HashMap<String, Float>();
	}
	
}
