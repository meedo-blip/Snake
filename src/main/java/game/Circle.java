package game;

import org.joml.Vector4f;

public class Circle extends SnakePart {

    public Circle(Vector4f color) {
        this.shape = 1;
        this.color = color;
        init();
    }
    @Override
    public void update(float dt) {

    }
}
