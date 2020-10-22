package com.ocp.basejava.storage.serializer;

import com.ocp.basejava.model.*;

import java.io.*;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {
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
            for (Map.Entry<SectionType, AbstractSection> entry : resume.getSection().entrySet()) {
                String nameSection = entry.getKey().name();
                dos.writeUTF(nameSection);
                switch (nameSection) {
                    case ("OBJECTIVE"):
                    case ("PERSONAL"):
                        TextSection ts = (TextSection) entry.getValue();
                        dos.writeUTF(ts.getContent());
                        break;
                    case ("ACHIEVEMENT"):
                    case ("QUALIFICATIONS"):
                        ListSection ls = (ListSection) entry.getValue();
                        for (String s : ls.getItems()) {
                            dos.writeUTF(s);
                        }
                        break;
                    case ("EXPERIENCE"):
                    case ("EDUCATION"):
                        OrganizationSection orgSec = (OrganizationSection) entry.getValue();
                        for (Organization org : orgSec.getOrganizations()) {
                            dos.writeUTF(org.getHomePage().getName());
                            dos.writeUTF(org.getHomePage().getUrl());
                            for (Organization.Experience exp : org.getExperiences()) {
                                dos.writeUTF(exp.getStartDate().toString());
                                dos.writeUTF(exp.getEndDate().toString());
                                dos.writeUTF(exp.getTittle());
                                dos.writeUTF(exp.getDescription());
                            }
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
            return resume;
        }
    }
}
