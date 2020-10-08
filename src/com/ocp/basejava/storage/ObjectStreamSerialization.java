package com.ocp.basejava.storage;

import com.ocp.basejava.exception.StorageException;
import com.ocp.basejava.model.Resume;

import java.io.*;

public class ObjectStreamSerialization implements ResumeSerialization {
    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(os)) {
            oos.writeObject(resume);
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (ObjectInputStream ois = new ObjectInputStream(is)) {
            return (Resume) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new StorageException("Error read resume", null, e);
        }
    }
}
