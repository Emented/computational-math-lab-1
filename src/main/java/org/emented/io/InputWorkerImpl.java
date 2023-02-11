package org.emented.io;

import org.emented.ExtendedMatrix;
import org.emented.exception.MatrixRowArgumentAmountMismatchException;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Scanner;

@Component
public class InputWorkerImpl implements InputWorker {

    private static final String SPLIT_REGEX = " ";

    public ExtendedMatrix readMatrixFromInputStream(InputStream inputStream) {
        Scanner sc = new Scanner(inputStream);

        int numberOfVariables = sc.nextInt();
        sc.nextLine();

        int[][] extendedMatrix = new int[numberOfVariables][numberOfVariables + 1];

        for (int i = 0; i < numberOfVariables; i++) {
            String[] splitted_line = sc.nextLine().trim().split(SPLIT_REGEX);
            if (splitted_line.length != numberOfVariables + 1) {
                throw new MatrixRowArgumentAmountMismatchException();
            }
            int[] converted_line = Arrays.stream(splitted_line).mapToInt(Integer::parseInt).toArray();
            extendedMatrix[i] = converted_line;
        }

        return new ExtendedMatrix(numberOfVariables, extendedMatrix);
    }

}
