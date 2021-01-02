package com.ocp.basejava.storage;

import com.ocp.basejava.Config;

public class SqlStorageTest extends AbstractStorageTest {
    SqlStorageTest() {
        super(Config.getInstance().getStorage());
    }
}