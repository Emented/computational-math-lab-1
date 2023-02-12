package org.emented.message;

public enum ErrorMessage {
    FILE_NOT_FOUND_MESSAGE("File not found or unreadable"),
    SYSTEM_COEFFICIENT_TYPE_MISMATCH_MESSAGE("All system coefficients must be numbers in range (-2^32, 2^32 - 1)"),
    NUMBER_OF_VARIABLES_TYPE_MISMATCH_MESSAGE("Number of variables must be a number in range (-2^32, 2^32 - 1)"),
    ACCURACY_TYPE_MISMATCH_MESSAGE("Accuracy must be a floating point number"),
    ROW_ARGS_AMOUNT_MISMATCH_MESSAGE("Each row of the extended matrix should contain (number of variables + 1) numbers"),
    ROWS_AMOUNT_MISMATCH_MESSAGE("Matrix should contain (number of variables) rows"),
    NOT_SUPPORTED_SYMBOL_MESSAGE("Input contains not supported symbol"),
    FATAL_ERROR("Something went wrong"),
    RERUN_APPLICATION_MESSAGE("Fix input, rerun the application and try again!"),
    IMPOSSIBLE_TO_CONVERT_TO_DIAGONAL_MESSAGE("Impossible to convert to diagonal predominant form");


    public final String message;

    ErrorMessage(String message) {
        this.message = message;
    }
}
