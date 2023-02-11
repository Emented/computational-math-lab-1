package org.emented;

import org.emented.exception.MatrixRowsAmountMismatchException;
import org.emented.exception.MatrixRowArgumentAmountMismatchException;
import org.emented.file_work.FileWorker;
import org.emented.io.InputWorker;
import org.emented.dto.ExtendedMatrix;
import org.emented.messages.ErrorMessages;
import org.emented.messages.UserMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

@Component
public class Application {

    private final InputWorker inputWorker;
    private final FileWorker fileWorker;
    private final Scanner sc;

    @Autowired
    public Application(InputWorker inputWorker, FileWorker fileWorker) {
        this.inputWorker = inputWorker;
        this.fileWorker = fileWorker;
        sc = new Scanner(System.in);
    }

    public void run() {
        System.out.println(UserMessages.WELCOME_MESSAGE);

        String answer = askToInput(UserMessages.INPUT_TYPE_MESSAGE);
        if (answer == null) return;

        InputStream inputStream;

        if ("y".equalsIgnoreCase(answer)) {
            String filename = askToInput(UserMessages.INPUT_PATH_MESSAGE);
            if (filename == null) return;
            try {
                inputStream = fileWorker.getInputStreamByFileName(filename);
            } catch (FileNotFoundException e) {
                printErrorMessage(ErrorMessages.FILE_NOT_FOUND_MESSAGE);
                return;
            }
        } else {
            System.out.println(UserMessages.INPUT_SYSTEM_MESSAGE);
            inputStream = System.in;
        }

        sc.close();

        ExtendedMatrix extendedMatrix;

        try {
            extendedMatrix = getExtendedMatrix(inputStream);
        } catch (IOException e) {
            printErrorMessage(ErrorMessages.FATAL_ERROR);
            return;
        }

        if (extendedMatrix == null) return;

        extendedMatrix.print();

        sc.close();
    }

    private ExtendedMatrix getExtendedMatrix(InputStream inputStream) throws IOException {
        ExtendedMatrix extendedMatrix = null;

        try (inputStream) {
            extendedMatrix = inputWorker.readMatrixFromInputStream(inputStream);
        } catch (NumberFormatException e) {
            printErrorMessage(ErrorMessages.SYSTEM_COEFFICIENT_TYPE_MISMATCH_MESSAGE);
        } catch (InputMismatchException e) {
            printErrorMessage(ErrorMessages.NUMBER_OF_VARIABLES_TYPE_MISMATCH_MESSAGE);
        } catch (MatrixRowArgumentAmountMismatchException e) {
            printErrorMessage(ErrorMessages.ROW_ARGS_AMOUNT_MISMATCH_MESSAGE);
        } catch (MatrixRowsAmountMismatchException e) {
            printErrorMessage(ErrorMessages.ROWS_AMOUNT_MISMATCH_MESSAGE);
        } catch (NoSuchElementException e) {
            printErrorMessage(ErrorMessages.NOT_SUPPORTED_SYMBOL_MESSAGE);
        }

        return extendedMatrix;
    }

    private String askToInput(UserMessages userMessage) {
        try {
            System.out.println(userMessage);
            System.out.print(">>> ");
            return sc.nextLine();
        } catch (NoSuchElementException e) {
            printErrorMessage(ErrorMessages.NOT_SUPPORTED_SYMBOL_MESSAGE);
            sc.close();
            return null;
        }
    }

    private void printErrorMessage(ErrorMessages errorMessage) {
        System.err.println(errorMessage);
        System.err.println("Fix input, rerun the application and try again!");
    }
}
