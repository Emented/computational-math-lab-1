package org.emented.file_work;

import java.io.FileNotFoundException;
import java.io.InputStream;

public interface FileWorker {

    InputStream getInputStreamByFileName(String filename) throws FileNotFoundException;

}
