package components;

import org.joml.Vector2f;
import renderer.Shader;
import renderer.Texture;
import org.joml.Vector4f;

public class Block extends SpriteRenderer {

    public Block(Shader shader, Vector4f color) {
        this.shader = shader;
        this.color = color;
        this.texture = null;
    }

    public Block(Shader shader, Texture texture) {
        this.shader = shader;
        this.texture = texture;
        this.color = new Vector4f(1,1,1,1);
    }

    @Override
    public void start() {
        transform = gameObject.transform;
        offsetPos = new Vector2f(transform.position);

        if (gameObject.parent != null) {
            pForm = gameObject.parent.transform;
        } else {
            pForm = null;
        }

        transform.position.x = (pForm != null ? pForm.position.x : 0f) + offsetPos.x;
        transform.position.y = (pForm != null ? pForm.position.y : 0f) + offsetPos.y;

        changed = false;
    }

    @Override
    public void update(float dt) {
    }
}
