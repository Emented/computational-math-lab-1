package org.emented.io;

import org.emented.ExtendedMatrix;

import java.io.InputStream;

public interface InputWorker {

    ExtendedMatrix readMatrixFromInputStream(InputStream inputStream);

}
