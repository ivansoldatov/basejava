package com.ocp.basejava.storage;

import com.ocp.basejava.exception.StorageException;
import com.ocp.basejava.model.Resume;
import com.ocp.basejava.storage.serializer.StreamSerializer;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {

    private Path directory;
    private StreamSerializer streamSerializer;

    public PathStorage(@NotNull String dir, @NotNull StreamSerializer rs) {
        directory = Paths.get(dir);
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
        this.streamSerializer = rs;
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return directory.resolve(uuid);
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
            streamSerializer.doWrite(resume, Files.newOutputStream(path));
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
            return streamSerializer.doRead(Files.newInputStream(path));
        } catch (IOException e) {
            throw new StorageException("Path read error", path.toString(), e);
        }
    }

    @Override
    protected List<Resume> doCopyAll() {
            return getFiles().map(this::doGet).collect(Collectors.toList());
    }

    @Override
    public void clear() {
            getFiles().forEach(this::doDelete);
    }

    @Override
    public int size() {
        return (int) getFiles().count();
    }

    private Stream<Path> getFiles() {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("Directory read error", directory.toString(), e);
        }
    }
}
