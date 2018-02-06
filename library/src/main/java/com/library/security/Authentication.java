package com.library.security;

import com.library.Utils;
import com.library.database.UserDao;
import com.library.exception.AuthenticationException;
import com.library.exception.DatabaseException;

import static com.library.security.Hash.hash;

public class Authentication {

    private UserDao userDao = new UserDao();

    public void runAuthentication(String username, String passwordFromForm)
            throws DatabaseException, AuthenticationException {
        if (username == null || passwordFromForm == null) {
            throw new AuthenticationException();
        }
        String password = userDao.getPasswordByUser(username);
        if (password == null) {
            throw new DatabaseException("User with name " + username + " doesn't exist");
        }
        String hashedPasswordFromForm = hash(passwordFromForm);
        if (!password.equals(hashedPasswordFromForm)) {
            throw new AuthenticationException();
        } else {
            if (userDao.getIsAdmin(username)) {
                Utils.isAdmin = true;
            }
        }
    }


    public void saveNewUser(String username, String password) throws DatabaseException {
        if (userDao.getPasswordByUser(username) != null) {
            throw new DatabaseException("User with name " + username + " already exists");
        }
        String hashedPasswordFromForm = hash(password);
        userDao.saveNewUserToDB(username, hashedPasswordFromForm);
    }
}
