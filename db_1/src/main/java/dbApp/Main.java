package dbApp;

import dbApp.gui.MainWindow;
import dbApp.gui.panels.LoginPanel;

public class Main {

    public static void main(String[] args) {
        MainWindow mainWindow = new MainWindow();
        LoginPanel loginPanel = new LoginPanel(mainWindow);
        loginPanel.run();
    }
}
