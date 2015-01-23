package twg.tactic.base.engine;

public class Transform {
	
	private static float zNear;
	private static float zFar;
	private static float width;
	private static float height;
	private static float fov;
	private static Camera camera;
	
	private Vector3f translation;
	private Vector3f rotation;
	private Vector3f scale;
	
	public static Camera GetCamera() { return camera; }
	public Vector3f GetTranslation() { return translation; }
	public Vector3f GetRotation() { return rotation; }
	public Vector3f GetScale() { return scale; }
	
	public static void SetProjection(float fov, float width, float height, float zNear, float zFar) { Transform.fov = fov; Transform.width = width; Transform.height = height; Transform.zNear = zNear; Transform.zFar = zFar; }
	public static void SetCamera(Camera camera) { Transform.camera = camera; }
	public void SetTranslation(Vector3f translation) { this.translation = translation; }
	public void SetTranslation(float x, float y, float z) { this.translation = new Vector3f(x, y, z); }
	public void SetRotation(Vector3f rotation) { this.rotation = rotation; }
	public void SetRotation(float x, float y, float z) { this.rotation = new Vector3f(x, y, z); }
	public void SetScale(Vector3f scale) { this.scale = scale; }
	public void SetScale(float x, float y, float z) { this.scale = new Vector3f(x, y, z); }
	
	public Transform() {
		translation = new Vector3f(0, 0, 0);
		rotation = new Vector3f(0, 0, 0);
		scale = new Vector3f(1, 1, 1);
	}
	
	public Matrix4f GetTransformation() {
		Matrix4f translationMatrix = new Matrix4f().InitTranslation(translation.GetX(), translation.GetY(), translation.GetZ());
		Matrix4f rotationMatrix = new Matrix4f().InitRotation(rotation.GetX(), rotation.GetY(), rotation.GetZ());
		Matrix4f scaleMatrix = new Matrix4f().InitScale(scale.GetX(), scale.GetY(), scale.GetZ());
		
		return translationMatrix.Mul(rotationMatrix.Mul(scaleMatrix));
	}
	
	public Matrix4f GetProjectedTransformation() {
		Matrix4f transformationMatrix = GetTransformation();
		Matrix4f projectionMatrix = new Matrix4f().InitProjection(fov, width, height, zNear, zFar);
		Matrix4f cameraRotation = new Matrix4f().InitCamera(camera.GetForward(), camera.GetUp());
		Matrix4f cameraTranslation = new Matrix4f().InitTranslation(-camera.GetPos().GetX(), -camera.GetPos().GetY(), -camera.GetPos().GetZ());
		
		return projectionMatrix.Mul(cameraRotation.Mul(cameraTranslation.Mul(transformationMatrix)));
	}
	
}
