package org.emented.io;

import org.emented.message.ErrorMessage;
import org.emented.message.UserMessage;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.function.Function;

@Service
public class ConsolePrinter implements OutputPrinter {

    @Override
    public void printErrorMessage(ErrorMessage errorMessage) {
        System.err.println(errorMessage.message);
        System.err.println(ErrorMessage.RERUN_APPLICATION_MESSAGE.message);
    }

    @Override
    public void printUserMessage(UserMessage userMessage) {
        System.out.println(userMessage.message);
    }

    @Override
    public <T> T askToInput(UserMessage userMessage,
                        ErrorMessage errorMessage,
                        Scanner scanner,
                        Function<String, T> convertionFunction) {
        try {
            System.out.println(userMessage.message);
            System.out.print(">>> ");
            return convertionFunction.apply(scanner.nextLine());
        } catch (NoSuchElementException e) {
            printErrorMessage(ErrorMessage.NOT_SUPPORTED_SYMBOL_MESSAGE);
            scanner.close();
            return null;
        } catch (Exception e) {
            printErrorMessage(errorMessage);
            scanner.close();
            return null;
        }
    }
}
