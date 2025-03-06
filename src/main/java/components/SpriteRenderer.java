package components;

import jade.Component;
import jade.Constants;
import jade.Transform;
import org.jetbrains.annotations.Range;
import renderer.Shader;
import renderer.Texture;
import org.joml.*;

import java.lang.Math;

public abstract class SpriteRenderer extends Component {

    @Range(from = 0,to = 7)
    public int texId = 0;

    protected Vector4f color;
    protected Texture texture = null;
    protected Shader shader;
    protected boolean changed = true;

    public Transform transform, pForm; // parent transform
    protected Vector2f offsetPos;

    public Vector4f getColor() {
        return color;
    }

    public Texture getTexture() {
        return texture;
    }

    public Vector2f[] getTexCoords() {

        // The texCoords must follow this order for OpenGl to load the texture properly
        Vector2f[] texCoords = {
                new Vector2f(1f,1f), // 1. Top right
                new Vector2f(0f,1f), // 2. Bottom right
                new Vector2f(0f,0f), // 3. Bottom left
                new Vector2f(1f,0f)  // 4. Top left
        };

        return texCoords;

    }

    public int getTexId() {
        return texId;
    }

    public Shader getShader() {
        return shader;
    }

    public boolean isChanged() {
        return changed;
    }
}
