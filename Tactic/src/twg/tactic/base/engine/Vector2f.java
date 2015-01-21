package twg.tactic.base.engine;

public class Vector2f {
	
	private float m_x;
	private float m_y;
	
	public float GetX() { return m_x; }
	public float GetY() { return m_y; }
	
	public void SetX(float x) { m_x = x; }
	public void SetY(float y) { m_y = y; }
	
	public Vector2f(float x, float y) {
		m_x = x;
		m_y = y;
	}
	
	public float Length() {
		return (float)Math.sqrt(m_x*m_x+m_y*m_y);
	}
	
	public float Dot(Vector2f r) {
		return m_x*r.GetX()+m_y*r.GetY();
	}
	
	public Vector2f Normalise() {
		float length = Length();
		m_x/=length;
		m_y/=length;
		return this;
	}
	
	public Vector2f Rotate(float angle) {
		double rad = (double)Math.toRadians(angle);
		double cos = (double)Math.cos(rad);
		double sin = (double)Math.sin(rad);
		
		return new Vector2f((float)(m_x*cos-m_y*sin), (float)(m_x*sin+m_y*cos));
	}
	
	public Vector2f Add(Vector2f r) {
		return new Vector2f(m_x+r.GetX(), m_y+r.GetY());
	}
	
	public Vector2f Add(float r) {
		return new Vector2f(m_x+r, m_y+r);
	}
	
	public Vector2f Sub(Vector2f r) {
		return new Vector2f(m_x-r.GetX(), m_y-r.GetY());
	}
	
	public Vector2f Sub(float r) {
		return new Vector2f(m_x-r, m_y-r);
	}
	
	public Vector2f Mul(Vector2f r) {
		return new Vector2f(m_x*r.GetX(), m_y*r.GetY());
	}
	
	public Vector2f Mul(float r) {
		return new Vector2f(m_x*r, m_y*r);
	}
	
	public Vector2f Div(Vector2f r) {
		return new Vector2f(m_x/r.GetX(), m_y/r.GetY());
	}
	
	public Vector2f Div(float r) {
		return new Vector2f(m_x/r, m_y/r);
	}
	
	public String ToString() {
		return "("+m_x+", "+m_y+")";
	}
	
}
