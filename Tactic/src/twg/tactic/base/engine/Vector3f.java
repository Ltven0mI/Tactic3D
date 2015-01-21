package twg.tactic.base.engine;

public class Vector3f {
	
	private float m_x;
	private float m_y;
	private float m_z;
	
	public float GetX() { return m_x; }
	public float GetY() { return m_y; }
	public float GetZ() { return m_z; }
	
	public void SetX(float x) { m_x = x; }
	public void SetY(float y) { m_y = y; }
	public void SetZ(float y) { m_z = y; }
	
	public Vector3f(float x, float y, float z) {
		m_x = x;
		m_y = y;
		m_z = z;
	}
	
	public float Length() {
		return (float)Math.sqrt(m_x*m_x+m_y*m_y+m_z*m_z);
	}
	
	public float Dot(Vector3f r) {
		return m_x*r.GetX()+m_y*r.GetY()+m_z*r.GetZ();
	}
	
	public Vector3f Normalise() {
		float length = Length();
		m_x/=length;
		m_y/=length;
		m_z/=length;
		return this;
	}
	
	public Vector3f Rotate(float angle) {
		return null;
	}
	
	public Vector3f Cross(Vector3f r) {
		float x = m_y * r.GetZ() - m_z * r.GetY();
		float y = m_z * r.GetX() - m_x * r.GetZ();
		float z = m_x * r.GetY() - m_y * r.GetX();
		return new Vector3f(x, y, z);
	}
	
	public Vector3f Add(Vector3f r) {
		return new Vector3f(m_x+r.GetX(), m_y+r.GetY(), m_z+r.GetZ());
	}
	
	public Vector3f Add(float r) {
		return new Vector3f(m_x+r, m_y+r, m_z+r);
	}
	
	public Vector3f Sub(Vector3f r) {
		return new Vector3f(m_x-r.GetX(), m_y-r.GetY(), m_z-r.GetZ());
	}
	
	public Vector3f Sub(float r) {
		return new Vector3f(m_x-r, m_y-r, m_z-r);
	}
	
	public Vector3f Mul(Vector3f r) {
		return new Vector3f(m_x*r.GetX(), m_y*r.GetY(), m_z*r.GetZ());
	}
	
	public Vector3f Mul(float r) {
		return new Vector3f(m_x*r, m_y*r, m_z*r);
	}
	
	public Vector3f Div(Vector3f r) {
		return new Vector3f(m_x/r.GetX(), m_y/r.GetY(), m_z/r.GetZ());
	}
	
	public Vector3f Div(float r) {
		return new Vector3f(m_x/r, m_y/r, m_z/r);
	}
	
	public String ToString() {
		return "("+m_x+", "+m_y+", "+m_z+")";
	}
	
}
