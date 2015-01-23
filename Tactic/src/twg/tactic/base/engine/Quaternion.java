package twg.tactic.base.engine;

public class Quaternion {
	
	private float m_x;
	private float m_y;
	private float m_z;
	private float m_w;
	
	public float GetX() { return m_x; }
	public float GetY() { return m_y; }
	public float GetZ() { return m_z; }
	public float GetW() { return m_w; }
	
	public void SetX(float x) { m_x = x; }
	public void SetY(float y) { m_y = y; }
	public void SetZ(float y) { m_z = y; }
	public void SetW(float w) { m_w = w; }
	
	public Quaternion(float x, float y, float z, float w) {
		m_x = x;
		m_y = y;
		m_z = z;
		m_w = w;
	}
	
	public float Length() {
		return (float)Math.sqrt(m_x*m_x+m_y*m_y+m_z*m_z+m_w*m_w);
	}
	
	public Quaternion Normalise() {
		float length = Length();
		m_x/=length;
		m_y/=length;
		m_z/=length;
		m_w/=length;
		return this;
	}
	
	public Quaternion Conjugate() {
		return new Quaternion(-m_x, -m_y, -m_z, m_w);
	}
	
	public Quaternion Mul(Quaternion r) {
		float w = m_w*r.GetW()-m_x*r.GetX()-m_y*r.GetY()-m_z*r.GetZ();
		float x = m_x*r.GetW()+m_w*r.GetX()+m_y*r.GetZ()-m_z*r.GetY();
		float y = m_y*r.GetW()+m_w*r.GetY()+m_z*r.GetX()-m_x*r.GetZ();
		float z = m_z*r.GetW()+m_w*r.GetZ()+m_x*r.GetY()-m_y*r.GetX();
		return new Quaternion(x, y, z, w);
	}
	
	public Quaternion Mul(Vector3f r) {
		float w = -m_x*r.GetX()-m_y*r.GetY()-m_z*r.GetZ();
		float x = m_w*r.GetX()+m_y*r.GetZ()-m_z*r.GetY();
		float y = m_w*r.GetY()+m_z*r.GetX()-m_x*r.GetZ();
		float z = m_w*r.GetZ()+m_x*r.GetY()-m_y*r.GetX();
		
		return new Quaternion(x, y, z, w);
	}
	
}