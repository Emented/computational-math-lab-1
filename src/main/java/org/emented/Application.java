package org.emented;

import org.emented.exception.MatrixRowsAmountMismatchException;
import org.emented.exception.MatrixRowArgumentAmountMismatchException;
import org.emented.file_work.FileWorker;
import org.emented.io.InputWorker;
import org.emented.dto.ExtendedMatrix;
import org.emented.io.OutputPrinter;
import org.emented.message.ErrorMessage;
import org.emented.message.UserMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.function.Function;

@Component
public class Application {

    private final InputWorker inputWorker;
    private final FileWorker fileWorker;
    private final OutputPrinter outputPrinter;
    private final Scanner sc;

    @Autowired
    public Application(InputWorker inputWorker, FileWorker fileWorker, OutputPrinter outputPrinter) {
        this.inputWorker = inputWorker;
        this.fileWorker = fileWorker;
        this.outputPrinter = outputPrinter;
        sc = new Scanner(System.in);
    }

    public void run() {
        outputPrinter.printUserMessage(UserMessage.WELCOME_MESSAGE);

        String answer = outputPrinter.askToInput(UserMessage.INPUT_TYPE_MESSAGE,
                ErrorMessage.FATAL_ERROR,
                sc,
                Function.identity());
        if (answer == null) return;

        InputStream inputStream;

        if ("y".equalsIgnoreCase(answer)) {
            String filename = outputPrinter.askToInput(UserMessage.INPUT_PATH_MESSAGE,
                    ErrorMessage.FATAL_ERROR,
                    sc,
                    Function.identity());
            if (filename == null) return;
            try {
                inputStream = fileWorker.getInputStreamByFileName(filename);
            } catch (FileNotFoundException e) {
                outputPrinter.printErrorMessage(ErrorMessage.FILE_NOT_FOUND_MESSAGE);
                return;
            }
        } else {
            outputPrinter.printUserMessage(UserMessage.INPUT_SYSTEM_MESSAGE);
            inputStream = System.in;
        }

        Double accuracy = outputPrinter.askToInput(UserMessage.INPUT_ACCURACY_MESSAGE,
                ErrorMessage.ACCURACY_TYPE_MISMATCH_MESSAGE,
                sc,
                Double::parseDouble);
        if (accuracy == null) return;

        sc.close();

        ExtendedMatrix extendedMatrix;

        try {
            extendedMatrix = getExtendedMatrix(inputStream);
        } catch (IOException e) {
            outputPrinter.printErrorMessage(ErrorMessage.FATAL_ERROR);
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
            outputPrinter.printErrorMessage(ErrorMessage.SYSTEM_COEFFICIENT_TYPE_MISMATCH_MESSAGE);
        } catch (InputMismatchException e) {
            outputPrinter.printErrorMessage(ErrorMessage.NUMBER_OF_VARIABLES_TYPE_MISMATCH_MESSAGE);
        } catch (MatrixRowArgumentAmountMismatchException e) {
            outputPrinter.printErrorMessage(ErrorMessage.ROW_ARGS_AMOUNT_MISMATCH_MESSAGE);
        } catch (MatrixRowsAmountMismatchException e) {
            outputPrinter.printErrorMessage(ErrorMessage.ROWS_AMOUNT_MISMATCH_MESSAGE);
        } catch (NoSuchElementException e) {
            outputPrinter.printErrorMessage(ErrorMessage.NOT_SUPPORTED_SYMBOL_MESSAGE);
        }

        return extendedMatrix;
    }
}
