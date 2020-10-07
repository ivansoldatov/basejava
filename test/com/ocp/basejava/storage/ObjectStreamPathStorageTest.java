package com.ocp.basejava.storage;

import static org.junit.jupiter.api.Assertions.*;

class ObjectStreamPathStorageTest extends AbstractStorageTest {
    public ObjectStreamPathStorageTest() {
        super(new ObjectStreamPathStorage(STRORAGE_DIR.toString()));
    }
}
