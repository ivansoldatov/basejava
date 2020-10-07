package com.ocp.basejava.storage;

import com.ocp.basejava.ResumeTestData;
import com.ocp.basejava.exception.ExistStorageException;
import com.ocp.basejava.exception.NotExistStorageException;
import com.ocp.basejava.model.Resume;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

abstract class AbstractStorageTest {
    protected static final File STRORAGE_DIR = new File("D:\\Java\\basejava\\storage");
    Storage storage;

    static final String UUID_1 = "uuid1";
    //    static final Resume RESUME_1 = new Resume(UUID_1, "Aleksandr Pushkin");
    static final Resume RESUME_1 = ResumeTestData.fillResume(UUID_1, "Aleksandr Pushkin");

    static final String UUID_2 = "uuid2";
    //    static final Resume RESUME_2 = new Resume(UUID_2, "Leo Tolstoy");
    static final Resume RESUME_2 = ResumeTestData.fillResume(UUID_2, "Leo Tolstoy");

    static final String UUID_3 = "uuid3";
    //    static final Resume RESUME_3 = new Resume(UUID_3, "Mikhail Lermontov");
    static final Resume RESUME_3 = ResumeTestData.fillResume(UUID_3, "Mikhail Lermontov");

    static final String UUID_4 = "uuid4";
    //    static final Resume RESUME_4 = new Resume(UUID_4, "Leo Tolstoy");
    static final Resume RESUME_4 = ResumeTestData.fillResume(UUID_4, "Leo Tolstoy");

    static final String UUID_5 = "uuid5";
    //    static final Resume RESUME_5 = new Resume(UUID_5, "Mikhail Bulgakov");
    static final Resume RESUME_5 = ResumeTestData.fillResume(UUID_5, "Mikhail Bulgakov");

    AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    void setUp() {
        storage.clear();
        storage.save(RESUME_4);
        storage.save(RESUME_3);
        storage.save(RESUME_1);
        storage.save(RESUME_2);
    }

    @Test
    void size() {
        assertSize(4);
    }

    @Test
    void clear() {
        storage.clear();
        assertSize(0);
    }

    @Test
    void update() {
        Resume newResume = new Resume(UUID_1, "Sergey Lukyanenko");
        storage.update(newResume);
        assertEquals(newResume, storage.get(UUID_1));
    }

    @Test
    void updateNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.update(RESUME_5));
    }

    @Test
    void save() {
        storage.save(RESUME_5);
        assertSize(5);
        assertGet(RESUME_5);
    }

    @Test
    void saveExist() {
        assertThrows(ExistStorageException.class, () -> storage.save(RESUME_1));
    }

    @Test
    void delete() {
        storage.delete(UUID_2);
        assertSize(3);
        assertThrows(NotExistStorageException.class, () -> storage.get(UUID_2));
    }

    @Test
    void deleteNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.get(UUID_5));
    }

    @Test
    void get() {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
        assertGet(RESUME_4);
    }

    @Test
    void getAllSorted() {
        List<Resume> expectedResume = Arrays.asList(RESUME_1, RESUME_2, RESUME_4, RESUME_3);
        List<Resume> actualResume = storage.getAllSorted();
        assertEquals(4, actualResume.size());
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