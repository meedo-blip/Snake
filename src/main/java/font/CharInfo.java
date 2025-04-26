package font;

import org.joml.Vector2f;

public class CharInfo {
    public int sourceX, sourceY;
    public int width, height;

    public Vector2f[] texCoords = new Vector2f[4];

    public CharInfo(int x, int y, int width, int height) {
        sourceX = x;
        sourceY = y;
        this.width = width;
        this.height = height;
    }

    public void calculateTexCoords(int fontWidth, int fontHeight) {
        float x0 = (float) sourceX / (float) fontWidth;
        float x1 = (float) (sourceX + width) / (float) fontWidth;
        float y0 = (float) (sourceY - height) / (float) fontHeight;
        float y1 = (float) (sourceY) / (float) fontHeight;

        texCoords[0] = new Vector2f(x1, y1);
        texCoords[1] = new Vector2f(x0, y1);
        texCoords[2] = new Vector2f(x0, y0);
        texCoords[3] = new Vector2f(x1, y0);
    }

}
