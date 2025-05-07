package game;

import components.QuadSprite;

public class GameUtil {
    static boolean checkCollision(QuadSprite spr1, QuadSprite spr2) {
        float pos1x, pos1y, pos2x, pos2y;
        if(spr1 instanceof GameSprite) {
            pos1x = spr1.transform.position.x * GameScene.pixelWidth;
            pos1y = spr1.transform.position.y * GameScene.pixelWidth;
        } else {
            pos1x = spr1.transform.position.x;
            pos1y = spr1.transform.position.y;
        }

        if(spr2 instanceof GameSprite) {
            pos2x = spr2.transform.position.x * GameScene.pixelWidth;
            pos2y = spr2.transform.position.y * GameScene.pixelWidth;
        } else {
            pos2x = spr2.transform.position.x;
            pos2y = spr2.transform.position.y;
        }

        float scale1x = spr1.transform.scale.x / 2;
        float scale1y = spr1.transform.scale.y / 2;
        float scale2x = spr2.transform.scale.x / 2;
        float scale2y = spr2.transform.scale.y / 2;

        // 00 01 10 11
        float ax1 = pos1x - (scale1x);
        float ay1 = pos1y + (scale1y);

        float ax2 = pos1x + (scale1x);
        float ay2 = pos1y + (scale1y);

        float ax3 = pos1x - (scale1x);
        float ay3 = pos1y - (scale1y);

        float ax4 = pos1x + (scale1x);
        float ay4 = pos1y - (scale1y);

        // 00 11
        float bx1 = pos2x - (scale2x);
        float by1 = pos2y + (scale2y);

        float bx2 = pos2x + (scale2x);
        float by2 = pos2y - (scale2y);

        if(ax1 >= bx1 && ax1 <= bx2 && ay1 <= by1 && ay1 >= by2) return true;
        if(ax2 >= bx1 && ax2 <= bx2 && ay2 <= by1 && ay2 >= by2) return true;
        if(ax3 >= bx1 && ax3 <= bx2 && ay3 <= by1 && ay3 >= by2) return true;
        return ax4 >= bx1 && ax4 <= bx2 && ay4 <= by1 && ay4 >= by2;
    }
}
