#type vertex
#version 300 es

layout (location=0) in vec3 aCenter;
layout (location=1) in vec2 aScale;
layout (location=2) in vec3 aColor;
layout (location=3) in float aRotate;

out vec3 fColor;
out vec2 fTexCoords;
out vec2 fCoords;
out float fTexId;
flat out int fAttribs;

uniform mat4 uView;
uniform mat4 uProjection;
uniform float uPixel; // pixel width

void main() {
    // element vertex id: gl_VertexID

    //  1      0
    //
    //  2      3

    int vert = gl_VertexID & 3;
    int attribs = int(aCenter.z);

    vec2 center = aCenter.xy * uPixel;

    vec2 pos = ((vert == 2) ? vec2(-aScale.x, -aScale.y)
    : (vert == 3) ? vec2(aScale.x, -aScale.y)
    : (vert == 1) ? vec2(-aScale.x, aScale.y)
    : vec2(aScale.x, aScale.y));

    fColor = aColor;
    fAttribs = abs(attribs >> 4);
    fTexCoords = vert == 0 ? vec2(0.0,1.0)
        : vert == 1 ? vec2(0.0, 0.0)
        : vert == 2 ? vec2(1.0, 0.0)
        : vec2(1.0, 1.0);

    float texId = float(attribs & 15);
    fTexId = texId > 0.0 ? texId : -1.0;

    fCoords = vec2(round(float(aCenter.x)), float(round(float(aCenter.y))));

    if((attribs & 112) == 0) {
        float c = cos(aRotate), s = sin(aRotate);
        gl_Position = uView * uProjection * vec4(((pos.x * c) - (pos.y * s)) + center.x,
        ((s * pos.x) + (c * pos.y)) + center.y, -1, 1.0);
    } else {
        gl_Position = uView * uProjection * vec4(pos + center, -1, 1.0);
    }
}

#type fragment
#version 300 es
precision mediump float; // Required for ES

in vec3 fColor;
in vec2 fTexCoords;
in vec2 fCoords;
in float fTexId;
flat in int fAttribs;

uniform sampler2D uTextures[8];
                            //  y = mx + c
// xy coords, gradient, c(and isClockwise bit)
uniform vec2 uHead, uBack;
out vec4 color;

// -3 Hemicircle
// -2 circle
// -1 box

// 0 -> 3 snake conjunction
// 2    3

// 0    1

void main() {
    int shape = int(fAttribs) - 3;

    float x = fTexCoords.x;
    float y = fTexCoords.y - 0.5f;

    if (shape == -3) {
        if ((x * x) + (y * y) > 0.25)
            discard;
    }
    else if (shape == -2) {
        x -= 0.5;
        if ((x * x) + (y * y) > 0.25)
            discard;
    }

    else if (shape >= 0) {
        float ox = float((shape & 1)  ^ ((shape & 1) ^ ((shape & 2) >> 1))),
        oy = float(((shape & 2) >> 1) ^ ((shape & 1) ^ ((shape & 2) >> 1)));

        float dx = fTexCoords.x - ox;
        float dy = fTexCoords.y - oy;

        float dist = (dy * dy) + (dx * dx);

        if (dist < 0.0675 || dist > 0.5625) {
            discard;
        }

        // float m = ((oy - (uHead.y - floor(uHead.y)) / (ox - (uHead.x - floor(uHead.x)))));
        // float c = (oy - (m * (ox)));

        // if(round(uHead.x) == fCoords.x && round(uHead.y) == fCoords.y) {
        //      if (fTexCoords.y > (m * fTexCoords.x) + c)
        //         discard;
        //  }
        // else if(uBack.x == fCoords.x && uBack.y == fCoords.y) {
        //if (fTexCoords.y * (ox - uBack.x) < (oy - uBack.y) * fTexCoords.x)
        //  discard;
    }


    if (fTexId > 0.0) {
        int id = int(fTexId);
        switch(id) {
            case 1: color = vec4(fColor, 1.0) * texture(uTextures[1], fTexCoords); break;
            case 2: color = vec4(fColor, 1.0) * texture(uTextures[2], fTexCoords); break;
            case 3: color = vec4(fColor, 1.0) * texture(uTextures[3], fTexCoords); break;
            case 4: color = vec4(fColor, 1.0) * texture(uTextures[4], fTexCoords); break;
            case 5: color = vec4(fColor, 1.0) * texture(uTextures[5], fTexCoords); break;
            case 6: color = vec4(fColor, 1.0) * texture(uTextures[6], fTexCoords); break;
            case 7: color = vec4(fColor, 1.0) * texture(uTextures[7], fTexCoords); break;
        }
    } else {
        color = vec4(fColor, 1.0);
    }

   // if(fTexCoords.x <= 0.05 || fTexCoords.y <= 0.05
    //|| fTexCoords.x >= 0.95 || fTexCoords.y >= 0.95) {
    //    color = vec4(1,1,1,1);
    //}
}
