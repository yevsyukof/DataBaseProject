package dbApp;

import dbApp.view.View;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

//    static final String USER = "Tolya";
//    static final String PASS = "qwerty12345";

    public static void main(String[] args) {
        View view = new View();
        view.run();

//        System.out.println("Testing connection to PostgreSQL JDBC");
//
//
//
//        System.out.println("PostgreSQL JDBC Driver successfully connected");
//        Connection connection = null;
//
//
//        if (connection != null) {
//            System.out.println("You successfully connected to database now");
//        } else {
//            System.out.println("Failed to make connection to database");
//        }
    }
}
