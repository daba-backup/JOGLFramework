#version 330

struct Camera{
    vec3 position;
    vec3 target;
    mat4 projection;
    mat4 view_transformation;
    float near;
    float far;
};
struct Fog{
    float start;
    float end;
    vec4 color;
};

uniform Camera camera;
uniform Fog fog;

in vec3 vs_out_position;
in vec4 vs_out_color;
out vec4 fs_out_color;

void main(void){
    float linear_pos=length(camera.position-vs_out_position);
    float fog_factor=clamp((fog.end-linear_pos)/(fog.end-fog.start),0.0,1.0);

    fs_out_color=mix(fog.color,vs_out_color,fog_factor);
}
