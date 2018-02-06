package com.library;

import com.library.domain.Journal;
import org.apache.logging.log4j.LogManager;

import java.io.FileWriter;
import java.io.IOException;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class ReportWriter {
    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(ReportWriter.class);

    private static final String COMA_SEPARATOR = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";

    public static void writeReport(String fileName, List<Journal> journals, String customInfo) {

        FileWriter fileWriter = null;
        String FILE_HEADER = "Start date,End date,Return date,Fine,First Name,Last name,Book";
        try {
            fileWriter = new FileWriter(fileName);
            if (customInfo != null && !customInfo.isEmpty()) fileWriter.append(customInfo).append(NEW_LINE_SEPARATOR);
            fileWriter.append(FILE_HEADER);
            fileWriter.append(NEW_LINE_SEPARATOR);

            for (Journal journal : journals) {
                fileWriter.append(journal.getStartDate().toString());
                fileWriter.append(COMA_SEPARATOR);
                fileWriter.append(journal.getEndDate().toString());
                fileWriter.append(COMA_SEPARATOR);
                if (journal.getReturnDate() != null) {
                    fileWriter.append(journal.getReturnDate().toString());
                } else {
                    fileWriter.append(" ");
                }
                fileWriter.append(COMA_SEPARATOR);
                long fine = (ChronoUnit.DAYS.between(journal.getEndDate(), journal.getReturnDate()))
                        * journal.getBook().getBookType().getFinePerDay();
                if (fine < 0) {
                    fine = 0;
                }
                fileWriter.append(String.valueOf(fine));
                fileWriter.append(COMA_SEPARATOR);
                fileWriter.append(journal.getClient().getFirstName());
                fileWriter.append(COMA_SEPARATOR);
                fileWriter.append(journal.getClient().getLastName());
                fileWriter.append(COMA_SEPARATOR);
                fileWriter.append(journal.getBook().getBookName());
                fileWriter.append(NEW_LINE_SEPARATOR);
            }
            LOGGER.info("CVS has been created");
        } catch (Exception e) {
            System.out.println("Error in CsvFileWriter");
            e.printStackTrace();
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}