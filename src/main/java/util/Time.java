package util;

import java.util.Timer;
import java.util.TimerTask;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

public class Time {
    private static Thread timer;

    public static float dt;
    public static void setInterval(long millis, Runnable run) {

        if(timer == null) {
            timer = new Thread(() -> {

                 while (true) {
                    try {
                        Thread.sleep(millis);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    run.run();
                }
            });

            timer.start();
        }
    }
}
