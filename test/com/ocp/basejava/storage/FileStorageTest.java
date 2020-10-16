package com.ocp.basejava.storage;

import com.ocp.basejava.storage.serializer.ObjectStreamSerializer;

class FileStorageTest extends AbstractStorageTest {
    public FileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new ObjectStreamSerializer()));
    }
}