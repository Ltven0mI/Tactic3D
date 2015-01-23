package twg.tactic.base.engine;

public class Main {
	
	public static final int width = 1000;
	public static final int height = 800;
	public static final String title = "Tactic3D";
	public static final double frameCap = 5000.0;
	
	private boolean isRunning;
	private Game game;
	
	public Main() {
		System.out.println(RenderUtil.getOpenGLVersion());
		RenderUtil.initGraphics();
		isRunning = false;
		game = new Game();
	}
	
	public void start() {
		if(isRunning) return;
		run();
	}
	
	public void stop() {
		if(!isRunning) return;
		isRunning = false;
	}
	
	private void run() {
		isRunning = true;
		
		int frames = 0;
		int frameCounter = 0;
		
		final double frameTime = 1.0/frameCap;
		
		long lastTime = Time.getTime();
		double unprocessedTime = 0;
		
		while(isRunning){
			boolean render = false;
			
			long currentTime = Time.getTime();
			long passedTime = currentTime-lastTime;
			lastTime = currentTime;
			
			unprocessedTime += passedTime / (double)Time.second;
			frameCounter += passedTime;
			
			while(unprocessedTime > frameTime){
				unprocessedTime -= frameTime;
				render = true;

				if(Window.isCloseRequested()) stop();
				
				//Update Engine
				Time.setDelta(frameTime);
				
				Input.update();
				
				game.input();
				game.update();
				
				if(frameCounter >= Time.second){
					System.out.println(frames);
					frameCounter = 0;
					frames = 0;
				}
			}
			if(render){
				render();
				frames++;
			}else{
				try { Thread.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
			}
		}
		cleanup();
	}
	
	private void render() {
		RenderUtil.clearScreen();
		game.render();
		Window.render();
	}
	
	private void cleanup() {
		Window.dispose();
	}
	
	public static void main(String[] args) {
		Window.createWindow(width, height, title);
		
		Main game = new Main();
		game.start();
	}
	
}
