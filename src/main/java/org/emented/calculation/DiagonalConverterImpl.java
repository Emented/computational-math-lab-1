package org.emented.calculation;

import org.emented.dto.ExtendedMatrix;
import org.springframework.stereotype.Component;

@Component
public class DiagonalConverterImpl implements DiagonalConverter {

    @Override
    public ExtendedMatrix convertToDiagonalPredominant(ExtendedMatrix extendedMatrix) {
        double[][] sourceMatrix = extendedMatrix.getMatrix();
        int matrixSize = extendedMatrix.getNumberOfVariables();
        double[][] newMatrix = new double[matrixSize][matrixSize + 1];
        boolean[] fillOfNewMatrix = new boolean[matrixSize];

        for (int i = 0; i < matrixSize; i++) {
            double maxInRow = Double.MIN_VALUE;
            int maxIndex = 0;

            for (int j = 0; j < matrixSize; j++) {
                if (Math.abs(sourceMatrix[i][j]) > maxInRow) {
                    maxInRow = Math.abs(sourceMatrix[i][j]);
                    maxIndex = j;
                }

                if (fillOfNewMatrix[maxIndex]) return null;

                newMatrix[maxIndex] = sourceMatrix[i];
                fillOfNewMatrix[maxIndex] = true;
            }
        }

        if (checkIfDiagonalPredominant(newMatrix)) {
            return new ExtendedMatrix(matrixSize, newMatrix);
        }
        return null;
    }

    private boolean checkIfDiagonalPredominant(double[][] matrix) {
        int numberOfNonStrict = 0;

        for (int i = 0; i < matrix.length; i++) {
            double absRowSum = 0;
            double absDiagonalValue = Math.abs(matrix[i][i]);
            for (int j = 0; j < matrix.length; j++) {
                absRowSum += Math.abs(matrix[i][j]);
            }
            absRowSum -= absDiagonalValue;
            if (absDiagonalValue < absRowSum) {
                return false;
            } else if (absDiagonalValue == absRowSum) {
                numberOfNonStrict++;
            }
        }

        return numberOfNonStrict != matrix.length;
    }

}
