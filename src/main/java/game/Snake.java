package game;

import jade.*;
import org.joml.Vector2f;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;

public class Snake {
    public Vector4f color;
    public static final float
            STEP =  1f / 25, // step per pixel width
            ANGLE_STEP = 90f / 25,
            SNAKE_WIDTH = 0.8f;

    public byte newDirection = GameConsts.RIGHT;

    SnakeEnd snakeHead;
    SnakeEnd snakeBack;
    private float lastHeadX = 0f, lastHeadY = 0f;

    private Scene scene;

    private final List<SnakeBlock> snakeBlocks = new ArrayList<>(3);
    private final List<SnakeJunction> junctions = new ArrayList<>(1);
    private Vector2f headPos, backPos;
    private SnakePart point;

    public Snake() {
        color = new Vector4f(0.5f, 1f, 0.5f, 1f);
    }

    public Snake(Vector4f color) {
        this.color = color;
        init();
    }

    public void init() {

        scene = Window.getScene();

        this.snakeHead = (SnakeEnd) genSnakePart(0,

                -2, 0, 0.5f, SNAKE_WIDTH,

                GameConsts.RIGHT, 0f);


        this.snakeBack = (SnakeEnd) genSnakePart(0,

                -4, 0, 0.5f, SNAKE_WIDTH,

                GameConsts.RIGHT, 180f);
    }

    public void start() {
        headPos = snakeHead.transform.position;
        backPos = snakeBack.transform.position;
    }

    public Vector2f headPos() {
        return headPos;
    }

    public Vector2f backPos() {
        return backPos;
    }

    public void update() {

        if (KeyListener.isKeyPressed(GLFW_KEY_LEFT))
            newDirection = GameConsts.LEFT;
        else if (KeyListener.isKeyPressed(GLFW_KEY_RIGHT))
            newDirection = GameConsts.RIGHT;
        else if (KeyListener.isKeyPressed(GLFW_KEY_DOWN))
            newDirection = GameConsts.DOWN;
        else if (KeyListener.isKeyPressed(GLFW_KEY_UP))
            newDirection = GameConsts.UP;


            if (Math.round(headPos.x()) != lastHeadX || Math.round(headPos.y()) != lastHeadY) {
                if (snakeHead.rotating != null) {
                    if (snakeHead.rotating.getSnakeTick() == 25) {
                        snakeHead.rotating = null;
                        scene.removeGO(point.gameObject);
                    }
                }
                if ((newDirection & 2) != (snakeHead.direction & 2) && snakeHead.rotating == null) {

                    // find shape of junction curve
                    int shape = ((newDirection & 1) << (((newDirection & 2) >> 1) ^ 1))
                            | ((snakeHead.direction & 1) << (((snakeHead.direction & 2) >> 1) ^ 1));

                    shape = (newDirection & 2) == 2 ? shape ^ 2 : shape ^ 1;

                    System.out.println(shape);

                    SnakeJunction junction = (SnakeJunction) genSnakePart(
                            shape + 3,
                            Math.round(snakeHead.transform.position.x),
                            Math.round(snakeHead.transform.position.y),
                            1, 1, snakeHead.direction, 0
                    );


                    junction.transform.centerR.x =
                            snakeHead.transform.centerR.x = Math.round(headPos.x()) - 0.5f + (shape & 1);
                    junction.transform.centerR.y =
                            snakeHead.transform.centerR.y = Math.round(headPos.y()) + 0.5f - ((shape & 2) >> 1);

                    snakeHead.direction = newDirection;

                    snakeHead.rotating = junction;

                    point = genSnakePart(1, junction.transform.centerR.x(), junction.transform.centerR.y(), 0.3f,0.3f, GameConsts.RIGHT,0);
                }
            }


        lastHeadX = Math.round(headPos.x());
        lastHeadY = Math.round(headPos.y());
    }

    private SnakePart genSnakePart(int shape, float x,
                                   float y, float a,
                                   float b, byte direction,
                                   float rotate)
    {

        GameObject go = new GameObject("part",
                new Transform(
                        new Vector2f(x, y),
                        1, new Vector2f(a * GameScene.pixelWidth, b * GameScene.pixelWidth),
                        new Vector2f(), rotate));

        if(shape == 0) {
            if(rotate == 0) {
                snakeHead = new SnakeEnd(color);
                scene.addGameObjectToScene(go.addComponent(snakeHead));
                return snakeHead;
            } else {
                snakeBack = new SnakeEnd(color);
                scene.addGameObjectToScene(go.addComponent(snakeBack));
                return snakeBack;
            }
        }
        else if(shape < 3) {
            SnakeBlock snakeBlock = new SnakeBlock(this, color, direction);

            scene.addGameObjectToScene(go.addComponent(snakeBlock));

            snakeBlocks.add(snakeBlock);

            return snakeBlock;

        } else {
            SnakeJunction junction = new SnakeJunction(newDirection, direction, color, shape);

            scene.addGameObjectToScene(go.addComponent(junction));

            junctions.add(junction);

            return junction;
        }
    }
}
