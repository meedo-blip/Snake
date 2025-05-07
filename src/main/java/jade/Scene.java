package jade;

import components.*;
import font.MyFont;
import org.joml.Vector2f;
import renderer.Renderer;

import java.util.ArrayList;
import java.util.List;

public abstract class Scene {

    public float fixedDT = 0f; // in seconds

    protected final Renderer renderer = new Renderer();
	protected Camera camera;
    protected final List<Sprite> gameSprites = new ArrayList<>();
    protected final List<Integer> gameParents = new ArrayList<>();

    private int spriteId = 0;
    private boolean isRunning = false;
    private int ticks = 0;

    public Scene() {}

    public void init() {}

    public void start() {

        for(int i = 0; i < gameSprites.size(); i++) {
            Sprite spr = gameSprites.get(i);
            spr.start();
            this.renderer.add(spr);
        }
        isRunning = true;
    }

    public Sprite addSpriteObjectToScene(Sprite spr) {
        spr.id = spriteId++;
        gameSprites.add(spr);

        if(isRunning) {
            spr.start();
            this.renderer.add(spr);
        }

        return spr;
    }


    public Sprite addSpriteObjectToScene(Sprite spr, Sprite parent) {
        if(parent == null)
            return addSpriteObjectToScene(spr);

        spr.id = spriteId++;
        spr.pForm = parent.transform;
        gameSprites.add(spr);
        addSpriteToParent(spr, parent);

        if(isRunning) {
            spr.start();
            this.renderer.add(spr);
        }

        return spr;
    }

    public Sprite makeText(MyFont font, String text, float x, float y, int fontsize) {
        return makeText(font, text, x, y, fontsize, null);

    }

    public TextNode makeText(MyFont font, String text, float x, float y, int fontsize, Sprite grandParent) {

        int width = text.length();
        int height = (text.length() / width) + Math.min(1, text.length() - width);

        return (TextNode) addSpriteObjectToScene(new DefaultTextNode(font, text, fontsize, new Transform(new Vector2f(x,y), -1, new Vector2f(fontsize * width, fontsize * height))), grandParent);
    }

    public Sprite getSprite(String name) {
        for (Sprite spr : gameSprites)
            if(spr.getName().equals(name))
                return spr;

        return null;
    }

    public void removeSprite(Sprite spr) {
        if(spr == null) return;
        int loc2 = gameParents.indexOf(-spr.id);
        if (loc2++ != -1) {

            while (!(gameParents.get(loc2) < 0)) {
                removeSprite(getSpriteById(gameParents.remove(loc2)));
                if(loc2 == gameParents.size() - 1)
                    break;
            }

            gameParents.remove(loc2 - 1);
        }

        gameSprites.remove(spr);
        renderer.remove(spr);
    }

    public void update(float dt) {
        for(int i = 0; i < gameSprites.size(); i++)
            gameSprites.get(i).update(dt);

        renderer.render();

        ticks++;
    }

    public int getTicks() {
        return ticks;
    }

    public Camera camera() { return this.camera; }

    public Sprite getSpriteById(int id){
        int pos = Math.min(id, gameSprites.size() - 1);
        int i = pos;
        for (; i >= 0; i--) {
            if(gameSprites.get(i).id == id)
                return gameSprites.get(i);
        }
        for (i = pos + 1; i < gameSprites.size(); i++) {
            if(gameSprites.get(i).id == id)
                return gameSprites.get(i);
        }

        return null;
    }

    public Sprite getSpriteByName(String name) {
        for (Sprite spr : gameSprites)
            if(spr.name.equals(name))
                return spr;

        return null;
    }

    public Sprite getParentOf(Sprite sprite) {
        int loc = gameParents.indexOf(sprite.id);
        for (int i = loc - 1; i >= 0; i--) {
            if(gameParents.get(i) < 0) {
                // turn back to positive
                return getSpriteById(-gameParents.get(i));
            }
        }
        return null;
    }

    private void addSpriteToParent(Sprite spr, Sprite parent) {
        int loc2 = gameParents.indexOf(-parent.id);
        if(!gameSprites.contains(parent)) {
            gameParents.add(-addSpriteObjectToScene(parent).id);
            loc2 = gameParents.size();
        } else if (loc2 == -1) {
            gameParents.add(-parent.id);
            loc2 = gameParents.size();
        } else {
            loc2++;
        }
        gameParents.add(loc2, spr.id);
    }

    public void removeChildrenOf(Sprite parent) {
        int loc = gameParents.indexOf(-parent.id);

        if(loc != -1) {
            gameParents.remove(loc);
            while(gameParents.get(loc) >= 0){

                if(gameParents.size() - 1 == loc) {
                    removeSprite(getSpriteById(gameParents.remove(loc)));
                    break;
                }

                removeSprite(getSpriteById(gameParents.remove(loc)));
            }
        }
    }

}
