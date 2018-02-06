package com.library.domain;

import com.library.Utils;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Journal extends BaseObject {

    private Client client;
    private Book book;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate returnDate;

    private int clientId;
    private int bookId;

    public static Journal random() {
        LocalDate startDate = Utils.randomDate();
        Book book = Book.random();
        return new Journal()
                .withClient(Client.random())
                .withBook(book)
                .withStartDate(startDate)
                .withEndDate(startDate.plusDays(book.getBookType().getDaysBeforeFine()))
                .withReturnDate(null);
    }

    public Journal withClient(Client client) {
        this.client = client;
        return this;
    }

    public Journal withBook(Book book) {
        this.book = book;
        return this;
    }

    public Journal withStartDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public Journal withEndDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public Journal withReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
        return this;
    }

    public Client getClient() {
        return client;
    }

    public Book getBook() {
        return book;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    @Override
    public Map<String, Object> getParameterMapping() {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("book_id", this.getBook().getId());
        parameters.put("client_id", this.getClient().getId());
        parameters.put("date_start", Date.valueOf(this.getStartDate()));
        parameters.put("date_end", Date.valueOf(this.getEndDate()));
        parameters.put("date_return", this.getReturnDate() == null ? null : Date.valueOf(this.getReturnDate()));
        return parameters;
    }

    public int getClientId() {
        return this.getClient() == null ? clientId : this.getClient().getId();
    }

    public int getBookId() {
        return this.getBook() == null ? bookId : this.getBook().getId();
    }
}
