package com.ocp.basejava.model;

import org.jetbrains.annotations.NotNull;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Link implements Serializable {
    public static final long serialVersionUID = 1L;

    private String name;
    private String url;

    public Link() {
    }

    public Link(@NotNull String name, @NotNull String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String toString() {
        return "Link(" + name + ',' + url + ')';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Link link = (Link) o;
        return name.equals(link.name) &&
                url.equals(link.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, url);
    }
}
