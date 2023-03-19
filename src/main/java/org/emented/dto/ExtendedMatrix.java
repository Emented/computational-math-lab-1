package org.emented.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExtendedMatrix {

    private int numberOfVariables;

    private double accuracy;
    private double[][] matrix;

    public void print() {
        for (double[] row : matrix) {
            for (double el : row) {
                System.out.printf("%.5f ", el);
                if (el >= 0) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

}
