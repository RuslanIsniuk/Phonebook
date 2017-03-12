package com.phonebook.model.dao.impl;

import com.phonebook.entities.Client;
import com.phonebook.model.dao.ClientDAO;
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

public class JDBCClientDAO implements ClientDAO {

    private String SQLStatementRead;
    private String SQLStatementReadUsingClientLogin;
    private String SQLStatementCreate;
    private String SQLStatementUpdate;
    private String SQLStatementDelete;
    private String SQLStatementReadAll;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static class ClientRowMapper implements RowMapper<Client> {
        @Override
        public Client mapRow(ResultSet rs, int rowNum) throws SQLException {
            Client clientTemp = new Client();
            clientTemp.setClientID(rs.getInt("client_id"));
            clientTemp.setClientLogin(rs.getString("client_login"));
            clientTemp.setClientPass(rs.getString("client_pass"));
            clientTemp.setClientFullName(rs.getString("client_full_name"));
            return clientTemp;
        }
    }

    public JDBCClientDAO() {
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
        return this.jdbcTemplate.queryForObject(
                SQLStatementRead,
                new Object[]{clientID},
                new ClientRowMapper());
    }

    @Override
    public Client readByLogin(String clientLogin) {
        return this.jdbcTemplate.queryForObject(
                SQLStatementReadUsingClientLogin,
                new Object[]{clientLogin},
                new ClientRowMapper());
    }

    @Override
    public List<Client> readAll() {
        return this.jdbcTemplate.query(
                SQLStatementReadAll,
                new ClientRowMapper());
    }

    @Override
    public void insert(Client client) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {

                                @Override
                                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {

                                    PreparedStatement pst = connection.prepareStatement(SQLStatementCreate, Statement.RETURN_GENERATED_KEYS);
                                    pst.setString(1, client.getClientLogin());
                                    pst.setString(2, client.getClientPass());
                                    pst.setString(3, client.getClientFullName());
                                    return pst;
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
