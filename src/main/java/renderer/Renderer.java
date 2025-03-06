package renderer;

import components.SpriteRenderer;
import jade.Constants;
import jade.GameObject;
import util.AssetPool;

import java.util.ArrayList;
import java.util.List;


public class Renderer {

    private List<RenderBatch> batches;
    private static SpriteRenderer temp;

    public Renderer() {
        this.batches = new ArrayList<>();
    }

    public void render() {
        for (RenderBatch batch : batches) {
            batch.render();
        }
    }

    public void add(GameObject go) {
        temp = go.getComponent(SpriteRenderer.class);
        if (temp != null) {
            add(temp);
        }
    }

    public void remove(GameObject go) {
        if ((temp = go.getComponent(SpriteRenderer.class)) != null)
            for (RenderBatch batch : batches)
                if (batch.removeSprite(temp))
                    break;
    }

    private void add(SpriteRenderer spr) {
        for (RenderBatch batch : batches) {
            if (batch.hasRoom() && batch.usesShader(spr.getShader())) {
                if (spr.getTexture() == null || batch.hasTextureRoom() || batch.hasTexture(spr.getTexture())) {
                    batch.addSprite(spr);
                    return;
                }
            }
        }


        RenderBatch newBatch = spr.getShader() == Constants.DEFAULT_SH
                ? new DefaultBatch()
                : AssetPool.getBatchOf(spr.getShader());

        newBatch.start();
        batches.add(newBatch);
        newBatch.addSprite(spr);
    }
}

