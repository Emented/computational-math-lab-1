package org.emented.io;

import org.emented.messages.ErrorMessage;
import org.emented.messages.UserMessage;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Scanner;

@Service
public class ConsolePrinter implements OutputPrinter {

    @Override
    public void printErrorMessage(ErrorMessage errorMessage) {
        System.err.println(errorMessage);
        System.err.println(ErrorMessage.RERUN_APPLICATION_MESSAGE);
    }

    @Override
    public void printUserMessage(UserMessage userMessage) {
        System.out.println(userMessage);
    }

    @Override
    public String askToInput(UserMessage userMessage, Scanner scanner) {
        try {
            System.out.println(userMessage);
            System.out.print(">>> ");
            return scanner.nextLine();
        } catch (NoSuchElementException e) {
            printErrorMessage(ErrorMessage.NOT_SUPPORTED_SYMBOL_MESSAGE);
            scanner.close();
            return null;
        }
    }
}
