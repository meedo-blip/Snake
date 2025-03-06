package jade;

import renderer.Renderer;

import java.util.ArrayList;
import java.util.List;

public abstract class Scene {

    protected Renderer renderer = new Renderer();
	protected Camera camera;
    public float fixedDT = 0f;
    private boolean isRunning = false;
    protected final List<GameObject> gameObjects = new ArrayList<>();
	
    public Scene() {}

    public void start() {
        for(GameObject go : gameObjects) {
            go.start();
            this.renderer.add(go);
        }
        isRunning = true;
    }

    public void init() {}

    public void addGameObjectToScene(GameObject go) {
        if(!isRunning) {
            gameObjects.add(go);
        } else {
            gameObjects.add(go);
            go.start();
            this.renderer.add(go);
        }
    }

    public GameObject getGO(String name) {
        for (GameObject go : gameObjects)
            if(go.getName().equals(name))
                return go;

        return null;
    }

    public void removeGO(GameObject go) {
        gameObjects.remove(go);
        renderer.remove(go);
    }

    public abstract void update(float dt);

    public Camera camera() { return this.camera; }
}
