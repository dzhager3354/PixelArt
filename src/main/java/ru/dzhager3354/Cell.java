package ru.dzhager3354;

import org.lwjgl.opengl.GL11;

public class Cell {
    private Color color;
    private int xCell;
    private int yCell;

    public Cell(Color color) {
        this.color = color;
    }

    public Cell(int red, int green, int blue) {
        this.color = new Color(red, green, blue);
    }

    public void draw() {
        GL11.glColor3d(color.getRed()/256.0, color.getGreen()/256.0, color.getBlue()/256.0);
        GL11.glBegin(GL11.GL_TRIANGLE_FAN);
            GL11.glVertex2d(xCell, yCell);
            GL11.glVertex2d(xCell, yCell + 1);
            GL11.glVertex2d(xCell+1, yCell + 1);
            GL11.glVertex2d(xCell+1, yCell);
        GL11.glEnd();
    }

    public int getX() {
        return xCell;
    }

    public void setX(int xCell) {
        this.xCell = xCell;
    }

    public int getY() {
        return yCell;
    }

    public void setY(int yCell) {
        this.yCell = yCell;
    }
}
