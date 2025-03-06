package util;

import components.FontRenderer;
import org.lwjgl.glfw.GLFW;
import renderer.*;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class AssetPool {
    private static final Map<String, Shader> shaders = new HashMap<>();
    private static final Map<String, Texture> textures = new HashMap<>();
    private static final Map<String, FontRenderer> fonts = new HashMap<>();

    private static final Map<String, BatchGen> shaderToBatch = new HashMap<>();

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

    public static Texture getTexture(String resourceName) {
        File file = new File(resourceName);
        if (textures.containsKey(file.getAbsolutePath())) {
            return textures.get(file.getAbsolutePath());
        } else {
            Texture texture = new Texture(resourceName);
            textures.put(file.getAbsolutePath(), texture);
            return texture;
        }
    }

    public static RenderBatch getBatchOf(Shader shader) {
        return shaderToBatch.get(shader.getPath()).genBatch();
    }

    public static void addBatchGetterToShader(Shader shader, BatchGen batchGetter) {
        shaderToBatch.put(shader.getPath(), batchGetter);
    }
}
