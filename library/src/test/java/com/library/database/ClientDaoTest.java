package com.library.database;

import com.library.domain.Client;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class ClientDaoTest {

    private ClientDao clientDao = new ClientDao();

    @Test
    public void saveNewClient() throws Exception {
        Client client = Client.random();
        clientDao.saveOrUpdate(client);
        assertThat(clientDao.getById(client.getId())).isNotNull();
        clientDao.deleteById(client.getId());
    }

    @Test
    public void updateClient() throws Exception {
        Client client = Client.random();
        clientDao.saveOrUpdate(client);
        assertThat(clientDao.getById(client.getId())).isNotNull();
        String newValue = "New Last Name";
        client.withLastName(newValue);
        clientDao.saveOrUpdate(client);
        assertThat(clientDao.getById(client.getId()).getLastName()).isEqualTo(newValue);
        clientDao.deleteById(client.getId());
    }

    @Test
    public void saveTwoNewClients() throws Exception {
        List<Client> clients = Arrays.asList(Client.random(), Client.random());
        clientDao.saveOrUpdateAll(clients);
        clients.forEach(client ->
                assertThat(clientDao.getById(client.getId())).isNotNull());
        clients.forEach(client -> clientDao.deleteById(client.getId()));
    }

    @Test
    public void getAll() throws Exception {
        clientDao.deleteAll();
        List<Client> clientsToSave = Arrays.asList(Client.random(), Client.random(), Client.random());
        clientDao.saveOrUpdateAll(clientsToSave);
        List<Client> clientsFromDb = clientDao.getAll();
        assertThat(clientsFromDb).hasSameSizeAs(clientsToSave);
        clientDao.deleteAll();
    }

    @Test
    public void getClientByPassportNumber() {
        clientDao.saveOrUpdate(
                new Client().withLastName("Smith")
                .withFirstName("Yan")
                .withPassportNumber("000111")
        );
        Client client = clientDao.getByName("000111");
        assertNotNull(client);
        assertTrue(client.getLastName().equals("Smith"));
        assertTrue(client.getFirstName().equals("Yan"));
    }
}