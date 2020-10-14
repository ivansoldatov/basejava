package com.ocp.basejava.storage;

import com.ocp.basejava.strategy.ObjectStreamSerialization;

class PathStorageTest extends AbstractStorageTest {
    public PathStorageTest() {
        super(new PathStorage(STORAGE_DIR.toString(), new ObjectStreamSerialization()));
    }
}