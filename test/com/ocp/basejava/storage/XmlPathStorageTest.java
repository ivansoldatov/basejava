package com.ocp.basejava.storage;

import com.ocp.basejava.storage.serializer.XmlStreamSerializer;

class XmlPathStorageTest extends AbstractStorageTest {
    public XmlPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.toString(), new XmlStreamSerializer()));
    }
}