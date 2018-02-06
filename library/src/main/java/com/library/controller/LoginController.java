package com.library.controller;

import com.library.exception.AuthenticationException;
import com.library.exception.DatabaseException;
import com.library.security.Authentication;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;

public class LoginController extends BaseController {

    @FXML
    protected Label exceptionLabel;

    @FXML
    protected TextField usernameInput;

    @FXML
    protected TextField passwordInput;

    @FXML
    protected Button loginButton;

    @FXML
    protected Button signButton;

    public void runAuthentication() {
        try {
            new Authentication().runAuthentication(usernameInput.getText(), passwordInput.getText());
            createNextStage(MAIN_WINDOW, "Main Window", loginButton);
        } catch (DatabaseException | AuthenticationException e) {
            exceptionLabel.setText(e.getMessage());
            exceptionLabel.setVisible(true);
        }
    }

    public void saveNewUser() {
        try {
            new Authentication().saveNewUser(usernameInput.getText(), passwordInput.getText());
            exceptionLabel.setText("New user is saved.");
        } catch (DatabaseException e) {
            exceptionLabel.setText(e.getMessage());
            exceptionLabel.setVisible(true);
        }
    }

    public TextField getUsernameInput() {
        return usernameInput;
    }

    public TextField getPasswordInput() {
        return passwordInput;
    }

    public Button getLoginButton() {
        return loginButton;
    }

    public Label getExceptionLabel() {
        return exceptionLabel;
    }
}
