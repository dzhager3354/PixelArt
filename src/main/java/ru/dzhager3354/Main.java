package ru.dzhager3354;

import ru.dzhager3354.window.Window;

public class Main {
    public static void main(String[] args) {
        new Thread(new Window()).start();
    }
}
