package twg.tactic.base.engine;

public class Time {
	public static final long m_second = (long)1000000000;
	
	private static double m_delta;
	
	public static long GetTime() {
		return System.nanoTime();
	}
	
	public static double GetDelta() {
		return m_delta;
	}
	
	public static void SetDelta(double delta) {
		m_delta = delta;
	}
	
}
