package org.emented.calculation;

import org.emented.dto.ExtendedMatrix;

public interface PredominantMatrixGenerator {

    ExtendedMatrix generatePredominantMatrix(int numberOfVariables, double accuracy);

}
