package org.emented.io;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Scanner;

import org.emented.dto.ExtendedMatrix;
import org.emented.exception.MatrixRowArgumentAmountMismatchException;
import org.emented.exception.MatrixRowsAmountMismatchException;
import org.emented.exception.ZeroRowException;

public abstract class AbstractInputWorker {

    protected static final String SPLIT_REGEX = "\\s+";

    protected final OutputPrinter outputPrinter;

    public AbstractInputWorker(OutputPrinter outputPrinter) {
        this.outputPrinter = outputPrinter;
    }

    public abstract ExtendedMatrix getExtendedMatrix(InputStream inputStream);

    protected double[][] readMatrix(Scanner sc, int numberOfVariables) {

        double[][] extendedMatrix = new double[numberOfVariables][numberOfVariables + 1];

        for (int i = 0; i < numberOfVariables; i++) {
            String row = sc.nextLine().trim();
            if (row.isEmpty()) {
                throw new MatrixRowsAmountMismatchException();
            }

            String[] splitted_row = row.split(SPLIT_REGEX);
            if (splitted_row.length != numberOfVariables + 1) {
                throw new MatrixRowArgumentAmountMismatchException();
            }

            double[] converted_line = Arrays.stream(splitted_row)
                    .mapToDouble(el -> Double.parseDouble(el.replaceAll(",", ".")))
                    .toArray();

            double absRowSum = Arrays.stream(converted_line)
                    .limit(converted_line.length - 1)
                    .map(Math::abs)
                    .sum();

            if (absRowSum == 0) {
                throw new ZeroRowException();
            }
            extendedMatrix[i] = converted_line;
        }

        return extendedMatrix;

    }

}
