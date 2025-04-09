package com.example.untitleddungeongame.misc;

public class OutputText {
    private static String outputText = "";
    private static boolean newText = false;
    private static boolean inDialog = false;

    public static void setOutputText(String text) {
        outputText = text;
        newText = true;
    }
    public static String getOutputText() {
        return outputText;
    }
    public static boolean isNewText() {
        if (newText) {
            newText = false;
            return true;
        }
        return false;
    }
    public static void setNewText(boolean newText) {
        OutputText.newText = newText;
    }
    public static boolean isInDialog() {
        return inDialog;
    }
    public static void setInDialog(boolean inDialog) {
        OutputText.inDialog = inDialog;
    }
}
