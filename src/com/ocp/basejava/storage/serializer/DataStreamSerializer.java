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
            dos.writeInt(resume.getContact().size());
            for (Map.Entry<ContactType, String> entry : resume.getContact().entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }
            dos.writeInt(resume.getSection().size());
            for (Map.Entry<SectionType, AbstractSection> entry : resume.getSection().entrySet()) {
                SectionType st = entry.getKey();
                dos.writeUTF(st.toString());
                switch (st) {
                    case OBJECTIVE:
                    case PERSONAL:
                        TextSection ts = (TextSection) entry.getValue();
                        dos.writeUTF(ts.getContent());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        ListSection ls = (ListSection) entry.getValue();
                        dos.writeInt(ls.getItems().size());
                        for (String s : ls.getItems()) {
                            dos.writeUTF(s);
                        }
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        OrganizationSection orgSec = (OrganizationSection) entry.getValue();
                        dos.writeInt(orgSec.getOrganizations().size());
                        for (Organization org : orgSec.getOrganizations()) {
                            dos.writeInt(org.getExperiences().size());
                            for (Organization.Experience exp : org.getExperiences()) {
                                dos.writeUTF(exp.getStartDate().toString());
                                dos.writeUTF(exp.getEndDate().toString());
                                dos.writeUTF(exp.getTittle());
                                dos.writeUTF(exp.getDescription());
                            }
                            dos.writeUTF(org.getHomePage().getName());
                            dos.writeUTF(org.getHomePage().getUrl());
                        }
                        break;
                }
            }
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
                        List<Organization> listOrg = new ArrayList<>(numOrg);
                        for (int k = 0; k < numOrg; k++) {
                            int numExp = dis.readInt();
                            List<Organization.Experience> listExp = new ArrayList<>(numExp);
                            for (int m = 0; m < numExp; m++) {
                                listExp.add(new Organization.Experience(LocalDate.parse(dis.readUTF()), LocalDate.parse(dis.readUTF()), dis.readUTF(), dis.readUTF()));
                            }
                            listOrg.add(new Organization(dis.readUTF(), dis.readUTF(), listExp));
                        }
                        resume.addSection(SectionType.valueOf(ts), new OrganizationSection(listOrg));
                }
            }
            return resume;
        }
    }
}
