package dbApp.model.db;

import dbApp.model.db.tables.Technologies;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBase {

    public DbUserRole getCurSessionUserRole() {
        return curSessionUserRole;
    }

    public enum DbUserRole {
        ADMIN,
        READER,
        SUPPLIER
    }

    private final DbUserRole curSessionUserRole;

    private final Technologies technologiesTable;

    private final DBService dbService;

    public DataBase(String dbURL, String username, char[] password) throws SQLException {
        dbService = new DBService(dbURL, username, password);
        technologiesTable = new Technologies(dbService);

        curSessionUserRole = identifyUserRole(username);
        setRoleRights();
    }

    private DbUserRole identifyUserRole(String username) throws SQLException {
        String sql = """
            SELECT rolname FROM pg_roles
            WHERE oid = (SELECT roleid FROM pg_auth_members
                         WHERE member = (SELECT usesysid FROM pg_user
                                         WHERE usename = ?))""";

        PreparedStatement preparedStatement = dbService.getDbConnection().prepareStatement(sql);
        preparedStatement.setString(1, username);

        ResultSet resultSet = preparedStatement.executeQuery();

        resultSet.next();
        switch (resultSet.getString("rolname")) {
            case "admin" -> {
                return DbUserRole.ADMIN;
            }
            case "supplier" -> {
                return DbUserRole.SUPPLIER;
            }
            default -> {
                return DbUserRole.READER;
            }
        }
    }

    private void setRoleRights() throws SQLException {
        String sql = switch (curSessionUserRole) {
            case ADMIN -> "SET ROLE \"admin\"";
            case READER -> "SET ROLE \"reader\"";
            case SUPPLIER -> "SET ROLE \"supplier\"";
        };

        Statement statement = dbService.getDbConnection().createStatement();
        statement.execute(sql);
        statement.close();
    }

    public DBService getDbService() {
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
