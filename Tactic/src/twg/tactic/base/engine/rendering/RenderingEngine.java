package twg.tactic.base.engine.rendering;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL32.*;
import twg.tactic.base.engine.core.GameObject;
import twg.tactic.base.engine.core.Vector3f;

public class RenderingEngine {
	
	private Camera mainCamera;
	private Vector3f ambientLight;
	private DirectionalLight directionalLight;
	private DirectionalLight directionalLight2;
	private PointLight pointLight;
	private PointLight[] pointLights;
	private SpotLight spotLight;
	
	
	public Camera getMainCamera() { return mainCamera; }
	public Vector3f getAmbientLight() { return ambientLight; }
	public DirectionalLight getDirectionalLight() { return directionalLight; }
	public DirectionalLight getDirectionalLight2() { return directionalLight2; }
	public PointLight getPointLight() { return pointLight; }
	public SpotLight getSpotLight() { return spotLight; }
	
	public void setMainCamera(Camera mainCamera) { this.mainCamera = mainCamera; }
	
	
	public RenderingEngine() {
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		
		glFrontFace(GL_CW);
		glCullFace(GL_BACK);
		glEnable(GL_CULL_FACE);
		glEnable(GL_DEPTH_TEST);
		
		glEnable(GL_DEPTH_CLAMP);
		
		glEnable(GL_TEXTURE_2D);
		
		mainCamera = new Camera((float)Math.toRadians(70.0f), (float)Window.getWidth()/(float)Window.getHeight(), 0.01f, 1000.0f);
		
		ambientLight = new Vector3f(0.1f, 0.1f, 0.1f);
		directionalLight = new DirectionalLight(new BaseLight(new Vector3f(0, 0, 1), 0.4f), new Vector3f(1, 1, 1));
		directionalLight2 = new DirectionalLight(new BaseLight(new Vector3f(1, 0, 0), 0.4f), new Vector3f(-1, 1, -1));
		spotLight = new SpotLight(new PointLight(new BaseLight(new Vector3f(1, 1, 1), 0.4f), new Attenuation(0, 0, 1), new Vector3f(14, 0.1f, 5), 100), new Vector3f(1, 0, 0), 0.7f);
		
		int fieldX = 5;
		int fieldY = 5;
		int spaceX = 5;
		int spaceY = 5;
		
		pointLights = new PointLight[fieldX*fieldY];
		
		for(int y=0; y<fieldY; y++){
			for(int x=0; x<fieldX; x++){
				pointLights[x+y*fieldX] = new PointLight(new BaseLight(new Vector3f(0, 1, 0), 0.8f), new Attenuation(0, 0, 1), new Vector3f(x*spaceX, 0, y*spaceY), 50);
			}
		}
	}
	
	public void input(float delta) {
		mainCamera.input(delta);
	}
	
	public void render(GameObject object) {
		clearScreen();
		
		Shader forwardAmbient = ForwardAmbient.getInstance();
		Shader forwardDirectional = ForwardDirectional.getInstance();
		Shader forwardPointLight = ForwardPoint.getInstance();
		Shader forwardSpot = ForwardSpot.getInstance();
		forwardAmbient.setRenderingEngine(this);
		forwardDirectional.setRenderingEngine(this);
		forwardPointLight.setRenderingEngine(this);
		forwardSpot.setRenderingEngine(this);
		
		object.render(forwardAmbient);
		
		glEnable(GL_BLEND);
		glBlendFunc(GL_ONE, GL_ONE);
		glDepthMask(false);
		glDepthFunc(GL_EQUAL);
		
		//object.render(forwardDirectional);
		
		DirectionalLight temp = directionalLight;
		directionalLight = directionalLight2;
		directionalLight2 = temp;
		
		//object.render(forwardDirectional);
		
		temp = directionalLight;
		directionalLight = directionalLight2;
		directionalLight2 = temp;
		
		for(int i=0; i<pointLights.length; i++){
			pointLight = pointLights[i];
			object.render(forwardPointLight);
		}
		
		//object.render(forwardSpot);
		
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
	
}
