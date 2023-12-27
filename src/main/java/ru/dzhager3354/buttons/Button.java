package ru.dzhager3354.buttons;

import ru.dzhager3354.window.Window;

import java.util.function.Consumer;

public abstract class Button<T> {
    private double x;
    private double y;
    private double width;
    private double height;
    private Window window;
    private Consumer<T> consumer;

    public Button(double x, double y, double width, double height, Window window, Consumer<T> consumer) {
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

    protected void release(T t) {
        consumer.accept(t);
    }
}
