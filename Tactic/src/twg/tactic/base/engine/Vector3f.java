package twg.tactic.base.engine;

public class Vector3f {
	
	private float x;
	private float y;
	private float z;
	
	public float GetX() { return x; }
	public float GetY() { return y; }
	public float GetZ() { return z; }
	
	public void SetX(float x) { this.x = x; }
	public void SetY(float y) { this.y = y; }
	public void SetZ(float y) { this.z = y; }
	
	public Vector3f(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public float Length() {
		return (float)Math.sqrt(x*x+y*y+z*z);
	}
	
	public float Dot(Vector3f r) {
		return x*r.GetX()+y*r.GetY()+z*r.GetZ();
	}
	
	public Vector3f Normalize() {
		float length = Length();
		x/=length;
		y/=length;
		z/=length;
		return this;
	}
	
	public Vector3f Rotate(float angle, Vector3f axis) {
		float sinHalfAngle = (float)Math.sin(Math.toRadians(angle/2));
		float cosHalfAngle = (float)Math.sin(Math.toRadians(angle/2));
		
		float rX = axis.GetX() * sinHalfAngle;
		float rY = axis.GetY() * sinHalfAngle;
		float rZ = axis.GetZ() * sinHalfAngle;
		float rW = cosHalfAngle;
		
		Quaternion rotation = new Quaternion(rX, rY, rZ, rW);
		Quaternion conjugate = rotation.Conjugate();
		Quaternion w = rotation.Mul(this).Mul(conjugate);
		
		x = w.GetX();
		y = w.GetY();
		z = w.GetZ();
		
		return this;
	}
	
	public Vector3f Cross(Vector3f r) {
		float x_ = y * r.GetZ() - z * r.GetY();
		float y_ = z * r.GetX() - x * r.GetZ();
		float z_ = x * r.GetY() - y * r.GetX();
		return new Vector3f(x_, y_, z_);
	}
	
	public Vector3f Add(Vector3f r) {
		return new Vector3f(x+r.GetX(), y+r.GetY(), z+r.GetZ());
	}
	
	public Vector3f Add(float r) {
		return new Vector3f(x+r, y+r, z+r);
	}
	
	public Vector3f Sub(Vector3f r) {
		return new Vector3f(x-r.GetX(), y-r.GetY(), z-r.GetZ());
	}
	
	public Vector3f Sub(float r) {
		return new Vector3f(x-r, y-r, z-r);
	}
	
	public Vector3f Mul(Vector3f r) {
		return new Vector3f(x*r.GetX(), y*r.GetY(), z*r.GetZ());
	}
	
	public Vector3f Mul(float r) {
		return new Vector3f(x*r, y*r, z*r);
	}
	
	public Vector3f Div(Vector3f r) {
		return new Vector3f(x/r.GetX(), y/r.GetY(), z/r.GetZ());
	}
	
	public Vector3f Div(float r) {
		return new Vector3f(x/r, y/r, z/r);
	}
	
	public String ToString() {
		return "("+x+", "+y+", "+z+")";
	}
	
}
