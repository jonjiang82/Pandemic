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
	Pandemic Game;

	private int windowWidth = 1280;
	private int windowHeight = 720;
	private HashMap<Integer, ArrayList<Image>> images;
	private int numLayers = 0;

	// The window handle
	private long window;

	//Input Variables
	private boolean leftMousePrev;
	private boolean rightMousePrev;
	private boolean leftMouse;
	private boolean rightMouse;
	private DoubleBuffer xpos, ypos;

	public Display(Pandemic game) {
		Game = game;
		init();
		images = new HashMap<>();
		xpos = BufferUtils.createDoubleBuffer(1);
		ypos = BufferUtils.createDoubleBuffer(1);
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
			Game.Update();

			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
			renderImages();
			glfwSwapBuffers(window); // swap the color buffers

			// Poll for window events. The key callback above will only be
			// invoked during this call.
			glfwPollEvents();
		}
	}

	private void setInputVariables() {
		leftMousePrev = leftMouse;
		rightMousePrev = rightMouse;
		leftMouse = glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_LEFT) == GLFW_TRUE;
		rightMouse = glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_RIGHT) == GLFW_TRUE;
		glfwGetCursorPos(window, xpos, ypos);
	}

	public boolean GetLeftMouseHeld() {
		return leftMouse;
	}

	public boolean GetRightMouseHeld() {
		return rightMouse;
	}

	public boolean GetLeftMouseDown() {
		return leftMouse && !leftMousePrev;
	}

	public boolean GetRightMouseDown() {
		return rightMouse && !rightMousePrev;
	}

	public boolean GetLeftMouseUp() {
		return !leftMouse && leftMousePrev;
	}

	public boolean GetRightMouseUp() {
		return !rightMouse && rightMousePrev;
	}

	public double GetMouseX() {
		return xpos.get(0);
	}

	public double GetMouseY() {
		return ypos.get(0);
	}

	public boolean GetMouseOver(Image image) {
		return GetMouseX() >= image.x && GetMouseY() >= image.y &&
		       GetMouseX() <= image.x + image.width && GetMouseY() <= image.y + image.height;
	}

	public boolean addImage(Image image) {
	    return addImage(image, 0);
	}

	public boolean addImage(Image image, int layer) {
		// abort if image is null or it's already marked as added to display
	    if (image == null || image.addedToDisplay || layer < 0) {
			return false;
		}

		// if layer doesn't exist, create it
		ArrayList<Image> list = images.get(layer);
		if (list == null) {
		    list = new ArrayList<>();
		    images.put(layer, list);
		    if (layer + 1 > numLayers) {
		        numLayers = layer + 1;
            }
        }

        // add image
        list.add(image);
        image.addedToDisplay = true;
        return true;
	}

	public boolean removeImage(Image image) {
	    if (!image.addedToDisplay) {
	        return false;
        }
		for (ArrayList<Image> list : images.values()) {
	        for (int i = 0, len = list.size(); i < len; i++) {
	            if (list.get(i) == image) list.remove(i);
	            return true;
            }
        }
        return false;
	}

	private void renderImages() {
		for (int i = 0, len = numLayers; i < len; i++) {
		    ArrayList<Image> layer = images.get(i);
		    if (layer == null) continue;
			for (Image img : layer) {
			    if (img.visible) {
                    img.bind();
                    glBegin(GL_QUADS);
                    {
                        glTexCoord2f(0.0f, 0.0f);
                        glVertex2f((img.x / windowWidth * 2) - 1, -(img.y / windowHeight * 2) + 1);

                        glTexCoord2f(0.0f, 1.0f);
                        glVertex2f((img.x / windowWidth * 2) - 1, -((img.y + img.height) / windowHeight * 2) + 1);

                        glTexCoord2f(1.0f, 1.0f);
                        glVertex2f(((img.x + img.width) / windowWidth * 2) - 1, -((img.y + img.height) / windowHeight * 2) + 1);

                        glTexCoord2f(1.0f, 0.00f);
                        glVertex2f(((img.x + img.width) / windowWidth * 2) - 1, -(img.y / windowHeight * 2) + 1);
                    }
                    glEnd();
                }
			}
		}
	}
}