#version 100

uniform mediump vec3 color;

void main() {
    gl_FragColor = vec4(color.r, color.g, color.b, 1.0);
}