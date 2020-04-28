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
struct Lighting{
    vec3 direction;
    vec4 ambient_color;
    vec4 diffuse_color;
    vec4 specular_color;
    float ambient_power;
    float diffuse_power;
    float specular_power;
};

uniform Camera camera;
uniform Fog fog;
uniform Lighting lighting;

layout(location=0) in vec3 vs_in_position;
layout(location=1) in vec2 vs_in_uv;
layout(location=2) in vec3 vs_in_normal;
out vec2 vs_out_uv;
out vec4 vs_out_color;
out float vs_out_fog_factor;

void main(){
    //Position
    mat4 camera_matrix=camera.projection*camera.view_transformation;
    gl_Position=camera_matrix*vec4(vs_in_position,1.0);

    //UVs
    vs_out_uv=vs_in_uv;

    //Lighting
    vec3 camera_direction=normalize(camera.target-camera.position);
    vec3 half_le=-normalize(camera_direction+light.direction);

    float diffuse_coefficient=clamp(dot(vs_in_normal,-light.direction),0.0,1.0);
    float specular_coefficient=pow(clamp(dot(vs_in_normal,half_le),0.0,1.0),2.0);

    vec4 ambient_color=vec4(lighting.ambient_color*lighting.ambient_power)
    vec4 diffuse_color=vec4(lighting.diffuse_color*diffuse_coefficient*diffuse_power);
    vec4 specular_color=vec4(lighting.specular_color*specular_coefficient*specular_power);

    vs_out_color=ambient_color+diffuse_color+specular_color;
    vs_out_color.a=1.0;

    //Fog
    float linear_pos=length(camera.position-vs_in_position);
    vs_out_fog_factor=clamp((fog.end-linear_pos)/(fog.end-fog.start),0.0,1.0);
}
