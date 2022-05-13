package dbApp.view;

import dbApp.view.gui.MainWindow;
import dbApp.view.gui.panels.LoginPanel;

public class View implements Runnable {

    @Override
    public void run() {
        MainWindow mainWindow = new MainWindow();
        LoginPanel loginPanel = new LoginPanel(mainWindow);
        loginPanel.run();
    }
}
