package org.emented.message;

public enum UserMessage {
    WELCOME_MESSAGE("Welcome to the simple iterations calculation program!"),
    INPUT_TYPE_MESSAGE("Do you want to read data from file " +
            "(by default program reads data from console)? (y/n)"),
    INPUT_PATH_MESSAGE("Input relative path to the file"),
    INPUT_MATRIX_MESSAGE("Input coefficients of system as extended matrix," +
            " with columns divided by whitespace and rows divided by newline"),
    INPUT_ACCURACY_MESSAGE("Input accuracy of calculation"),
    MATRIX_AFTER_DIAGONALIZATION_MESSAGE("Source matrix after cast into predominant diagonal form:"),
    GENERATE_NUMBERS_MESSAGE("Do you want to generate numbers for system " +
            "(by default you have to input them yourself)? (y/n)"),
    INPUT_NUMBER_OF_VARIABLES_MESSAGE("Input number of variables"),
    SOURCE_MATRIX_MESSAGE("Source matrix: ");
    public final String message;

    UserMessage(String message) {
        this.message = message;
    }
}
