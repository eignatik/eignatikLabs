package com.library.database;

import com.library.exception.DatabaseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static com.library.Utils.loadProperties;

public class DbConnection {

    private static final Logger logger = LogManager.getLogger(DbConnection.class);

    private static Sql2o connectionFactory;

    static {
        Properties properties = loadProperties();
        connectionFactory = new Sql2o(properties.getProperty("database"),
                properties.getProperty("username"),
                properties.getProperty("password"));
    }

    private static Connection openConnection;

    public static Connection getConnection() throws DatabaseException {
        try {
            openConnection = connectionFactory.open();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return openConnection;
    }
}
