#version 330

layout(location=0) in vec3 vs_in_position;
layout(location=1) in vec2 vs_in_uv;
layout(location=2) in vec3 vs_in_normal;

uniform vec3 camera_position;
uniform vec3 camera_target;
uniform mat4 projection;
uniform mat4 view_transformation;
uniform float camera_near;
uniform float camera_far;

uniform vec3 light_position;
uniform vec3 light_direction;
uniform float light_attenuation;
uniform float phi;
uniform float theta;
uniform float falloff;

uniform vec4 diffuse_color;
uniform vec4 ambient_color;

uniform float specular_power;

out vec2 vs_out_uv;
out vec4 vs_out_color;
out float vs_out_fog_factor;

void SetPosition(){
    mat4 camera_matrix=projection*view_transformation;
    gl_Position=camera_matrix*vec4(vs_in_position,1.0);
}
void SetUVs(){
    vs_out_uv=vs_in_uv;
}
void SetLighting(){
    vec3 r=vs_in_position-light_position;
    float length_r=length(r);
    float attenuation=1.0/(light_attenuation*length_r);

    vec3 normalized_r=normalize(r);

    float cos_alpha=dot(normalized_r,light_direction);
    float cos_half_theta=cos(theta/2.0);
    float cos_half_phi=cos(phi/2.0);

    if(cos_alpha<=cos_half_phi){
        vs_out_color=ambient_color;
        return;
    }
    else{
        if(cos_alpha>cos_half_theta){
        
        }
        else{
            attenuation*=pow((cos_alpha-cos_half_phi)/(cos_half_theta-cos_half_phi),falloff);
        }
    }

    vec3 half_le=normalize(camera_target+light_direction);
    float specular=pow(clamp(dot(vs_in_normal,half_le),0.0,1.0),2.0);

    vec4 specular_color=vec4(specular*specular_power);

    vs_out_color=ambient_color+diffuse_color*attenuation+specular_color;
    vs_out_color.a=1.0;
}

void main(){
    SetPosition();
    SetUVs();
    SetLighting();
}
