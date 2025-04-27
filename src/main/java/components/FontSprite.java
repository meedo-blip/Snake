package components;

import jade.Constants;
import jade.Transform;

public class FontSprite extends QuadSprite {

    public FontSprite(int texId, float[] texCoords, Transform transform) {
        this.shader = Constants.FONT_SH;
        this.texId = texId;
        this.transform = transform;
        this.color = Constants.WHITE;
        this.texCoords = texCoords;
    }

    public void start() {
        super.start();
    }

    @Override
    public void update(float dt) {
        transform.position.x = pForm.position.x + offsetForm.position.x;
        transform.position.y = pForm.position.y + offsetForm.position.y;
    }
}
