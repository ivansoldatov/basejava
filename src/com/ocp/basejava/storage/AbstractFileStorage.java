package com.ocp.basejava.storage;

import com.ocp.basejava.exception.StorageException;
import com.ocp.basejava.model.Resume;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractFileStorage extends AbstractStorage<File> {

    private File directory;
    private ResumeSerialization serialization;

    public AbstractFileStorage(@NotNull File directory, @NotNull ResumeSerialization rs) {
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
        this.serialization = rs;
    }
//    protected abstract void doWrite(Resume resume, OutputStream os) throws IOException;
//    protected abstract Resume doRead(InputStream is) throws IOException;

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected boolean checkExist(File file) {
        return file.exists();
    }

    @Override
    protected void doSave(File file, Resume resume) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new StorageException("Couldn't create file " + file.getAbsolutePath(), file.getName(), e);
        }
        doUpdate(file, resume);
    }

    @Override
    protected void doUpdate(File file, Resume resume) {
        try {
            serialization.doWrite(resume, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File write error", file.getName(), e);
        }
    }

    @Override
    protected void doDelete(File file) {
        try {
            file.delete();
        } catch (Exception e) {
            throw new StorageException("File delete error", file.getName(), e);
        }
    }

    @Override
    protected Resume doGet(File file) {
        try {
            return serialization.doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File read error", file.getName(), e);
        }
    }

    @Override
    protected List<Resume> doCopyAll() {
        File[] listFiles = directory.listFiles();
        if (listFiles == null) {
            throw new StorageException("Directory read error", null);
        } else {
            List<Resume> listResume = new ArrayList<>();
            for (File f : listFiles) {
                listResume.add(doGet(f));
            }
            return listResume;
        }
    }

    @Override
    public void clear() {
        File[] listFiles = directory.listFiles();
        if (listFiles != null) {
            for (File f : listFiles) {
                doDelete(f);
            }
        }
    }

    @Override
    public int size() {
        String[] listFiles = directory.list();
        if (listFiles == null) {
            throw new StorageException("Directory read error", null);
        } else {
            return listFiles.length;
        }
    }
}
