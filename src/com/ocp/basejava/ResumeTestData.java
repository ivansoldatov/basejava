package com.ocp.basejava;

import com.ocp.basejava.model.*;
import com.ocp.basejava.util.DateUtil;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

public class ResumeTestData {
    static {


    }

    //  TextSections
    public static final String objective = "Ведущий стажировок и корпоративного обучения по " +
            "Java Web и Enterprise технологиям";
    public static final String personal = "Аналитический склад ума, сильная логика, креативность, инициативность. " +
            "Пурист кода и архитектуры.";

    //  ListSections
    public static final String achievement_1 = "С 2013 года: разработка проектов \"Разработка Web приложения\"," +
            "\"Java Enterprise\", \"Многомодульный maven. Многопоточность." +
            " XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие" +
            " (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов." +
            " Более 1000 выпускников.";
    public static final String achievement_2 = "Реализация двухфакторной аутентификации для онлайн платформы" +
            " управления проектами Wrike. Интеграция с Twilio, DuoSecurity," +
            " Google Authenticator, Jira, Zendesk.";
    public static final String qualification_1 = "JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2";
    public static final String qualification_2 = "Version control: Subversion, Git, Mercury, ClearCase, Perforce";

    //  OrganizationSections
    public static final Organization.Experience experience_1 = new Organization.Experience(DateUtil.of(2013, Month.OCTOBER), LocalDate.now(), "Автор проекта.", "Создание, организация и проведение Java онлайн проектов и стажировок.");
    public static final Organization.Experience experience_2 = new Organization.Experience(DateUtil.of(2014, Month.OCTOBER), DateUtil.of(2016, Month.JANUARY), "Старший разработчик (backend)", "Проектирование и " + "разработка " +
            "онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). " +
            "Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.");
    public static final Organization.Experience education_1 = new Organization.Experience(DateUtil.of(2013, Month.MARCH), DateUtil.of(2013, Month.MAY), "------", "Functional Programming Principles in Scala by Martin Odersky");
    public static final Organization.Experience education_2 = new Organization.Experience(DateUtil.of(2011, Month.MARCH), DateUtil.of(2001, Month.APRIL), "------", "Курс Объектно-ориентированный анализ ИС. " +
            "Концептуальное моделирование на UML.");
    public static final Organization.Experience education_3 = new Organization.Experience(DateUtil.of(1987, Month.SEPTEMBER), DateUtil.of(1993, Month.JULY), "------", "Аспирантура (программист С, С++)");
    public static final Organization.Experience education_4 = new Organization.Experience(DateUtil.of(1993, Month.SEPTEMBER), DateUtil.of(1996, Month.JULY), "------", "Инженер (программист Fortran, C)");
    public static final Organization orgExperience1 = new Organization("Java Online Projects", "https://javaops.ru/", asList(experience_1));
    public static final Organization orgExperience2 = new Organization("Wrike", "https://www.wrike.com/", asList(experience_2));
    public static final Organization orgEducation1 = new Organization("Coursera", "https://www.coursera.org/", asList(education_1));
    public static final Organization orgEducation2 = new Organization("Luxoft", "https://www.luxoft-training.ru/kurs/obektno-orientirovannyy_analiz_i_proektirovanie_na_uml.html", asList(education_2));
    public static final Organization orgEducation3 = new Organization("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики", "https://itmo.ru/ru/", asList(education_3, education_4));

    public static Resume fillResume(String name, String uuid) {
        Resume myResume = new Resume(name, uuid);
        myResume.getContacts().put(ContactType.PHONE, "+7(921) 855-0482");
        myResume.getContacts().put(ContactType.MAIL, "gkislin@yandex.ru");
        myResume.getContacts().put(ContactType.GITHUB, "https://github.com/gkislin");
        myResume.getContacts().put(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473/grigory-kislin");
        myResume.getContacts().put(ContactType.HOMEPAGE, "http://gkislin.ru/");

        myResume.getSections().put(SectionType.OBJECTIVE, new TextSection(objective));
        myResume.getSections().put(SectionType.PERSONAL, new TextSection(personal));

        List<String> achievementPoints = asList(achievement_1, achievement_2);
        myResume.getSections().put(SectionType.ACHIEVEMENT, new ListSection(achievementPoints));
        List<String> qualificationsPoints = asList(qualification_1, qualification_2);
        myResume.getSections().put(SectionType.QUALIFICATIONS, new ListSection(qualificationsPoints));

        myResume.getSections().put(SectionType.EXPERIENCE, new OrganizationSection(asList(orgExperience1, orgExperience2)));
        myResume.getSections().put(SectionType.EDUCATION, new OrganizationSection(asList(orgEducation1, orgEducation2, orgEducation3)));
        return myResume;
    }

    public static void main(String[] args) {
        Resume myResume = fillResume("001", "Григорий Кислин");
        System.out.println("myResume:");
        System.out.println("UUID: " + myResume.getUuid());
        System.out.println("fullName: " + myResume.getFullName());
        for (Map.Entry<ContactType, String> entryContact : myResume.getContacts().entrySet()) {
            System.out.printf("%s: %s \n", entryContact.getKey(), entryContact.getValue());
        }
        System.out.println();

        for (Map.Entry<SectionType, AbstractSection> entry : myResume.getSections().entrySet()) {
            SectionType sectionType = entry.getKey();
            System.out.println(sectionType.getTitle() + ":");
            AbstractSection section = entry.getValue();
            System.out.println(section.toString());
        }
    }
}
