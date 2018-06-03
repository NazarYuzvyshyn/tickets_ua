package gdTicketsUaDesctop.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static gdTicketsUaDesctop.utils.loggers.Logger.error;
import static org.testng.Assert.assertTrue;

/**
 * @author Назар on 14.09.2016.
 */
public class PropertyReader {

    private Properties properties;

    public PropertyReader(String filePath) {
        InputStream inputStream;
        properties = new Properties();
        try {
            File file = new File(filePath);
            inputStream = new FileInputStream(file.getPath());
            properties.load(inputStream);
        } catch (IOException e) {
            error("File doesn't exist or other IO exception");
            error(e.getMessage());
            assertTrue(false);
        }
    }

    public String getValue(String key) {
        return properties.getProperty(key);
    }

}
