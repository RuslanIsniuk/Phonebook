package com.phonebook.model.dao.impl;

import com.phonebook.entities.Client;
import com.phonebook.model.dao.ClientDAO;
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
public class JDBCClient implements ClientDAO{

    private String SQLStatementRead;
    private String SQLStatementCreate;
    private String SQLStatementUpdate;
    private String SQLStatementDelete;
    private String SQLStatementReadAll;

    private ApplicationContext ac = new ClassPathXmlApplicationContext("spring.xml");
    private JdbcTemplate jdbcTemplate = (JdbcTemplate) ac.getBean("jdbcTemplate");

    public JDBCClient(){
        Properties properties = new Properties();

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("JDBCClientConfig.properties")) {

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

    public Client read(String clientLogin,String clientPass){
        Client client = this.jdbcTemplate.queryForObject(
                SQLStatementRead,
                new Object[]{clientLogin,clientPass},
                new RowMapper<Client>() {
                    public Client mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Client clientTemp = new Client();
                        clientTemp.setClientLogin(rs.getString("client_login"));
                        clientTemp.setClientPass(rs.getString("client_pass"));
                        clientTemp.setClientFullName(rs.getString("client_full_name"));
                        return clientTemp;
                    }
                });
        return client;
    }

    public List<Client> readAll(){
        List<Client> clientList = this.jdbcTemplate.query(
                SQLStatementReadAll,
                new RowMapper<Client>() {
                    public Client mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Client clientTemp = new Client();
                        clientTemp.setClientLogin(rs.getString("client_login"));
                        clientTemp.setClientPass(rs.getString("client_pass"));
                        clientTemp.setClientFullName(rs.getString("client_full_name"));
                        return clientTemp;
                    }
                });
        return clientList;
    }

    public void create (Client client){
        jdbcTemplate.update(SQLStatementCreate,client.getClientLogin(),client.getClientPass(),client.getClientFullName());
    }

    public void update (Client client){
        jdbcTemplate.update(SQLStatementUpdate,client.getClientPass(),client.getClientFullName(),client.getClientLogin());
    }

    public void delete(String clientLogin){
        jdbcTemplate.update(SQLStatementDelete,clientLogin);
    }
}
