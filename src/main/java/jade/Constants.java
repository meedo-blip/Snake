package jade;

import font.MyFont;
import org.joml.Vector4f;
import renderer.Shader;
import util.AssetPool;

public class Constants {
    public static final int MAX_BATCH_SIZE = 1000;

    public static Shader DEFAULT_SH = AssetPool.getShader( "assets/shaders/default.glsl"),
                        FONT_SH = AssetPool.getShader("assets/shaders/sdf.glsl"),
                        CIRCLE_SH = AssetPool.getShader("assets/shaders/circle.glsl");

    public static final MyFont ARIAL_FONT = new MyFont("assets\\fonts\\arialbd.ttf", 64);

    public static final Vector4f BLACK = new Vector4f(0,0,0,1);
    public static final Vector4f WHITE = new Vector4f(1,1,1,1);
    public static final Vector4f INVISIBLE = new Vector4f(1,1,1,0);

}
