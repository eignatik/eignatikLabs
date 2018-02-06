package com.library.security;

import com.library.database.UserDao;
import com.library.exception.AuthenticationException;
import com.library.exception.DatabaseException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class AuthenticationTest {

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String WRONG_PASSWORD = "not a password";

    @Mock
    private UserDao userDao;

    @InjectMocks
    private Authentication authentication = new Authentication();

    @Before
    public void initMock() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldPassWithValidUser() throws DatabaseException, AuthenticationException {
        when(userDao.getPasswordByUser(eq(USERNAME))).thenReturn(Hash.hash(PASSWORD));
        assertThatCode(() -> authentication.runAuthentication(USERNAME, PASSWORD))
                .doesNotThrowAnyException();
    }

    @Test
    public void shouldThrowExceptionWithNotExistingUser() throws DatabaseException {
        when(userDao.getPasswordByUser(eq(USERNAME))).thenReturn(null);
        assertThatExceptionOfType(DatabaseException.class).isThrownBy(() -> {
            authentication.runAuthentication(USERNAME, PASSWORD);
        });
    }

    @Test
    public void shouldThrowExceptionWithExistingUserAndWrongPassword() throws DatabaseException {
        when(userDao.getPasswordByUser(eq(USERNAME))).thenReturn(WRONG_PASSWORD);
        assertThatExceptionOfType(AuthenticationException.class).isThrownBy(() -> {
            authentication.runAuthentication(USERNAME, PASSWORD);
        });
    }

    @Test
    public void shouldThrowExceptionWhenUserAndPasswordAreNull() {
        assertThatExceptionOfType(AuthenticationException.class).isThrownBy(() -> {
            authentication.runAuthentication(null, null);
        });
    }

}