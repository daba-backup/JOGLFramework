#version 330

uniform float z;

layout(location=0) in vec2 vs_in_position;
layout(location=1) in vec2 vs_in_uv;
out vec2 vs_out_uv;

void main(){
    gl_Position=vec4(vs_in_position,z,1.0);
    vs_out_uv=vs_in_uv;
}
