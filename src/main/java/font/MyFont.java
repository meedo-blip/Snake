package font;


import com.mlomb.freetypejni.Bitmap;
import com.mlomb.freetypejni.Face;
import com.mlomb.freetypejni.FreeType;
import com.mlomb.freetypejni.Library;
import util.Utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.Map;

import static com.mlomb.freetypejni.FreeType.*;
import static com.mlomb.freetypejni.FreeTypeConstants.FT_LOAD_RENDER;

public class MyFont {
    public final String noExtensionPath;

    public final int texId;
    public Map<Character, float[]> charTexCoords;
    public final JsonHandler jsonHandler;

    private static final String absFontsPath = new File("assets\\fonts").getAbsolutePath();

    public MyFont(String ttfPath, int fontsize) {
        String absPath = new File(ttfPath).getAbsolutePath();

        String ttfName = ttfPath.substring(ttfPath.lastIndexOf('\\') + 1);
        ttfName = ttfName.substring(0, ttfName.lastIndexOf('.'));

        System.out.println("ttfName: " + ttfName);

        jsonHandler = new JsonHandler();
        noExtensionPath = "assets\\fonts\\" + ttfName;

        Path path = Path.of(noExtensionPath + ".ttf");
        if(!absPath.substring(0,absPath.lastIndexOf('\\' )).equals(absFontsPath)
                && !Files.exists(path, LinkOption.NOFOLLOW_LINKS)) {
            try {
                Files.copy(Path.of(absPath), path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        if(new File(noExtensionPath + ".json").exists()) {
            charTexCoords = jsonHandler.readCharInfo(noExtensionPath);
            texId = Utils.createTexture(noExtensionPath + ".png", false);
        } else {
            texId = genBitmap(fontsize);
        }
    }


    public int genBitmap(int fontsize) {
        int upscaleResolution = 64;

        System.out.println("\nHi " + 1);

        Library library = FreeType.newLibrary();

        String filepath = noExtensionPath + ".ttf";
        Face font = library.newFace(filepath, 0);

        System.out.println("\nHi " + 2);

        FT_Set_Pixel_Sizes(font.getPointer(), 0, upscaleResolution);

        System.out.println("\nHi " + 3);

        for (int i = 65; i < 75; i++) {

            System.out.println("yo " + i);

            if (FT_Load_Char(font.getPointer(), (char) i, FT_LOAD_RENDER)) {
                System.out.println("FreeType could not generate character " + (char) i);
                continue;
            }

            System.out.println("yo " + i);

            Bitmap bitmap = font.getGlyphSlot().getBitmap();

            if(bitmap.getWidth() < 1 || bitmap.getRows() < 1)
                continue;

            byte[] buf = new byte[bitmap.getWidth() * bitmap.getRows()];

            bitmap.getBuffer().get(buf);
        }

        System.out.println("\nHi " + 4);

        FT_Done_FreeType(library.getPointer());
        FT_Done_Face(font.getPointer());

        return 0;
    }

    public float[] getCharTexCoords(int codepoint) {
        return charTexCoords.get((char)codepoint);
    }
}
