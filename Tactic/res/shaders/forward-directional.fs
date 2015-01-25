#version 120

varying vec2 texCoord0;
varying vec3 normal0;
varying vec3 worldPos0;

struct BaseLight{
	vec3 color;
	float intensity;
};

struct DirectionalLight{
	BaseLight base;
	vec3 direction;
};

uniform vec3 eyePos;
uniform sampler2D diffuse;

uniform float specInt;
uniform float specExp;

uniform DirectionalLight directionalLight;

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

vec4 calcDirectionalLight(DirectionalLight directionalLight, vec3 normal){
	return calcLight(directionalLight.base, -directionalLight.direction, normal);
}

void main(){
	gl_FragColor = texture2D(diffuse, texCoord0.xy)*calcDirectionalLight(directionalLight, normalize(normal0));
}