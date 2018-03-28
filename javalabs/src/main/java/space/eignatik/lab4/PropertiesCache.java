package space.eignatik.lab4;

import space.eignatik.exceptions.LabException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PropertiesCache {
    private Map<String, String> properties;

    public PropertiesCache(String filePath) {
        properties = parseFileToMap(filePath);
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    private Map<String, String> parseFileToMap(String filePath) {
        Map<String, String> properties = new HashMap<>();
        File file = new File(filePath);
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String input;
            while ((input = reader.readLine()) != null) {
                String[] props = input.split("=");
                properties.put(props[0], props[1]);
            }
        } catch (IOException e) {
            throw new LabException(e);
        }
        return properties;
    }
}
