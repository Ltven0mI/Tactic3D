package twg.tactic.base.engine;

public class Camera {
	
	public static final Vector3f yAxis = new Vector3f(0, 1, 0);
	
	private Vector3f pos;
	private Vector3f forward;
	private Vector3f up;
	
	public Vector3f getPos() { return pos; }
	public Vector3f getForward() { return forward; }
	public Vector3f getUp() { return up; }
	public Vector3f getLeft() { return forward.cross(up).normailze(); }
	public Vector3f getRight() { return up.cross(forward).normailze(); }
	
	public void setPos(Vector3f pos) { this.pos = pos; }
	public void setForward(Vector3f forward) { this.forward = forward; }
	public void setUp(Vector3f up) { this.up = up; }
	
	public Camera() {
		this(new Vector3f(0, 0, 0), new Vector3f(0, 0, 1), new Vector3f(0, 1, 0));
	}
	
	public Camera(Vector3f pos, Vector3f forward, Vector3f up) {
		this.pos = pos;
		this.forward = forward;
		this.up = up;
		
		this.forward.normailze();
		this.up.normailze();
	}
	
	public void input() {
		float moveAmt = (float)(10*Time.getDelta());
		float rotAmt = (float)(100*Time.getDelta());
		
		if(Input.getKey(Input.KEY_W)){
			move(getForward(), moveAmt);
		}
		if(Input.getKey(Input.KEY_S)){
			move(getForward(), -moveAmt);
		}
		if(Input.getKey(Input.KEY_A)){
			move(getLeft(), moveAmt);
		}
		if(Input.getKey(Input.KEY_D)){
			move(getLeft(), -moveAmt);
		}
		
		if(Input.getKey(Input.KEY_UP)){
			rotateX(-rotAmt);
		}
		if(Input.getKey(Input.KEY_DOWN)){
			rotateX(rotAmt);
		}
		if(Input.getKey(Input.KEY_LEFT)){
			rotateY(-rotAmt);
		}
		if(Input.getKey(Input.KEY_RIGHT)){
			rotateY(rotAmt);
		}
	}
	
	public void move(Vector3f dir, float amt) {
		pos = pos.add(dir.mul(amt));
	}
	
	public void rotateY(float angle) {
		Vector3f Haxis = yAxis.cross(forward);
		Haxis.normailze();
		
		forward.rotate(angle, yAxis);
		forward.normailze();
		
		up = forward.cross(Haxis);
		up.normailze();
	}
	
	public void rotateX(float angle) {
		Vector3f Haxis = yAxis.cross(forward);
		Haxis.normailze();
		
		forward.rotate(angle, Haxis);
		forward.normailze();
		
		up = forward.cross(Haxis);
		up.normailze();
	}
	
}
