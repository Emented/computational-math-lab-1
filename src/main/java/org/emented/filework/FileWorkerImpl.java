package org.emented.filework;

import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class FileWorkerImpl implements FileWorker {

    @Override
    public InputStream getInputStreamByFileName(String filename) throws FileNotFoundException {
        File file = new File(filename);

        return new FileInputStream(file);
    }
}
