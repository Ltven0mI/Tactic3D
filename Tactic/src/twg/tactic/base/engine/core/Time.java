package twg.tactic.base.engine.core;

public class Time {
	private static final long second = 1000000000;
	
	public static double getTime() {
		return (double)(System.nanoTime()/(double)second);
	}
	
}
