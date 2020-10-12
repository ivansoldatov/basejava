package com.ocp.basejava;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MainFile {
    private static void getNameFiles(File directory, String ofset) {
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File f : files) {
                    if (f.isFile()) {
                        System.out.println(ofset + "File: " + f.getName());
                    } else {
                        System.out.println(ofset + "Dir: " + f.getName());
                        getNameFiles(f, ofset + "-");
                    }
                }
            }
        } else
            System.out.println("Directory error");
    }

    public static void main(String[] args) {
        String myProjectDir = ".\\src\\com\\ocp\\basejava";
        File directory = new File(myProjectDir);
        getNameFiles(directory, "|");
        System.out.println("-------------------------");
        String filePath = ".\\.gitignore";
        File file = new File(filePath);
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error.", e);
        }
        File dir = new File(".\\src\\com\\ocp\\basejava");
        String[] list = dir.list();
        if (list != null) {
            for (String name : list) {
                System.out.println(name);
            }
        }
        try (FileInputStream fis = new FileInputStream(filePath)) {
            System.out.println(fis.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
