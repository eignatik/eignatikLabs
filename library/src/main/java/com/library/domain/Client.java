package com.library.domain;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.HashMap;
import java.util.Map;

public class Client extends BaseObject {

    private String firstName;
    private String lastName;
    private String passportNumber;

    public static Client random() {
        return new Client()
                .withFirstName(RandomStringUtils.randomAlphabetic(10))
                .withLastName(RandomStringUtils.randomAlphabetic(10))
                .withPassportNumber(RandomStringUtils.randomAlphabetic(10));
    }

    public Client withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public Client withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Client withPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    @Override
    public Map<String, Object> getParameterMapping() {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("first_name", this.getFirstName());
        parameters.put("last_name", this.getLastName());
        parameters.put("passport", this.getPassportNumber());
        return parameters;
    }

    @Override
    public String toString() {
        return this.getFirstName() + " " + this.getLastName() + " {" + this.getPassportNumber() + "}";
    }
}
