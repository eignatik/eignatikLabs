package com.library.database;

import com.library.Utils;
import com.library.domain.Book;
import com.library.domain.Client;
import com.library.domain.Journal;
import org.junit.Test;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class JournalDaoTest {

    private JournalDao journalDao = new JournalDao();

    @Test
    public void saveNewJournal() throws Exception {
        for (int i = 0; i < 30; i++) {
            List<Book> books = new BookDao().getAll();
            List<Client> clients = new ClientDao().getAll();
            Journal journal = Journal.random()
                    .withBook(Utils.randomElement(books))
                    .withClient(Utils.randomElement(clients));
            journal.withEndDate(journal.getStartDate().plusDays(journal.getBook().getBookType().getDaysBeforeFine()));
            journalDao.saveOrUpdate(journal);
            assertThat(journalDao.getById(journal.getId())).isNotNull();
        }
//        journalDao.deleteById(journal.getId());
    }

    @Test
    public void updateJournal() throws Exception {
        Journal journal = Journal.random();
        journalDao.saveOrUpdate(journal);
        assertThat(journalDao.getById(journal.getId())).isNotNull();
        LocalDate newValue = LocalDate.now().minusDays(2);
        journal.withStartDate(newValue);
        journalDao.saveOrUpdate(journal);
        assertThat(journalDao.getById(journal.getId()).getStartDate()).isEqualTo(Date.valueOf(newValue));
        journalDao.deleteById(journal.getId());
    }

    @Test
    public void saveTwoNewJournals() throws Exception {
        List<Journal> journals = Arrays.asList(Journal.random(), Journal.random());
        journalDao.saveOrUpdateAll(journals);
        journals.forEach(client ->
                assertThat(journalDao.getById(client.getId())).isNotNull());
        journals.forEach(client -> journalDao.deleteById(client.getId()));
    }

    @Test
    public void getAll() throws Exception {
        journalDao.deleteAll();
        List<Journal> journalsToSave = Arrays.asList(Journal.random(), Journal.random(), Journal.random());
        journalDao.saveOrUpdateAll(journalsToSave);
        List<Journal> journalsFromDb = journalDao.getAll();
        assertThat(journalsFromDb).hasSameSizeAs(journalsToSave);
        journalsFromDb.forEach(journal -> {
            assertThat(journal.getClient()).isNotNull();
        });
        journalDao.deleteAll();
    }

}