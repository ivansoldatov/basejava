package com.ocp.basejava.storage;

import com.ocp.basejava.exception.StorageException;
import com.ocp.basejava.model.Resume;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public abstract class AbstractPathStorage extends AbstractStorage<Path> {

    private Path directory;
    private ResumeSerialization serialization;

    public AbstractPathStorage(@NotNull String dir, @NotNull ResumeSerialization rs) {
        directory = Paths.get(dir);
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
        this.directory = directory;
        this.serialization=rs;
    }

//    protected abstract void doWrite(Resume resume, OutputStream os) throws IOException;
//    protected abstract Resume doRead(InputStream is) throws IOException;

    @Override
    protected Path getSearchKey(String uuid) {
        return Paths.get(directory.toString(), uuid);
    }

    @Override
    protected boolean checkExist(Path path) {
        return Files.exists(path);
    }

    @Override
    protected void doSave(Path path, Resume resume) {
        doUpdate(path, resume);
    }

    @Override
    protected void doUpdate(Path path, Resume resume) {
        try {
            serialization.doWrite(resume, new BufferedOutputStream(new FileOutputStream(path.toString())));
        } catch (IOException e) {
            throw new StorageException("Path write error", path.toString(), e);
        }
    }

    @Override
    protected void doDelete(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("File delete error.", path.toString(), e);
        }
    }

    @Override
    protected Resume doGet(Path path) {
        try {
            return serialization.doRead(new BufferedInputStream(new FileInputStream(path.toString())));
        } catch (IOException e) {
            throw new StorageException("Path read error", path.toString(), e);
        }
    }

    @Override
    protected List<Resume> doCopyAll() {
        List<Resume> list = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory)) {
            for (Path entry : stream) {
                list.add(doGet(entry));
            }
        } catch (IOException e) {
            throw new StorageException("Directory read error", directory.toString(), e);
        }
        return list;
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::doDelete);
        } catch (IOException e) {
            throw new StorageException("Resume delete error ", null);
        }
    }

    @Override
    public int size() {
        try (Stream<Path> files = Files.list(directory)) {
            return (int) files.count();
        } catch (IOException e) {
            throw new StorageException("Directory read error", directory.toString(), e);
        }
    }
}
