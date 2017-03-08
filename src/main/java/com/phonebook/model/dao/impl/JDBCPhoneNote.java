package com.phonebook.model.dao.impl;

import com.phonebook.entities.PhoneNote;
import com.phonebook.model.dao.PhoneNoteDAO;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

/**
 * Created by Руслан on 07.03.2017.
 */
public class JDBCPhoneNote implements PhoneNoteDAO{
    private String SQLStatementRead;
    private String SQLStatementCreate;
    private String SQLStatementUpdate;
    private String SQLStatementDelete;
    private String SQLStatementReadAll;

    private ApplicationContext ac = new ClassPathXmlApplicationContext("spring.xml");
    private JdbcTemplate jdbcTemplate = (JdbcTemplate) ac.getBean("jdbcTemplate");

    public JDBCPhoneNote(){
        Properties properties = new Properties();

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("JDBCPhoneNoteConfig.properties")) {

            properties.load(inputStream);

            SQLStatementRead = properties.getProperty("StatementRead");
            SQLStatementUpdate = properties.getProperty("StatementUpdate");
            SQLStatementDelete = properties.getProperty("StatementDelete");
            SQLStatementCreate = properties.getProperty("StatementCreate");
            SQLStatementReadAll = properties.getProperty("StatementReadAll");

        } catch (IOException ex) {
            ex.getMessage();
        }
    }

    @Override
    public PhoneNote read(String firstName, String secondName){
        PhoneNote phoneNote = this.jdbcTemplate.queryForObject(
                SQLStatementRead,
                new Object[]{firstName,secondName},
                new RowMapper<PhoneNote>() {
                    public PhoneNote mapRow(ResultSet rs, int rowNum) throws SQLException {
                        PhoneNote phoneNoteTemp = new PhoneNote();
                        phoneNoteTemp.setFirstName(rs.getString("first_name"));
                        phoneNoteTemp.setSecondName(rs.getString("second_name"));
                        phoneNoteTemp.setAdditionalName(rs.getString("additional_name"));
                        phoneNoteTemp.setMobileNumber(rs.getString("mobile_number"));
                        phoneNoteTemp.setHomeNumber(rs.getString("home_number"));
                        phoneNoteTemp.setLocation(rs.getString("location"));
                        phoneNoteTemp.setEmail(rs.getString("email"));

                        return phoneNoteTemp;
                    }
                });
        return phoneNote;
    }

    @Override
    public List<PhoneNote> readAll(){
        List<PhoneNote> phoneNoteList = this.jdbcTemplate.query(
                SQLStatementReadAll,
                new RowMapper<PhoneNote>() {
                    public PhoneNote mapRow(ResultSet rs, int rowNum) throws SQLException {
                        PhoneNote phoneNoteTemp = new PhoneNote();
                        phoneNoteTemp.setFirstName(rs.getString("first_name"));
                        phoneNoteTemp.setSecondName(rs.getString("second_name"));
                        phoneNoteTemp.setAdditionalName(rs.getString("additional_name"));
                        phoneNoteTemp.setMobileNumber(rs.getString("mobile_number"));
                        phoneNoteTemp.setHomeNumber(rs.getString("home_number"));
                        phoneNoteTemp.setLocation(rs.getString("location"));
                        phoneNoteTemp.setEmail(rs.getString("email"));

                        return phoneNoteTemp;
                    }
                });
        return phoneNoteList;
    }

    @Override
    public void create(PhoneNote phoneNote){
        jdbcTemplate.update(SQLStatementCreate,phoneNote.getFirstName(),phoneNote.getSecondName(),
                phoneNote.getAdditionalName(),phoneNote.getMobileNumber(),phoneNote.getHomeNumber(),
                phoneNote.getLocation(),phoneNote.getEmail());
    }

    @Override
    public void update(String firstName, String secondName,PhoneNote phoneNote){
        jdbcTemplate.update(SQLStatementUpdate,phoneNote.getFirstName(),phoneNote.getSecondName(),
                phoneNote.getAdditionalName(),phoneNote.getMobileNumber(),phoneNote.getHomeNumber(),
                phoneNote.getLocation(),phoneNote.getEmail(),firstName,secondName);
    }

    @Override
    public void delete(String firstName, String secondName){
        jdbcTemplate.update(SQLStatementDelete,firstName,secondName);
    }

}
