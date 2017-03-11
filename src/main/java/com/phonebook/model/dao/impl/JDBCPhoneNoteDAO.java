package com.phonebook.model.dao.impl;

import com.phonebook.entities.PhoneNote;
import com.phonebook.model.dao.ClientDAO;
import com.phonebook.model.dao.PhoneNoteDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.List;
import java.util.Properties;


public class JDBCPhoneNoteDAO implements PhoneNoteDAO {
    private String SQLStatementRead;
    private String SQLStatementReadByName;
    private String SQLStatementReadByClientID;
    private String SQLStatementCreate;
    private String SQLStatementUpdate;
    private String SQLStatementDelete;
    private String SQLStatementReadAll;

    private String SQLStatementReadBySubStrInFirstName;
    private String SQLStatementReadBySubStrInSecondName;
    private String SQLStatementReadBySubStrInMobileNumber;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ClientDAO clientDAO;

    public JDBCPhoneNoteDAO() {
        Properties properties = new Properties();

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("JDBCPhoneNoteConfig.properties")) {

            properties.load(inputStream);

            SQLStatementRead = properties.getProperty("StatementRead");
            SQLStatementReadByName = properties.getProperty("StatementReadByName");
            SQLStatementReadByClientID = properties.getProperty("StatementReadByClientID");
            SQLStatementUpdate = properties.getProperty("StatementUpdate");
            SQLStatementDelete = properties.getProperty("StatementDelete");
            SQLStatementCreate = properties.getProperty("StatementCreate");
            SQLStatementReadAll = properties.getProperty("StatementReadAll");

            SQLStatementReadBySubStrInFirstName = properties.getProperty("StatementReadBySubStrInFirstName");
            SQLStatementReadBySubStrInSecondName = properties.getProperty("StatementReadBySubStrInSecondName");
            SQLStatementReadBySubStrInMobileNumber = properties.getProperty("StatementReadBySubStrInMobileNumber");

        } catch (IOException ex) {
            ex.getMessage();
        }
    }

    @Override
    public PhoneNote read(int noteID) {
        PhoneNote phoneNote = this.jdbcTemplate.queryForObject(
                SQLStatementRead,
                new Object[]{noteID},
                new RowMapper<PhoneNote>() {
                    public PhoneNote mapRow(ResultSet rs, int rowNum) throws SQLException {
                        PhoneNote phoneNoteTemp = new PhoneNote();
                        phoneNoteTemp.setNoteID(rs.getInt("note_id"));
                        phoneNoteTemp.setFirstName(rs.getString("first_name"));
                        phoneNoteTemp.setSecondName(rs.getString("second_name"));
                        phoneNoteTemp.setAdditionalName(rs.getString("additional_name"));
                        phoneNoteTemp.setMobileNumber(rs.getString("mobile_number"));
                        phoneNoteTemp.setHomeNumber(rs.getString("home_number"));
                        phoneNoteTemp.setLocation(rs.getString("location"));
                        phoneNoteTemp.setEmail(rs.getString("email"));
                        phoneNoteTemp.setNoteOwner(clientDAO.read(rs.getInt("client_id")));

                        return phoneNoteTemp;
                    }
                });
        return phoneNote;
    }

    @Override
    public PhoneNote readByName(String firstName, String secondName) {
        PhoneNote phoneNote = this.jdbcTemplate.queryForObject(
                SQLStatementReadByName,
                new Object[]{firstName, secondName},
                new RowMapper<PhoneNote>() {
                    public PhoneNote mapRow(ResultSet rs, int rowNum) throws SQLException {
                        PhoneNote phoneNoteTemp = new PhoneNote();
                        phoneNoteTemp.setNoteID(rs.getInt("note_id"));
                        phoneNoteTemp.setFirstName(rs.getString("first_name"));
                        phoneNoteTemp.setSecondName(rs.getString("second_name"));
                        phoneNoteTemp.setAdditionalName(rs.getString("additional_name"));
                        phoneNoteTemp.setMobileNumber(rs.getString("mobile_number"));
                        phoneNoteTemp.setHomeNumber(rs.getString("home_number"));
                        phoneNoteTemp.setLocation(rs.getString("location"));
                        phoneNoteTemp.setEmail(rs.getString("email"));
                        phoneNoteTemp.setNoteOwner(clientDAO.read(rs.getInt("client_id")));

                        return phoneNoteTemp;
                    }
                });
        return phoneNote;
    }

    @Override
    public List<PhoneNote> readByClientID(int clientID) {
        List<PhoneNote> phoneNoteList = this.jdbcTemplate.query(
                SQLStatementReadByClientID,
                new Object[]{clientID},
                new RowMapper<PhoneNote>() {
                    public PhoneNote mapRow(ResultSet rs, int rowNum) throws SQLException {
                        PhoneNote phoneNoteTemp = new PhoneNote();
                        phoneNoteTemp.setNoteID(rs.getInt("note_id"));
                        phoneNoteTemp.setFirstName(rs.getString("first_name"));
                        phoneNoteTemp.setSecondName(rs.getString("second_name"));
                        phoneNoteTemp.setAdditionalName(rs.getString("additional_name"));
                        phoneNoteTemp.setMobileNumber(rs.getString("mobile_number"));
                        phoneNoteTemp.setHomeNumber(rs.getString("home_number"));
                        phoneNoteTemp.setLocation(rs.getString("location"));
                        phoneNoteTemp.setEmail(rs.getString("email"));
                        phoneNoteTemp.setNoteOwner(clientDAO.read(rs.getInt("client_id")));

                        return phoneNoteTemp;
                    }
                });
        return phoneNoteList;
    }

    @Override
    public List<PhoneNote> readAll() {
        List<PhoneNote> phoneNoteList = this.jdbcTemplate.query(
                SQLStatementReadAll,
                new RowMapper<PhoneNote>() {
                    public PhoneNote mapRow(ResultSet rs, int rowNum) throws SQLException {
                        PhoneNote phoneNoteTemp = new PhoneNote();
                        phoneNoteTemp.setNoteID(rs.getInt("note_id"));
                        phoneNoteTemp.setFirstName(rs.getString("first_name"));
                        phoneNoteTemp.setSecondName(rs.getString("second_name"));
                        phoneNoteTemp.setAdditionalName(rs.getString("additional_name"));
                        phoneNoteTemp.setMobileNumber(rs.getString("mobile_number"));
                        phoneNoteTemp.setHomeNumber(rs.getString("home_number"));
                        phoneNoteTemp.setLocation(rs.getString("location"));
                        phoneNoteTemp.setEmail(rs.getString("email"));
                        phoneNoteTemp.setNoteOwner(clientDAO.read(rs.getInt("client_id")));

                        return phoneNoteTemp;
                    }
                });
        return phoneNoteList;
    }

    @Override
    public List<PhoneNote> readBySubStrInFirstName(String subStr, int clientID) {
        List<PhoneNote> phoneNoteList = this.jdbcTemplate.query(
                (SQLStatementReadBySubStrInFirstName + "'%" + subStr + "%'"),
                new Object[]{clientID},
                new RowMapper<PhoneNote>() {
                    public PhoneNote mapRow(ResultSet rs, int rowNum) throws SQLException {
                        PhoneNote phoneNoteTemp = new PhoneNote();
                        phoneNoteTemp.setNoteID(rs.getInt("note_id"));
                        phoneNoteTemp.setFirstName(rs.getString("first_name"));
                        phoneNoteTemp.setSecondName(rs.getString("second_name"));
                        phoneNoteTemp.setAdditionalName(rs.getString("additional_name"));
                        phoneNoteTemp.setMobileNumber(rs.getString("mobile_number"));
                        phoneNoteTemp.setHomeNumber(rs.getString("home_number"));
                        phoneNoteTemp.setLocation(rs.getString("location"));
                        phoneNoteTemp.setEmail(rs.getString("email"));
                        phoneNoteTemp.setNoteOwner(clientDAO.read(rs.getInt("client_id")));

                        return phoneNoteTemp;
                    }
                });
        return phoneNoteList;
    }

    @Override
    public List<PhoneNote> readBySubStrInSecondName(String subStr, int clientID) {
        List<PhoneNote> phoneNoteList = this.jdbcTemplate.query(
                (SQLStatementReadBySubStrInSecondName + "'%" + subStr + "%'"),
                new Object[]{clientID},
                new RowMapper<PhoneNote>() {
                    public PhoneNote mapRow(ResultSet rs, int rowNum) throws SQLException {
                        PhoneNote phoneNoteTemp = new PhoneNote();
                        phoneNoteTemp.setNoteID(rs.getInt("note_id"));
                        phoneNoteTemp.setFirstName(rs.getString("first_name"));
                        phoneNoteTemp.setSecondName(rs.getString("second_name"));
                        phoneNoteTemp.setAdditionalName(rs.getString("additional_name"));
                        phoneNoteTemp.setMobileNumber(rs.getString("mobile_number"));
                        phoneNoteTemp.setHomeNumber(rs.getString("home_number"));
                        phoneNoteTemp.setLocation(rs.getString("location"));
                        phoneNoteTemp.setEmail(rs.getString("email"));
                        phoneNoteTemp.setNoteOwner(clientDAO.read(rs.getInt("client_id")));

                        return phoneNoteTemp;
                    }
                });
        return phoneNoteList;
    }

    @Override
    public List<PhoneNote> readBySubStrInMobileNumber(String subStr, int clientID) {
        List<PhoneNote> phoneNoteList = this.jdbcTemplate.query(
                (SQLStatementReadBySubStrInMobileNumber + "'%" + subStr + "%'"),
                new Object[]{clientID},
                new RowMapper<PhoneNote>() {
                    public PhoneNote mapRow(ResultSet rs, int rowNum) throws SQLException {
                        PhoneNote phoneNoteTemp = new PhoneNote();
                        phoneNoteTemp.setNoteID(rs.getInt("note_id"));
                        phoneNoteTemp.setFirstName(rs.getString("first_name"));
                        phoneNoteTemp.setSecondName(rs.getString("second_name"));
                        phoneNoteTemp.setAdditionalName(rs.getString("additional_name"));
                        phoneNoteTemp.setMobileNumber(rs.getString("mobile_number"));
                        phoneNoteTemp.setHomeNumber(rs.getString("home_number"));
                        phoneNoteTemp.setLocation(rs.getString("location"));
                        phoneNoteTemp.setEmail(rs.getString("email"));
                        phoneNoteTemp.setNoteOwner(clientDAO.read(rs.getInt("client_id")));

                        return phoneNoteTemp;
                    }
                });
        return phoneNoteList;
    }

    @Override
    public void create(PhoneNote phoneNote) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
                                @Override
                                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {

                                    PreparedStatement pst = connection.prepareStatement(SQLStatementCreate, Statement.RETURN_GENERATED_KEYS);
                                    pst.setInt(1, phoneNote.getNoteOwner().getClientID());
                                    pst.setString(2, phoneNote.getFirstName());
                                    pst.setString(3, phoneNote.getSecondName());
                                    pst.setString(4, phoneNote.getAdditionalName());
                                    pst.setString(5, phoneNote.getMobileNumber());
                                    pst.setString(6, phoneNote.getHomeNumber());
                                    pst.setString(7, phoneNote.getLocation());
                                    pst.setString(8, phoneNote.getEmail());
                                    return pst;
                                }
                            },
                keyHolder);

        phoneNote.setNoteID(keyHolder.getKey().intValue());
    }

    @Override
    public void update(PhoneNote phoneNote) {
        jdbcTemplate.update(SQLStatementUpdate, phoneNote.getFirstName(), phoneNote.getSecondName(),
                phoneNote.getAdditionalName(), phoneNote.getMobileNumber(), phoneNote.getHomeNumber(),
                phoneNote.getLocation(), phoneNote.getEmail(),phoneNote.getNoteID());
    }

    @Override
    public void delete(int noteID) {
        jdbcTemplate.update(SQLStatementDelete, noteID);
    }

}
