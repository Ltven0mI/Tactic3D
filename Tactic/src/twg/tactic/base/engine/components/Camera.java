package twg.tactic.base.engine.components;

import twg.tactic.base.engine.core.Input;
import twg.tactic.base.engine.core.Matrix4f;
import twg.tactic.base.engine.core.Quaternion;
import twg.tactic.base.engine.core.Time;
import twg.tactic.base.engine.core.Vector2f;
import twg.tactic.base.engine.core.Vector3f;
import twg.tactic.base.engine.rendering.RenderingEngine;
import twg.tactic.base.engine.rendering.Window;

public class Camera extends GameComponent{
	
	public static final Vector3f yAxis = new Vector3f(0, 1, 0);
	
	private Matrix4f projection;
	
	public Camera(float fov, float aspectRatio, float zNear, float zFar) {
		this.projection = new Matrix4f().initPerspective(fov, aspectRatio, zNear, zFar);
	}
	
	public Matrix4f getViewProjection() {
		Matrix4f cameraRotation = getTransform().getTransformedRot().conjugate().toRotationMatrix();
		Vector3f cameraPos = getTransform().getTransformedPos().mul(-1);
		
		Matrix4f cameraTranslation = new Matrix4f().initTranslation(cameraPos.getX(), cameraPos.getY(), cameraPos.getZ());
		
		return projection.mul(cameraRotation.mul(cameraTranslation));
	}
	
	@Override
	public void addToRenderingEngine(RenderingEngine renderingEngine) {
		renderingEngine.addCamera(this);
	}
	
	boolean mouseLocked = false;
	Vector2f centerPos = new Vector2f(Window.getWidth()/2, Window.getHeight()/2);
	
	@Override
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
			move(getTransform().getRot().getForward(), moveAmt);
		}
		if(Input.getKey(Input.KEY_S)){
			move(getTransform().getRot().getForward(), -moveAmt);
		}
		if(Input.getKey(Input.KEY_A)){
			move(getTransform().getRot().getLeft(), moveAmt);
		}
		if(Input.getKey(Input.KEY_D)){
			move(getTransform().getRot().getLeft(), -moveAmt);
		}
		
		if(mouseLocked){
			Vector2f deltaPos = Input.getMousePos().sub(centerPos);
			
			boolean rotX = deltaPos.getY() != 0;
			boolean rotY = deltaPos.getX() != 0;
			
			if(rotY){
				getTransform().rotate(yAxis, (float)Math.toRadians(deltaPos.getX()*sensitivity));
			}
			if(rotX){
				getTransform().rotate(getTransform().getRot().getRight(), (float)Math.toRadians(-deltaPos.getY()*sensitivity));
			}
			
			if(rotX || rotY){
				Input.setMousePos(new Vector2f(Window.getWidth()/2, Window.getHeight()/2));
			}
		}
	}
	
	public void move(Vector3f dir, float amt) {
		getTransform().setPos(getTransform().getPos().add(dir.mul(amt)));
	}
	
}
