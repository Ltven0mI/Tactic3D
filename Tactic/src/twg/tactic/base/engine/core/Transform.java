package twg.tactic.base.engine.core;

public class Transform {
	
	private Transform parent;
	private Matrix4f parentMatrix;
	
	private Vector3f pos;
	private Quaternion rot;
	private Vector3f scale;
	
	private Vector3f oldPos;
	private Quaternion oldRot;
	private Vector3f oldScale;
	
	public Vector3f getPos() { return pos; }
	public Quaternion getRot() { return rot; }
	public Vector3f getScale() { return scale; }
	
	public void setParent(Transform parent) { this.parent = parent; }
	public void setPos(Vector3f pos) { this.pos = pos; }
	public void setPos(float x, float y, float z) { this.pos = new Vector3f(x, y, z); }
	public void setRot(Quaternion rot) { this.rot = rot; }
	public void setRot(float x, float y, float z, float w) { this.rot = new Quaternion(x, y, z, w); }
	public void setScale(Vector3f scale) { this.scale = scale; }
	public void setScale(float x, float y, float z) { this.scale = new Vector3f(x, y, z); }
	
	public Transform() {
		pos = new Vector3f(0, 0, 0);
		rot = new Quaternion(0, 0, 0, 1);
		scale = new Vector3f(1, 1, 1);
		parentMatrix = new Matrix4f().initIdentity();
	}
	
	public void update() {
		if(oldPos != null){
			oldPos.set(pos).add(1.0f);
			oldRot.set(rot).mul(0.5f);
			oldScale.set(scale).add(1.0f);
		}else{
			oldPos = new Vector3f(0, 0, 0).set(pos);
			oldRot = new Quaternion(0, 0, 0, 0).set(rot);
			oldScale = new Vector3f(0, 0, 0).set(scale);
		}
	}
	
	public void rotate(Vector3f axis, float angle) {
		rot = new Quaternion(axis, angle).mul(rot).normalized();
	}
	
	public boolean hasChanged() {	
		if(parent != null && parent.hasChanged()){
			return true;
		}
		
		if(!pos.equals(oldPos) || !rot.equals(oldRot) || !scale.equals(oldScale)){
			return true;
		}
		
		return false;
	}
	
	public Matrix4f getTransformation() {
		Matrix4f translationMatrix = new Matrix4f().initTranslation(pos.getX(), pos.getY(), pos.getZ());
		Matrix4f rotationMatrix = rot.toRotationMatrix();
		Matrix4f scaleMatrix = new Matrix4f().initScale(scale.getX(), scale.getY(), scale.getZ());
		
		return getParentMatrix().mul(translationMatrix.mul(rotationMatrix.mul(scaleMatrix)));
	}
	
	public Matrix4f getParentMatrix() {
		if(parent != null && parent.hasChanged()){
			parentMatrix = parent.getTransformation();
		}
		
		return parentMatrix;
	}
	
	public Vector3f getTransformedPos() {
		return getParentMatrix().transform(pos);
	}
	
	public Quaternion getTransformedRot() {
		Quaternion parentRot = new Quaternion(0, 0, 0, 1);
		if(parent != null){
			parentRot = parent.getTransformedRot();
		}
		return parentRot.mul(rot);
	}
	
}
