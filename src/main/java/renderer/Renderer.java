package renderer;

import components.FontSprite;
import components.QuadSprite;
import components.Sprite;
import util.AssetPool;

import java.util.ArrayList;
import java.util.List;


public class Renderer {

    private final List<RenderBatch> batches;

    public Renderer() {
        this.batches = new ArrayList<>();
    }

    public void render() {
        for (RenderBatch batch : batches) {
            batch.render();
        }
    }

    public void add(Sprite spr) {
        if(spr != null) {
            if (spr instanceof QuadSprite) {
                add((QuadSprite) spr);
            }
        }

    }

    public void remove(Sprite spr) {
        if ((spr != null))
            for (RenderBatch batch : batches)
                if (batch.removeSprite(spr))
                    return;
    }

    private void add(QuadSprite spr) {
        for (RenderBatch batch : batches) {
            if(batch.addSprite(spr))
                return;
        }
        RenderBatch newBatch = AssetPool.getBatchOf(spr.getShader());

        newBatch.start();
        batches.add(newBatch);
        newBatch.addSprite(spr);
    }
}

