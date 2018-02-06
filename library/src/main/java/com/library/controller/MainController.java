package com.library.controller;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class MainController extends BaseController {

    @FXML
    private ImageView bookPicture;

    @FXML
    private ImageView clientPicture;

    @FXML
    private ImageView reportPicture;

    @FXML
    private ImageView journalPicture;

    public void openCrudForBook(MouseEvent mouseEvent) {
        CrudController.setSubject(State.BOOK);
        createNextStage(CRUD_WINDOW, "CRUD", bookPicture);
    }

    public void openCrudForClient(MouseEvent mouseEvent) {
        CrudController.setSubject(State.CLIENT);
        createNextStage(CRUD_WINDOW, "CRUD", clientPicture);
    }

    public void openCrudForJournal(MouseEvent mouseEvent) {
        CrudController.setSubject(State.JOURNAL);
        createNextStage(CRUD_WINDOW, "CRUD", journalPicture);
    }

    public void openReports() {
        createNextStage(REPORTS, "Reports", reportPicture);
    }

    public ImageView getBookPicture() {
        return bookPicture;
    }

    public ImageView getClientPicture() {
        return clientPicture;
    }

    public ImageView getReportPicture() {
        return reportPicture;
    }

    public ImageView getJournalPicture() {
        return journalPicture;
    }
}
