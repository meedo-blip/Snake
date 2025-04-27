package game;

import jade.Transform;
import org.joml.Vector4f;
import renderer.QuadBatch;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;

public class GameBatch extends QuadBatch {

    private static final int
            POS_SIZE = 3,
            SCALE_SIZE = 2,
            COLOR_SIZE = 3,
            ROTATE_SIZE = 1;

    private static final int
            POS_OFFSET = 0,
            SCALE_OFFSET = POS_OFFSET + (POS_SIZE * Float.BYTES),
            COLOR_OFFSET = SCALE_OFFSET + (SCALE_SIZE * Float.BYTES),
            ROTATE_OFFSET = COLOR_OFFSET + (COLOR_SIZE * Float.BYTES),
            VERTEX_SIZE = 9,
            VERTEX_SIZE_BYTES = VERTEX_SIZE * Float.BYTES;

    public GameBatch() {
        super(GameConsts.GAME_SH, 9);
    }

    @Override
    protected void initAttribPointers() {
        // Info on the vertex attribute pointers
        glVertexAttribPointer(0, POS_SIZE, GL_FLOAT, false, VERTEX_SIZE_BYTES, POS_OFFSET);

        glVertexAttribPointer(1, SCALE_SIZE, GL_FLOAT, false, VERTEX_SIZE_BYTES, SCALE_OFFSET);

        glVertexAttribPointer(2, COLOR_SIZE, GL_FLOAT, false, VERTEX_SIZE_BYTES, COLOR_OFFSET);

        glVertexAttribPointer(3, ROTATE_SIZE, GL_FLOAT, false, VERTEX_SIZE_BYTES, ROTATE_OFFSET);

    }

    @Override
    protected void loadVertexProperties(int index) {
        GameSprite sprite = (GameSprite) this.sprites.get(index);

        // Find offset within vertices array (4 vertices per sprite)
        int offset = index * 4 * VERTEX_SIZE;

        Vector4f color = sprite.getColor();

        Transform transform = sprite.transform;

        // texID is the tex position in textures
        // 0 means no texture
        float z_id = textures.indexOf(sprite.getTexId()) + 1 + ((sprite.getShape() & 7) << 4);

        float halfX = transform.scale.x / 2;
        float halfY = transform.scale.y / 2;

        for (int i = 0; i < 4; i++) {

            // Top right     0
            // Top left      1
            // Bottom left   2
            // Bottom right  3

            // Load position
            vertices[offset] = transform.position.x;
            vertices[offset + 1] = transform.position.y;
            vertices[offset + 2] = z_id;

            // Load scale
            vertices[offset + 3] = halfX;
            vertices[offset + 4] = halfY;

            // Load color
            vertices[offset + 5] = color.x;
            vertices[offset + 6] = color.y;
            vertices[offset + 7] = color.z;

            // Load texture id
            vertices[offset + 8] = sprite.orbit;

            offset += VERTEX_SIZE;
        }
    }

    @Override
    protected void enableVertexAttribs()  {
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);
        glEnableVertexAttribArray(3);
    }

    @Override
    protected void disableVertexAttribs() {
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(2);
        glDisableVertexAttribArray(3);
    }

    @Override
    protected void uploadUniforms() {
        shader.uploadFloat("uPixel", GameScene.pixelWidth);
    }
}
