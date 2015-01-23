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
		
		PhongShader.setAmbientLight(new Vector3f(0.1f, 0.1f, 0.1f));
		
		Vertex[] vertices = new Vertex[] {new Vertex(new Vector3f(-1,-1,0), new Vector2f(0, 0)),
		new Vertex(new Vector3f(0,1,0), new Vector2f(0.5f, 0)),
		new Vertex(new Vector3f(1,-1,0), new Vector2f(1.0f, 0)),
		new Vertex(new Vector3f(0,-1,1), new Vector2f(0, 0.5f))};
		int[] indices = new int[] {3,1,0,2,1,3,0,1,2,0,2,3};

		mesh.addVertices(vertices, indices);
		
		Transform.setProjection(70, Main.width, Main.height, 0.1f, 1000);
		Transform.setCamera(camera);
		transform = new Transform();
		
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
		//transform.SetScale(0.5f, 0.5f, 0.5f);
	}
	
	public void render() {
		RenderUtil.setClearColor(Transform.getCamera().getPos().div(2048f).abs());
		shader.bindShader();
		shader.updateUniforms(transform.getTransformation(), transform.getProjectedTransformation(), material);
		mesh.draw();
	}
	
}
