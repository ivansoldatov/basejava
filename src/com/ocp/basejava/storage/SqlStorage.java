package com.ocp.basejava.storage;

import com.ocp.basejava.exception.NotExistStorageException;
import com.ocp.basejava.model.ContactType;
import com.ocp.basejava.model.Resume;
import com.ocp.basejava.sql.SQLHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {

    private final SQLHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        this.sqlHelper = new SQLHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume");
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.transactionExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE resume r SET full_name=? WHERE uuid=?")) {
                ps.setString(1, resume.getFullName());
                ps.setString(2, resume.getUuid());
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(resume.getUuid());
                }
            }
            deleteContacts(resume);
            insertContacts(conn, resume);
            return null;
        });
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.transactionExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, resume.getFullName());
                ps.execute();
            }
            insertContacts(conn, resume);
            return null;
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute("DELETE FROM resume r WHERE r.uuid=?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.execute("SELECT * FROM resume r " +
                "LEFT JOIN contact c on r.uuid = c.resume_uuid " +
                "WHERE r.uuid=?", ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            Resume r = new Resume(uuid, rs.getString("full_name"));
            do {
                addContact(r, rs);
            } while (rs.next());
            return r;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.transactionExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume r " +
                    "LEFT JOIN contact c ON r.uuid=c.resume_uuid " +
                    "ORDER BY full_name, uuid")) {
                ResultSet rs = ps.executeQuery();
                Map<String, Resume> map = new LinkedHashMap<>();
                while (rs.next()) {
                    String uuid = rs.getString("uuid").strip();
                    Resume resume = map.get(uuid);
                    if (resume == null) {
                        resume = new Resume(uuid, rs.getString("full_name"));
                        map.put(uuid, resume);
                    }
                    addContact(map.get(uuid), rs);
                }
                return new ArrayList<Resume>(map.values());
            }
        });
    }


    /* @Override
    public List<Resume> getAllSorted() {
        List<Resume> list = new ArrayList<>(size());
        return sqlHelper.execute("SELECT * FROM resume ORDER BY full_name, uuid", ps -> {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Resume newResume = new Resume(rs.getString("uuid").trim(), rs.getString("full_name"));
                sqlHelper.execute("SELECT * FROM contact WHERE resume_uuid=?", psc -> {
                    psc.setString(1, newResume.getUuid());
                    ResultSet rsc = psc.executeQuery();
                    while (rsc.next()) {
                        String type = rsc.getString("type");
                        newResume.addContact(ContactType.valueOf(type), rsc.getString("value"));
                    }
                    return null;
                });
                list.add(newResume);
            }
            return list;
        });
    }*/

    @Override
    public int size() {
        return sqlHelper.execute("SELECT count(*) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        });
    }

    private void addContact(Resume resume, ResultSet rs) throws SQLException {
        String type = rs.getString("type");
        if (!rs.wasNull()) {
            resume.addContact(ContactType.valueOf(type), rs.getString("value"));
        }
    }

    private void deleteContacts(Resume resume) {
        sqlHelper.transactionExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM contact WHERE resume_uuid=?")) {
                ps.setString(1, resume.getUuid());
                ps.execute();
                return null;
            }
        });
    }

    private void insertContacts(Connection conn, Resume resume) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> e : resume.getContacts().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3, e.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }
}
