package twg.tactic.base.engine;

public class Time {
	public static final long second = (long)1000000000;
	
	private static double delta;
	
	public static long GetTime() {
		return System.nanoTime();
	}
	
	public static double GetDelta() {
		return delta;
	}
	
	public static void SetDelta(double delta) {
		Time.delta = delta;
	}
	
}
