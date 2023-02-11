package org.emented.io;

import org.emented.messages.ErrorMessage;
import org.emented.messages.UserMessage;

import java.util.Scanner;

public interface OutputPrinter {

    void printErrorMessage(ErrorMessage errorMessage);
    void printUserMessage(UserMessage userMessage);

    String askToInput(UserMessage userMessage, Scanner scanner);

}
