#version 330

in vec2 texCoord0;

out vec4 fragColor;

uniform vec3 baseColor;
uniform int hasTexture;
uniform sampler2D sampler;
uniform vec3 ambientLight;

void main(){
	
	vec4 totalLight = vec4(ambientLight, 1);
	vec4 color = vec4(baseColor, 1);
	
	if(hasTexture == 1)
		color *= texture2D(sampler, texCoord0.xy);
	
	fragColor = color*totalLight;
}