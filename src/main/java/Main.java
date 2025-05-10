import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import game.GameScene;
import jade.Window;


public class Main {
    public static void main(String[] args) {
    	extractAndLoadNatives();
        Window.get("Snake").run(new GameScene());
    }
    
    public static void extractAndLoadNatives() {
    try {
        // Create temp directory
        Path tempDir = Files.createTempDirectory("lwjgl-natives");
        tempDir.toFile().deleteOnExit();

        // Get list of native files in the jar
        String[] natives = {
            "liblwjgl.so",
            "liblwjgl_opengl.so",
            "liblwjgl_glfw.so"
            // Add any others you include
        };

        for (String lib : natives) {
            try (InputStream in = Main.class.getResourceAsStream("/natives/" + lib)) {
                if (in == null) continue;
                Path tempFile = tempDir.resolve(lib);
                Files.copy(in, tempFile, StandardCopyOption.REPLACE_EXISTING);
                tempFile.toFile().deleteOnExit();
            }
        }

        // Point LWJGL to the extracted folder
        System.setProperty("org.lwjgl.librarypath", tempDir.toAbsolutePath().toString());

    } catch (IOException e) {
        e.printStackTrace();
        System.exit(1);
    }
}

}
