package jade;

import java.util.ArrayList;
import java.util.List;

public class GameObject {

    private String name;
    private List<Component> components;
    public GameObject parent;
    public Transform transform;

    public GameObject(String name){
        init(name, null, new Transform());
    }

    public GameObject(String name, Transform transform){
        init(name, null, transform);
    }

    public GameObject(String name, GameObject parent, Transform transform){
        init(name, parent, transform);
    }

    public void init(String name, GameObject parent, Transform transform) {
        this.name = name;
        this.components = new ArrayList<>();
        this.parent = parent;
        this.transform = transform;
    }

    public <T extends Component> T getComponent(Class<T> componentClass) {
        for(Component c : components) {
            if(componentClass.isAssignableFrom(c.getClass())) {
                try {
                    return componentClass.cast(c);
                } catch (ClassCastException e) {
                    e.printStackTrace();
                    assert false : "Error: could not cast component";
                }
            }
        }

        return null;
    }

    public <T extends Component> void removeComponent(Class<T> componentClass) {
        for(int i = 0; i < components.size(); i++) {
            Component c = components.get(i);
            if(componentClass.isAssignableFrom(c.getClass())){
                components.remove(i);
                return;
            }
        }
    }

    public GameObject addComponent(Component c) {
        this.components.add(c);
        c.gameObject = this;
        return this;
    }

    public void update(float dt) {
        for (int i = 0; i < components.size(); i++) {
            components.get(i).update(dt);
        }
    }

    public void start() {
        for (int i = 0; i < components.size(); i++) {
            components.get(i).start();
        }
    }

    public String getName() { return name; }
}
