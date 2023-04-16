package org.emented.io;

import java.io.InputStream;
import java.util.Scanner;

import org.emented.calculation.PredominantMatrixGenerator;
import org.emented.dto.ExtendedMatrix;
import org.emented.message.ErrorMessage;
import org.emented.message.UserMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GeneratorInputWorker extends AbstractInputWorker {

    private final PredominantMatrixGenerator generator;

    @Autowired
    public GeneratorInputWorker(OutputPrinter outputPrinter,
                                PredominantMatrixGenerator generator) {
        super(outputPrinter);
        this.generator = generator;
    }

    @Override
    public ExtendedMatrix getExtendedMatrix(InputStream inputStream) {

        Scanner sc = new Scanner(inputStream);

        Double accuracy = outputPrinter.askToInput(UserMessage.INPUT_ACCURACY_MESSAGE,
                sc,
                str -> Double.parseDouble(str.replaceAll(",", ".")),
                ErrorMessage.ACCURACY_TYPE_MISMATCH_MESSAGE,
                num -> num > 0,
                ErrorMessage.ACCURACY_NUMBER_NEGATIVE_OR_ZERO_MESSAGE);

        Integer numberOfVariables = outputPrinter.askToInput(UserMessage.INPUT_NUMBER_OF_VARIABLES_MESSAGE,
                sc,
                Integer::parseInt,
                ErrorMessage.NUMBER_OF_VARIABLES_TYPE_MISMATCH_MESSAGE,
                num -> num > 0 && num <= 20,
                ErrorMessage.NUMBER_OF_VARIABLES_TYPE_MISMATCH_MESSAGE);

        return generator.generatePredominantMatrix(numberOfVariables, accuracy);
    }
}
