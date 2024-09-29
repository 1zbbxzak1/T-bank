package backend.academy.test;

import backend.academy.properties.ConfigLoader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ConfigLoaderTest {

    @BeforeAll
    public static void setUp() {
        // Загрузка свойств перед запуском тестов
        ConfigLoader.loadProperties();
    }

    @Test
    public void testGetPropertyValidKey() {
        // в config.properties есть свойство "test.key" со значением "testValue"
        String value = ConfigLoader.getProperty("test.key");
        Assertions.assertEquals("testValue", value);
    }

    @Test
    public void testGetPropertyInvalidKey() {
        // Если ключа нет, должен вернуть null
        String value = ConfigLoader.getProperty("invalid.key");
        Assertions.assertNull(value);
    }

    @Test
    public void testLoadPropertiesFileNotFound() {
        // Установите временный путь, который не существует
        String nonExistentPath = "src/main/resources/non_existent_config.properties";

        // Изменяем путь к файлу свойств, чтобы он указывал на несуществующий файл
        System.setProperty("config.properties.path", nonExistentPath);

        // Проверяем, что выбрасывается исключение при попытке загрузки
        Exception exception = assertThrows(RuntimeException.class, ConfigLoader::loadProperties);

        // Проверяем, что сообщение об ошибке соответствует ожидаемому
        Assertions.assertEquals("Не удалось загрузить конфигурационный файл", exception.getMessage());
    }
}
