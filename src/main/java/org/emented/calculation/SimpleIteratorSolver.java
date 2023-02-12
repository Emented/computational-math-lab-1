package org.emented.calculation;

import org.emented.dto.ExtendedMatrix;

public class SimpleIteratorSolver implements SystemSolver {

    private final ExtendedMatrix extendedMatrix;
    private final double accuracy;
    private int iterationsCount = 0;

    public SimpleIteratorSolver(ExtendedMatrix extendedMatrix, double accuracy) {
        this.extendedMatrix = extendedMatrix;
        this.accuracy = accuracy;
    }

    @Override
    public void prepare() {

    }

    @Override
    public void solve() {

    }

    @Override
    public void printAnswer() {

    }
}
