package org.emented.filework;

import java.io.FileNotFoundException;
import java.io.InputStream;

public interface FileWorker {

    InputStream getInputStreamByFileName(String filename) throws FileNotFoundException;

}
