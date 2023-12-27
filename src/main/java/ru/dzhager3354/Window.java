package ru.dzhager3354;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.system.MemoryStack.stackPush;

public class Window implements Runnable{
    private long windowID;
    private int windowWidth = 500;
    private int windowHeight = 500;

    private double offsetX;
    private double offsetY;
    private double scale = 1;

    public Tools tool = Tools.CREATE;

    private List<Button> buttons = new ArrayList<>();

    private List<Layer> layers = new ArrayList<>();
    private int currentLayerOffset;

    {
        layers.add(new Layer(this));
        buttons.add(new ButtonMode(-0.05, -1, 0.1, 0.1, this, (window -> {
            window.tool = Tools.MOVE;
        })));
        buttons.add(new ButtonMode(0.2, -1, 0.1, 0.1, this, (window -> {
            window.tool = Tools.CREATE;
        })));
        buttons.add(new ScrollButton(0.4, -1, 0.1, 0.3, this, (button -> {
            this.getCurrentLayer().setRed(button.getLevel());
        })));
        buttons.add(new ScrollButton(0.6, -1, 0.1, 0.3, this, (button -> {
            this.getCurrentLayer().setGreen(button.getLevel());
        })));
        buttons.add(new ScrollButton(0.8, -1, 0.1, 0.3, this, (button -> {
            this.getCurrentLayer().setBlue(button.getLevel());
        })));
    }

    //Текущие кооринаты мыши на экране в пикселях
    private final double[] cursorX = new double[1];
    private final double[] cursorY = new double[1];

    public void init() {
        GLFW.glfwInit();

        glfwDefaultWindowHints();
        windowID = glfwCreateWindow(windowWidth, windowHeight, new StringBuffer("PixelArt"), 0, 0);

        try ( MemoryStack stack = stackPush() ) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(windowID, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            glfwSetMouseButtonCallback(windowID, (window, button, action, mods) -> {
                if (button == GLFW_MOUSE_BUTTON_LEFT && action == GLFW_PRESS) {
                    glfwGetCursorPos(window, cursorX, cursorY);
                    if (!isButtonClick())
                        tool.leftClickPress(this);
                }
                else if (button == GLFW_MOUSE_BUTTON_LEFT && action == GLFW_RELEASE) {
                    glfwGetCursorPos(window, cursorX, cursorY);
                    if (!isButtonClick())
                        tool.leftClickRelease(this);
                }
            });

            glfwSetScrollCallback(windowID, (window, x, y) -> setScale((int)y));

            glfwSetCursorPosCallback(windowID, (window, x, y)-> {
                cursorX[0] = x;
                cursorY[0] = y;
                tool.mouseMove(this);
            });

            // Center the window
            glfwSetWindowPos(
                    windowID,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        }

        glfwMakeContextCurrent(windowID);
        glfwSwapInterval(1);

        glfwShowWindow(windowID);
    }

    @Override
    public void run() {
        init();
        loop();

        glfwFreeCallbacks(windowID);
        glfwDestroyWindow(windowID);

        glfwTerminate();
    }

    public void loop() {
        GL.createCapabilities();
        glClearColor(1, 1, 1, 1);
        while ( !glfwWindowShouldClose(windowID) ) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            GL11.glPushMatrix();
            GL11.glScaled(scale, scale, 1);
            GL11.glTranslated(offsetX, offsetY, 0);
            GL11.glBegin(GL_LINES);
            GL11.glVertex2d(-1, 0);
            GL11.glVertex2d(1, 0);
            GL11.glVertex2d(0, 1);
            GL11.glVertex2d(0, -1);
            GL11.glEnd();
            layers.get(currentLayerOffset).draw();
            GL11.glPopMatrix();
            drawButton();
            glfwSwapBuffers(windowID);
            glfwPollEvents();
        }
    }

    private void drawButton() {
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
        for (Button button : buttons) {
            button.draw();
        }
        GL11.glPopMatrix();
    }

    private boolean isButtonClick() {
        for (Button button : buttons) {
            double x = Util.translateCursorX(getX(), windowWidth);
            double y = Util.translateCursorY(getY(), windowHeight);
            if (button.isClickInHitbox(x, y)) {
                button.eventClick(x, y);
                return true;
            }
        }
        return false;
    }

    private void setScale(int level) {
        scale += level*0.02;
        if (scale < 0.001) scale = 0.001;
        if (scale > 1) scale = 1;
    }

    public double getX() {
        return cursorX[0];
    }

    public double getY() {
        return cursorY[0];
    }

    public int getWindowWidth() {
        return windowWidth;
    }

    public int getWindowHeight() {
        return windowHeight;
    }

    public Layer getCurrentLayer() {
        return layers.get(currentLayerOffset);
    }

    public double getOffsetX() {
        return offsetX;
    }

    public double getOffsetY() {
        return offsetY;
    }

    public void setOffsetX(double offsetX) {
        this.offsetX = offsetX;
    }

    public void setOffsetY(double offsetY) {
        this.offsetY = offsetY;
    }

    public double getScale() {
        return scale;
    }
}
