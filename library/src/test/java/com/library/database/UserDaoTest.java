package com.library.database;

import org.junit.Test;

public class UserDaoTest {

    @Test
    public void getPasswordByUser() throws Exception {
        UserDao userDao = new UserDao();
        userDao.getPasswordByUser("user");
    }

}