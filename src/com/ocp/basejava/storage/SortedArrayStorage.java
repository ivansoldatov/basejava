package com.ocp.basejava.storage;

import com.ocp.basejava.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void fillDeletedElement(int searchKey) {
        int numMoved = size - searchKey - 1;
        if (numMoved > 0)
            System.arraycopy(storage, searchKey + 1, storage, searchKey, numMoved);
    }

    @Override
    protected void insertElement(int searchKey, Resume resume) {
        int insertIdx = -searchKey - 1;
        System.arraycopy(storage, insertIdx, storage, insertIdx + 1, size - insertIdx);
        storage[insertIdx] = resume;
    }

    private static final Comparator<Resume> UUID_COMPARATOR = Comparator.comparing(Resume::getUuid);

    @Override
    protected Integer getSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid, "dummy");
        return Arrays.binarySearch(storage, 0, size, searchKey, UUID_COMPARATOR);
    }
}
