package twg.tactic.base.engine;

public class Time {
	public static final long second = (long)1000000000;
	
	private static double delta;
	
	public static long getTime() {
		return System.nanoTime();
	}
	
	public static double getDelta() {
		return delta;
	}
	
	public static void setDelta(double delta) {
		Time.delta = delta;
	}
	
}
