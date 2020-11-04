package com.ocp.basejava.storage.serializer;

import com.ocp.basejava.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {

    interface wrElement<T> {
        void write(T t) throws IOException;
    }

    private <T> void wrCollection(DataOutputStream dos, Collection<T> collection, wrElement<T> writer) throws IOException {
        dos.writeInt(collection.size());
        for (T item : collection) {
            writer.write(item);
        }
    }

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            Map<ContactType, String> contacts = resume.getContacts();
            wrCollection(dos, contacts.entrySet(), entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });
            Map<SectionType, AbstractSection> sections = resume.getSections();
            wrCollection(dos, sections.entrySet(), entry -> {
                SectionType sectionType = entry.getKey();
                AbstractSection section = entry.getValue();
                dos.writeUTF(sectionType.toString());
                switch (sectionType) {
                    case OBJECTIVE:
                    case PERSONAL:
                        dos.writeUTF(((TextSection) section).getContent());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        wrCollection(dos, ((ListSection) section).getItems(), dos::writeUTF);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        wrCollection(dos, ((OrganizationSection) section).getOrganizations(), org -> {
                            dos.writeUTF(org.getHomePage().getName());
                            dos.writeUTF(org.getHomePage().getUrl());
                            wrCollection(dos, org.getExperiences(), item -> {
                                dos.writeUTF(item.getStartDate().toString());
                                dos.writeUTF(item.getEndDate().toString());
                                dos.writeUTF(item.getTittle());
                                dos.writeUTF(item.getDescription());
                            });
                        });
                        break;
                }
            });
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int sizeContacts = dis.readInt();
            for (int i = 0; i < sizeContacts; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }
            int sizeSections = dis.readInt();
            for (int i = 0; i < sizeSections; i++) {
                String ts = dis.readUTF();
                switch (ts) {
                    case ("OBJECTIVE"):
                    case ("PERSONAL"):
                        resume.addSection(SectionType.valueOf(ts), new TextSection(dis.readUTF()));
                        break;
                    case ("ACHIEVEMENT"):
                    case ("QUALIFICATIONS"):
                        int sizeList = dis.readInt();
                        List<String> list = new ArrayList<>(sizeList);
                        for (int j = 0; j < sizeList; j++) {
                            list.add(dis.readUTF());
                        }
                        resume.addSection(SectionType.valueOf(ts), new ListSection(list));
                        break;
                    case ("EXPERIENCE"):
                    case ("EDUCATION"):
                        int numOrg = dis.readInt();
                        String name = dis.readUTF();
                        String url = dis.readUTF();
                        List<Organization> listOrg = new ArrayList<>(numOrg);
                        for (int k = 0; k < numOrg; k++) {
                            int numExp = dis.readInt();
                            List<Organization.Experience> listExp = new ArrayList<>(numExp);
                            for (int m = 0; m < numExp; m++) {
                                listExp.add(new Organization.Experience(LocalDate.parse(dis.readUTF()), LocalDate.parse(dis.readUTF()), dis.readUTF(), dis.readUTF()));
                            }
                            listOrg.add(new Organization(name, url, listExp));
                        }
                        resume.addSection(SectionType.valueOf(ts), new OrganizationSection(listOrg));
                }
            }
            return resume;
        }
    }
}
