package com.ocp.basejava.storage;

import com.ocp.basejava.exception.NotExistStorageException;
import com.ocp.basejava.model.ContactType;
import com.ocp.basejava.model.Resume;
import com.ocp.basejava.sql.SQLHelper;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {

    private final SQLHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        this.sqlHelper = new SQLHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.doConnection("DELETE FROM resume", ps -> {
            ps.execute();
            return null;
        });
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.doConnection("UPDATE resume r SET full_name=? WHERE uuid=?", ps -> {
            ps.setString(1, resume.getFullName());
            ps.setString(2, resume.getUuid());
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(resume.getUuid());
            }
            return null;
        });
        sqlHelper.doConnection("UPDATE contact c SET type=?, value=? WHERE resume_uuid=?", ps -> {
            ps.setString(3, resume.getUuid());
            for (Map.Entry e : resume.getContacts().entrySet()) {
                ps.setString(1, e.getKey().toString());
                ps.setString(2, e.getValue().toString());
                ps.execute();
            }
            return null;
        });
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.doConnection("INSERT INTO resume (uuid, full_name) VALUES (?,?)", ps -> {
            ps.setString(1, resume.getUuid());
            ps.setString(2, resume.getFullName());
            ps.execute();
            return null;
        });
        sqlHelper.doConnection("INSERT INTO contact (resume_uuid, type, value) VALUES  (?,?,?)", ps -> {
            ps.setString(1, resume.getUuid());
            for (Map.Entry e : resume.getContacts().entrySet()) {
                ps.setString(2, e.getKey().toString());
                ps.setString(3, e.getValue().toString());
                ps.execute();
            }
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.doConnection("SELECT * FROM resume r " +
                "LEFT JOIN contact c on r.uuid = c.resume_uuid " +
                "WHERE r.uuid=?", ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            Resume r = new Resume(uuid, rs.getString("full_name"));
            do {
                String type = rs.getString("type");
                if (!rs.wasNull()) {
                    r.addContact(ContactType.valueOf(type), rs.getString("value"));
                }
            }
            while (rs.next());
            return r;
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.doConnection("DELETE FROM resume r WHERE r.uuid=?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> list = new ArrayList<>(size());
        return sqlHelper.doConnection("SELECT * FROM resume ORDER BY full_name, uuid", ps -> {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Resume newResume = new Resume(rs.getString("uuid").trim(), rs.getString("full_name"));
                sqlHelper.doConnection("SELECT * FROM contact WHERE resume_uuid=?", psc -> {
                    psc.setString(1, newResume.getUuid());
                    ResultSet rsc = psc.executeQuery();
                    while (rsc.next()) {
                        newResume.addContact(ContactType.valueOf(rsc.getString("type")), rsc.getString("value"));
                    }
                    return null;
                });
                list.add(newResume);
            }
            return list;
        });
    }

    @Override
    public int size() {
        return sqlHelper.doConnection("SELECT count(*) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        });
    }
}
