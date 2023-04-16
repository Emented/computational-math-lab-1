package org.emented.io;

import java.io.InputStream;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.emented.dto.ExtendedMatrix;
import org.emented.exception.MatrixRowArgumentAmountMismatchException;
import org.emented.exception.MatrixRowsAmountMismatchException;
import org.emented.exception.NumberOfVariablesTypeMismatchException;
import org.emented.exception.ZeroRowException;
import org.emented.message.ErrorMessage;
import org.emented.message.UserMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConsoleInputWorker extends AbstractInputWorker {

    @Autowired
    public ConsoleInputWorker(OutputPrinter outputPrinter) {
        super(outputPrinter);
    }

    @Override
    public ExtendedMatrix getExtendedMatrix(InputStream inputStream) {
        Scanner sc = new Scanner(inputStream);

        Double accuracy = outputPrinter.askToInput(UserMessage.INPUT_ACCURACY_MESSAGE,
                sc,
                str -> Double.parseDouble(str.replaceAll(",", ".")),
                ErrorMessage.ACCURACY_TYPE_MISMATCH_MESSAGE,
                num -> num > 0,
                ErrorMessage.ACCURACY_NUMBER_NEGATIVE_OR_ZERO_MESSAGE);

        Integer numberOfVariables = outputPrinter.askToInput(UserMessage.INPUT_NUMBER_OF_VARIABLES_MESSAGE,
                sc,
                Integer::parseInt,
                ErrorMessage.NUMBER_OF_VARIABLES_TYPE_MISMATCH_MESSAGE,
                num -> num > 0 && num <= 20,
                ErrorMessage.NUMBER_OF_VARIABLES_TYPE_MISMATCH_MESSAGE);

        outputPrinter.printUserMessage(UserMessage.INPUT_MATRIX_MESSAGE);

        double[][] matrix = null;
        while (null == matrix) {
            try {
                matrix = readMatrix(sc, numberOfVariables);
            } catch (NumberFormatException e) {
                outputPrinter.printErrorMessage(ErrorMessage.SYSTEM_COEFFICIENT_TYPE_MISMATCH_MESSAGE);
            } catch (InputMismatchException | NumberOfVariablesTypeMismatchException e) {
                outputPrinter.printErrorMessage(ErrorMessage.NUMBER_OF_VARIABLES_TYPE_MISMATCH_MESSAGE);
            } catch (MatrixRowArgumentAmountMismatchException e) {
                outputPrinter.printErrorMessage(ErrorMessage.ROW_ARGS_AMOUNT_MISMATCH_MESSAGE);
            } catch (MatrixRowsAmountMismatchException e) {
                outputPrinter.printErrorMessage(ErrorMessage.ROWS_AMOUNT_MISMATCH_MESSAGE);
            } catch (ZeroRowException e) {
                outputPrinter.printErrorMessage(ErrorMessage.ZERO_ROW_MESSAGE);
            } catch (NoSuchElementException e) {
                outputPrinter.printErrorMessage(ErrorMessage.NOT_SUPPORTED_SYMBOL_MESSAGE);
                outputPrinter.printErrorMessage(ErrorMessage.RERUN_APPLICATION_MESSAGE);
                System.exit(0);
            }
            if (null == matrix) {
                outputPrinter.printErrorMessage(ErrorMessage.TRY_AGAIN_MESSAGE);
            }
        }

        return new ExtendedMatrix(numberOfVariables, accuracy, matrix);

    }
}
