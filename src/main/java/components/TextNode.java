package components;

import font.MyFont;
import jade.Constants;
import jade.Transform;
import jade.Window;
import org.joml.Vector2f;

public class TextNode extends QuadSprite {

    public String text;
    public MyFont font;
    private int fontsize;

    public TextNode(MyFont font, String text, int fontsize, Transform transform)
    {
        this.text = text;
        this.font = font;
        this.fontsize = fontsize;
        this.transform = transform;
        color = Constants.INVISIBLE;
    }

    private void makeFontSprites() {
        int width = text.length();
        int height = Math.ceilDiv(text.length(), width);

        float halfW = (float) text.length() / 2;

        for (int i = 0; i < text.length(); i++) {
            Window.getScene().addSpriteObjectToScene(
                    new FontSprite(font.texId, font.getCharTexCoords(text.charAt(i)), new Transform(new Vector2f(((i % width) - halfW) * fontsize, (i / width) * fontsize), -1,
                            new Vector2f(fontsize, fontsize))), this
            );
        }
    }

    public void changeText(String text) {
        this.text = text;
        Window.getScene().removeChildrenOf(this);
        makeFontSprites();
    }

    @Override
    public void start() {
        super.start();
        makeFontSprites();
    }

    @Override
    public void update(float dt) {

    }
}
