package twg.tactic.base.engine.components;

import twg.tactic.base.engine.core.Vector3f;
import twg.tactic.base.engine.rendering.Attenuation;
import twg.tactic.base.engine.rendering.BaseLight;
import twg.tactic.base.engine.rendering.RenderingEngine;

public class PointLight extends GameComponent{
	
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
	
	@Override
	public void addToRenderingEngine(RenderingEngine renderingEngine) {
		renderingEngine.addPointLight(this);
	}
	
}
