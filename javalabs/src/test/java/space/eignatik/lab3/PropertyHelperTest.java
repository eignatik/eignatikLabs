package space.eignatik.lab3;

import org.testng.annotations.Test;
import space.eignatik.exceptions.LabException;

import java.util.Optional;

import static org.testng.Assert.*;
import static space.eignatik.lab3.PropertyHelper.*;

public class PropertyHelperTest {

    @Test
    public void testGetPropertyCorrectly() {
        Optional prop = getProperty(this.getClass().getResource("/properties/app.properties").getPath(), "database");
        assertEquals(prop.orElse(null), "true");
    }

    @Test
    public void testGetEmptyProperties() {
        Optional prop = getProperty(this.getClass().getResource("/properties/emptyProperty.properties").getPath(), "database");
        assertEquals(prop.orElse(null), null);
    }

    @Test
    public void testGetDuplicatedProperties() {
        Optional prop = getProperty(this.getClass().getResource("/properties/isNotValidProperties.properties").getPath(), "property");
        assertEquals(prop.orElse(null), "cat");
    }

    @Test(expectedExceptions = LabException.class)
    public void testIfPathEmpty() {
        getProperty("", "param");
    }

    @Test(expectedExceptions = LabException.class)
    public void testIfParamIsEmpty() {
        getProperty("path", "");
    }

    @Test(expectedExceptions = LabException.class)
    public void testIfParamIsNull() {
        getProperty("path", null);
    }

    @Test(expectedExceptions = LabException.class)
    public void testIfPathIsNull() {
        getProperty(null, "apfs");
    }

}