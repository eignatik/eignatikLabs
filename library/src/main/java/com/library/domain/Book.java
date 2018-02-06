package com.library.domain;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.HashMap;
import java.util.Map;

public class Book extends BaseObject {

    private String bookName;
    private Integer amount;
    private BookType bookType;

    public static Book random() {
        return new Book()
                .withBookName(RandomStringUtils.randomAlphabetic(10))
                .withAmount(10)
                .withBookType(BookType.USUAL);
    }

    public Book withBookName(String bookName) {
        this.bookName = bookName;
        return this;
    }

    public Book withAmount(Integer amount) {
        this.amount = amount;
        return this;
    }

    public Book withBookType(BookType bookType) {
        this.bookType = bookType;
        return this;
    }

    public String getBookName() {
        return bookName;
    }

    public Integer getAmount() {
        return amount;
    }

    public BookType getBookType() {
        return bookType;
    }

    @Override
    public Map<String, Object> getParameterMapping() {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", this.getBookName());
        parameters.put("amount", this.getAmount());
        parameters.put("book_type_id", this.getBookType().ordinal());
        return parameters;
    }

    @Override
    public String toString() {
        return this.getBookName();
    }
}
