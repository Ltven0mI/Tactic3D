package twg.tactic.base.engine;

public class Game {
	
	private Mesh mesh;
	//private Shader shader;
	private Transform transform;
	private Camera camera;
	private Material material;
	private PhongShader shader;
	
	public Game() {
		mesh = new Mesh();//ResourceLoader.loadMesh("tri.obj");
		camera = new Camera();
		material = new Material(ResourceLoader.loadTexture("test.png"), new Vector3f(1, 1, 1));
		shader = new PhongShader();
		
		Vertex[] vertices = new Vertex[] { new Vertex( new Vector3f(-1.0f, -1.0f, 0.5773f),	new Vector2f(0.0f, 0.0f)),
							new Vertex( new Vector3f(0.0f, -1.0f, -1.15475f), new Vector2f(0.5f, 0.0f)),
							new Vertex( new Vector3f(1.0f, -1.0f, 0.5773f),	new Vector2f(1.0f, 0.0f)),
							new Vertex( new Vector3f(0.0f, 1.0f, 0.0f), new Vector2f(0.5f, 1.0f)) };

				int indices[] = {0, 3, 1, 1, 3, 2, 2, 3, 0, 1, 2, 0};

		mesh.addVertices(vertices, indices, true);
		
		Transform.setProjection(70, Main.width, Main.height, 0.1f, 1000);
		Transform.setCamera(camera);
		transform = new Transform();
		
		PhongShader.setAmbientLight(new Vector3f(0.1f, 0.1f, 0.1f));
		PhongShader.setDirectionalLight(new DirectionalLight(new BaseLight(new Vector3f(1, 1, 1), 0.8f), new Vector3f(1, 1, 1)));
		
	}
	
	public void input() {
		camera.input();
	}
	
	float temp = 0.0f;
	
	public void update() {
		temp += Time.getDelta();
		float sinTemp = (float)Math.sin(temp);
		
		transform.setTranslation(0, 0, 5);
		transform.setRotation(0, sinTemp*180, 0);
		//transform.setScale(0.5f, sinTemp, 0.5f);
		
		//Transform.setProjection(Math.abs(sinTemp)*80 + 10, Main.width, Main.height, 0.1f, 1000);
	}
	
	public void render() {
		RenderUtil.setClearColor(Transform.getCamera().getPos().div(2048f).abs());
		shader.bindShader();
		shader.updateUniforms(transform.getTransformation(), transform.getProjectedTransformation(), material);
		mesh.draw();
	}
	
}
