package com.ocp.basejava;

import com.ocp.basejava.model.SectionType;

public class TestSingleton {
    private static TestSingleton instans;

    public static TestSingleton getInstance() {
        if (instans == null) {
            instans = new TestSingleton();
        }
        return instans;
    }

    private TestSingleton() {
    }

    public static void main(String[] args) {
        TestSingleton.getInstance().toString();
        Singlenon instance = Singlenon.valueOf("INSTANCE");
        System.out.println(instance.ordinal());
        for (SectionType type : SectionType.values()) {
            System.out.println(type.getTitle());
        }
    }

    public enum Singlenon {
        INSTANCE
    }
}
