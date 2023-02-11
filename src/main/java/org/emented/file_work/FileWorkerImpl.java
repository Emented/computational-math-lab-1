package org.emented.file_work;

import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class FileWorkerImpl implements FileWorker {

    @Override
    public InputStream getInputStreamByFileName(String filename) {
        return null;
    }
}
