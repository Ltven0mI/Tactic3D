#version 330

in vec2 texCoord0;
in vec3 normal0;
in vec3 worldPos0;

out vec4 fragColor;

struct BaseLight{
	vec3 color;
	float intensity;
};

struct DirectionalLight{
	BaseLight base;
	vec3 direction;
};

uniform vec3 baseColor;
uniform int hasTexture;
uniform sampler2D sampler;
uniform vec3 ambientLight;

uniform vec3 eyePos;

uniform float specInt;
uniform float specExp;

uniform DirectionalLight directionalLight;

vec4 calcLight(BaseLight base, vec3 direction, vec3 norm){
	float diffuseFactor = dot(norm, -direction);
	
	vec4 diffuseColor = vec4(0, 0, 0, 0);
	vec4 specColor = vec4(0, 0, 0, 0);
	
	if(diffuseFactor > 0){
		diffuseColor = vec4(base.color, 1.0) * base.intensity * diffuseFactor;
		
		vec3 directionToEye = normalize(eyePos - worldPos0);
		vec3 reflectDirection = normalize(reflect(direction, norm));
		
		float specFactor = dot(directionToEye, reflectDirection);
		specFactor = pow(specFactor, specExp);
		
		if(specFactor > 0){
			specColor = vec4(base.color, 1.0) * specInt * specFactor;
		}
	}
	
	return diffuseColor + specColor;
}

vec4 calcDirectionalLight(DirectionalLight directionalLight, vec3 norm){
	return calcLight(directionalLight.base, -directionalLight.direction, norm);
}

void main(){
	
	vec4 totalLight = vec4(ambientLight, 1);
	vec4 color = vec4(baseColor, 1);
	
	if(hasTexture == 1)
		color *= texture2D(sampler, texCoord0.xy);
	
	vec3 normal = normalize(normal0);
	
	totalLight += calcDirectionalLight(directionalLight, normal);
	
	fragColor = color*totalLight;
}