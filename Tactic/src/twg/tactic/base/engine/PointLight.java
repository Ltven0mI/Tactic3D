package twg.tactic.base.engine;

public class PointLight {
	
	private BaseLight base;
	private Attenuation atten;
	private Vector3f pos;
	private float range;
	
	public BaseLight getBase() { return base; }
	public Attenuation getAtten() { return atten; }
	public Vector3f getPos() { return pos; }
	public float getRange() { return range; }
	
	public void setBase(BaseLight base) { this.base = base; }
	public void setAtten(Attenuation atten) { this.atten = atten; }
	public void setPos(Vector3f pos) { this.pos = pos; }
	public void setRange(float range) { this.range = range; }
	
	public PointLight(BaseLight base, Attenuation atten, Vector3f pos, float range) {
		this.base = base;
		this.atten = atten;
		this.pos = pos;
		this.range = range;
	}
	
}
