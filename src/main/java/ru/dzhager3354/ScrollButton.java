package ru.dzhager3354;

import org.lwjgl.opengl.GL11;

import java.util.function.Consumer;

public class ScrollButton extends Button{
    private int currLevel;

    public ScrollButton(double x, double y, double width, double height, Window window, Consumer<ScrollButton> consumer) {
        super(x, y, width, height, window, consumer);
    }

    @Override
    public void draw() {
        GL11.glColor3d(0.2, 0.7, 0.5);
        GL11.glBegin(GL11.GL_TRIANGLE_FAN);
        GL11.glVertex2d(getX(), getY());
        GL11.glVertex2d(getX(), getY()+getHeight());
        GL11.glVertex2d(getX() + getWidth(), getY() + getHeight());
        GL11.glVertex2d(getX() + getWidth(), getY());
        GL11.glEnd();
        GL11.glColor3d(0, 0, 0);
        GL11.glBegin(GL11.GL_TRIANGLE_FAN);
            double temp = getLevel()/256.0*getHeight();
            GL11.glVertex2d(getX()+getWidth()/4, temp + getY()+0.01);
            GL11.glVertex2d(getX()+getWidth()/4, getY() + temp -0.01);
            GL11.glVertex2d(getX()+getWidth()*0.75, getY() + temp -0.01);
            GL11.glVertex2d(getX()+getWidth()*0.75, temp + getY()+0.01);
        GL11.glEnd();
    }

    @Override
    public void eventClick(double x, double y) {
        if (isClickInHitbox(x, y)) {
            double res = (y-getY())/getHeight();
            if (res < 0) res = 0;
            if (res > 1) res = 1;
            currLevel = (int) (res*256);
            release(this);
        }
    }

    public int getLevel() {
        return currLevel;
    }
}
