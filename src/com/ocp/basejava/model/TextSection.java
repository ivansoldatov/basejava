package com.ocp.basejava.model;

import org.jetbrains.annotations.NotNull;

public class TextSection extends AbstractSection {
    public static final long serialVersionUID = 1L;
    private final String content;

    public TextSection(@NotNull String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return content + '\n';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TextSection)) return false;

        TextSection that = (TextSection) o;

        return content.equals(that.content);
    }

    @Override
    public int hashCode() {
        return content.hashCode();
    }
}