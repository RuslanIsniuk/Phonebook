package com.phonebook.model.dao.impl;

import com.phonebook.entities.Client;
import com.phonebook.model.dao.ClientDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
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

/**
 * Created by Руслан on 07.03.2017.
 */
public class JDBCClient implements ClientDAO {

    private String SQLStatementRead;
    private String SQLStatementReadUsingClientLogin;
    private String SQLStatementCreate;
    private String SQLStatementUpdate;
    private String SQLStatementDelete;
    private String SQLStatementReadAll;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public JDBCClient(){
        Properties properties = new Properties();

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("JDBCClientConfig.properties")) {

            properties.load(inputStream);

            SQLStatementRead = properties.getProperty("StatementRead");
            SQLStatementReadUsingClientLogin = properties.getProperty("StatementReadUsingClientLogin");
            SQLStatementUpdate = properties.getProperty("StatementUpdate");
            SQLStatementDelete = properties.getProperty("StatementDelete");
            SQLStatementCreate = properties.getProperty("StatementCreate");
            SQLStatementReadAll = properties.getProperty("StatementReadAll");

        } catch (IOException ex) {
            ex.getMessage();
        }
    }

    @Override
    public Client read(int clientID) {
        Client client = this.jdbcTemplate.queryForObject(
                SQLStatementRead,
                new Object[]{clientID},
                new RowMapper<Client>() {
                    public Client mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Client clientTemp = new Client();
                        clientTemp.setClientID(rs.getInt("client_id"));
                        clientTemp.setClientLogin(rs.getString("client_login"));
                        clientTemp.setClientPass(rs.getString("client_pass"));
                        clientTemp.setClientFullName(rs.getString("client_full_name"));
                        return clientTemp;
                    }
                });
        return client;
    }

    @Override
    public Client readByLogin(String clientLogin) {
        Client client = this.jdbcTemplate.queryForObject(
                SQLStatementReadUsingClientLogin,
                new Object[]{clientLogin},
                new RowMapper<Client>() {
                    public Client mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Client clientTemp = new Client();
                        clientTemp.setClientID(rs.getInt("client_id"));
                        clientTemp.setClientLogin(rs.getString("client_login"));
                        clientTemp.setClientPass(rs.getString("client_pass"));
                        clientTemp.setClientFullName(rs.getString("client_full_name"));
                        return clientTemp;
                    }
                });
        return client;
    }

    @Override
    public List<Client> readAll() {
        List<Client> clientList = this.jdbcTemplate.query(
                SQLStatementReadAll,
                new RowMapper<Client>() {
                    public Client mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Client clientTemp = new Client();
                        clientTemp.setClientID(rs.getInt("client_id"));
                        clientTemp.setClientLogin(rs.getString("client_login"));
                        clientTemp.setClientPass(rs.getString("client_pass"));
                        clientTemp.setClientFullName(rs.getString("client_full_name"));
                        return clientTemp;
                    }
                });
        return clientList;
    }

    @Override
    public void create(Client client) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {

                                @Override
                                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {

                                    PreparedStatement pst = connection.prepareStatement(SQLStatementCreate, Statement.RETURN_GENERATED_KEYS);
                                        pst.setString(1, client.getClientLogin());
                                        pst.setString(2, client.getClientPass());
                                        pst.setString(3, client.getClientFullName());
                                        return   pst;
                                }
                            },
                keyHolder);
        client.setClientID(keyHolder.getKey().intValue());
    }

    @Override
    public void update(Client client) {
        jdbcTemplate.update(SQLStatementUpdate, client.getClientPass(), client.getClientFullName(), client.getClientLogin());
    }

    @Override
    public void delete(int clientID) {
        jdbcTemplate.update(SQLStatementDelete, clientID);
    }
}
