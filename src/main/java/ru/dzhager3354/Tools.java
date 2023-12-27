package ru.dzhager3354;

import java.util.function.Consumer;

public enum Tools {
    MOVE {
        @Override
        public void leftClickPress(Window window) {
            event((win -> {
                isPressed = true;
                startX = Util.translateCursorX(win.getX(), win.getWindowWidth());
                startY = Util.translateCursorY(win.getY(), win.getWindowHeight());
            }), window);
        }

        @Override
        public void leftClickRelease(Window window) {
            event((win -> isPressed = false), window);
        }

        @Override
        public void mouseMove(Window window) {
            event((win -> {
                if (!isPressed) return;
                double divX = Util.translateCursorX(win.getX(), win.getWindowWidth())/win.getScale() - startX/win.getScale();
                double divY = Util.translateCursorY(win.getY(), win.getWindowHeight())/win.getScale() - startY/win.getScale();

                win.setOffsetX(win.getOffsetX() + divX);
                win.setOffsetY(win.getOffsetY() + divY);

                startX = Util.translateCursorX(win.getX(), win.getWindowWidth());
                startY = Util.translateCursorY(win.getY(), win.getWindowHeight());
            }), window);
        }
    }, CREATE {
        @Override
        public void leftClickPress(Window window) {
            event((win-> {
                isPressed = true;
                win.getCurrentLayer().createCell();
            }), window);
        }

        @Override
        public void leftClickRelease(Window window) {
            isPressed = false;
        }

        @Override
        public void mouseMove(Window window) {
            if (isPressed)
                window.getCurrentLayer().createCell();
        }
    };

    protected double startX;
    protected double startY;
    protected boolean isPressed;

    private static void event(Consumer<Window> consumer, Window window) {
        consumer.accept(window);
    }

    public abstract void leftClickPress(Window window);
    public abstract void leftClickRelease(Window window);
    public abstract void mouseMove(Window window);
}
