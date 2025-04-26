package game;

import components.Sprite;
import components.StaticBlock;
import components.TextNode;
import jade.*;
import org.joml.Vector2f;
import org.joml.Vector4f;
import util.AssetPool;
import util.Time;
import util.Utils;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_R;

public class GameScene extends Scene {
    public GameScene() {
        System.out.println("Inside level scene");
    }

    public static final int boardLen = 9; // board pixels in a row or column

    public static final float boardWidth = 500f,
            boardPadding = 50f,
            pixelWidth = boardWidth / (boardLen - 1);

    private Snake snake;
    public Sprite board;
    private TextNode fpsDisplay;

    @Override
    public void init() {
        this.camera = new Camera(new Vector2f(-1, 1));

        fixedDT = 0.05f;

        AssetPool.addBatchGetterToShader(GameConsts.SNAKE_SH, SnakeBatch::new);

        board = addSpriteObjectToScene(new StaticBlock(Constants.DEFAULT_SH, Utils.hexToRgba("9b111e")).setTransform(
                new Transform(new Vector2f(), -1, new Vector2f(boardWidth + boardPadding * 2,boardWidth + boardPadding * 2))
        ));

        for (int i = 0; i < boardLen * boardLen; i++) {
            Sprite sprite = addSpriteObjectToScene(new StaticBlock(Constants.DEFAULT_SH, (i & 1) == 1 ? GameConsts.SAND_COLOR : GameConsts.GREEN_SHADE).setTransform(
                    new Transform(new Vector2f(((i - ((i / boardLen) * boardLen)) * pixelWidth) - (boardWidth / 2), ((i / boardLen) * pixelWidth) - ((float) boardWidth / 2)), -1, new Vector2f(pixelWidth, pixelWidth))
            ), board);
        }

        snake = new Snake();

        System.out.println("Init");
        //addSpriteObjectToScene(new StaticBlock(Constants.DEFAULT_SH, AssetPool.getTexture("assets/fonts/arialbd.png")).setTransform(new Transform(new Vector2f(), -1, new Vector2f(1000, 1000))));
        fpsDisplay =(TextNode) makeText(Constants.ARIAL_FONT, "FPS: 000", 500, -500, 64);



    }

    @Override
    public void start() {
        super.start();
        snake.start();

        Time.setInterval(1000, () -> fpsDisplay.changeText("FPS: " + Math.round((1f / Time.dt) * 10000) / 10000));
    }

    @Override
    public void update(float dt) {

        if (MouseListener.mouseButtonDown(0)) {
            camera.position.x += MouseListener.getDx() / camera.getProjection_w();
            camera.position.y -= MouseListener.getDy() / camera.getProjection_h();
        }

        if(KeyListener.isKeyPressed(GLFW_KEY_R))
            removeSprite(board);

        snake.update();

        System.out.println("update");

        super.update(dt);

    }
}
