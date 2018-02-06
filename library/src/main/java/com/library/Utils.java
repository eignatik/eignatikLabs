package com.library;

import com.library.database.BookDao;
import com.library.database.ClientDao;
import com.library.database.DbConnection;
import com.library.database.JournalDao;
import com.library.domain.BaseObject;
import com.library.domain.Book;
import com.library.domain.Journal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Utils {
    private static final Logger logger = LogManager.getLogger();

    public static boolean isAdmin;

    public static Properties loadProperties() {
        Properties properties = new Properties();
        try {
            InputStream stream = DbConnection.class.getClassLoader().getResourceAsStream("environment.properties");
            properties.load(stream);
        } catch (IOException e) {
            logger.error(e);
        }
        return properties;
    }

    public static LocalDate randomDate() {
        long minDay = LocalDate.of(2016, 1, 1).toEpochDay();
        long maxDay = LocalDate.now().toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
        return LocalDate.ofEpochDay(randomDay);
    }

    public static <T> T randomElement(List<T> list) {
        Random random = new Random();
        int index = random.nextInt(list.size());
        return list.get(index);
    }
}
