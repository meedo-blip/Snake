package components;

import org.joml.Vector2f;
import renderer.Shader;
import org.joml.Vector4f;

public class StaticBlock extends QuadSprite {

    public StaticBlock(Shader shader, Vector4f color) {
        this.shader = shader;
        this.color = color;
        this.texId = -1;
    }

    public StaticBlock(Shader shader, int texture) {
        this.shader = shader;
        this.texId = texture;
        this.color = new Vector4f(1,0,1,1);
    }

    @Override
    public void start() {
        super.start();
        offsetPos = new Vector2f(transform.position);

        transform.position.x = (pForm != null ? pForm.position.x : 0f) + offsetPos.x;
        transform.position.y = (pForm != null ? pForm.position.y : 0f) + offsetPos.y;

        changed = true;
    }

    @Override
    public void update(float dt) {

    }
}
