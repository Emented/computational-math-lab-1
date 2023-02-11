package org.emented;

import org.emented.exception.MatrixRowAmountMismatchException;
import org.emented.exception.MatrixRowArgumentAmountMismatchException;
import org.emented.file_work.FileWorker;
import org.emented.io.InputWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

@Component
public class Application {

    private final InputWorker inputWorker;
    private final FileWorker fileWorker;
    private final Scanner sc;

    @Autowired
    public Application(InputWorker inputWorker, FileWorker fileWorker) {
        this.inputWorker = inputWorker;
        this.fileWorker = fileWorker;
        sc = new Scanner(System.in);
    }

    public void run(String[] args) {
        System.out.println("Welcome to the simple iterations calculation program!");

        String answer = askToInput("Do you want to read data from file " +
                "(by default program reads data from console)? (y/n)");

        if (answer == null) return;

        InputStream inputStream;

        if ("y".equalsIgnoreCase(answer)) {
            String filename = askToInput("Input relative path to the file");
            if (filename == null) return;

            inputStream = fileWorker.getInputStreamByFileName(filename);
        } else {
            System.out.println("Input number of variables and then coefficients of system as extended matrix," +
                    " with columns divided by whitespace and rows divided by newline");
            inputStream = System.in;
        }

        ExtendedMatrix extendedMatrix;

        try {
            extendedMatrix = inputWorker.readMatrixFromInputStream(inputStream);
        } catch (NumberFormatException e) {
            printErrorMessage("All system coefficients must be numbers in range (-2^32, 2^32 - 1)");
            return;
        } catch (InputMismatchException e) {
            printErrorMessage("Number of variables must be a number in range (-2^32, 2^32 - 1)");
            return;
        } catch (MatrixRowArgumentAmountMismatchException e) {
            printErrorMessage("Each row of the extended matrix should contain (number of variables + 1) numbers");
            return;
        } catch (MatrixRowAmountMismatchException e) {
            printErrorMessage("Matrix should contain (number of variables) rows");
            return;
        } catch (NoSuchElementException e) {
            printErrorMessage("Input contains not supported symbol");
            return;
        }

        extendedMatrix.print();

        sc.close();
    }

    private String askToInput(String stringToAskWith) {
        try {
            System.out.println(stringToAskWith);
            System.out.print(">>> ");
            return sc.nextLine();
        } catch (NoSuchElementException e) {
            printErrorMessage("Input contains not supported symbol");
            return null;
        }
    }

    private void printErrorMessage(String message) {
        System.err.println(message);
        System.err.println("Fix input, rerun the application and try again!");
    }
}
