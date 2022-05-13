package dbApp.model.db;

import dbApp.model.db.tables.Technologies;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBase {

    private final Technologies technologiesTable;

    private final DBService dbService;

    public DataBase(String dbURL, String username, char[] password) throws SQLException {
        dbService = new DBService(dbURL, username, password);

        technologiesTable = new Technologies(dbService);
    }

    public DBService getDbService() throws SQLException {
        return dbService;
    }

    // Создание всех таблиц и ключей между ними
    public void createAndLinkTables() throws SQLException {
        technologiesTable.createTable();
//// Создание внешних ключей
//        shareRates.createForeignKeys();
    }

    public Technologies getTechnologiesTable() {
        return technologiesTable;
    }

    public void close() {
        dbService.close();
    }

    public static class DBService {

        private Connection dbConnection = null;

        public DBService(String dbURL, String username, char[] password) throws SQLException {
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException e) {
                System.err
                    .println("APP INIT ERROR: JDBC Driver is not found");
                System.exit(1);
            }

            dbConnection = DriverManager
                .getConnection(dbURL, username, String.valueOf(password));
        }

        public Connection getDbConnection() {
            try {
                if (dbConnection == null || dbConnection.isClosed()) {
                    return null; // TODO: тут нужна бизнес-логика
                } else {
                    return dbConnection;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        public void close() {
            try {
                dbConnection.close();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
