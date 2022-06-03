package dbApp.utils;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertiesService {

    private PropertiesService() {}

    private static final Properties properties = new Properties();

    /*
     * этот блок обрабатывается либо при 1-ом запуске конструктора,
     * либо при первом вызове статического метода (причем блок отработает перед методом)
     * */
    static {
        try {
            properties.load(new FileReader("src/main/resources/database.properties"));
        } catch (IOException e) {
            System.err
                .println("APP INIT ERROR: Cannot load properties file");
            System.exit(1);
        }
    }

//    public synchronized static String getProperty(String key) {
//        return properties.getProperty(key);
//    }

    public static final String DEFAULT_DB_URL = properties.getProperty("DEFAULT_DB_URL");

    public static final String READER_USERNAME = properties.getProperty("READER_USERNAME");
    public static final String READER_PASSWORD = properties.getProperty("READER_PASSWORD");

    public static final String USED_JDBC_DRIVER = properties.getProperty("USED_JDBC_DRIVER");

    public static final int MAX_ERROR_MESSAGE_LENGTH
        = Integer.parseInt(properties.getProperty("MAX_ERROR_MESSAGE_LENGTH"));
}
