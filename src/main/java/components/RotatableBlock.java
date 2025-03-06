package components;

import static util.Utils.DEGREES_TO_RADIANS;

import org.joml.Matrix2f;
import org.joml.Vector2f;
import org.joml.Vector4f;
import renderer.Shader;
import renderer.Texture;

public class RotatableBlock extends SpriteRenderer {

    private final Matrix2f destMat = new Matrix2f();

    public RotatableBlock(Shader shader, Vector4f color) {
        this.shader = shader;
        this.color = color;
        this.texture = null;
    }

    public RotatableBlock(Shader shader, Texture texture) {
        this.shader = shader;
        this.texture = texture;
        this.color = new Vector4f(1,1,1,1);
    }

    @Override
    public void start() {
        transform = gameObject.transform;
        offsetPos = new Vector2f(transform.position);

        System.out.println(gameObject.getName());

        if (gameObject.parent != null) {
            pForm = gameObject.parent.transform;
        } else {
            pForm = null;
        }
    }

    @Override
    public void update(float dt) {
    }

    protected void rotate() {
        float angleRadians = transform.rotate * DEGREES_TO_RADIANS;

        float sine = (float) Math.sin(angleRadians);
        float cosine = (float) Math.cos(angleRadians);

        destMat.set(cosine, -sine, sine, cosine);

        transform.position = transform.position.sub(transform.centerR).mul(destMat).add(transform.centerR);
    }
}
