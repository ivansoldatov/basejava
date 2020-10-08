package com.ocp.basejava.storage;

import org.jetbrains.annotations.NotNull;

import java.io.File;

public class ObjectStreamStorage extends AbstractFileStorage {
    public ObjectStreamStorage(@NotNull File directory) {
        super(directory, new ObjectStreamSerialization() {
        });
    }

   /* @Override
    protected void doWrite(Resume resume, OutputStream os) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(os)) {
            oos.writeObject(resume);
        }
    }

    @Override
    protected Resume doRead(InputStream is) throws IOException {
        try (ObjectInputStream ois = new ObjectInputStream(is)) {
            return (Resume) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new StorageException("Error read resume",null,e);
        }
    }*/
}
