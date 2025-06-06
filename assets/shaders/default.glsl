#type vertex
#version 300 es

layout (location=0) in vec3 aCenter;
layout (location=1) in vec2 aScale;
layout (location=2) in vec4 aColor;
layout (location=3) in vec2 aTexCoords;
layout (location=4) in float aTexId;

out vec4 fColor;
out vec2 fTexCoords;
out float fTexId;

uniform mat4 uView;
uniform mat4 uProjection;

void main() {
    // element vertex id: gl_VertexID

    //  1      0
    //
    //  2      3

    int vert = gl_VertexID & 3;

    vec3 pos = aCenter + vec3(
        (vert == 2) ? -aScale :
        (vert == 3) ? vec2(aScale.x, -aScale.y) :
        (vert == 1) ? vec2(-aScale.x, aScale.y) :
                      aScale,
        0.0
    );

    fColor = aColor;
    fTexCoords = aTexCoords;
    fTexId = aTexId > 0.0 ? float(aTexId) : -1.0;

    gl_Position = uView * uProjection * vec4(pos, 1.0);

}

#type fragment
#version 300 es
precision mediump float;

in vec4 fColor;
in vec2 fTexCoords;
in float fTexId;

uniform sampler2D uTextures[8];

out vec4 color;

void main() {
    if(fColor.w == 0.0)
        discard;

    if (fTexId > 0.0) {
        int id = int(fTexId);
        switch(id) {
            case 1: color = fColor * texture(uTextures[1], fTexCoords); break;
            case 2: color = fColor * texture(uTextures[2], fTexCoords); break;
            case 3: color = fColor * texture(uTextures[3], fTexCoords); break;
            case 4: color = fColor * texture(uTextures[4], fTexCoords); break;
            case 5: color = fColor * texture(uTextures[5], fTexCoords); break;
            case 6: color = fColor * texture(uTextures[6], fTexCoords); break;
            case 7: color = fColor * texture(uTextures[7], fTexCoords); break;
        }
    } else {
        color = fColor;
    }
}
