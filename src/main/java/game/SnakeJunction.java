package game;

import org.joml.Vector2f;
import org.joml.Vector4f;

public class SnakeJunction extends GameSprite {
//                                    shape: from 3 to 6
    int snakeTick = 0;

    public final double angleStep;
    public final byte newDirection;

    public SnakeJunction(byte newDirection, byte direction, Vector4f color, int shape) {
        this.color = color;
        this.direction = direction;
        this.shape = shape;
        this.newDirection = newDirection;

        init();

        angleStep = isClockWise(newDirection, direction) ?
                Snake.ANGLE_STEP : -Snake.ANGLE_STEP;
    }

    @Override
    public void start() {
        super.start();
        transform.centerR = new Vector2f();
    }

    @Override
    public void update(float dt) {
        snakeTick++;
    }

    public int getSnakeTick() { return this.snakeTick; }

    private static boolean isClockWise(byte newDirect, byte direct)
    {
        return newDirect - direct == 2
                || newDirect - direct == -1
                || newDirect - direct == -3; // clockwise constants
    }
}
