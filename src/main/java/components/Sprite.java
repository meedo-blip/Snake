package components;

import jade.Constants;
import jade.Transform;
import jade.Window;
import org.joml.Vector2f;
import org.joml.Vector4f;
import renderer.Shader;

public abstract class Sprite {
    protected Shader shader;
    public Transform transform, pForm, offsetForm; // parent transform
    public int id;
    protected Vector4f color;
    protected boolean changed = true;
    public String name;

    public Sprite setTransform(Transform transform) {
        this.transform = transform;
        return this;
    }

    public void start() {
        if(transform == null)
            transform = new Transform();
        if(shader == null)
            shader = Constants.DEFAULT_SH;

        Sprite parent = Window.getScene().getParentOf(this);

        if (parent != null) {
            pForm = parent.transform;
            offsetForm = new Transform();
        }
    }


    public abstract void update(float dt);

    public Vector4f getColor() {
        return color;
    }

    public Shader getShader() {
        return shader;
    }

    public boolean isChanged() {
        return changed;
    }

    public Sprite setName(String name) {
        this.name = name;
        return this;
    }

    public String getName() {
        return name;
    }
}
