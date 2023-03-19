package org.emented.io;

import org.emented.dto.ExtendedMatrix;
import org.emented.exception.MatrixRowsAmountMismatchException;
import org.emented.exception.MatrixRowArgumentAmountMismatchException;
import org.emented.exception.NumberOfVariablesTypeMismatchException;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Scanner;

@Service
public class InputWorkerImpl implements InputWorker {

    private static final String SPLIT_REGEX = " ";

    public ExtendedMatrix readMatrixFromInputStream(InputStream inputStream) {
        Scanner sc = new Scanner(inputStream);

        int numberOfVariables;

        try {
            numberOfVariables = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            throw new NumberOfVariablesTypeMismatchException();
        }

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
            extendedMatrix[i] = converted_line;
        }

        return new ExtendedMatrix(numberOfVariables, extendedMatrix);
    }

}
