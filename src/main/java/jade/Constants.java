package jade;

import renderer.Shader;
import util.AssetPool;

public class Constants {
    public static final String shadersPath = "assets/shaders/";

    public static Shader DEFAULT_SH = AssetPool.getShader(shadersPath + "default.glsl");
    public static final int MAX_BATCH_SIZE = 1000;

}
