package com.ocp.basejava;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final File PROPS = new File("./config/resumes.properties");
    private static final Config INSTANCE = new Config();
    private Properties props = new Properties();

    private File storageDir;

    private Config() {
        try (InputStream is = new FileInputStream(PROPS)) {
            props.load(is);
            storageDir = new File(props.getProperty("storage.dir"));
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file " + PROPS.getAbsolutePath());
        }
    }

    public static Config getInstance() {
        return INSTANCE;
    }

    public File getStorageDir() {
        return storageDir;
    }
}
