import game.GameScene;
import jade.Window;

public class Main {
    public static void main(String[] args) {
        for (int i = 0; i < args.length; i++) {
            System.out.print(args[i] + "  ");
        }
        Window.get().run(new GameScene());
    }
}