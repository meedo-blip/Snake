package renderer;

import components.SpriteRenderer;
import jade.Constants;
import jade.Transform;
import org.joml.Vector2f;
import org.joml.Vector4f;

import java.util.ArrayList;

import static jade.Constants.MAX_BATCH_SIZE;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL20.*;

public class DefaultBatch extends RenderBatch {
    // Vertex
    // =======
    // Pos              Scale               Color                  Tex coords      tex id
    // float, float     float, float,       float, float, float    float, float    float
    private final int
            POS_SIZE = 3,
            SCALE_SIZE = 2,
            COLOR_SIZE = 3,
            TEX_COORDS_SIZE = 2,
            TEX_ID_SIZE = 1;

    protected final int
            POS_OFFSET = 0,
            SCALE_OFFSET = POS_OFFSET + (POS_SIZE * Float.BYTES),
            COLOR_OFFSET = SCALE_OFFSET + (SCALE_SIZE * Float.BYTES),
            TEX_COORDS_OFFSET = COLOR_OFFSET + (COLOR_SIZE * Float.BYTES),
            TEX_ID_OFFSET = TEX_COORDS_OFFSET + (TEX_COORDS_SIZE * Float.BYTES),
            VERTEX_SIZE = 11,
            VERTEX_SIZE_BYTES = VERTEX_SIZE * Float.BYTES;

    public DefaultBatch() {
        this.shader = Constants.DEFAULT_SH;

        vertexSize = VERTEX_SIZE;
    }

    @Override
    protected void initAttribPointers() {
        // Info on the vertex attribute pointers
        glVertexAttribPointer(0, POS_SIZE, GL_FLOAT, false, VERTEX_SIZE_BYTES, POS_OFFSET);

        glVertexAttribPointer(1, SCALE_SIZE, GL_FLOAT, false, VERTEX_SIZE_BYTES, SCALE_OFFSET);

        glVertexAttribPointer(2, COLOR_SIZE, GL_FLOAT, false, VERTEX_SIZE_BYTES, COLOR_OFFSET);

        glVertexAttribPointer(3, TEX_COORDS_SIZE, GL_FLOAT, false, VERTEX_SIZE_BYTES, TEX_COORDS_OFFSET);

        glVertexAttribPointer(4, TEX_ID_SIZE, GL_FLOAT, false, VERTEX_SIZE_BYTES, TEX_ID_OFFSET);
    }

    @Override
    protected void loadVertexProperties(int index) {
        SpriteRenderer sprite = this.sprites.get(index);

        // Find offset within vertices array (4 vertices per sprite)
        int offset = index * 4 * VERTEX_SIZE;

        Vector2f[] texCoords = sprite.getTexCoords();
        Vector4f color = sprite.getColor();

        Transform transform = sprite.gameObject.transform;

        // texID is the tex position in textures
        // 0 means no texture

        float halfX = transform.scale.x / 2;
        float halfY = transform.scale.y / 2;

        for(int i = 0; i < 4; i++) {

            // Top right     0
            // Top left      1
            // Bottom left   2
            // Bottom right  3

            // Load position
            vertices[offset] = transform.position.x;
            vertices[offset + 1] = transform.position.y;
            vertices[offset + 2] = transform.z;

            // Load scale
            vertices[offset + 3] = halfX;
            vertices[offset + 4] = halfY;

            // Load color
            vertices[offset + 5] = color.x;
            vertices[offset + 6] = color.y;
            vertices[offset + 7] = color.z;

            // Load texture coords
            vertices[offset + 8] = texCoords[i].x;
            vertices[offset + 9] = texCoords[i].y;

            // Load texture id
            // texID is the tex position in textures
            // 0 means no texture
            vertices[offset + 10] = sprite.getTexId();

            offset += VERTEX_SIZE;
        }
    }

    @Override
    protected void enableVertexAttribs()  {
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);
        glEnableVertexAttribArray(3);
        glEnableVertexAttribArray(4);
    }

    @Override
    protected void disableVertexAttribs() {
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(2);
        glDisableVertexAttribArray(3);
        glDisableVertexAttribArray(4);
    }
}
