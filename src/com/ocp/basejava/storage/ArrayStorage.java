package com.ocp.basejava.storage;

import com.ocp.basejava.model.Resume;

public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected void fillDeletedElement(int searchKey) {
        storage[searchKey] = storage[size - 1];
    }

    @Override
    protected void insertElement(int searchKey, Resume resume) {
        storage[size] = resume;
    }

    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid()))
                return i;
        }
        return -1;
    }
}
