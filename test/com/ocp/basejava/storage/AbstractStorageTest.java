package com.ocp.basejava.storage;

import com.ocp.basejava.Config;
import com.ocp.basejava.ResumeTestData;
import com.ocp.basejava.exception.ExistStorageException;
import com.ocp.basejava.exception.NotExistStorageException;
import com.ocp.basejava.model.Resume;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

abstract class AbstractStorageTest {
    protected static final File STORAGE_DIR = Config.getInstance().getStorageDir();
    public static final String DB_URL = Config.getInstance().getDbURL();
    public static final String DB_USER = Config.getInstance().getDbUser();
    public static final String DB_PASSWORD = Config.getInstance().getDbPassword();

    Storage storage;

    static final String UUID_1 = "uuid1";
    static final String UUID_2 = "uuid2";
    static final String UUID_3 = "uuid3";
    static final String UUID_4 = "uuid4";

    static final Resume RESUME_1 = ResumeTestData.fillResumeEmpty(UUID_1, "Name1");
    static final Resume RESUME_2 = ResumeTestData.fillResumeEmpty(UUID_2, "Name2");
    static final Resume RESUME_3 = ResumeTestData.fillResumeEmpty(UUID_3, "Name3");
    static final Resume RESUME_4 = ResumeTestData.fillResumeEmpty(UUID_4, "Name4");

//    static final Resume RESUME_2 = ResumeTestData.fillResumeContacts(UUID_2, "Name2");
//    static final Resume RESUME_3 = ResumeTestData.fillResumeFull(UUID_3, "Name3");
//    static final Resume RESUME_4 = ResumeTestData.fillResumeFull(UUID_4, "Name4");

    AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    void setUp() {
        storage.clear();
        storage.save(RESUME_3);
        storage.save(RESUME_1);
        storage.save(RESUME_2);
    }

    @Test
    void size() {
        assertSize(3);
    }

    @Test
    void clear() {
        storage.clear();
        assertSize(0);
    }

    @Test
    void update() {
        Resume newResume = new Resume(UUID_1, "New Name");
        storage.update(newResume);
        assertEquals(newResume, storage.get(UUID_1));
    }

    @Test
    void updateNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.update(RESUME_4));
    }

    @Test
    void save() {
        storage.save(RESUME_4);
        assertSize(4);
        assertGet(RESUME_4);
    }

    @Test
    void saveExist() {
        assertThrows(ExistStorageException.class, () -> storage.save(RESUME_1));
    }

    @Test
    void delete() {
        storage.delete(UUID_2);
        assertSize(2);
        assertThrows(NotExistStorageException.class, () -> storage.get(UUID_2));
    }

    @Test
    void deleteNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.get(UUID_4));
    }

    @Test
    void get() {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    @Test
    void getAllSorted() {
        List<Resume> expectedResume = Arrays.asList(RESUME_1, RESUME_2, RESUME_3);
        List<Resume> actualResume = storage.getAllSorted();
        assertEquals(3, actualResume.size());
        assertEquals(expectedResume, actualResume);
    }

    @Test
    void getNotExist() {
        Exception exception = assertThrows(NotExistStorageException.class, () -> storage.get("dummy"));
        assertEquals("Resume dummy not exist", exception.getMessage());
    }

    private void assertGet(Resume resume) {
        assertEquals(resume, storage.get(resume.getUuid()));
    }

    void assertSize(int size) {
        assertEquals(size, storage.size());
    }
}