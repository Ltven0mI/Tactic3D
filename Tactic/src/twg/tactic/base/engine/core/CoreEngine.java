package twg.tactic.base.engine.core;

import twg.tactic.base.engine.rendering.RenderingEngine;
import twg.tactic.base.engine.rendering.Window;

public class CoreEngine {
	
	private boolean isRunning;
	private Game game;
	private RenderingEngine renderingEngine;
	private int width;
	private int height;
	private double frameTime;
	
	public CoreEngine(int width, int height, int frameRate, Game game) {
		isRunning = false;
		this.game = game;
		this.width = width;
		this.height = height;
		this.frameTime = 1.0/frameRate;
	}
	
	public void createWindow(String title) {
		Window.createWindow(width, height, title);
		this.renderingEngine = new RenderingEngine();
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
		
		game.init();
		
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
				
				game.input();
				renderingEngine.input();
				Input.update();
				
				game.update();
				
				if(frameCounter >= Time.second){
					System.out.println(frames);
					frameCounter = 0;
					frames = 0;
				}
			}
			if(render){
				renderingEngine.render(game.getRootObject());
				Window.render();
				frames++;
			}else{
				try { Thread.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
			}
		}
		cleanup();
	}
	
	private void cleanup() {
		Window.dispose();
	}
	
}
