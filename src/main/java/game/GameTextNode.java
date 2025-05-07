package game;

import components.TextNode;
import font.MyFont;
import jade.Transform;

public class GameTextNode extends TextNode {

    public GameTextNode(MyFont font, String text, int fontsize, Transform transform) {
        super(font, text, fontsize, transform);
    }

    @Override
    public void update(float dt) {
        if(parent != null) {
            if(parent.isChanged()) {
                transform.position.x = (pForm.position.x * GameScene.pixelWidth) + offsetForm.position.x;
                transform.position.y = (pForm.position.y * GameScene.pixelWidth) + offsetForm.position.y;
            }
        }
    }
}
