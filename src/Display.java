import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;
import java.util.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Display {
    public static Display instance;

    private int windowWidth = 1280;
    private int windowHeight = 720;
    private ArrayList<Image> images;

    // The window handle
    private long window;

    //Input Variables
    private boolean leftMouseDown;
    private DoubleBuffer xpos, ypos;

    public Display() {
        init();
        images = new ArrayList<Image>();
        try (MemoryStack stack = stackPush()) {
            xpos = BufferUtils.createDoubleBuffer(1);
            ypos = BufferUtils.createDoubleBuffer(1);
        }
    }

    public void run() {
        loop();

        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void init() {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        //glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE); // the window will be resizable

        // Create the window
        window = glfwCreateWindow(windowWidth, windowHeight, "Pandemic", NULL, NULL);
        glfwSetWindowAspectRatio(window, 16, 9);
        if (window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
        });

        // Get the thread stack and push a new frame
        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(window, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            glfwSetWindowPos( window, (vidmode.width() - pWidth.get(0)) / 2, (vidmode.height() - pHeight.get(0)) / 2);
        } // the stack frame is popped automatically

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(window);

        GL.createCapabilities();
    }

    private void loop() {
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        // Set the clear color
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glEnable(GL_TEXTURE_2D);

        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while (!glfwWindowShouldClose(window)) {
            setInputVariables();
            Pandemic.instance.Update();

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
            renderImages();
            glfwSwapBuffers(window); // swap the color buffers

            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }
    }

    private void setInputVariables() {
        leftMouseDown = glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_LEFT) == GLFW_TRUE;
        glfwGetCursorPos(window, xpos, ypos);
    }

    public boolean GetLeftMouseDown() {
        return leftMouseDown;
    }

    public double GetMouseX() {
        return xpos.get(0);
    }

    public double GetMouseY() {
        return ypos.get(0);
    }

    public boolean addImage(Image image) {
        if (image != null && !images.contains(image)) {
            images.add(image);
            return true;
        }
        return false;
    }

    public boolean removeImage(Image image) {
        return images.remove(image);
    }

    private void renderImages() {
        for (Image i : images) {
            i.bind();
            glBegin(GL_QUADS);
            {
                glTexCoord2f(0.0f, 0.0f);
                glVertex2f((i.x / windowWidth * 2) - 1, -(i.y / windowHeight * 2) + 1);

                glTexCoord2f(0.0f, 1.0f);
                glVertex2f((i.x / windowWidth * 2) - 1, -((i.y + i.height) / windowHeight * 2) + 1);

                glTexCoord2f(1.0f, 1.0f);
                glVertex2f(((i.x + i.width) / windowWidth * 2) - 1, -((i.y + i.height) / windowHeight * 2) + 1);

                glTexCoord2f(1.0f, 0.00f);
                glVertex2f(((i.x + i.width) / windowWidth * 2) - 1, -(i.y / windowHeight * 2) + 1);
            }
            glEnd();
        }
    }
}