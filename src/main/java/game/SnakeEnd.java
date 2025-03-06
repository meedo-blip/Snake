package game;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.joml.Vector4i;

import static util.Utils.DEGREES_TO_RADIANS;

public class SnakeEnd extends SnakePart {

    public SnakeJunction rotating = null;
    public Vector2f uEnd;

    private static float m;

    public SnakeEnd(Vector4f color) {
        this.color = color;
        this.uEnd = new Vector2f(0,0);
        shape = 0;
        init();
    }

    @Override
    public void update(float dt) {
        if (rotating == null) {
            transform.position.x += Snake.STEP * ((direction & 2) >> 1) * (((direction & 1) << 1) - 1);
            transform.position.y -= Snake.STEP * (-((direction & 2) >> 1) + 1) * (((direction & 1) << 1) - 1);
        } else {
            rotate();
        }

        uEnd.x = transform.position.x();
        uEnd.y = transform.position.y();
    }


    public void rotate() {
        transform.rotate += ((rotating.clockwise << 1) - 1) * Snake.ANGLE_STEP * DEGREES_TO_RADIANS;

        float sin = (float) Math.sin(transform.rotate);
        float cos = (float) Math.cos(transform.rotate);

        float x = transform.position.x - transform.centerR.x;
        float y = transform.position.y - transform.centerR.y;

        transform.position.x = (x * cos) - (y * sin) + transform.centerR.x();

        transform.position.y = (sin * x) + (cos * y) + transform.centerR.y();
    }

    // decimal of x and of y
    public float getDecX() {
        return (float) (transform.position.x() - Math.floor(transform.position.x()) + 0.5f);
    }

    public float getDecY() {
        return (float) (1f - (transform.position.y() - Math.floor(transform.position.y())));
    }


}
