package ru.dzhager3354.buttons;

import org.lwjgl.opengl.GL11;
import ru.dzhager3354.window.Window;

import java.util.function.Consumer;

public class ButtonMode extends Button {
    private boolean isClick;
    public ButtonMode(double x, double y, double width, double height, Window window, Consumer<Window> consumer) {
        super(x, y, width, height, window, consumer);
    }

    @Override
    public void eventClick(double x, double y) {
        if (isClickInHitbox(x, y)) {
            if (isClick)
                release(getWindow());
            isClick = !isClick;
        }
    }

    public boolean isClick() {
        return isClick;
    }

    @Override
    public void draw() {
        GL11.glColor3d(0.5, 0.5, 0.5);
        GL11.glBegin(GL11.GL_TRIANGLE_FAN);
            GL11.glVertex2d(getX(), getY());
            GL11.glVertex2d(getX(), getY()+getHeight());
            GL11.glVertex2d(getX() + getWidth(), getY() + getHeight());
            GL11.glVertex2d(getX() + getWidth(), getY());
        GL11.glEnd();
    }
}
