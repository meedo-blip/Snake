
import java.util.function.Consumer;
import java.util.function.Function;

public class Test {
    public static void main(String[] args) {
        int n = 1;

        Cat cat = () -> System.out.println("meow");

        cat.sayMeow();

        System.out.println();

        System.out.println(n -= 1);
        System.out.println(n = Integer.MAX_VALUE + 1);

        System.out.println();

        Panda panda = new Panda();

        System.out.println("panda is, " + panda.age + " yrs old");
        cat.sayMeow();

        System.out.println("\n" + sumEvens(50000 ,  -1));

        for(int i = 0; i < 100 ; i++)
            System.out.println("\n.." + (char) i + ":" + i);

    }


    // sum of all even numbers between top and bottom inclusively
    public static int sumEvens(int top, int bottom) {
        if (top > bottom) {
            top++; // to include top value if even
            int n = ((top >> 1) - (bottom >> 1)) + ((top & 1) | (bottom & 1));
            return n * ((bottom + (bottom & 1)) + (n - 1));
        }

        return 0;
    }
}

@FunctionalInterface
interface Cat {
    void sayMeow();
}

class Panda {
    public String name;
    public int age = 7;
}