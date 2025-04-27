package game;

import components.Sprite;
import components.StaticBlock;
import components.TextNode;
import font.MyFont;
import jade.*;
import org.joml.Vector2f;
import org.joml.Vector4f;
import util.AssetPool;
import util.Utils;

import java.io.File;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_R;

public class GameScene extends Scene {
    public GameBlock apple;

    public GameScene() {
        System.out.println("Inside level scene");
    }

    public static final int boardLen = 9; // board pixels in a row or column

    public static final float boardWidth = 500f,
            boardPadding = 50f,
            pixelWidth = boardWidth / (boardLen - 1);

    private Snake snake;
    public Sprite board;
    public int score = 0;

    private TextNode fpsDisplay;
    private TextNode scoreDisplay;

    @Override
    public TextNode makeText(MyFont font, String text, float x, float y, int fontsize, Sprite grandParent) {

        int width = text.length();
        int height = Math.ceilDiv(text.length(), width);

        float halfW = ((float) width) / 2;

        return (TextNode) addSpriteObjectToScene(
                new GameTextNode(font, text, fontsize, new Transform(new Vector2f(x * GameScene.pixelWidth,y * GameScene.pixelWidth), -1, new Vector2f(fontsize * width, fontsize * height))), grandParent);
    }

    @Override
    public void init() {
        this.camera = new Camera(new Vector2f(-1, 1));

        fixedDT = 0.05f;

        AssetPool.addBatchGetterToShader(GameConsts.GAME_SH, GameBatch::new);

        board = addSpriteObjectToScene(new StaticBlock(Constants.DEFAULT_SH, Utils.hexToRgba("9b111e")).setTransform(
                new Transform(new Vector2f(), -1, new Vector2f(boardWidth + boardPadding * 2,boardWidth + boardPadding * 2))
        ));

        for (int i = 0; i < boardLen * boardLen; i++) {
            Sprite sprite = addSpriteObjectToScene(new StaticBlock(Constants.DEFAULT_SH, (i & 1) == 1 ? GameConsts.SAND_COLOR : GameConsts.GREEN_SHADE).setTransform(
                    new Transform(new Vector2f(((i - ((i / boardLen) * boardLen)) * pixelWidth) - (boardWidth / 2), ((i / boardLen) * pixelWidth) - ((float) boardWidth / 2)), -1, new Vector2f(pixelWidth, pixelWidth))
            ).setName("px" + i), board);
        }

        snake = new Snake();

        System.out.println("Init");

        scoreDisplay = (TextNode) makeText(Constants.ARIAL_FONT, "Score: " + score, -5, -5.5f, 64);
        fpsDisplay = (TextNode) makeText(Constants.ARIAL_FONT, "FPS: 000", 5, -5.5f, 64);
    }

    @Override
    public void start() {
        super.start();
        snake.start();
    }

    @Override
    public void update(float dt) {

        if (MouseListener.mouseButtonDown(0)) {
            camera.position.x += MouseListener.getDx() / camera.getProjection_w();
            camera.position.y -= MouseListener.getDy() / camera.getProjection_h();
        }

        if(KeyListener.isKeyPressed(GLFW_KEY_R))
            if(board != null) {
                removeSprite(board);
                board = null;
            }


        fpsDisplay.changeText("FPS: " + (int)(1f / dt));

        snake.update();

        super.update(dt);
    }

    void makeApple() {
        int x = (int)(Utils.getRandomInteger(0, boardLen + 1) - ((float) boardLen / 2));
        int y = (int)(Utils.getRandomInteger(0, boardLen + 1) - ((float) boardLen / 2));

        apple = (GameBlock) Window.getScene().addSpriteObjectToScene(
                new GameBlock(1, new Vector4f(1, 0, 0, 1))
                        .setTransform(new Transform(new Vector2f(x,y), -1, new Vector2f(GameScene.pixelWidth / 2, GameScene.pixelWidth / 2)))
        );

    }
    void eatApple() {
        removeSprite(apple);
        score++;

        scoreDisplay.changeText("Score: " + score);
        GameConsts.FOOD_CRUNCH_SN.play();

        makeApple();
    }
}
