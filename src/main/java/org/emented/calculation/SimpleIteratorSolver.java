package org.emented.calculation;

import lombok.Data;
import org.emented.dto.ExtendedMatrix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public class SimpleIteratorSolver implements SystemSolver {

    private final ExtendedMatrix extendedMatrix;
    private final double accuracy;
    private int iterationsCount = 0;

    private final List<double[]> approximation;
    private final List<double[]> errors;

    private double[] answer;

    public SimpleIteratorSolver(ExtendedMatrix extendedMatrix, double accuracy) {
        this.extendedMatrix = extendedMatrix;
        this.accuracy = accuracy;
        approximation = new ArrayList<>();
        errors = new ArrayList<>();
        answer = new double[extendedMatrix.getNumberOfVariables()];
    }

    @Override
    public void prepare() {
        int numberOfVariables = extendedMatrix.getNumberOfVariables();
        double[] firstEntry = new double[numberOfVariables];
        double[][] matrix = extendedMatrix.getMatrix();

        for (int i = 0; i < numberOfVariables; i++) {
            double maxInRow = Double.MIN_VALUE;
            for (int j = 0; j < numberOfVariables; j++) {
                maxInRow = Math.max(maxInRow, Math.abs(matrix[i][j]));
            }

            for (int j = 0; j <= numberOfVariables; j++) {
                if (i == j) {
                    matrix[i][j] = 0;
                } else {
                    matrix[i][j] /= -maxInRow;

                    if (j == numberOfVariables) {
                        matrix[i][j] *= -1;
                    }
                }
            }

            firstEntry[i] = matrix[i][numberOfVariables];
        }
        approximation.add(firstEntry);

    }

    @Override
    public void solve() {
        int numberOfVariables = extendedMatrix.getNumberOfVariables();
        double[][] matrix = extendedMatrix.getMatrix();

        do {
            approximation.add(new double[numberOfVariables]);
            errors.add(new double[numberOfVariables]);

            for (int i = 0; i < numberOfVariables; i++) {
                double newX = 0;

                for (int j = 0; j < numberOfVariables; j++) {
                    if (i != j) {
                        newX += matrix[i][j] * approximation.get(iterationsCount)[j];
                    }
                }

                newX += matrix[i][numberOfVariables];
                errors.get(iterationsCount)[i] = Math.abs(newX - approximation.get(iterationsCount)[i]);
                approximation.get(iterationsCount + 1)[i] = newX;
            }

            iterationsCount++;

        } while (!(Arrays.stream(errors.get(errors.size() - 1)).max().getAsDouble() <= accuracy));

        answer = approximation.get(approximation.size() - 1);

    }

    @Override
    public void printAnswer() {
        System.out.println("\nAnswer:");

        for (int i = 0; i < answer.length; i++) {
            System.out.printf("x_%d = %.10f\n", i + 1, answer[i]);
        }

        System.out.printf("Number of iterations: %d\n", iterationsCount);

        System.out.println("Errors: ");
        for (int i = 0; i < errors.size(); i++) {
            System.out.printf("%d iteration: %.12f\n",
                    i + 1,
                    errors.get(i)[extendedMatrix.getNumberOfVariables() - 1]);
        }
    }
}
