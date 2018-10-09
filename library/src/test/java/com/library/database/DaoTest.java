package com.library.database;

import com.library.domain.Book;
import com.library.domain.Client;
import com.library.domain.Journal;
import org.junit.Test;

import static java.util.Arrays.asList;

public class DaoTest {

    private ClientDao clientDao = new ClientDao();
    private BookDao bookDao = new BookDao();
    private JournalDao journalDao = new JournalDao();

    @Test
    public void cleanDB() {
        journalDao.deleteAll();
        clientDao.deleteAll();
        bookDao.deleteAll();
    }

    @Test
    public void fillDbWithRandomData() {
        clientDao.saveOrUpdateAll(asList(Client.random(), Client.random()));
        bookDao.saveOrUpdateAll(asList(Book.random(), Book.random()));
        journalDao.saveOrUpdateAll(asList(Journal.random(), Journal.random()));
    }
}
