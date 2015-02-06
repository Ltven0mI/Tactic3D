package twg.tactic.base.engine.rendering;

import static org.lwjgl.opengl.GL11.GL_BACK;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_CW;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_EQUAL;
import static org.lwjgl.opengl.GL11.GL_LESS;
import static org.lwjgl.opengl.GL11.GL_ONE;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_VERSION;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glCullFace;
import static org.lwjgl.opengl.GL11.glDepthFunc;
import static org.lwjgl.opengl.GL11.glDepthMask;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glFrontFace;
import static org.lwjgl.opengl.GL11.glGetString;
import static org.lwjgl.opengl.GL32.GL_DEPTH_CLAMP;

import java.util.ArrayList;

import twg.tactic.base.engine.components.BaseLight;
import twg.tactic.base.engine.components.Camera;
import twg.tactic.base.engine.core.GameObject;
import twg.tactic.base.engine.core.Vector3f;

public class RenderingEngine {
	
	private Camera mainCamera;
	private Vector3f ambientLight;
	
	//Permanent Structures
	private ArrayList<BaseLight> lights;
	private BaseLight activeLight;
	
	
	public Camera getMainCamera() { return mainCamera; }
	public Vector3f getAmbientLight() { return ambientLight; }
	
	public void setMainCamera(Camera mainCamera) { this.mainCamera = mainCamera; }
	
	
	public RenderingEngine() {
		lights = new ArrayList<BaseLight>();
		
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		
		glFrontFace(GL_CW);
		glCullFace(GL_BACK);
		glEnable(GL_CULL_FACE);
		glEnable(GL_DEPTH_TEST);
		
		glEnable(GL_DEPTH_CLAMP);
		
		glEnable(GL_TEXTURE_2D);
		
//		mainCamera = new Camera((float)Math.toRadians(70.0f), (float)Window.getWidth()/(float)Window.getHeight(), 0.01f, 1000.0f);
		
		ambientLight = new Vector3f(0.001f, 0.001f, 0.001f);
//		spotLight = new SpotLight(new PointLight(new BaseLight(new Vector3f(1, 1, 1), 0.4f), new Attenuation(0, 0, 1), new Vector3f(14, 0.1f, 5), 100), new Vector3f(1, 0, 0), 0.7f);
		
//		int fieldX = 5;
//		int fieldY = 5;
//		int spaceX = 5;
//		int spaceY = 5;
//		
//		pointLights = new PointLight[fieldX*fieldY];
//		
//		for(int y=0; y<fieldY; y++){
//			for(int x=0; x<fieldX; x++){
//				pointLights[x+y*fieldX] = new PointLight(new BaseLight(new Vector3f(0, 1, 0), 0.8f), new Attenuation(0, 0, 1), new Vector3f(x*spaceX, 0, y*spaceY), 50);
//			}
//		}
	}
	
	public void render(GameObject object) {
		clearScreen();
		lights.clear();
		
		object.addToRenderingEngine(this);
		
		Shader forwardAmbient = ForwardAmbient.getInstance();
		forwardAmbient.setRenderingEngine(this);
		
		object.render(forwardAmbient);
		
		glEnable(GL_BLEND);
		glBlendFunc(GL_ONE, GL_ONE);
		glDepthMask(false);
		glDepthFunc(GL_EQUAL);
		
		for(BaseLight light : lights){
			light.getShader().setRenderingEngine(this);
			
			activeLight = light;
			
			object.render(light.getShader());
		}
		
		glDepthFunc(GL_LESS);
		glDepthMask(true);
		glDisable(GL_BLEND);
	}
	
	private static void clearScreen() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}
	
	private static void setClearColor(Vector3f color) {
		glClearColor(color.getX(), color.getY(), color.getZ(), 1.0f);
	}
	
	private static void setTextures(boolean enabled) {
		if(enabled){
			glEnable(GL_TEXTURE_2D);
		}else{
			glDisable(GL_TEXTURE_2D);
		}
	}
	
	private static void unbindTextures() {
		glBindTexture(GL_TEXTURE_2D, 0);
	}
	
	public static String getOpenGLVersion() {
		return glGetString(GL_VERSION);
	}
	
	public void addLight(BaseLight light) {
		lights.add(light);
	}
	
	public void addCamera(Camera camera) {
		mainCamera = camera;
	}
	
	public BaseLight getActiveLight() {
		return activeLight;
	}
	
}
