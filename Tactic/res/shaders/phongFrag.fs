#version 330

const int MAX_POINT_LIGHTS = 4;
const int MAX_SPOT_LIGHTS = 4;

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

uniform vec3 baseColor;
uniform int hasTexture;
uniform sampler2D sampler;
uniform vec3 ambientLight;

uniform vec3 eyePos;

uniform float specInt;
uniform float specExp;

uniform DirectionalLight directionalLight;
uniform PointLight pointLights[MAX_POINT_LIGHTS];
uniform SpotLight spotLights[MAX_SPOT_LIGHTS];

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
	
	vec4 totalLight = vec4(ambientLight, 1);
	vec4 color = vec4(baseColor, 1);
	
	if(hasTexture == 1)
		color *= texture2D(sampler, texCoord0.xy);
	
	vec3 normal = normalize(normal0);
	
	totalLight += calcDirectionalLight(directionalLight, normal);
	
	for(int i=0; i<MAX_POINT_LIGHTS; i++){
		if(pointLights[i].base.intensity>0){
			totalLight += calcPointLight(pointLights[i], normal);
		}
	}
	
	for(int i=0; i<MAX_SPOT_LIGHTS; i++){
		if(spotLights[i].pointLight.base.intensity>0){
			totalLight += calcSpotLight(spotLights[i], normal);
		}
	}
	
	fragColor = color*totalLight;
}