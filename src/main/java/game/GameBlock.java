package game;

import components.QuadSprite;
import jade.Window;
import org.joml.Vector4f;

public class GameBlock extends GameSprite {

    public GameBlock(int shape, Vector4f color) {
        this.shape = shape;
        this.color = color;
        init();
    }

    @Override
    public void update(float dt) {
        if(Window.getScene().getTicks() == 1)
            changed = false;
    }
}
