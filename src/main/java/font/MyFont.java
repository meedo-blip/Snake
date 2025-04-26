package font;


import com.mlomb.freetypejni.Bitmap;
import com.mlomb.freetypejni.Face;
import com.mlomb.freetypejni.FreeType;
import com.mlomb.freetypejni.Library;
import util.AssetPool;
import util.Utils;

import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mlomb.freetypejni.FreeType.*;
import static com.mlomb.freetypejni.FreeTypeConstants.FT_LOAD_RENDER;

public class MyFont {
    public final String noExtensionPath;

    public final int texId;
    public Map<Character, float[]> charTexCoords;
    public final JsonHandler jsonHandler;

    public MyFont(String ttfPath) {
        jsonHandler = new JsonHandler();
        noExtensionPath = ttfPath.substring(0,ttfPath.length() - 4);

        System.out.println(noExtensionPath);

        if(Files.exists(Path.of(noExtensionPath + ".json"))) {
            charTexCoords = jsonHandler.readCharInfo(noExtensionPath);
            texId = AssetPool.getTexture(noExtensionPath + ".png");
        } else {
            texId = genBitmap(ttfPath);
        }
    }


    public int genBitmap(String filepath) {
        int upscaleResolution = 64;

        System.out.println("\nHi " + 1);

        Library library = FreeType.newLibrary();

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
