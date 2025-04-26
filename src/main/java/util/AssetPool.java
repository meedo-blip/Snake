package util;

import font.MyFont;
import renderer.*;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class AssetPool {
    private static final Map<String, Shader> shaders = new HashMap<>();
    private static final Map<String, Integer> textures = new HashMap<>();
    private static final Map<String, MyFont> fonts = new HashMap<>();

    private static final Map<Integer, BatchGen> shaderToBatch = new HashMap<>();

    public static Shader getShader(String filename) {
        File file = new File(filename);

        if (shaders.containsKey(file.getAbsolutePath())) {
            return shaders.get(file.getAbsolutePath());
        } else {
            Shader shader = new Shader(filename);
            shader.compile();
            shaders.put(file.getAbsolutePath(), shader);
            return shader;
        }
    }

    public static int getTexture(String resourceName) {
        File file = new File(resourceName);
        if (textures.containsKey(file.getAbsolutePath())) {
            return textures.get(file.getAbsolutePath());
        } else {
            int texture = Utils.createTexture(resourceName);
            textures.put(file.getAbsolutePath(), texture);
            return texture;
        }
    }

    public static MyFont getFont(String resourceName) {
        File file = new File(resourceName);
        if (fonts.containsKey(file.getAbsolutePath())) {
            return fonts.get(file.getAbsolutePath());
        } else {
            MyFont font = new MyFont(resourceName);
            fonts.put(file.getAbsolutePath(), font);
            return font;
        }
    }


    public static RenderBatch getBatchOf(Shader shader) {
        return shaderToBatch.getOrDefault(shader.getId(), (BatchGen) () -> new DefaultBatch(shader)).genBatch();
    }

    public static void addBatchGetterToShader(Shader shader, BatchGen batchGetter) {
        shaderToBatch.put(shader.getId(), batchGetter);
    }
}
