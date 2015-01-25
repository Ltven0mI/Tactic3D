package twg.tactic.base.engine.game;

import twg.tactic.base.engine.components.DirectionalLight;
import twg.tactic.base.engine.components.MeshRenderer;
import twg.tactic.base.engine.components.PointLight;
import twg.tactic.base.engine.core.Game;
import twg.tactic.base.engine.core.GameObject;
import twg.tactic.base.engine.core.Vector2f;
import twg.tactic.base.engine.core.Vector3f;
import twg.tactic.base.engine.rendering.Attenuation;
import twg.tactic.base.engine.rendering.BaseLight;
import twg.tactic.base.engine.rendering.Material;
import twg.tactic.base.engine.rendering.Mesh;
import twg.tactic.base.engine.rendering.Texture;
import twg.tactic.base.engine.rendering.Vertex;

public class TestGame extends Game{
	
	public void init() {
		float fieldDepth = 10.0f;
		float fieldWidth = 10.0f;
		
		Vertex[] vertices = new Vertex[] { 	new Vertex( new Vector3f(-fieldWidth, 0.0f, -fieldDepth), new Vector2f(0.0f, 0.0f)),
											new Vertex( new Vector3f(-fieldWidth, 0.0f, fieldDepth * 3), new Vector2f(0.0f, 1.0f)),
											new Vertex( new Vector3f(fieldWidth * 3, 0.0f, -fieldDepth), new Vector2f(1.0f, 0.0f)),
											new Vertex( new Vector3f(fieldWidth * 3, 0.0f, fieldDepth * 3), new Vector2f(1.0f, 1.0f))};
		
		int indices[] = { 0, 1, 2, 2, 1, 3};

		Mesh mesh = new Mesh(vertices, indices, true);
		Material material = new Material(new Texture("test.png"), new Vector3f(1, 1, 1), 1, 8);
		MeshRenderer meshRenderer = new MeshRenderer(mesh, material);
		
		GameObject planeObject = new GameObject();
		planeObject.addComponent(meshRenderer);
		planeObject.getTransform().setPos(0, -1, 5);
		
		GameObject directionalLightObject = new GameObject();
		directionalLightObject.addComponent(new DirectionalLight(new BaseLight(new Vector3f(0, 0, 1), 0.4f), new Vector3f(1, 1, 1)));
		directionalLightObject.addComponent(new DirectionalLight(new BaseLight(new Vector3f(1, 1, 0), 0.4f), new Vector3f(-1, 1, -1)));
		directionalLightObject.getTransform().setPos(10, 1, 10);
		
		GameObject pointLightObject = new GameObject();
		pointLightObject.addComponent(new PointLight(new BaseLight(new Vector3f(0, 1, 0), 0.8f), new Attenuation(0, 0, 1), new Vector3f(10, 0, 10), 50));
		pointLightObject.getTransform().setPos(10, 1, 10);
		
		getRootObject().addChild(planeObject);
		getRootObject().addChild(directionalLightObject);
		getRootObject().addChild(pointLightObject);
	}
}
