package game;

import jade.*;
import org.joml.Vector2f;
import org.joml.Vector4f;
import util.Utils;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;

public class Snake {
    public Vector4f color;
    public static final float
            STEP =  1f / 25, // step per pixel width
            ANGLE_STEP = (90f / 25f) * Utils.DEGREES_TO_RADIANS,
            SNAKE_WIDTH = 0.5f;

    public byte newDirection = GameConsts.RIGHT;

    public SnakeEnd snakeHead;
    public SnakeEnd snakeBack;

    private GameScene gameScene;

    private float lastHeadX = 0f, lastHeadY = 0f;

    private List<SnakeBlock> snakeBlocks;
    private List<SnakeJunction> junctions;

    private Vector2f headPos, backPos;
    private GameSprite point;

    public Snake() {
        color = new Vector4f(0.5f, 1f, 0.5f, 1f);
        init();
    }

    public Snake(Vector4f color) {
        this.color = color;
        init();
    }

    private void init() {
        snakeBlocks = new ArrayList<>();
        junctions = new ArrayList<>();
        gameScene = (GameScene) Window.getScene();

        this.snakeHead = (SnakeEnd) genSnakePart("head", 0,

                -2, 0, SNAKE_WIDTH, 0.8f,

                GameConsts.RIGHT, 90f * Utils.DEGREES_TO_RADIANS);

        this.snakeBack = (SnakeEnd) genSnakePart("back", 0,

                -4, 0, SNAKE_WIDTH, 0.8f,

                GameConsts.RIGHT, 270f * Utils.DEGREES_TO_RADIANS);

        genSnakePart("block", 2, -3, 0,
                1f, SNAKE_WIDTH, GameConsts.RIGHT,
                0);
    }



    public void start() {
        headPos = snakeHead.transform.position;
        backPos = snakeBack.transform.position;

        gameScene.makeApple();

        Window.getScene().makeText(Constants.ARIAL_FONT, "head", 0, 0, 12, snakeHead);

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


        if (snakeHead.rotating != null) {
            if (snakeHead.rotating.getSnakeTick() >= 25) {
                snakeHead.rotating = null;

                genSnakePart("block",2, headPos.x(),
                        headPos.y(), SNAKE_WIDTH, SNAKE_WIDTH,
                        snakeHead.direction, 0);
            }
        }

        if (snakeBack.rotating != null) {
            if (snakeBack.rotating.getSnakeTick() == 25) {
                snakeBack.rotating = null;
                Window.getScene().removeSprite(junctions.removeFirst());

                snakeBlocks.getFirst().backPart = snakeBack;
            }
        }

        if(GameUtil.checkCollision(gameScene.apple, snakeHead)) {
            gameScene.eatApple();
        }


        if (Math.round(headPos.x()) != lastHeadX || Math.round(headPos.y()) != lastHeadY) {

            if(headPos.x() > GameScene.boardLen / 2f || headPos.x() < -GameScene.boardLen / 2f
            || headPos.y() > GameScene.boardLen / 2f || headPos.y() < -GameScene.boardLen / 2f)
                Window.changeScene(new GameScene());

            if ((newDirection & 2) != (snakeHead.direction & 2) && snakeHead.rotating == null) {

                // find shape of junction curve
                int shape = ((newDirection & 1) << (((newDirection & 2) >> 1) ^ 1))
                        | ((snakeHead.direction & 1) << (((newDirection & 2) >> 1)));

                shape = (newDirection & 2) == 2 ? shape ^ 2 : shape ^ 1;

                System.out.println(shape);

                SnakeJunction junction = (SnakeJunction) genSnakePart(
                        "junction",
                        shape + 3,
                        Math.round(snakeHead.transform.position.x),
                        Math.round(snakeHead.transform.position.y),
                        1, 1, snakeHead.direction, 0
                );


                junction.transform.centerR.x =
                        snakeHead.transform.centerR.x = Math.round(headPos.x()) - 0.5f + (shape & 1);
                junction.transform.centerR.y =
                        snakeHead.transform.centerR.y = Math.round(headPos.y()) + 0.5f - ((shape & 2) >> 1);

                snakeHead.transform.rotate = 0;

                snakeHead.ox = headPos.x() - snakeHead.transform.centerR.x();
                snakeHead.oy = headPos.y() - snakeHead.transform.centerR.y();

                snakeHead.direction = newDirection;

                snakeHead.rotating = junction;

                snakeBlocks.getLast().frontPart = junction;
            }

            if(snakeBack.rotating == null && !junctions.isEmpty()) {
                if(Math.round(backPos.x()) == junctions.getFirst().transform.position.x
                        && Math.round(backPos.y()) == junctions.getFirst().transform.position.y)
                {
                    snakeBack.rotating = junctions.getFirst();
                    snakeBack.rotating.snakeTick = 0;
                    snakeBack.transform.rotate = 0;

                    snakeBack.transform.centerR = snakeBack.rotating.transform.centerR;

                    Window.getScene().removeSprite(snakeBlocks.removeFirst());

                    snakeBack.ox = backPos.x() - snakeBack.transform.centerR.x();
                    snakeBack.oy = backPos.y() - snakeBack.transform.centerR.y();

                    snakeBack.direction = snakeBack.rotating.newDirection;
                }
            }
        }


        lastHeadX = Math.round(headPos.x());
        lastHeadY = Math.round(headPos.y());
    }

    private GameSprite genSnakePart(String name, int shape, float x,
                                    float y, float a,
                                    float b, byte direction,
                                    float rotate)
    {
        Transform transform = new Transform(
                new Vector2f(x, y),
                1, new Vector2f(a * GameScene.pixelWidth, b * GameScene.pixelWidth),
                new Vector2f(), rotate);

        if(shape == 0) {
            SnakeEnd end = new SnakeEnd(color);
            end.name = name;

            Window.getScene()
                    .addSpriteObjectToScene(end.setTransform(transform));

            return end;
        }
        else if(shape < 3) {
            SnakeBlock snakeBlock = new SnakeBlock(color, direction,
                    snakeHead,
                    junctions.isEmpty() ? snakeBack : junctions.getLast());

            snakeBlock.name = name;

            Window.getScene()
                    .addSpriteObjectToScene(snakeBlock.setTransform(transform));

            snakeBlocks.add(snakeBlock);

            return snakeBlock;

        } else {
            SnakeJunction junction = new SnakeJunction(newDirection, direction, color, shape);

            junction.name = name;

            Window.getScene()
                    .addSpriteObjectToScene(junction.setTransform(transform));

            junctions.add(junction);

            return junction;
        }
    }
}
