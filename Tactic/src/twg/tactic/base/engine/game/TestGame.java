package twg.tactic.base.engine.game;

import twg.tactic.base.engine.components.Camera;
import twg.tactic.base.engine.components.DirectionalLight;
import twg.tactic.base.engine.components.MeshRenderer;
import twg.tactic.base.engine.components.PointLight;
import twg.tactic.base.engine.components.SpotLight;
import twg.tactic.base.engine.core.Game;
import twg.tactic.base.engine.core.GameObject;
import twg.tactic.base.engine.core.Quaternion;
import twg.tactic.base.engine.core.Vector2f;
import twg.tactic.base.engine.core.Vector3f;
import twg.tactic.base.engine.rendering.Attenuation;
import twg.tactic.base.engine.rendering.Material;
import twg.tactic.base.engine.rendering.Mesh;
import twg.tactic.base.engine.rendering.Texture;
import twg.tactic.base.engine.rendering.Vertex;
import twg.tactic.base.engine.rendering.Window;

public class TestGame extends Game{
	
	public void init() {
		float fieldDepth = 10.0f;
		float fieldWidth = 10.0f;
		
		Vertex[] vertices = new Vertex[] { 	new Vertex( new Vector3f(-fieldWidth, 0.0f, -fieldDepth), new Vector2f(0.0f, 0.0f)),
											new Vertex( new Vector3f(-fieldWidth, 0.0f, fieldDepth * 3), new Vector2f(0.0f, 1.0f)),
											new Vertex( new Vector3f(fieldWidth * 3, 0.0f, -fieldDepth), new Vector2f(1.0f, 0.0f)),
											new Vertex( new Vector3f(fieldWidth * 3, 0.0f, fieldDepth * 3), new Vector2f(1.0f, 1.0f))};
		
		int indices[] = { 0, 1, 2, 2, 1, 3};
		
		fieldDepth = 1.0f;
		fieldWidth = 1.0f;
		
		Vertex[] vertices2 = new Vertex[] { 	new Vertex( new Vector3f(-fieldWidth, 0.0f, -fieldDepth), new Vector2f(0.0f, 0.0f)),
											new Vertex( new Vector3f(-fieldWidth, 0.0f, fieldDepth * 3), new Vector2f(0.0f, 1.0f)),
											new Vertex( new Vector3f(fieldWidth * 3, 0.0f, -fieldDepth), new Vector2f(1.0f, 0.0f)),
											new Vertex( new Vector3f(fieldWidth * 3, 0.0f, fieldDepth * 3), new Vector2f(1.0f, 1.0f))};
		
		int indices2[] = { 0, 1, 2, 2, 1, 3};

		Mesh mesh = new Mesh(vertices, indices, true);
		Mesh mesh2 = new Mesh(vertices2, indices2, true);
		Material material = new Material();//(new Texture("tiles.png"), new Vector3f(1, 1, 1), 1, 8);
		//material.addTexture("diffuse", new Texture("tiles.png"));
		material.addFloat("specularIntensity", 1.0f);
		material.addFloat("specularPower", 8.0f);
		
		MeshRenderer meshRenderer = new MeshRenderer(mesh, material);
		
		GameObject planeObject = new GameObject();
		planeObject.addComponent(meshRenderer);
		planeObject.getTransform().setPos(0, -1, 5);
		
		GameObject directionalLightObject = new GameObject();
		directionalLightObject.addComponent(new DirectionalLight(new Vector3f(1, 1, 1), 0.4f));
		directionalLightObject.getTransform().setPos(10, 1, 10);
		directionalLightObject.getTransform().setRot(new Quaternion(new Vector3f(1, 0, 0), (float)Math.toRadians(-45)));
		
		GameObject pointLightObject = new GameObject();
		pointLightObject.addComponent(new PointLight(new Vector3f(0, 1, 0), 0.8f,  new Vector3f(0, 0, 1)));
		pointLightObject.getTransform().setPos(10, 1, 10);
		
		int fieldX = 5;
		int fieldY = 5;
		int spaceX = 5;
		int spaceY = 5;
		
		GameObject[] gameObjects = new GameObject[fieldX*fieldY];
		
		for(int y=0; y<fieldY; y++){
			for(int x=0; x<fieldX; x++){
				gameObjects[x+y*fieldX] = new GameObject();
				gameObjects[x+y*fieldX].getTransform().getPos().set(x*spaceX, -0.5f, y*spaceY);
				gameObjects[x+y*fieldX].getTransform().setRot(new Quaternion(new Vector3f(0, 1, 0), (float)Math.toRadians(90.0f)));
				gameObjects[x+y*fieldX].addComponent(new SpotLight(new Vector3f((float)(Math.random()*0.1f+0.9f), (float)(Math.random()*0.1f+0.7f), 0), 0.8f, new Vector3f(0, 0, 0.8f), 0.7f/*, new Vector3f(x*spaceX, 0, y*spaceY)*/));
//				gameObjects[x+y*fieldX].addComponent(new SpotLight(new Vector3f((float)(Math.random()*0.1f+0.9f), (float)(Math.random()*0.1f+0.7f), 0), 0.8f, new Vector3f(0, 0, 1), new Vector3f(x*spaceX, 0, y*spaceY), 100, new Vector3f(1, 0, 1), 0.3f));
			}
		}
		
		GameObject camera = new GameObject().addComponent(new Camera((float)Math.toRadians(70.0f), (float)Window.getWidth()/(float)Window.getHeight(), 0.01f, 1000.0f));
		
		addObject(planeObject);
		addObject(directionalLightObject);
		//getRootObject().addChild(pointLightObject);
		
		GameObject testMesh1 = new GameObject().addComponent(new MeshRenderer(mesh2, material));
		GameObject testMesh2 = new GameObject().addComponent(new MeshRenderer(mesh2, material));

		testMesh1.getTransform().getPos().set(0, 2, 0);
		testMesh1.getTransform().setRot(new Quaternion(new Vector3f(0,1,0), 0.4f));

		testMesh2.getTransform().getPos().set(0, 0, 5);

		testMesh1.addChild(testMesh2);
		addObject(testMesh1);
		testMesh2.addChild(camera);
		
//		for(int y=0; y<fieldY; y++){
//			for(int x=0; x<fieldX; x++){
//				getRootObject().addChild(gameObjects[x+y*fieldX]);
//			}
//		}
		
//		getRootObject().addChild(camera);
	}
}
