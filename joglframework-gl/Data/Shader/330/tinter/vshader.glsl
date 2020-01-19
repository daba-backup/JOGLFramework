#version 330

layout(location=0) in vec2 vs_in_position;

uniform vec4 tint_color;

out vec4 vs_out_color;

void main(){
    gl_Position=vec4(vs_in_position,-1.0,1.0);
    vs_out_color=tint_color;
}
