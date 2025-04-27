package game;

import jade.Sound;
import org.joml.Vector4f;
import renderer.Shader;
import util.AssetPool;
import util.Utils;


public class GameConsts {

    public static final Shader
            GAME_SH = AssetPool.getShader("assets/shaders/game.glsl");

    public static final Sound
            FOOD_CRUNCH_SN = AssetPool.getSound("assets/sounds/food-crunch.ogg", false);

    public static final Vector4f
        SAND_COLOR = Utils.hexToRgba("fee8b9"),
        GREEN_SHADE = Utils.hexToRgba("6dad4f");

    // left bit: 1 : horizontal or 0 : vertical
    // right bit: 1 : +ve or 0 : -ve
    public static final byte
        DOWN =  0, // 00
        UP =    1, // 01
        LEFT =  2, // 10
        RIGHT = 3; // 11
}
