package jade;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.GL_MAX_TEXTURE_IMAGE_UNITS;
import static org.lwjgl.system.MemoryUtil.*;

public class Window {

    private int width, height;
    private String title;

    // Location of window in the memory
    private long glfwWindow;

    private static Window window = null;

    public int window_w = 2560, window_h = 1369;

    private static Scene currentScene;

    public static float time = 0, lastTime = 0;

    private Window() {
        this.width = 2560;
        this.height = 1440;
        this.title = "Snake";
    }

    public static void changeScene(Scene newScene) {
        currentScene = newScene;
        currentScene.init();
        currentScene.start();
    }

    public static Window get() {
        if (Window.window == null) {
            Window.window = new Window();
        }

        return Window.window;
    }

    public static Scene getScene() {
        return currentScene;
    }

    public void run(Scene scene) {
        System.out.println("Hello LWJGL"+ Version.getVersion() +"!");

        init();
        changeScene(scene);
        loop();

        // Free the memory
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        // Terminate glfw and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public void init() {
        // Setup error callback
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        // Configure GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // invisible
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // can be resized
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE); // set fullscreen to true

        // Create the window
        glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);

        if (glfwWindow == NULL) {
            throw new IllegalStateException("Failed to create the GLFW window");
        }

        glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback);
        glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);
        glfwSetKeyCallback(glfwWindow, KeyListener::keyCallback);

        // Make the OpenGL context current
        glfwMakeContextCurrent(glfwWindow);

        // Enable vsync, this locks your frame rate to the refresh rate of the user's monitor
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(glfwWindow);

        // This line is critical for LWJGL's interoperation with GLFW
        GL.createCapabilities();

        int[] texture_units = new int[1];
        glGetIntegerv(GL_MAX_TEXTURE_IMAGE_UNITS, texture_units);


        System.out.println("Your Gpu supports upto " + texture_units[0] + " textures per batch.");
    }

	public void loop() {
    	boolean usingWindows = System.getProperty("os.name").contains("Windows");
    	
        float startTime = (float) glfwGetTime();
        float endTime;
        float dt = 0.0f;

        IntBuffer bufferW = BufferUtils.createIntBuffer(1);
        IntBuffer bufferH = BufferUtils.createIntBuffer(1);


        while (!glfwWindowShouldClose(glfwWindow)) {
            // Poll events
            glfwPollEvents();

            // Set screen color
            glClearColor(0f, 0f, 0f, 1.0f);
            glClear(GL_COLOR_BUFFER_BIT);

            glfwGetWindowSize(glfwWindow, bufferW, bufferH);

            window_w = bufferW.get(0);
            window_h = bufferH.get(0);

            currentScene.update(dt);

            glfwSwapBuffers(glfwWindow);

            lastTime = startTime;

            if(currentScene.fixedDT > 0f) {
                try {
                    Thread.sleep((long) (Math.max(currentScene.fixedDT - glfwGetTime() + startTime, 0f) * 1000));
                } catch (InterruptedException ignored) {
                }
            }

            endTime = (float) glfwGetTime();
            dt = endTime - startTime;
            startTime = endTime;
        }
    }
}
