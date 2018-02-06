package com.library.database;

import com.library.domain.Journal;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JournalDao extends BaseDao<Journal> {

    private String updateWithReturnDate = "update journal set book_id=:book_id, client_id=:client_id, date_start=:date_start, " +
            "date_end=:date_end, date_return=:date_return " +
            "where id= :id";

    private String updateWithNullReturnDate = "update journal set book_id=:book_id, client_id=:client_id, date_start=:date_start, " +
            "date_end=:date_end where id= :id";

    private String updateQuery = updateWithReturnDate;

    @Override
    public Journal getById(int id) {
        Journal journal = super.getById(id);
        correctFields(journal);
        return journal;
    }

    @Override
    public List<Journal> getAll() {
        List<Journal> list = super.getAll();
        list.forEach(this::correctFields);
        return list;
    }

    private void correctFields(Journal journal) {
        if (journal.getClient() == null || journal.getClient().getId().equals(journal.getClientId())) {
            journal.withClient(new ClientDao().getById(journal.getClientId()));
        }
        if (journal.getBook() == null || journal.getBook().getId().equals(journal.getBookId())) {
            journal.withBook(new BookDao().getById(journal.getBookId()));
        }
        String startDate = ((Object)journal.getStartDate()).toString();
        String endDate = ((Object)journal.getEndDate()).toString();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        journal.withStartDate(LocalDate.parse(startDate, formatter));
        journal.withEndDate(LocalDate.parse(endDate, formatter));

        String returnDate = journal.getReturnDate() != null ? ((Object)journal.getReturnDate()).toString() : null;
        if (returnDate != null) {
            journal.withReturnDate(LocalDate.parse(returnDate, formatter));
        }
    }

    @Override
    public Journal saveOrUpdate(Journal journal) {
        new ClientDao().saveOrUpdate(journal.getClient());
        new BookDao().saveOrUpdate(journal.getBook());
        if (journal.getReturnDate() == null) {
            updateQuery = updateWithNullReturnDate;
        } else {
            updateQuery = updateWithReturnDate;
        }
        return super.saveOrUpdate(journal);
    }

    @Override
    protected String getInsertQuery() {
        return "insert into journal (book_id, client_id, date_start, date_end, date_return) " +
                "values (:book_id, :client_id, :date_start, :date_end, :date_return)";
    }

    @Override
    protected String getUpdateQuery() {
        return updateQuery;
    }

    @Override
    protected String getSelectAllQuery() {
        return "select * from journal";
    }

    @Override
    protected String getSelectByIdQuery() {
        return "select * from journal where id= :id";
    }

    @Override
    protected String getSelectByNameQuery() {
        return null;
    }

    @Override
    protected String getDeleteByIdQuery() {
        return "delete from journal where id= :id";
    }

    @Override
    protected String getDeleteAllQuery() {
        return "delete from journal";
    }

    @Override
    protected Map<String, String> getMapping() {
        Map<String, String> columns = new HashMap<>();
        columns.put("book_id", "bookId");
        columns.put("client_id", "clientId");
        columns.put("date_start", "startDate");
        columns.put("date_end", "endDate");
        columns.put("date_return", "returnDate");
        return columns;
    }

    @Override
    protected Class getThisClass() {
        return Journal.class;
    }
}
