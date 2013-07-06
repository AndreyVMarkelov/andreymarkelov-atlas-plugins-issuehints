package ru.andreymarkelov.atlas.plugins;

/**
 * Counter.
 * 
 * @author Andrey Markelov
 */
public class Counter {
    private static long curr = System.currentTimeMillis();

    /**
     * Private constructor.
     */
    private Counter() {
    }

    /**
     * Get current mills.
     */
    public static synchronized long getValue() {
        return curr++;
    }
}
