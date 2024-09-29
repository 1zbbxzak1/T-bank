package backend.academy.properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ConfigLoader {
    private static final Properties PROPERTIES = new Properties();
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigLoader.class);

    static {
        try {
            loadProperties();
        } catch (RuntimeException e) {
            LOGGER.error("Не удалось загрузить конфигурационный файл.", e);
            throw e;
        }
    }

    private ConfigLoader() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static void loadProperties() {
        String configFilePath = System.getProperty("config.properties.path", "src/main/resources/config.properties");
        try (FileInputStream inputStream = new FileInputStream(configFilePath)) {
            PROPERTIES.load(inputStream);
        } catch (IOException e) {
            LOGGER.error("Не удалось загрузить config", e);
            throw new RuntimeException("Не удалось загрузить конфигурационный файл", e);
        }
    }

    public static String getProperty(String key) {
        return PROPERTIES.getProperty(key);
    }
}

