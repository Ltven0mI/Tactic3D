package twg.tactic.base.engine;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL32.*;

import java.util.HashMap;

public class Shader {
	
	private int program;
	private HashMap<String, Integer> uniforms;
	
	public Shader() {
		program = glCreateProgram();
		uniforms = new HashMap<String, Integer>();
		
		if(program == 0){
			System.err.println("Shader creation failed: Could not find valid memory location in constructor");
			System.exit(1);
		}
	}
	
	public void BindShader() {
		glUseProgram(program);
	}
	
	public void AddUniform(String uniform) {
		int uniformLocation = glGetUniformLocation(program, uniform);
		System.out.println(uniformLocation);
		if(uniformLocation == 0xFFFFFF){
			System.err.println("Error: Cannot find uniform: " + uniform);
			new Exception().printStackTrace();
			System.exit(1);
		}
		
		uniforms.put(uniform, uniformLocation);
	}
	
	public void AddVertexShader(String text) {
		AddProgram(text, GL_VERTEX_SHADER);
	}
	
	public void AddGeometryShader(String text) {
		AddProgram(text, GL_GEOMETRY_SHADER);
	}

	public void AddFragmentShader(String text) {
		AddProgram(text, GL_FRAGMENT_SHADER);
	}
	
	@SuppressWarnings("deprecation")
	public void CompileShader() {
		glLinkProgram(program);
		
		if(glGetProgram(program, GL_LINK_STATUS) == 0){
			System.err.println(glGetProgramInfoLog(program, 1024));
			System.exit(1);
		}
		
		glValidateProgram(program);
		
		if(glGetProgram(program, GL_VALIDATE_STATUS) == 0){
			System.err.println(glGetProgramInfoLog(program, 1024));
			System.exit(1);
		}
	}
	
	@SuppressWarnings("deprecation")
	private void AddProgram(String text, int type) {
		int shader = glCreateShader(type);
		
		if(shader == 0){
			System.err.println("Shader creation failed: Could not find valid memory location when adding shader");
			System.exit(1);
		}
		glShaderSource(shader, text);
		glCompileShader(shader);
		
		if(glGetShader(shader, GL_COMPILE_STATUS) == 0){
			System.err.println(glGetShaderInfoLog(shader, 1024));
			System.exit(1);
		}
		glAttachShader(program, shader);
	}
	
	public void SetUniformi(String uniformName, int value) {
		glUniform1i(uniforms.get(uniformName), value);
	}
	
	public void SetUniformf(String uniformName, float value) {
		glUniform1f(uniforms.get(uniformName), value);
	}
	
	public void SetUniform(String uniformName, Vector3f value) {
		glUniform3f(uniforms.get(uniformName), value.GetX(), value.GetY(), value.GetZ());
	}
	
	public void SetUniform(String uniformName, Matrix4f value) {
		glUniformMatrix4(uniforms.get(uniformName), true, Util.CreateFlippedBuffer(value));
	}
	
}
