package com.ocp.basejava.storage;

import com.ocp.basejava.storage.serializer.ObjectStreamSerializer;

class PathStorageTest extends AbstractStorageTest {
    public PathStorageTest() {
        super(new PathStorage(STORAGE_DIR.toString(), new ObjectStreamSerializer()));
    }
}