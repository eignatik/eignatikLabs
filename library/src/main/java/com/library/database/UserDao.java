package com.library.database;

import com.library.exception.DatabaseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sql2o.Connection;

public class UserDao {

    private static final Logger logger = LogManager.getLogger(UserDao.class);

    public String getPasswordByUser(String username) throws DatabaseException {
        String query = "select pass from lib_user where username = :username";
        try(Connection connection = DbConnection.getConnection()) {
            return connection.createQuery(query)
                    .addParameter("username", username)
                    .executeScalar(String.class);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DatabaseException(e.getMessage(), e);
        }
    }

    public boolean getIsAdmin(String username) throws DatabaseException {
        String query = "select admin from lib_user where username = :username";
        try(Connection connection = DbConnection.getConnection()) {
            return connection.createQuery(query)
                    .addParameter("username", username)
                    .executeScalar(Boolean.class);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DatabaseException(e.getMessage(), e);
        }
    }

    public void saveNewUserToDB(String username, String password) throws DatabaseException {
        String query = "insert into lib_user (username, pass, admin) VALUES " +
                "(:username, :pass, false)";
        try(Connection connection = DbConnection.getConnection()) {
            connection.createQuery(query)
                    .addParameter("username", username)
                    .addParameter("pass", password)
                    .executeUpdate();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DatabaseException(e.getMessage(), e);
        }
    }
}
