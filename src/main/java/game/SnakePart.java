package game;

import components.SpriteRenderer;

public abstract class SnakePart extends SpriteRenderer{
    protected int shape;
    protected byte direction = GameConsts.RIGHT;
    protected Snake snake;

    protected void init() {
        shader = GameConsts.SNAKE_SH;
    }

    @Override
    public void start() {
        System.out.println(gameObject.getName());
        transform = gameObject.transform;
    }

    public int getShape() {
        return this.shape;
    }
}
