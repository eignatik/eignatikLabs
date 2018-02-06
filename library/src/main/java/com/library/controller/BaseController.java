package com.library.controller;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Control;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.time.LocalDate;

public class BaseController {

    private static final Logger logger = LogManager.getLogger(BaseController.class);

    protected static final String MAIN_WINDOW = "windows/main.fxml";
    protected static final String CRUD_WINDOW = "windows/crud.fxml";
    protected static final String ADD_EDIT_WINDOW = "windows/add-edit.fxml";
    protected static final String REPORTS = "windows/reports.fxml";

    void createNextStage(String fxmlPath, String title, Node relatedObject) {
        createNextStage(fxmlPath, title, (Stage) relatedObject.getScene().getWindow());
    }

    void createNextStage(String fxmlPath, String title, Stage stage) {
        try {
            stage.close();
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getClassLoader().getResource(fxmlPath));
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
            if (fxmlPath.equals(CRUD_WINDOW) || fxmlPath.equals(ADD_EDIT_WINDOW) || fxmlPath.equals(REPORTS)) {
                CrudController.setAction(null);
                stage.setOnCloseRequest(event -> createNextStage(MAIN_WINDOW, "Main", new Stage()));
            }
        } catch (IOException e) {
            logger.error(e);
        }
    }

    void showAlert(Alert.AlertType alertType, String alertTitle, String alertHeader, String alertContent) {
        Alert alert = new Alert(alertType);
        alert.setTitle(alertTitle);
        alert.setHeaderText(alertHeader);
        alert.setContentText(alertContent);
        alert.showAndWait();
    }
}
