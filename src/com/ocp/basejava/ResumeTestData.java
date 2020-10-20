package com.ocp.basejava;

import com.ocp.basejava.model.*;
import com.ocp.basejava.util.DateUtil;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

public class ResumeTestData {

    public static Resume fillResumeEmpty(String name, String uuid) {
        return new Resume(name, uuid);
    }

    public static Resume fillResumeContacts(String name, String uuid) {
        Resume resume = new Resume(name, uuid);
        resume.addContact(ContactType.PHONE, "+7(4922) 44-44-44");
        resume.addContact(ContactType.MAIL, "iasoldatoff@yandex.ru");
        resume.addContact(ContactType.GITHUB, "https://github.com/ivansoldatov");
        resume.addContact(ContactType.HOMEPAGE, "http://isoldatov.ru/");
        return resume;
    }

    public static Resume fillResumeFull(String name, String uuid) {
        Resume resume = new Resume(name, uuid);
        resume.addContact(ContactType.PHONE, "+7(921) 855-0482");
        resume.addContact(ContactType.MAIL, "gkislin@yandex.ru");
        resume.addContact(ContactType.GITHUB, "https://github.com/gkislin");
        resume.addContact(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473/grigory-kislin");
        resume.addContact(ContactType.HOMEPAGE, "http://gkislin.ru/");
        resume.addSection(SectionType.OBJECTIVE, new TextSection("Тренер по Java Web и Enterprise технологиям"));
        resume.addSection(SectionType.PERSONAL, new TextSection("Пурист кода и архитектуры."));
        resume.addSection(SectionType.ACHIEVEMENT, new ListSection("Java Enterprise", "Управления проектами Wrike"));
        resume.addSection(SectionType.QUALIFICATIONS, new ListSection("OC4J", " JBoss", "Tomcat", " Jetty"));
        resume.addSection(SectionType.EXPERIENCE, new OrganizationSection(new Organization("Java Online Projects", "https://javaops.ru/",
                new Organization.Experience(DateUtil.of(2013, Month.OCTOBER), LocalDate.now(), "Автор проекта", "Создание Java проектов и стажировок."))));
        resume.addSection(SectionType.EDUCATION, new OrganizationSection(new Organization("Интститут", "https://itmo.ru/ru/",
                new Organization.Experience(DateUtil.of(1987, Month.SEPTEMBER), DateUtil.of(1993, Month.JULY), "------", "Аспирантура"),
                new Organization.Experience(DateUtil.of(1993, Month.SEPTEMBER), DateUtil.of(1996, Month.JULY), "------", "Инженер"))));
        return resume;
    }

    public static void main(String[] args) {
//        Resume myResume = fillResume("001", "Григорий Кислин");
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
