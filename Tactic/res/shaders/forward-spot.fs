#version 120

varying vec2 texCoord0;
varying vec3 normal0;
varying vec3 worldPos0;

struct BaseLight{
	vec3 color;
	float intensity;
};

struct Attenuation{
	float constant;
	float linear;
	float exponent;
};

struct PointLight{
	BaseLight base;
	Attenuation atten;
	vec3 position;
	float range;
};

struct SpotLight{
	PointLight pointLight;
	vec3 direction;
	float cutOff;
};

uniform vec3 eyePos;
uniform sampler2D diffuse;

uniform float specInt;
uniform float specExp;

uniform SpotLight spotLight;

vec4 calcLight(BaseLight base, vec3 direction, vec3 normal){
	float diffuseFactor = dot(normal, -direction);
	
	vec4 diffuseColor = vec4(0, 0, 0, 0);
	vec4 specColor = vec4(0, 0, 0, 0);
	
	if(diffuseFactor > 0){
		diffuseColor = vec4(base.color, 1.0) * base.intensity * diffuseFactor;
		
		vec3 directionToEye = normalize(eyePos - worldPos0);
		vec3 reflectDirection = normalize(reflect(direction, normal));
		
		float specFactor = dot(directionToEye, reflectDirection);
		specFactor = pow(specFactor, specExp);
		
		if(specFactor > 0){
			specColor = vec4(base.color, 1.0) * specInt * specFactor;
		}
	}
	
	return diffuseColor + specColor;
}

vec4 calcPointLight(PointLight pointLight, vec3 normal){
	vec3 lightDir = worldPos0 - pointLight.position;
	float distToPoint = length(lightDir);
	
	if(distToPoint > pointLight.range){
		return vec4(0, 0, 0, 0);
	}
	
	lightDir = normalize(lightDir);
	
	vec4 color = calcLight(pointLight.base, lightDir, normal);
	
	float attenuation = pointLight.atten.constant + pointLight.atten.linear * distToPoint + pointLight.atten.exponent * distToPoint * distToPoint + 0.0001f;
	
	return color/attenuation;
}

vec4 calcSpotLight(SpotLight spotLight, vec3 normal){
	vec3 lightDirection =normalize(worldPos0 - spotLight.pointLight.position);
	float spotFactor = dot(lightDirection, spotLight.direction);
	
	vec4 color = vec4(0, 0, 0, 0);
	
	if(spotFactor > spotLight.cutOff){
		color = calcPointLight(spotLight.pointLight, normal) * (1.0-(1.0 - spotFactor)/(1.0-spotLight.cutOff));
	}
	
	return color;
}

void main(){
	gl_FragColor = texture2D(diffuse, texCoord0.xy)*calcSpotLight(spotLight, normalize(normal0));
}