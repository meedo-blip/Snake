
import java.util.function.Consumer;
import java.util.function.Function;

public class Test {
    public static void main(String[] args) {
        int n = 1;

        Cat cat = () -> System.out.println("meow");

        cat.sayMeow();

        System.out.println(n -= 1);
        System.out.println(n = Integer.MAX_VALUE + 1);

    }



    public static void meow(Consumer<String> lamda) {
        lamda.accept("meow");
    }
}



interface Cat {
    void sayMeow();
}