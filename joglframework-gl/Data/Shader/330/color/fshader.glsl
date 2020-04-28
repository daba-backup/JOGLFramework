#version 330

struct Fog{
    float start;
    float end;
    vec4 color;
};

uniform Fog fog;

in vec4 vs_out_color;
in float vs_out_fog_factor;
out vec4 fs_out_color;

void main(void){
  fs_out_color=mix(fog.color,vs_out_color,vs_out_fog_factor);
}
