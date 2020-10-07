package com.ocp.basejava.storage;

class ObjectStreamStorageTest extends AbstractStorageTest {

    ObjectStreamStorageTest() {
        super(new ObjectStreamStorage(STRORAGE_DIR));
    }
}