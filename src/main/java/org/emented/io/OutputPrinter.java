package org.emented.io;

import org.emented.message.ErrorMessage;
import org.emented.message.UserMessage;

import java.util.Scanner;

public interface OutputPrinter {

    void printErrorMessage(ErrorMessage errorMessage);
    void printUserMessage(UserMessage userMessage);

    String askToInput(UserMessage userMessage, Scanner scanner);

}
