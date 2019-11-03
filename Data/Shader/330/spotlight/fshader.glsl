#version 330

in vec2 vs_out_uv;
in vec4 vs_out_color;

uniform sampler2D texture_sampler;

out vec4 fs_out_color;

void main(){
    fs_out_color=vs_out_color*texture(texture_sampler,vs_out_uv);
}
