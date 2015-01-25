package twg.tactic.base.engine.game;

import twg.tactic.base.engine.core.CoreEngine;

public class Main {
	
	public static void main(String[] args) {
		CoreEngine engine = new CoreEngine(1000, 800, 60, new TestGame());
		engine.createWindow("Tactic3D");
		engine.start();
	}
	
}
