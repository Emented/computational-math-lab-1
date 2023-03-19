package org.emented.message;

public enum ErrorMessage {
    FILE_NOT_FOUND_MESSAGE("File not found or unreadable"),
    SYSTEM_COEFFICIENT_TYPE_MISMATCH_MESSAGE("All system coefficients must be numbers in range (-2^32, 2^32 - 1)"),
    NUMBER_OF_VARIABLES_TYPE_MISMATCH_MESSAGE("Number of variables must be a number in range (0, 2^32 - 1)"),
    ACCURACY_TYPE_MISMATCH_MESSAGE("Accuracy must be a floating point number"),
    ACCURACY_NUMBER_NEGATIVE_OR_ZERO_MESSAGE("Accuracy must be a greater then 0 floating point number"),
    ROW_ARGS_AMOUNT_MISMATCH_MESSAGE("Each row of the extended matrix should contain (number of variables + 1) numbers"),
    ROWS_AMOUNT_MISMATCH_MESSAGE("Matrix should contain (number of variables) rows"),
    NOT_SUPPORTED_SYMBOL_MESSAGE("Input contains not supported symbol"),
    YES_NO_QUESTION_ANSWER_VALIDATION_ERROR_MESSAGE("Input y(yes)/n(no)"),
    FATAL_ERROR("Something went wrong"),
    RERUN_APPLICATION_MESSAGE("Fix input, rerun the application and try again!"),
    TRY_AGAIN_MESSAGE("Please try again!"),
    IMPOSSIBLE_TO_CONVERT_TO_DIAGONAL_MESSAGE("Impossible to convert to diagonal predominant form"),
    ACCURACY_AND_NUMBER_MESSAGE("First string of the file should look like: {numberOfVars} {accuracy}"),
    ZERO_ROW_MESSAGE("Row of matrix should not have all zeros");


    public final String message;

    ErrorMessage(String message) {
        this.message = message;
    }
}
