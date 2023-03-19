package org.emented.calculation;

import java.util.Random;

import org.emented.dto.ExtendedMatrix;
import org.springframework.stereotype.Component;

@Component
public class PredominantMatrixGeneratorImpl implements PredominantMatrixGenerator {

    private static final double RANGE = 10;

    @Override
    public ExtendedMatrix generatePredominantMatrix(int numberOfVariables, double accuracy) {
        Random random = new Random();

        double[][] generatedMatrix = new double[numberOfVariables][numberOfVariables + 1];

        for (int i = 0; i < numberOfVariables; i++) {

            double absSumOfRow = 0;

            for (int j = 0; j < numberOfVariables; j++) {
                if (i != j) {
                    double current = random.nextDouble(RANGE * 2) - RANGE;
                    absSumOfRow += Math.abs(current);
                    generatedMatrix[i][j] = current;
                }
            }

            generatedMatrix[i][numberOfVariables] = random.nextDouble(RANGE * 2) - RANGE;

            generatedMatrix[i][i] = Math.round(Math.random()) == 1 ? absSumOfRow + 0.1 : -absSumOfRow - 0.1;
        }

        shuffleRows(numberOfVariables, generatedMatrix);

        return new ExtendedMatrix(numberOfVariables, accuracy, generatedMatrix);
    }

    private void shuffleRows(int numberOfVariables, double[][] matrix) {

        Random random = new Random();

        for (int i = 0; i < numberOfVariables; i++) {
            int randomIndex = random.nextInt(numberOfVariables);
            double[] temp = matrix[randomIndex];
            matrix[randomIndex] = matrix[i];
            matrix[i] = temp;
        }

    }

}
