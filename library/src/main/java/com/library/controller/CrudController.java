package com.library.controller;

import com.library.Utils;
import com.library.database.BookDao;
import com.library.database.ClientDao;
import com.library.database.JournalDao;
import com.library.domain.*;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;
import java.util.ResourceBundle;

@SuppressWarnings("unchecked")
public class CrudController extends BaseController implements Initializable {

    private static State subject;

    private static State action;

    protected ClientDao clientDao = new ClientDao();
    protected BookDao bookDao = new BookDao();
    protected JournalDao journalDao = new JournalDao();

    @FXML
    private TableView tableView;

    @FXML
    private Button addNewButton;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Pane returnPanel;

    @FXML
    private DatePicker returnDatePicker;

    @FXML
    private Button returnButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        if (tableView != null) {
            tableView.setEditable(true);
        }
        returnPanel.setVisible(false);

        if (action == null) {
            switch (subject) {
                case BOOK:
                    fillBooks();
                    break;
                case CLIENT:
                    fillClients();
                    break;
                case JOURNAL:
                    fillJournal();
                    returnPanel.setVisible(true);
                    returnButton.setVisible(true);
                    returnButton.setDisable(false);
                    returnDatePicker.setVisible(Utils.isAdmin);
                    break;
                default:
                    throw new IllegalStateException("State is not defined!");
            }
        }
    }

    public void checkSelectedItem() {
        if (subject == State.JOURNAL) {
            Journal item = (Journal) tableView.getSelectionModel().getSelectedItem();
            if (item != null && (!Utils.isAdmin && item.getReturnDate() != null)) {
                returnPanel.setDisable(true);
            } else {
                returnPanel.setDisable(false);
            }
        }
    }

    public void returnBook() {
        LocalDate date = returnDatePicker.getValue() == null ? LocalDate.now() : returnDatePicker.getValue();
        Journal journal = (Journal) tableView.getSelectionModel().getSelectedItem();
        if (date != null && journal != null) {
            if (isReturnDateCorrect(date, journal)) {
                noticeIfExpired(date, journal);
                journalDao.saveOrUpdate(journal.withReturnDate(date));
                returnPanel.setDisable(true);
                fillJournal();
            } else {
                showAlert(
                        Alert.AlertType.ERROR,
                        "Date picking error",
                        "Date picking error.",
                        "Incorrect date has been chosen. \n Select date in range between "
                                + journal.getStartDate() + " and " + LocalDate.now());
            }
        }
    }

    public void openAddingDialog(ActionEvent actionEvent) {
        setAction(State.ADD);
        createNextStage(ADD_EDIT_WINDOW, action + " " + subject, addNewButton);
    }

    public void openEditingDialog(ActionEvent actionEvent) {
        BaseObject baseObject = (BaseObject) tableView.getSelectionModel().getSelectedItem();
        if (baseObject != null) {
            AddEditController.setItemId(baseObject.getId());
            setAction(State.EDIT);
        } else {
            setAction(State.ADD);
        }
        createNextStage(ADD_EDIT_WINDOW, action + " " + subject, addNewButton);
    }

    public void deleteItem(ActionEvent actionEvent) {
        try {
            BaseObject baseObject = (BaseObject) tableView.getSelectionModel().getSelectedItem();
            switch (getSubject()) {
                case BOOK:
                    bookDao.deleteById(baseObject.getId());
                    fillBooks();
                    break;
                case CLIENT:
                    clientDao.deleteById(baseObject.getId());
                    fillClients();
                    break;
                case JOURNAL:
                    journalDao.deleteById(baseObject.getId());
                    fillJournal();
                    break;
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Can't delete item",
                    "Item has active journal record",
                    "Please, update journal first before deleting the item. " +
                            "Make sure he has paid all the fines.");
        }
    }

    private void fillBooks() {
        tableView.setItems(null);
        List<Book> books = bookDao.getAll();

        TableColumn bookNameColumn = new TableColumn("Name");
        bookNameColumn.cellValueFactoryProperty().setValue(new PropertyValueFactory<Book, String>("bookName"));
        TableColumn amountColumn = new TableColumn("Amount");
        amountColumn.cellValueFactoryProperty().setValue(new PropertyValueFactory<Book, Integer>("amount"));
        TableColumn typeColumn = new TableColumn("Type");
        typeColumn.cellValueFactoryProperty().setValue(new PropertyValueFactory<Book, BookType>("bookType"));

        tableView.getColumns().clear();
        tableView.getColumns().addAll(bookNameColumn, amountColumn, typeColumn);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tableView.setItems(FXCollections.observableArrayList(books));
    }

    private void fillClients() {
        tableView.setItems(null);
        List<Client> clients = clientDao.getAll();

        TableColumn firstNameColumn = new TableColumn("First Name");
        firstNameColumn.cellValueFactoryProperty().setValue(new PropertyValueFactory<Book, String>("firstName"));
        TableColumn lastNameColumn = new TableColumn("Last Name");
        lastNameColumn.cellValueFactoryProperty().setValue(new PropertyValueFactory<Book, String>("lastName"));
        TableColumn passportColumn = new TableColumn("Passport");
        passportColumn.cellValueFactoryProperty().setValue(new PropertyValueFactory<Book, String>("passportNumber"));

        tableView.getColumns().clear();
        tableView.getColumns().addAll(firstNameColumn, lastNameColumn, passportColumn);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tableView.setItems(FXCollections.observableArrayList(clients));
    }

    private boolean isReturnDateCorrect(LocalDate date, Journal journal) {
        return !date.isBefore(journal.getStartDate())
                && (date.isAfter(journal.getStartDate()) && date.isBefore(LocalDate.now()) || date.isEqual(LocalDate.now()));
    }

    private void noticeIfExpired(LocalDate date, Journal journal) {
        if (date.isEqual(journal.getEndDate()) || date.isAfter(journal.getEndDate())) {
            showAlert(
                    Alert.AlertType.WARNING,
                    "Expiring notice",
                    "Book has been returned out of end date..",
                    "Please, notice that book has been returned to library after end date (" + journal.getEndDate() + "). It means that client have to pay fine equaled to days count after expiring.");
        }
    }

    private void fillJournal() {
        tableView.setItems(null);
        List<Journal> journals = journalDao.getAll();

        TableColumn bookColumn = new TableColumn("Book");

        bookColumn.setCellValueFactory(cellDataFeatures -> {
            TableColumn.CellDataFeatures value = (TableColumn.CellDataFeatures) cellDataFeatures;
            Journal journal = (Journal) value.getValue();
            return Bindings.createStringBinding(() -> journal.getBook().getBookName());
        });

        TableColumn clientColumn = new TableColumn("Client");

        clientColumn.setCellValueFactory(cellDataFeatures -> {
            TableColumn.CellDataFeatures value = (TableColumn.CellDataFeatures) cellDataFeatures;
            Journal journal = (Journal) value.getValue();
            return Bindings.createStringBinding(() -> journal.getClient().getFirstName()
                    + " " + journal.getClient().getLastName());
        });

        TableColumn startDateColumn = new TableColumn("Start date");
        startDateColumn.cellValueFactoryProperty().setValue(new PropertyValueFactory<Book, Date>("startDate"));
        TableColumn endDateColumn = new TableColumn("End date");
        endDateColumn.cellValueFactoryProperty().setValue(new PropertyValueFactory<Book, Date>("endDate"));
        TableColumn returnDateColumn = new TableColumn("Return date");
        returnDateColumn.cellValueFactoryProperty().setValue(new PropertyValueFactory<Book, Date>("returnDate"));

        tableView.getColumns().clear();
        tableView.getColumns().addAll(bookColumn, clientColumn, startDateColumn, endDateColumn, returnDateColumn);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tableView.setItems(FXCollections.observableArrayList(journals));
    }

    public static State getSubject() {
        return subject;
    }

    public static void setSubject(State stateToSet) {
        subject = stateToSet;
    }

    public static State getAction() {
        return action;
    }

    public static void setAction(State action) {
        CrudController.action = action;
    }
}
