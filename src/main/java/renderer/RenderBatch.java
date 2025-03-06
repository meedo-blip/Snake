package renderer;

import components.SpriteRenderer;
import jade.Window;

import java.util.*;

import static jade.Constants.MAX_BATCH_SIZE;
import static org.lwjgl.opengl.ARBVertexArrayObject.glBindVertexArray;
import static org.lwjgl.opengl.ARBVertexArrayObject.glGenVertexArrays;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

public abstract class RenderBatch {

    protected List<SpriteRenderer> sprites;
    protected int vertexSize;
    protected float[] vertices;
    protected int[] texSlots = {0, 1, 2, 3, 4, 5, 6, 7};

    protected List<Texture> textures;
    protected int vaoID, vboID;
    protected boolean preGenIndices = true;
    protected Shader shader;

    public void start() {
        textures = new ArrayList<>(7);
        sprites = new ArrayList<>(MAX_BATCH_SIZE);

        vertices = new float[4 * MAX_BATCH_SIZE * vertexSize];

        // Generate and bind a Vertex Array Object
        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        // Allocate space for vertices
        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);

        // Buffer types: Array                    size in bytes               vertices can change
        glBufferData(GL_ARRAY_BUFFER, (long) vertices.length * Float.BYTES, GL_DYNAMIC_DRAW);

        // Create and upload indices buffer
        // e.g. 2, 1, 3,
        //      3, 0, 1
        int eboID = glGenBuffers();
        int[] indices = generateIndices();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);

        initAttribPointers();
    }

    protected abstract void initAttribPointers();

    public void addSprite(SpriteRenderer spr) {
        // Add sprite to list
        this.sprites.add(spr);

        if (spr.getTexture() != null)
            if ((spr.texId = textures.indexOf(spr.getTexture()) + 1) == 0) {
                textures.add(spr.getTexture());
                spr.texId = textures.size() - 1;
            }

        // Add properties to local vertices array
        loadVertexProperties(sprites.size() - 1);
    }

    protected abstract void loadVertexProperties(int index);

    public void render() {
        // Update vertices
        for (int i = 0; i < this.sprites.size(); i++) {
            if(sprites.get(i).isChanged())
                loadVertexProperties(i);
        }

        // For now we will rebuffer all data every frame
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferSubData(GL_ARRAY_BUFFER, 0, vertices);

        // Use shader
        shader.use();

        // Upload matrices and uniforms
        shader.uploadMat4f("uProjection", Window.getScene().camera().getProjectionMatrix());
        shader.uploadMat4f("uView", Window.getScene().camera().getViewMatrix());

        uploadUniforms();

        for (int i = 0; i < textures.size(); i++) {
            glActiveTexture(GL_TEXTURE0 + i + 1);
            textures.get(i).bind();
        }

        shader.uploadIntArray("uTextures", texSlots);

        glBindVertexArray(vaoID);

        enableVertexAttribs();

        glDrawElements(GL_TRIANGLES, sprites.size() * 6, GL_UNSIGNED_INT, 0);

        disableVertexAttribs();

        glBindVertexArray(0);

        for (int i = 0; i < textures.size(); i++) {
            textures.get(i).unbind();
        }

        shader.detach();
    }

    public boolean hasRoom() {
        return this.sprites.size() < MAX_BATCH_SIZE;
    }

    public boolean hasTextureRoom() {
        return textures.size() < 7;
    }

    public boolean hasTexture(Texture tex) {
        return textures.contains(tex);
    }

    public boolean usesShader(Shader shader) {
        return this.shader == shader;
    }

    protected abstract void enableVertexAttribs() ;

    protected abstract void disableVertexAttribs();

    protected void uploadUniforms() {}

    public boolean removeSprite(SpriteRenderer spr) {
        if(this.sprites.remove(spr)) {
            for (int i = 0; i < sprites.size(); i++) {
                loadVertexProperties(i);
            }
            return true;
        }

        return false;
    }

    private int[] generateIndices() {
        // 6 indices per quad (3 per triangle)
        int[] elements = new int[6 * MAX_BATCH_SIZE];

        // elements array is mutable
        for(int i=0; i < MAX_BATCH_SIZE; i++)
            loadElementIndices(elements, i);

        return elements;
    }

    private void loadElementIndices(int[] elements, int index) {
        int offsetArrayIndex = 6 * index;
        int offset = 4 * index;

        // Element 1                  Element 2
        // 3, 2, 0, 0, 2, 1           7, 6, 4, 4, 6, 5

        // Triangle 1
        elements[offsetArrayIndex] = offset + 3;
        elements[offsetArrayIndex + 1] = offset + 2;
        elements[offsetArrayIndex + 2] = offset;

        // Triangle 2
        elements[offsetArrayIndex + 3] = offset;
        elements[offsetArrayIndex + 4] = offset + 2;
        elements[offsetArrayIndex + 5] = offset + 1;
    }

}
