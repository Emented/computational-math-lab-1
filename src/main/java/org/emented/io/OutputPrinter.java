package org.emented.io;

import org.emented.message.ErrorMessage;
import org.emented.message.UserMessage;

import java.util.Scanner;
import java.util.function.Function;
import java.util.function.Predicate;

public interface OutputPrinter {

    void printErrorMessage(ErrorMessage errorMessage);
    void printUserMessage(UserMessage userMessage);

    <T> T askToInput(UserMessage userMessage,
                     Scanner scanner,
                     Function<String, T> convertionFunction,
                     ErrorMessage convertationErrorMessage,
                     Predicate<T> validationPredicate,
                     ErrorMessage validationErrorMessage);

}
