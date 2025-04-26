package game;

import jade.Transform;
import org.joml.Vector4f;

import static util.Utils.DEGREES_TO_RADIANS;

public class SnakeEnd extends SnakePart {

    public SnakeJunction rotating = null;

    // offset x amd y for rotation
    float ox, oy;

    public SnakeEnd(Vector4f color) {
        this.color = color;
        shape = 0;
        init();
    }

    @Override
    public void start() {
        super.start();
        orbit = transform.rotate;
    }

    @Override
    public void update(float dt) {
        if (rotating == null) {
            transform.position.x += Snake.STEP * ((direction & 2) >> 1) * (((direction & 1) << 1) - 1);
            transform.position.y -= Snake.STEP * (-((direction & 2) >> 1) + 1) * (((direction & 1) << 1) - 1);
        } else {
            rotate();
        }
    }


    public void rotate() {
        transform.rotate += rotating.angleStep;
        orbit += rotating.angleStep;

        float sin = (float) Math.sin(transform.rotate);
        float cos = (float) Math.cos(transform.rotate);

        transform.position.x = ((ox * cos) - (oy * sin)) + transform.centerR.x();

        transform.position.y = ((sin * ox) + (cos * oy)) + transform.centerR.y();
    }

    // decimal of x and of y
    public float getDecX() {
        return (float) (transform.position.x() - Math.floor(transform.position.x()) + 0.5f);
    }

    public float getDecY() {
        return (float) (1f - (transform.position.y() - Math.floor(transform.position.y())));
    }


}
