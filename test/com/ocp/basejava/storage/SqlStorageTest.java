package com.ocp.basejava.storage;

import static org.junit.Assert.*;

public class SqlStorageTest extends AbstractStorageTest {
    SqlStorageTest() {
        super(new SqlStorage(DB_URL, DB_USER, DB_PASSWORD));
    }
}