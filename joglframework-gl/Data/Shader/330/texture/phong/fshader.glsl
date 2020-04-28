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
uniform sampler2D texture_sampler;

in vec3 vs_out_position;
in vec2 vs_out_uv;
in vec3 vs_out_normal;
out vec4 fs_out_color;

void main(){
    //Lighting
    vec3 camera_direction=normalize(camera.target-camera.position);
    vec3 half_le=-normalize(camera_direction+lighting.direction);

    float diffuse_coefficient=clamp(dot(vs_in_normal,-lighting.direction),0.0,1.0);
    float specular_coefficient=pow(clamp(dot(vs_in_normal,half_le),0.0,1.0),2.0);

    vec4 ambient_color=vec4(lighting.ambient_color*lighting.ambient_power);
    vec4 diffuse_color=vec4(lighting.diffuse_color*diffuse_coefficient*lighting.diffuse_power);
    vec4 specular_color=vec4(lighting.specular_color*specular_coefficient*lighting.specular_power);

    vec4 post_lighting_color=ambient_color+diffuse_color+specular_color;
    post_lighting_color.a=1.0;

    //Fog
    float linear_pos=length(camera.position-vs_out_position);
    float fog_factor=clamp((fog.end-linear_pos)/(fog.end-fog.start),0.0,1.0);

    fs_out_color=mix(fog.color,post_lighting_color*texture(texture_sampler,vs_out_uv),fog_factor);
}
