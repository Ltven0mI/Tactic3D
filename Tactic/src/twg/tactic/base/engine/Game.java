package twg.tactic.base.engine;

public class Game {
	
	private Mesh mesh;
	//private Shader shader;
	private Transform transform;
	private Camera camera;
	private Material material;
	private PhongShader shader;
	
	PointLight p1 = new PointLight(new BaseLight(new Vector3f(1, 0.5f, 0), 0.8f), new Attenuation(0, 0, 1), new Vector3f(-2, 0, 3f));
	PointLight p2 = new PointLight(new BaseLight(new Vector3f(0, 0.5f, 1), 0.8f), new Attenuation(0, 0, 1), new Vector3f(2, 0, 7f));
	
	public Game() {
		mesh = new Mesh();//ResourceLoader.loadMesh("tri.obj");
		camera = new Camera();
		material = new Material(ResourceLoader.loadTexture("test.png"), new Vector3f(1, 1, 1), 1, 8);
		shader = PhongShader.getInstance();
		
		/*Vertex[] vertices = new Vertex[] { new Vertex( new Vector3f(-1.0f, -1.0f, 0.5773f),	new Vector2f(0.0f, 0.0f)),
							new Vertex( new Vector3f(0.0f, -1.0f, -1.15475f), new Vector2f(0.5f, 0.0f)),
							new Vertex( new Vector3f(1.0f, -1.0f, 0.5773f),	new Vector2f(1.0f, 0.0f)),
							new Vertex( new Vector3f(0.0f, 1.0f, 0.0f), new Vector2f(0.5f, 1.0f)) };

		int indices[] = {0, 3, 1, 1, 3, 2, 2, 3, 0, 1, 2, 0};*/
				
		float fieldDepth = 10.0f;
		float fieldWidth = 10.0f;
		
		Vertex[] vertices = new Vertex[] { 	new Vertex( new Vector3f(-fieldWidth, 0.0f, -fieldDepth), new Vector2f(0.0f, 0.0f)),
											new Vertex( new Vector3f(-fieldWidth, 0.0f, fieldDepth * 3), new Vector2f(0.0f, 1.0f)),
											new Vertex( new Vector3f(fieldWidth * 3, 0.0f, -fieldDepth), new Vector2f(1.0f, 0.0f)),
											new Vertex( new Vector3f(fieldWidth * 3, 0.0f, fieldDepth * 3), new Vector2f(1.0f, 1.0f))};
		
		int indices[] = { 0, 1, 2, 2, 1, 3};

		mesh.addVertices(vertices, indices, true);
		
		Transform.setProjection(70f, Window.getWidth(), Window.getHeight(), 0.1f, 1000);
		Transform.setCamera(camera);
		transform = new Transform();
		
		PhongShader.setAmbientLight(new Vector3f(0.1f, 0.1f, 0.1f));
		//PhongShader.setDirectionalLight(new DirectionalLight(new BaseLight(new Vector3f(1, 1, 1), 0.8f), new Vector3f(1, 1, 1)));
		
		PhongShader.setPointLight(new PointLight[]{p1, p2});
	}
	
	public void input() {
		camera.input();
	}
	
	float temp = 0.0f;
	
	public void update() {
		temp += Time.getDelta();
		float sinTemp = (float)Math.sin(temp);
		
		transform.setTranslation(0, -0.5f, 5);
		//transform.setRotation(0, sinTemp*180, 0);
		//transform.setScale(0.5f, sinTemp, 0.5f);
		
		//Transform.setProjection(Math.abs(sinTemp)*80 + 10, Main.width, Main.height, 0.1f, 1000);
		
		p1.setPos(new Vector3f(3,0,8.0f * (float)(Math.sin(temp) + 1.0/2.0) + 10));
		p2.setPos(new Vector3f(7,0,8.0f * (float)(Math.cos(temp) + 1.0/2.0) + 10));
	}
	
	public void render() {
		RenderUtil.setClearColor(Transform.getCamera().getPos().div(2048f).abs());
		shader.bind();
		shader.updateUniforms(transform.getTransformation(), transform.getProjectedTransformation(), material);
		mesh.draw();
	}
	
}
