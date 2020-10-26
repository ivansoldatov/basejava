package com.ocp.basejava.model;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ListSection extends AbstractSection {
    public static final long serialVersionUID = 1L;
    private List<String> items;

    public ListSection() {
    }

    public ListSection(@NotNull List<String> items) {
        this.items = items;
    }

    public ListSection(@NotNull String... items) {
        this(Arrays.asList(items));
    }

    public List<String> getItems() {
        return items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListSection that = (ListSection) o;
        return items.equals(that.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(items);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : items) {
            stringBuilder.append(s).append('\n');
        }
        return stringBuilder.toString();
    }
}
