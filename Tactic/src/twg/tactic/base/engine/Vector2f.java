package twg.tactic.base.engine;

public class Vector2f {
	
	private float x;
	private float y;
	
	public float GetX() { return x; }
	public float GetY() { return y; }
	
	public void SetX(float x) { this.x = x; }
	public void SetY(float y) { this.y = y; }
	
	public Vector2f(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public float Length() {
		return (float)Math.sqrt(x*x+y*y);
	}
	
	public float Dot(Vector2f r) {
		return x*r.GetX()+y*r.GetY();
	}
	
	public Vector2f Normalise() {
		float length = Length();
		x/=length;
		y/=length;
		return this;
	}
	
	public Vector2f Rotate(float angle) {
		double rad = (double)Math.toRadians(angle);
		double cos = (double)Math.cos(rad);
		double sin = (double)Math.sin(rad);
		
		return new Vector2f((float)(x*cos-y*sin), (float)(x*sin+y*cos));
	}
	
	public Vector2f Add(Vector2f r) {
		return new Vector2f(x+r.GetX(), y+r.GetY());
	}
	
	public Vector2f Add(float r) {
		return new Vector2f(x+r, y+r);
	}
	
	public Vector2f Sub(Vector2f r) {
		return new Vector2f(x-r.GetX(), y-r.GetY());
	}
	
	public Vector2f Sub(float r) {
		return new Vector2f(x-r, y-r);
	}
	
	public Vector2f Mul(Vector2f r) {
		return new Vector2f(x*r.GetX(), y*r.GetY());
	}
	
	public Vector2f Mul(float r) {
		return new Vector2f(x*r, y*r);
	}
	
	public Vector2f Div(Vector2f r) {
		return new Vector2f(x/r.GetX(), y/r.GetY());
	}
	
	public Vector2f Div(float r) {
		return new Vector2f(x/r, y/r);
	}
	
	public String ToString() {
		return "("+x+", "+y+")";
	}
	
}
