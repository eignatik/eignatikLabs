package com.library.controller;

import com.library.ReportWriter;
import com.library.database.JournalDao;
import com.library.domain.Journal;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.Pane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class ReportsController extends BaseController implements Initializable {
    private static final Logger LOGGER = LogManager.getLogger(ReportsController.class);

    private static final String RETURNED_WITH_FINES = "returnedWithFines";
    private static final String NOT_RETURNED_REPORT = "notReturnedBooksReport";
    private static final String RANGE_REPORT = "rangeReport";
    private static final String CVS_EXT = ".csv";

    @FXML
    private ComboBox reportPicker;

    @FXML
    private Pane rangeReport;

    @FXML
    private Pane outOfDate;

    @FXML
    private Pane didntReturn;

    @FXML
    private CheckBox dateFilter;

    @FXML
    private DatePicker rangePicker;

    @FXML
    private DatePicker fromDate;

    @FXML
    private DatePicker toDate;

    private static Reports currentReportType;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<String> reportKinds = List.of(
                Reports.RETURNED_WITH_FINES.getValue(),
                Reports.IN_RANGE.getValue(),
                Reports.NOT_RETURNED.getValue());
        reportPicker.setItems(FXCollections.observableArrayList(reportKinds));
    }

    public void showReportPanel() {
        String selection = (String) reportPicker.getSelectionModel().getSelectedItem();
        currentReportType = Reports.getByValue(selection);
        switch (currentReportType) {
            case RETURNED_WITH_FINES:
                outOfDate.setVisible(true);
                rangeReport.setVisible(false);
                didntReturn.setVisible(false);
                break;
            case NOT_RETURNED:
                didntReturn.setVisible(true);
                outOfDate.setVisible(false);
                rangeReport.setVisible(false);
                break;
            case IN_RANGE:
                rangeReport.setVisible(true);
                didntReturn.setVisible(false);
                outOfDate.setVisible(false);
                break;
            default:
                rangeReport.setVisible(false);
                didntReturn.setVisible(false);
                outOfDate.setVisible(false);
        }
    }

    public void disableDataPicker() {
        if (dateFilter.isSelected()) {
            rangePicker.setDisable(false);
        } else {
            rangePicker.setDisable(true);
        }
    }

    public void createReport() {
        String filename;
        if (currentReportType == null) {
            showAlert(Alert.AlertType.ERROR,"Report type",
                    "Report type is not selected",
                    "Please, select type of report to generate");
        } else {
            switch (currentReportType) {
                case RETURNED_WITH_FINES:
                    filename = RETURNED_WITH_FINES + new Date().getTime() + CVS_EXT;
                    ReportWriter.writeReport(filename, getAllOutOfDate(), "All clients who have returned books after end date and have to pay fines");
                    showAlert(Alert.AlertType.INFORMATION, "Report has been created", "Report has been successfully created", "New CSV report in file " + filename + " has been created.");
                    break;
                case NOT_RETURNED:
                    filename = NOT_RETURNED_REPORT + new Date().getTime() + CVS_EXT;
                    if (dateFilter.isSelected()) {
                        ReportWriter.writeReport(filename, getAllNotReturnedJournalsBeforeDate(), "All clients who haven't return books");
                    } else {
                        ReportWriter.writeReport(filename, getAllNotReturnedJournals(), "All clients who haven't return books");
                    }
                    showAlert(Alert.AlertType.INFORMATION, "Report has been created", "Report has been successfully created", "New CSV report in file " + filename + " has been created.");
                    break;
                case IN_RANGE:
                    if (fromDate.getValue() == null || toDate.getValue() == null) {
                        fromDate.setValue(LocalDate.MIN);
                        toDate.setValue(LocalDate.now());
                    }
                    filename = RANGE_REPORT + new Date().getTime() + CVS_EXT;
                    ReportWriter.writeReport(filename, getJournalInRange(), "All journal records from " + fromDate.getValue() + " to " + toDate.getValue());
                    showAlert(Alert.AlertType.INFORMATION, "Report has been created", "Report has been successfully created", "New CSV report in file " + filename + " has been created. The whole journal from " + fromDate.getValue() + " to " + toDate.getValue());
                    break;
                default:
                    LOGGER.info("Cant determine the type of the report");
                    showAlert(Alert.AlertType.ERROR, "Report hasn't been created", "Report has not been created!", "New CSV report is unavailable to create. Report this issue to your system administrator or developers team. .");
            }
        }
    }

    private List<Journal> getAllOutOfDate() {
        List<Journal> journals = new ArrayList<>();
        for (Journal journal : new JournalDao().getAll()) {
            if (journal.getReturnDate() != null
                    && journal.getReturnDate().isAfter(journal.getEndDate())) {
                journals.add(journal);
            }
        }
        return journals;
    }

    private List<Journal> getAllNotReturnedJournalsBeforeDate() {
        LocalDate date = rangePicker.getValue();
        List<Journal> journals = new ArrayList<>();
        for (Journal journal : new JournalDao().getAll()) {
            if (journal.getReturnDate() == null && journal.getEndDate().isBefore(date)) journals.add(journal);
        }
        return journals;
    }

    private List<Journal> getAllNotReturnedJournals() {
        List<Journal> journals = new ArrayList<>();
        for (Journal journal : new JournalDao().getAll()) {
            if (journal.getReturnDate() == null) journals.add(journal);
        }
        return journals;
    }

    private List<Journal> getJournalInRange() {
        LocalDate fromDateValue = fromDate.getValue();
        LocalDate toDateValue = toDate.getValue();
        List<Journal> journals = new ArrayList<>();
        for (Journal journal : new JournalDao().getAll()) {
            if (journal.getStartDate().isAfter(fromDateValue) && journal.getStartDate().isBefore(toDateValue)) journals.add(journal);
        }
        return journals;
    }

    enum Reports {
        RETURNED_WITH_FINES("Books that have been returned after end date"),
        IN_RANGE("Journal records in specified time range"),
        NOT_RETURNED("Clients who haven't return their books");

        Reports(String value) {
            this.value = value;
        }

        private String value;

        public String getValue() {
            return value;
        }

        public static Reports getByValue(String value) {
            switch (value) {
                case "Books that have been returned after end date" : return RETURNED_WITH_FINES;
                case "Journal records in specified time range" : return IN_RANGE;
                case "Clients who haven't return their books" : return NOT_RETURNED;
                default: throw new RuntimeException("No available reports for type " + value);
            }
        }
    }
}
