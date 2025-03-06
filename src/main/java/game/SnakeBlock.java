package game;

import org.joml.Vector4f;

public class SnakeBlock extends SnakePart {

    public SnakeBlock(Snake snake, Vector4f color, byte direction) {
        this.color = color;
        this.snake = snake;
        shape = 2;
        this.direction = direction;
        init();
    }

    @Override
    public void update(float dt) {

    }
}
