package org.emented.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExtendedMatrix {

    private int numberOfVariables;
    private double[][] matrix;

    public void print() {
        for (double[] row : matrix) {
            for (double el : row) {
                System.out.print(el + " ");
            }
            System.out.println();
        }
    }

}
