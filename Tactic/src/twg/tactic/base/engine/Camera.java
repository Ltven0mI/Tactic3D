package twg.tactic.base.engine;

public class Camera {
	
	public static final Vector3f yAxis = new Vector3f(0, 1, 0);
	
	private Vector3f pos;
	private Vector3f forward;
	private Vector3f up;
	
	public Vector3f GetPos() { return pos; }
	public Vector3f GetForward() { return forward; }
	public Vector3f GetUp() { return up; }
	public Vector3f GetLeft() { return up.Cross(forward).Normalize(); }
	public Vector3f GetRight() { return forward.Cross(up).Normalize(); }
	
	public void SetPos(Vector3f pos) { this.pos = pos; }
	public void SetForward(Vector3f forward) { this.forward = forward; }
	public void SetUp(Vector3f up) { this.up = up; }
	
	public Camera() {
		this(new Vector3f(0, 0, 0), new Vector3f(0, 0, 1), new Vector3f(0, 1, 0));
	}
	
	public Camera(Vector3f pos, Vector3f forward, Vector3f up) {
		this.pos = pos;
		this.forward = forward;
		this.up = up;
		
		this.forward.Normalize();
		this.up.Normalize();
	}
	
	public void Input() {
		float moveAmt = (float)(10*Time.GetDelta());
		float rotAmt = (float)(100*Time.GetDelta());
	}
	
	public void Move(Vector3f dir, float amt) {
		pos = pos.Mul(dir.Mul(amt));
	}
	
	public void RotateY(float angle) {
		Vector3f Haxis = yAxis.Cross(forward);
		Haxis.Normalize();
		
		forward.Rotate(angle, yAxis);
		forward.Normalize();
		
		up = forward.Cross(Haxis);
		up.Normalize();
	}
	
	public void RotateX(float angle) {
		Vector3f Haxis = yAxis.Cross(forward);
		Haxis.Normalize();
		
		forward.Rotate(angle, Haxis);
		forward.Normalize();
		
		up = forward.Cross(Haxis);
		up.Normalize();
	}
	
}
