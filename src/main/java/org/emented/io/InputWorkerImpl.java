package org.emented.io;

import org.emented.ExtendedMatrix;
import org.emented.exception.MatrixRowAmountMismatchException;
import org.emented.exception.MatrixRowArgumentAmountMismatchException;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Scanner;

@Service
public class InputWorkerImpl implements InputWorker {

    private static final String SPLIT_REGEX = " ";

    public ExtendedMatrix readMatrixFromInputStream(InputStream inputStream) {
        Scanner sc = new Scanner(inputStream);

        int numberOfVariables = sc.nextInt();
        sc.nextLine();

        int[][] extendedMatrix = new int[numberOfVariables][numberOfVariables + 1];

        for (int i = 0; i < numberOfVariables; i++) {
            String row = sc.nextLine().trim();
            if (row.isEmpty()) {
                throw new MatrixRowAmountMismatchException();
            }

            String[] splitted_row = row.split(SPLIT_REGEX);
            if (splitted_row.length != numberOfVariables + 1) {
                throw new MatrixRowArgumentAmountMismatchException();
            }

            int[] converted_line = Arrays.stream(splitted_row).mapToInt(Integer::parseInt).toArray();
            extendedMatrix[i] = converted_line;
        }

        return new ExtendedMatrix(numberOfVariables, extendedMatrix);
    }

}
