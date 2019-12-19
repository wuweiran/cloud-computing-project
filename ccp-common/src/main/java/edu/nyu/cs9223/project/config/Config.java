package edu.nyu.cs9223.project.config;

public class Config {
    private Config() {}

    public static String getBotAlias() {
        return "Prod";
    }

    public static String getBotName() {
        return "ccProject";
    }

    public static String getSearchIntentName() {
        return "SearchIntent";
    }
}
