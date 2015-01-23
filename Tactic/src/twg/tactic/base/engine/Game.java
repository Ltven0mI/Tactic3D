package twg.tactic.base.engine;

import org.lwjgl.input.Keyboard;

public class Game {
	
	private Mesh mesh;
	private Shader shader;
	private Transform transform;
	private Camera camera;
	
	public Game() {
		mesh = ResourceLoader.LoadMesh("box3.obj");
		shader = new Shader();
		camera = new Camera();
		
		Transform.SetProjection(70, Main.width, Main.height, 0.1f, 1000);
		Transform.SetCamera(camera);
		transform = new Transform();
		
		shader.AddVertexShader(ResourceLoader.LoadShader("basicVertex.vs"));
		shader.AddFragmentShader(ResourceLoader.LoadShader("basicFragment.fs"));
		shader.CompileShader();
		
		shader.AddUniform("transform");
	}
	
	public void Input() {
		if(Input.GetKeyDown(Keyboard.KEY_UP)){
			System.out.println("Pressed Up");
		}
		if(Input.GetKeyUp(Keyboard.KEY_UP)){
			System.out.println("Released Up");
		}
		
		if(Input.GetMouseDown(1)){
			System.out.println("Pressed RMB At: "+Input.GetMousePosition().ToString());
		}
		if(Input.GetMouseUp(1)){
			System.out.println("Released RMB: "+Input.GetMousePosition().ToString());
		}
	}
	
	float temp = 0.0f;
	
	public void Update() {
		temp += Time.GetDelta();
		float sinTemp = (float)Math.sin(temp);
		
		transform.SetTranslation(0, 0, 5);
		transform.SetRotation(0, sinTemp*180, 0);
		//transform.SetScale(0.5f, 0.5f, 0.5f);
	}
	
	public void Render() {
		shader.BindShader();
		shader.SetUniform("transform", transform.GetProjectedTransformation());
		mesh.Draw();
	}
	
}
