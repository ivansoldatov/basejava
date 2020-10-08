package com.ocp.basejava.storage;

class PathStorageTest extends AbstractStorageTest {
    public PathStorageTest() {
        super(new PathStorage(STORAGE_DIR.toString(), new ObjectStreamSerialization()));
    }
}