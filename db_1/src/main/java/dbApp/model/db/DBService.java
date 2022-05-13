//package dbApp.model.db;
//
//import dbApp.util.PropertiesService;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//
//public class DBService implements AutoCloseable {
//
//    private Connection dbConnection = null;
//
//    public DBService(String dbURL, String username, char[] password) throws SQLException {
//        try {
//            Class.forName("org.postgresql.Driver");
//        } catch (ClassNotFoundException e) {
//            System.err
//                .println("APP INIT ERROR: JDBC Driver is not found");
//            System.exit(1);
//        }
//
//        dbConnection = DriverManager
//            .getConnection(dbURL, username, String.valueOf(password));
//    }
//
//    @Override
//    public void close() throws Exception {
//        dbConnection.close();
//    }
//}
