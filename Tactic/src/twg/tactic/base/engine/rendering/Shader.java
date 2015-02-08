package twg.tactic.base.engine.rendering;

import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VALIDATE_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glBindAttribLocation;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glGetProgram;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetShader;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUniform1f;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL20.glUniform3f;
import static org.lwjgl.opengl.GL20.glUniformMatrix4;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glValidateProgram;
import static org.lwjgl.opengl.GL32.GL_GEOMETRY_SHADER;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

import twg.tactic.base.engine.components.BaseLight;
import twg.tactic.base.engine.components.DirectionalLight;
import twg.tactic.base.engine.components.PointLight;
import twg.tactic.base.engine.components.SpotLight;
import twg.tactic.base.engine.core.FileInfo;
import twg.tactic.base.engine.core.Matrix4f;
import twg.tactic.base.engine.core.Transform;
import twg.tactic.base.engine.core.Util;
import twg.tactic.base.engine.core.Vector3f;
import twg.tactic.base.engine.rendering.resourceManagement.ShaderResource;
import twg.tactic.base.engine.rendering.resourceManagement.TextureResource;

public class Shader {
	
	private static HashMap<String, ShaderResource> loadedShaders = new HashMap<String, ShaderResource>();
	
	private ShaderResource resource;
	private String filename;
	
	public Shader(String filename) {
		this.filename = filename;
		
		ShaderResource oldResource = loadedShaders.get(filename);
		if(oldResource != null){
			resource = oldResource;
			resource.addReferance();
		}else{
			resource = new ShaderResource();
			
			String vertexShaderText = loadShader(filename+".vs");
			String fragmentShaderText = loadShader(filename+".fs");
			
			addVertexShader(vertexShaderText);
			addFragmentShader(fragmentShaderText);
			
			addAllAttributes(vertexShaderText);
			
			compileShader();
			
			addAllUniforms(vertexShaderText);
			addAllUniforms(fragmentShaderText);
		}
	}
	
	public void bind() {
		glUseProgram(resource.getProgram());
	}
	
	public void addAllAttributes(String shaderText) {
		final String ATTRIBUTE_KEYWORD = "attribute";
		int attributeStartLocation = shaderText.indexOf(ATTRIBUTE_KEYWORD);
		int attribNumber = 0;
		while(attributeStartLocation != -1){
			int begin = attributeStartLocation + ATTRIBUTE_KEYWORD.length() + 1;
			int end = shaderText.indexOf(";", begin);

			String attributeLine = shaderText.substring(begin, end);
			String attributeName = attributeLine.substring(attributeLine.indexOf(' ') + 1, attributeLine.length());

			setAttribLocation(attributeName, attribNumber);
			attribNumber++;

			attributeStartLocation = shaderText.indexOf(ATTRIBUTE_KEYWORD, attributeStartLocation + ATTRIBUTE_KEYWORD.length());
		}
	}

	private class GLSLStruct {
		public String name;
		public String type;
	}

	public HashMap<String, ArrayList<GLSLStruct>> findUniformStructs(String shaderText) {
		HashMap<String, ArrayList<GLSLStruct>> result = new HashMap<String, ArrayList<GLSLStruct>>();

		final String STRUCT_KEYWORD = "struct";
		int structStartLocation = shaderText.indexOf(STRUCT_KEYWORD);
		while(structStartLocation != -1){
			int nameBegin = structStartLocation + STRUCT_KEYWORD.length() + 1;
			int braceBegin = shaderText.indexOf("{", nameBegin);
			int braceEnd = shaderText.indexOf("}", braceBegin);

			String structName = shaderText.substring(nameBegin, braceBegin).trim();
			ArrayList<GLSLStruct> glslStructs = new ArrayList<GLSLStruct>();

			int componentSemicolonPos = shaderText.indexOf(";", braceBegin);
			while(componentSemicolonPos != -1 && componentSemicolonPos < braceEnd){
				int componentNameStart = componentSemicolonPos;

				while(!Character.isWhitespace(shaderText.charAt(componentNameStart - 1))) componentNameStart--;

				int componentTypeEnd = componentNameStart - 1;
				int componentTypeStart = componentTypeEnd;

				while(!Character.isWhitespace(shaderText.charAt(componentTypeStart - 1))) componentTypeStart--;

				String componentName = shaderText.substring(componentNameStart, componentSemicolonPos);
				String componentType = shaderText.substring(componentTypeStart, componentTypeEnd);

				GLSLStruct glslStruct = new GLSLStruct();
				glslStruct.name = componentName;
				glslStruct.type = componentType;

				glslStructs.add(glslStruct);

				componentSemicolonPos = shaderText.indexOf(";", componentSemicolonPos + 1);
			}

			result.put(structName, glslStructs);

			structStartLocation = shaderText.indexOf(STRUCT_KEYWORD, structStartLocation + STRUCT_KEYWORD.length());
		}

		return result;
	}

	public void addAllUniforms(String shaderText) {
		HashMap<String, ArrayList<GLSLStruct>> structs = findUniformStructs(shaderText);

		final String UNIFORM_KEYWORD = "uniform";
		int uniformStartLocation = shaderText.indexOf(UNIFORM_KEYWORD);
		while(uniformStartLocation != -1){
			int begin = uniformStartLocation + UNIFORM_KEYWORD.length() + 1;
			int end = shaderText.indexOf(";", begin);

			String uniformLine = shaderText.substring(begin, end);

			int whiteSpacePos = uniformLine.indexOf(' ');
			String uniformName = uniformLine.substring(whiteSpacePos + 1, uniformLine.length());
			String uniformType = uniformLine.substring(0, whiteSpacePos);

			resource.getUniformNames().add(uniformName);
			resource.getUniformTypes().add(uniformType);
			addUniform(uniformName, uniformType, structs);

			uniformStartLocation = shaderText.indexOf(UNIFORM_KEYWORD, uniformStartLocation + UNIFORM_KEYWORD.length());
		}
	}

	public void addUniform(String uniformName, String uniformType, HashMap<String, ArrayList<GLSLStruct>> structs) {
		boolean addThis = true;
		ArrayList<GLSLStruct> structComponents = structs.get(uniformType);

		if(structComponents != null){
			addThis = false;
			for(GLSLStruct struct : structComponents){
				addUniform(uniformName + "." + struct.name, struct.type, structs);
			}
		}

		if(!addThis) return;
		
		int uniformLocation = glGetUniformLocation(resource.getProgram(), uniformName);
		if(uniformLocation == 0xFFFFFFFF){
			System.err.println("Error: Cannot find uniform: " + uniformName);
			new Exception().printStackTrace();
			System.exit(1);
		}
		
		resource.getUniforms().put(uniformName, uniformLocation);
	}
	
	public void updateUniforms(Transform transform, Material material, RenderingEngine renderingEngine) {
		Matrix4f worldMatrix = transform.getTransformation();
		Matrix4f MVPMatrix = renderingEngine.getMainCamera().getViewProjection().mul(worldMatrix);
		
		for(int i=0; i<resource.getUniformNames().size(); i++){
			String uniformName = resource.getUniformNames().get(i);
			String uniformType = resource.getUniformTypes().get(i);
			
			if(uniformType.equals("sampler2D")){
				int samplerSlot = renderingEngine.getSamplerSlot(uniformName);
				material.getTexture(uniformName).bind(samplerSlot);
				setUniformi(uniformName, samplerSlot);
			}else if(uniformName.startsWith("T_")){
				if(uniformName.equals("T_MVP")){
					setUniform(uniformName, MVPMatrix);
				}else if(uniformName.equals("T_model")){
					setUniform(uniformName, worldMatrix);
				}else{
					throw new IllegalArgumentException(uniformName + " is not a valid component of Transform");
				}
			}else if(uniformName.startsWith("R_")){
				String unprefixedUniformName = uniformName.substring(2);
				if(uniformType.equals("vec3")){
					setUniform(uniformName, renderingEngine.getVector3f(unprefixedUniformName));
				}else if(uniformType.equals("float")){
					setUniformf(uniformName, renderingEngine.getFloat(unprefixedUniformName));
				}else if(uniformType.equals("DirectionalLight")){
					setUniformDirectionalLight(uniformName, (DirectionalLight)renderingEngine.getActiveLight());
				}else if(uniformType.equals("PointLight")){
					setUniformPointLight(uniformName, (PointLight)renderingEngine.getActiveLight());
				}else if(uniformType.equals("SpotLight")){
					setUniformSpotLight(uniformName, (SpotLight)renderingEngine.getActiveLight());
				}else{
					renderingEngine.updateUniformStruct(transform, material, this, uniformName, uniformType);
				}
			}else if(uniformName.startsWith("C_")){
				if(uniformName.equals("C_eyePos")){
					setUniform(uniformName, renderingEngine.getMainCamera().getTransform().getTransformedPos());
				}else{
					throw new IllegalArgumentException(uniformName + " is not a valid component of Camera");
				}
			}else{
				if(uniformType.equals("vec3")){
					setUniform(uniformName, material.getVector3f(uniformName));
				}else if(uniformType.equals("float")){
					setUniformf(uniformName, material.getFloat(uniformName));
				}else{
					throw new IllegalArgumentException(uniformType + " is not a supported type in Material");
				}
			}
		}
	}
	
	public void addVertexShaderFromFile(String fileName) {
		addProgram(loadShader(fileName), GL_VERTEX_SHADER);
	}
	
	public void addGeometryShaderFromFile(String fileName) {
		addProgram(loadShader(fileName), GL_GEOMETRY_SHADER);
	}

	public void addFragmentShaderFromFile(String fileName) {
		addProgram(loadShader(fileName), GL_FRAGMENT_SHADER);
	}
	
	public void addVertexShader(String shaderText) {
		addProgram(shaderText, GL_VERTEX_SHADER);
	}
	
	public void addGeometryShader(String shaderText) {
		addProgram(shaderText, GL_GEOMETRY_SHADER);
	}

	public void addFragmentShader(String shaderText) {
		addProgram(shaderText, GL_FRAGMENT_SHADER);
	}
	
	public void setAttribLocation(String attribName, int location) {
		glBindAttribLocation(resource.getProgram(), location, attribName);
	}
	
	@SuppressWarnings("deprecation")
	public void compileShader() {
		glLinkProgram(resource.getProgram());
		
		if(glGetProgram(resource.getProgram(), GL_LINK_STATUS) == 0){
			System.err.println(glGetProgramInfoLog(resource.getProgram(), 1024));
			System.exit(1);
		}
		
		glValidateProgram(resource.getProgram());
		
		if(glGetProgram(resource.getProgram(), GL_VALIDATE_STATUS) == 0){
			System.err.println(glGetProgramInfoLog(resource.getProgram(), 1024));
			System.exit(1);
		}
	}
	
	@SuppressWarnings("deprecation")
	public void addProgram(String text, int type) {
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
		glAttachShader(resource.getProgram(), shader);
	}
	
	public static String loadShader(String fileName) {
		StringBuilder shaderSource = new StringBuilder();
		BufferedReader shaderReader = null;
		final String INCLUDE_DIRECTIVE = "#include";
		
		try{
			shaderReader = new BufferedReader(new FileReader(FileInfo.shaderFolder+fileName));
			String line;
			while((line = shaderReader.readLine()) != null){
				if(line.startsWith(INCLUDE_DIRECTIVE)){
					shaderSource.append(loadShader(line.substring(INCLUDE_DIRECTIVE.length() + 2, line.length()-1)));
				}else{
					shaderSource.append(line).append("\n");
				}
			}
			shaderReader.close();
		}catch(Exception e){
			e.printStackTrace();
			System.exit(1);
		}
		
		return shaderSource.toString();
	}
	
	public void setUniformi(String uniformName, int value) {
		glUniform1i(resource.getUniforms().get(uniformName), value);
	}
	
	public void setUniformf(String uniformName, float value) {
		glUniform1f(resource.getUniforms().get(uniformName), value);
	}
	
	public void setUniform(String uniformName, Vector3f value) {
		glUniform3f(resource.getUniforms().get(uniformName), value.getX(), value.getY(), value.getZ());
	}
	
	public void setUniform(String uniformName, Matrix4f value) {
		glUniformMatrix4(resource.getUniforms().get(uniformName), true, Util.createFlippedBuffer(value));
	}
	
	public void setUniformBaseLight(String uniformName, BaseLight light) {
		setUniform(uniformName + ".color", light.getColor());
		setUniformf(uniformName + ".intensity", light.getIntensity());
	}
	
	public void setUniformDirectionalLight(String uniformName, DirectionalLight light) {
		setUniformBaseLight(uniformName + ".base", light);
		setUniform(uniformName + ".direction", light.getDirection());
	}
	
	public void setUniformSpotLight(String uniformName, SpotLight spotLight) {
		setUniformBaseLight(uniformName + ".pointLight.base", spotLight);
		setUniformf(uniformName + ".pointLight.atten.constant", spotLight.getConstant());
		setUniformf(uniformName + ".pointLight.atten.linear", spotLight.getLinear());
		setUniformf(uniformName + ".pointLight.atten.exponent", spotLight.getExponent());
		setUniform(uniformName + ".pointLight.position", spotLight.getTransform().getTransformedPos());
		setUniformf(uniformName + ".pointLight.range", spotLight.getRange());
		setUniform(uniformName + ".direction", spotLight.getDirection());
		setUniformf(uniformName + ".cutOff", spotLight.getCutOff());
	}
	
	public void setUniformPointLight(String uniformName, PointLight pointLight) {
		setUniformBaseLight(uniformName + ".base", pointLight);
		setUniformf(uniformName + ".atten.constant", pointLight.getConstant());
		setUniformf(uniformName + ".atten.linear", pointLight.getLinear());
		setUniformf(uniformName + ".atten.exponent", pointLight.getExponent());
		setUniform(uniformName + ".position", pointLight.getTransform().getTransformedPos());
		setUniformf(uniformName + ".range", pointLight.getRange());
	}
	
}
