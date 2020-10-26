package com.ocp.basejava.model;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class OrganizationSection extends AbstractSection {
    public static final long serialVersionUID = 1L;

    private List<Organization> organizations;

    public OrganizationSection() {
    }

    public OrganizationSection(@NotNull List<Organization> organizations) {

        this.organizations = organizations;
    }

    public OrganizationSection(@NotNull Organization... organizations) {
        this.organizations = Arrays.asList(organizations);
    }

    public List<Organization> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(List<Organization> organizations) {
        this.organizations = organizations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganizationSection that = (OrganizationSection) o;
        return organizations != null ? organizations.equals(that.organizations) : that.organizations == null;
    }

    @Override
    public int hashCode() {
        return organizations != null ? organizations.hashCode() : 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Organization o : organizations) {
            sb.append(o);
        }
        return sb.toString();
    }
}
