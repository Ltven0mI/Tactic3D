package twg.tactic.base.engine;

public class PointLight {
	
	private BaseLight base;
	private Attenuation atten;
	private Vector3f pos;
	
	public BaseLight getBase() { return base; }
	public Attenuation getAtten() { return atten; }
	public Vector3f getPos() { return pos; }
	
	public void setBase(BaseLight base) { this.base = base; }
	public void setAtten(Attenuation atten) { this.atten = atten; }
	public void setPos(Vector3f pos) { this.pos = pos; }
	
	public PointLight(BaseLight base, Attenuation atten, Vector3f pos) {
		this.base = base;
		this.atten = atten;
		this.pos = pos;
	}
	
}
