package com.library.database;

import com.library.exception.DatabaseException;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.Assert.*;

public class DbConnectionTest {

    @Test
    public void testConnection() throws DatabaseException {
        assertThatCode(DbConnection::getConnection).doesNotThrowAnyException();
    }

}