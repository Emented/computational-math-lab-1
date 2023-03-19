package org.emented.calculation;

public interface SystemSolver {
    void prepare();

    void solve(boolean zeidel);

    void printAnswer();
}
