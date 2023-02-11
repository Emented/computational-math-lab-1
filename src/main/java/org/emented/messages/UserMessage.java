package org.emented.messages;

public enum UserMessage {
    WELCOME_MESSAGE("Welcome to the simple iterations calculation program!"),
    INPUT_TYPE_MESSAGE("Do you want to read data from file " +
            "(by default program reads data from console)? (y/n)"),
    INPUT_PATH_MESSAGE("Input relative path to the file"),
    INPUT_SYSTEM_MESSAGE("Input number of variables and then coefficients of system as extended matrix," +
            " with columns divided by whitespace and rows divided by newline");

    public final String message;

    UserMessage(String message) {
        this.message = message;
    }
}
