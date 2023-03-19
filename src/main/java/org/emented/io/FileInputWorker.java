package org.emented.io;

import java.io.InputStream;
import java.util.Scanner;

import org.emented.dto.ExtendedMatrix;
import org.emented.exception.AccuracyOrNumberOfVariablesNotInputtedException;
import org.emented.exception.AccuracyTypeMismatchException;
import org.emented.exception.NumberOfVariablesTypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileInputWorker extends AbstractInputWorker {

    @Autowired
    public FileInputWorker(OutputPrinter outputPrinter) {
        super(outputPrinter);
    }

    @Override
    public ExtendedMatrix getExtendedMatrix(InputStream inputStream) {
        Scanner sc = new Scanner(inputStream);

        String[] variablesAndAccuracy = sc.nextLine().split(SPLIT_REGEX);

        if (variablesAndAccuracy.length != 2) {
            throw new AccuracyOrNumberOfVariablesNotInputtedException();
        }

        int numberOfVariables;

        try {
            numberOfVariables = Integer.parseInt(variablesAndAccuracy[0]);
        } catch (NumberFormatException e) {
            throw new NumberOfVariablesTypeMismatchException();
        }

        double accuracy;

        try {
            accuracy = Double.parseDouble(variablesAndAccuracy[1].replaceAll(",", "."));
        } catch (NumberFormatException e) {
            throw new AccuracyTypeMismatchException();
        }

        double[][] matrix = readMatrix(sc, numberOfVariables);

        return new ExtendedMatrix(numberOfVariables, accuracy, matrix);
    }
}
