package dbApp;

import dbApp.view.View;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        View view = new View();
        view.run();
    }
}
