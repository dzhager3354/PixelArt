package ru.dzhager3354;

import java.util.ArrayList;
import java.util.List;

public class Layer {
    private final Window window;
    private final Color colorOption = new Color(0, 0, 1);
    private List<Cell> cells = new ArrayList<>();

    public Layer(Window window) {
        this.window = window;
    }

    public void draw() {
        for (Cell cell : cells) {
            cell.draw();
        }
    }

    public void createCell() {
        Cell newCell = new Cell(colorOption.getRed(), colorOption.getGreen(), colorOption.getBlue());
        double xCell = Util.translateCursorX(window.getX(), window.getWindowWidth())/window.getScale() - window.getOffsetX();
        double yCell = Util.translateCursorY(window.getY(), window.getWindowHeight())/window.getScale() - window.getOffsetY();
        int x = (int) Math.floor(xCell);
        int y = (int) Math.floor(yCell);
        newCell.setX(x);
        newCell.setY(y);
        cells.add(newCell);
    }

    public Window getWindow() {
        return window;
    }

    public void setRed(int level) {
        colorOption.setRed(level);
    }

    public void setGreen(int level) {
        colorOption.setGreen(level);
    }

    public void setBlue(int level) {
        colorOption.setBlue(level);
    }

    public Color getColor() {
        return colorOption.clone();
    }
}
