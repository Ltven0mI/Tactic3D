package twg.tactic.base.engine;

public class Game {
	
	private Mesh mesh;
	private Shader shader;
	private Transform transform;
	private Camera camera;
	
	public Game() {
		mesh = ResourceLoader.loadMesh("monkey.obj");
		shader = new Shader();
		camera = new Camera();
		
		Transform.setProjection(70, Main.width, Main.height, 0.1f, 1000);
		Transform.setCamera(camera);
		transform = new Transform();
		
		shader.addVertexShader(ResourceLoader.loadShader("basicVertex.vs"));
		shader.addFragmentShader(ResourceLoader.loadShader("basicFragment.fs"));
		shader.compileShader();
		
		shader.addUniform("transform");
	}
	
	public void input() {
		camera.input();
	}
	
	float temp = 0.0f;
	
	public void update() {
		temp += Time.getDelta();
		float sinTemp = (float)Math.sin(temp);
		
		transform.setTranslation(0, 0, 5);
		transform.setRotation(sinTemp*180, sinTemp*180, 0);
		//transform.SetScale(0.5f, 0.5f, 0.5f);
	}
	
	public void render() {
		shader.bindShader();
		shader.setUniform("transform", transform.getProjectedTransformation());
		mesh.draw();
	}
	
}
