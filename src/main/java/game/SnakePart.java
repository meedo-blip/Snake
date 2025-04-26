package game;

import components.QuadSprite;
import jade.Transform;

public abstract class SnakePart extends QuadSprite {
    protected int shape;
    protected byte direction = GameConsts.RIGHT;

    float orbit;

    protected void init() {
        shader = GameConsts.SNAKE_SH;
    }

    @Override
    public void start() {
        super.start();
        System.out.println(getName());
    }

    public int getShape() {
        return this.shape;
    }
}
