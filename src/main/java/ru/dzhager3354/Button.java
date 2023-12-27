package ru.dzhager3354;

import java.util.function.Consumer;

public abstract class Button {
    private double x;
    private double y;
    private double width;
    private double height;
    private Window window;
    private Consumer<Window> consumer;

    public Button(double x, double y, double width, double height, Window window, Consumer<Window> consumer) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.window = window;
        this.consumer = consumer;
    }

    public boolean isClickInHitbox(double clickX, double clickY) {
        return clickX > x && clickX < x + width && clickY > y && clickY < y + height;
    }

    public abstract void draw();

    public abstract void eventClick(double x, double y);

    public Window getWindow() {
        return window;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    protected void release() {
        consumer.accept(window);
    }
}
