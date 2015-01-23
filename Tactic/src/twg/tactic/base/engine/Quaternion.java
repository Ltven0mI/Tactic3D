package twg.tactic.base.engine;

public class Quaternion {
	
	private float x;
	private float y;
	private float z;
	private float w;
	
	public float getX() { return x; }
	public float getY() { return y; }
	public float getZ() { return z; }
	public float getW() { return w; }
	
	public void setX(float x) { this.x = x; }
	public void setY(float y) { this.y = y; }
	public void setZ(float y) { this.z = y; }
	public void setW(float w) { this.w = w; }
	
	public Quaternion(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public float length() {
		return (float)Math.sqrt(x*x+y*y+z*z+w*w);
	}
	
	public Quaternion normalize() {
		float length = length();
		x/=length;
		y/=length;
		z/=length;
		w/=length;
		return this;
	}
	
	public Quaternion conjugate() {
		return new Quaternion(-x, -y, -z, w);
	}
	
	public Quaternion mul(Quaternion r) {
		float w_ = w*r.getW()-x*r.getX()-y*r.getY()-z*r.getZ();
		float x_ = x*r.getW()+w*r.getX()+y*r.getZ()-z*r.getY();
		float y_ = y*r.getW()+w*r.getY()+z*r.getX()-x*r.getZ();
		float z_ = z*r.getW()+w*r.getZ()+x*r.getY()-y*r.getX();
		return new Quaternion(x_, y_, z_, w_);
	}
	
	public Quaternion mul(Vector3f r) {
		float w_ = -x*r.getX()-y*r.getY()-z*r.getZ();
		float x_ = w*r.getX()+y*r.getZ()-z*r.getY();
		float y_ = w*r.getY()+z*r.getX()-x*r.getZ();
		float z_ = w*r.getZ()+x*r.getY()-y*r.getX();
		
		return new Quaternion(x_, y_, z_, w_);
	}
	
}
