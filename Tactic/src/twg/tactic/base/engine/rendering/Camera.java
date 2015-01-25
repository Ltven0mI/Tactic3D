package twg.tactic.base.engine.rendering;

import twg.tactic.base.engine.core.Input;
import twg.tactic.base.engine.core.Matrix4f;
import twg.tactic.base.engine.core.Time;
import twg.tactic.base.engine.core.Vector2f;
import twg.tactic.base.engine.core.Vector3f;

public class Camera {
	
	public static final Vector3f yAxis = new Vector3f(0, 1, 0);
	
	private Vector3f pos;
	private Vector3f forward;
	private Vector3f up;
	private Matrix4f projection;
	
	public Vector3f getPos() { return pos; }
	public Vector3f getForward() { return forward; }
	public Vector3f getUp() { return up; }
	public Vector3f getLeft() { return forward.cross(up).normalized(); }
	public Vector3f getRight() { return up.cross(forward).normalized(); }
	
	public void setPos(Vector3f pos) { this.pos = pos; }
	public void setForward(Vector3f forward) { this.forward = forward; }
	public void setUp(Vector3f up) { this.up = up; }
	
	public Camera(float fov, float aspectRatio, float zNear, float zFar) {
		this.pos = new Vector3f(0, 0, 0);
		this.forward = new Vector3f(0, 0, 1).normalized();
		this.up = new Vector3f(0, 1, 0).normalized();
		
		this.projection = new Matrix4f().initPerspective(fov, aspectRatio, zNear, zFar);
	}
	
	public Matrix4f getViewProjection() {
		Matrix4f cameraRotation = new Matrix4f().initRotation(forward, up);
		Matrix4f cameraTranslation = new Matrix4f().initTranslation(-pos.getX(), -pos.getY(), -pos.getZ());
		
		return projection.mul(cameraRotation.mul(cameraTranslation));
	}
	
	boolean mouseLocked = false;
	Vector2f centerPos = new Vector2f(Window.getWidth()/2, Window.getHeight()/2);
	
	public void input(float delta) {
		float sensitivity = 0.5f;
		float moveAmt = (float)(10*delta);
		//float rotAmt = (float)(100*Time.getDelta());
		
		if(Input.getKey(Input.KEY_ESCAPE)){
			Input.setCursor(true);
			mouseLocked = false;
		}
		if(Input.getMouseDown(0)){
			Input.setMousePos(centerPos);
			Input.setCursor(false);
			mouseLocked = true;
		}
		
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
		
		if(mouseLocked){
			Vector2f deltaPos = Input.getMousePos().sub(centerPos);
			
			boolean rotX = deltaPos.getY() != 0;
			boolean rotY = deltaPos.getX() != 0;
			
			if(rotY){
				rotateY(deltaPos.getX()*sensitivity);
			}
			if(rotX){
				rotateX(-deltaPos.getY()*sensitivity);
			}
			
			if(rotX || rotY){
				Input.setMousePos(new Vector2f(Window.getWidth()/2, Window.getHeight()/2));
			}
		}
		
/*		
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
*/
	}
	
	public void move(Vector3f dir, float amt) {
		pos = pos.add(dir.mul(amt));
	}
	
	public void rotateY(float angle) {
		Vector3f Haxis = yAxis.cross(forward).normalized();
		
		forward.rotate(angle, yAxis).normalized();
		
		up = forward.cross(Haxis).normalized();
	}
	
	public void rotateX(float angle) {
		Vector3f Haxis = yAxis.cross(forward);
		Haxis.normalized();
		
		forward.rotate(angle, Haxis);
		forward.normalized();
		
		up = forward.cross(Haxis);
		up.normalized();
	}
	
}
