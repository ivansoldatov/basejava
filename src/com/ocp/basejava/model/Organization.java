package com.ocp.basejava.model;

import org.jetbrains.annotations.NotNull;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static java.util.Arrays.asList;
import static com.ocp.basejava.util.DateUtil.of;
import static com.ocp.basejava.util.DateUtil.NOW;

@XmlAccessorType(XmlAccessType.FIELD)
public class Organization implements Serializable {
    public static final long serialVersionUID = 1L;

    private Link homePage;
    private List<Experience> experiences;

    public Organization() {
    }

    public Organization(@NotNull String name, @NotNull String url, @NotNull List<Experience> experiences) {
        this.homePage = new Link(name, url);
        this.experiences = experiences;
    }

    public Organization(@NotNull String name, @NotNull String url, Experience... experiences) {
        this.homePage = new Link(name, url);
        this.experiences = asList(experiences);
    }

    public Link getHomePage() {
        return homePage;
    }

    public void setHomePage(Link homePage) {
        this.homePage = homePage;
    }

    public List<Experience> getExperiences() {
        return experiences;
    }

    public void setExperiences(List<Experience> experiences) {
        this.experiences = experiences;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb = sb.append(homePage.getName()).append('\n').append(homePage.getUrl()).append('\n');
        for (Experience e : experiences) {
            sb.append(e).append('\n');
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        if (!homePage.equals(that.homePage)) return false;
        return experiences.equals(that.experiences);
    }

    @Override
    public int hashCode() {
        int result = homePage.hashCode();
        result = 31 * result + experiences.hashCode();
        return result;
    }
    //--------------------------------------------------
    //nested class
    //--------------------------------------------------

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Experience implements Serializable {
        public static final long serialVersionUID = 1L;

        private LocalDate startDate;
        private LocalDate endDate;
        private String tittle;
        private String description;

        public Experience() {
        }

        public Experience(int startYear, Month startMonth, String tittle, String description) {
            this(of(startYear, startMonth), NOW, tittle, description);
        }

        public Experience(int startYear, Month startMonth, int endYear, Month endMonth, String tittle, String description) {
            this(of(startYear, startMonth), of(endYear, endMonth), tittle, description);
        }

        public Experience(@NotNull LocalDate startDate, @NotNull LocalDate endDate, String tittle, @NotNull String description) {
            this.startDate = startDate;
            this.endDate = endDate;
            this.tittle = tittle;
            this.description = description;
        }

        public LocalDate getStartDate() {
            return startDate;
        }

        public void setStartDate(LocalDate startDate) {
            this.startDate = startDate;
        }

        public LocalDate getEndDate() {
            return endDate;
        }

        public void setEndDate(LocalDate endDate) {
            this.endDate = endDate;
        }

        public String getTittle() {
            return tittle;
        }

        public void setTittle(String tittle) {
            this.tittle = tittle;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        @Override
        public String toString() {
            return tittle + '\n' +
                    startDate + " - " + endDate + '\n' +
                    description + '\n';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Experience that = (Experience) o;
            if (!startDate.equals(that.startDate)) return false;
            if (!endDate.equals(that.endDate)) return false;
            if (!tittle.equals(that.tittle)) return false;
            return description.equals(that.description);
        }

        @Override
        public int hashCode() {
            int result = startDate.hashCode();
            result = 31 * result + endDate.hashCode();
            result = 31 * result + tittle.hashCode();
            result = 31 * result + description.hashCode();
            return result;
        }
    }
}
