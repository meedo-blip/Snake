package game;

import components.QuadSprite;

public abstract class GameSprite extends QuadSprite {
    protected int shape;
    protected byte direction = GameConsts.RIGHT;

    float orbit;

    protected void init() {
        shader = GameConsts.GAME_SH;
    }

    @Override
    public void start() {
        super.start();
        System.out.println(getName());
        texId = -1;
    }

    public int getShape() {
        return this.shape;
    }
}
