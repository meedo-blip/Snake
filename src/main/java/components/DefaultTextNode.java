package components;

import font.MyFont;
import jade.Transform;

public class DefaultTextNode extends TextNode {

    public DefaultTextNode(MyFont font, String text, int fontsize, Transform transform) {
        super(font, text, fontsize, transform);
    }

    @Override
    public void update(float dt) {
        if(parent != null) {
            if(parent.changed) {
                transform.position.x = pForm.position.x + offsetForm.position.x;
                transform.position.y = pForm.position.y + offsetForm.position.y;
            }
        }
    }
}
