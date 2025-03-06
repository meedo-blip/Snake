package game;

import components.Block;
import components.RotatableBlock;
import jade.*;
import org.joml.Vector2f;
import org.joml.Vector4f;
import util.AssetPool;

public class GameScene extends Scene {
    public GameScene() {
        System.out.println("Inside level scene");
    }


    public static final byte boardLen = 9; // board pixels in a row or column

    public static final float boardWidth = 500f,
            boardPadding = 50f,
            pixelWidth = boardWidth / boardLen;

    private Snake snake;

    @Override
    public void init() {
        this.camera = new Camera(new Vector2f(-1, 1));

        fixedDT = 0.05f;

        AssetPool.addBatchGetterToShader(GameConsts.SNAKE_SH, () -> new SnakeBatch(snake));

        GameObject board = new GameObject("Board", new Transform(new Vector2f(), -10f, new Vector2f(boardWidth + boardPadding * 2, boardWidth + boardPadding * 2)));
        board.addComponent(new Block(Constants.DEFAULT_SH, new Vector4f(0.5f, 0.1f, 0.1f, 0f)));
        addGameObjectToScene(board);

        snake = new Snake(new Vector4f(0.5f,0.3f,0.6f, 0));

        for (int i = 0; i < boardLen; i++) {
            for (int j = 0; j < boardLen; j++) {
                GameObject pixel = new GameObject("Px", board, new Transform(new Vector2f((i + 0.5f) * pixelWidth - boardWidth / 2 , (j + 0.5f) * pixelWidth - boardWidth / 2),
                        -5f, new Vector2f(pixelWidth, pixelWidth)));

                pixel.addComponent(new Block(Constants.DEFAULT_SH, ((i + j) & 1) == 1 ? GameConsts.GREEN_SHADE : GameConsts.SAND_COLOR));
                addGameObjectToScene(pixel);
            }
        }
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

        for(GameObject go : gameObjects) {
            go.update(dt);
        }

        snake.update();

        this.renderer.render();

    }
}
