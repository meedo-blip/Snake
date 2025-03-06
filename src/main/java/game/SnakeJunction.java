package game;

import org.jetbrains.annotations.Range;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector4f;

public class SnakeJunction extends SnakePart {
//                                    shape: from 3 to 6
    private int snakeTick = 0;
    public final int clockwise;

    public SnakeJunction(byte newDirection, byte direction, Vector4f color, int shape) {
        this.color = color;
        this.direction = direction;
        this.shape = shape;

        init();

        clockwise = isClockWise(newDirection, direction) ? 1 : 0;
    }

    @Override
    public void start() {
        super.start();
        transform.centerR = new Vector2f();
    }

    @Override
    public void update(float dt) {
        if(snakeTick < 25)
            snakeTick++;
    }

    public int getSnakeTick() { return this.snakeTick; }

    private static boolean isClockWise(byte newDirect, byte direct)
    { return newDirect - direct == 2 || newDirect - direct == -1 || newDirect - direct == -3; // clockwise constants
    }
}
