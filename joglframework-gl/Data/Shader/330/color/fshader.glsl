#version 330

uniform Fog fog;

in vec4 vs_out_color;
in float vs_out_fog_factor;
out vec4 fs_out_color;

void main(void){
  fs_out_color=mix(fog.color,vs_out_color,vs_out_fog_factor);
}
