package com.library.database;

import com.library.domain.Book;
import com.library.domain.BookType;
import com.library.domain.Client;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class BookDaoTest {

    private BookDao bookDao = new BookDao();

    @Test
    public void saveNewBook() throws Exception {
        Book book = Book.random();
        bookDao.saveOrUpdate(book);
        assertThat(bookDao.getById(book.getId())).isNotNull();
        bookDao.deleteById(book.getId());
    }

    @Test
    public void updateBook() throws Exception {
        Book book = Book.random();
        bookDao.saveOrUpdate(book);
        assertThat(bookDao.getById(book.getId())).isNotNull();
        String newValue = "New Book";
        book.withBookName(newValue);
        bookDao.saveOrUpdate(book);
        assertThat(bookDao.getById(book.getId()).getBookName()).isEqualTo(newValue);
        bookDao.deleteById(book.getId());
    }

    @Test
    public void saveTwoNewBooks() throws Exception {
        List<Book> books = Arrays.asList(Book.random(), Book.random());
        bookDao.saveOrUpdateAll(books);
        books.forEach(client ->
                assertThat(bookDao.getById(client.getId())).isNotNull());
        books.forEach(client -> bookDao.deleteById(client.getId()));
    }

    @Test
    public void getAll() throws Exception {
        bookDao.deleteAll();
        List<Book> booksToSave = Arrays.asList(Book.random(), Book.random(), Book.random());
        bookDao.saveOrUpdateAll(booksToSave);
        List<Book> booksFromDb = bookDao.getAll();
        assertThat(booksFromDb).hasSameSizeAs(booksToSave);
        bookDao.deleteAll();
    }

    @Test
    public void getBookByBookName() {
        bookDao.saveOrUpdate(
                new Book().withBookName("name1")
                .withAmount(1)
                .withBookType(BookType.USUAL)
        );
        Book book = bookDao.getByName("name1");
        assertNotNull(book);
        assertTrue(book.getBookName().equals("name1"));
    }

}