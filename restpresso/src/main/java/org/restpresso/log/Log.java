package org.restpresso.log;

public class Log {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static void info(String message) {
        System.out.printf("%s[INFO] - %s%n", ANSI_BLUE, message);
    }

    public static void info(String format, Object... args) {
        System.out.printf("%s[INFO] - %s%n", ANSI_BLUE, String.format(format, args));
    }

    public static void warn(String warning) {
        System.out.printf("%s[WARN] - %s%n", ANSI_RED, warning);
    }

    public static void warn(String format, Object... args) {
        System.out.printf("%s[WARN] - %s%n", ANSI_RED, String.format(format, args));
    }
}
