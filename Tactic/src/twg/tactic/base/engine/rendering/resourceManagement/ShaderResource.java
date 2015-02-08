package twg.tactic.base.engine.rendering.resourceManagement;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

import java.util.ArrayList;
import java.util.HashMap;

public class ShaderResource {
	
	private int program;
	private int refCount;
	
	private HashMap<String, Integer> uniforms;
	private ArrayList<String> uniformNames;
	private ArrayList<String> uniformTypes;
	
	public int getProgram() { return program; }

	public ShaderResource() {
		this.program = glCreateProgram();
		this.refCount = 1;
		
		uniforms = new HashMap<String, Integer>();
		uniformNames = new ArrayList<String>(); 
		uniformTypes = new ArrayList<String>();
		
		if(program == 0){
			System.err.println("Shader creation failed: Could not find valid memory location in constructor");
			System.exit(1);
		}
	}
	
	@Override
	protected void finalize() {
		glDeleteBuffers(program);
	}
	
	public void addReferance() {
		refCount++;
	}
	
	public boolean removeReferance() {
		refCount--;
		return refCount == 0;
	}
	
	public HashMap<String, Integer> getUniforms() { return uniforms; }
	public ArrayList<String> getUniformNames() { return uniformNames; }
	public ArrayList<String> getUniformTypes() { return uniformTypes; }
	
}
