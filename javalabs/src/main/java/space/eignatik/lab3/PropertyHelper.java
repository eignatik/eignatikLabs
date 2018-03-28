package space.eignatik.lab3;

import space.eignatik.exceptions.LabException;

import java.io.*;
import java.net.URL;
import java.nio.file.Path;
import java.util.Optional;

public class PropertyHelper {

    /**
     *
     * @param pathToFile
     * @param propertyName
     * @return
     */
    public static Optional getProperty(String pathToFile, String propertyName) {
        if (pathToFile == null || propertyName == null || propertyName.isEmpty()) {
            throw new LabException("pathToFIle = " + pathToFile + ", propertyName = " + propertyName);
        }

        return Optional.ofNullable(findProperty(pathToFile, propertyName));
    }

    private static String findProperty(String pathToFile, String propertyName) {
        File file = new File(pathToFile);
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String input;
            while ((input = reader.readLine()) != null) {
                String[] props = input.split("=");
                if (propertyName.equals(props[0])) return props[1];
            }
        } catch (IOException e) {
            throw new LabException(e);
        }
        return null;
    }
}
