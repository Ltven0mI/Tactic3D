package twg.tactic.base.engine;

public class Main {
	
	public static final int width = 1000;
	public static final int height = 800;
	public static final String title = "Tactic3D";
	public static final double frameCap = 5000.0;
	
	private boolean isRunning;
	private Game game;
	
	public Main() {
		System.out.println(RenderUtil.GetOpenGLVersion());
		RenderUtil.InitGraphics();
		isRunning = false;
		game = new Game();
	}
	
	public void Start() {
		if(isRunning) return;
		Run();
	}
	
	public void Stop() {
		if(!isRunning) return;
		isRunning = false;
	}
	
	private void Run() {
		isRunning = true;
		
		int frames = 0;
		int frameCounter = 0;
		
		final double frameTime = 1.0/frameCap;
		
		long lastTime = Time.GetTime();
		double unprocessedTime = 0;
		
		while(isRunning){
			boolean render = false;
			
			long currentTime = Time.GetTime();
			long passedTime = currentTime-lastTime;
			lastTime = currentTime;
			
			unprocessedTime += passedTime / (double)Time.second;
			frameCounter += passedTime;
			
			while(unprocessedTime > frameTime){
				unprocessedTime -= frameTime;
				render = true;

				if(Window.IsCloseRequested()) Stop();
				
				//Update Engine
				Time.SetDelta(frameTime);
				
				Input.Update();
				
				game.Input();
				game.Update();
				
				if(frameCounter >= Time.second){
					System.out.println(frames);
					frameCounter = 0;
					frames = 0;
				}
			}
			if(render){
				Render();
				frames++;
			}else{
				try { Thread.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
			}
		}
		Cleanup();
	}
	
	private void Render() {
		RenderUtil.ClearScreen();
		game.Render();
		Window.Render();
	}
	
	private void Cleanup() {
		Window.Dispose();
	}
	
	public static void main(String[] args) {
		Window.CreateWindow(width, height, title);
		
		Main game = new Main();
		game.Start();
	}
	
}
