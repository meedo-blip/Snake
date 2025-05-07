package util;

public class Time {
    private static Thread timer;

    public static float dt;

    @Deprecated
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
