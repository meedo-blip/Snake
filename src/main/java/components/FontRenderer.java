package components;

import jade.Component;

public class FontRenderer extends Component {

    @Override
    public void start() {
        if(gameObject.getComponent(FontRenderer.class) != null) {
            System.out.println("Found Font Renderer!");
        }
    }

    @Override
    public void update(float dt) {

    }
}
