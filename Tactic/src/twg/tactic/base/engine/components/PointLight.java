package twg.tactic.base.engine.components;

import twg.tactic.base.engine.core.Vector3f;
import twg.tactic.base.engine.rendering.Shader;

public class PointLight extends BaseLight{
	
	private static final int COLOR_DEPTH = 256;
	
	private BaseLight base;
	private Vector3f attenuation;
	private float range;
	
	public BaseLight getBase() { return base; }
	public float getConstant() { return attenuation.getX(); }
	public float getLinear() { return attenuation.getY(); }
	public float getExponent() { return attenuation.getZ(); }
	public float getRange() { return range; }
	
	public void setBase(BaseLight base) { this.base = base; }
	public void setConstant(float constant) { this.attenuation.setX(constant); }
	public void setLinear(float linear) { this.attenuation.setY(linear); }
	public void setExponent(float exponent) { this.attenuation.setZ(exponent); }
	public void setRange(float range) { this.range = range; }
	
	public PointLight(Vector3f color, float intensity, Vector3f attenuation) {
		super(color, intensity);
		this.attenuation = attenuation;
		
		float a = attenuation.getZ();
		float b = attenuation.getY();
		float c = attenuation.getX() - COLOR_DEPTH * getIntensity() * getColor().max();
		
		this.range = (float)((-b + Math.sqrt(b*b - 4 * a * c))/(2*a));
		
		setShader(new Shader("forward-point"));
	}
	
}
