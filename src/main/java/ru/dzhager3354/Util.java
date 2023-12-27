package ru.dzhager3354;

public class Util {
    public static double translateCursorX(double cursor, double width) {
        return (cursor - width/2)/(width/2);
    }

    public static double translateCursorY(double cursor, double height) {
        return (height/2 - cursor)/(height/2);
    }
}
