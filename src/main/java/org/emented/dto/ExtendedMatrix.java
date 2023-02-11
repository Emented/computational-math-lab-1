package org.emented.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExtendedMatrix {

    private int numberOfVariables;
    private int[][] matrix;

    public void print() {
        for (int[] row : matrix) {
            for (int el : row) {
                System.out.print(el + " ");
            }
            System.out.println();
        }
    }

}
