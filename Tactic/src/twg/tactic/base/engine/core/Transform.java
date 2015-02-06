package twg.tactic.base.engine.core;

public class Transform {
	
	private Vector3f pos;
	private Quaternion rot;
	private Vector3f scale;
	
	public Vector3f getPos() { return pos; }
	public Quaternion getRot() { return rot; }
	public Vector3f getScale() { return scale; }
	
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
	}
	
	public Matrix4f getTransformation() {
		Matrix4f translationMatrix = new Matrix4f().initTranslation(pos.getX(), pos.getY(), pos.getZ());
		Matrix4f rotationMatrix = rot.toRotationMatrix();
		Matrix4f scaleMatrix = new Matrix4f().initScale(scale.getX(), scale.getY(), scale.getZ());
		
		return translationMatrix.mul(rotationMatrix.mul(scaleMatrix));
	}
	
}