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

layout(location=0) in vec3 vs_in_position;
layout(location=1) in vec4 vs_in_color;
out vec4 vs_out_color;
out float vs_out_fog_factor;

void main(){
    mat4 camera_matrix=camera.projection*camera.view_transformation;
    gl_Position=camera_matrix*vec4(vs_in_position,1.0);
    vs_out_color=vs_in_color;

    float linear_pos=length(camera.position-vs_in_position);
    vs_out_fog_factor=clamp((fog.end-linear_pos)/(fog.end-fog.start),0.0,1.0);
}
