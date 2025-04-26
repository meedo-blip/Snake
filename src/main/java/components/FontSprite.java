package components;

import font.MyFont;
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

    public void start(){
        transform.position.add(pForm.position);
    }

    @Override
    public void update(float dt) {
        changed = false;
    }
}
