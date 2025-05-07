package game;

import org.joml.Vector4f;

public class SnakeBlock extends GameSprite {

    GameSprite frontPart;
    GameSprite backPart;

    public SnakeBlock(Vector4f color, byte direction,
                      GameSprite frontPart,
                      GameSprite backPart)
    {
        this.color = color;
        this.direction = direction;
        shape = 2;

        this.frontPart = frontPart;
        this.backPart = backPart;

        init();
    }

    @Override
    public void update(float dt) {
        changed = (frontPart.isChanged() || backPart.isChanged());
        if(changed) {
            if((direction & 2) == 2) {
                transform.position.x = ((frontPart.transform.position.x()
                        - backPart.transform.position.x()) / 2) + backPart.transform.position.x();

                transform.scale.x = (Math.abs((frontPart.transform.position.x()
                        - transform.position.x()) * GameScene.pixelWidth)
                        - (frontPart.transform.scale.x / 2)) * 2;
            } else {
                transform.position.y = ((frontPart.transform.position.y()
                        - backPart.transform.position.y()) / 2) + backPart.transform.position.y();

                transform.scale.y = (Math.abs((frontPart.transform.position.y()
                        - transform.position.y()) * GameScene.pixelWidth)
                        - (frontPart.transform.scale.y() / 2)) * 2;
            }
        }
    }
}
