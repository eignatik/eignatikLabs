package com.library.database;

import com.library.domain.Book;

import java.util.HashMap;
import java.util.Map;

public class BookDao extends BaseDao<Book> {
    @Override
    protected String getInsertQuery() {
        return "insert into book (name, amount, book_type_id) " +
                "values (:name, :amount, :book_type_id)";
    }

    @Override
    protected String getUpdateQuery() {
        return "update book set name= :name, amount= :amount, " +
                "book_type_id= :book_type_id where id= :id";

    }

    @Override
    protected String getSelectAllQuery() {
        return "select * from book";
    }

    @Override
    protected String getSelectByIdQuery() {
        return "select * from book where id = :id";
    }

    @Override
    protected String getSelectByNameQuery() {
        return "select * from book where name = :name";
    }

    @Override
    protected String getDeleteByIdQuery() {
        return "delete from book where id= :id";
    }

    @Override
    protected String getDeleteAllQuery() {
        return "delete from book";
    }

    @Override
    protected Map<String, String> getMapping() {
        Map<String, String> columns = new HashMap<>();
        columns.put("name", "bookName");
        columns.put("amount", "amount");
        columns.put("book_type_id", "bookType");
        return columns;
    }

    @Override
    protected Class getThisClass() {
        return Book.class;
    }
}
