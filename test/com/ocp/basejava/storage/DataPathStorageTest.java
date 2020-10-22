package com.ocp.basejava.storage;

import com.ocp.basejava.storage.serializer.DataStreamSerializer;

class DataPathStorageTest extends AbstractStorageTest {
    public DataPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.toString(), new DataStreamSerializer()));
    }
}