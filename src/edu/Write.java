package edu;

public class Write {
    static private String getColor(String s) {
        s = s.toLowerCase();
        switch (s) {
            case "black": return "\u001B[30m";
            case "pink": return "\u001B[31m";
            case "green": return "\u001B[32m";
            case "yellow": return "\u001B[33m";
            case "orange": return "\u001B[34m";
            case "purple": return "\u001B[35m";
            case "cyan": return "\u001B[36m";
            case "white": return "\u001B[0m";
            case "gray": return "\u001B[37m";
        }
        return "";
    }
    public static void println(String s, String color) {
        System.out.println(getColor(color)+ s + "\u001B[0m");
    }
    public static void print(String s, String color) {
        System.out.print(getColor(color)+ s + "\u001B[0m");
    }
}
