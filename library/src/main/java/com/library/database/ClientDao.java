package com.library.database;

import com.library.domain.Client;

import java.util.HashMap;
import java.util.Map;

public class ClientDao extends BaseDao<Client> {

    @Override
    protected String getInsertQuery() {
        return "insert into client (first_name, last_name, passport) " +
                "values (:first_name, :last_name, :passport);";
    }

    @Override
    protected String getUpdateQuery() {
        return "update client set first_name= :first_name, last_name= :last_name, passport= :passport where id=:id;";
    }

    @Override
    protected String getSelectAllQuery() {
        return "select * from client";
    }

    @Override
    protected String getSelectByIdQuery() {
        return "select * from client where id = :id";
    }

    @Override
    protected String getSelectByNameQuery() {
        return "select * from client where passport = :name";
    }

    @Override
    protected String getDeleteByIdQuery() {
        return "delete from client where id= :id";
    }

    @Override
    protected String getDeleteAllQuery() {
        return "delete from client";
    }

    @Override
    protected Map<String, String> getMapping() {
        Map<String, String> columns = new HashMap<>();
        columns.put("first_name", "firstName");
        columns.put("last_name", "lastName");
        columns.put("passport", "passportNumber");
        return columns;
    }

    @Override
    protected Class getThisClass() {
        return Client.class;
    }
}
