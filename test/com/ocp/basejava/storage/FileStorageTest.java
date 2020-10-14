package com.ocp.basejava.storage;

import com.ocp.basejava.strategy.ObjectStreamSerialization;

class FileStorageTest extends AbstractStorageTest {
    public FileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new ObjectStreamSerialization()));
    }
}