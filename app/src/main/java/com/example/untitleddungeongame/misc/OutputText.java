package com.example.untitleddungeongame.misc;

import java.util.ArrayList;
import java.util.List;

public class OutputText {
    private static String outputText = "";
    private static boolean newText = false;
    private static boolean inDialog = false;
    private static List<DialogEventListener> events = new ArrayList<>();
    public interface DialogEventListener {
        void run(DialogEvent event);
    }

    public enum DialogEvent {
        NONE,
        NEW_TEXT,
        TEXT_UPDATING,
        TEXT_FINISHED,
        CLOSED,
    }

    public static void setOutputText(String text) {
        outputText = text;
        newText = true;
        inDialog = true;
        for (DialogEventListener event : events) {
            event.run(DialogEvent.NEW_TEXT);
        }
    }
    public static void textFinished() {
        for (DialogEventListener event : events) {
            event.run(DialogEvent.TEXT_FINISHED);
        }
    }

    public static void textUpdating() {
        for (DialogEventListener event : events) {
            event.run(DialogEvent.TEXT_UPDATING);
        }
    }

    public static void dialogClosed() {
        for (DialogEventListener event : events) {
            event.run(DialogEvent.CLOSED);
        }
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
        if (!inDialog) {
            dialogClosed();
        }
    }

    public static void addDialogEventListener(DialogEventListener event) {
        events.add(event);
    }

}
