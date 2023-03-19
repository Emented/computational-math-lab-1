package org.emented;

import org.emented.calculation.PredominantDiagonalConverterImpl;
import org.emented.calculation.PredominantMatrixGenerator;
import org.emented.calculation.SimpleIteratorSolver;
import org.emented.exception.MatrixRowsAmountMismatchException;
import org.emented.exception.MatrixRowArgumentAmountMismatchException;
import org.emented.exception.NumberOfVariablesTypeMismatchException;
import org.emented.filework.FileWorker;
import org.emented.io.InputWorker;
import org.emented.dto.ExtendedMatrix;
import org.emented.io.OutputPrinter;
import org.emented.message.ErrorMessage;
import org.emented.message.UserMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.function.Function;
import java.util.function.Predicate;


@Component
public class Application {

    private static final Predicate<String> YES_NO_VALIDATION_PREDICATE =
            el -> el.equalsIgnoreCase("y") || el.equalsIgnoreCase("n") || el.isBlank();

    private static final Predicate<String> FILENAME_VALIDATION_PREDICATE = el -> {
        File file = new File(el);
        return file.exists() && !file.isDirectory();
    };

    private final InputWorker inputWorker;
    private final FileWorker fileWorker;
    private final OutputPrinter outputPrinter;
    private final PredominantDiagonalConverterImpl diagonalConverter;
    private final PredominantMatrixGenerator predominantMatrixGenerator;
    private final Scanner sc;

    @Autowired
    public Application(InputWorker inputWorker,
                       FileWorker fileWorker,
                       OutputPrinter outputPrinter,
                       PredominantDiagonalConverterImpl diagonalConverter,
                       PredominantMatrixGenerator predominantMatrixGenerator) {
        this.inputWorker = inputWorker;
        this.fileWorker = fileWorker;
        this.outputPrinter = outputPrinter;
        this.diagonalConverter = diagonalConverter;
        this.predominantMatrixGenerator = predominantMatrixGenerator;
        sc = new Scanner(System.in);
    }

    public void run() {
        outputPrinter.printUserMessage(UserMessage.WELCOME_MESSAGE);

        ExtendedMatrix extendedMatrix;

        String generateNumbersAnswer = outputPrinter.askToInput(UserMessage.GENERATE_NUMBERS_MESSAGE,
                sc,
                Function.identity(),
                ErrorMessage.FATAL_ERROR,
                YES_NO_VALIDATION_PREDICATE,
                ErrorMessage.YES_NO_QUESTION_ANSWER_VALIDATION_ERROR_MESSAGE);

        Double accuracy = outputPrinter.askToInput(UserMessage.INPUT_ACCURACY_MESSAGE,
                sc,
                str -> Double.parseDouble(str.replaceAll(",", ".")),
                ErrorMessage.ACCURACY_TYPE_MISMATCH_MESSAGE,
                num -> num > 0,
                ErrorMessage.ACCURACY_NUMBER_NEGATIVE_OR_ZERO_MESSAGE);

        if ("y".equalsIgnoreCase(generateNumbersAnswer)) {
            Integer numberOfVariables = outputPrinter.askToInput(UserMessage.INPUT_NUMBER_OF_VARIABLES_MESSAGE,
                    sc,
                    Integer::parseInt,
                    ErrorMessage.NUMBER_OF_VARIABLES_TYPE_MISMATCH_MESSAGE,
                    num -> num > 0,
                    ErrorMessage.NUMBER_OF_VARIABLES_TYPE_MISMATCH_MESSAGE);

            extendedMatrix = predominantMatrixGenerator.generatePredominantMatrix(numberOfVariables);
        } else {
            String inputAnswer = outputPrinter.askToInput(UserMessage.INPUT_TYPE_MESSAGE,
                    sc,
                    Function.identity(),
                    ErrorMessage.FATAL_ERROR,
                    YES_NO_VALIDATION_PREDICATE,
                    ErrorMessage.YES_NO_QUESTION_ANSWER_VALIDATION_ERROR_MESSAGE);

            InputStream inputStream;

            if ("y".equalsIgnoreCase(inputAnswer)) {
                String filename = outputPrinter.askToInput(UserMessage.INPUT_PATH_MESSAGE,
                        sc,
                        Function.identity(),
                        ErrorMessage.FATAL_ERROR,
                        FILENAME_VALIDATION_PREDICATE,
                        ErrorMessage.FILE_NOT_FOUND_MESSAGE);
                try {
                    inputStream = fileWorker.getInputStreamByFileName(filename);
                } catch (FileNotFoundException e) {
                    outputPrinter.printErrorMessage(ErrorMessage.FILE_NOT_FOUND_MESSAGE);
                    return;
                }

                extendedMatrix = getExtendedMatrixFromFile(inputStream);
            } else {
                outputPrinter.printUserMessage(UserMessage.INPUT_SYSTEM_MESSAGE);
                inputStream = System.in;

                extendedMatrix = getExtendedMatrixFromConsole(inputStream);
            }

            if (extendedMatrix == null) return;
        }

        outputPrinter.printUserMessage(UserMessage.SOURCE_MATRIX_MESSAGE);
        extendedMatrix.print();

        System.out.println();

        extendedMatrix = diagonalConverter.convertToDiagonalPredominant(extendedMatrix);

        if (extendedMatrix == null) {
            outputPrinter.printErrorMessage(ErrorMessage.IMPOSSIBLE_TO_CONVERT_TO_DIAGONAL_MESSAGE);
            return;
        }

        outputPrinter.printUserMessage(UserMessage.MATRIX_AFTER_DIAGONALIZATION_MESSAGE);
        extendedMatrix.print();

        SimpleIteratorSolver simpleIteratorSolver = new SimpleIteratorSolver(extendedMatrix, accuracy);

        simpleIteratorSolver.prepare();
        simpleIteratorSolver.solve();
        simpleIteratorSolver.printAnswer();

        sc.close();
    }

    private ExtendedMatrix getExtendedMatrixFromFile(InputStream inputStream) {
        return getExtendedMatrix(inputStream);
    }

    private ExtendedMatrix getExtendedMatrixFromConsole(InputStream inputStream) {
        ExtendedMatrix extendedMatrix = null;

        while (null == extendedMatrix) {
            extendedMatrix = getExtendedMatrix(inputStream);

            if (null == extendedMatrix) {
                outputPrinter.printErrorMessage(ErrorMessage.TRY_AGAIN_MESSAGE);
            }
        }

        return extendedMatrix;
    }

    private ExtendedMatrix getExtendedMatrix(InputStream inputStream) {
        ExtendedMatrix extendedMatrix = null;

        try {
            extendedMatrix = inputWorker.readMatrixFromInputStream(inputStream);
        } catch (NumberFormatException e) {
            outputPrinter.printErrorMessage(ErrorMessage.SYSTEM_COEFFICIENT_TYPE_MISMATCH_MESSAGE);
        } catch (InputMismatchException | NumberOfVariablesTypeMismatchException e) {
            outputPrinter.printErrorMessage(ErrorMessage.NUMBER_OF_VARIABLES_TYPE_MISMATCH_MESSAGE);
        } catch (MatrixRowArgumentAmountMismatchException e) {
            outputPrinter.printErrorMessage(ErrorMessage.ROW_ARGS_AMOUNT_MISMATCH_MESSAGE);
        } catch (MatrixRowsAmountMismatchException e) {
            outputPrinter.printErrorMessage(ErrorMessage.ROWS_AMOUNT_MISMATCH_MESSAGE);
        } catch (NoSuchElementException e) {
            outputPrinter.printErrorMessage(ErrorMessage.NOT_SUPPORTED_SYMBOL_MESSAGE);
            outputPrinter.printErrorMessage(ErrorMessage.RERUN_APPLICATION_MESSAGE);
            System.exit(0);
        }


        return extendedMatrix;
    }
}
