package org.emented.io;

import org.emented.message.ErrorMessage;
import org.emented.message.UserMessage;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.function.Function;
import java.util.function.Predicate;

@Service
public class ConsolePrinter implements OutputPrinter {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";

    @Override
    public void printErrorMessage(ErrorMessage errorMessage) {
        System.out.println(ANSI_RED + errorMessage.message + ANSI_RESET);
    }

    @Override
    public void printUserMessage(UserMessage userMessage) {
        System.out.println(userMessage.message);
    }

    @Override
    public <T> T askToInput(UserMessage userMessage,
                            Scanner scanner,
                            Function<String, T> convertionFunction,
                            ErrorMessage convertationErrorMessage,
                            Predicate<T> validationPredicate,
                            ErrorMessage validationErrorMessage) {
        T result;
        System.out.println(userMessage.message);

        while (true) {
            try {
                System.out.print(">>> ");

                T currentResult = convertionFunction.apply(scanner.nextLine());

                if (validationPredicate.test(currentResult)) {
                    result = currentResult;
                    break;
                } else {
                    printErrorMessage(validationErrorMessage);
                }
            } catch (NoSuchElementException e) {
                printErrorMessage(ErrorMessage.NOT_SUPPORTED_SYMBOL_MESSAGE);
                printErrorMessage(ErrorMessage.RERUN_APPLICATION_MESSAGE);
                System.exit(0);
            } catch (Exception e) {
                printErrorMessage(convertationErrorMessage);
            }
            printErrorMessage(ErrorMessage.TRY_AGAIN_MESSAGE);
        }
        return result;
    }
}
