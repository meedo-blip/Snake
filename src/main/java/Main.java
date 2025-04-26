import game.GameScene;
import jade.Window;

public class Main {
    public static void main(String[] args) {
        Window.get("Snake").run(new GameScene());
    }
}
